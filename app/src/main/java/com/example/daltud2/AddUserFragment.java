package com.example.daltud2;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.daltud2.Model.User;

public class AddUserFragment extends Fragment {

    private static final String ARG_USER = "user";
    private User currentUser; // Đối tượng người dùng để chỉnh sửa

    public AddUserFragment() {
        // Required empty public constructor
    }

    public static AddUserFragment newInstance(User user) {
        AddUserFragment fragment = new AddUserFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, user); // Lưu trữ đối tượng User vào Bundle
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentUser = (User) getArguments().getSerializable(ARG_USER); // Lấy dữ liệu người dùng
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Liên kết các trường giao diện
        EditText etName = view.findViewById(R.id.etName);
        EditText etEmail = view.findViewById(R.id.etEmail);
        EditText etPassword = view.findViewById(R.id.etPassword);
        Button btnSave = view.findViewById(R.id.btnSave);
        ImageButton btnBack = view.findViewById(R.id.btnBack);
        CheckBox cbShowPassword = view.findViewById(R.id.cbShowPassword);

        // Nếu đang chỉnh sửa, hiển thị thông tin người dùng hiện tại
        if (currentUser != null) {
            etName.setText(currentUser.getName());
            etEmail.setText(currentUser.getEmail());
            etPassword.setText(currentUser.getPassword());
        }

        // Xử lý khi bấm nút Back
        btnBack.setOnClickListener(v -> {
            // Đóng AddUserFragment
            getParentFragmentManager().popBackStack();
        });

        // Xử lý hiển thị/ẩn mật khẩu
        cbShowPassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            etPassword.setSelection(etPassword.getText().length()); // Đặt con trỏ ở cuối
        });

        // Xử lý khi bấm nút Lưu
        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // Kiểm tra các trường nhập
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            } else {
                if (currentUser == null) {
                    // Trường hợp thêm mới người dùng
                    currentUser = new User(name, email, "url_avatar", password);
                    Toast.makeText(getContext(), "Đã thêm tài khoản: " + name, Toast.LENGTH_SHORT).show();
                } else {
                    // Trường hợp chỉnh sửa người dùng
                    currentUser.setName(name);
                    currentUser.setEmail(email);
                    currentUser.setPassword(password);
                    Toast.makeText(getContext(), "Đã cập nhật tài khoản: " + name, Toast.LENGTH_SHORT).show();
                }

                // Giả lập việc thêm hoặc cập nhật thông tin người dùng (hoặc gửi lên API)

                // Quay lại màn hình trước đó
                getParentFragmentManager().popBackStack();
            }
        });
    }
}
