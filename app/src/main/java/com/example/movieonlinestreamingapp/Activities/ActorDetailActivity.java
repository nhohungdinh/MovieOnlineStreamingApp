package com.example.movieonlinestreamingapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.movieonlinestreamingapp.Client.RetrofitClient;
import com.example.movieonlinestreamingapp.Interfaces.RetrofitService;
import com.example.movieonlinestreamingapp.Models.Actor;
import com.example.movieonlinestreamingapp.R;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActorDetailActivity extends AppCompatActivity {

    private KenBurnsView actorDetailProfileImageView;
    private LinearLayoutCompat actorDetailAlsoKnowAsLayout;
    private LinearLayoutCompat actorDetailDateOfBitrthLayout;
    private LinearLayoutCompat actorDetailBiographyLayout;

    private AppCompatTextView actorDetailAlsoKnowAs;
    private AppCompatTextView actorDetailDateOfBitrth;
    private AppCompatTextView actorDetailBiography;
    private AppCompatTextView actorDetailName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor_detail);

        Intent intent = getIntent();

        // initiate the retrofit service
        RetrofitService retrofitService = RetrofitClient.getClient().create(RetrofitService.class);

        actorDetailProfileImageView = findViewById(R.id.actor_detail_profile_image_view);
        actorDetailAlsoKnowAsLayout = findViewById(R.id.actor_detail_also_know_as_layout);
        actorDetailDateOfBitrthLayout = findViewById(R.id.actor_detail_date_of_birth_layout);
        actorDetailBiographyLayout = findViewById(R.id.actor_detail_biography_layout);
        actorDetailAlsoKnowAs = findViewById(R.id.actor_detail_gender);
        actorDetailDateOfBitrth = findViewById(R.id.actor_detail_date_of_birth);
        actorDetailBiography = findViewById(R.id.actor_detail_biography);
        actorDetailName = findViewById(R.id.actor_detail_name);
        if (intent != null && intent.getExtras() != null){
            // get id from previous activity
            if (intent.getExtras().getString("id") != null){
                int id = Integer.parseInt(intent.getExtras().getString("id"));
                Call<Actor> actorCall = retrofitService.getActorById(id);
                actorCall.enqueue(new Callback<Actor>() {
                    @Override
                    public void onResponse(@NonNull Call<Actor> call,@NonNull Response<Actor> response) {
                        Actor actorDetailResponse = response.body();
                        if (actorDetailResponse != null){
                            prepareActor(actorDetailResponse);
                        }
                        else {
                            Toast.makeText(ActorDetailActivity.this, "Any details not found",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Actor> call,@NonNull Throwable t) {
                        Toast.makeText(ActorDetailActivity.this, "Any details not found",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    private void prepareActor(Actor actorDetailResponse) {
        String gender = actorDetailResponse.getGender();
        String name = actorDetailResponse.getName();
        String birthOfdate = actorDetailResponse.getBirthOfDate();
        String profilePathUrl = actorDetailResponse.getProfilePathUrl();
        String biography = actorDetailResponse.getBiography();

        Picasso.get().load(profilePathUrl).into(actorDetailProfileImageView);
        if (name != null){
            if (name.length() > 0){
                actorDetailName.setText(name);
                actorDetailName.setVisibility(View.VISIBLE);
            }
            else {
                actorDetailName.setVisibility(View.GONE);
            }
        }else {
            actorDetailName.setVisibility(View.GONE);
        }

        if (gender != null){
            if (gender.length() > 0){
                actorDetailAlsoKnowAs.setText(gender);
                actorDetailAlsoKnowAsLayout.setVisibility(View.VISIBLE);
            }
            else {
                actorDetailAlsoKnowAsLayout.setVisibility(View.GONE);
            }
        }else {
            actorDetailAlsoKnowAsLayout.setVisibility(View.GONE);
        }

        if (birthOfdate != null){
            if (birthOfdate.length() > 0){
                actorDetailDateOfBitrth.setText(birthOfdate);
                actorDetailDateOfBitrthLayout.setVisibility(View.VISIBLE);
            }
            else {
                actorDetailDateOfBitrthLayout.setVisibility(View.GONE);
            }
        }else {
            actorDetailDateOfBitrthLayout.setVisibility(View.GONE);
        }

        if (biography != null){
            if (biography.length() > 0){
                actorDetailBiography.setText(biography);
                actorDetailBiographyLayout.setVisibility(View.VISIBLE);
            }
            else {
                actorDetailBiographyLayout.setVisibility(View.GONE);
            }
        }else {
            actorDetailBiographyLayout.setVisibility(View.GONE);
        }
    }

    // set animation for back to main activity


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}