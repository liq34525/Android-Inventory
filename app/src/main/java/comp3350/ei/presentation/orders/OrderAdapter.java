package comp3350.ei.presentation.orders;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import comp3350.ei.R;
import comp3350.ei.objects.Order;

class OrderAdapter extends BaseAdapter {
    private List<Order> mData;
    private Context mContext;

    public OrderAdapter(List<Order> orders, Context context) {
        this.mData = orders;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_order_list, parent, false);
        TextView txt_id = convertView.findViewById(R.id.text_order_id);
        TextView txt_date = convertView.findViewById(R.id.text_order_date);
        txt_id.setText(String.valueOf(mData.get(position).getOrderId()));

        String year = mData.get(position).getYear();
        String month = mData.get(position).getMonth();
        String day = mData.get(position).getDay();

        String date = year;
        if (!year.isEmpty() && !month.isEmpty()) {
            date += "/";
        }

        date += month;
        if (!date.isEmpty() && !day.isEmpty()) {
            date += "/";
        }

        date += day;

        txt_date.setText(date);

        return convertView;
    }

    public Order getOrderAtPosition(int position){ return mData.get(position); }
}
