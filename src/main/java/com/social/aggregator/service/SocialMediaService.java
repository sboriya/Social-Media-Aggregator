package com.social.aggregator.service;

import com.social.aggregator.dto.SocialMediaFeedDTO;
import com.social.aggregator.entity.Influencer;
import com.social.aggregator.entity.UserPreference;
import com.social.aggregator.repository.InfluencerRepository;
import com.social.aggregator.repository.UserPreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SocialMediaService {

    @Autowired
    private UserPreferenceRepository userPreferenceRepository;

    @Autowired
    private InfluencerRepository influencerRepository;

    public SocialMediaFeedDTO getAggregatedFeeds(Long userId, String platform) {
        Optional<UserPreference> userPreferenceOpt = userPreferenceRepository.findById(userId);

        if (userPreferenceOpt.isEmpty()) {
            throw new RuntimeException("User preferences not found");
        }

        UserPreference userPreference = userPreferenceOpt.get();
        List<Long> influencerIdList = userPreference.getInfluencers().stream()
                .map(Influencer::getId)
                .collect(Collectors.toList());
        List<Influencer> influencers = influencerRepository.findAllById(influencerIdList);

        List<String> feeds = influencers.stream()
                .map(influencer -> fetchFeedFromPlatform(influencer, platform))
                .collect(Collectors.toList());

        return new SocialMediaFeedDTO(feeds);
    }

    String fetchFeedFromPlatform(Influencer influencer, String platform) {
        switch (platform.toLowerCase()) {
            case "facebook":
                return "Facebook feed from " + influencer.getName();
            case "instagram":
                return "Instagram feed from " + influencer.getName();
            case "twitter":
                return "Twitter feed from " + influencer.getName();
            case "yahoo":
                return "Yahoo feed from " + influencer.getName();
            default:
                throw new IllegalArgumentException("Unsupported platform: " + platform);
        }
    }
}

