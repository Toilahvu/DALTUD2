package com.example.daltud2.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daltud2.Control.ComicAdapter;
import com.example.daltud2.Control.DataBaseSQLLite;
import com.example.daltud2.Control.bodyView;
import com.example.daltud2.Control.HeaderView;
import com.example.daltud2.Model.ChuongTruyen;
import com.example.daltud2.Model.Comment;
import com.example.daltud2.Model.Truyen;
import com.example.daltud2.Model.TruyenAddress;
import com.example.daltud2.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //region Variables
    private boolean isNotWhite = true;
    private ComicAdapter adapter;
    private HeaderView headerView;
    private bodyView bodyViewInstance;
    private final List<List<Truyen>> pageDataList = new ArrayList<>();
    private List<Truyen> truyenList = new ArrayList<>();
    private DataBaseSQLLite dataBaseSQLLite;

    private TextView previousPageNumber, tv4;
    private ImageButton btnBackwardStep, btnForwardStep, btnForwardFast, btnBackwardFast;
    private LinearLayout pageNumbersLayout;
    private LinearLayout  body;
    private RecyclerView ListComic;


    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //region Initial Setup
        declareVal();
        dataBaseSQLLite = new DataBaseSQLLite(this);
        returnDataListPage();
        //endregion

        headerView.setHeaderListener(new HeaderView.HeaderListener() {
             @Override
             public void onUIChangeRequested() {
                 if (isNotWhite){
                     ListComic.setBackgroundColor(Color.WHITE);
                     tv4.setTextColor(Color.BLACK);
                     body.setBackgroundColor(Color.WHITE);
                     btnForwardFast.setColorFilter(Color.BLACK);
                     btnBackwardFast.setColorFilter(Color.BLACK);
                     btnForwardStep.setColorFilter(Color.BLACK);
                     btnBackwardStep.setColorFilter(Color.BLACK);
                 }else {
                     ListComic.setBackgroundColor(Color.parseColor("#18191A"));
                     tv4.setTextColor(Color.parseColor("#FFC107"));
                     body.setBackgroundColor(Color.parseColor("#18191A"));
                     btnForwardFast.setColorFilter(Color.WHITE);
                     btnBackwardFast.setColorFilter(Color.WHITE);
                     btnForwardStep.setColorFilter(Color.WHITE);
                     btnBackwardStep.setColorFilter(Color.WHITE);
                 }
                 isNotWhite = !isNotWhite;
             }


            @Override
            public Truyen getOneComic() {
                return getThongTinTruyen("truyen01");
            }

        });


        bodyViewInstance.setDataProvider(new bodyView.dataProvide() {
            @Override
            public List<List<Truyen>> getPageDataList() {
                return pageDataList;
            }
        });
    }

    //region methods
    private void declareVal() {
        headerView = findViewById(R.id.headerView);
        bodyViewInstance = findViewById(R.id.ViewBody);

        btnBackwardStep = findViewById(R.id.btnBackwardStep);
        btnForwardStep = findViewById(R.id.btnForwardStep);
        ListComic = findViewById(R.id.ListComic);
        tv4 = findViewById(R.id.textViewTitle);
        body = findViewById(R.id.body);
        btnForwardFast = findViewById(R.id.btnForwardFast);
        btnBackwardFast = findViewById(R.id.btnBackwardFast);
    }
    //endregion

    //region Database
    private void getAllruyen() {
        if (dataBaseSQLLite == null) {
            dataBaseSQLLite = new DataBaseSQLLite(this, null, null, 1);
        }
        Cursor cursor = dataBaseSQLLite.getAllTruyen(dataBaseSQLLite.getReadableDatabase());
        if (cursor != null) {
            truyenList.clear();
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String idTruyen = cursor.getString(cursor.getColumnIndex("idTruyen"));
                @SuppressLint("Range") String tenTruyen = cursor.getString(cursor.getColumnIndex("tenTruyen"));
                @SuppressLint("Range") String tenTacGia = cursor.getString(cursor.getColumnIndex("tenTacGia"));
                @SuppressLint("Range") int luotXem = cursor.getInt(cursor.getColumnIndex("luotXem"));
                @SuppressLint("Range") int luotTheoDoi = cursor.getInt(cursor.getColumnIndex("luotTheoDoi"));
                @SuppressLint("Range") String ngayPhatHanh = cursor.getString(cursor.getColumnIndex("ngayPhatHanh"));
                @SuppressLint("Range") String moTaTruyen = cursor.getString(cursor.getColumnIndex("moTaTruyen"));
                @SuppressLint("Range") String urlAnhBia = cursor.getString(cursor.getColumnIndex("urlAnhBia"));

                truyenList.add(new Truyen(idTruyen, tenTruyen, tenTacGia, luotXem, luotTheoDoi, ngayPhatHanh, moTaTruyen, urlAnhBia));
            }
            cursor.close();
            Log.d("Truyen Count", "Total Truyen: " + truyenList.size());
        }
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





    private void sortTimeComics() {
        truyenList.sort((truyen1, truyen2) -> truyen2.getNgayPhatHanh().compareTo(truyen1.getNgayPhatHanh()));
        Log.d("SortTimeComics", "Danh sách đã được sắp xếp theo thời gian.");
    }

    private void createListComics(int numItems) {
        pageDataList.clear();
        int startIndex = 0;
        while (startIndex < truyenList.size()) {
            int endIndex = Math.min(startIndex + numItems, truyenList.size());
            pageDataList.add(new ArrayList<>(truyenList.subList(startIndex, endIndex)));
            startIndex += numItems;
        }
    }

    private void createSampleComicsData(int Nums) {
        truyenList.clear();
        for (int i = 1; i <= Nums; i++) {
            String idTruyen = String.format("truyen%03d", i);
            String tenTruyen = "Truyện mẫu " + i;
            String tenTacGia = "Tác giả " + i;
            int luotXem = (int) (Math.random() * 10000);
            int luotTheoDoi = (int) (Math.random() * 1000);
            String ngayPhatHanh = "2023-" + (i % 12 + 1) + "-" + (i % 28 + 1);
            String moTaTruyen = "Đây là mô tả cho truyện mẫu số " + i;
            String urlAnhBia = "/path/to/sample/images/" + idTruyen + ".jpg";

            Truyen truyen = new Truyen(idTruyen, tenTruyen, tenTacGia, luotXem, luotTheoDoi, ngayPhatHanh, moTaTruyen, urlAnhBia);

            truyenList.add(truyen);
        }
    }

    private void returnDataListPage(){
        getAllruyen();
        //createSampleComicsData(400);
        sortTimeComics();
        createListComics(20);
    }

    private void openThongTinTruyen(String idTruyen) {
        // Lấy thông tin truyện
        Truyen truyen = getThongTinTruyen(idTruyen); // Phương thức đã có

        if (truyen != null) {
            // Tạo Intent để mở `ThongTinTruyen`
            Intent intent = new Intent(MainActivity.this, ThongTinTruyen.class);

            // Truyền dữ liệu truyện qua Intent
            intent.putExtra("truyen", (CharSequence) truyen);

            // Truyền danh sách chương qua Intent
            ArrayList<ChuongTruyen> danhSachChuong = new ArrayList<>(truyen.getDanhSachChuong());
            intent.putExtra("danhSachChuong", danhSachChuong);

            // Khởi chạy Activity
            startActivity(intent);
        } else {
            Log.e("MainActivity", "Không tìm thấy thông tin truyện với ID: " + idTruyen);
        }
    }

    //endregion


}




