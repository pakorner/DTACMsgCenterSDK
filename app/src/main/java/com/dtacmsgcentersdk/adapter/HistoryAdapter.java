package com.dtacmsgcentersdk.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dtacmsgcentersdk.R;

import java.util.List;

import th.co.dtac.digitalservices.msgcenter.model.History;
import th.co.dtac.digitalservices.msgcenter.utils.Shared;

public class HistoryAdapter extends ArrayAdapter<History> {

    private Context context;
    private List<History> items;
    private int viewResourceId;
    private LayoutInflater inflater;

    public HistoryAdapter(Context context, int viewResourceId, List<History> items) {
        super(context, viewResourceId, items);
        this.context = context;
        this.items = items;
        this.viewResourceId = viewResourceId;
        inflater = ((Activity)context).getLayoutInflater();
    }

    public void setItems(List<History> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(viewResourceId, null);
        }

        History history = items.get(position);

        if (Shared.getLang(context).equalsIgnoreCase("en")) {
            ((TextView)view.findViewById(R.id.tv_title)).setText("title : " + history.getTitle().getTitleEn());
            ((TextView)view.findViewById(R.id.tv_message)).setText("message : " + history.getMessage().getMessageEn());
        }else {
            ((TextView)view.findViewById(R.id.tv_title)).setText("title : " + history.getTitle().getTitleTh());
            ((TextView)view.findViewById(R.id.tv_message)).setText("message : " + history.getMessage().getMessageTh());
        }

        ((TextView)view.findViewById(R.id.tv_category)).setText("category : " + history.getCategory());
//        ((TextView)view.findViewById(R.id.tv_data_ref)).setText(
//                "data_ref : " + history.getDataRef().getUserId() + "\n" +
//                "data_ref : " + history.getDataRef().getGoto());
        ((TextView)view.findViewById(R.id.tv_data_ref)).setText("data_ref : " + history.getDataRef().toString());

        ((TextView)view.findViewById(R.id.tv_create_date)).setText("create_date : " + history.getCreateDate());

        return view;
    }
}