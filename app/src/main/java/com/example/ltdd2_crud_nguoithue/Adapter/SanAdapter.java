package com.example.ltdd2_crud_nguoithue.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ltdd2_crud_nguoithue.Activity.ChiTietSanActivity;
import com.example.ltdd2_crud_nguoithue.Activity.DanhSachSanActivity;
import com.example.ltdd2_crud_nguoithue.Models.San;
import com.example.ltdd2_crud_nguoithue.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SanAdapter extends ArrayAdapter {

    Context context;
    int resource;
    List<San> sanList;

    public SanAdapter(@NonNull Context context, int resource, List<San> sanList) {
        super(context, resource, sanList);
        this.context = context;
        this.resource = resource;
        this.sanList = sanList;
    }

    public void SearchData(ArrayList<San> list){
        sanList = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        TextView txtIDSan = convertView.findViewById(R.id.txtIDSan);
        TextView txtTenSan = convertView.findViewById(R.id.txtTenSan);
        Button btnSua = convertView.findViewById(R.id.btnSuaSanLayoutItem);
        Button btnXoa = convertView.findViewById(R.id.btnXoaSanLayoutItem);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("San");
        San san = sanList.get(position);
        txtIDSan.setText(san.getIdSan());
        txtTenSan.setText(san.getTenSan());
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Bạn có muốn xóa sân " + txtTenSan.getText().toString() + " không");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        databaseReference.child(txtIDSan.getText().toString()).removeValue();
                        DanhSachSanActivity.sanList.clear();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
               builder.show();
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChiTietSanActivity.class);
                intent.putExtra("idSan", sanList.get(position).getIdSan());
                intent.putExtra("tenSan", sanList.get(position).getTenSan());
                context.startActivity(intent);
            }
        });


        return convertView;
    }
}
