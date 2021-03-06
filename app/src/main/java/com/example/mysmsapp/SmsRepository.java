package com.example.mysmsapp;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class SmsRepository {

    private MutableLiveData<List<SmsEntity>> mAllSms = new MutableLiveData<>();
    private Context mAppContext;

    SmsRepository(Application application) {
        mAppContext = application;
    }

    MutableLiveData<List<SmsEntity>> getAllWords() {
        LoadSms loadSms = new LoadSms();
        loadSms.execute();
        return mAllSms;
    }

    public void insert(SmsEntity smsEntity) {
        new insertAsyncTask().execute(smsEntity);
    }

    private class insertAsyncTask extends AsyncTask<SmsEntity, Void, Void> {

        @Override
        protected Void doInBackground(SmsEntity... sms) {
            Uri newUri;
            ContentValues newValues = new ContentValues();

            newValues.put(AppConstants.KEY_MSG_BODY, sms[0].getMsg());
            newValues.put(AppConstants.KEY_DATE, sms[0].getDate());
            newValues.put(AppConstants.KEY_ADDRESS, sms[0].getAddress());

            newUri = mAppContext.getContentResolver().insert(
                    Uri.parse("content://sms/sent"),
                    newValues
            );
            return null;
        }

    }

    class LoadSms extends AsyncTask<String, Void, List<SmsEntity>> {

        List<SmsEntity> mAdapterList = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mAdapterList.clear();
        }

        protected List<SmsEntity> doInBackground(String... args) {
            try {
                Uri uriInbox = Uri.parse("content://sms/");

                Cursor c = mAppContext.getContentResolver().query(uriInbox, null, "address IS NOT NULL) GROUP BY (thread_id", null, null); // 2nd null = "address IS NOT NULL) GROUP BY (address"
//                Uri uriSent = Uri.parse("content://sms/sent");
//                Cursor sent = getContentResolver().query(uriSent, null, "address IS NOT NULL) GROUP BY (thread_id", null, null); // 2nd null = "address IS NOT NULL) GROUP BY (address"
//                Cursor c = new MergeCursor(new Cursor[]{inbox,sent}); // Attaching inbox and sent sms

                if (c.moveToFirst()) {
                    for (int i = 0; i < c.getCount(); i++) {

                        String _id = c.getString(c.getColumnIndexOrThrow(AppConstants._ID));
                        String thread_id = c.getString(c.getColumnIndexOrThrow(AppConstants.KEY_THREAD_ID));
                        String msg = c.getString(c.getColumnIndexOrThrow(AppConstants.KEY_MSG_BODY));
                        String type = c.getString(c.getColumnIndexOrThrow(AppConstants.KEY_TYPE));
                        String date = c.getString(c.getColumnIndexOrThrow(AppConstants.KEY_DATE));
                        String user = c.getString(c.getColumnIndexOrThrow(AppConstants.KEY_ADDRESS));

                        mAdapterList.add(new SmsEntity(_id, thread_id, user, type, date, msg));

                        c.moveToNext();
                    }
                }
                c.close();

            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

//            Collections.sort(smsList, new MapComparator(Function.KEY_TIMESTAMP, "dsc")); // Arranging sms by timestamp decending
//            ArrayList<HashMap<String, String>> purified = Function.removeDuplicates(smsList); // Removing duplicates from inbox & sent
//            smsList.clear();
//            smsList.addAll(purified);


            return mAdapterList;

        }

        @Override
        protected void onPostExecute(List<SmsEntity> list) {
            mAllSms.postValue(list);
        }
    }

}

