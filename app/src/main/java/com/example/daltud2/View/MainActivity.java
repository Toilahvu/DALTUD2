package com.example.daltud2.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
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


    private ImageView notificationButton;

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

        // Kiểm tra nếu cần hiển thị popup
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        boolean showPopup = prefs.getBoolean("showPopup", true);

        if (showPopup) {
            Message messageFragment = Message.newInstance("Thông Báo Mới Nhất");
            messageFragment.show(getSupportFragmentManager(), "MessageDialog");

            // Cập nhật cờ để lần sau không hiển thị nữa
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("showPopup", false);
            editor.apply();
        }
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
                     notificationButton.setColorFilter(Color.BLACK);
                 }else {
                     ListComic.setBackgroundColor(Color.parseColor("#18191A"));
                     tv4.setTextColor(Color.parseColor("#FFC107"));
                     body.setBackgroundColor(Color.parseColor("#18191A"));
                     btnForwardFast.setColorFilter(Color.WHITE);
                     btnBackwardFast.setColorFilter(Color.WHITE);
                     btnForwardStep.setColorFilter(Color.WHITE);
                     btnBackwardStep.setColorFilter(Color.WHITE);
                     notificationButton.setColorFilter(Color.WHITE);
                 }
                 isNotWhite = !isNotWhite;
             }

        });


        bodyViewInstance.setDataProvider(new bodyView.dataProvide() {
            @Override
            public List<List<Truyen>> getPageDataList() {
                return pageDataList;
            }
        });

        bodyViewInstance.setFragmentManager(getSupportFragmentManager());
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
        notificationButton = findViewById(R.id.notification_button);

    }
    //endregion

    //region Database
    private void getAllruyen() {
        if (dataBaseSQLLite == null) {
            dataBaseSQLLite = new DataBaseSQLLite(this, null, null, 7);
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

    private void sortTimeComics() {
        truyenList.sort((truyen1, truyen2) -> truyen2.getNgayPhatHanh().compareTo(truyen1.getNgayPhatHanh()));
        Log.d("SortTimeComics", "Danh sách đã được sắp xếp theo thời gian.");
    }

    private void quickSortTimeComics(List<Truyen> list, int low, int high) {
        if (low < high) {
            int pi = partition(list, low, high);

            quickSortTimeComics(list, low, pi - 1);
            quickSortTimeComics(list, pi + 1, high);
        }
    }

    private int partition(List<Truyen> list, int low, int high) {
        Truyen pivot = list.get(high);
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            // So sánh ngày phát hành, xử lý trường hợp null
            String dateJ = list.get(j).getNgayPhatHanh();
            String datePivot = pivot.getNgayPhatHanh();

            if ((dateJ != null && datePivot != null && dateJ.compareTo(datePivot) >= 0) ||
                    (dateJ == null && datePivot != null) ||
                    (dateJ == null && datePivot == null))
            {
                i++;

                Truyen temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }

        Truyen temp = list.get(i + 1);
        list.set(i + 1, list.get(high));
        list.set(high, temp);

        return i + 1;
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
        List<Truyen> sortedTruyenList = new ArrayList<>(truyenList);
        quickSortTimeComics(truyenList, 0, truyenList.size() - 1);
        //sortTimeComics();
        createListComics(20);
    }

    //endregion
}




