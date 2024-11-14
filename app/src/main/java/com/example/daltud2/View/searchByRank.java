package com.example.daltud2.View;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daltud2.Control.GenericCustomSpinnerAdapter;
import com.example.daltud2.Control.HeaderView;
import com.example.daltud2.Control.bodyView;
import com.example.daltud2.Model.Comic;
import com.example.daltud2.Model.Truyen;
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
    private  int numItems = 1000;
    List<String> sortRank = Arrays.asList("Top Ngày", "Top Tuần", "Top Tháng");
    private GenericCustomSpinnerAdapter rankComicsAdapter;
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
        //createSampleData(numItems);
        rankComicsAdapter = new GenericCustomSpinnerAdapter(this,R.layout.item_selected,sortRank);
        sortRankpinner.setAdapter(rankComicsAdapter);

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

        bodyView.setDataProvider(new bodyView.dataProvide() {
            @Override
            public List<List<Truyen>> getPageDataList() {
                return pageDataList;
            }
        });

        sortRankpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedOption = sortRank.get(i);
                tvRank.setText(selectedOption);
                if (selectedOption.equals("Top Ngày")) {
                    /**
                     * lay cai ma vi du Newest đi rồi tạo hàm dựa vào mã dể call database và tạo bảng truyện có thời gian mới nhất
                     */
                } else if (selectedOption.equals("Top Tuần")) {
                    /**
                     * lay cai ma vi du Newest đi rồi tạo hàm dựa vào mã dể call database và tạo bảng truyện có thời gian cũ nhất
                     */
                } else if (selectedOption.equals("Top Tháng")) {
                    /**
                     * lay cai ma vi du Newest đi rồi tạo hàm dựa vào mã dể call database và tạo bảng truyện có view nhiều rồi giảm dần
                     */
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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

    /*
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

    */

    //endregion
}