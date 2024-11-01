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

public class MainActivity extends AppCompatActivity implements HeaderView.HeaderListener {

    //region Variables
    private LinearLayout searchBar, body, footer;
    private NestedScrollView mainLayout;
    private ImageButton searchButton, btnPopup;
    private ScrollView scroll;
    private boolean isNotWhite = true;
    private int maxPages;
    private TextView previousPageNumber, tv4;
    private ImageButton btnBackwardStep, btnForwardStep, btnForwardFast, btnBackwardFast;
    private LinearLayout pageNumbersLayout;
    private final List<List<Comic>> pageDataList = new ArrayList<>();
    private int currentPage = 1;
    private ComicAdapter adapter;
    private int pageSize = 20;
    private RecyclerView ListComic;

    private Button btnLogin;
    private Button btnRegister;

    private DataBaseSQLLite dataBaseSQLLite;
    private Button topButton;
    private HeaderView headerView;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //region Initial Setup
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        declareVal();
        headerView.setHeaderListener(this);
        dataBaseSQLLite = new DataBaseSQLLite(this);
        //endregion

        //region Database Initialization
        getAllruyen();
        createSampleData(200);
        //endregion

        //region Button Popup Menu
        btnPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectAnimator rotateAnimation = ObjectAnimator.ofFloat(btnPopup, "rotation", 0f, 180f);
                rotateAnimation.setDuration(500);
                rotateAnimation.start();

                PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.menu_item, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    Intent intent = new Intent(MainActivity.this, searchTheLoai.class);
                    startActivity(intent);
                    return true;
                });
                popupMenu.show();

                popupMenu.setOnDismissListener(popup -> {
                    ObjectAnimator rotateBack = ObjectAnimator.ofFloat(btnPopup, "rotation", 180f, 0f);
                    rotateBack.setDuration(300);
                    rotateBack.start();
                });
            }
        });
        //endregion

        //region Setup Comic Adapter
        adapter = new ComicAdapter(pageDataList.get(currentPage - 1));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        ListComic.setLayoutManager(gridLayoutManager);
        ListComic.setAdapter(adapter);
    //Mở trang login
    btnLogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
    });

    //Mở trang Register
    btnRegister.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
        }
    });

    // Khởi tạo Adapter với dữ liệu của trang đầu tiên
    adapter = new ComicAdapter(pageDataList.get(currentPage-1));
    GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
    ListComic.setLayoutManager(gridLayoutManager);
    ListComic.setAdapter(adapter);

    //List truyện và nút điều hướng
        createPageNumbers();
        //endregion

        //region Page Navigation
        setupPageNavigation();
        setupScrollListener();
        //endregion
    }

    //region Methods
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

    private void declareVal() {
        searchBar = findViewById(R.id.search_bar);
        searchButton = findViewById(R.id.search_button);
        mainLayout = findViewById(R.id.main);
        btnPopup = findViewById(R.id.btnPopup);
        btnBackwardStep = findViewById(R.id.btnBackwardStep);
        btnForwardStep = findViewById(R.id.btnForwardStep);
        ListComic = findViewById(R.id.ListComic);
        pageNumbersLayout = findViewById(R.id.pageNumbersLayout);
        tv4 = findViewById(R.id.textViewTitle);
        body = findViewById(R.id.body);
        footer = findViewById(R.id.footer);
        topButton = findViewById(R.id.topButton);
        btnForwardFast = findViewById(R.id.btnForwardFast);
        btnBackwardFast = findViewById(R.id.btnBackwardFast);
        headerView = findViewById(R.id.headerView);
    }

    private void createSampleData(int numItems) {
        List<Comic> fullDataList = new ArrayList<>();
        for (int i = 1; i <= numItems; i++) {
            fullDataList.add(new Comic("Comic " + i, 0));
        }
        pageDataList.clear();
        int startIndex = 0;
        while (startIndex < fullDataList.size()) {
            int endIndex = Math.min(startIndex + pageSize, fullDataList.size());
            pageDataList.add(new ArrayList<>(fullDataList.subList(startIndex, endIndex)));
            startIndex += pageSize;
        }
    }

    private void createPageNumbers() {
        pageNumbersLayout.removeAllViews();
        maxPages = pageDataList.size();
        int startPage = Math.max(1, currentPage - 2);
        int endPage = Math.min(startPage + 4, maxPages);

        for (int i = startPage; i <= endPage; i++) {
            TextView pageNumber = (TextView) LayoutInflater.from(this)
                    .inflate(R.layout.page_numbers_layout, pageNumbersLayout, false);
            pageNumber.setText(String.valueOf(i));
            pageNumber.setId(i);

            int originalTextColor = pageNumber.getCurrentTextColor();
            pageNumber.setOnClickListener(v -> {
                if (previousPageNumber != null) previousPageNumber.setTextColor(originalTextColor);
                pageNumber.setTextColor(Color.RED);
                currentPage = v.getId();
                adapter.updateData(pageDataList.get(currentPage - 1));
                previousPageNumber = pageNumber;
                mainLayout.smoothScrollTo(0, 0);
                createPageNumbers();
            });

            if (i == currentPage) {
                pageNumber.setTextColor(Color.parseColor("#FFA500"));
                previousPageNumber = pageNumber;
            } else {
                pageNumber.setTextColor(Color.GRAY);
            }
            pageNumbersLayout.addView(pageNumber);
        }
    }

    private void setupPageNavigation() {
        btnBackwardFast.setOnClickListener(view -> navigateToFirstPage());
        btnBackwardStep.setOnClickListener(view -> navigateBackward());
        btnForwardStep.setOnClickListener(view -> navigateForward());
        btnForwardFast.setOnClickListener(view -> navigateToLastPage());
    }

    private void setupScrollListener() {
        mainLayout.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                topButton.setVisibility(scrollY > 200 ? View.VISIBLE : View.GONE);
            }
        });
        topButton.setOnClickListener(view -> mainLayout.smoothScrollTo(0, 0));
    }

    private void navigateToFirstPage() {
        currentPage = 1;
        adapter.updateData(pageDataList.get(currentPage - 1));
        createPageNumbers();
        mainLayout.smoothScrollTo(0, 0);
    }

    private void navigateBackward() {
        if (currentPage > 1) {
            currentPage--;
            adapter.updateData(pageDataList.get(currentPage - 1));
            createPageNumbers();
            mainLayout.smoothScrollTo(0, 0);
        }
    }

    private void navigateForward() {
        if (currentPage < maxPages) {
            currentPage++;
            adapter.updateData(pageDataList.get(currentPage - 1));
            createPageNumbers();
            mainLayout.smoothScrollTo(0, 0);
        }
    }

    private void navigateToLastPage() {
        currentPage = maxPages;
        adapter.updateData(pageDataList.get(currentPage - 1));
        createPageNumbers();
        mainLayout.smoothScrollTo(0, 0);
    }
    //endregion

    //region Interface Methods
    @Override
    public void onUIChangeRequested() {
        if (isNotWhite) {
            ListComic.setBackgroundColor(Color.WHITE);
            tv4.setTextColor(Color.BLACK);
            body.setBackgroundColor(Color.WHITE);
            footer.setBackgroundColor(Color.WHITE);
            btnForwardFast.setColorFilter(Color.BLACK);
            btnBackwardFast.setColorFilter(Color.BLACK);
            btnForwardStep.setColorFilter(Color.BLACK);
            btnBackwardStep.setColorFilter(Color.BLACK);
        } else {
            ListComic.setBackgroundColor(Color.parseColor("#18191A"));
            tv4.setTextColor(Color.WHITE);
            body.setBackgroundColor(Color.parseColor("#18191A"));
            footer.setBackgroundColor(Color.parseColor("#18191A"));
            btnForwardFast.setColorFilter(Color.WHITE);
            btnBackwardFast.setColorFilter(Color.WHITE);
            btnForwardStep.setColorFilter(Color.WHITE);
            btnBackwardStep.setColorFilter(Color.WHITE);
        }
        isNotWhite = !isNotWhite;
    }

    @Override
    public void onSearchButtonClicked() {
        if (searchBar.getVisibility() == View.GONE) {
            searchBar.setVisibility(View.VISIBLE);
            Animation slideDown = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_down);
            searchBar.startAnimation(slideDown);
        } else {
            searchBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoginButtonClicked() {
    }

    @Override
    public void onRegisterButtonClicked() {
    }

    @Override
    public void onSearchComicsClicked(String query) {
    }
    //endregion
}
