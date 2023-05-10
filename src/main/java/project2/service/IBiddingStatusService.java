package project2.service;

import project2.model.BiddingStatus;
import project2.model.ImageProduct;

import java.util.List;

public interface IBiddingStatusService {
    List<BiddingStatus> findByAll();
    BiddingStatus findById(Long id);
}
