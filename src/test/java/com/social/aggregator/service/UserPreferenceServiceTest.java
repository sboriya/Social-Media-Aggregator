package com.social.aggregator.service;

import com.social.aggregator.dto.UserPreferenceRequestDTO;
import com.social.aggregator.dto.UserPreferenceResponseDTO;
import com.social.aggregator.entity.UserPreference;
import com.social.aggregator.enums.Theme;
import com.social.aggregator.exception.InvalidThemeException;
import com.social.aggregator.exception.UserAlreadyExistsException;
import com.social.aggregator.exception.UserNotPresentException;
import com.social.aggregator.repository.UserPreferenceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserPreferenceServiceTest {

    @Mock
    private UserPreferenceRepository userPreferenceRepository;

    @InjectMocks
    private UserPreferenceService userPreferenceService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUserPreference_Success() {
        UserPreferenceRequestDTO requestDTO = UserPreferenceRequestDTO.builder()
                .username("testUser")
                .theme("DARK")
                .selectedPlatforms("Facebook,Twitter")
                .build();

        UserPreference userPreference = new UserPreference();
        userPreference.setId(1L);
        userPreference.setUsername("testUser");
        userPreference.setTheme(Theme.DARK);
        userPreference.setSelectedPlatforms("Facebook,Twitter");

        when(userPreferenceRepository.findByUsername("testUser")).thenReturn(Optional.empty());
        when(userPreferenceRepository.save(any(UserPreference.class))).thenReturn(userPreference);

        UserPreferenceResponseDTO responseDTO = userPreferenceService.createUserPreference(requestDTO);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getUserId());
        assertEquals("testUser", responseDTO.getUsername());
        assertEquals("DARK", responseDTO.getTheme());
        assertEquals("Facebook,Twitter", responseDTO.getSelectedPlatforms());

        verify(userPreferenceRepository, times(1)).findByUsername("testUser");
        verify(userPreferenceRepository, times(1)).save(any(UserPreference.class));
    }

    @Test
    public void testCreateUserPreference_InvalidTheme() {
        UserPreferenceRequestDTO requestDTO = UserPreferenceRequestDTO.builder()
                .username("testUser")
                .theme("INVALID_THEME")
                .selectedPlatforms("Facebook,Twitter")
                .build();

        assertThrows(InvalidThemeException.class, () -> userPreferenceService.createUserPreference(requestDTO));

        verify(userPreferenceRepository, never()).findByUsername(anyString());
        verify(userPreferenceRepository, never()).save(any(UserPreference.class));
    }

    @Test
    public void testCreateUserPreference_UserAlreadyExists() {
        UserPreferenceRequestDTO requestDTO = UserPreferenceRequestDTO.builder()
                .username("testUser")
                .theme("DARK")
                .selectedPlatforms("Facebook,Twitter")
                .build();

        when(userPreferenceRepository.findByUsername("testUser")).thenReturn(Optional.of(new UserPreference()));

        assertThrows(UserAlreadyExistsException.class, () -> userPreferenceService.createUserPreference(requestDTO));

        verify(userPreferenceRepository, times(1)).findByUsername("testUser");
        verify(userPreferenceRepository, never()).save(any(UserPreference.class));
    }

    @Test
    public void testUpdateUserPreference_Success() {
        UserPreferenceRequestDTO requestDTO = UserPreferenceRequestDTO.builder()
                .username("testUser")
                .theme("LIGHT")
                .selectedPlatforms("Instagram,Yahoo")
                .build();

        UserPreference existingUserPreference = new UserPreference();
        existingUserPreference.setId(1L);
        existingUserPreference.setUsername("testUser");
        existingUserPreference.setTheme(Theme.DARK);
        existingUserPreference.setSelectedPlatforms("Facebook,Twitter");

        UserPreference updatedUserPreference = new UserPreference();
        updatedUserPreference.setId(1L);
        updatedUserPreference.setUsername("testUser");
        updatedUserPreference.setTheme(Theme.LIGHT);
        updatedUserPreference.setSelectedPlatforms("Instagram,Yahoo");

        when(userPreferenceRepository.findByUsername("testUser")).thenReturn(Optional.of(existingUserPreference));
        when(userPreferenceRepository.save(any(UserPreference.class))).thenReturn(updatedUserPreference);

        UserPreferenceResponseDTO responseDTO = userPreferenceService.updateUserPreference(requestDTO);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getUserId());
        assertEquals("testUser", responseDTO.getUsername());
        assertEquals("LIGHT", responseDTO.getTheme());
        assertEquals("Instagram,Yahoo", responseDTO.getSelectedPlatforms());

        verify(userPreferenceRepository, times(1)).findByUsername("testUser");
        verify(userPreferenceRepository, times(1)).save(any(UserPreference.class));
    }

    @Test
    public void testUpdateUserPreference_UserNotFound() {
        UserPreferenceRequestDTO requestDTO = UserPreferenceRequestDTO.builder()
                .username("testUser")
                .theme("LIGHT")
                .selectedPlatforms("Instagram,Yahoo")
                .build();

        when(userPreferenceRepository.findByUsername("testUser")).thenReturn(Optional.empty());

        assertThrows(UserNotPresentException.class, () -> userPreferenceService.updateUserPreference(requestDTO));

        verify(userPreferenceRepository, times(1)).findByUsername("testUser");
        verify(userPreferenceRepository, never()).save(any(UserPreference.class));
    }

    @Test
    public void testUpdateUserPreference_InvalidTheme() {
        UserPreferenceRequestDTO requestDTO = UserPreferenceRequestDTO.builder()
                .username("testUser")
                .theme("INVALID_THEME")
                .selectedPlatforms("Instagram,Yahoo")
                .build();

        UserPreference existingUserPreference = new UserPreference();
        existingUserPreference.setUsername("testUser");

        when(userPreferenceRepository.findByUsername("testUser")).thenReturn(Optional.of(existingUserPreference));

        assertThrows(InvalidThemeException.class, () -> userPreferenceService.updateUserPreference(requestDTO));

        verify(userPreferenceRepository, times(0)).findByUsername("testUser");
        verify(userPreferenceRepository, never()).save(any(UserPreference.class));
    }

    @Test
    public void testGetUserPreference_Success() {
        UserPreference userPreference = new UserPreference();
        userPreference.setId(1L);
        userPreference.setUsername("testUser");
        userPreference.setTheme(Theme.DARK);
        userPreference.setSelectedPlatforms("Facebook,Twitter");

        when(userPreferenceRepository.findByUsername("testUser")).thenReturn(Optional.of(userPreference));

        Optional<UserPreferenceResponseDTO> responseDTO = userPreferenceService.getUserPreference("testUser");

        assertTrue(responseDTO.isPresent());
        assertEquals(1L, responseDTO.get().getUserId());
        assertEquals("testUser", responseDTO.get().getUsername());
        assertEquals("DARK", responseDTO.get().getTheme());
        assertEquals("Facebook,Twitter", responseDTO.get().getSelectedPlatforms());

        verify(userPreferenceRepository, times(1)).findByUsername("testUser");
    }

    @Test
    public void testGetUserPreference_UserNotFound() {
        when(userPreferenceRepository.findByUsername("testUser")).thenReturn(Optional.empty());

        Optional<UserPreferenceResponseDTO> responseDTO = userPreferenceService.getUserPreference("testUser");

        assertFalse(responseDTO.isPresent());

        verify(userPreferenceRepository, times(1)).findByUsername("testUser");
    }
}
