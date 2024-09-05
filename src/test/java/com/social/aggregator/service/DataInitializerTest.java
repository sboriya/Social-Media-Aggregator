package com.social.aggregator.service;

import com.social.aggregator.entity.Influencer;
import com.social.aggregator.entity.UserPreference;
import com.social.aggregator.enums.Theme;
import com.social.aggregator.repository.InfluencerRepository;
import com.social.aggregator.repository.UserPreferenceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DataInitializerTest {

    @Mock
    private UserPreferenceRepository userPreferenceRepository;

    @Mock
    private InfluencerRepository influencerRepository;

    @InjectMocks
    private DataInitializer dataInitializer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadData() {

        Influencer mDhoni = new Influencer("M Dhoni", "Sports", "http://facebook.com/mdhoni", "http://instagram.com/mdhoni", "http://twitter.com/mdhoni", "http://yahoo.com/mdhoni");
        Influencer nModi = new Influencer("N Modi", "Politics", "http://facebook.com/nmodi", "http://instagram.com/nmodi", "http://twitter.com/nmodi", "http://yahoo.com/nmodi");
        Influencer dPadukone = new Influencer("Deepika Padukone", "Entertainment", "http://facebook.com/dpadukone", "http://instagram.com/dpadukone", "http://twitter.com/dpadukone", "http://yahoo.com/dpadukone");

        when(influencerRepository.findAllByNameIn(Set.of("M Dhoni", "N Modi"))).thenReturn(Set.of(mDhoni, nModi));
        when(influencerRepository.findAllByNameIn(Set.of("Deepika Padukone"))).thenReturn(Set.of(dPadukone));

        when(userPreferenceRepository.findByUsername("sourabh")).thenReturn(Optional.empty());
        when(userPreferenceRepository.findByUsername("pawan")).thenReturn(Optional.empty());
        when(userPreferenceRepository.findByUsername("rahul")).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> dataInitializer.loadData());

        verify(influencerRepository, times(1)).save(mDhoni);
        verify(influencerRepository, times(1)).save(nModi);
        verify(influencerRepository, times(1)).save(dPadukone);

        verify(userPreferenceRepository, times(1)).save(new UserPreference("sourabh", Theme.DARK, "Facebook,Twitter", new HashSet<>(Set.of(mDhoni, nModi))));
        verify(userPreferenceRepository, times(1)).save(new UserPreference("pawan", Theme.LIGHT, "Instagram,Yahoo", new HashSet<>(Set.of(dPadukone))));
        verify(userPreferenceRepository, times(1)).save(new UserPreference("rahul", Theme.DARK, "Twitter,Instagram", new HashSet<>(Set.of(mDhoni, nModi))));
    }

    @Test
    public void testLoadData_WhenUserPreferencesExist() {

        Influencer mDhoni = new Influencer("M Dhoni", "Sports", "http://facebook.com/mdhoni", "http://instagram.com/mdhoni", "http://twitter.com/mdhoni", "http://yahoo.com/mdhoni");
        Influencer nModi = new Influencer("N Modi", "Politics", "http://facebook.com/nmodi", "http://instagram.com/nmodi", "http://twitter.com/nmodi", "http://yahoo.com/nmodi");
        Influencer dPadukone = new Influencer("Deepika Padukone", "Entertainment", "http://facebook.com/dpadukone", "http://instagram.com/dpadukone", "http://twitter.com/dpadukone", "http://yahoo.com/dpadukone");

        when(influencerRepository.findAllByNameIn(Set.of("M Dhoni", "N Modi"))).thenReturn(Set.of(mDhoni, nModi));
        when(influencerRepository.findAllByNameIn(Set.of("Deepika Padukone"))).thenReturn(Set.of(dPadukone));

        when(userPreferenceRepository.findByUsername("sourabh")).thenReturn(Optional.of(new UserPreference("sourabh", Theme.DARK, "Facebook,Twitter", new HashSet<>(Set.of(mDhoni, nModi)))));
        when(userPreferenceRepository.findByUsername("pawan")).thenReturn(Optional.of(new UserPreference("pawan", Theme.LIGHT, "Instagram,Yahoo", new HashSet<>(Set.of(dPadukone)))));
        when(userPreferenceRepository.findByUsername("rahul")).thenReturn(Optional.of(new UserPreference("rahul", Theme.DARK, "Twitter,Instagram", new HashSet<>(Set.of(mDhoni, nModi)))));

        assertDoesNotThrow(() -> dataInitializer.loadData());

        verify(influencerRepository, times(1)).save(mDhoni);
        verify(influencerRepository, times(1)).save(nModi);
        verify(influencerRepository, times(1)).save(dPadukone);

        verify(userPreferenceRepository, never()).save(any(UserPreference.class));
    }
}
