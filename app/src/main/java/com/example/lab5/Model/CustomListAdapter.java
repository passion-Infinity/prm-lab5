package com.example.lab5.Model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lab5.R;

import java.util.List;

public class CustomListAdapter extends BaseAdapter {

    private List<MyCredit> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomListAdapter() {
    }

    public CustomListAdapter(Context aContext, List<MyCredit> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_layout, null);
            holder = new ViewHolder();
            holder.flagTransaction = (ImageView) convertView.findViewById(R.id.textView_transaction_listview);
            holder.titleView = (TextView) convertView.findViewById(R.id.textView_title_listview);
            holder.timeView = (TextView) convertView.findViewById(R.id.textView_time_listview);
            holder.cashView = (TextView) convertView.findViewById(R.id.textView_cash_listview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MyCredit myCredit = this.listData.get(position);
        holder.titleView.setText("Title: "+myCredit.getTitle());
        holder.timeView.setText("Time: "+myCredit.getTrackTime());
        holder.cashView.setText("Cash: "+myCredit.getCash());

        if(myCredit.getTypeTransaction()==1){
            int imageId = this.getMipmapResIdByName("up");
            holder.flagTransaction.setImageResource(imageId);
        }else{
            int imageId = this.getMipmapResIdByName("down");
            holder.flagTransaction.setImageResource(imageId);
        }

        return convertView;
    }

    // Find Image ID corresponding to the name of the image (in the directory mipmap).
    public int getMipmapResIdByName(String resName)  {
        String pkgName = context.getPackageName();
        // Return 0 if not found.
        int resID = context.getResources().getIdentifier(resName , "mipmap", pkgName);
        Log.i("CustomListView", "Res Name: "+ resName+"==> Res ID = "+ resID);
        return resID;
    }

    static class ViewHolder {
        ImageView flagTransaction;
        TextView titleView;
        TextView timeView;
        TextView cashView;
    }
}
