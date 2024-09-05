package com.social.aggregator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfluencerResponseDTO {
    private Long id;
    private String name;
    private String category;
    private String facebookUrl;
    private String instagramUrl;
    private String twitterUrl;
    private String yahooUrl;
}
