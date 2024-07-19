package com.example.ltdd2_crud_nguoithue.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import com.example.ltdd2_crud_nguoithue.Adapter.SanDaThueAdapter;
import com.example.ltdd2_crud_nguoithue.Helper.ChiTietNguoiThueClickListener;
import com.example.ltdd2_crud_nguoithue.Models.DatSan;
import com.example.ltdd2_crud_nguoithue.Models.San;
import com.example.ltdd2_crud_nguoithue.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DanhSachSanDaThueActivity extends AppCompatActivity implements ChiTietNguoiThueClickListener {
    RecyclerView recyclerViewSanDaDat;
    SanDaThueAdapter sanDaThueAdapter;
    List<DatSan> datSanList = new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_san_da_thue);
        setControl();
        setEvent();
    }

    private void setEvent() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Danh sách sân đã thuê");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("DatSan");

        recyclerViewSanDaDat.setHasFixedSize(true);
        recyclerViewSanDaDat.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        sanDaThueAdapter = new SanDaThueAdapter(datSanList, this);
        recyclerViewSanDaDat.setAdapter(sanDaThueAdapter);


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
               sanDaThueAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemCLick(DatSan datSan) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_chitiet_nguoithue);
        TextView txtChiTietTenKhachHang, txtChiTietSoDienThoai, txtChiTietNgayThue,txtChiTietTenSan, txtChiTietKhungGio, txtChiTietGiaThue;
        txtChiTietTenKhachHang = dialog.findViewById(R.id.txtChiTietTenKhachHang);
        txtChiTietSoDienThoai = dialog.findViewById(R.id.txtChiTietSoDienThoai);
        txtChiTietNgayThue = dialog.findViewById(R.id.txtChiTietNgayThue);
        txtChiTietTenSan = dialog.findViewById(R.id.txtChiTietTenSan);
        txtChiTietKhungGio = dialog.findViewById(R.id.txtChiTietKhungGio);
        txtChiTietGiaThue = dialog.findViewById(R.id.txtChiTietGiaThue);

        txtChiTietTenKhachHang.setText(datSan.getTenKH());
        txtChiTietSoDienThoai.setText(datSan.getSoDienThoai());
        txtChiTietNgayThue.setText(datSan.getNgayThue());
        txtChiTietTenSan.setText(datSan.getTenSan());
        txtChiTietKhungGio.setText(datSan.getKhungGio());
        txtChiTietGiaThue.setText(datSan.getGiaTien());

        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setControl() {
        recyclerViewSanDaDat = findViewById(R.id.recyclerViewSanDaThue);
    }
}