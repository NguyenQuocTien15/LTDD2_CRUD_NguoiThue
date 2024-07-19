package com.example.ltdd2_crud_nguoithue.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ltdd2_crud_nguoithue.Activity.DatSanAdminActivity;
import com.example.ltdd2_crud_nguoithue.Models.San;
import com.example.ltdd2_crud_nguoithue.R;

import java.util.List;

public class AdminDatSanAdapter extends ArrayAdapter {
    Context context;
    int resource;
    List<San> sanList;

    public AdminDatSanAdapter(@NonNull Context context, int resource, List<San> sanList) {
        super(context, resource, sanList);
        this.context = context;
        this.resource = resource;
        this.sanList = sanList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        TextView txtTenSan = convertView.findViewById(R.id.txtTenSan_layout_item_dat_san);
        TextView txtChonSan = convertView.findViewById(R.id.txtChonSan);

        San san = sanList.get(position);
        txtTenSan.setText(san.getTenSan());
        txtChonSan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DatSanAdminActivity.class);
                intent.putExtra("itemSan", sanList.get(position));
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
