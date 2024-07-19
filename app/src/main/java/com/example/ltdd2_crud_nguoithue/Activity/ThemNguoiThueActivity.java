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
import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import com.google.android.gms.tasks.OnFailureListener;
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


public class ThemNguoiThueActivity extends AppCompatActivity {
    ImageView imageViewNguoiThue;
    FloatingActionButton floatingActionButton;
    static TextView txtID;
    EditText edtName, edtSoDienThoai, edtPassword, edtConfirm;
    Button btnThemNguoiThue;

    Uri uri;
    private FirebaseStorage firebaseStorage;
    private FirebaseDatabase database;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nguoi_thue);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Thêm người thuê");

        setControl();
        setEvent();
    }

    private void setEvent() {
        database = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        ActivityResultLauncher<Intent> activityResultLauncher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                        new ActivityResultCallback<ActivityResult>() {
                            @Override
                            public void onActivityResult(ActivityResult o) {
                                if (o.getResultCode() == Activity.RESULT_OK) {
                                    Intent data = o.getData();
                                    uri = data.getData();
                                    imageViewNguoiThue.setImageURI(uri);
                                } else {
                                    Toast.makeText(ThemNguoiThueActivity.this, "No Image Selected!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImage();
            }
        });

        btnThemNguoiThue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertNguoiThue();
            }
        });
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
                Toast.makeText(ThemNguoiThueActivity.this, "Permision Danied!", Toast.LENGTH_SHORT).show();
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
            imageViewNguoiThue.setImageURI(uri);
        }

    }

    private void InsertNguoiThue() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(ThemNguoiThueActivity.this);
//        builder.setCancelable(false);
//        builder.setView(R.layout.progess_layout);
//        AlertDialog dialog = builder.create();
//        dialog.show();

        final StorageReference storageReference = firebaseStorage.getReference().child("Users").child(System.currentTimeMillis() + "");

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        User user = new User();
                        user.setId(databaseReference.push().getKey());
                        user.setImage(uri.toString());
                        user.setName(edtName.getText().toString());
                        user.setSoDienThoai(edtSoDienThoai.getText().toString());
                        user.setPassword(edtPassword.getText().toString());
                        user.setConFirm(edtConfirm.getText().toString());
                        databaseReference.child(user.getId()).child(txtID.getText().toString()).setValue(user);
                        MainActivity.userList.clear();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //dialog.dismiss();
                    }
                });
            }
        });
        onBackPressed();
    }


    private void setControl() {
        imageViewNguoiThue = findViewById(R.id.imageViewNguoiThue);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        txtID = findViewById(R.id.txtIDNguoiThue);
        edtName = findViewById(R.id.edtHoTen);
        edtSoDienThoai = findViewById(R.id.edtSoDienThoai);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirm = findViewById(R.id.edtConfirm);
        btnThemNguoiThue = findViewById(R.id.btnThemNguoiThue);
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
}