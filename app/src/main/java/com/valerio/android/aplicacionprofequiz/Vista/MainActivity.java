package com.valerio.android.aplicacionprofequiz.Vista;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toolbar;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.valerio.android.aplicacionprofequiz.R;
import com.valerio.android.aplicacionprofequiz.Vista.modelo.Top;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {


    //FloatingActionButton fab;
    DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;
    AutoCompleteTextView autoCompleteTextView;
    TextInputLayout textInputLayout;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /////////////////////////////////////////////////////////////////////////////////////




        ////////////////////////////////////////////////////////////////////////////////

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        //fab = findViewById(R.id.fab);
        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view); //NAVIGATION VIEW //////////////////

        Toolbar toolbar = findViewById(R.id.toolbar); //API 34
        setSupportActionBar(toolbar);

        // Enable Up button for Navigation
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_home_24);  // make sure you have this icon in your drawable resources
        drawerLayout = findViewById(R.id.drawer_layout); //ya esta inicializado

        // Set up NavigationView
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();

            menuItem.setChecked(true);
            drawerLayout.closeDrawer(GravityCompat.START);  // Cierra el drawer después de seleccionar un ítem

            if (id == R.id.nav_settings) {
                // Inicia SettingsActivity cuando se selecciona "Settings"
                // Intent intent = new Intent(MainActivity.this, MuestraActivity.class);
                //startActivity(intent);

            } else if (id == R.id.nav_home) {
                // Por ejemplo, reemplaza el fragmento actual por HomeFragment
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                //replaceFragment(new HomeFragment());
            } else if (id == R.id.nav_share) {
                // Aquí puedes agregar la acción para "Share"
                // Intent intent = new Intent(MainActivity.this, RegistroActivity.class);
                // startActivity(intent);

            } else if (id == R.id.nav_about) {
                // Aquí puedes agregar la acción para "About Us"
                //Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                //startActivity(intent);
            }else if(id == R.id.nav_feedback){
                //Intent intent = new Intent(MainActivity.this, IntroActivity.class);
                //startActivity(intent);
            }

            // Asegura que el ítem seleccionado se muestre como activo
            menuItem.setChecked(true);


            return true;
        });
        //////////////////////////////////


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();
            //navigationView.getCheckedItem(R.id.nav_home);
            navigationView.setCheckedItem(R.id.nav_home);
        }


        replaceFragment(new HomeFragment());
        bottomNavigationView.setBackground(null);
        //replaceFragment(new HomeFragment());
        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(item -> {

            Fragment selectedFragment = null;
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.shorts) {
                selectedFragment = new DetailFragment()  ;
            } else if (itemId == R.id.subscriptions) {
                selectedFragment = new SubscriptionsFragment();
            } else if (itemId == R.id.library) {
                selectedFragment = new LibraryFragment();
            }

            if (selectedFragment != null) {
                replaceFragment(selectedFragment);


            }
            return true;
        });
        /*
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomDialog();
            }
        });
         */

    }


    /////////////////////
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    ///////////////////////////////////////////////
    //outside onCreate
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }


  /*  public void openDetailFragment(Top top) {
        DetailFragment detailFragment = DetailFragment.newInstance(top);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, detailFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        // Seleccionar la opción de perfil de docente en la barra de navegación
        bottomNavigationView.setSelectedItemId(R.id.shorts);
    }*/




    private void showBottomDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout);

        LinearLayout videoLayout = dialog.findViewById(R.id.layoutVideo);
        LinearLayout shortsLayout = dialog.findViewById(R.id.layoutShorts);
        LinearLayout liveLayout = dialog.findViewById(R.id.layoutLive);
        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);

        videoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Upload a Video is clicked", Toast.LENGTH_SHORT).show();

            }
        });

        shortsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Create a short is Clicked", Toast.LENGTH_SHORT).show();

            }
        });

        liveLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Go live is Clicked", Toast.LENGTH_SHORT).show();

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }



}
