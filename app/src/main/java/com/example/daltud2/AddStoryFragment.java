package com.example.daltud2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.daltud2.Model.Story;

import java.util.ArrayList;
import java.util.List;

public class AddStoryFragment extends Fragment {

    private EditText storyTitle, storyAuthor;
    private ImageView storyImage;
    private ListView chapterListView;
    private Story story;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_story, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        storyImage = view.findViewById(R.id.storyImage);
        storyTitle = view.findViewById(R.id.storyTitle);
        storyAuthor = view.findViewById(R.id.storyAuthor);
        chapterListView = view.findViewById(R.id.chapterListView);
        Button btnChangeImage = view.findViewById(R.id.btnChangeImage);
        Button btnDelete = view.findViewById(R.id.btnDelete);
        ImageButton btnBack = view.findViewById(R.id.btnBack);
        ImageButton btnAddChapter = view.findViewById(R.id.btnAddChapter);

        // Dữ liệu ví dụ cho ListView
        List<String> chapterList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) { // Thêm 10 chapter vào danh sách ban đầu
            chapterList.add("Chapter " + i);
        }

        // Tạo ArrayAdapter và gắn vào ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                chapterList
        );
        chapterListView.setAdapter(adapter);

        // Xử lý sự kiện thêm chapter
        btnAddChapter.setOnClickListener(v -> {
            int nextChapterNumber = chapterList.size() + 1;
            chapterList.add("Chapter " + nextChapterNumber);
            adapter.notifyDataSetChanged();
        });

        // Change Image button functionality
        btnChangeImage.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Thay đổi hình ảnh chưa được triển khai", Toast.LENGTH_SHORT).show();
        });

        // Delete button functionality
        btnDelete.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Xóa chương chưa được triển khai", Toast.LENGTH_SHORT).show();
        });

        // Back button functionality
        btnBack.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });
    }

}
