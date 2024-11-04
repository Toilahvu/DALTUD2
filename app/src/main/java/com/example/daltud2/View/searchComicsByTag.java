package com.example.daltud2.View;

import android.graphics.Color;
import android.os.Bundle;
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

import com.example.daltud2.Control.HeaderView;
import com.example.daltud2.Control.bodyView;
import com.example.daltud2.Model.Comic;
import com.example.daltud2.R;

import java.util.ArrayList;
import java.util.List;

public class searchComicsByTag extends AppCompatActivity {

    //region Variables
    private LinearLayout mainSearchByTag;
    private HeaderView headerView;
    private bodyView bodyViewByTag;
    private TextView tagTitle, tagDescription, TagSpinnerLabel, SortSpinnerLabel;
    private Spinner spinnerTag, spinnerSort;

    private TextView previousPageNumber, tv4;
    private ImageButton btnBackwardStep, btnForwardStep, btnForwardFast, btnBackwardFast;
    private LinearLayout pageNumbersLayout;
    private LinearLayout  body;
    private RecyclerView ListComic;

    private final List<List<Comic>> pageDataList = new ArrayList<>();
    private boolean isNotWhite = true;
    //endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_comics_by_tag);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Initialize views
        declareVal();
        createSampleData(400);

        headerView.setHeaderListener(new HeaderView.HeaderListener() {
            @Override
            public void onUIChangeRequested() {
                if (isNotWhite) {
                    mainSearchByTag.setBackgroundColor(Color.WHITE);
                    bodyViewByTag.setBackgroundColor(Color.WHITE);
                    tagTitle.setTextColor(Color.BLACK);
                    tagDescription.setTextColor(Color.BLACK);
                    TagSpinnerLabel.setTextColor(Color.BLACK);
                    SortSpinnerLabel.setTextColor(Color.BLACK);

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
                    tagTitle.setTextColor(Color.WHITE);
                    tagDescription.setTextColor(Color.LTGRAY);
                    TagSpinnerLabel.setTextColor(Color.parseColor("#FFC107"));
                    SortSpinnerLabel.setTextColor(Color.parseColor("#FFC107"));

                    ListComic.setBackgroundColor(Color.parseColor("#18191A"));
                    tv4.setTextColor(Color.WHITE);
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

        bodyViewByTag.setDataProvider(new bodyView.dataProvide() {
            @Override
            public List<List<Comic>> getPageDataList() {
                return pageDataList;
            }
        });

    }

    /**
     * làm thêm adapter spinner , là xong phần này
     */

    //region Methods

    private void declareVal() {
        mainSearchByTag = findViewById(R.id.main);
        headerView = findViewById(R.id.headerViewByTag);
        bodyViewByTag = findViewById(R.id.BodyViewByTag);
        tagTitle = findViewById(R.id.Tag);
        tagDescription = findViewById(R.id.DescripTag);
        spinnerTag = findViewById(R.id.spinnerTag);
        spinnerSort = findViewById(R.id.spinnerSort);
        TagSpinnerLabel = findViewById(R.id.TagSpinnerLabel);
        SortSpinnerLabel = findViewById(R.id.SortSpinnerLabel);

        btnBackwardStep = findViewById(R.id.btnBackwardStep);
        btnForwardStep = findViewById(R.id.btnForwardStep);
        ListComic = findViewById(R.id.ListComic);
        tv4 = findViewById(R.id.textViewTitle);
        body = findViewById(R.id.body);
        btnForwardFast = findViewById(R.id.btnForwardFast);
        btnBackwardFast = findViewById(R.id.btnBackwardFast);
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
