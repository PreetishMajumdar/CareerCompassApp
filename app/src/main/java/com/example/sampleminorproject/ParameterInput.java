package com.example.sampleminorproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ParameterInput extends AppCompatActivity {

    Button taketestbutton, aptitudetestbutton;
    ImageView backbutton, toplogo;
    TextView topBackground, toptext, predict;
    ScrollView parameterLinearLayout;

    double maxCgpa = 10.0;
    double maxIq = 180.0;
    double maxProfileScore = 100.0;
    double maxAptitudeTest = 100.0;


    EditText cgpaInput, iqInput, profileScoreInput, proficientLanguagesInput, extraActivitiesInput, internshipExperienceInput, projectsInput, softSkillsInput, certificationsInput, interviewsInput, githubRepositoriesInput, workshopParticipationInput, socialWorkInput, aptitudeTestScoreInput;
    String url = "https://careercompassplacement-9ca0815d4bc2.herokuapp.com/predict";  // Ensure correct endpoint

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parameter_input);

        // Initialize inputs (IDs should match XML file)
        cgpaInput = findViewById(R.id.cgpainput);
        iqInput = findViewById(R.id.iqinput);
        profileScoreInput = findViewById(R.id.profilescoreinput);
        proficientLanguagesInput = findViewById(R.id.proficientLanguages);
        extraActivitiesInput = findViewById(R.id.extraActivities);
        internshipExperienceInput = findViewById(R.id.internshipExperience);
        projectsInput = findViewById(R.id.projects);
        softSkillsInput = findViewById(R.id.softSkills);
        certificationsInput = findViewById(R.id.certifications);
        interviewsInput = findViewById(R.id.interviews);
        githubRepositoriesInput = findViewById(R.id.githubRepositories);
        workshopParticipationInput = findViewById(R.id.workshopParticipation);
        socialWorkInput = findViewById(R.id.socialWork);
        aptitudeTestScoreInput = findViewById(R.id.aptitudeTestScore);

        // Predict button functionality
        predict = findViewById(R.id.predictbutton);
        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePredictionRequest();
            }
        });

        // Test button functionality
        taketestbutton = findViewById(R.id.taketestbutton);
        taketestbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParameterInput.this, QuizActivity.class);
                startActivity(intent);
            }
        });

        aptitudetestbutton = findViewById(R.id.aptitudetestbutton);
        aptitudetestbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParameterInput.this, AptitudeTestActivity.class);
                startActivity(intent);
            }
        });

        // Back button functionality
        backbutton = findViewById(R.id.backarrow);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParameterInput.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        // UI Elements with animations
        topBackground = findViewById(R.id.topbackground);
        toptext = findViewById(R.id.toptext);
        predict = findViewById(R.id.predictbutton);
        parameterLinearLayout = findViewById(R.id.parameterScrollView);
        toplogo = findViewById(R.id.toplogo);

        // Animations
        Animation topToDown = AnimationUtils.loadAnimation(this, R.anim.toptodown);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        Animation downToTop = AnimationUtils.loadAnimation(this, R.anim.downtotop);

        topBackground.startAnimation(topToDown);
        toptext.startAnimation(topToDown);
        toplogo.startAnimation(topToDown);
        backbutton.startAnimation(topToDown);
        predict.startAnimation(downToTop);
        aptitudetestbutton.startAnimation(downToTop);
        taketestbutton.startAnimation(downToTop);
        parameterLinearLayout.startAnimation(fadeIn);
    }

    private double calculateMatchScore() {
        // Parse input values
        double cgpa = Double.parseDouble(cgpaInput.getText().toString());
        double iq = Double.parseDouble(iqInput.getText().toString());
        double profileScore = Double.parseDouble(profileScoreInput.getText().toString());
        double aptitudeTest = Double.parseDouble(aptitudeTestScoreInput.getText().toString());

        // Scale each parameter based on its maximum value
        double scaledCgpa = (cgpa / maxCgpa) * 100;
        double scaledIq = (iq / maxIq) * 100;
        double scaledProfileScore = (profileScore / maxProfileScore) * 100;
        double scaledAptitudeTest = (aptitudeTest / maxAptitudeTest) * 100;

        // Calculate the match score by averaging the scaled values
        double matchScore = (scaledCgpa + scaledIq + scaledProfileScore + scaledAptitudeTest) / 4;

        return matchScore;
    }


    // Function to handle the network request
    private void makePredictionRequest() {
        // Validate inputs before making a network request
        if (cgpaInput.getText().toString().isEmpty() || iqInput.getText().toString().isEmpty() || profileScoreInput.getText().toString().isEmpty() || aptitudeTestScoreInput.getText().toString().isEmpty()) {
            showAlertDialog("Error", "Please fill in all fields");
            return;
        }

        // Calculate the match score
        double matchScore = calculateMatchScore();

        // Create the request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String data = jsonObject.getString("placement");
                            String placementResult;
                            if (data.equals("1")) {
                                placementResult = "You will be placed.";
                            } else {
                                placementResult = "You won't be placed.";
                            }

                            // Show the match score and placement prediction in the alert dialog
                            String message = "Placement Prediction: " + placementResult + "\nMatch Score: " + String.format("%.2f", matchScore) + "%";
                            showAlertDialog("Prediction Result", message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            showAlertDialog("Error", "JSON Parsing Error");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();  // Print full stack trace for detailed debugging

                        // Check if error has a more specific cause
                        String errorMessage = "Unknown error";
                        if (error.networkResponse != null) {
                            errorMessage = "Error code: " + error.networkResponse.statusCode;
                            try {
                                // Extract the server error message from the response body, if available
                                String errorResponse = new String(error.networkResponse.data, "UTF-8");
                                Log.e("VolleyError", "Response: " + errorResponse);
                                errorMessage += " - " + errorResponse;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (error.getMessage() != null) {
                            errorMessage = error.getMessage();
                        }

                        // Show the full error message in the alert dialog
                        showAlertDialog("Error", "Error: " + errorMessage);
                        Log.e("VolleyError", "Error details: " + errorMessage);
                    }

                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("cgpa", cgpaInput.getText().toString());
                params.put("iq", iqInput.getText().toString());
                params.put("profile_score", profileScoreInput.getText().toString());
                params.put("proficient_lang", proficientLanguagesInput.getText().toString());  // Updated
                params.put("extracurricular", extraActivitiesInput.getText().toString());  // Updated
                params.put("internship", internshipExperienceInput.getText().toString());  // Updated
                params.put("projects", projectsInput.getText().toString());
                params.put("softskills", softSkillsInput.getText().toString());  // Updated
                params.put("certifications", certificationsInput.getText().toString());
                params.put("interviews", interviewsInput.getText().toString());
                params.put("github_repositories", githubRepositoriesInput.getText().toString());
                params.put("workshop", workshopParticipationInput.getText().toString());  // Updated
                params.put("socialwork", socialWorkInput.getText().toString());  // Updated
                params.put("aptitudetest", aptitudeTestScoreInput.getText().toString());  // Updated
                return params;
            }
        };

        // Add the request to the Volley queue
        RequestQueue queue = Volley.newRequestQueue(ParameterInput.this);
        queue.add(stringRequest);
    }

    // Function to show alert dialog
    private void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ParameterInput.this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void onBackPressed() {
        // Minimize app when back is pressed or show a logout toast
        Toast.makeText(ParameterInput.this, "Logout?", Toast.LENGTH_SHORT).show();
    }
}
