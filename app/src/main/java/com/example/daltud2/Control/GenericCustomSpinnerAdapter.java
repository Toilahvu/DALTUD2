package com.example.daltud2.Control;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.daltud2.Model.tagComics;
import com.example.daltud2.R;

import java.util.List;

public class GenericCustomSpinnerAdapter<T> extends ArrayAdapter<T> {

    private final int resource; // Layout resource tùy chỉnh cho spinner
    private final Context context;
    private boolean isNotWhite = true;

    public GenericCustomSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<T> objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.context = context;
    }

    public void setTheme(boolean isNotWhite) {
        this.isNotWhite = isNotWhite;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }
        TextView tvSelected = convertView.findViewById(R.id.tv_selected);

        if (isNotWhite) {
            tvSelected.setBackgroundColor(Color.parseColor("#1C1C1E"));
            tvSelected.setTextColor(Color.WHITE);
        } else {
            tvSelected.setBackgroundColor(Color.WHITE);
            tvSelected.setTextColor(Color.BLACK);
        }

        T item = getItem(position);
        if (item != null) {
            tvSelected.setText(item.toString()); // Sử dụng `toString()` để hiển thị tên
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        }
        TextView tvDropDown = convertView.findViewById(R.id.tv_category);

        // Áp dụng màu sắc theo theme
        if (isNotWhite) {
            tvDropDown.setBackgroundColor(Color.WHITE);
            tvDropDown.setTextColor(Color.BLACK);
        } else {
            tvDropDown.setBackgroundColor(Color.parseColor("#1C1C1E"));
            tvDropDown.setTextColor(Color.WHITE);
        }

        T item = getItem(position);
        if (item != null) {
            tvDropDown.setText(item.toString()); // Hiển thị tên hoặc mô tả của đối tượng
        }
        return convertView;
    }
}
