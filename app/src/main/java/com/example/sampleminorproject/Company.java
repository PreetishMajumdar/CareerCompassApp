package com.example.sampleminorproject;

public class Company {

    private String name;
    private double requiredScore;
    private double scoreDifference;

    // Constructor
    public Company(String name, double requiredScore) {
        this.name = name;
        this.requiredScore = requiredScore;
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
}