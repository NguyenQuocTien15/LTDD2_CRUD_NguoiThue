package com.example.ltdd2_crud_nguoithue.Helper;

import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ltdd2_crud_nguoithue.Adapter.NguoiThueAdapter;

public class RyecyclerViewNguoiThueHelperListener extends ItemTouchHelper.SimpleCallback {
    private NguoiThueClickListener nguoiThueClickListener;

    public RyecyclerViewNguoiThueHelperListener(int dragDirs, int swipeDirs, NguoiThueClickListener nguoiThueClickListener) {
        super(dragDirs, swipeDirs);
        this.nguoiThueClickListener = nguoiThueClickListener;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        if (nguoiThueClickListener != null) {
            nguoiThueClickListener.onSwipe(viewHolder);
        }
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null){
            View viewForeground = ((NguoiThueAdapter.NguoiThueHolder)viewHolder).linearForeground;
            getDefaultUIUtil().onSelected(viewForeground);
        }
    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View viewForeground = ((NguoiThueAdapter.NguoiThueHolder)viewHolder).linearForeground;
        getDefaultUIUtil().onDrawOver(c, recyclerView, viewForeground, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View viewForeground = ((NguoiThueAdapter.NguoiThueHolder)viewHolder).linearForeground;
        getDefaultUIUtil().onDraw(c, recyclerView, viewForeground, dX, dY, actionState, isCurrentlyActive);
    }


    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        View viewForeground = ((NguoiThueAdapter.NguoiThueHolder)viewHolder).linearForeground;
        getDefaultUIUtil().clearView(viewForeground);
    }
}
