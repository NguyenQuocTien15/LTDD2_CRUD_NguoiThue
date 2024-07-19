package com.example.ltdd2_crud_nguoithue.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ltdd2_crud_nguoithue.Models.User;
import com.example.ltdd2_crud_nguoithue.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateActivity extends AppCompatActivity {
    ImageView imageViewSuaNguoiThue;
    FloatingActionButton floatingActionButtonSua;
    static TextView txtID;
    EditText edtSuaName, edtSuaSoDienThoai, edtSuaPassword, edtSuaConfirm;
    Button btnSuaNguoiThue, btnXoa;
    Uri uri;
    DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;
    FirebaseDatabase firebaseDatabase;
    String key = "";
    String oldImageUrl = "";
    List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Chi tiết người thuê");
        setControl();
        setEvent();
    }

    private void setEvent() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ActivityResultLauncher<Intent> intentActivityResultLauncher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult o) {
                            if (o.getResultCode() == Activity.RESULT_OK) {
                                Intent data = o.getData();
                                uri = data.getData();
                                imageViewSuaNguoiThue.setImageURI(uri);

                            } else {
                                Toast.makeText(UpdateActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );
            Picasso.get().load(bundle.getString("Image")).into(imageViewSuaNguoiThue);
            txtID.setText(bundle.getString("id"));
            edtSuaName.setText(bundle.getString("name"));
            edtSuaSoDienThoai.setText(bundle.getString("soDienThoai"));
            edtSuaPassword.setText(bundle.getString("password"));
            edtSuaConfirm.setText(bundle.getString("confirm"));
            oldImageUrl = bundle.getString("Image");
        }


        floatingActionButtonSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImage();
            }
        });


        btnSuaNguoiThue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveData();
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseDatabase.getReference().child("Users").child(txtID.getText().toString()).removeValue();
                MainActivity.userList.clear();
                onBackPressed();
            }
        });
    }

    private void SaveData() {
        StorageReference storageReference = firebaseStorage.getReference().child("Users");
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Map<String, Object> suaUser = new HashMap<String, Object>();
                        suaUser.put("image",uri.toString());
                        suaUser.put("name", edtSuaName.getText().toString());
                        suaUser.put("soDienThoai", edtSuaSoDienThoai.getText().toString());
                        suaUser.put("password", edtSuaPassword.getText().toString());
                        suaUser.put("conFirm", edtSuaConfirm.getText().toString());

                        firebaseDatabase.getReference().child("Users").child(txtID.getText().toString()).updateChildren(suaUser);
//                        firebaseDatabase.getReference().child("Users").child(txtID.getText().toString()).child("image").setValue(uri.toString());
//                        firebaseDatabase.getReference().child("Users").child(txtID.getText().toString()).child("name").setValue(edtSuaName.getText().toString());
//                        firebaseDatabase.getReference().child("Users").child(txtID.getText().toString()).child("soDienThoai").setValue(edtSuaSoDienThoai.getText().toString());
//                        firebaseDatabase.getReference().child("Users").child(txtID.getText().toString()).child("password").setValue(edtSuaPassword.getText().toString());
//                        firebaseDatabase.getReference().child("Users").child(txtID.getText().toString()).child("conFirm").setValue(edtSuaConfirm.getText().toString());
                        MainActivity.userList.clear();
                    }
                });
            }
        });
        onBackPressed();
    }

    private void UploadImage() {
//        ImagePicker.with(ThemNguoiThueActivity.this)
//                        .crop()                    //Crop image(Optional), Check Customization for more option
//                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
//                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
//                        .start();
        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 101);
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Toast.makeText(UpdateActivity.this, "Permision Danied!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK) {
            uri = data.getData();
            imageViewSuaNguoiThue.setImageURI(uri);
        }

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
        imageViewSuaNguoiThue = findViewById(R.id.imageViewSuaNguoiThue);
        floatingActionButtonSua = findViewById(R.id.floatingActionButtonSua);
        txtID = findViewById(R.id.txtIDNguoiThue);
        edtSuaName = findViewById(R.id.edtSuatHoTen);
        edtSuaSoDienThoai = findViewById(R.id.edtSuaSoDienThoai);
        edtSuaPassword = findViewById(R.id.edtSuaPassword);
        edtSuaConfirm = findViewById(R.id.edtSuaConfirm);
        btnSuaNguoiThue = findViewById(R.id.btnSuaNguoiThue);
        btnXoa = findViewById(R.id.btnXoaNguoiThue);
    }
}