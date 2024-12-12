package com.example.daltud2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    private Switch themeSwitch;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Khởi tạo các view
        themeSwitch = view.findViewById(R.id.themeSwitch);
        TextView appVersionTextView = view.findViewById(R.id.appVersionTextView);

        // Cài đặt trạng thái ban đầu cho switch
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("appPreferences", getContext().MODE_PRIVATE);
        boolean isDarkMode = sharedPreferences.getBoolean("isDarkMode", false);
        themeSwitch.setChecked(isDarkMode);

        // Xử lý thay đổi trạng thái switch
        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Chế độ tối
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                // Chế độ sáng
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }

            // Lưu trạng thái của chế độ sáng/tối
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isDarkMode", isChecked);
            editor.apply();
        });

        // Hiển thị thông tin phiên bản ứng dụng
        appVersionTextView.setText("Phiên bản: 1.0.0");

        return view;
    }
}
