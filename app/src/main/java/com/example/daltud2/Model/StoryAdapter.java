package com.example.daltud2.Model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daltud2.Model.Story;
import com.example.daltud2.R;

import java.util.List;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryViewHolder> {

    private final List<Story> storyList;
    private final OnStoryClickListener listener;

    public interface OnStoryClickListener {
        void onEditStory(int position, Story story);

        void onEditStory(int position);

        void onDeleteStory(int position);
    }

    public StoryAdapter(List<Story> storyList, OnStoryClickListener listener) {
        this.storyList = storyList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_story, parent, false);
        return new StoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, int position) {
        Story story = storyList.get(position);
        holder.bind(story, position);
    }

    @Override
    public int getItemCount() {
        return storyList.size();
    }

    class StoryViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle, tvAuthor;
        private final Button btnEdit, btnDelete;

        public StoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }

        public void bind(final Story story, final int position) {
            tvTitle.setText(story.getTitle());
            tvAuthor.setText(story.getAuthor());

            btnEdit.setOnClickListener(v -> listener.onEditStory(position, story));
            btnDelete.setOnClickListener(v -> listener.onDeleteStory(position));
        }
    }
}
