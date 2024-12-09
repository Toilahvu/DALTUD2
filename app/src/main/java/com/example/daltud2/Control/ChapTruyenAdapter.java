package com.example.daltud2.Control;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daltud2.Model.ChuongTruyen;
import com.example.daltud2.R;

import java.util.List;

public class ChapTruyenAdapter extends RecyclerView.Adapter<ChapTruyenAdapter.MyViewHolder> {

    private List<ChuongTruyen> ChuongTruyen;

    public ChapTruyenAdapter(List<ChuongTruyen> chapList) {
        this.ChuongTruyen = chapList;

        if (ChuongTruyen != null) {
            Log.d("ChapTruyenAdapter", "Số lượng chương: " + ChuongTruyen.size());
        } else {
            Log.e("ChapTruyenAdapter", "Danh sách chương bị null.");
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout item_chap.xml
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chap_truyen_recycler_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Lấy dữ liệu của từng Chap
        ChuongTruyen chuongTruyen = ChuongTruyen.get(position);

        // Gắn dữ liệu vào ViewHolder
        holder.tvChap.setText("Chapter " + chuongTruyen.getChuongSo());
        holder.tvNgayPhatHanh.setText(chuongTruyen.getNgayPhatHanh());
    }

    @Override
    public int getItemCount() {
        return ChuongTruyen.size(); // Số lượng item
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvChap;
        TextView tvNgayPhatHanh;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            // Ánh xạ các View từ item_chap.xml
            tvChap = itemView.findViewById(R.id.tvChap);
            tvNgayPhatHanh = itemView.findViewById(R.id.tvNgayPhatHanh);
        }
    }
}
