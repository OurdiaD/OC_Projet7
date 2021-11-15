package com.openclassrooms.go4lunch.ui.main;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.openclassrooms.go4lunch.R;
import com.openclassrooms.go4lunch.databinding.ActivityMainBinding;
import com.openclassrooms.go4lunch.datas.repositories.PlaceRepository;
import com.openclassrooms.go4lunch.datas.repositories.UserRepository;
import com.openclassrooms.go4lunch.models.User;
import com.openclassrooms.go4lunch.ui.LoginActivity;
import com.openclassrooms.go4lunch.ui.PreferenceActivity;
import com.openclassrooms.go4lunch.ui.details.DetailsActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        initNav();
        initApiMaps();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(searchListener());
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_lunch){
            UserRepository.getInstance().getUserData().addOnCompleteListener(task -> {
                User userData = task.getResult().toObject(User.class);
                if (userData != null && userData.getPlaceId() != null) {
                    startDetailsActivity(userData);
                }
            });
        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(this, PreferenceActivity.class);
            ActivityCompat.startActivity(this, intent, null);
        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        //this.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    public void initNav(){
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        navigationView.setNavigationItemSelectedListener(this);
        BottomNavigationView bottomNavView = findViewById(R.id.nav_bottom);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_map, R.id.navigation_list, R.id.navigation_workmates)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavView, navController);

        View headerNav = navigationView.getHeaderView(0);
        TextView nameNav = headerNav.findViewById(R.id.nav_name);
        TextView emaiNav = headerNav.findViewById(R.id.nav_email);
        ImageView picNav = headerNav.findViewById(R.id.nav_pic);
        if (user != null){
            nameNav.setText(user.getDisplayName());
            emaiNav.setText(user.getEmail());
            Glide.with(getBaseContext())
                    .load(user.getPhotoUrl())
                    .circleCrop()
                    .into(picNav);
        }
    }

    public void initApiMaps() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                    PackageManager.PERMISSION_GRANTED);
        }
    }

    public SearchView.OnQueryTextListener searchListener() {
        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 2){
                    PlaceRepository.getInstance().searchPlace(newText);
                } else {
                    PlaceRepository.getInstance().resetSearch();
                }
                return false;
            }
        };
    }

    public void startDetailsActivity(User user){
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("place_id", user.getPlaceId());
        ActivityCompat.startActivity(this, intent, null);
    }
}