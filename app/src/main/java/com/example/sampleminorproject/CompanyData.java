package com.example.sampleminorproject;

import java.util.ArrayList;

public class CompanyData {

    // Method to return a list of companies with their required match scores and logos
    public static ArrayList<Company> getCompanyList() {
        ArrayList<Company> companies = new ArrayList<>();

        // Add companies with their required score and logo resource ID
        companies.add(new Company("Google", 92.0, R.drawable.google_logo));
        companies.add(new Company("Microsoft", 90.0, R.drawable.microsoft_logo));
        companies.add(new Company("Amazon", 88.0, R.drawable.amazon_logo));
        companies.add(new Company("Apple", 89.0, R.drawable.apple_logo));
        companies.add(new Company("IBM", 87.0, R.drawable.ibm_logo));
        companies.add(new Company("Cisco Systems", 85.0, R.drawable.cisco_logo));
        companies.add(new Company("Intel", 86.0, R.drawable.intel_logo));

        companies.add(new Company("TCS", 82.0, R.drawable.tcs_logo));
        companies.add(new Company("Infosys", 80.0, R.drawable.infosys_logo));
        companies.add(new Company("Wipro", 78.0, R.drawable.wipro_logo));
        companies.add(new Company("Accenture", 81.0, R.drawable.accenture_logo));
        companies.add(new Company("Oracle", 80.0, R.drawable.oracle_logo));
        companies.add(new Company("Texas Instruments", 83.0, R.drawable.ti_logo));
        companies.add(new Company("Qualcomm", 82.0, R.drawable.qualcomm_logo));

        companies.add(new Company("Adobe", 77.0, R.drawable.adobe_logo));
        companies.add(new Company("Goldman Sachs", 78.0, R.drawable.gs_logo));
        companies.add(new Company("Deloitte", 76.0, R.drawable.deloitte_logo));
        companies.add(new Company("EY", 75.0, R.drawable.ey_logo));
        companies.add(new Company("KPMG", 74.0, R.drawable.kpmg_logo));

        return companies;
    }
}
