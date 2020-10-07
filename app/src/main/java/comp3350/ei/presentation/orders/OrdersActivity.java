package comp3350.ei.presentation.orders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import comp3350.ei.business.AccessOrders;
import comp3350.ei.objects.Order;

import java.util.ArrayList;
import java.util.List;

import comp3350.ei.R;
import comp3350.ei.presentation.Messages;


public class OrdersActivity extends Activity {
    private AccessOrders accessOrders;
    private List<Order> orderList = null;
    private OrderAdapter orderAdapter = null;
    private ListView list_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        accessOrders = new AccessOrders();
        orderList = new ArrayList<>();
        orderList.addAll(accessOrders.getOrders());

        try {
            list_order = findViewById(R.id.list_orders);
            orderAdapter = new OrderAdapter(orderList, OrdersActivity.this);
            list_order.setAdapter(orderAdapter);
            list_order.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Order theOrder = orderAdapter.getOrderAtPosition(i);
                   Intent viewOrder = new Intent(OrdersActivity.this,ViewOrderActivity.class);
                   viewOrder.putExtra("orderId",theOrder.getOrderId());
                   OrdersActivity.this.startActivity(viewOrder);
                    return true;
                }
            });
        } catch (final Exception e) {
            Messages.fatalError(this, "Create crashed " + e.getMessage());
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        accessOrders = new AccessOrders();
        orderList = new ArrayList<>();
        orderList.addAll(accessOrders.getOrders());
        try {
            list_order = findViewById(R.id.list_orders);
            orderAdapter = new OrderAdapter(orderList, OrdersActivity.this);
            list_order.setAdapter(orderAdapter);
            list_order.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Order theOrder = orderAdapter.getOrderAtPosition(i);
                    Intent viewOrder = new Intent(OrdersActivity.this,ViewOrderActivity.class);
                    viewOrder.putExtra("orderId",theOrder.getOrderId());
                    OrdersActivity.this.startActivity(viewOrder);
                    return true;
                }
            });
        } catch (final Exception e) {
            Messages.fatalError(this, e.getMessage());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    public void onClickBtnOrderIn(View view) {
        Intent orders = new Intent(OrdersActivity.this, OrderInActivity.class);
        OrdersActivity.this.startActivity(orders);
    }

    public void onClickBtnOrderOut(View view) {
        Intent orders = new Intent(OrdersActivity.this, OrderOutActivity.class);
        OrdersActivity.this.startActivity(orders);
    }
}
