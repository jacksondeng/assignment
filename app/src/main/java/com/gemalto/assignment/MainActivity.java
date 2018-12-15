package com.gemalto.assignment;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;


import com.gemalto.assignment.api.ApiResponse;
import com.gemalto.assignment.api.GemaltoApi;
import com.gemalto.assignment.data.User;
import com.gemalto.assignment.db.UserDb;
import com.gemalto.assignment.repository.UserRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jacksondeng on 15/12/18.
 */

public class MainActivity extends DaggerAppCompatActivity {

    @Inject
    UserRepository userRepository;
    @Inject
    GemaltoApi gemaltoApi;
    @Inject
    UserDb userDb;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedBundleInstance){
        super.onCreate(savedBundleInstance);
        AndroidInjection.inject(this);
        setContentView(R.layout.activity_main);
        initViews();
        userRepository.getQueriedUsers().observe(this, this::setRecyclerView);
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        listStoredUsers();
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }


    private void initViews(){
        initRecyclerView();
        initToolbar();
        initNavigationView();
        initDrawerLayout();
    }

    private void initRecyclerView(){
        recyclerView = findViewById(R.id.user_recycler_view);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
    }

    private void initToolbar(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initDrawerLayout(){
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initNavigationView(){
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch(id)
            {
                case R.id.query_user:
                    queryUser();
                    drawerLayout.closeDrawers();
                    return true;
                case R.id.view_stored_user:
                    listStoredUsers();
                    drawerLayout.closeDrawers();
                    return true;
                case R.id.exit:
                    drawerLayout.closeDrawers();
                    return true;
                default:
                    return true;
            }
        });
    }

    private void queryUser(){
        gemaltoApi.getMultipleRandomUsers(10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(apiResponse -> {
                    for(User user : apiResponse.getUser()){
                        user.setSeed(apiResponse.getInfo().getSeed());
                    }
                    setRecyclerView(apiResponse.getUser());
                });
    }

    private void setRecyclerView(List<User> users){
        mAdapter = new UserRecyclerViewAdapter(users);
        recyclerView.setAdapter(mAdapter);
    }

    private void listStoredUsers(){
        new Thread(() -> {
            List<User> users;
            users = userDb.userDao().listAllUsers();
            userRepository.getQueriedUsers().postValue(users);
        }) .start();
    }

}
