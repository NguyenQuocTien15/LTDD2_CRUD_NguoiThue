package com.example.ltdd2_crud_nguoithue.Helper;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ltdd2_crud_nguoithue.Models.User;

public interface NguoiThueClickListener {
    void onItemClick(User user);
    void onSwipe(RecyclerView.ViewHolder viewHolder);
}
