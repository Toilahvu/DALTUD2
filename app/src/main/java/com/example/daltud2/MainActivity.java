package com.example.daltud2;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private LinearLayout searchBar,mainLayout,header;
    private EditText searchInput;
    private ImageButton searchButton,btnChange,logoQQ;
    private ScrollView scroll;

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

        searchBar = (LinearLayout) findViewById(R.id.search_bar);
        searchInput = (EditText) findViewById(R.id.search_input);
        searchButton = (ImageButton) findViewById(R.id.search_button);
        btnChange = (ImageButton) findViewById(R.id.btnChange);
        logoQQ = (ImageButton) findViewById(R.id.logoQQ);
        mainLayout = (LinearLayout) findViewById(R.id.main);
        header = (LinearLayout) findViewById(R.id.header);

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
                int currentColor = ((ColorDrawable) mainLayout.getBackground()).getColor();
                if(currentColor == Color.WHITE){
                    mainLayout.setBackgroundColor(Color.BLACK);
                    logoQQ.setBackgroundColor(Color.BLACK);
                    header.setBackgroundColor(Color.BLACK);
                    searchButton.setBackgroundColor(Color.BLACK);
                    btnChange.setImageResource(R.drawable.baseline_light_mode_24);
                    btnChange.setBackgroundColor(Color.BLACK);
                    logoQQ.setImageResource(R.mipmap.logonight);
                }else{
                    mainLayout.setBackgroundColor(Color.WHITE);
                    logoQQ.setBackgroundColor(Color.WHITE);
                    header.setBackgroundColor(Color.WHITE);
                    searchButton.setBackgroundColor(Color.WHITE);
                    btnChange.setBackgroundColor(Color.WHITE);
                    btnChange.setImageResource(R.drawable.baseline_mode_night_24);
                    logoQQ.setImageResource(R.mipmap.logoday);
                }
            }
        });
    }
}