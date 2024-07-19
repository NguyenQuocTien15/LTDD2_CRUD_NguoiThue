package com.example.ltdd2_crud_nguoithue.Activity;

import static com.example.ltdd2_crud_nguoithue.Activity.DanhSachSanActivity.sanAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.ltdd2_crud_nguoithue.Adapter.DatSanUserAdapter;
import com.example.ltdd2_crud_nguoithue.Adapter.SanAdapter;
import com.example.ltdd2_crud_nguoithue.Helper.UserDatSanClickListener;
import com.example.ltdd2_crud_nguoithue.Models.San;
import com.example.ltdd2_crud_nguoithue.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserHomeAvtivity extends AppCompatActivity implements UserDatSanClickListener {
    SearchView searchView;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<San> sanList = new ArrayList<>();
    DatSanUserAdapter datSanUserAdapter;
    ImageView imgChiTiet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        setControl();
        setEvent();
    }

    private void setEvent() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Trang chủ");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("San");


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        datSanUserAdapter = new DatSanUserAdapter(this,sanList, this);
        recyclerView.setAdapter(datSanUserAdapter);

        imgChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserHomeAvtivity.this, UserChiTietDatSan.class);
                startActivity(intent);
            }
        });

        DocDL();
    }

//    private void SearchSan(String newText) {
//        ArrayList<San> sanArrayList = new ArrayList<>();
//        for (San san : sanArrayList){
//            if (san.getTenSan().toLowerCase().contains(newText.toLowerCase())){
//                sanArrayList.add(san);
//            }
//        }
//        sanAdapter.SearchData(sanArrayList);
//    }

    private void DocDL() {
        sanList.clear();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    San san  = dataSnapshot.getValue(San.class);
                    sanList.add(san);
                }
                datSanUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setControl() {
      //  searchView = findViewById(R.id.searchSanUser);
        recyclerView = findViewById(R.id.recyclerViewUserHome);
        imgChiTiet = findViewById(R.id.imgHistory);
    }

    @Override
    public void onItemClick(San san) {
        Intent intent = new Intent(this, UserDatSanActivity.class);
        startActivity(intent);
    }
}