package comp3350.ei.presentation.orders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import comp3350.ei.R;
import comp3350.ei.business.AccessOrders;
import comp3350.ei.objects.Order;
import comp3350.ei.objects.OrderIn;
import comp3350.ei.objects.OrderOut;
import comp3350.ei.objects.StoreProduct;
import comp3350.ei.presentation.Messages;

public class ViewOrderActivity extends Activity {
    private Order select;
    private HashMap<StoreProduct, Integer> orderItemList;
    private List<StoreProduct> products;
    private List<Integer> quantities;
    private AccessOrders accessOrders;
    private StoreProductOrderAdapter productArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);
        Intent intent = getIntent();
        products = new ArrayList<>();
        quantities = new ArrayList<>();
        accessOrders = new AccessOrders();

        select = accessOrders.getOrder(intent.getIntExtra("orderId",0));
        orderItemList = select.getProducts();

        for (Map.Entry<StoreProduct, Integer> entry : orderItemList.entrySet()) {
            products.add(entry.getKey());
            quantities.add(entry.getValue());
        }

        productArrayAdapter = new StoreProductOrderAdapter(this, products, quantities);

        try{
            setEditFieldsWithCurrInfo();
            RecyclerView productList = findViewById(R.id.order_products);
            productList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            productList.setAdapter(productArrayAdapter);
        }catch(final Exception e){
            Messages.fatalError(this, e.getMessage());
        }
    }

    private void setEditFieldsWithCurrInfo() {
        ((EditText) findViewById(R.id.change_orderStore)).setText(select.getStore().getStoreId()+"");
        ((EditText) findViewById(R.id.change_year)).setText(select.getYear());
        ((EditText) findViewById(R.id.change_month)).setText(select.getMonth());
        ((EditText) findViewById(R.id.change_day)).setText(select.getDay());
    }
}
