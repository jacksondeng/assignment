package com.gemalto.assignment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.gemalto.assignment.data.User;
import com.gemalto.assignment.db.UserDb;
import com.gemalto.assignment.repository.UserRepository;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;
import javax.inject.Inject;
import dagger.android.AndroidInjection;
import dagger.android.support.DaggerAppCompatActivity;
import timber.log.Timber;


/**
 * Created by jacksondeng on 15/12/18.
 */

public class UserDashboardActivity extends DaggerAppCompatActivity {

    private TextView userId;
    private TextView userName;
    private TextView userGender;
    private TextView userAge;
    private TextView userDob;
    private TextView userEmail;
    private User user;
    private Button btnStore;
    private Button btnDelete;
    private ImageView profilePic;

    @Inject
    UserDb userDb;
    @Inject
    UserRepository userRepository;


    @Override
    protected void onCreate(Bundle savedBundleInstance){
        super.onCreate(savedBundleInstance);
        AndroidInjection.inject(this);
        setContentView(R.layout.activity_user_dashboard);
        user = getIntent().getParcelableExtra("user");
        initViews();
        initListeners();
    }

    @Override
    protected void onPause(){
        super.onPause();
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
        initBindings();
        userId.setText(user.getSeed());
        userName.setText(user.getrealUsername());
        userGender.setText(user.getGender());
        userDob.setText(user.getDobStr());
        userAge.setText(String.valueOf(user.getAge()));
        userEmail.setText(user.getEmail());
        if(user.getIsStored() == 0){
            btnStore.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.GONE);
        }
        else{
            btnStore.setVisibility(View.GONE);
            btnDelete.setVisibility(View.VISIBLE);
        }
        loadProfilePic();
    }

    private void initListeners(){
        btnStore.setOnClickListener(view -> {
            user.setIsStored(1);
            storeUser(user);
        });

        btnDelete.setOnClickListener(view ->{
            deleteUser(user);
        });
    }

    private void initBindings(){
        userId = findViewById(R.id.user_id);
        userName = findViewById(R.id.user_name);
        userGender = findViewById(R.id.user_gender);
        userAge = findViewById(R.id.user_age);
        userDob = findViewById(R.id.user_dob);
        userEmail = findViewById(R.id.user_email);
        btnStore = findViewById(R.id.btn_store);
        btnDelete = findViewById(R.id.btn_delete);
        profilePic = findViewById(R.id.profile_pic);
    }

    private void storeUser(final User user){
        new Thread(() -> {
            userDb.userDao().storeUser(user);
            listStoredUsers();
        }).start();
    }

    private void deleteUser(final User user){
        new Thread(() -> {
            userDb.userDao().deleteUser(user);
            listStoredUsers();
        }).start();
    }

    private void listStoredUsers(){
        new Thread(() -> {
            List<User> users;
            users = userDb.userDao().listAllUsers();
            userRepository.getQueriedUsers().postValue(users);
            finish();
        }) .start();
    }

    private void loadProfilePic(){
        Picasso.get().load(user.getProfilePicUrl()).tag("ProfilePic").fit().centerCrop().into(profilePic, new Callback() {
            @Override
            public void onSuccess() {
                Timber.tag("Picasso load success").d(" ");
            }

            @Override
            public void onError(Exception e) {
                Timber.tag("Picasso load failed ").d(" ");
            }
        });
    }
}
