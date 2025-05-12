package com.example.complaint.service;

import com.example.complaint.entity.Complaint;
import com.example.complaint.repository.ComplaintRepository;
import com.example.complaint.exception.ComplaintNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository repository;

    public Complaint addComplaint(Complaint complaint) {
        complaint.setStatus("Open");  // Default status
        return repository.save(complaint);
    }

    public List<Complaint> getAllComplaints() {
        return repository.findAll();
    }

    public Complaint updateStatus(Long id, String status) {
        Optional<Complaint> complaintOpt = repository.findById(id);
        if (complaintOpt.isPresent()) {
            Complaint complaint = complaintOpt.get();
            complaint.setStatus(status);
            return repository.save(complaint);
        } else {
            throw new ComplaintNotFoundException("Complaint not found with id: " + id);
        }
    }

    public void deleteComplaint(Long id) {
        Optional<Complaint> complaintOpt = repository.findById(id);
        if (complaintOpt.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new ComplaintNotFoundException("Complaint not found with id: " + id);
        }
    }
}
