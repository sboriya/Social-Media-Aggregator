package com.social.aggregator.service;

import com.social.aggregator.dto.InfluencerRequestDTO;
import com.social.aggregator.dto.InfluencerResponseDTO;
import com.social.aggregator.entity.Influencer;
import com.social.aggregator.exception.UserAlreadyExistsException;
import com.social.aggregator.exception.UserNotPresentException;
import com.social.aggregator.repository.InfluencerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InfluencerService {

    @Autowired
    InfluencerRepository influencerRepository;

    private static InfluencerResponseDTO getInfluencerResponseDTO(Influencer influencer) {
        return InfluencerResponseDTO.builder().id(influencer.getId())
                .name(influencer.getName())
                .facebookUrl(influencer.getFacebookUrl())
                .instagramUrl(influencer.getInstagramUrl())
                .twitterUrl(influencer.getTwitterUrl())
                .yahooUrl(influencer.getYahooUrl())
                .category(influencer.getCategory())
                .build();
    }

    public InfluencerResponseDTO createInfluencer(InfluencerRequestDTO influencerRequestDTO) throws UserAlreadyExistsException {
        if (influencerRepository.findByName(influencerRequestDTO.getName()).isPresent()) {
            throw new UserAlreadyExistsException(influencerRequestDTO.getName());
        }
        Influencer influencer = getInfluencerDetails(influencerRequestDTO);
        return getInfluencerResponseDTO(influencer);

    }

    public Optional<InfluencerResponseDTO> getInfluencer(Long id) throws UserNotPresentException {
        return influencerRepository.findById(id)
                .map(influencer -> getInfluencerResponseDTO(influencer));
    }

    private Influencer getInfluencerDetails(InfluencerRequestDTO influencerRequestDTO) {
        Influencer influencer = new Influencer();
        influencer.setCategory(influencerRequestDTO.getCategory());
        influencer.setName(influencerRequestDTO.getName());
        influencer.setFacebookUrl(influencerRequestDTO.getFacebookUrl());
        influencer.setTwitterUrl(influencerRequestDTO.getTwitterUrl());
        influencer.setYahooUrl(influencerRequestDTO.getYahooUrl());
        influencer.setInstagramUrl(influencerRequestDTO.getInstagramUrl());
        influencer = influencerRepository.save(influencer);
        return influencer;
    }
}
