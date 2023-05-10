package project2.service;

import project2.model.ApprovalStatus;

import java.util.List;

public interface IApprovalStatusService {

    ApprovalStatus getApprovalStatusById(Long id);
    List<ApprovalStatus> findByAll();

    List<ApprovalStatus> findAllBy();


}
