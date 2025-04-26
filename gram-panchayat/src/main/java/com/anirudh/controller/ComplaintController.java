package com.anirudh.controller;

import com.anirudh.dto.ComplaintDTO;
import com.anirudh.entity.Complaint;
import com.anirudh.entity.ComplaintStatus;
import com.anirudh.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
Step 1:  Comment out @RestController and use @Controller
Step 2: Change the @GetMapping functions return type to String
 */
//@RestController
//@RequestMapping("/api/complaints")


@Controller
public class ComplaintController {

    @Autowired
    private ComplaintService service;


    @PostMapping("/register/complaints")
    public Complaint registerComplaint(@RequestBody ComplaintDTO dto) {
        return service.createComplaint(dto);
    }

    @GetMapping("/complaints/all")
    public List<Complaint> getAllComplaints() {
        return service.getAllComplaints();
    }

    @GetMapping("/complaint/{id}")
    public Complaint getComplaint(@PathVariable Long id) {
        return service.getComplaintById(id);
    }

    @PutMapping("/complaint/{id}")
    public Complaint updateComplaint(@PathVariable Long id, @RequestBody ComplaintDTO dto) {
        return service.updateComplaint(id, dto);
    }

    @DeleteMapping("/complaint/{id}")
    public String deleteComplaint(@PathVariable Long id) {
        service.deleteComplaint(id);
        return "Complaint with ID " + id + " deleted successfully.";
    }

    // Admin endpoint to mark a complaint as resolved
    @PutMapping("/complaint/resolve/{id}")
    public Complaint resolveComplaint(@PathVariable Long id) {
        return service.resolveComplaint(id);
    }


    @GetMapping("/complaint/report")
    public List<Complaint> generateComplaintReport() {
        return service.generateComplaintReport();
    }

    @GetMapping("/complaint/status/{status}")
    public ResponseEntity<List<Complaint>> getComplaintsByStatus(@PathVariable ComplaintStatus status) {
        List<Complaint> complaints = service.getComplaintsByStatus(status);
        return ResponseEntity.ok(complaints);
    }

}

