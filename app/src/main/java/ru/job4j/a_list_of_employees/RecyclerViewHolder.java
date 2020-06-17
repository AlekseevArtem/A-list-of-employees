package ru.job4j.a_list_of_employees;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private View view;
    private final Callback callback;

    public RecyclerViewHolder(@NonNull View view, Callback callback) {
        super(view);
        this.view = itemView;
        this.callback = callback;
    }

    public View getView() {
        return view;
    }

    public void bind(View view, Object object) {
        callback.createNewFragment(view, object);
    }
}