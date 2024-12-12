package com.example.daltud2;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.daltud2.Model.User;
import com.example.daltud2.Model.UserAdapter;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class QLTaiKhoanFragment extends Fragment {
    private RecyclerView rvUserList;
    private EditText etSearchUser;
    private UserAdapter userAdapter;
    private List<User> userList; // Danh sách đang hiển thị
    private List<User> originalList; // Danh sách gốc
    private FloatingActionButton fabAddUser;

    public QLTaiKhoanFragment() {
        // Required empty public constructor
    }

    public static QLTaiKhoanFragment newInstance() {
        return new QLTaiKhoanFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_q_l_tai_khoan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvUserList = view.findViewById(R.id.rvUserList);
        etSearchUser = view.findViewById(R.id.etSearchUser);
        fabAddUser = view.findViewById(R.id.fab_add_user);

        userList = new ArrayList<>();
        originalList = new ArrayList<>();
        userAdapter = new UserAdapter(userList, new UserAdapter.OnUserActionListener() {
            @Override
            public void onEdit(User user) {
                // Chuyển sang AddUserFragment với thông tin người dùng
                AddUserFragment addUserFragment = AddUserFragment.newInstance(user);
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, addUserFragment)
                        .addToBackStack(null)
                        .commit();
            }

            @Override
            public void onDelete(User user) {
                // Hiển thị hộp thoại xác nhận trước khi xóa
                new MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Xác nhận xóa")
                        .setMessage("Bạn có chắc chắn muốn xóa người dùng này?")
                        .setPositiveButton("Xóa", (dialog, which) -> {
                            int position = userList.indexOf(user);
                            if (position >= 0) {
                                userList.remove(position);
                                originalList.remove(user);
                                userAdapter.notifyItemRemoved(position);
                                Toast.makeText(getContext(), "Đã xóa người dùng: " + user.getName(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Hủy", null)
                        .show();
            }
        });

        rvUserList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvUserList.setAdapter(userAdapter);

        // Load dữ liệu giả
        loadDummyData();

        // Xử lý tìm kiếm
        etSearchUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterUsers(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Xử lý sự kiện nút thêm người dùng
        fabAddUser.setOnClickListener(v -> {
            // Chuyển sang AddUserFragment để thêm người dùng
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new AddUserFragment())
                    .addToBackStack(null)
                    .commit();
        });
    }

    private void loadDummyData() {
        originalList.add(new User("Nguyễn Văn A", "example@gmail.com", "url_avatar", "123456"));
        originalList.add(new User("Trần Thị B", "test@gmail.com", "url_avatar", "123456"));
        originalList.add(new User("Lê Văn C", "abc@gmail.com", "url_avatar", "123456"));
        userList.addAll(originalList);
        userAdapter.notifyDataSetChanged();
    }

    private void filterUsers(String query) {
        userList.clear();
        if (query.isEmpty()) {
            userList.addAll(originalList);
        } else {
            for (User user : originalList) {
                if (user.getName().toLowerCase().contains(query.toLowerCase()) ||
                        user.getEmail().toLowerCase().contains(query.toLowerCase())) {
                    userList.add(user);
                }
            }
        }
        userAdapter.notifyDataSetChanged();

        if (userList.isEmpty()) {
            Toast.makeText(getContext(), "Không tìm thấy người dùng phù hợp!", Toast.LENGTH_SHORT).show();
        }
    }
}
