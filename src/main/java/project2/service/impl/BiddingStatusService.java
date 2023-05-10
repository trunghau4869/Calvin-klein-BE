package project2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project2.model.BiddingStatus;
import project2.repository.IBiddingStatusRepository;
import project2.service.IBiddingStatusService;

import java.util.List;

@Service
public class BiddingStatusService implements IBiddingStatusService {
    @Autowired
    private IBiddingStatusRepository iBiddingStatusRepository;

    @Override
    public BiddingStatus findById(Long id) {
        return this.iBiddingStatusRepository.findById(id).orElse(null);
    }
    @Override

    public List<BiddingStatus> findByAll(){
        return this.iBiddingStatusRepository.findAll();
    }
}
