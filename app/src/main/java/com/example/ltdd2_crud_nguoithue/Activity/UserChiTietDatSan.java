package com.example.ltdd2_crud_nguoithue.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.ltdd2_crud_nguoithue.Adapter.DanhSachSanUserDaDatAdapter;
import com.example.ltdd2_crud_nguoithue.Models.DatSan;
import com.example.ltdd2_crud_nguoithue.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserChiTietDatSan extends AppCompatActivity {

    ListView listView;
    public static DanhSachSanUserDaDatAdapter danhSachSanUserDaDatAdapter;
    public static List<DatSan> datSanList = new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chi_tiet_dat_san);
        setControl();
        setEvent();
    }

    private void setEvent() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("DatSan");

        danhSachSanUserDaDatAdapter = new DanhSachSanUserDaDatAdapter(this, R.layout.layout_item_user_danh_sach_san_da_dat, datSanList);
        listView.setAdapter(danhSachSanUserDaDatAdapter);
        DocDL();
    }

    private void DocDL() {
        datSanList.clear();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DatSan datSan = dataSnapshot.getValue(DatSan.class);
                    datSanList.add(datSan);
                }
                danhSachSanUserDaDatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setControl() {
        listView = findViewById(R.id.lvDanhSachSanUserDaDat);
    }
}