package com.anirudh.service;

import com.anirudh.dto.ComplaintDTO;
import com.anirudh.entity.Complaint;
import com.anirudh.entity.ComplaintStatus;

import java.util.List;

public interface ComplaintService {
    Complaint createComplaint(ComplaintDTO dto);
    List<Complaint> getAllComplaints();
    Complaint getComplaintById(Long id);
    Complaint updateComplaint(Long id, ComplaintDTO dto);
    void deleteComplaint(Long id);


    Complaint resolveComplaint(Long id); // Method to mark complaint as resolved
    List<Complaint> generateComplaintReport(); // Method to generate a report of all complaints

    List<Complaint> getComplaintsByStatus(ComplaintStatus status);

}