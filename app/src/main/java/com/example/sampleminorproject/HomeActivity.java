package com.example.sampleminorproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    TextView topBackground, home_hi;
    ImageView toplogo, logout_logo, profile_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        topBackground = findViewById(R.id.topbackground);
        toplogo = findViewById(R.id.toplogo);
        home_hi = findViewById(R.id.home_hi);
        viewPager = findViewById(R.id.viewPager);
        logout_logo = findViewById(R.id.logout_logo);
        profile_photo = findViewById(R.id.profile_photo);

        Animation topToDown = AnimationUtils.loadAnimation(this, R.anim.toptodown);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        Animation downToTop = AnimationUtils.loadAnimation(this, R.anim.downtotop);

        topBackground.startAnimation(topToDown);
        toplogo.startAnimation(topToDown);
        home_hi.startAnimation(fadeIn);
        viewPager.startAnimation(downToTop);
        logout_logo.startAnimation(topToDown);
        profile_photo.startAnimation(topToDown);


        profile_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AboutusActivity.class);
                startActivity(intent);
            }
        });

        logout_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LoginUserActivity.class);
                startActivity(intent);
            }
        });

        try {
            List<Integer> imageList = new ArrayList<>();
            imageList.add(R.drawable.wallpaper1);
            imageList.add(R.drawable.wallpaper2);
            imageList.add(R.drawable.wallpaper3);
            imageList.add(R.drawable.wallpaper4); // Add wallpaper4 here

            viewPagerAdapter = new ViewPagerAdapter(this, imageList);
            viewPager.setAdapter(viewPagerAdapter);

            // Add a page change listener if you want to respond to changes in the ViewPager
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

                @Override
                public void onPageSelected(int position) {}

                @Override
                public void onPageScrollStateChanged(int state) {}
            });
        } catch (Exception e) {
            Log.e("HomeActivity", "Error in onCreate", e);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(HomeActivity.this, LoginUserActivity.class);
        startActivity(intent);
        finish(); // Optional: Call finish if you want to remove HomeActivity from the back stack
    }
}