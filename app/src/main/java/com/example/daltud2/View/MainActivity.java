package com.example.daltud2.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daltud2.Control.ComicAdapter;
import com.example.daltud2.Control.DataBaseSQLLite;
import com.example.daltud2.Control.bodyView;
import com.example.daltud2.LoginActivity;
import com.example.daltud2.Control.HeaderView;
import com.example.daltud2.Model.Comic;
import com.example.daltud2.Model.NguoiDung;
import com.example.daltud2.Model.Truyen;
import com.example.daltud2.R;
import com.example.daltud2.RegisterActivity;
import android.animation.ObjectAnimator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //region Variables
    private boolean isNotWhite = true;
    private ComicAdapter adapter;
    private HeaderView headerView;
    private bodyView bodyViewInstance;
    private final List<List<Comic>> pageDataList = new ArrayList<>();
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
        //endregion

        //region Database Initialization
        getAllruyen();
        createSampleData(200);
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
             public void onSearchButtonClicked() {
             }

             @Override
             public void onSearchComicsClicked(String query) {
             }

             @Override
             public void onHomeButtonClicked() {
             }
        });

        bodyViewInstance.setDataProvider(new bodyView.dataProvide() {
            @Override
            public List<List<Comic>> getPageDataList() {
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

    /**
     * Huy Nhắn Chinh,
     *getAllTruyen  = createSampleData(int numItems)
     *giờ làm sao để getAllTruyen nó return  cái pageDataList  hàm là được,
     *sau này khi click vào thì intent tất cả dữ liệu sang trang đọc truyện thôi
     */

    private void getAllruyen() {
        if (dataBaseSQLLite == null) {
            dataBaseSQLLite = new DataBaseSQLLite(this, null, null, 1);
        }
        Cursor cursor = dataBaseSQLLite.getAllTruyen(dataBaseSQLLite.getReadableDatabase());
        if (cursor != null) {
            List<Truyen> truyenList = new ArrayList<>();
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String tenTruyen = cursor.getString(cursor.getColumnIndex("tenTruyen"));
                truyenList.add(new Truyen(tenTruyen));
                Log.d("Ten truyen", "Name: " + tenTruyen);
            }
            cursor.close();
            Log.d("Truyen Count", "Total Truyen: " + truyenList.size());
        }
    }
    private void createSampleData(int numItems) {
        List<Comic> fullDataList = new ArrayList<>();
        for (int i = 1; i <= numItems; i++) {
            fullDataList.add(new Comic("Comic " + i, 0));
        }
        pageDataList.clear();
        int startIndex = 0;
        while (startIndex < fullDataList.size()) {
            int pageSize = 20;
            int endIndex = Math.min(startIndex + pageSize, fullDataList.size());
            pageDataList.add(new ArrayList<>(fullDataList.subList(startIndex, endIndex)));
            startIndex += pageSize;
        }
    }

    //endregion
}




