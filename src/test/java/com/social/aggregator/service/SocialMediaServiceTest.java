package com.social.aggregator.service;

import com.social.aggregator.entity.Influencer;
import com.social.aggregator.repository.InfluencerRepository;
import com.social.aggregator.repository.UserPreferenceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class SocialMediaServiceTest {

    @Mock
    private UserPreferenceRepository userPreferenceRepository;

    @Mock
    private InfluencerRepository influencerRepository;

    @InjectMocks
    private SocialMediaService socialMediaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAggregatedFeeds_UserPreferenceNotFound() {
        when(userPreferenceRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            socialMediaService.getAggregatedFeeds(1L, "facebook");
        });

        assertEquals("User preferences not found", thrown.getMessage());
    }


    @Test
    public void testFetchFeedFromPlatform_Facebook() {
        Influencer influencer = new Influencer(1L, "Influencer1");
        String feed = socialMediaService.fetchFeedFromPlatform(influencer, "facebook");
        assertEquals("Facebook feed from Influencer1", feed);
    }

    @Test
    public void testFetchFeedFromPlatform_Instagram() {
        Influencer influencer = new Influencer(1L, "Influencer1");
        String feed = socialMediaService.fetchFeedFromPlatform(influencer, "instagram");
        assertEquals("Instagram feed from Influencer1", feed);
    }

    @Test
    public void testFetchFeedFromPlatform_Twitter() {
        Influencer influencer = new Influencer(1L, "Influencer1");
        String feed = socialMediaService.fetchFeedFromPlatform(influencer, "twitter");
        assertEquals("Twitter feed from Influencer1", feed);
    }

    @Test
    public void testFetchFeedFromPlatform_Yahoo() {
        Influencer influencer = new Influencer(1L, "Influencer1");
        String feed = socialMediaService.fetchFeedFromPlatform(influencer, "yahoo");
        assertEquals("Yahoo feed from Influencer1", feed);
    }
}
