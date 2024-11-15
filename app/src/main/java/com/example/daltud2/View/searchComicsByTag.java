package com.example.daltud2.View;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.example.daltud2.Model.Comic;
import com.example.daltud2.Model.Truyen;
import com.example.daltud2.Model.tagComics;
import com.example.daltud2.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class searchComicsByTag extends AppCompatActivity {

    //region Variables
    private LinearLayout mainSearchByTag;
    private HeaderView headerView;
    private bodyView bodyViewByTag;
    private TextView tagTitle, tagDescription, TagSpinnerLabel, SortSpinnerLabel;
    private Spinner spinnerTag, spinnerSort;
    List<String> sortOptions = Arrays.asList("Mới nhất", "Cũ nhất", "Lượt xem giảm dần","Lượt xem tăng dần");
    private GenericCustomSpinnerAdapter tagComicsAdapter;
    GenericCustomSpinnerAdapter<String> sortAdapter;

    private TextView previousPageNumber, tv4;
    private ImageButton btnBackwardStep, btnForwardStep, btnForwardFast, btnBackwardFast;
    private LinearLayout pageNumbersLayout;
    private LinearLayout  body;
    private RecyclerView ListComic;

    private TextView tv_category;
    private TextView tv_selected;
    private DataBaseSQLLite dataBaseSQLLite;

    private final List<List<Truyen>> pageDataList = new ArrayList<>();
    private List<Truyen> truyenList = new ArrayList<>();
    private boolean isNotWhite = true;

    private String tagComics = "Action";
    private String sortComics = "Mới nhất";
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

        declareVal();
        ComicList(tagComics,sortComics,20);

        tagComicsAdapter = new GenericCustomSpinnerAdapter(this,R.layout.item_selected,getListCategory());
        spinnerTag.setAdapter(tagComicsAdapter);
        sortAdapter = new GenericCustomSpinnerAdapter<>(this, R.layout.item_selected, sortOptions);
        spinnerSort.setAdapter(sortAdapter);

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
                    tagTitle.setTextColor(Color.parseColor("#FFC107"));
                    tagDescription.setTextColor(Color.LTGRAY);
                    TagSpinnerLabel.setTextColor(Color.parseColor("#FFC107"));
                    SortSpinnerLabel.setTextColor(Color.parseColor("#FFC107"));

                    ListComic.setBackgroundColor(Color.parseColor("#18191A"));
                    tv4.setTextColor(Color.parseColor("#FFC107"));
                    body.setBackgroundColor(Color.parseColor("#18191A"));
                    btnForwardFast.setColorFilter(Color.WHITE);
                    btnBackwardFast.setColorFilter(Color.WHITE);
                    btnForwardStep.setColorFilter(Color.WHITE);
                    btnBackwardStep.setColorFilter(Color.WHITE);
                }
                isNotWhite = !isNotWhite;

                tagComicsAdapter.setTheme(isNotWhite);
                sortAdapter.setTheme(isNotWhite);

                spinnerTag.invalidate();
                spinnerSort.invalidate();
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

        spinnerTag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tagComics selectedTag = (tagComics) tagComicsAdapter.getItem(i);

                if (selectedTag != null) {
                    tagComics = selectedTag.getName();
                    tagTitle.setText(selectedTag.getName());
                    tagDescription.setText(selectedTag.getDescription());
                    Toast.makeText(searchComicsByTag.this, selectedTag.getName(), Toast.LENGTH_SHORT).show();

                    ComicList(tagComics, sortComics, 20);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                sortComics = sortOptions.get(i);
                ComicList(tagComics, sortComics, 20);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    //region Methods
    private void declareVal() {
        mainSearchByTag = findViewById(R.id.main);
        headerView = findViewById(R.id.headerViewByTag);
        bodyViewByTag = findViewById(R.id.BodyViewByTag);
        tagTitle = findViewById(R.id.tv_Tag);
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

    @SuppressLint("NotifyDataSetChanged")
    private void ComicList(String tag, String type, int nums){
        getComicTag(tag);
        sortComicsBy(type);
        createPageData(nums);

        //khi chạy lần đầu thì nó sẽ ko sự thay đổi , nên chắc chắn lỗi, làm thêm cái kiểm tra
        if (ListComic.getAdapter() != null) {
            ((ComicAdapter) ListComic.getAdapter()).updateData(truyenList);
        }
    }

    private void getComicTag(String tag){
        if (dataBaseSQLLite == null) {
            dataBaseSQLLite = new DataBaseSQLLite(this, null, null, 1);
        }

        Cursor cursor = dataBaseSQLLite.searchComicsByTag(dataBaseSQLLite.getReadableDatabase(),tag);
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
                Log.d("Ten truyen", "Name: " + tenTruyen);
                Log.d("Ten truyen", "Name: " + tenTacGia);
            }
            cursor.close();
            Log.d("Truyen Count", "Total Truyen: " + truyenList.size());
        }
    }

    private void sortComicsBy(String type){
        switch (type) {
            case "Mới nhất":
                truyenList.sort((truyen1, truyen2) -> truyen2.getNgayPhatHanh().compareTo(truyen1.getNgayPhatHanh()));
                break;

            case "Cũ nhất":
                truyenList.sort(Comparator.comparing(Truyen::getNgayPhatHanh));
                break;

            case "Lượt xem giảm dần":
                truyenList.sort((truyen1, truyen2) -> Integer.compare(truyen2.getSoLuotXem(), truyen1.getSoLuotXem()));
                break;

            case "Lượt xem tăng dần":
                truyenList.sort(Comparator.comparingInt(Truyen::getSoLuotTheoDoi));
                break;

            default:
                Log.d("SortComicsBy", "Tiêu chí sắp xếp không hợp lệ: " + type);
                return;
        }
        Log.d("sortComicsBy", "Sorted comics by: " + type);
    }

    private void createPageData(int numItems) {
        pageDataList.clear();
        int startIndex = 0;
        while (startIndex < truyenList.size()) {
            int pageSize = 20;
            int endIndex = Math.min(startIndex + pageSize, truyenList.size());
            pageDataList.add(new ArrayList<>(truyenList.subList(startIndex, endIndex)));
            startIndex += pageSize;
        }
    }

    private List<tagComics> getListCategory() {
        List<tagComics> tagComicsList = new ArrayList<>();

        if (dataBaseSQLLite == null) {
            dataBaseSQLLite = new DataBaseSQLLite(this, null, null, 1);
        }

        Cursor cursor = dataBaseSQLLite.getTagsComics(dataBaseSQLLite.getReadableDatabase());

        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String tenTag = cursor.getString(cursor.getColumnIndex("tenTag"));
                @SuppressLint("Range") String moTaTag = cursor.getString(cursor.getColumnIndex("moTaTag"));

                tagComicsList.add(new tagComics(tenTag, moTaTag));
            }
            cursor.close();
        }

        return tagComicsList;
    }


    //endregion


}
