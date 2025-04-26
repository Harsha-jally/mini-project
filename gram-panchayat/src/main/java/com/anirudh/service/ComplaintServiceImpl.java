package com.anirudh.service;


import com.anirudh.dto.ComplaintDTO;
import com.anirudh.entity.Complaint;
import com.anirudh.entity.ComplaintCategory;
import com.anirudh.entity.ComplaintStatus;
import com.anirudh.entity.Report;
import com.anirudh.exception.CustomException;
import com.anirudh.repository.ComplaintRepository;
import com.anirudh.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class ComplaintServiceImpl implements ComplaintService {

    @Autowired
    private ComplaintRepository repository;

    @Autowired
    private ReportRepository reportRepository;

    //Priority Map
    private static final Map<ComplaintCategory, Integer> PRIORITY_MAP = Map.of(
            ComplaintCategory.WATER_HOSPITAL, 1,
            ComplaintCategory.RATION, 2,
            ComplaintCategory.TRANSPORT, 3,
            ComplaintCategory.ELECTRICITY, 4,
            ComplaintCategory.ROAD, 5,
            ComplaintCategory.LAND, 6,
            ComplaintCategory.OTHERS, 7
    );


    @Override
    public Complaint createComplaint(ComplaintDTO dto) {
        Complaint complaint = Complaint.builder()
                .name(dto.getName())
                .phoneNumber(dto.getPhoneNumber())
                .aadhaarNumber(dto.getAadhaarNumber())
                .address(dto.getAddress())
                .category(dto.getCategory())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return repository.save(complaint);
    }

    @Override
    public List<Complaint> getAllComplaints() {
        List<Complaint> complaints = repository.findAll();
        complaints.sort(Comparator.comparingInt(c -> PRIORITY_MAP.get(c.getCategory())));
        return complaints;
    }


    @Override
    public Complaint getComplaintById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CustomException("Complaint not found with ID: " + id));
    }

    @Override
    public Complaint updateComplaint(Long id, ComplaintDTO dto) {
        Complaint existing = getComplaintById(id);
        existing.setName(dto.getName());
        existing.setPhoneNumber(dto.getPhoneNumber());
        existing.setAadhaarNumber(dto.getAadhaarNumber());
        existing.setAddress(dto.getAddress());
        existing.setCategory(dto.getCategory());
        existing.setDescription(dto.getDescription());
        existing.setStatus(dto.getStatus());
        existing.setUpdatedAt(LocalDateTime.now());
        return repository.save(existing);
    }

    @Override
    public void deleteComplaint(Long id) {
        Complaint existing = getComplaintById(id);
        repository.delete(existing);
    }

    // Admin functionality to resolve a complaint
    @Override
    public Complaint resolveComplaint(Long id) {
        Complaint complaint = getComplaintById(id);
        complaint.setStatus(ComplaintStatus.RESOLVED);
        complaint.setUpdatedAt(LocalDateTime.now());
        Complaint updated = repository.save(complaint);

        // Log the report
        Report report = Report.builder()
                .complaintId(updated.getId())
                .complaintCategory(updated.getCategory().name())
                .actionTaken("Marked as RESOLVED")
                .timestamp(LocalDateTime.now())
                .build();

        reportRepository.save(report);

        return updated;
    }

    // Admin functionality to generate a report of all complaints
    @Override
    public List<Complaint> generateComplaintReport() {
        return repository.findAll();
    }

    @Override
    public List<Complaint> getComplaintsByStatus(ComplaintStatus status) {
        List<Complaint> complaints = repository.findByStatus(status);
        complaints.sort(Comparator.comparingInt(c -> PRIORITY_MAP.get(c.getCategory())));
        return complaints;
    }

}

