package com.example.daltud2.View;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ScrollView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daltud2.Control.ComicAdapter;
import com.example.daltud2.Model.Comic;
import com.example.daltud2.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private LinearLayout searchBar,mainLayout,header;
    //private EditText searchInput;
    private ImageButton searchButton,btnChange,logoQQ,btnPopup;
    private ScrollView scroll;
    private boolean isWhite = false;

    private Button btnPrev;
    private Button btnNext;
    private List<List<Comic>> pageDataList = new ArrayList<>();
    private List<Comic> pageComic;
    private int currentPage = 1;
    private ComicAdapter adapter;
    private int pageSize = 20;
    private RecyclerView ListComic;

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

    declareVal();
    createSampleData(100);
    searchButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if ( searchBar.getVisibility() == View.GONE){
                searchBar.setVisibility(View.VISIBLE);

                //gán animation đã tạo
                Animation slideDown = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_down);
                searchBar.startAnimation(slideDown);


            }else searchBar.setVisibility(View.GONE);


        }
    });

    btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isWhite = !isWhite;
                colorUiChange(isWhite);
            }
        });

    btnPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this,view);
                popupMenu.getMenuInflater().inflate(R.menu.menu_item, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getItemId() == R.id.item_1){
                            Intent intent = new Intent(MainActivity.this,searchTheLoai.class);
                            startActivity(intent);
                        }else if (menuItem.getItemId() == R.id.item_2) {
                            Intent intent = new Intent(MainActivity.this, searchTheLoai.class);
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(MainActivity.this, searchTheLoai.class);
                            startActivity(intent);
                        }

                        return true;
                    }
                });
                popupMenu.show();
            }
        });


    // Khởi tạo Adapter với dữ liệu của trang đầu tiên
    adapter = new ComicAdapter(pageDataList.get(currentPage-1));
    GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
    ListComic.setLayoutManager(gridLayoutManager);
    ListComic.setAdapter(adapter);


        // Xử lý chuyển trang (tương tự như ví dụ trước)
    btnPrev.setOnClickListener(view -> {
        if (currentPage > 1) {
            currentPage--;
            adapter.updateData(pageDataList.get(currentPage - 1));
            adapter.notifyDataSetChanged();
        }
    });

    btnNext.setOnClickListener(view -> {
        if (currentPage < pageDataList.size()) {
            currentPage++;
            adapter.updateData(pageDataList.get(currentPage - 1));
            adapter.notifyDataSetChanged();
        }
    });
    }
    private void colorUiChange(boolean isWhite) {
        if (isWhite) {
            mainLayout.setBackgroundColor(Color.WHITE);
            logoQQ.setBackgroundColor(Color.WHITE);
            header.setBackgroundColor(Color.WHITE);
            btnChange.setImageResource(R.drawable.baseline_mode_night_24);
            btnChange.setBackground(null);
            searchButton.setBackground(null);
            logoQQ.setImageResource(R.mipmap.logoday);

        } else {
            mainLayout.setBackgroundColor(Color.BLACK);
            logoQQ.setBackgroundColor(Color.BLACK);
            header.setBackgroundColor(Color.BLACK);
            btnChange.setImageResource(R.drawable.baseline_light_mode_24);
            searchButton.setBackground(null);
            btnChange.setBackground(null);
            logoQQ.setImageResource(R.mipmap.logonight);
        }
    }

    private void declareVal(){
        searchBar = (LinearLayout) findViewById(R.id.search_bar);
       //searchInput = (EditText) findViewById(R.id.search_input);
        searchButton = (ImageButton) findViewById(R.id.search_button);
        btnChange = (ImageButton) findViewById(R.id.btnChange);
        logoQQ = (ImageButton) findViewById(R.id.logoQQ);
        mainLayout = (LinearLayout) findViewById(R.id.main);
        header = (LinearLayout) findViewById(R.id.header);
        btnPopup = (ImageButton) findViewById(R.id.btnPopup);
        btnPrev = (Button) findViewById(R.id.btnPrev);
        btnNext = (Button) findViewById(R.id.btnNext);
        ListComic = (RecyclerView) findViewById(R.id.ListComic);
    }

    // Hàm tạo dữ liệu mẫu
    private void createSampleData(int numItems) {
        List<Comic> fullDataList = new ArrayList<>();
        for (int i = 1; i <= numItems; i++) {
            fullDataList.add(new Comic("Comic " + i, 0));
        }

        // Chia dữ liệu thành các trang
        pageDataList.clear();
        int startIndex = 0;
        while (startIndex < fullDataList.size()) {
            int endIndex = Math.min(startIndex + pageSize, fullDataList.size());
            pageComic = new ArrayList<>(fullDataList.subList(startIndex, endIndex));
            pageDataList.add(pageComic);
            startIndex += pageSize;
        }
    }
}