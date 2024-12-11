package com.example.daltud2.View;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daltud2.Control.DataBaseSQLLite;
import com.example.daltud2.Model.Truyen;
import com.example.daltud2.R;

public class TrinhDocTruyen extends AppCompatActivity {
    TextView tvDuongDanHienTai,tvTuaDe;
    Button btnChapTruoc, btnChapSau;
    private RecyclerView rvImgTruyen;
    private DataBaseSQLLite dataBaseSQLLite;
    private  RecyclerView.Adapter ImgTruyenAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trinh_doc_truyen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        String idChapter = getIntent().getStringExtra("idChapter");


        Controls();

    }
    private void Controls(){
        tvDuongDanHienTai = findViewById(R.id.tvDuongDanHienTai);
        tvTuaDe = findViewById(R.id.tvTuaDe);
    }
    private void setThongTinTruyen(Truyen truyen) {

    }
}