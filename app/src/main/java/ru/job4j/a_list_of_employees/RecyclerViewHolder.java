package ru.job4j.a_list_of_employees;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private View view;
    public RecyclerViewHolder(@NonNull View view) {
        super(view);
        this.view = itemView;
    }

    public View getView() {
        return view;
    }
}