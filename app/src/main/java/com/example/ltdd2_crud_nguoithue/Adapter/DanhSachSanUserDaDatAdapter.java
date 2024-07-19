package com.example.ltdd2_crud_nguoithue.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ltdd2_crud_nguoithue.Activity.DanhSachSanActivity;
import com.example.ltdd2_crud_nguoithue.Activity.UserChiTietDatSan;
import com.example.ltdd2_crud_nguoithue.Models.DatSan;
import com.example.ltdd2_crud_nguoithue.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class DanhSachSanUserDaDatAdapter extends ArrayAdapter {
    Context context;
    int resource;
    List<DatSan> datSanList;

    public DanhSachSanUserDaDatAdapter(@NonNull Context context, int resource, List<DatSan> datSanList) {
        super(context, resource, datSanList);
        this.context = context;
        this.resource = resource;
        this.datSanList = datSanList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        TextView txtIDSan = convertView.findViewById(R.id.txtIDSan);
        TextView txtTenSan = convertView.findViewById(R.id.txtTenSanDatSan);
        TextView txtKhungGio = convertView.findViewById(R.id.txtGioSanDaThue);
        TextView txtHuy = convertView.findViewById(R.id.txtHuy);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DatSan");
        DatSan datSan = datSanList.get(position);

        txtIDSan.setText(datSan.getIdDatSan());
        txtTenSan.setText(datSan.getTenSan());
        txtKhungGio.setText(datSan.getKhungGio());
        txtHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Bạn có muốn hủy sân " + txtTenSan.getText().toString() + " không.");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        databaseReference.child(txtIDSan.getText().toString()).removeValue();
                        UserChiTietDatSan.datSanList.clear();
                        UserChiTietDatSan.danhSachSanUserDaDatAdapter.notifyDataSetChanged();

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


        return convertView;
    }
}
