package com.social.aggregator.service;

import com.social.aggregator.entity.Influencer;
import com.social.aggregator.entity.UserPreference;
import com.social.aggregator.enums.Theme;
import com.social.aggregator.repository.InfluencerRepository;
import com.social.aggregator.repository.UserPreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class DataInitializer {

    @Autowired
    private UserPreferenceRepository userPreferenceRepository;

    @Autowired
    private InfluencerRepository influencerRepository;

    @PostConstruct
    public void loadData() {
        loadInfluencers();
        loadUserPreferences();
    }

    private void loadUserPreferences() {
        Set<Influencer> influencers1 = new HashSet<>(influencerRepository.findAllByNameIn(Set.of("M Dhoni", "N Modi")));
        Set<Influencer> influencers2 = new HashSet<>(influencerRepository.findAllByNameIn(Set.of("Deepika Padukone")));

        saveUserPreferenceIfNotExists(new UserPreference("sourabh", Theme.DARK, "Facebook,Twitter", influencers1));
        saveUserPreferenceIfNotExists(new UserPreference("pawan", Theme.LIGHT, "Instagram,Yahoo", influencers2));
        saveUserPreferenceIfNotExists(new UserPreference("rahul", Theme.DARK, "Twitter,Instagram", influencers1));
    }

    private void saveUserPreferenceIfNotExists(UserPreference preference) {
        Optional<UserPreference> existingPreference = userPreferenceRepository.findByUsername(preference.getUsername());
        if (existingPreference.isEmpty()) {
            userPreferenceRepository.save(preference);
        }
    }

    private void loadInfluencers() {
        saveInfluencerIfNotExists(new Influencer("M Dhoni", "Sports", "http://facebook.com/mdhoni", "http://instagram.com/mdhoni", "http://twitter.com/mdhoni", "http://yahoo.com/mdhoni"));
        saveInfluencerIfNotExists(new Influencer("N Modi", "Politics", "http://facebook.com/nmodi", "http://instagram.com/nmodi", "http://twitter.com/nmodi", "http://yahoo.com/nmodi"));
        saveInfluencerIfNotExists(new Influencer("Deepika Padukone", "Entertainment", "http://facebook.com/dpadukone", "http://instagram.com/dpadukone", "http://twitter.com/dpadukone", "http://yahoo.com/dpadukone"));
    }

    private void saveInfluencerIfNotExists(Influencer influencer) {
        Optional<Influencer> existingInfluencer = influencerRepository.findByName(influencer.getName());
        if (existingInfluencer.isEmpty()) {
            influencerRepository.save(influencer);
        }
    }
}
