package com.rakesh.mytractor;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.rakesh.mytractor.databinding.ActivityDasboardScreenBinding;

public class DasboardScreen extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityDasboardScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasboard_screen);
    }


}