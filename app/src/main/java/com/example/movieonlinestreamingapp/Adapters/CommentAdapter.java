package com.example.movieonlinestreamingapp.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieonlinestreamingapp.Helpers.CommentViewHolder;
import com.example.movieonlinestreamingapp.Helpers.VienTuongViewHolder;
import com.example.movieonlinestreamingapp.Models.Comment;
import com.example.movieonlinestreamingapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {
    private Activity activity;
    List<Comment> mData;

    public CommentAdapter(Activity activity, List<Comment> mData) {
        this.activity = activity;
        this.mData = mData;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.row_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull CommentViewHolder holder, int position) {
        if (mData.get(position).getUimg() != null){
            holder.setCommentUserImg(activity, mData.get(position).getUimg());
        } else {
            Glide.with(activity).load(R.drawable.userphoto).into(holder.commentUserImg);
        }

        holder.commentUserName.setText(mData.get(position).getUname());
        holder.commentContent.setText(mData.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
