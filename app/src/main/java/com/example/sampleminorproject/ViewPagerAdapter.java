package com.example.sampleminorproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private List<Integer> imageList;

    public ViewPagerAdapter(Context context, List<Integer> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.image_item, container, false);
        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setImageResource(imageList.get(position));

        // Set an OnClickListener on the ImageView
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (position) {
                    case 0:
                        intent = new Intent(context, ParameterInput.class);
                        break;
                    case 1:
                        intent = new Intent(context, QuizActivity.class);
                        break;
                    case 2:
                        intent = new Intent(context, AptitudeTestActivity.class);
                        break;
                    case 3:
                        intent = new Intent(context, CompanyActivity.class);
                        break;
                    case 4:
                        intent = new Intent(context, AiAssistantActivity.class);
                        break;
                    default:
                        return; // Do nothing for any other case
                }
                context.startActivity(intent);
            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
