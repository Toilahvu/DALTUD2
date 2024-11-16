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
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addControls();
        addEvents();
    }
    void addControls()
    {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }
    void addEvents()
    {
        // Xử lý sự kiện khi chọn menu
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_manage_stories) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new QLTruynFragment())
                        .commit();
                return true;
            } else if (item.getItemId() == R.id.menu_manage_accounts) {
                if (item.getItemId() == R.id.menu_manage_accounts) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainer, new QLTaiKhoanFragment())
                            .commit();
                }
                return true;
            } else if (item.getItemId() == R.id.menu_settings) {
                if (item.getItemId() == R.id.menu_settings) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainer, new SettingsFragment())
                            .commit();
                }
                return true;
            }
            return false;
        });

    }

}