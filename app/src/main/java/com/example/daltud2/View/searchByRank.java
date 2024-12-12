package com.example.daltud2.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daltud2.Control.ComicAdapter;
import com.example.daltud2.Control.DataBaseSQLLite;
import com.example.daltud2.Control.GenericCustomSpinnerAdapter;
import com.example.daltud2.Control.HeaderView;
import com.example.daltud2.Control.bodyView;
import com.example.daltud2.Model.ChuongTruyen;
import com.example.daltud2.Model.Comic;
import com.example.daltud2.Model.Truyen;
import com.example.daltud2.Model.tagComics;
import com.example.daltud2.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class searchByRank extends AppCompatActivity {

    //region variables
    private HeaderView headerView;
    private bodyView bodyView;
    private boolean isNotWhite = true;
    private LinearLayout mainSearchByRank;
    private TextView tvRank;
    private TextView SortRankpinnerLabel;
    private Spinner sortRankpinner;


    private TextView previousPageNumber, tv4;
    private ImageButton btnBackwardStep, btnForwardStep, btnForwardFast, btnBackwardFast;
    private LinearLayout pageNumbersLayout;
    private LinearLayout  body;
    private RecyclerView ListComic;

    private final List<List<Truyen>> pageDataList = new ArrayList<>();
    List<String> sortRank = Arrays.asList("Top Ngày", "Top Tuần", "Top Tháng");
    private GenericCustomSpinnerAdapter rankComicsAdapter;

    private DataBaseSQLLite dataBaseSQLLite;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_by_rank);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        declareVal();
        rankComicsAdapter = new GenericCustomSpinnerAdapter(this,R.layout.item_selected,sortRank);
        sortRankpinner.setAdapter(rankComicsAdapter);
        ComicList(getDatabaseEveryDayTrends(),20);

        headerView.setHeaderListener(new HeaderView.HeaderListener() {
            @Override
            public void onUIChangeRequested() {

                if (isNotWhite) {
                    mainSearchByRank.setBackgroundColor(Color.WHITE);
                    bodyView.setBackgroundColor(Color.WHITE);
                    tvRank.setTextColor(Color.BLACK);
                    SortRankpinnerLabel.setTextColor(Color.BLACK);

                    ListComic.setBackgroundColor(Color.WHITE);
                    tv4.setTextColor(Color.BLACK);
                    body.setBackgroundColor(Color.WHITE);
                    btnForwardFast.setColorFilter(Color.BLACK);
                    btnBackwardFast.setColorFilter(Color.BLACK);
                    btnForwardStep.setColorFilter(Color.BLACK);
                    btnBackwardStep.setColorFilter(Color.BLACK);
                } else {
                    mainSearchByRank.setBackgroundColor(Color.parseColor("#18191A"));
                    bodyView.setBackgroundColor(Color.parseColor("#18191A"));
                    tvRank.setTextColor(Color.parseColor("#FFC107"));
                    SortRankpinnerLabel.setTextColor(Color.parseColor("#FFC107"));

                    ListComic.setBackgroundColor(Color.parseColor("#18191A"));
                    tv4.setTextColor(Color.parseColor("#FFC107"));
                    body.setBackgroundColor(Color.parseColor("#18191A"));
                    btnForwardFast.setColorFilter(Color.WHITE);
                    btnBackwardFast.setColorFilter(Color.WHITE);
                    btnForwardStep.setColorFilter(Color.WHITE);
                    btnBackwardStep.setColorFilter(Color.WHITE);
                }
                isNotWhite = !isNotWhite;

                rankComicsAdapter.setTheme(isNotWhite);
                sortRankpinner.invalidate();

            }



        });

        sortRankpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedOption = sortRank.get(i);
                tvRank.setText(selectedOption);
                switch (selectedOption) {
                    case "Top Ngày":
                        ComicList(getDatabaseEveryDayTrends(), 20);
                        break;
                    case "Top Tuần":
                        ComicList(getDatabaseEveryMonthTrends(), 20);
                        break;
                    case "Top Tháng":
                        ComicList(getDatabaseEveryYearTrends(), 20);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        bodyView.setDataProvider(new bodyView.dataProvide() {
            @Override
            public List<List<Truyen>> getPageDataList() {
                return pageDataList;
            }
        });
        bodyView.setFragmentManager(getSupportFragmentManager());


    }

    //region method
    private void declareVal(){
        headerView = findViewById(R.id.headerViewByRank);
        bodyView = findViewById(R.id.BodyViewByRank);
        mainSearchByRank = findViewById(R.id.main);
        tvRank = findViewById(R.id.tv_Rank);
        sortRankpinner = findViewById(R.id.spinnerSortRank);
        SortRankpinnerLabel = findViewById(R.id.SortRankpinnerLabel);

        btnBackwardStep = findViewById(R.id.btnBackwardStep);
        btnForwardStep = findViewById(R.id.btnForwardStep);
        ListComic = findViewById(R.id.ListComic);
        tv4 = findViewById(R.id.textViewTitle);
        body = findViewById(R.id.body);
        btnForwardFast = findViewById(R.id.btnForwardFast);
        btnBackwardFast = findViewById(R.id.btnBackwardFast);
    }

    private List<Truyen> getDatabaseEveryDayTrends() {
        List<Truyen> truyenList = new ArrayList<>();
        if (dataBaseSQLLite == null) {
            dataBaseSQLLite = new DataBaseSQLLite(this, null, null, 1);
        }
        @SuppressLint("SimpleDateFormat")
        String ngayDoc = "21-11-2024";//new java.text.SimpleDateFormat("dd-MM-yyyy").format(new java.util.Date());

        Cursor cursor = dataBaseSQLLite.danhSachTruyenDocNhieuTrongNgay(dataBaseSQLLite.getReadableDatabase(), ngayDoc);
        if (cursor != null) {

            while (cursor.moveToNext()) {
                @SuppressLint("Range") String idTruyen = cursor.getString(cursor.getColumnIndex("idTruyen"));
                @SuppressLint("Range") String tenTruyen = cursor.getString(cursor.getColumnIndex("tenTruyen"));
                @SuppressLint("Range") String tenTacGia = cursor.getString(cursor.getColumnIndex("tenTacGia"));
                @SuppressLint("Range") int luotXem = cursor.getInt(cursor.getColumnIndex("luotXem"));
                @SuppressLint("Range") int luotTheoDoi = cursor.getInt(cursor.getColumnIndex("luotTheoDoi"));
                @SuppressLint("Range") String ngayPhatHanh = cursor.getString(cursor.getColumnIndex("ngayPhatHanh"));
                @SuppressLint("Range") String moTaTruyen = cursor.getString(cursor.getColumnIndex("moTaTruyen"));
                @SuppressLint("Range") String urlAnhBia = cursor.getString(cursor.getColumnIndex("urlAnhBia"));

                //@SuppressLint("Range") int tongSoLanDoc = cursor.getInt(cursor.getColumnIndex("tongSoLanDoc"));

                Truyen truyen = new Truyen(idTruyen, tenTruyen, tenTacGia, luotXem, luotTheoDoi, ngayPhatHanh, moTaTruyen, urlAnhBia);
                //truyen.setTongSoLanDoc(tongSoLanDoc); // Gán tổng số lần đọc
                truyenList.add(truyen);
            }
            cursor.close();
            Log.d("Truyen Count", "Total Truyen (Today): " + truyenList.size());
        }
        return truyenList;
    }

    private List<Truyen> getDatabaseEveryMonthTrends() {
        List<Truyen> truyenList = new ArrayList<>();
        if (dataBaseSQLLite == null) {
            dataBaseSQLLite = new DataBaseSQLLite(this, null, null, 1);
        }
        @SuppressLint("SimpleDateFormat")
        String thang ="11";// new java.text.SimpleDateFormat("MM").format(new java.util.Date());
        String nam = "2024";//new java.text.SimpleDateFormat("yyyy").format(new java.util.Date());

        Cursor cursor = dataBaseSQLLite.danhSachTruyenDocNhieuTrongThang(dataBaseSQLLite.getReadableDatabase(), thang, nam);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String idTruyen = cursor.getString(cursor.getColumnIndex("idTruyen"));
                @SuppressLint("Range") String tenTruyen = cursor.getString(cursor.getColumnIndex("tenTruyen"));
                @SuppressLint("Range") String tenTacGia = cursor.getString(cursor.getColumnIndex("tenTacGia"));
                @SuppressLint("Range") int luotXem = cursor.getInt(cursor.getColumnIndex("luotXem"));
                @SuppressLint("Range") int luotTheoDoi = cursor.getInt(cursor.getColumnIndex("luotTheoDoi"));
                @SuppressLint("Range") String ngayPhatHanh = cursor.getString(cursor.getColumnIndex("ngayPhatHanh"));
                @SuppressLint("Range") String moTaTruyen = cursor.getString(cursor.getColumnIndex("moTaTruyen"));
                @SuppressLint("Range") String urlAnhBia = cursor.getString(cursor.getColumnIndex("urlAnhBia"));

                //@SuppressLint("Range") int tongSoLanDoc = cursor.getInt(cursor.getColumnIndex("tongSoLanDoc"));

                Truyen truyen = new Truyen(idTruyen, tenTruyen, tenTacGia, luotXem, luotTheoDoi, ngayPhatHanh, moTaTruyen, urlAnhBia);
                //truyen.setTongSoLanDoc(tongSoLanDoc); // Gán tổng số lần đọc
                truyenList.add(truyen);
            }
            cursor.close();
            Log.d("Truyen Count", "Total Truyen (Month): " + truyenList.size());
        }
        return truyenList;
    }

    private List<Truyen> getDatabaseEveryYearTrends() {
        List<Truyen> truyenList = new ArrayList<>();
        if (dataBaseSQLLite == null) {
            dataBaseSQLLite = new DataBaseSQLLite(this, null, null, 1);
        }
        @SuppressLint("SimpleDateFormat")
        String nam = "2024";// new java.text.SimpleDateFormat("yyyy").format(new java.util.Date());

        Cursor cursor = dataBaseSQLLite.danhSachTruyenDocNhieuTrongNam(dataBaseSQLLite.getReadableDatabase(), nam);
        if (cursor != null) {

            while (cursor.moveToNext()) {
                @SuppressLint("Range") String idTruyen = cursor.getString(cursor.getColumnIndex("idTruyen"));
                @SuppressLint("Range") String tenTruyen = cursor.getString(cursor.getColumnIndex("tenTruyen"));
                @SuppressLint("Range") String tenTacGia = cursor.getString(cursor.getColumnIndex("tenTacGia"));
                @SuppressLint("Range") int luotXem = cursor.getInt(cursor.getColumnIndex("luotXem"));
                @SuppressLint("Range") int luotTheoDoi = cursor.getInt(cursor.getColumnIndex("luotTheoDoi"));
                @SuppressLint("Range") String ngayPhatHanh = cursor.getString(cursor.getColumnIndex("ngayPhatHanh"));
                @SuppressLint("Range") String moTaTruyen = cursor.getString(cursor.getColumnIndex("moTaTruyen"));
                @SuppressLint("Range") String urlAnhBia = cursor.getString(cursor.getColumnIndex("urlAnhBia"));

                //@SuppressLint("Range") int tongSoLanDoc = cursor.getInt(cursor.getColumnIndex("tongSoLanDoc"));

                Truyen truyen = new Truyen(idTruyen, tenTruyen, tenTacGia, luotXem, luotTheoDoi, ngayPhatHanh, moTaTruyen, urlAnhBia);
                //truyen.setTongSoLanDoc(tongSoLanDoc); // Gán tổng số lần đọc
                truyenList.add(truyen);
            }
            cursor.close();
            Log.d("Truyen Count", "Total Truyen (Year): " + truyenList.size());
        }
        return truyenList;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void ComicList(List<Truyen> truyenList,int nums ){
        createListComics(nums,truyenList);

        //khi chạy lần đầu thì nó sẽ ko sự thay đổi , nên chắc chắn lỗi, làm thêm cái kiểm tra
        if (ListComic.getAdapter() != null) {
            ((ComicAdapter) ListComic.getAdapter()).updateData(truyenList);
        }
    }

    private void createListComics(int numItems,List<Truyen> truyenList) {
        pageDataList.clear();
        int startIndex = 0;
        while (startIndex < truyenList.size()) {
            int endIndex = Math.min(startIndex + numItems, truyenList.size());
            pageDataList.add(new ArrayList<>(truyenList.subList(startIndex, endIndex)));
            startIndex += numItems;
        }
        System.out.println("Số trang: " + pageDataList.size());
    }

    private void createSampleComicsData(int Nums) {
         List<Truyen> listTruyen = new ArrayList<>();
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

            listTruyen.add(truyen);
        }
        createListComics(20,listTruyen);

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