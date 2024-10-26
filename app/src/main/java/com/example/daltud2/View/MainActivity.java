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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daltud2.Control.ComicAdapter;
import com.example.daltud2.Control.DataBaseSQLLite;
import com.example.daltud2.Model.Comic;
import com.example.daltud2.Model.NguoiDung;
import com.example.daltud2.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //khai báo biến
    private LinearLayout searchBar,header,body,footer;
    private NestedScrollView mainLayout;
    //private EditText searchInput;
    private ImageButton searchButton,btnChange,logoQQ,btnPopup;
    private ScrollView scroll;
    private boolean isWhite = false;

    private int maxPages;
    private int displayedStartPage = 1;
    private TextView previousPageNumber,tv4;
    private Button btnPrev;
    private Button btnNext;
    private LinearLayout pageNumbersLayout;
    private List<List<Comic>> pageDataList = new ArrayList<>();// chứa các mảng danh sách truện .
    private List<Comic> pageComic;//mảng chưa danh sách truyện
    private int currentPage = 1;
    private ComicAdapter adapter;
    private int pageSize = 20;
    private RecyclerView ListComic;

    private DataBaseSQLLite dataBaseSQLLite;


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

    // Khởi tạo cơ sở dữ liệu
    dataBaseSQLLite = new DataBaseSQLLite(this, null, null, 1);
    getAllAdmin();

    declareVal();
    createSampleData(200);
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

    createPageNumbers();
    btnPrev.setOnClickListener(view -> handlePrevButton());
    btnNext.setOnClickListener(view -> handleNextButton());

    }
    private void getAllAdmin(){
        if (dataBaseSQLLite == null) {
            dataBaseSQLLite = new DataBaseSQLLite(this, null, null, 1);
        }
        Cursor cursor = dataBaseSQLLite.timKiemAdmin(dataBaseSQLLite.getReadableDatabase());
        if (cursor != null) {
            List<NguoiDung> adminList = new ArrayList<>(); // Danh sách lưu admin
            while (cursor.moveToNext()) {
                // Lấy thông tin từ cursor
                @SuppressLint("Range") String idUser = cursor.getString(cursor.getColumnIndex("idUser"));
                @SuppressLint("Range") String tenUser = cursor.getString(cursor.getColumnIndex("tenUser"));
                @SuppressLint("Range") String matKhau = cursor.getString(cursor.getColumnIndex("matKhau"));
                @SuppressLint("Range") String soDienThoai = cursor.getString(cursor.getColumnIndex("soDienThoai"));
                @SuppressLint("Range") int role = cursor.getInt(cursor.getColumnIndex("role"));

                // Tạo đối tượng NguoiDung và thêm vào danh sách
                NguoiDung nguoiDung = new NguoiDung(idUser, tenUser, matKhau, soDienThoai, role);
                adminList.add(nguoiDung);

                // Ví dụ: in ra thông tin admin
                Log.d("Admin Info", "ID: " + nguoiDung.getIdUser() + ", Name: " + nguoiDung.getTenUser());
            }
            cursor.close();

            // Sử dụng adminList tùy ý
            // Ví dụ: hiển thị số lượng admin
            Log.d("Admin Count", "Total Admin: " + adminList.size());
        }
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
            ListComic.setBackgroundColor(Color.WHITE);
            tv4.setTextColor(Color.BLACK);
            body.setBackgroundColor(Color.WHITE);
            footer.setBackgroundColor(Color.WHITE);
        } else {
            mainLayout.setBackgroundColor(Color.BLACK);
            logoQQ.setBackgroundColor(Color.BLACK);
            header.setBackgroundColor(Color.BLACK);
            btnChange.setImageResource(R.drawable.baseline_light_mode_24);
            searchButton.setBackground(null);
            btnChange.setBackground(null);
            logoQQ.setImageResource(R.mipmap.logonight);
            ListComic.setBackgroundColor(Color.BLACK);
            tv4.setTextColor(Color.WHITE);
            body.setBackgroundColor(Color.BLACK);
            footer.setBackgroundColor(Color.BLACK);
        }
    }

    private void declareVal(){
        searchBar = (LinearLayout) findViewById(R.id.search_bar);
       //searchInput = (EditText) findViewById(R.id.search_input);
        searchButton = (ImageButton) findViewById(R.id.search_button);
        btnChange = (ImageButton) findViewById(R.id.btnChange);
        logoQQ = (ImageButton) findViewById(R.id.logoQQ);
        mainLayout = (NestedScrollView) findViewById(R.id.main);
        header = (LinearLayout) findViewById(R.id.header);
        btnPopup = (ImageButton) findViewById(R.id.btnPopup);
        btnPrev = (Button) findViewById(R.id.btnPrev);
        btnNext = (Button) findViewById(R.id.btnNext);
        ListComic = (RecyclerView) findViewById(R.id.ListComic);
        pageNumbersLayout = (LinearLayout)  findViewById(R.id.pageNumbersLayout);
        tv4 = (TextView) findViewById(R.id.textView4);
        body = (LinearLayout) findViewById(R.id.body);
        footer = (LinearLayout) findViewById(R.id.footer);
    }

    // Hàm tạo dữ liệu mẫu
    private void createSampleData(int numItems) {
        //sau này sửa lại lay' data tu` database
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

    private void createPageNumbers() {
        pageNumbersLayout.removeAllViews(); // Xóa tất cả các TextView cũ

        maxPages = pageDataList.size(); // Tổng số trang
        // Hiển thị tối đa 5 trang: Ví dụ từ trang 1 đến trang 5 hoặc trang hiện tại
        int startPage = Math.max(1, currentPage - 2); // Hiển thị từ trang hiện tại - 2
        int endPage = Math.min(startPage + 4, maxPages); // Hiển thị tối đa 5 trang (nếu còn đủ trang)

        for (int i = startPage; i <= endPage; i++) {
            TextView pageNumber = (TextView) LayoutInflater.from(this)
                    .inflate(R.layout.page_numbers_layout, pageNumbersLayout, false);
            pageNumber.setText(String.valueOf(i));
            pageNumber.setId(i);

            int originalTextColor = pageNumber.getCurrentTextColor();
            pageNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (previousPageNumber != null) {
                        previousPageNumber.setTextColor(originalTextColor); // Đổi lại màu trang trước đó
                    }

                    pageNumber.setTextColor(Color.RED); // Đổi màu trang hiện tại
                    currentPage = v.getId(); // Lấy ID của TextView đã click (số trang)
                    adapter.updateData(pageDataList.get(currentPage - 1)); // Cập nhật dữ liệu
                    previousPageNumber = pageNumber;
                    mainLayout.smoothScrollTo(0, 0);
                    createPageNumbers(); // Cập nhật lại số trang hiển thị
                }
            });

            if (i == currentPage) {
                // Đánh dấu trang hiện tại màu đỏ
                pageNumber.setTextColor(Color.RED);
                previousPageNumber = pageNumber;
            }

            pageNumbersLayout.addView(pageNumber); // Thêm số trang vào layout
        }
    }


    // Xử lý sự kiện click cho nút "Next"
// Xử lý sự kiện click cho nút "Next"
    private void handleNextButton() {
        if (currentPage < maxPages) {
            currentPage++;
            adapter.updateData(pageDataList.get(currentPage - 1)); // Cập nhật dữ liệu trang mới
            createPageNumbers(); // Cập nhật lại số trang hiển thị

            mainLayout.smoothScrollTo(0, 0);
        }
    }

    // Xử lý sự kiện click cho nút "Prev"
    private void handlePrevButton() {
        if (currentPage > 1) {
            currentPage--;
            adapter.updateData(pageDataList.get(currentPage - 1)); // Cập nhật dữ liệu trang mới
            createPageNumbers(); // Cập nhật lại số trang hiển thị

            mainLayout.smoothScrollTo(0, 0);
        }
    }
}
