package com.example.ltdd2_crud_nguoithue.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ltdd2_crud_nguoithue.Models.DatSan;
import com.example.ltdd2_crud_nguoithue.Models.San;
import com.example.ltdd2_crud_nguoithue.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UserDatSanActivity extends AppCompatActivity {

    EditText edtKHDatSan, edtSoDienThoai;
    TextView txtDatePicker, txtGiaSan, txtTenSanDatSan, txtIDDatSan;
    Spinner spnKhungGio;
    Button btnDatSan, btnHuy;
    ImageView imgDatePicker, imgTimePiker;
    List<String> stringListKhungGio = new ArrayList<>();

    ArrayAdapter khungGioAdapter;
    San san;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dat_san);
        setControl();
        setEvent();
    }

    private void setEvent() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Đặt Sân");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("DatSan");
        san = (San) getIntent().getSerializableExtra("itemSan");
        txtTenSanDatSan.setText(san.getTenSan());


        FuncDatePicker();

        KhoiTaoKhungGio();
        spnKhungGio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerGiaKhungGio();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        btnDatSan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDatSan();
            }
        });

    }

    private void UserDatSan() {
        DatSan datSan = new DatSan();
        datSan.setIdDatSan(databaseReference.push().getKey());
        datSan.setTenKH(edtKHDatSan.getText().toString());
        datSan.setSoDienThoai(edtSoDienThoai.getText().toString());
        datSan.setNgayThue(txtDatePicker.getText().toString());
        datSan.setKhungGio(spnKhungGio.getSelectedItem().toString());
        datSan.setTenSan(txtTenSanDatSan.getText().toString());
        datSan.setGiaTien(txtGiaSan.getText().toString());
        datSan.setConfirmed(false);
        databaseReference.child(datSan.getIdDatSan()).child(txtIDDatSan.getText().toString()).setValue(datSan);

        Intent intent = new Intent(this, UserHomeAvtivity.class);
        startActivity(intent);
    }

    private void FuncDatePicker() {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);

        imgDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(UserDatSanActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        if (i2 >= day && i1 >= month && i >= year) {
                            txtDatePicker.setText(i2 + "/" + i1 + "/" + i);
                        } else {
                            txtDatePicker.setText("Vui lòng chọn lại ngày");
                        }
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    private void SpinnerGiaKhungGio() {
        if (spnKhungGio.getSelectedItem().equals("7:00 - 8:00")) {
            txtGiaSan.setText("200000");
        }
        if (spnKhungGio.getSelectedItem().equals("8:00 - 9:00")) {
            txtGiaSan.setText("210000");
        }
        if (spnKhungGio.getSelectedItem().equals("9:00 - 10:00")) {
            txtGiaSan.setText("190000");
        }
        if (spnKhungGio.getSelectedItem().equals("16:00 - 17:00")) {
            txtGiaSan.setText("200000");
        }
        if (spnKhungGio.getSelectedItem().equals("17:00 - 18:00")) {
            txtGiaSan.setText("220000");
        }
        if (spnKhungGio.getSelectedItem().equals("18:00 - 19:00")) {
            txtGiaSan.setText("230000");
        }
        if (spnKhungGio.getSelectedItem().equals("19:00 - 20:00")) {
            txtGiaSan.setText("220000");
        }
        if (spnKhungGio.getSelectedItem().equals("20:00 - 21:00")) {
            txtGiaSan.setText("210000");
        }
    }

    private void KhoiTaoKhungGio() {
        stringListKhungGio.add("7:00 - 8:00");
        stringListKhungGio.add("8:00 - 9:00");
        stringListKhungGio.add("9:00 - 10:00");
        stringListKhungGio.add("16:00 - 17:00");
        stringListKhungGio.add("17:00 - 18:00");
        stringListKhungGio.add("18:00 - 19:00");
        stringListKhungGio.add("19:00 - 20:00");
        stringListKhungGio.add("20:00 - 21:00");
        khungGioAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, stringListKhungGio);
        spnKhungGio.setAdapter(khungGioAdapter);


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
        edtKHDatSan = findViewById(R.id.edtKhachHang);
        edtSoDienThoai = findViewById(R.id.edtSoDienThoaiKH);
        imgDatePicker = findViewById(R.id.imgDatePicker);
        txtDatePicker = findViewById(R.id.txtDatePicker);
        txtIDDatSan = findViewById(R.id.txtIDDatSan);
        txtGiaSan = findViewById(R.id.txtGiaSan);
        txtTenSanDatSan = findViewById(R.id.txtTenSanDatSan);
        spnKhungGio = findViewById(R.id.spnHour);

        btnDatSan = findViewById(R.id.btnDatSan);
        btnHuy = findViewById(R.id.bntHuy);
    }
}