package project2.service;

import project2.model.Rank;

import java.util.List;
import project2.model.Role;

import java.util.List;
import java.util.Optional;

public interface IRankService {
    List<Rank> findAllRank();

    Rank findById(Long id);

    List<Rank> findAll();

    Optional<Rank> findByName(String nameRank);
}
