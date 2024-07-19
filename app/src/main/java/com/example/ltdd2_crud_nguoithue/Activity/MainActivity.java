package com.example.ltdd2_crud_nguoithue.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ltdd2_crud_nguoithue.Adapter.NguoiThueAdapter;
import com.example.ltdd2_crud_nguoithue.Helper.NguoiThueClickListener;
import com.example.ltdd2_crud_nguoithue.Helper.RyecyclerViewNguoiThueHelperListener;
import com.example.ltdd2_crud_nguoithue.Models.User;
import com.example.ltdd2_crud_nguoithue.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NguoiThueClickListener {
    RecyclerView recyclerView;
    static List<User> userList = new ArrayList<>();
    NguoiThueAdapter nguoiThueAdapter;
    FirebaseDatabase firebaseDatabase;
    LinearLayout linerListUser;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    TextView txtID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControl();
        setEvent();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Danh sách người thuê");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.mnAddNguoiThue:
                Intent intent = new Intent(MainActivity.this, ThemNguoiThueActivity.class);
                startActivity(intent);
                break;
            case R.id.search:
                SearchView searchView = (SearchView) item.getActionView();
                searchView.setQueryHint("Search....");
                searchView.clearFocus();
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        SearchList(newText);
                        return true;
                    }
                });

                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void SearchList(String text) {
        ArrayList<User> userArrayList = new ArrayList<>();
        for (User user: userList) {
            if (user.getName().toLowerCase().contains(text.toLowerCase())){
                userArrayList.add(user);
            }
        }
        nguoiThueAdapter.SearchData(userArrayList);
    }

    private void setEvent() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        userList.clear();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        nguoiThueAdapter = new NguoiThueAdapter(MainActivity.this, userList, this);
        recyclerView.setAdapter(nguoiThueAdapter);

        ItemTouchHelper.SimpleCallback simpleCallback = new RyecyclerViewNguoiThueHelperListener(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
        DocDL();
    }

    public void DocDL() {
        userList.clear();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    userList.add(user);
                }
                nguoiThueAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setControl() {
        linerListUser = findViewById(R.id.linearUser);
        recyclerView = findViewById(R.id.recyclerView_NguoiThue);
    }


    @Override
    public void onItemClick(User user) {
        Intent intent = new Intent(this, UpdateActivity.class);
        startActivity(intent);
    }


    @Override
    public void onSwipe(RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof NguoiThueAdapter.NguoiThueHolder) {
            String nameDelete = userList.get(viewHolder.getAdapterPosition()).getName();
            User userDelete = userList.get(viewHolder.getAdapterPosition());
            int indexDelete = viewHolder.getAdapterPosition();


            //removeitem
            AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);

            dialogXoa.setMessage("Bạn có muốn xóa người thuê " + nameDelete + " không!!");
            dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    nguoiThueAdapter.removeItem(indexDelete);
                    userList.clear();
                    nguoiThueAdapter.notifyDataSetChanged();

                    Snackbar snackbar = Snackbar.make(linerListUser, nameDelete + " remove!!!", Snackbar.LENGTH_LONG);
                    snackbar.setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (indexDelete == 0 || indexDelete == userList.size() - 1) {
                                recyclerView.scrollToPosition(indexDelete);
                            }
                            userList.clear();
                            nguoiThueAdapter.notifyDataSetChanged();
                        }
                    });
                    snackbar.setActionTextColor(Color.YELLOW);
                    snackbar.show();
                }
            });
            dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            dialogXoa.show();

        }
    }
}

