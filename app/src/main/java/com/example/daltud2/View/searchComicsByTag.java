package com.example.daltud2.View;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.daltud2.Control.HeaderView;
import com.example.daltud2.R;

public class searchComicsByTag extends AppCompatActivity implements HeaderView.HeaderListener {

    private HeaderView headerView;
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

        headerView.setHeaderListener(this);



    }

    //region methods
    private void declareVal(){
        headerView = findViewById(R.id.headerViewByTag);
    }
    //endregion

    //region Interface

    @Override
    public void onUIChangeRequested() {

    }

    @Override
    public void onSearchButtonClicked() {

    }

    @Override
    public void onLoginButtonClicked() {

    }

    @Override
    public void onRegisterButtonClicked() {

    }

    @Override
    public void onSearchComicsClicked(String query) {

    }

    @Override
    public void onHomeButtonClicked() {

    }

    @Override
    public void onPopUpMenu() {

    }

    //endregion
}