package com.example.daltud2.View;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daltud2.Control.ChapTruyenAdapter;
import com.example.daltud2.Control.DataBaseSQLLite;
import com.example.daltud2.Control.TagAdapter;
import com.example.daltud2.Model.ChuongTruyen;
import com.example.daltud2.Model.Comment;
import com.example.daltud2.Model.Truyen;
import com.example.daltud2.Model.TruyenAddress;
import com.example.daltud2.R;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.example.daltud2.Control.CommentAdapter;
import java.util.ArrayList;
import java.util.List;

public class ThongTinTruyen extends AppCompatActivity {
    private Button btnDocChapDau, btnTheoDoi, btnThich, btnDang, btnXemThem;
    private TextView tvDuongDanHienTai, tvTenTruyen, tvTinhTrang, tvLuotThich, tvLuotTheoDoi, tvLuotXem, tvGioiThieu, tvLuotBinhLuan, tvTenTacGia;
    private RecyclerView rvTag, rvDanhSachChapTruyen, rvDanhSachComment;
    private EditText edtBinhLuan;
    private ImageView imgTruyen;
    private RecyclerView.Adapter tagAdapter, listChapTruyenAdapter, CommentAdapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thong_tin_truyen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Controls();
        Events();

        btnXemThem.setVisibility(View.GONE); // Ẩn hoàn toàn

        // Nhận dữ liệu từ Intent
        ArrayList<TruyenAddress> danhSachTag = (ArrayList<TruyenAddress>) getIntent().getSerializableExtra("danhSachTag");

        // Khởi tạo LayoutManager cho rvTag
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        rvTag.setLayoutManager(layoutManager);

        // Khởi tạo Adapter cho rvTag
        tagAdapter = new TagAdapter(danhSachTag);
        rvTag.setAdapter(tagAdapter);


        // Nhận danh sách chap từ Intent
        List<ChuongTruyen> listTruyen = (ArrayList<ChuongTruyen>) getIntent().getSerializableExtra("danhSachChuong");

        // Thiết lập Adapter
        listChapTruyenAdapter = new ChapTruyenAdapter(listTruyen);
        rvDanhSachChapTruyen.setLayoutManager(new LinearLayoutManager(this));
        rvDanhSachChapTruyen.setAdapter(listChapTruyenAdapter);


        // Nhan ds info truyen
        Truyen truyen = (Truyen) getIntent().getSerializableExtra("truyen");
        if (truyen != null) {
            setThongTinTruyen(truyen); // Gắn thông tin truyện vào các TextView
        }

// Nhận danh sách bình luận từ Intent
        ArrayList<Comment> listComment = (ArrayList<Comment>) getIntent().getSerializableExtra("danhSachComment");

// Kiểm tra danh sách bình luận không null
        if (listComment != null) {
            // Hiển thị tổng số bình luận
            tvLuotBinhLuan.setText("(" + listComment.size() + ")");

            CommentAdapter commentAdapter = new CommentAdapter(this, listComment); // Truyền `this` làm Context
            rvDanhSachComment.setLayoutManager(new LinearLayoutManager(this));
            rvDanhSachComment.setAdapter(commentAdapter);
        } else {
            Log.e("ThongTinTruyen", "Danh sách bình luận bị null");
            tvLuotBinhLuan.setText("(0)");
        }

        capNhatDanhSachBinhLuan();
    }

    private void Controls() {
        btnDocChapDau = findViewById(R.id.btnDocChapDau);
        btnTheoDoi = findViewById(R.id.btnTheoDoi);
        btnThich = findViewById(R.id.btnThich);
        btnDang = findViewById(R.id.btnDang);
        btnXemThem = findViewById(R.id.btnXemThem);

        tvDuongDanHienTai = findViewById(R.id.tvDuongDanHienTai);
        tvTenTruyen = findViewById(R.id.tvTenTruyen);
        tvTinhTrang = findViewById(R.id.tvTinhTrang);
        tvLuotThich = findViewById(R.id.tvLuotThich);
        tvLuotTheoDoi = findViewById(R.id.tvLuotTheoDoi);
        tvLuotXem = findViewById(R.id.tvLuotXem);
        tvGioiThieu = findViewById(R.id.tvGioiThieu);
        tvLuotBinhLuan = findViewById(R.id.tvLuotBinhLuan);
        tvTenTacGia = findViewById(R.id.tvTenTacGia);

        edtBinhLuan = findViewById(R.id.edtBinhLuan);

        rvTag = findViewById(R.id.rvTag);
        rvDanhSachChapTruyen = findViewById(R.id.rvDanhSachChapTruyen);
        rvDanhSachComment = findViewById(R.id.rvDanhSachComment);

        imgTruyen = findViewById(R.id.imgTruyen);
    }

    private void Events() {
        btnDang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String noiDungBinhLuan = edtBinhLuan.getText().toString().trim();

                if (noiDungBinhLuan.isEmpty()) {
                    Toast.makeText(ThongTinTruyen.this, "Vui lòng nhập nội dung bình luận!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Truyen truyen = (Truyen) getIntent().getSerializableExtra("truyen");
                if (truyen != null) {
                    DataBaseSQLLite dataBaseSQLLite = new DataBaseSQLLite(ThongTinTruyen.this);
                    dataBaseSQLLite.themBinhLuanMoi(truyen.getIdTruyen(), noiDungBinhLuan);

                    Toast.makeText(ThongTinTruyen.this, "Đã thêm bình luận thành công!", Toast.LENGTH_SHORT).show();

                    capNhatDanhSachBinhLuan(); // Tải lại danh sách
                    edtBinhLuan.setText(""); // Xóa nội dung nhập
                } else {
                    Toast.makeText(ThongTinTruyen.this, "Không tìm thấy thông tin truyện", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Sự kiện cho nút "Xem thêm"
        btnXemThem.setOnClickListener(view -> {
            // Lấy danh sách đầy đủ từ RecyclerView tag
            ArrayList<Comment> danhSachBinhLuan = (ArrayList<Comment>) rvDanhSachComment.getTag();

            if (danhSachBinhLuan != null && !danhSachBinhLuan.isEmpty()) {
                // Cập nhật Adapter với toàn bộ bình luận
                CommentAdapter adapter = new CommentAdapter(ThongTinTruyen.this, danhSachBinhLuan);
                adapter.setLimitComments(danhSachBinhLuan.size());
                rvDanhSachComment.setAdapter(adapter);
                // Ẩn nút "Xem thêm"
                btnXemThem.setVisibility(View.GONE);
            } else {
                Toast.makeText(ThongTinTruyen.this, "Không có thêm bình luận nào để hiển thị!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void capNhatDanhSachBinhLuan() {
        DataBaseSQLLite dbHelper = new DataBaseSQLLite(this);
        ArrayList<Comment> danhSachBinhLuan = new ArrayList<>();
        Truyen truyen = (Truyen) getIntent().getSerializableExtra("truyen");

        if (truyen == null) {
            Log.e("capNhatDanhSachBinhLuan", "Không tìm thấy thông tin truyện");
            return;
        }

        Cursor cursor = dbHelper.getCommentsWithUserNamesByTruyen(dbHelper.getReadableDatabase(), truyen.getIdTruyen());
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String idComment = cursor.getString(cursor.getColumnIndex("idComment"));
                @SuppressLint("Range") String idUser = cursor.getString(cursor.getColumnIndex("idUser"));
                @SuppressLint("Range") String tenUser = cursor.getString(cursor.getColumnIndex("tenUser"));
                @SuppressLint("Range") String noiDung = cursor.getString(cursor.getColumnIndex("noiDungBinhLuan"));
                @SuppressLint("Range") String thoiGian = cursor.getString(cursor.getColumnIndex("thoiGianBinhLuan"));

                danhSachBinhLuan.add(new Comment(idComment, truyen.getIdTruyen(), idUser, noiDung, thoiGian, tenUser));
            }
            cursor.close();
        }

        // Hiển thị tối đa 5 bình luận ban đầu
        int limit = Math.min(5, danhSachBinhLuan.size());
        ArrayList<Comment> danhSachHienThi = new ArrayList<>(danhSachBinhLuan.subList(0, limit));

        // Gán danh sách hiện tại và đầy đủ vào Adapter
        CommentAdapter adapter = new CommentAdapter(this, danhSachBinhLuan);
        adapter.setLimitComments(limit);
        rvDanhSachComment.setAdapter(adapter);

        // Lưu trữ danh sách đầy đủ vào RecyclerView tag
        rvDanhSachComment.setTag(danhSachBinhLuan);

        // Hiển thị nút "Xem thêm" nếu có nhiều hơn 5 bình luận
        btnXemThem.setVisibility(danhSachBinhLuan.size() > 5 ? View.VISIBLE : View.GONE);

        // Cập nhật tổng số bình luận
        tvLuotBinhLuan.setText("(" + danhSachBinhLuan.size() + ")");
    }


    private void setThongTinTruyen(Truyen truyen) {
        tvTenTruyen.setText(truyen.getTenTruyen());
        tvTenTacGia.setText(truyen.getTenTacGia());
        tvTinhTrang.setText("Đang cập nhật"); // Nếu có trường trạng thái, sửa lại
        tvLuotThich.setText(String.valueOf(0)); // Nếu có số lượt thích, sửa lại
        tvLuotTheoDoi.setText(String.valueOf(truyen.getSoLuotTheoDoi()));
        tvLuotXem.setText(String.valueOf(truyen.getSoLuotXem()));
        tvGioiThieu.setText(truyen.getMoTaTruyen());
        tvDuongDanHienTai.setText("Trang chủ > " + truyen.getTenTruyen());
    }
}