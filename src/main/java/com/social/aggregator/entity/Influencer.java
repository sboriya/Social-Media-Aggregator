package com.social.aggregator.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "influencer")
public class Influencer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
    private String category;
    private String facebookUrl;
    private String instagramUrl;
    private String twitterUrl;
    private String yahooUrl;

    public Influencer(String name, String category, String facebookUrl, String instagramUrl, String twitterUrl, String yahooUrl) {
        this.name = name;
        this.category = category;
        this.facebookUrl = facebookUrl;
        this.instagramUrl = instagramUrl;
        this.twitterUrl = twitterUrl;
        this.yahooUrl = yahooUrl;
    }

    public Influencer(long id, String influencer1) {
        this.id = id;
        this.name = influencer1;
    }
}
