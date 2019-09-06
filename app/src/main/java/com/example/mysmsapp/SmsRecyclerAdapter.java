package com.example.mysmsapp;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SmsRecyclerAdapter extends BaseRecyclerAdapter<SmsRecyclerAdapter.CustomViewHolder,SmsEntity> {


    public SmsRecyclerAdapter(List<SmsEntity> objectList,RecyclerClickListener listener) {
        super(objectList,listener);
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sms_list_item,parent,false);
        return new CustomViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        SmsEntity entity = getItem(position);

        holder.smsHeader.setText(entity.getAddress());
        String iconText = TextUtils.isEmpty(entity.getAddress()) ? "A" : String.valueOf(entity.getAddress().charAt(0));
        holder.iconText.setText(iconText);
        holder.smsMsg.setText(entity.getMsg());
        holder.timeStamp.setText(Utils.getTimeStamp(entity.getDate()));

    }


    public class CustomViewHolder extends RecyclerView.ViewHolder{

        TextView iconText,smsHeader,smsMsg,timeStamp;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            iconText = itemView.findViewById(R.id.icon_text);
            smsHeader = itemView.findViewById(R.id.sms_header);
            smsMsg = itemView.findViewById(R.id.sms_mssg);
            timeStamp = itemView.findViewById(R.id.time_stamp);

        }
    }
}
