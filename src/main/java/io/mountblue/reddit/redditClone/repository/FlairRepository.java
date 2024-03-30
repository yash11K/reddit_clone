package io.mountblue.reddit.redditClone.repository;

import io.mountblue.reddit.redditClone.model.Flair;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FlairRepository extends JpaRepository<Flair, Long >{
    Optional<Flair> findFlairByFlairName(String name);
}
