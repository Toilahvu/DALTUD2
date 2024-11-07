package com.example.daltud2.Control;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daltud2.LoginActivity;
import com.example.daltud2.R;
import com.example.daltud2.RegisterActivity;
import com.example.daltud2.View.MainActivity;
import com.example.daltud2.View.searchByRank;
import com.example.daltud2.View.searchComicsByTag;

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
        btnPopup = (ImageButton) findViewById(R.id.btnPopup);
        buttonHome = (Button) findViewById(R.id.buttonHome);
        searchButton = (ImageButton) findViewById(R.id.search_button);
        btnDK = (Button) findViewById(R.id.btnDK);
        btnDN = (Button) findViewById(R.id.btnDN);
        searchInput = (EditText) findViewById(R.id.search_input);
        searchComics = (Button)  findViewById(R.id.search_Comics);
        header = (LinearLayout) findViewById(R.id.header);


        searchButton.setOnClickListener(view -> {
            if (searchBar.getVisibility() == View.GONE) {
                searchBar.setVisibility(View.VISIBLE);
                Animation slideDown = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down);
                searchBar.startAnimation(slideDown);
            } else {
                searchBar.setVisibility(View.GONE);
            }

            if (listener != null) {
                listener.onSearchButtonClicked();
            }

        });

        btnDK.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                getContext().startActivity(intent);
            }
        });

        btnDN.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RegisterActivity.class);
                getContext().startActivity(intent);
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
                //setBackgroundColor(Color.WHITE);
                logoQQ.setBackgroundColor(Color.WHITE);
                header.setBackgroundColor(Color.WHITE);
                btnChange.setImageResource(R.drawable.baseline_mode_night_24);
                btnChange.setBackground(null);
                searchButton.setBackground(null);
                logoQQ.setImageResource(R.mipmap.logoday);


            } else {
                //setBackgroundColor(Color.BLACK);
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
            ObjectAnimator rotateAnimation = ObjectAnimator.ofFloat(btnPopup, "rotation", 0f, 180f);
            rotateAnimation.setDuration(500);
            rotateAnimation.start();

            PopupMenu popupMenu = new PopupMenu(getContext(), btnPopup);
            popupMenu.getMenuInflater().inflate(R.menu.menu_item, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                Intent intent;
                if (menuItem.getItemId() == R.id.item_1) {
                    intent = new Intent(getContext(), searchComicsByTag.class);
                    getContext().startActivity(intent);
                } else if (menuItem.getItemId() == R.id.item_2) {
                    intent = new Intent(getContext(), searchByRank.class);
                    getContext().startActivity(intent);
                } else if (menuItem.getItemId() == R.id.item_3) {
                    intent = new Intent(getContext() ,searchComicsByTag.class);
                    getContext().startActivity(intent);
                } else return false;
                return true;
            });
            popupMenu.show();

            popupMenu.setOnDismissListener(popup -> {
                ObjectAnimator rotateBack = ObjectAnimator.ofFloat(btnPopup, "rotation", 180f, 0f);
                rotateBack.setDuration(300);
                rotateBack.start();
            });
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
        void onSearchComicsClicked(String query);
        void onHomeButtonClicked();
    }



}
