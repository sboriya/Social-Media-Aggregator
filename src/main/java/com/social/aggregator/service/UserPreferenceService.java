package com.social.aggregator.service;

import com.social.aggregator.dto.UserPreferenceRequestDTO;
import com.social.aggregator.dto.UserPreferenceResponseDTO;
import com.social.aggregator.entity.UserPreference;
import com.social.aggregator.enums.Theme;
import com.social.aggregator.exception.InvalidThemeException;
import com.social.aggregator.exception.UserAlreadyExistsException;
import com.social.aggregator.exception.UserNotPresentException;
import com.social.aggregator.repository.UserPreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserPreferenceService {

    @Autowired
    private UserPreferenceRepository userPreferenceRepository;

    public UserPreferenceResponseDTO createUserPreference(UserPreferenceRequestDTO preferenceDTO) {

        if (!Theme.isValidTheme(preferenceDTO.getTheme().toUpperCase())) {
            throw new InvalidThemeException(preferenceDTO.getTheme());
        }

        if (userPreferenceRepository.findByUsername(preferenceDTO.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException(preferenceDTO.getUsername());
        }

        UserPreference userPreference = new UserPreference();
        userPreference.setUsername(preferenceDTO.getUsername());
        userPreference.setTheme(Theme.valueOf(preferenceDTO.getTheme().toUpperCase()));
        userPreference.setSelectedPlatforms(preferenceDTO.getSelectedPlatforms());
        userPreference = userPreferenceRepository.save(userPreference);

        return UserPreferenceResponseDTO.builder().userId(userPreference.getId()).username(userPreference.getUsername()).theme(userPreference.getTheme().name()).selectedPlatforms(userPreference.getSelectedPlatforms()).build();
    }

    public UserPreferenceResponseDTO updateUserPreference(UserPreferenceRequestDTO userPreferenceRequestDTO) {
        if (userPreferenceRequestDTO.getUsername() == null || userPreferenceRequestDTO.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username is required. Please provide a valid username.");
        }

        if (!Theme.isValidTheme(userPreferenceRequestDTO.getTheme().toUpperCase())) {
            throw new InvalidThemeException(userPreferenceRequestDTO.getTheme());
        }

        Optional<UserPreference> userPreference = userPreferenceRepository.findByUsername(userPreferenceRequestDTO.getUsername());
        if (userPreference.isEmpty()) {
            throw new UserNotPresentException(userPreferenceRequestDTO.getUsername());
        } else {
            UserPreference userPreferenceToSave = userPreference.get();
            if (userPreferenceRequestDTO.getTheme() != null) {
                userPreferenceToSave.setTheme(Theme.valueOf(userPreferenceRequestDTO.getTheme().toUpperCase()));
            }

            if (userPreferenceRequestDTO.getSelectedPlatforms() != null) {
                userPreferenceToSave.setSelectedPlatforms(userPreferenceRequestDTO.getSelectedPlatforms());
            }

            userPreferenceToSave = userPreferenceRepository.save(userPreferenceToSave);

            return UserPreferenceResponseDTO.builder().userId(userPreferenceToSave.getId()).username(userPreferenceToSave.getUsername()).theme(userPreferenceToSave.getTheme().name()).selectedPlatforms(userPreferenceToSave.getSelectedPlatforms()).build();
        }
    }

    public Optional<UserPreferenceResponseDTO> getUserPreference(String username) {
        return userPreferenceRepository.findByUsername(username)
                .map(userPreference -> UserPreferenceResponseDTO.builder()
                        .userId(userPreference.getId())
                        .username(userPreference.getUsername())
                        .theme(userPreference.getTheme().name())
                        .selectedPlatforms(userPreference.getSelectedPlatforms())
                        .build());
    }
}
