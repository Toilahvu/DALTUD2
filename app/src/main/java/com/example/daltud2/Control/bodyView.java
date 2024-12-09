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
            updateNavigationButtons();
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
        if (pageDataList == null || pageDataList.isEmpty()) return;
        if(pageDataList.size() == 1) return;

        pageNumbersLayout.removeAllViews();
        maxPages = pageDataList.size();

        // Xác định giới hạn dải trang hiển thị
        int displayRange = 4;
        int startPage = currentPage;//Math.max(1, currentPage-2);
        // mục đích -2 là cho cân đối, chứ để startPage = currentPage cx đc
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
                updateNavigationButtons();
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

    private void updateNavigationButtons() {
        if (maxPages == 1) {
            btnBackwardStep.setVisibility(View.GONE);
            btnBackwardFast.setVisibility(View.GONE);
            btnForwardStep.setVisibility(View.GONE);
            btnForwardFast.setVisibility(View.GONE);
            return;
        }

        if ( currentPage == 1) {
            btnBackwardStep.setVisibility(View.GONE);
            btnBackwardFast.setVisibility(View.GONE);
            btnForwardStep.setVisibility(View.VISIBLE);
            btnForwardFast.setVisibility(View.VISIBLE);
        } else if (currentPage == maxPages) {
            btnBackwardStep.setVisibility(View.VISIBLE);
            btnBackwardFast.setVisibility(View.VISIBLE);
            btnForwardStep.setVisibility(View.GONE);
            btnForwardFast.setVisibility(View.GONE);
        } else {
            btnBackwardStep.setVisibility(View.VISIBLE);
            btnBackwardFast.setVisibility(View.VISIBLE);
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
                updateNavigationButtons();
            }
        });
        btnBackwardStep.setOnClickListener(view -> {
            if (currentPage > 1 && !pageDataList.isEmpty()) {
                ListComic.scrollToPosition(0);
                currentPage--;
                adapter.updateData(pageDataList.get(currentPage - 1));
                listPageComic();
                updateNavigationButtons();
            }
        });
        btnForwardStep.setOnClickListener(view -> {
            if (currentPage < maxPages && !pageDataList.isEmpty()) {
                ListComic.scrollToPosition(0);
                currentPage++;
                adapter.updateData(pageDataList.get(currentPage - 1));
                listPageComic();
                updateNavigationButtons();
            }
        });
        btnForwardFast.setOnClickListener(view -> {
            if (!pageDataList.isEmpty()) {
                ListComic.scrollToPosition(0);
                currentPage = maxPages;
                adapter.updateData(pageDataList.get(currentPage - 1));
                listPageComic();
                updateNavigationButtons();
            }
        });
    }



}
