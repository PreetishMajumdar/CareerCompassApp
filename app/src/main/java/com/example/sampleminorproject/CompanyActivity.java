package com.example.sampleminorproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CompanyActivity extends AppCompatActivity {

    // Define UI elements
    private TextView[] companyTextViews = new TextView[10];
    private ImageView[] companyImageViews = new ImageView[10]; // To hold ImageViews for logos
    private TextView cgpaInput, iqScoreInput, profileScoreInput, aptitudeTestScoreInput;
    private Button shortlistButton;

    // Define threshold for matching
    private static final double THRESHOLD = 30.0;

    // List to hold companies
    private ArrayList<Company> companies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        // Initialize input fields
        cgpaInput = findViewById(R.id.cgpainput);
        iqScoreInput = findViewById(R.id.iqinput);
        profileScoreInput = findViewById(R.id.profilescoreinput);
        aptitudeTestScoreInput = findViewById(R.id.aptitudeTestScore);

        // Initialize TextViews for each company
        companyTextViews[0] = findViewById(R.id.companytitle1);
        companyTextViews[1] = findViewById(R.id.companytitle2);
        companyTextViews[2] = findViewById(R.id.companytitle3);
        companyTextViews[3] = findViewById(R.id.companytitle4);
        companyTextViews[4] = findViewById(R.id.companytitle5);
        companyTextViews[5] = findViewById(R.id.companytitle6);
        companyTextViews[6] = findViewById(R.id.companytitle7);
        companyTextViews[7] = findViewById(R.id.companytitle8);
        companyTextViews[8] = findViewById(R.id.companytitle9);
        companyTextViews[9] = findViewById(R.id.companytitle10);

        // Initialize ImageViews for company logos
        companyImageViews[0] = findViewById(R.id.companylogo1);
        companyImageViews[1] = findViewById(R.id.companylogo2);
        companyImageViews[2] = findViewById(R.id.companylogo3);
        companyImageViews[3] = findViewById(R.id.companylogo4);
        companyImageViews[4] = findViewById(R.id.companylogo5);
        companyImageViews[5] = findViewById(R.id.companylogo6);
        companyImageViews[6] = findViewById(R.id.companylogo7);
        companyImageViews[7] = findViewById(R.id.companylogo8);
        companyImageViews[8] = findViewById(R.id.companylogo9);
        companyImageViews[9] = findViewById(R.id.companylogo10);

        // Initialize Button
        shortlistButton = findViewById(R.id.shortlist_button);

        // Fetch the list of companies from CompanyData
        companies = CompanyData.getCompanyList();

        // Set up an OnClickListener for the button
        shortlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Calculate the match score based on user input
                double matchScore = calculateMatchScore();

                // Check if match score is too low (e.g., less than or equal to 0)
                if (matchScore <= 0) {
                    // If match score is 0 or negative, clear all TextViews and ImageViews
                    clearCompanyViews();
                    return; // Exit early
                }

                // Sort and shortlist companies based on match score
                ArrayList<Company> shortlistedCompanies = shortlistCompanies(matchScore);

                // Display the shortlisted companies in their respective TextViews and ImageViews
                displayCompanies(shortlistedCompanies);
            }
        });
    }

    // Function to clear the TextViews and ImageViews
    private void clearCompanyViews() {
        for (int i = 0; i < companyTextViews.length; i++) {
            companyTextViews[i].setText(""); // Clear the TextViews
            companyImageViews[i].setImageResource(0); // Clear the ImageViews
        }
    }

    // Function to calculate match score from the input fields
    private double calculateMatchScore() {
        // Parse input values
        double cgpa = Double.parseDouble(cgpaInput.getText().toString());
        double iqScore = Double.parseDouble(iqScoreInput.getText().toString());
        double profileScore = Double.parseDouble(profileScoreInput.getText().toString());
        double aptitudeTestScore = Double.parseDouble(aptitudeTestScoreInput.getText().toString());

        // Define max values for each parameter (assumed here)
        double maxCgpa = 10.0;  // Example max value for CGPA
        double maxIqScore = 200.0;  // Example max IQ Score
        double maxProfileScore = 100.0;  // Example max Profile Score
        double maxAptitudeTestScore = 100.0;  // Example max Aptitude Test Score

        // Scale each parameter based on its maximum value
        double scaledCgpa = (cgpa / maxCgpa) * 100;
        double scaledIqScore = (iqScore / maxIqScore) * 100;
        double scaledProfileScore = (profileScore / maxProfileScore) * 100;
        double scaledAptitudeTestScore = (aptitudeTestScore / maxAptitudeTestScore) * 100;

        // Calculate the match score by averaging the scaled values
        return (scaledCgpa + scaledIqScore + scaledProfileScore + scaledAptitudeTestScore) / 4;
    }

    // Function to shortlist companies based on the match score
    private ArrayList<Company> shortlistCompanies(double matchScore) {
        ArrayList<Company> shortlisted = new ArrayList<>();

        // Calculate the difference between the match score and each company's required score
        for (Company company : companies) {
            double scoreDifference = Math.abs(company.getRequiredScore() - matchScore);
            company.setScoreDifference(scoreDifference);

            // Only shortlist companies where the score difference is within the threshold
            if (scoreDifference <= THRESHOLD) {
                shortlisted.add(company);
            }
        }

        // Sort companies based on score difference (ascending order)
        Collections.sort(shortlisted, new Comparator<Company>() {
            @Override
            public int compare(Company c1, Company c2) {
                return Double.compare(c1.getScoreDifference(), c2.getScoreDifference());
            }
        });

        // If fewer than 10 companies pass the threshold, fill with closest matches
        if (shortlisted.size() < 10) {
            for (Company company : companies) {
                if (!shortlisted.contains(company) && shortlisted.size() < 10) {
                    shortlisted.add(company);
                }
            }
        }

        return shortlisted;
    }

    // Function to display shortlisted companies in TextViews and ImageViews
    private void displayCompanies(ArrayList<Company> shortlistedCompanies) {
        for (int i = 0; i < companyTextViews.length; i++) {
            if (i < shortlistedCompanies.size()) {
                Company company = shortlistedCompanies.get(i);

                // Set the company name in the TextView
                companyTextViews[i].setText(company.getName());

                // Set the company logo in the ImageView
                companyImageViews[i].setImageResource(company.getLogoResourceId());
            } else {
                // Clear the TextView and ImageView if no more companies
                companyTextViews[i].setText("");
                companyImageViews[i].setImageResource(0);
            }
        }
    }
}
