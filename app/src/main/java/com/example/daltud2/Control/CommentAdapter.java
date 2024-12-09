package com.example.daltud2.Control;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daltud2.Model.Comment;
import com.example.daltud2.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<Comment> fullCommentList;
    private List<Comment> visibleCommentList;
    private Context context;
    private int limitComments = 5;

    public CommentAdapter(Context context, List<Comment> commentList) {
        this.context = context;
        this.fullCommentList = commentList;
        updateVisibleComments();
    }

    public void setLimitComments(int limit) {
        this.limitComments = limit;
        updateVisibleComments();
        notifyDataSetChanged();
    }
    public void setComments(List<Comment> newComments) {
        this.fullCommentList = newComments; // Cập nhật danh sách bình luận đầy đủ.
        updateVisibleComments(); // Cập nhật danh sách hiển thị.
        notifyDataSetChanged(); // Cập nhật lại giao diện.
    }


    private void updateVisibleComments() {
        if (fullCommentList != null) {
            int endIndex = Math.min(limitComments, fullCommentList.size());
            visibleCommentList = new ArrayList<>(fullCommentList.subList(0, endIndex));
        } else {
            visibleCommentList = new ArrayList<>();
        }
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_commnet_recycler_view, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = visibleCommentList.get(position);
        if (comment != null) {
            holder.tvUserName.setText(comment.getNameUser());
            holder.tvCommentContent.setText(comment.getNoiDungBinhLuan());
            holder.tvCommentTime.setText(getFormattedTime(comment.getThoiGianBinhLuan()));
        }
    }

    @Override
    public int getItemCount() {
        return visibleCommentList != null ? visibleCommentList.size() : 0;
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserName, tvCommentContent, tvCommentTime;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvTenUser);
            tvCommentContent = itemView.findViewById(R.id.tvComment);
            tvCommentTime = itemView.findViewById(R.id.tvThoiGianBinhLuan);
        }
    }

    private String getFormattedTime(String thoiGianBinhLuan) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date commentDate = dateFormat.parse(thoiGianBinhLuan);

            Calendar currentDate = Calendar.getInstance();
            Calendar commentDateCal = Calendar.getInstance();
            commentDateCal.setTime(commentDate);

            long diffInMillis = currentDate.getTimeInMillis() - commentDateCal.getTimeInMillis();
            long daysDiff = diffInMillis / (1000 * 60 * 60 * 24);

            if (daysDiff < 1) {
                return "Mới đăng";
            } else if (daysDiff < 31) {
                return daysDiff + " ngày trước";
            } else {
                long monthsDiff = daysDiff / 30;
                if (monthsDiff < 12) {
                    return monthsDiff + " tháng trước";
                } else {
                    long yearsDiff = monthsDiff / 12;
                    return yearsDiff + " năm trước";
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return "Không xác định";
        }
    }
}
