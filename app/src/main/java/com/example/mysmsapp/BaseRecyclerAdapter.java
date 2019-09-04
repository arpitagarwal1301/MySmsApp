package com.example.mysmsapp;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseRecyclerAdapter<VH extends RecyclerView.ViewHolder ,T> extends RecyclerView.Adapter<VH>  implements View.OnClickListener{

    private final List<T> mObjectList;
    private RecyclerClickListener mClickListener;

    public BaseRecyclerAdapter(List<T> objectList ) {
        mObjectList = objectList;
    }

    public BaseRecyclerAdapter(List<T> objectList , RecyclerClickListener clickListener ) {
        mObjectList = objectList;
        mClickListener = clickListener;
    }

    @Override
    public void onClick(View view) {
        mClickListener.onClickAction(view);
    }

    @Override
    public int getItemCount() {
        return mObjectList.size();
    }

    public T getItem(int position){
        return mObjectList.get(position);
    }

    public interface RecyclerClickListener{
        public void onClickAction(View view);
    }
}
