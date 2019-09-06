package com.example.mysmsapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class SmsViewModel extends AndroidViewModel {


    private SmsRepository mRepository;
//    private LiveData<List<SmsEntity>> mAllSms;

    public SmsViewModel(@NonNull Application application) {
        super(application);
        mRepository = new SmsRepository(application);
//        mAllSms = mRepository.getAllWords();
    }

    public void insert(SmsEntity sms) {
        mRepository.insert(sms);
    }


    LiveData<List<SmsEntity>> getAllSms() { return mRepository.getAllWords(); }

}
