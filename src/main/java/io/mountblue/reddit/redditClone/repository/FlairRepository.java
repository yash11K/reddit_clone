package io.mountblue.reddit.redditClone.repository;

import io.mountblue.reddit.redditClone.model.Flair;
import io.mountblue.reddit.redditClone.model.SubReddit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FlairRepository extends JpaRepository<Flair, Long >{
    Optional<Flair> findFlairByFlairName(String name);

    List<Flair> findFlairsBySubReddit(SubReddit subReddit);

    List<Flair> findFlairsByFlairNameInOrderByFlairName(List<String> flairNames);
}
