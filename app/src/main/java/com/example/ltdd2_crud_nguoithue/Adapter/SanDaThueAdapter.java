package com.example.ltdd2_crud_nguoithue.Adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ltdd2_crud_nguoithue.Helper.ChiTietNguoiThueClickListener;
import com.example.ltdd2_crud_nguoithue.Models.DatSan;
import com.example.ltdd2_crud_nguoithue.R;

import java.util.List;

public class SanDaThueAdapter extends RecyclerView.Adapter<SanDaThueHolder> {
    List<DatSan> datSanList;
    ChiTietNguoiThueClickListener chiTietNguoiThueClickListener;

    public SanDaThueAdapter(List<DatSan> datSanList, ChiTietNguoiThueClickListener chiTietNguoiThueClickListener) {
        this.datSanList = datSanList;
        this.chiTietNguoiThueClickListener = chiTietNguoiThueClickListener;
    }

    @NonNull
    @Override
    public SanDaThueHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_san_da_thue, parent, false);
        return new SanDaThueHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanDaThueHolder holder, @SuppressLint("RecyclerView") int position) {
        DatSan datSan = datSanList.get(position);
        holder.txtTenSanDaThue.setText(datSan.getTenSan());
        holder.txtKhungGio.setText(datSan.getKhungGio());
        holder.cardViewSanDaThue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chiTietNguoiThueClickListener.onItemCLick(datSanList.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return datSanList.size();
    }
}

class SanDaThueHolder extends RecyclerView.ViewHolder {
    CardView cardViewSanDaThue;
    TextView txtTenSanDaThue, txtKhungGio;

    public SanDaThueHolder(@NonNull View itemView) {
        super(itemView);
        cardViewSanDaThue = itemView.findViewById(R.id.cardView_sanDaThue);
        txtTenSanDaThue = itemView.findViewById(R.id.txtTenSan_layout_san_dathue);
        txtKhungGio = itemView.findViewById(R.id.txtGioSanDaThue);
    }
}
