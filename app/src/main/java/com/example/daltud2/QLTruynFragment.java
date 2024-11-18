package com.example.daltud2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daltud2.Model.Story;
import com.example.daltud2.Model.StoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class QLTruynFragment extends Fragment {

    private List<Story> storyList = new ArrayList<>();
    private com.example.daltud2.Model.StoryAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_q_l_truyen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Khởi tạo danh sách truyện
        storyList = new ArrayList<>();
        storyList.add(new Story(1, "Truyện Mới 1", "Tác giả Mới 1"));
        storyList.add(new Story(2, "Truyện Mới 2", "Tác giả Mới 2"));

        // Thiết lập RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewStories);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new StoryAdapter(storyList, new com.example.daltud2.Model.StoryAdapter.OnStoryClickListener() {
            @Override
            public void onEditStory(int position, Story story) {

            }

            @Override
            public void onEditStory(int position) {
                // Sửa truyện
                editStory(position);
            }

            @Override
            public void onDeleteStory(int position) {
                // Xóa truyện
                deleteStory(position);
            }
        });
        recyclerView.setAdapter(adapter);

        // Nút thêm truyện
        view.findViewById(R.id.fab_add_story).setOnClickListener(v -> addStory());
    }

    private void addStory() {
        int id = storyList.size() + 1;
        storyList.add(new Story(id, "Truyện mới " + id, "Tác giả mới " + id));
        adapter.notifyItemInserted(storyList.size() - 1);
    }

    private void editStory(int position) {
        Story story = storyList.get(position);
        storyList.set(position, new Story(story.getId(), story.getTitle() + " (Đã sửa)", story.getAuthor() + " (Đã sửa)"));
        adapter.notifyItemChanged(position);
    }

    private void deleteStory(int position) {
        storyList.remove(position);
        adapter.notifyItemRemoved(position);
    }
}
