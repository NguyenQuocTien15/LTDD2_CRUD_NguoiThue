package com.example.ltdd2_crud_nguoithue.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.example.ltdd2_crud_nguoithue.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    ViewFlipper viewFlipper;
    ImageView imgDanhSachKhachHang, imgDanhSachSan, imgDanhSachKhachHangDaSan, imgDanhSachSanDaThue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setControl();
        setEvent();
    }

    private void setEvent() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Home Admin");
        ActionViewFlipper();


        imgDanhSachKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDSKH = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intentDSKH);
            }
        });
        imgDanhSachSan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDSSan = new Intent(HomeActivity.this, DanhSachSanActivity.class);
                startActivity(intentDSSan);
            }
        });
        imgDanhSachKhachHangDaSan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentKHDatSan = new Intent(HomeActivity.this,
                        DanhSachSanDeDatActivity.class);
                startActivity(intentKHDatSan);
            }
        });
        imgDanhSachSanDaThue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSanDaThue = new Intent(HomeActivity.this,
                        DanhSachSanDaThueActivity.class);
                startActivity(intentSanDaThue);
            }
        });
    }


    private void ActionViewFlipper() {
        ArrayList<Integer> mangQuangCao = new ArrayList<>();
        mangQuangCao.add(R.drawable.imgbanner1);
        mangQuangCao.add(R.drawable.imgbanner2);
        mangQuangCao.add(R.drawable.imgbanner3);

        for (int i = 0; i < mangQuangCao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.get().load(mangQuangCao.get(i)).into(imageView);

            //chỉnh tấm hình vừa khung quảng cáo
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //thêm ảnh imgview vào Viewflipper
            viewFlipper.addView(imageView);
        }
        //thiết lạp chạy tự động cho viewFlipper
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);

        //gọi animation cho vào ra
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);

        //gọi animation vào viewflipper
        viewFlipper.setAnimation(animation_slide_in);
        viewFlipper.setAnimation(animation_slide_out);
    }

    private void setControl() {
        viewFlipper = findViewById(R.id.viewflipperHome);
        imgDanhSachKhachHang = findViewById(R.id.imgDanhSachKhachHang);
        imgDanhSachSan = findViewById(R.id.imgDanhSachSan);
        imgDanhSachKhachHangDaSan = findViewById(R.id.imgDanhSachSanDaDat);
        imgDanhSachSanDaThue = findViewById(R.id.imgDanhSachDaChoThue);
    }
}