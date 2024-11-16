package com.example.daltud2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.service.controls.actions.FloatAction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.daltud2.Model.User;
import com.example.daltud2.Model.UserAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QLTaiKhoanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QLTaiKhoanFragment extends Fragment {
    private RecyclerView rvUserList;
    private EditText etSearchUser;
    private UserAdapter userAdapter;
    private List<User> userList;
    private FloatingActionButton fabAddUser;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QLTaiKhoanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QLTaiKhoanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QLTaiKhoanFragment newInstance(String param1, String param2) {
        QLTaiKhoanFragment fragment = new QLTaiKhoanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

        userList = new ArrayList<>();
        userAdapter = new UserAdapter(userList, new UserAdapter.OnUserActionListener() {
            @Override
            public void onEdit(User user) {
                Toast.makeText(getContext(), "Chỉnh sửa: " + user.getName(), Toast.LENGTH_SHORT).show();
                // Chuyển sang màn hình chỉnh sửa
            }

            @Override
            public void onDelete(User user) {
                Toast.makeText(getContext(), "Xóa người dùng: " + user.getName(), Toast.LENGTH_SHORT).show();
                // Thực hiện xóa người dùng
            }
        });

        rvUserList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvUserList.setAdapter(userAdapter);

        // Load dữ liệu giả (hoặc thay bằng API)
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
        // Load dữ liệu giả (hoặc thay bằng API)
        loadDummyData();

        FloatingActionButton fabAddUser = view.findViewById(R.id.fab_add_user);
        if (fabAddUser == null) {
            throw new NullPointerException("FloatingActionButton is null. Check your layout and ID.");
        }

        fabAddUser.setOnClickListener(v -> {
            // Chuyển sang AddUserFragment
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new AddUserFragment())
                    .addToBackStack(null)
                    .commit();
        });
    }

    private void loadDummyData() {
        userList.add(new User("Nguyễn Văn A", "example@gmail.com", "url_avatar"));
        userList.add(new User("Trần Thị B", "test@gmail.com", "url_avatar"));
        userList.add(new User("Lê Văn C", "abc@gmail.com", "url_avatar"));
        userAdapter.updateData(userList);
    }

    private void filterUsers(String query) {
        List<User> filteredList = new ArrayList<>();
        for (User user : userList) {
            if (user.getName().toLowerCase().contains(query.toLowerCase()) ||
                    user.getEmail().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(user);
            }
        }
        userAdapter.updateData(filteredList);
    }

}