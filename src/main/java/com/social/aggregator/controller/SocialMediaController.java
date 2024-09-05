package com.social.aggregator.controller;

import com.social.aggregator.dto.SocialMediaFeedDTO;
import com.social.aggregator.service.SocialMediaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/social-media")
@Tag(name = "Social Media Feed APIs")
public class SocialMediaController {

    @Autowired
    private SocialMediaService socialMediaService;

    @GetMapping("/feeds/{userId}")
    @Operation(summary = "Fetch feed for specific plateform")
    public SocialMediaFeedDTO getAggregatedFeeds(@PathVariable Long userId, @RequestParam String platform) {
        return socialMediaService.getAggregatedFeeds(userId, platform);
    }
}
