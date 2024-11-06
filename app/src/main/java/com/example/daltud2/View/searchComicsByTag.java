package com.example.daltud2.View;

import android.graphics.Color;
import android.os.Bundle;
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

import com.example.daltud2.Control.GenericCustomSpinnerAdapter;
import com.example.daltud2.Control.HeaderView;
import com.example.daltud2.Control.bodyView;
import com.example.daltud2.Model.Comic;
import com.example.daltud2.Model.tagComics;
import com.example.daltud2.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        tagComicsAdapter = new GenericCustomSpinnerAdapter(this,R.layout.item_selected,getListCategory());
        spinnerTag.setAdapter(tagComicsAdapter);
        sortAdapter = new GenericCustomSpinnerAdapter<>(this, R.layout.item_selected, sortOptions);
        spinnerSort.setAdapter(sortAdapter);

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

        spinnerTag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tagComics selectedTag = (tagComics) tagComicsAdapter.getItem(i);

                if (selectedTag != null) {
                    tagTitle.setText(selectedTag.getName());
                    tagDescription.setText(selectedTag.getDescription());
                    Toast.makeText(searchComicsByTag.this, selectedTag.getName(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                String selectedOption = sortOptions.get(i);
                Toast.makeText(getApplicationContext(), selectedOption, Toast.LENGTH_SHORT).show();
                if (selectedOption.equals("Mới nhất")) {
                    /**
                     * lay cai ma vi du Newest đi rồi tạo hàm dựa vào mã dể call database và tạo bảng truyện có thời gian mới nhất
                     */
                } else if (selectedOption.equals("Cũ nhất")) {
                    /**
                     * lay cai ma vi du Newest đi rồi tạo hàm dựa vào mã dể call database và tạo bảng truyện có thời gian cũ nhất
                     */
                } else if (selectedOption.equals("Lượt xem giảm dần")) {
                    /**
                     * lay cai ma vi du Newest đi rồi tạo hàm dựa vào mã dể call database và tạo bảng truyện có view nhiều rồi giảm dần
                     */
                } else if (selectedOption.equals("Lượt xem tăng dần")) {
                    /**
                     * lay cai ma vi du Newest đi rồi tạo hàm dựa vào mã dể call database và tạo bảng truyện có view ít nhất rồi tăng dần
                     */
                }
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

    private List<tagComics> getListCategory(){
        List<tagComics> tagcomics = new ArrayList<>();
        tagcomics.add(new tagComics("Action", "Thể loại này thường có nội dung về đánh nhau, bạo lực, hỗn loạn, với diễn biến nhanh"));
        tagcomics.add(new tagComics("Adventure", "Thể loại này tập trung vào các chuyến phiêu lưu và thám hiểm."));
        tagcomics.add(new tagComics("Anime", "Thể loại liên quan đến phong cách hoạt hình Nhật Bản."));
        tagcomics.add(new tagComics("Chuyển Sinh", "Thể loại mà nhân vật chính được chuyển sinh hoặc tái sinh sang thế giới khác."));
        tagcomics.add(new tagComics("Cổ Đại", "Thể loại lấy bối cảnh trong thời kỳ lịch sử xa xưa."));
        tagcomics.add(new tagComics("Comedy", "Thể loại hài hước với nội dung nhẹ nhàng và gây cười."));
        tagcomics.add(new tagComics("Comic", "Thể loại truyện tranh tổng hợp từ nhiều chủ đề khác nhau."));
        tagcomics.add(new tagComics("Demons", "Thể loại liên quan đến quỷ và các sinh vật siêu nhiên."));
        tagcomics.add(new tagComics("Detective", "Thể loại truyện điều tra, phá án và các vụ án bí ẩn."));
        tagcomics.add(new tagComics("Doujinshi", "Thể loại truyện tự sáng tác của người hâm mộ dựa trên các tác phẩm gốc."));
        tagcomics.add(new tagComics("Drama", "Thể loại truyện với tình tiết kịch tính và cảm xúc."));
        tagcomics.add(new tagComics("Fantasy", "Thể loại viễn tưởng, thường có yếu tố phép thuật hoặc thế giới kỳ ảo."));
        tagcomics.add(new tagComics("Gender Bender", "Thể loại nhân vật chính bị thay đổi giới tính."));
        tagcomics.add(new tagComics("Harem", "Thể loại mà nhân vật chính được bao quanh bởi nhiều người yêu hoặc người hâm mộ."));
        tagcomics.add(new tagComics("Historical", "Thể loại lấy bối cảnh trong một thời kỳ lịch sử nhất định."));
        tagcomics.add(new tagComics("Horror", "Thể loại kinh dị, tập trung vào yếu tố sợ hãi và rùng rợn."));
        tagcomics.add(new tagComics("Huyền Huyễn", "Thể loại viễn tưởng kỳ ảo, thường xuất hiện trong các câu chuyện Trung Quốc."));
        tagcomics.add(new tagComics("Isekai", "Thể loại chuyển sinh sang thế giới khác hoặc song song."));
        tagcomics.add(new tagComics("Josei", "Thể loại dành cho đối tượng nữ trưởng thành."));
        tagcomics.add(new tagComics("Mafia", "Thể loại xoay quanh thế giới ngầm và các tổ chức tội phạm."));

        return tagcomics;
    }


    //endregion


}
