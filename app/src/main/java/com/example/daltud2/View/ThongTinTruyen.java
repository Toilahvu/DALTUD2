package com.example.daltud2.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    private DataBaseSQLLite dataBaseSQLLite;
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

        String idTruyen = getIntent().getStringExtra("TRUYEN_ID");
        Truyen truyen = getThongTinTruyen(idTruyen);

        Controls();
        Events(truyen);

        initData(truyen);

        btnXemThem.setVisibility(View.GONE); // Ẩn hoàn toàn

        // Nhận dữ liệu từ Intent
        List<TruyenAddress> danhSachTag = truyen.getTagList();

        // Khởi tạo LayoutManager cho rvTag
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        rvTag.setLayoutManager(layoutManager);

        // Khởi tạo Adapter cho rvTag
        tagAdapter = new TagAdapter(danhSachTag);
        rvTag.setAdapter(tagAdapter);


        // Nhận danh sách chap từ Intent
       List<ChuongTruyen> listTruyen = truyen.getDanhSachChuong();

        // Thiết lập Adapter
        listChapTruyenAdapter = new ChapTruyenAdapter(listTruyen);
        rvDanhSachChapTruyen.setLayoutManager(new LinearLayoutManager(this));
        rvDanhSachChapTruyen.setAdapter(listChapTruyenAdapter);

        if (truyen != null) {
            setThongTinTruyen(truyen); // Gắn thông tin truyện vào các TextView
        }

// Nhận danh sách bình luận từ Intent
        List<Comment> listComment = truyen.getCommentList();

// Kiểm tra danh sách bình luận không null
        if (listComment != null) {
            // Hiển thị tổng số bình luận
            tvLuotBinhLuan.setText("(" + listComment.size() + ")");

            CommentAdapter = new CommentAdapter(this, listComment); // Truyền `this` làm Context
            rvDanhSachComment.setLayoutManager(new LinearLayoutManager(this));
            rvDanhSachComment.setAdapter(CommentAdapter);
        } else {
            Log.e("ThongTinTruyen", "Danh sách bình luận bị null");
            tvLuotBinhLuan.setText("(0)");
        }

        capNhatDanhSachBinhLuan(truyen);
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

    private void Events( Truyen truyen ) {
        btnDang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String noiDungBinhLuan = edtBinhLuan.getText().toString().trim();

                if (noiDungBinhLuan.isEmpty()) {
                    Toast.makeText(ThongTinTruyen.this, "Vui lòng nhập nội dung bình luận!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (truyen != null) {
                    DataBaseSQLLite dataBaseSQLLite = new DataBaseSQLLite(ThongTinTruyen.this);
                    dataBaseSQLLite.themBinhLuanMoi(truyen.getIdTruyen(), noiDungBinhLuan);

                    Toast.makeText(ThongTinTruyen.this, "Đã thêm bình luận thành công!", Toast.LENGTH_SHORT).show();

                    capNhatDanhSachBinhLuan(truyen ); // Tải lại danh sách
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

    private void capNhatDanhSachBinhLuan( Truyen truyen ) {
        DataBaseSQLLite dbHelper = new DataBaseSQLLite(this);
        List<Comment> danhSachBinhLuan = new ArrayList<>();


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

    private Truyen getThongTinTruyen(String idTruyen) {
        if (dataBaseSQLLite == null) {
            dataBaseSQLLite = new DataBaseSQLLite(this);
        }

        SQLiteDatabase db = dataBaseSQLLite.getReadableDatabase();
        Truyen truyen = null;

        // Lấy thông tin cơ bản của truyện và chương
        Cursor cursor = dataBaseSQLLite.getThongTinTruyenVaChapter(db, idTruyen);
        List<ChuongTruyen> chuongTruyenList = new ArrayList<>();

        if (cursor != null) {
            boolean isFirstRow = true;
            while (cursor.moveToNext()) {
                if (isFirstRow) {
                    // Lấy thông tin cơ bản của truyện
                    @SuppressLint("Range") String tenTruyen = cursor.getString(cursor.getColumnIndex("tenTruyen"));
                    @SuppressLint("Range") String tenTacGia = cursor.getString(cursor.getColumnIndex("tenTacGia"));
                    @SuppressLint("Range") int luotXem = cursor.getInt(cursor.getColumnIndex("luotXem"));
                    @SuppressLint("Range") int luotTheoDoi = cursor.getInt(cursor.getColumnIndex("luotTheoDoi"));
                    @SuppressLint("Range") String ngayPhatHanh = cursor.getString(cursor.getColumnIndex("ngayPhatHanh"));
                    @SuppressLint("Range") String moTaTruyen = cursor.getString(cursor.getColumnIndex("moTaTruyen"));
                    @SuppressLint("Range") String urlAnhBia = cursor.getString(cursor.getColumnIndex("urlAnhBia"));

                    truyen = new Truyen(idTruyen, tenTruyen, tenTacGia, luotXem, luotTheoDoi, ngayPhatHanh, moTaTruyen, urlAnhBia);
                    isFirstRow = false;
                }

                // Lấy thông tin chương
                @SuppressLint("Range") String idChapter = cursor.getString(cursor.getColumnIndex("idChapter"));
                @SuppressLint("Range") int chuongSo = cursor.getInt(cursor.getColumnIndex("chuongSo"));
                @SuppressLint("Range") String ngayPhatHanhChapter = cursor.getString(cursor.getColumnIndex("ngayPhatHanh"));

                if (idChapter != null) {
                    ChuongTruyen chuongTruyen = new ChuongTruyen(idChapter, idTruyen, chuongSo, ngayPhatHanhChapter);
                    if (!chuongTruyenList.contains(chuongTruyen)) { // Kiểm tra trùng lặp
                        chuongTruyenList.add(chuongTruyen);
                    }
                }
            }
            cursor.close();
        }

        // Lấy danh sách tag của truyện
        Cursor tagCursor = dataBaseSQLLite.getTagForTruyen(db, idTruyen);
        List<TruyenAddress> tagList = new ArrayList<>();
        if (tagCursor != null) {
            while (tagCursor.moveToNext()) {
                @SuppressLint("Range") String tenTag = tagCursor.getString(tagCursor.getColumnIndex("tenTag"));
                TruyenAddress truyenAddress = new TruyenAddress(idTruyen, tenTag);
                if (!tagList.contains(truyenAddress)) { // Kiểm tra trùng lặp
                    tagList.add(truyenAddress);
                }
            }
            tagCursor.close();
        }

        // Lấy danh sách bình luận
        Cursor commentCursor = dataBaseSQLLite.getCommentsWithUserNamesByTruyen(db, idTruyen);
        List<Comment> commentList = new ArrayList<>();
        if (commentCursor != null) {
            while (commentCursor.moveToNext()) {
                @SuppressLint("Range") String idComment = commentCursor.getString(commentCursor.getColumnIndex("idComment"));
                @SuppressLint("Range") String idUser = commentCursor.getString(commentCursor.getColumnIndex("idUser"));
                @SuppressLint("Range") String tenUser = commentCursor.getString(commentCursor.getColumnIndex("tenUser"));
                @SuppressLint("Range") String noiDungBinhLuan = commentCursor.getString(commentCursor.getColumnIndex("noiDungBinhLuan"));
                @SuppressLint("Range") String thoiGianBinhLuan = commentCursor.getString(commentCursor.getColumnIndex("thoiGianBinhLuan"));

                Comment comment = new Comment(idComment, idTruyen, idUser, noiDungBinhLuan, thoiGianBinhLuan, tenUser);
                commentList.add(comment);
            }
            commentCursor.close();
        }

        // Gán danh sách chương, tag và bình luận vào đối tượng `Truyen`
        if (truyen != null) {
            truyen.setDanhSachChuong(chuongTruyenList);
            truyen.setTagList(tagList);
            truyen.setCommentList(commentList);
        }

        db.close();
        return truyen;
    }

    private void initData(Truyen truyen) {
            List<ChuongTruyen> chapList = truyen.getDanhSachChuong();
            ChapTruyenAdapter chapTruyenAdapter = new ChapTruyenAdapter(chapList);

            chapTruyenAdapter.setOnItemClickListener(idChapter -> {
                Log.d("ThongTinTruyen", "Clicked chapter ID: " + idChapter);
                // Tạo Intent để mở Activity khác
                Intent intent = new Intent(this, TrinhDocTruyen.class);

                // Truyền idChapter qua Intent
                intent.putExtra("idChapter", idChapter);

                // Khởi chạy Activity
                startActivity(intent);
            });

            rvDanhSachChapTruyen.setAdapter(chapTruyenAdapter);
    }

}