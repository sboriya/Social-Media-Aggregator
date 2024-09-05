package com.social.aggregator.service;

import com.social.aggregator.dto.InfluencerRequestDTO;
import com.social.aggregator.dto.InfluencerResponseDTO;
import com.social.aggregator.entity.Influencer;
import com.social.aggregator.exception.UserAlreadyExistsException;
import com.social.aggregator.exception.UserNotPresentException;
import com.social.aggregator.repository.InfluencerRepository;
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
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class InfluencerServiceTest {

    @Mock
    private InfluencerRepository influencerRepository;

    @InjectMocks
    private InfluencerService influencerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createInfluencer_shouldCreateAndReturnInfluencer_whenValidRequest() throws UserAlreadyExistsException {
        InfluencerRequestDTO requestDTO = new InfluencerRequestDTO();
        requestDTO.setName("M Dhoni");
        requestDTO.setCategory("Sports");
        requestDTO.setFacebookUrl("http://facebook.com/mdhoni");
        requestDTO.setInstagramUrl("http://instagram.com/mdhoni");
        requestDTO.setTwitterUrl("http://twitter.com/mdhoni");
        requestDTO.setYahooUrl("http://yahoo.com/mdhoni");

        Influencer savedInfluencer = new Influencer();
        savedInfluencer.setId(1L);
        savedInfluencer.setName("M Dhoni");
        savedInfluencer.setCategory("Sports");
        savedInfluencer.setFacebookUrl("http://facebook.com/mdhoni");
        savedInfluencer.setInstagramUrl("http://instagram.com/mdhoni");
        savedInfluencer.setTwitterUrl("http://twitter.com/mdhoni");
        savedInfluencer.setYahooUrl("http://yahoo.com/mdhoni");

        when(influencerRepository.findByName("M Dhoni")).thenReturn(Optional.empty());
        when(influencerRepository.save(any(Influencer.class))).thenReturn(savedInfluencer);

        InfluencerResponseDTO responseDTO = influencerService.createInfluencer(requestDTO);

        assertNotNull(responseDTO);
        assertEquals("M Dhoni", responseDTO.getName());
        assertEquals("Sports", responseDTO.getCategory());
        assertEquals("http://facebook.com/mdhoni", responseDTO.getFacebookUrl());
        verify(influencerRepository, times(1)).save(any(Influencer.class));
    }

    @Test
    void createInfluencer_shouldThrowException_whenInfluencerAlreadyExists() {
        InfluencerRequestDTO requestDTO = new InfluencerRequestDTO();
        requestDTO.setName("M Dhoni");

        Influencer existingInfluencer = new Influencer();
        existingInfluencer.setId(1L);
        existingInfluencer.setName("M Dhoni");

        when(influencerRepository.findByName("M Dhoni")).thenReturn(Optional.of(existingInfluencer));

        assertThrows(UserAlreadyExistsException.class, () -> influencerService.createInfluencer(requestDTO));
        verify(influencerRepository, never()).save(any(Influencer.class));
    }

    @Test
    void getInfluencer_shouldReturnInfluencer_whenInfluencerExists() throws UserNotPresentException {
        Influencer influencer = new Influencer();
        influencer.setId(1L);
        influencer.setName("M Dhoni");
        influencer.setCategory("Sports");
        influencer.setFacebookUrl("http://facebook.com/mdhoni");
        influencer.setInstagramUrl("http://instagram.com/mdhoni");
        influencer.setTwitterUrl("http://twitter.com/mdhoni");
        influencer.setYahooUrl("http://yahoo.com/mdhoni");

        when(influencerRepository.findById(1L)).thenReturn(Optional.of(influencer));

        Optional<InfluencerResponseDTO> responseDTO = influencerService.getInfluencer(1L);

        assertTrue(responseDTO.isPresent());
        assertEquals("M Dhoni", responseDTO.get().getName());
        assertEquals("Sports", responseDTO.get().getCategory());
        verify(influencerRepository, times(1)).findById(1L);
    }

    @Test
    void getInfluencer_shouldReturnEmpty_whenInfluencerDoesNotExist() {
        when(influencerRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<InfluencerResponseDTO> responseDTO = influencerService.getInfluencer(1L);

        assertFalse(responseDTO.isPresent());
        verify(influencerRepository, times(1)).findById(1L);
    }
}

