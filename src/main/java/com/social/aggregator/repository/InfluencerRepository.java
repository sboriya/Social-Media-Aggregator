package com.social.aggregator.repository;

import com.social.aggregator.entity.Influencer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface InfluencerRepository extends JpaRepository<Influencer, Long> {
    Optional<Influencer> findByName(String name);
    Set<Influencer> findAllByNameIn(Set<String> strings);
    List<Influencer> findAllById(Iterable<Long> ids);
}
