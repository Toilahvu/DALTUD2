package com.example.daltud2.View;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daltud2.Control.DataBaseSQLLite;
import com.example.daltud2.R;
import com.example.daltud2.Control.NoticeAdapter;

import java.util.ArrayList;
import java.util.List;

public class Message extends DialogFragment {

    private static final String ARG_PARAM1 = "param1";

    private String title;
    private String messageContent;

    public Message() {
        // Required empty public constructor
    }

    public static Message newInstance(String title) {
        Message fragment = new Message();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, title);
        fragment.setArguments(args);
        return fragment;
    }

    public void setMessageContent(String content) {
        this.messageContent = content; // Cập nhật nội dung thông báo
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Lấy kích thước màn hình
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.9); // 90% chiều rộng màn hình
        int height = ViewGroup.LayoutParams.WRAP_CONTENT; // Chiều cao tùy thuộc vào nội dung
        // Đặt kích thước cho DialogFragment
        getDialog().getWindow().setLayout(width, height);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        // Set tiêu đề
        TextView titleText = view.findViewById(R.id.titleText);
        titleText.setText(title);

        // Danh sách thông báo
        RecyclerView notificationList = view.findViewById(R.id.notificationList);
        notificationList.setLayoutManager(new LinearLayoutManager(getContext()));

        // Lấy dữ liệu thông báo từ cơ sở dữ liệu
        List<String> notifications = getLatestNotifications(5); // Lấy tối đa 5 thông báo mới nhất
        NoticeAdapter adapter = new NoticeAdapter(notifications);
        notificationList.setAdapter(adapter);

        // Nút Close
        Button closeButton = view.findViewById(R.id.close_button);
        closeButton.setOnClickListener(v -> dismiss()); // Đóng DialogFragment

        return view;
    }

    private List<String> getLatestNotifications(int limit) {
        List<String> notifications = new ArrayList<>();
        DataBaseSQLLite   dataBaseSQLLite = new DataBaseSQLLite(getActivity(), null, null, 1);

        Cursor cursor = dataBaseSQLLite.getAllNews(dataBaseSQLLite.getReadableDatabase());
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                notifications.add(cursor.getString(2)); // Lấy nội dung thông báo
            }
        }
        cursor.close();

        return notifications;
    }
}

