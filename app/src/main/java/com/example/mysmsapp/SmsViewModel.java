package com.example.mysmsapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class SmsViewModel extends AndroidViewModel {


    private SmsRepository mRepository;

    public SmsViewModel(@NonNull Application application) {
        super(application);
        mRepository = new SmsRepository(application);
    }

    public void insert(SmsEntity sms) {
        mRepository.insert(sms);
    }


    LiveData<List<SmsEntity>> getAllSms() { return mRepository.getAllWords(); }

}
