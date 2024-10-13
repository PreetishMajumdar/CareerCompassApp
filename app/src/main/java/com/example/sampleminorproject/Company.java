package com.example.sampleminorproject;

public class Company {

    private String name;
    private double requiredScore;
    private double scoreDifference;
    private int logoResourceId;  // New field for logo resource

    // Constructor
    public Company(String name, double requiredScore, int logoResourceId) {
        this.name = name;
        this.requiredScore = requiredScore;
        this.logoResourceId = logoResourceId;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public double getRequiredScore() {
        return requiredScore;
    }

    public void setScoreDifference(double scoreDifference) {
        this.scoreDifference = scoreDifference;
    }

    public double getScoreDifference() {
        return scoreDifference;
    }

    public int getLogoResourceId() {
        return logoResourceId;
    }
}
