package com.example.mysmsapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class SmsHomeActivity extends AppCompatActivity implements SmsBroadcastReceiver.SmsListener {


    private SmsRecyclerAdapter adapter;
    private List<SmsEntity> mAdapterList = new ArrayList<>();

    private static final int REQUEST_PERMISSION_KEY = 0;

    private final String PERMISSIONS[] = new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS,Manifest.permission.READ_PHONE_STATE};
    private SmsViewModel smsViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_home);

        SmsBroadcastReceiver.setListener(this);
        setUpView();

    }


    private void setUpView() {

        //TODO :: use databinding instead
        FloatingActionButton fab = findViewById(R.id.create_sms_btn);
        RecyclerView contentRecyclerView = findViewById(R.id.content_recycler_view);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SmsHomeActivity.this, SmsComposeActivity.class);
                startActivity(intent);
            }
        });

        contentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        contentRecyclerView.setHasFixedSize(true);
        adapter = new SmsRecyclerAdapter(mAdapterList, new BaseRecyclerAdapter.RecyclerClickListener() {
            @Override
            public void onClickAction(View view) {

            }
        });
        contentRecyclerView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!Utils.hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSION_KEY);
        } else {
            initViewHolder();
        }
    }


    public void initViewHolder(){
        smsViewModel = ViewModelProviders.of(this).get(SmsViewModel.class);
        smsViewModel.getAllSms().observe(this, observer);
    }

    Observer observer = new Observer<List<SmsEntity>>() {
        @Override
        public void onChanged(@Nullable final List<SmsEntity> smsEntityList) {

            mAdapterList.clear();
            mAdapterList.addAll(smsEntityList);
            adapter.notifyDataSetChanged();

        }
    };


    @Override
    public void onTextReceived(String add,String msg) {
        smsViewModel.insert(new SmsEntity( add,  msg));
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSION_KEY:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initViewHolder();
                } else {
                    Utils.showToast(this, "You must accept permissions for app to function properly");
                }
                break;
        }
    }

}
