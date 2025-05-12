package com.example.complaint.controller;

import com.example.complaint.entity.Complaint;
import com.example.complaint.exception.ComplaintNotFoundException;
import com.example.complaint.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/complaints")
@CrossOrigin(origins = "*")  // Allow cross-origin requests from any source (use more restrictive CORS policy in production)
public class ComplaintController {

    @Autowired
    private ComplaintService service;

    // Add a new complaint
    @PostMapping("/add")
    public ResponseEntity<Complaint> addComplaint(@RequestBody Complaint complaint) {
        Complaint createdComplaint = service.addComplaint(complaint);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComplaint);  // Status 201 for resource created
    }

    // Get all complaints
    @GetMapping("/all")
    public ResponseEntity<List<Complaint>> getAllComplaints() {
        List<Complaint> complaints = service.getAllComplaints();
        return ResponseEntity.ok(complaints);  // Status 200 OK
    }

    // Update complaint status by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<Complaint> updateComplaintStatus(@PathVariable Long id, @RequestBody String status) {
        Complaint updatedComplaint = service.updateStatus(id, status);
        return ResponseEntity.ok(updatedComplaint);  // Status 200 OK
    }

    // Delete a complaint by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteComplaint(@PathVariable Long id) {
        service.deleteComplaint(id);
        return ResponseEntity.noContent().build();  // Status 204 No Content (no response body)
    }

    // Global exception handler for handling complaints not found
    @ExceptionHandler(ComplaintNotFoundException.class)
    public ResponseEntity<String> handleComplaintNotFound(ComplaintNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);  // Status 404 Not Found
    }
}
