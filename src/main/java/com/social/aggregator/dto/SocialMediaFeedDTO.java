package com.social.aggregator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SocialMediaFeedDTO {
    private List<String> feeds;
}