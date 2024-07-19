package com.example.ltdd2_crud_nguoithue.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ltdd2_crud_nguoithue.Activity.UserDatSanActivity;
import com.example.ltdd2_crud_nguoithue.Helper.UserDatSanClickListener;
import com.example.ltdd2_crud_nguoithue.Models.DatSan;
import com.example.ltdd2_crud_nguoithue.Models.San;
import com.example.ltdd2_crud_nguoithue.Models.User;
import com.example.ltdd2_crud_nguoithue.R;

import java.util.ArrayList;
import java.util.List;

public class DatSanUserAdapter extends RecyclerView.Adapter<DatSanUserHolder>{
    Context context;
    List<San> sanList;
    UserDatSanClickListener userDatSanClickListener;

    public DatSanUserAdapter(Context context,List<San> sanList, UserDatSanClickListener userDatSanClickListener) {
        this.context = context;
        this.sanList = sanList;
        this.userDatSanClickListener = userDatSanClickListener;
    }

    @NonNull
    @Override
    public DatSanUserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_san_user, parent, false);
        return  new DatSanUserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DatSanUserHolder holder, int position) {
        San san = sanList.get(position);
        holder.txtTenSan.setText(san.getTenSan());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserDatSanActivity.class);
                intent.putExtra("itemSan", sanList.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sanList.size();
    }

    public void SearchData(ArrayList<San> list){
        sanList = list;
        notifyDataSetChanged();
    }
}
class DatSanUserHolder extends RecyclerView.ViewHolder {

    CardView cardView;
    TextView txtTenSan;

    public DatSanUserHolder(@NonNull View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.cardView_layout_san_user);
        txtTenSan = itemView.findViewById(R.id.txtTenSan);
    }
}
