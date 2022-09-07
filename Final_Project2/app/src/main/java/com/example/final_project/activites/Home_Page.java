package com.example.final_project.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.final_project.R;
import com.example.final_project.fragments.HomeFragment;
import com.example.final_project.listener.ICartLoadListener;
import com.example.final_project.listener.IResinLoadListener;
import com.example.final_project.models.CartModel;
import com.example.final_project.models.MyCartModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import butterknife.ButterKnife;

public class Home_Page extends AppCompatActivity  {

    Fragment homeFragments;
    FirebaseAuth auth;
    Toolbar toolbar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        auth = FirebaseAuth.getInstance();

        toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);

        homeFragments = new HomeFragment();
        loadFragments(homeFragments);


        //getSupportActionBar().hide();


    }

    private void loadFragments(Fragment homeFragments) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_container, homeFragments);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_logout) {

            auth.signOut();
            startActivity(new Intent(Home_Page.this, Sign_up_Page.class));
            finish();
        } else if (id == R.id.menu_my_cart) {

            startActivity(new Intent(Home_Page.this, CartActivity.class));
        }
        return true;
    }


}