package project2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project2.model.Rank;
import project2.repository.IRankRepository;
import project2.service.IRankService;

import java.util.List;
import java.util.Optional;


@Service
public class RankService implements IRankService {

    @Autowired
    private IRankRepository iRankRepository;

    @Override
    public List<Rank> findAllRank() {
        return iRankRepository.findAll();
    }

    @Override
    public Rank findById(Long id) {
        return iRankRepository.findById(id).orElse(null);
    }


    @Override
    public List<Rank> findAll() {
        return iRankRepository.findAll();
    }

    @Override
    public Optional<Rank> findByName(String nameRank) {
        return iRankRepository.findByNameRank(nameRank);
    }
}
