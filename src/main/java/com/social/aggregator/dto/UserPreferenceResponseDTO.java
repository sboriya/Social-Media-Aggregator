package com.social.aggregator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPreferenceResponseDTO {
        private Long userId;
        private String username;
        private String theme;
        private String selectedPlatforms;
}
