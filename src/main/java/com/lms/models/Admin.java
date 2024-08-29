package com.lms.models;

import java.util.List;

public class Admin extends User {

    // Constructor
    public Admin(String fullname, String email, String password, String branch, Role role) {
        super(fullname, email, password, branch, role);
    }

    // Method to create a new user
    public User createUser(String fullname, String email, String password, String branch, Role role) {
        return new User(fullname, email, password, branch, role);
    }

    // Method to create a new University Student
    public UniversityStudent createUniversityStudent(String regdNo, String fullname, String email, String password, String branch, Role role) {
        return new UniversityStudent(regdNo, fullname, email, password, branch, role);
    }

    // Method to create a new Individual Student
    public IndividualStudent createIndividualStudent(String username, String fullname, String email, String password, String branch, Role role) {
        return new IndividualStudent(username, fullname, email, password, branch, role);
    }

    // Method to create a new Faculty
    public Faculty createFaculty(String fullname, String email, String password, String branch, Role role, LocalDate joiningDate, String designation) {
        return new Faculty(fullname, email, password, branch, role, joiningDate, designation);
    }

    // Method to list all users (could be a list fetched from a database in a real application)
    public void listAllUsers(List<User> users) {
        for (User user : users) {
            System.out.println(user.getFullname() + " - " + user.getEmail() + " - " + user.getRole());
        }
    }

    // Method to delete a user
    public void deleteUser(User user, List<User> users) {
        users.remove(user);
        System.out.println("User " + user.getFullname() + " has been removed.");
    }

    // Method to update user details
    public void updateUser(User user, String fullname, String email, String password, String branch, Role role) {
        user.setFullname(fullname);
        user.setEmail(email);
        user.setPassword(password);
        user.setBranch(branch);
        user.setRole(role);
        user.setUpdatedAt(java.time.LocalDateTime.now());
    }
}