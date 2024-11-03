package com.example.daltud2.Control;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.daltud2.R;

public class HeaderView extends LinearLayout {

    private LinearLayout header,searchBar;
    private ImageButton btnChange, logoQQ, searchButton;
    private Button btnDK, btnDN, searchComics;
    private EditText searchInput;
    private HeaderListener listener;
    private boolean isNotWhite = true;

    private ImageButton btnPopup;
    private Button buttonHome;

    public HeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.header, this, true);

        btnChange = (ImageButton) findViewById(R.id.btnChange);
        logoQQ = (ImageButton) findViewById(R.id.logoQQ);
        searchButton = (ImageButton) findViewById(R.id.search_button);
        btnDK = (Button) findViewById(R.id.btnDK);
        btnDN = (Button) findViewById(R.id.btnDN);
        searchInput = (EditText) findViewById(R.id.search_input);
        searchComics = (Button)  findViewById(R.id.search_Comics);
        header = (LinearLayout) findViewById(R.id.header);

        btnPopup = (ImageButton) findViewById(R.id.btnPopup);
        buttonHome = (Button) findViewById(R.id.buttonHome);


        searchButton.setOnClickListener(view -> {
            listener.onSearchButtonClicked();
        });

        btnDK.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onLoginButtonClicked();
                }
            }
        });

        btnDN.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onRegisterButtonClicked();
                }
            }
        });

        searchComics.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onSearchComicsClicked(searchInput.getText().toString());
                }
            }
        });

        btnChange.setOnClickListener(view -> {
            if (isNotWhite) {
                setBackgroundColor(Color.WHITE);
                logoQQ.setBackgroundColor(Color.WHITE);
                header.setBackgroundColor(Color.WHITE);
                btnChange.setImageResource(R.drawable.baseline_mode_night_24);
                btnChange.setBackground(null);
                searchButton.setBackground(null);
                logoQQ.setImageResource(R.mipmap.logoday);
            } else {
                setBackgroundColor(Color.BLACK);
                logoQQ.setBackgroundColor(Color.BLACK);
                header.setBackgroundColor(Color.BLACK);
                btnChange.setImageResource(R.drawable.baseline_light_mode_24);
                btnChange.setBackground(null);
                searchButton.setBackground(null);
                logoQQ.setImageResource(R.mipmap.logonight);
            }
            isNotWhite = !isNotWhite;

            if (listener != null) {
                listener.onUIChangeRequested();
            }
        });

        buttonHome.setOnClickListener(v -> {
            if (listener != null) {
                listener.onHomeButtonClicked();
            }
        });

        btnPopup.setOnClickListener(view -> {
            if (listener != null) {
                listener.onPopUpMenu();
            }
        });
    }

    // Thiết lập interface để giao tiếp với Activity
    public void setHeaderListener(HeaderListener listener) {
        this.listener = listener;
    }



    // Interface để định nghĩa các sự kiện
    public interface HeaderListener {
        void onUIChangeRequested();
        void onSearchButtonClicked();
        void onLoginButtonClicked();
        void onRegisterButtonClicked();
        void onSearchComicsClicked(String query);
        void onHomeButtonClicked();
        void onPopUpMenu();
    }



}
