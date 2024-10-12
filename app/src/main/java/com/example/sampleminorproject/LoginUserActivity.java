package com.example.sampleminorproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginUserActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button login;
    TextView topBackground;
    TextView toptext;
    ImageView toplogo;
    ImageView google_logo;
    ImageView linkedin_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        username=findViewById(R.id.usernamefield);
        password=findViewById(R.id.passwordfield);
        login=findViewById(R.id.loginbutton);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredUsername = username.getText().toString();
                String enteredPassword = password.getText().toString();

                if (enteredUsername.equals("p") && enteredPassword.equals("p")) {
                    // Credentials are correct, open the ParameterInput activity
                    Intent intent = new Intent(LoginUserActivity.this, HomeActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                } else {
                    // Credentials are incorrect, show a Toast message
                    Toast.makeText(LoginUserActivity.this, "Incorrect Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
        topBackground = findViewById(R.id.topbackground);
        toptext = findViewById(R.id.toptext);
        toplogo = findViewById(R.id.toplogo);
        google_logo = findViewById(R.id.google_logo);
        linkedin_logo = findViewById(R.id.linkedin_logo);

        Animation topToDown = AnimationUtils.loadAnimation(this, R.anim.toptodown);
        Animation FadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        Animation DownToTop = AnimationUtils.loadAnimation(this, R.anim.downtotop);

        google_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the browser and load Google.com
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
                // Start the activity with the intent to open the URL
                startActivity(browserIntent);
            }
        });

        linkedin_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the browser and load Google.com
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com"));
                // Start the activity with the intent to open the URL
                startActivity(browserIntent);
            }
        });

        // Apply the animation to the TextView
        topBackground.startAnimation(topToDown);
        toptext.startAnimation(topToDown);
        toplogo.startAnimation(topToDown);
        login.startAnimation(FadeIn);
        username.startAnimation(FadeIn);
        password.startAnimation(FadeIn);
        google_logo.startAnimation(DownToTop);
        linkedin_logo.startAnimation(DownToTop);




    }
    @Override
    public void onBackPressed() {
        // Option 1: Do nothing
        // Do nothing to prevent going back

        // Option 2: Minimize the app instead of navigating back
        moveTaskToBack(true);
    }

}