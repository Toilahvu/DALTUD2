package com.example.daltud2;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class AddStoryFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;  // Mã yêu cầu để chọn hình ảnh
    private EditText storyTitle, storyAuthor;
    private ImageView storyImage;
    private ListView chapterListView;
    private List<String> chapterList;

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
        ImageButton btnBack = view.findViewById(R.id.btnBack);
        ImageButton btnAddChapter = view.findViewById(R.id.btnAddChapter);

        // Khởi tạo dữ liệu ví dụ cho ListView
        chapterList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            chapterList.add("Chapter " + i);
        }

        // Tạo ArrayAdapter và gắn vào ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, chapterList);
        chapterListView.setAdapter(adapter);

        // Xử lý sự kiện thêm chapter
        btnAddChapter.setOnClickListener(v -> {
            int nextChapterNumber = chapterList.size() + 1;
            chapterList.add("Chapter " + nextChapterNumber);
            adapter.notifyDataSetChanged();
        });

        // Xử lý sự kiện thay đổi hình ảnh
        btnChangeImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        // Xử lý sự kiện quay lại
        btnBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        // Long click listener để hiển thị thông báo xóa
        chapterListView.setOnItemLongClickListener((parent, view1, position, id) -> {
            String chapter = chapterList.get(position);

            // Tạo hộp thoại xác nhận xóa
            new AlertDialog.Builder(requireContext())
                    .setTitle("Xóa chương?")
                    .setMessage("Bạn có chắc chắn muốn xóa chương: " + chapter + " không?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        // Nếu người dùng chọn "Có", xóa mục khỏi danh sách
                        chapterList.remove(position);

                        // Cập nhật lại số thứ tự các chương
                        for (int i = 0; i < chapterList.size(); i++) {
                            chapterList.set(i, "Chapter " + (i + 1)); // Cập nhật lại số thứ tự
                        }

                        // Notify adapter cập nhật dữ liệu
                        adapter.notifyDataSetChanged();

                        // Hiển thị thông báo xóa thành công
                        Toast.makeText(requireContext(), "Đã xóa " + chapter, Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Không", (dialog, which) -> dialog.dismiss())
                    .show(); // Hiển thị hộp thoại

            return true;  // Trả về true để ngừng xử lý thêm sự kiện này
        });

    }

    // Nhận kết quả chọn hình ảnh từ thư viện
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK && requestCode == PICK_IMAGE_REQUEST && data != null) {
            Uri selectedImageUri = data.getData();
            storyImage.setImageURI(selectedImageUri);  // Cập nhật hình ảnh trong ImageView
            Toast.makeText(requireContext(), "Hình ảnh đã được thay đổi", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "Không chọn hình ảnh nào", Toast.LENGTH_SHORT).show();
        }
    }
}
