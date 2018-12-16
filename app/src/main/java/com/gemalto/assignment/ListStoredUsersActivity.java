package com.gemalto.assignment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.gemalto.assignment.data.User;
import com.gemalto.assignment.repository.UserRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import timber.log.Timber;

public class ListStoredUsersActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Inject
    GemaltoApi gemaltoApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        setContentView(R.layout.activity_list_stored_users);
        initViews();
        Timber.d("UserRepo " +gemaltoApi.getUserRepository().hashCode());
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        setRecyclerView(gemaltoApi.getUserRepository().getLocalUsers().getValue());
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }


    private void setRecyclerView(List<User> users){
        mAdapter = new RemoteUserRecyclerViewAdapter(users);
        recyclerView.setAdapter(mAdapter);
    }

    private void initViews(){
        initBindings();
        initRecyclerView();
        initToolbar();
    }

    private void initToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initRecyclerView(){
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
    }

    private void initBindings(){
        recyclerView = findViewById(R.id.user_recycler_view);
        toolbar = findViewById(R.id.toolbar);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
