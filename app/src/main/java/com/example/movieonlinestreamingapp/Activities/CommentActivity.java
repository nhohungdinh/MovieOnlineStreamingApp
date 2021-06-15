package com.example.movieonlinestreamingapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.movieonlinestreamingapp.Adapters.CommentAdapter;
import com.example.movieonlinestreamingapp.Models.Comment;
import com.example.movieonlinestreamingapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity {

    ImageView movieImg, currentUserImg;
    TextView txtOverview, txtRate, txtTitle;
    EditText edtComment;
    Button addBtn;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    RecyclerView rvComment;
    String id;
    FirebaseDatabase mDatabase;
    CommentAdapter commentAdapter;
    List<Comment> commentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Intent intent = getIntent();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance();
        movieImg = findViewById(R.id.movie_img);
        currentUserImg = findViewById(R.id.currentuser_img);
        txtOverview = findViewById(R.id.movie_overview);
        txtRate = findViewById(R.id.movie_rate);
        txtTitle = findViewById(R.id.mtitle);
        edtComment = findViewById(R.id.movie_comment);
        addBtn = findViewById(R.id.movie_add_comment_btn);
        rvComment = findViewById(R.id.rv_comment);
        rvComment.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));

        if (currentUser.getPhotoUrl() != null){
            Glide.with(this).load(currentUser.getPhotoUrl()).into(currentUserImg);
        } else {
            Glide.with(this).load(R.drawable.userphoto).into(currentUserImg);
        }



        if (intent != null && intent.getExtras() != null){
            if (intent.getExtras().getString("rate") != null){
                txtRate.setText(intent.getExtras().getString("rate"));
            }
            if (intent.getExtras().getString("poster") != null){
                Glide.with(this).load(intent.getExtras().getString("poster")).into(movieImg);
            }
            if (intent.getExtras().getString("title") != null){
                txtTitle.setText(intent.getExtras().getString("title"));
            }
            if (intent.getExtras().getString("overview") != null){
                txtOverview.setText(intent.getExtras().getString("overview"));
            }
            id = intent.getExtras().getString("id1");
        }
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBtn.setVisibility(View.INVISIBLE);
                DatabaseReference commentReference = mDatabase.getReference("Comment").child(id).push();
                String commentContent = edtComment.getText().toString();
                String uid = currentUser.getUid();
                String uname = currentUser.getDisplayName();
                String uimg = currentUser.getPhotoUrl().toString();
                Comment comment = new Comment(commentContent,uid,uimg,uname);
                commentReference.setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        showMessage("Comment Added");
                        edtComment.setText("");
                        addBtn.setVisibility(View.VISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        showMessage("Fail to add comment : " + e.getMessage());
                    }
                });
            }
        });

        iniRvComment();
    }

    private void iniRvComment() {
        DatabaseReference commentRef = mDatabase.getReference("Comment").child(id);
        commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                commentList = new ArrayList<>();
                for (DataSnapshot snap : snapshot.getChildren()){
                    Comment comment = snap.getValue(Comment.class);
                    commentList.add(comment);
                }
                commentAdapter = new CommentAdapter(CommentActivity.this,commentList);
                rvComment.setAdapter(commentAdapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void showMessage(String s) {
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }
}