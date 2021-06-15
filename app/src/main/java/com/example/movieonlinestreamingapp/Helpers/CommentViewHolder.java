package com.example.movieonlinestreamingapp.Helpers;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieonlinestreamingapp.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class CommentViewHolder extends RecyclerView.ViewHolder {

    public ImageView commentUserImg;
    public TextView commentUserName, commentContent;

    public CommentViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        commentUserImg = itemView.findViewById(R.id.comment_user_img);
        commentUserName = itemView.findViewById(R.id.comment_username);
        commentContent = itemView.findViewById(R.id.comment_content);

    }
    public void setCommentUserImg(Context context, String url) {
        Glide.with(context).load(url).into(commentUserImg);
    }
}
