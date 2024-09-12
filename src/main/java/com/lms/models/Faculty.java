package com.lms.models;

import java.time.LocalDate;

public class Faculty extends User {
    private LocalDate joiningDate;
    private String designation;

    // Constructor         
    public Faculty(String fullname, String email, String password, String branch, Role role, LocalDate joiningDate, String designation) {
        super(fullname, email, password, branch, role);
        this.setJoiningDate(joiningDate);
        this.setDesignation(designation);
    }

    // Getter and Setter for joiningDate
    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        if (joiningDate == null || joiningDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Joining date cannot be in the future or empty");
        }
        this.joiningDate = joiningDate;
    }

    // Getter and Setter for designation
    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        if (designation == null || designation.trim().isEmpty()) {
            throw new IllegalArgumentException("Designation cannot be empty");
        }
        this.designation = designation;
    }
}
