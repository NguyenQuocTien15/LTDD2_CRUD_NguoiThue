package com.example.ltdd2_crud_nguoithue.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ltdd2_crud_nguoithue.Activity.UpdateActivity;
import com.example.ltdd2_crud_nguoithue.Helper.NguoiThueClickListener;
import com.example.ltdd2_crud_nguoithue.Models.User;
import com.example.ltdd2_crud_nguoithue.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NguoiThueAdapter extends RecyclerView.Adapter<NguoiThueAdapter.NguoiThueHolder> {

    Context context;
    List<User> data;
    NguoiThueClickListener nguoiThueClickListener;

    public NguoiThueAdapter(Context context, List<User> data, NguoiThueClickListener nguoiThueClickListener) {
        this.context = context;
        this.data = data;
        this.nguoiThueClickListener = nguoiThueClickListener;
    }

    @NonNull
    @Override
    public NguoiThueHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item, parent, false);
        return new NguoiThueHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NguoiThueHolder holder, @SuppressLint("RecyclerView") int position) {
        User user = data.get(position);
//        Glide.with(holder.imgAvatar.getContext())
//                .load(user.getImage()).placeholder(R.drawable.ic_launcher_background).circleCrop()
//                .error(R.drawable.ic_launcher_foreground).into(holder.imgAvatar);
        Picasso.get().load(user.getImage()).placeholder(R.drawable.jude).into(holder.imgAvatar);
        holder.txtHoTen.setText(user.getName());
        holder.txtSDThoai.setText(user.getSoDienThoai());
        holder.txtPassword.setText(user.getPassword());
        holder.txtConfirm.setText(user.getConFirm());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", data.get(holder.getAdapterPosition()).getId());
                intent.putExtra("Image", data.get(holder.getAdapterPosition()).getImage());
                intent.putExtra("name",  data.get(holder.getAdapterPosition()).getName());
                intent.putExtra("soDienThoai",  data.get(holder.getAdapterPosition()).getSoDienThoai());
                intent.putExtra("password",  data.get(holder.getAdapterPosition()).getPassword());
                intent.putExtra("confirm",  data.get(holder.getAdapterPosition()).getConFirm());
                context.startActivity(intent);
            }
        });
    }

    public void SearchData(ArrayList<User> list){
        data = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void undoItem(User userDelete, int indexDelete) {
        data.add(indexDelete, userDelete);
        notifyDataSetChanged();
    }

    public class NguoiThueHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public LinearLayout linearForeground;
        ImageView imgAvatar;
        TextView txtHoTen, txtSDThoai, txtPassword, txtConfirm;

        public NguoiThueHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            linearForeground = itemView.findViewById(R.id.linearForeground);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            txtHoTen = itemView.findViewById(R.id.txtHotenNguoiThue);
            txtSDThoai = itemView.findViewById(R.id.txtSDTNguoiThue);
            txtPassword = itemView.findViewById(R.id.txtPasswordNguoiThue);
            txtConfirm = itemView.findViewById(R.id.txtConfirmNguoiThue);
        }
    }

    public void removeItem(int index) {
        data.remove(index);
        notifyDataSetChanged();

    }
}


