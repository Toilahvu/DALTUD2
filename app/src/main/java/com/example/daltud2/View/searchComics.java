package com.example.daltud2.View;

import android.annotation.SuppressLint;
import android.database.Cursor;
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

import com.example.daltud2.Control.DataBaseSQLLite;
import com.example.daltud2.Control.HeaderView;
import com.example.daltud2.Control.bodyView;
import com.example.daltud2.Model.Comic;
import com.example.daltud2.Model.Truyen;
import com.example.daltud2.R;

import java.util.ArrayList;
import java.util.List;

public class searchComics extends AppCompatActivity {

    private LinearLayout mainSearchByTag;
    private HeaderView headerView;
    private bodyView bodyViewByTag;
    private TextView tv_Notice;

    private TextView previousPageNumber, tv4;
    private ImageButton btnBackwardStep, btnForwardStep, btnForwardFast, btnBackwardFast;
    private LinearLayout pageNumbersLayout;
    private LinearLayout  body;
    private RecyclerView ListComic;

    private final List<List<Truyen>> pageDataList = new ArrayList<>();
    private List<Truyen> truyenList = new ArrayList<>();

    private boolean isNotWhite = true;

    private DataBaseSQLLite dataBaseSQLLite;

    private int numsItem = 20;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_comics);
        String searchQuery = getIntent().getStringExtra("comicSearch");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        declareVal();
        getComicSearch(searchQuery);
        createPage(numsItem);

        headerView.setHeaderListener(new HeaderView.HeaderListener() {
            @Override
            public void onUIChangeRequested() {
                if (isNotWhite) {
                    mainSearchByTag.setBackgroundColor(Color.WHITE);
                    bodyViewByTag.setBackgroundColor(Color.WHITE);
                    tv_Notice.setTextColor(Color.BLACK);

                    ListComic.setBackgroundColor(Color.WHITE);
                    tv4.setTextColor(Color.BLACK);
                    body.setBackgroundColor(Color.WHITE);
                    btnForwardFast.setColorFilter(Color.BLACK);
                    btnBackwardFast.setColorFilter(Color.BLACK);
                    btnForwardStep.setColorFilter(Color.BLACK);
                    btnBackwardStep.setColorFilter(Color.BLACK);
                } else {
                    mainSearchByTag.setBackgroundColor(Color.parseColor("#18191A"));
                    bodyViewByTag.setBackgroundColor(Color.parseColor("#18191A"));
                    tv_Notice.setTextColor(Color.WHITE);

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
            public void onSearchButtonClicked() {
            }
        });

        bodyViewByTag.setDataProvider(new bodyView.dataProvide() {
            @Override
            public List<List<Truyen>> getPageDataList() {
                return pageDataList;
            }
        });


    }

    private void declareVal() {
        mainSearchByTag = findViewById(R.id.main);
        headerView = findViewById(R.id.headerViewByTag);
        bodyViewByTag = findViewById(R.id.BodyViewByTag);

        tv_Notice = findViewById(R.id.tv_Notice);

        btnBackwardStep = findViewById(R.id.btnBackwardStep);
        btnForwardStep = findViewById(R.id.btnForwardStep);
        ListComic = findViewById(R.id.ListComic);
        tv4 = findViewById(R.id.textViewTitle);
        body = findViewById(R.id.body);
        btnForwardFast = findViewById(R.id.btnForwardFast);
        btnBackwardFast = findViewById(R.id.btnBackwardFast);

    }

    private void getComicSearch(String SearchText){
        // lấy dữ liệu rồi tìm thông qua searchText
        if (dataBaseSQLLite == null) {
            dataBaseSQLLite = new DataBaseSQLLite(this, null, null, 1);
        }

        if (SearchText == null || SearchText.isEmpty()) {
            Log.e("SearchComics", "Search text is null or empty.");
            return;
        }

        Cursor cursor = dataBaseSQLLite.searchComics(dataBaseSQLLite.getReadableDatabase(), SearchText);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String idTruyen = cursor.getString(cursor.getColumnIndex("idTruyen"));
                @SuppressLint("Range") String tenTruyen = cursor.getString(cursor.getColumnIndex("tenTruyen"));
                @SuppressLint("Range") String tenTacGia = cursor.getString(cursor.getColumnIndex("tenTacGia"));
                @SuppressLint("Range") int soLuotXem = cursor.getInt(cursor.getColumnIndex("luotXem"));
                @SuppressLint("Range") int soLuotTheoDoi = cursor.getInt(cursor.getColumnIndex("luotTheoDoi"));
                @SuppressLint("Range") String ngayPhatHanh = cursor.getString(cursor.getColumnIndex("ngayPhatHanh"));
                @SuppressLint("Range") String moTaTruyen = cursor.getString(cursor.getColumnIndex("moTaTruyen"));
                @SuppressLint("Range") String urlAnhBia = cursor.getString(cursor.getColumnIndex("urlAnhBia"));

                // Tạo đối tượng Truyen với đầy đủ dữ liệu
                Truyen truyen = new Truyen(idTruyen, tenTruyen, tenTacGia, soLuotXem, soLuotTheoDoi, ngayPhatHanh, moTaTruyen, urlAnhBia);
                truyenList.add(truyen);
                Log.d("Ten truyen", "Name: " + tenTruyen);
                Log.d("Ten truyen", "Name: " + tenTacGia);
            }
            cursor.close();
            Log.d("Truyen Count", "Total Truyen: " + truyenList.size());
        }
    }

    private void createPage(int numItems) {
        pageDataList.clear();
        int startIndex = 0;
        while (startIndex < truyenList.size()) {
            int pageSize = 20;
            int endIndex = Math.min(startIndex + pageSize, truyenList.size());
            pageDataList.add(new ArrayList<>(truyenList.subList(startIndex, endIndex)));
            startIndex += pageSize;
        }
    }


}