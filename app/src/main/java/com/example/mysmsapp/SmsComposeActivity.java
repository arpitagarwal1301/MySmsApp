package com.example.mysmsapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;

public class SmsComposeActivity extends AppCompatActivity {


    private final int REQUEST_SEND_SMS = 103;
    private SmsViewModel smsViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_compose);

        smsViewModel = ViewModelProviders.of(this).get(SmsViewModel.class);
        setUpViews();

    }

    private void setUpViews() {

        //TODO :: use butterknife in prod app
        Button button = findViewById(R.id.send_button);
        final EditText toEditText = findViewById(R.id.contact_edit_text);
        final EditText msgEditText = findViewById(R.id.msg_edit_text);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String msg = toEditText.getText().toString().trim();
                String address =  msgEditText.getText().toString().trim();

                if (sendSMS(msg,address)) {
                    Utils.showToast(view.getContext(), "Message sent");
                    smsViewModel.insert(new SmsEntity(address,  msg));
                } else {
                    Utils.showToast(view.getContext(), "Message not sent");
                }

            }
        });

    }



    private boolean sendSMS(String toPhoneNumber, String smsMessage) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestSendSmsPermission();
        } else {
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(toPhoneNumber, null, smsMessage, null, null);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    private void requestSendSmsPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.SEND_SMS)) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.sms_permission_rationale);
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(SmsComposeActivity.this, new String[]{Manifest.permission.SEND_SMS},
                            REQUEST_SEND_SMS);
                }
            });
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    dialog.dismiss();
                }
            });
            builder.create().show();

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},
                    REQUEST_SEND_SMS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_SEND_SMS) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Utils.showToast(this, "Permission Granted");
            } else {
                Utils.showToast(this, "Permission Not Granted");
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }


    }
}
