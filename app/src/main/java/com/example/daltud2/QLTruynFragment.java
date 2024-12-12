package com.example.daltud2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daltud2.Model.Story;
import com.example.daltud2.Model.StoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class QLTruynFragment extends Fragment {

    private List<Story> storyList = new ArrayList<>();
    private StoryAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_q_l_truyen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Khởi tạo danh sách truyện
        storyList.add(new Story(1, "Truyện Mới 1", "Tác giả Mới 1"));
        storyList.add(new Story(2, "Truyện Mới 2", "Tác giả Mới 2"));

        // Thiết lập RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewStories);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new StoryAdapter(storyList, new StoryAdapter.OnStoryClickListener() {
            @Override
            public void onEditStory(Story story) {
                // Tạo AddStoryFragment và truyền dữ liệu
                AddStoryFragment addStoryFragment = new AddStoryFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("story", story);
                addStoryFragment.setArguments(bundle);

                // Sử dụng FragmentTransaction để hiển thị AddStoryFragment
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, addStoryFragment);
                transaction.addToBackStack(null); // Quay lại QLTruyenFragment
                transaction.commit();
            }

            @Override
            public void onDeleteStory(int position) {
                deleteStory(position);
            }
        });
        recyclerView.setAdapter(adapter);

        view.findViewById(R.id.fab_add_story).setOnClickListener(v -> addStory());
    }

    private void addStory() {
        int id = storyList.size() + 1;
        Story newStory = new Story(id, "Truyện mới " + id, "Tác giả mới " + id);
        storyList.add(newStory);
        adapter.notifyItemInserted(storyList.size() - 1);
    }

    private void deleteStory(int position) {
        storyList.remove(position);
        adapter.notifyItemRemoved(position);
    }
}
