package com.example.sampleminorproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class AboutusActivity extends AppCompatActivity {

    ImageView backarrow, toplogo;
    TextView topbackground;
    ScrollView aboutpreetishscroll, abouthemanthscroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);


        backarrow=findViewById(R.id.backarrow);
        toplogo=findViewById(R.id.toplogo);
        topbackground=findViewById(R.id.topbackground);
        aboutpreetishscroll=findViewById(R.id.aboutpreetishscroll);
        abouthemanthscroll=findViewById(R.id.abouthemanthscroll);

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutusActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });


        Animation topToDown = AnimationUtils.loadAnimation(this, R.anim.toptodown);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        Animation downToTop = AnimationUtils.loadAnimation(this, R.anim.downtotop);
        Animation lefttoright = AnimationUtils.loadAnimation(this, R.anim.lefttoright);
        Animation righttoleft = AnimationUtils.loadAnimation(this, R.anim.righttoleft);



        toplogo.startAnimation(topToDown);
        topbackground.startAnimation(topToDown);
        backarrow.startAnimation(topToDown);
        aboutpreetishscroll.startAnimation(lefttoright);
        abouthemanthscroll.startAnimation(righttoleft);


    }
}