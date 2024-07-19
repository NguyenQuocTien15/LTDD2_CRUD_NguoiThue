package com.example.ltdd2_crud_nguoithue.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ltdd2_crud_nguoithue.Models.San;
import com.example.ltdd2_crud_nguoithue.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class ThemSanActivity extends AppCompatActivity {
    EditText edtIDSan, edtTenSan;
    Button btnThemSan;
    private FirebaseDatabase database;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_san);
        setControl();
        setEvent();
    }

    private void setEvent() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Thêm sân");

        database = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("San");

        btnThemSan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThemSan();
            }
        });
    }

    private void ThemSan() {
        San san = new San();
        san.setIdSan(databaseReference.push().getKey());
        san.setTenSan(edtTenSan.getText().toString());
        databaseReference.child(san.getIdSan()).child(edtIDSan.getText().toString()).setValue(san);
        DanhSachSanActivity.sanAdapter.notifyDataSetChanged();
        DanhSachSanActivity.sanList.clear();
        onBackPressed();
    }

    private void setControl() {
        edtIDSan = findViewById(R.id.edtIDSan);
        edtTenSan = findViewById(R.id.edtTenSan);
        btnThemSan = findViewById(R.id.btnThemSan);
    }
}