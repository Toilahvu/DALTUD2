package com.example.daltud2;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Kích hoạt chế độ Edge to Edge
        EdgeToEdge.enable(this);

        // Đặt layout chính
        setContentView(R.layout.activity_admin);

        // Thiết lập WindowInsets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            if (v != null) {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            }
            return insets;
        });

        // Gọi các phương thức xử lý
        addControls();
        addEvents();
    }

    void addControls() {
        // Tìm đối tượng BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }

    void addEvents() {
        // Xử lý sự kiện khi chọn menu
        if (bottomNavigationView != null) {
            bottomNavigationView.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();

                if (itemId == R.id.stories) {
                    // Thay thế fragment quản lý truyện
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new QLTruynFragment())
                            .commit();
                    return true;

                } else if (itemId == R.id.accounts) {
                    // Thay thế fragment quản lý tài khoản
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new QLTaiKhoanFragment())
                            .commit();
                    return true;

                } else if (itemId == R.id.settings) {
                    // Thay thế fragment cài đặt
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new SettingsFragment())
                            .commit();
                    return true;

                } else {
                    return false;
                }
            });
        }
    }

}
