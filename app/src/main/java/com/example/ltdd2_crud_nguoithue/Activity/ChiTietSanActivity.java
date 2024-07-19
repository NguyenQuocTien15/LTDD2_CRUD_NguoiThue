package com.example.ltdd2_crud_nguoithue.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ltdd2_crud_nguoithue.Models.San;
import com.example.ltdd2_crud_nguoithue.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ChiTietSanActivity extends AppCompatActivity {

    EditText edtIDSan, edtTenSan;
    Button btnSuaSan;
    San san;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san);
        setControl();
        setEvent();
    }

    private void setEvent() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Chi tiết sân");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("San");

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            edtIDSan.setText(bundle.getString("idSan"));
            edtTenSan.setText(bundle.getString("tenSan"));
        }


        btnSuaSan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SuaSan();
            }
        });

    }

    private void XoaSan() {
        firebaseDatabase.getReference().child("San").child(edtIDSan.getText().toString()).removeValue();
        DanhSachSanActivity.sanList.clear();
        onBackPressed();
    }

    private void SuaSan() {
        Map<String, Object> suaSan = new HashMap<String, Object>();
        suaSan.put("tenSan", edtTenSan.getText().toString());
        firebaseDatabase.getReference().child("San").child(edtIDSan.getText().toString()).updateChildren(suaSan);
        DanhSachSanActivity.sanList.clear();
        suaSan.clear();
        onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void setControl() {
        edtIDSan = findViewById(R.id.edtIDSan);
        edtTenSan = findViewById(R.id.edtTenSan);

        btnSuaSan = findViewById(R.id.btnSuaSan);
    }
}