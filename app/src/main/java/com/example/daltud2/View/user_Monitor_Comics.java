package com.example.daltud2.View;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
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
import com.example.daltud2.Model.Truyen;
import com.example.daltud2.R;

import java.util.ArrayList;
import java.util.List;

public class user_Monitor_Comics extends AppCompatActivity {

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

    private String userIdDemo = "user02";
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
        //endregion

        createSampleData(20);

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
        Cursor cursor = dataBaseSQLLite.userMonitor(dataBaseSQLLite.getReadableDatabase(),userIdDemo);
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

    private void sortTimeComics() {
        truyenList.sort((truyen1, truyen2) -> truyen2.getNgayPhatHanh().compareTo(truyen1.getNgayPhatHanh()));
        Log.d("SortTimeComics", "Danh sách đã được sắp xếp theo thời gian.");
    }

    private void createSampleData(int numItems) {
        getAllruyen();
        sortTimeComics();
        pageDataList.clear();
        int startIndex = 0;
        while (startIndex < truyenList.size()) {
            int pageSize = 20;
            int endIndex = Math.min(startIndex + pageSize, truyenList.size());
            pageDataList.add(new ArrayList<>(truyenList.subList(startIndex, endIndex)));
            startIndex += pageSize;
        }
    }

    private Truyen getThongTinTruyen(String idTruyen) {
        if (dataBaseSQLLite == null) {
            dataBaseSQLLite = new DataBaseSQLLite(this);
        }
        SQLiteDatabase db = dataBaseSQLLite.getReadableDatabase();

        Cursor cursor = dataBaseSQLLite.getThongTinTruyenVaChapter(db, idTruyen);
        Truyen truyen = null; // Đối tượng Truyen để lưu thông tin
        List<ChuongTruyen> chuongTruyenList = new ArrayList<>(); // Danh sách các chương của truyện

        if (cursor != null) {
            boolean isFirstRow = true; // Kiểm tra dòng đầu tiên (thông tin truyện)
            while (cursor.moveToNext()) {
                if (isFirstRow) {
                    // Lấy thông tin truyện (chỉ lấy 1 lần ở dòng đầu tiên)
                    @SuppressLint("Range") String tenTruyen = cursor.getString(cursor.getColumnIndex("tenTruyen"));
                    @SuppressLint("Range") String tenTacGia = cursor.getString(cursor.getColumnIndex("tenTacGia"));
                    @SuppressLint("Range") int luotXem = cursor.getInt(cursor.getColumnIndex("luotXem"));
                    @SuppressLint("Range") int luotTheoDoi = cursor.getInt(cursor.getColumnIndex("luotTheoDoi"));
                    @SuppressLint("Range") String ngayPhatHanh = cursor.getString(cursor.getColumnIndex("ngayPhatHanh"));
                    @SuppressLint("Range") String moTaTruyen = cursor.getString(cursor.getColumnIndex("moTaTruyen"));
                    @SuppressLint("Range") String urlAnhBia = cursor.getString(cursor.getColumnIndex("urlAnhBia"));

                    // Tạo đối tượng Truyen
                    truyen = new Truyen(idTruyen, tenTruyen, tenTacGia, luotXem, luotTheoDoi, ngayPhatHanh, moTaTruyen, urlAnhBia);

                    isFirstRow = false; // Đánh dấu đã xử lý thông tin truyện
                }

                // Lấy thông tin chương (các dòng tiếp theo)
                @SuppressLint("Range") String idChapter = cursor.getString(cursor.getColumnIndex("idChapter"));
                @SuppressLint("Range") int chuongSo = cursor.getInt(cursor.getColumnIndex("chuongSo"));
                @SuppressLint("Range") String ngayPhatHanhChapter = cursor.getString(cursor.getColumnIndex("ngayPhatHanh"));

                // Tạo đối tượng ChuongTruyen và thêm vào danh sách
                ChuongTruyen chuongTruyen = new ChuongTruyen(idChapter, idTruyen, chuongSo, ngayPhatHanhChapter);
                chuongTruyenList.add(chuongTruyen);
            }
            cursor.close();
        } else {
            Log.e("ThongTinTruyen", "Không tìm thấy thông tin cho truyện với ID: " + idTruyen);
        }

        db.close();

        return truyen;
    }
    //endregion
}




