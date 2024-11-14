package com.example.daltud2.Control;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daltud2.Model.Comic;
import com.example.daltud2.Model.Truyen;
import com.example.daltud2.R;

import java.util.ArrayList;
import java.util.List;

public class bodyView extends LinearLayout {
    private NestedScrollView mainLayout;
    private TextView previousPageNumber, tv4;
    private ImageButton btnBackwardStep, btnForwardStep, btnForwardFast, btnBackwardFast;
    private LinearLayout pageNumbersLayout;
    private RecyclerView ListComic;
    private int currentPage = 1;
    private List<List<Truyen>> pageDataList = new ArrayList<>();  // Initialize with empty list
    private int maxPages;
    private ComicAdapter adapter;
    private dataProvide provider;

    public bodyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public interface dataProvide {
        List<List<Truyen>> getPageDataList();
    }

    public void setDataProvider(dataProvide provider) {
        this.provider = provider;
        initData();
    }

    private void initData() {
        if (provider != null) {
            this.pageDataList = provider.getPageDataList();
            this.maxPages = pageDataList.size();

            if (!pageDataList.isEmpty()) {
                adapter = new ComicAdapter(pageDataList.get(currentPage - 1));
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                ListComic.setLayoutManager(gridLayoutManager);
                ListComic.setAdapter(adapter);
            }

            listPageComic();
        }
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.body, this, true);

        btnBackwardStep = findViewById(R.id.btnBackwardStep);
        btnForwardStep = findViewById(R.id.btnForwardStep);
        ListComic = findViewById(R.id.ListComic);
        pageNumbersLayout = findViewById(R.id.pageNumbersLayout);
        tv4 = findViewById(R.id.textViewTitle);
        btnForwardFast = findViewById(R.id.btnForwardFast);
        btnBackwardFast = findViewById(R.id.btnBackwardFast);

        setupPageNavigation();
    }

    private void listPageComic() {
        if (pageDataList == null || pageDataList.isEmpty()) return;// nhỡ lỗi thì bỏ qua

        pageNumbersLayout.removeAllViews();
        maxPages = pageDataList.size();

        // Xác định giới hạn dải trang hiển thị
        int displayRange = 5; // Hiển thị tối đa 5 trang
        int startPage = Math.max(1, currentPage - 2);
        int endPage = Math.min(startPage + displayRange - 1, maxPages);

        // Điều chỉnh dải hiển thị để trang hiện tại luôn ở giữa
        if (endPage - startPage < displayRange - 1) {
            startPage = Math.max(1, endPage - displayRange + 1);
        }

        for (int i = startPage; i <= endPage; i++) {
            TextView pageNumber = (TextView) LayoutInflater.from(getContext())
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
                ListComic.scrollToPosition(0);
                listPageComic();
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


    private void updateNavigationButtons(View view) {
        if (currentPage == 1) {
            btnBackwardStep.setVisibility(View.GONE);
            btnBackwardFast.setVisibility(View.GONE);
        } else {
            btnBackwardStep.setVisibility(View.VISIBLE);
            btnBackwardFast.setVisibility(View.VISIBLE);
        }

        // Ẩn nút "trang sau" và "về cuối" khi đang ở trang cuối cùng
        if (currentPage == maxPages) {
            btnForwardStep.setVisibility(View.GONE);
            btnForwardFast.setVisibility(View.GONE);
        } else {
            btnForwardStep.setVisibility(View.VISIBLE);
            btnForwardFast.setVisibility(View.VISIBLE);
        }
    }

    private void setupPageNavigation() {
        btnBackwardFast.setOnClickListener(view -> {
            if (!pageDataList.isEmpty()) {
                ListComic.scrollToPosition(0);
                currentPage = 1;
                adapter.updateData(pageDataList.get(currentPage - 1));
                listPageComic();
                updateNavigationButtons(view);
            }
        });
        btnBackwardStep.setOnClickListener(view -> {
            if (currentPage > 1 && !pageDataList.isEmpty()) {
                ListComic.scrollToPosition(0);
                currentPage--;
                adapter.updateData(pageDataList.get(currentPage - 1));
                listPageComic();
                updateNavigationButtons(view);
            }
        });
        btnForwardStep.setOnClickListener(view -> {
            if (currentPage < maxPages && !pageDataList.isEmpty()) {
                ListComic.scrollToPosition(0);
                currentPage++;
                adapter.updateData(pageDataList.get(currentPage - 1));
                listPageComic();
                updateNavigationButtons(view);
            }
        });
        btnForwardFast.setOnClickListener(view -> {
            if (!pageDataList.isEmpty()) {
                ListComic.scrollToPosition(0);
                currentPage = maxPages;
                adapter.updateData(pageDataList.get(currentPage - 1));
                listPageComic();
                updateNavigationButtons(view);
            }
        });
    }





}
