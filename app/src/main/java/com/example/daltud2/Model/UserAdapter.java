package com.example.daltud2.Model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.daltud2.R; // Đường dẫn đúng đến file R
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> userList;
    private final OnUserActionListener onUserActionListener;

    // Giao diện callback cho các hành động
    public interface OnUserActionListener {
        void onEdit(User user);
        void onDelete(User user);
    }

    // Constructor cho Adapter
    public UserAdapter(List<User> userList, OnUserActionListener listener) {
        this.userList = userList;
        this.onUserActionListener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Sử dụng layout item_user
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void updateData(List<User> newList) {
        this.userList = newList;
        notifyDataSetChanged();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvUserName;
        private final TextView tvUserEmail;
        private final Button btnEditUser;
        private final Button btnDeleteUser;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            // Liên kết các view trong item_user.xml
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvUserEmail = itemView.findViewById(R.id.tvUserEmail);
            btnEditUser = itemView.findViewById(R.id.btnEditUser);
            btnDeleteUser = itemView.findViewById(R.id.btnDeleteUser);
        }

        public void bind(final User user) {
            tvUserName.setText(user.getName());
            tvUserEmail.setText(user.getEmail());

            btnEditUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onUserActionListener.onEdit(user);
                }
            });

            btnDeleteUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onUserActionListener.onDelete(user);
                }
            });
        }
    }
}
