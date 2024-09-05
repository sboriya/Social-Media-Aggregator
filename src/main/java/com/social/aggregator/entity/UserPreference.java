package com.social.aggregator.entity;


import com.social.aggregator.enums.Theme;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.JoinTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_preference")
public class UserPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    private Theme theme;
    private String selectedPlatforms;

    @ManyToMany
    @JoinTable(
            name = "user_influencer",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "influencer_id")
    )
    private Set<Influencer> influencers;

    public UserPreference(String username, Theme theme, String selectedPlatforms,Set<Influencer> influencers) {
        this.username = username;
        this.theme = theme;
        this.selectedPlatforms = selectedPlatforms;
        this.influencers = influencers;
    }
}

