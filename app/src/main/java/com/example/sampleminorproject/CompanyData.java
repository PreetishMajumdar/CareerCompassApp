package com.example.sampleminorproject;

import java.util.ArrayList;

public class CompanyData {

    // Method to return a list of companies with their required match scores
    public static ArrayList<Company> getCompanyList() {
        ArrayList<Company> companies = new ArrayList<>();

        // Companies with a strong emphasis on academics and competitive exams
        companies.add(new Company("Google", 92.0));
        companies.add(new Company("Microsoft", 90.0));
        companies.add(new Company("Amazon", 88.0));
        companies.add(new Company("Apple", 89.0));
        companies.add(new Company("IBM", 87.0));
        companies.add(new Company("Cisco Systems", 85.0));
        companies.add(new Company("Intel", 86.0));

        // Companies with a balance of academics, technical skills, and problem-solving
        companies.add(new Company("TCS", 82.0));
        companies.add(new Company("Infosys", 80.0));
        companies.add(new Company("Wipro", 78.0));
        companies.add(new Company("Accenture", 81.0));
        companies.add(new Company("Oracle", 80.0));
        companies.add(new Company("Texas Instruments", 83.0));
        companies.add(new Company("Qualcomm", 82.0));

        // Companies with a focus on specific skills and cultural fit
        companies.add(new Company("Adobe", 77.0));  // Design & Creativity
        companies.add(new Company("Goldman Sachs", 78.0));  // Finance & Communication
        companies.add(new Company("Deloitte", 76.0));  // Consulting & Teamwork
        companies.add(new Company("EY", 75.0));  // Consulting & Communication
        companies.add(new Company("KPMG", 74.0));  // Consulting & Teamwork

        return companies;
    }
}