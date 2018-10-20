package com.news.android.newsapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class About extends AppCompatActivity {

    TextView myTitleTextView;
    TextView myDescTextView;
    ImageView myImageView;
    private String photo;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //getting extras from main activity
         String title = getIntent().getExtras().getString("title");
         String description = getIntent().getExtras().getString("description");
         photo = getIntent().getExtras().getString("photo");

        myTitleTextView = (TextView)findViewById(R.id.naslov);
        myDescTextView = (TextView)findViewById(R.id.opis);
        myImageView = (ImageView)findViewById(R.id.imageView);

        //setting title
        myTitleTextView.setText(title);

        //setting description
        if(description.equals("null")){
            myDescTextView.setText("Description will be added soon...");
        }else { myDescTextView.setText(description); }

        //setting photo
        if((photo.equals("null")) || (photo.equals(""))        ){
            myImageView.setImageDrawable(getDrawable(R.drawable.download));
        }
        else {
            Picasso.get().load(photo).into(myImageView);
        }

        }


    }




