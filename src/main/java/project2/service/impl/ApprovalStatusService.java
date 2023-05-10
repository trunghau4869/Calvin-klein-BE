package project2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project2.model.ApprovalStatus;
import project2.repository.IApprovalStatusRepository;
import project2.service.IApprovalStatusService;

import java.util.List;

@Service
public class ApprovalStatusService implements IApprovalStatusService {
    @Autowired
    private IApprovalStatusRepository iApprovalStatusRepository;

    @Override
    public List<ApprovalStatus> findAllBy() {
        return iApprovalStatusRepository.findAll();
    }

    public ApprovalStatus getApprovalStatusById(Long id) {
        return iApprovalStatusRepository.findById(id).orElse(null);
    }

    @Override
    public List<ApprovalStatus> findByAll() {
        return iApprovalStatusRepository.findAll();
    }

}
