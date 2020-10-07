package comp3350.ei.presentation.orders;

import comp3350.ei.R;
import comp3350.ei.business.AccessStoreProducts;
import comp3350.ei.objects.OrderOut;
import comp3350.ei.business.AccessOrders;
import comp3350.ei.objects.Store;
import comp3350.ei.objects.StoreProduct;
import comp3350.ei.presentation.FieldParser;
import comp3350.ei.presentation.Messages;
import comp3350.ei.presentation.StoreProductParcel;
import comp3350.ei.presentation.SwipeController.SwipeButton;
import comp3350.ei.presentation.SwipeController.SwipeController;
import comp3350.ei.presentation.SwipeController.SwipeControllerActions;
import comp3350.ei.presentation.inventory.ProductActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OrderOutActivity extends Activity {
    public static final int CONFIRM_ORDER_REQUEST = 5;
    public static final int ADD_ITEM_REQUEST = 6;

    private final FieldParser parser = new FieldParser(this);
    private AccessOrders accessOrders;
    private OrderOut order;
    private StoreProductOrderAdapter productArrayAdapter;
    private List<StoreProduct> productList;
    private List<Integer> quantityList;
    SwipeController swipeController = null;
    private SwipeControllerActions swipeActions;
    int currentStoreProduct = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        accessOrders = new AccessOrders();
        order = new OrderOut(new Store());

        productList = new ArrayList<>();
        quantityList = new ArrayList<>();
        productArrayAdapter = new StoreProductOrderAdapter(this, productList, quantityList);

        try {
            setUpRecyclerView();
        } catch (final Exception e) {
            Messages.fatalError(this, e.getMessage());
        }
    }

    public void onClickBtnAddOrder(View view) {
        Intent confirm = new Intent(comp3350.ei.presentation.orders.OrderOutActivity.this, Messages.class);
        startActivityForResult(confirm, comp3350.ei.presentation.orders.OrderOutActivity.CONFIRM_ORDER_REQUEST);
    }

    public void onClickBtnAddItem(View view) {
        Intent addItems = new Intent(comp3350.ei.presentation.orders.OrderOutActivity.this, AddItemActivity.class);
        startActivityForResult(addItems, comp3350.ei.presentation.orders.OrderOutActivity.ADD_ITEM_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == comp3350.ei.presentation.orders.OrderOutActivity.CONFIRM_ORDER_REQUEST && resultCode == RESULT_OK) {
            if(addOrder()) {
                Intent viewProduct = new Intent(comp3350.ei.presentation.orders.OrderOutActivity.this,
                        ProductActivity.class);
                NavUtils.navigateUpTo(comp3350.ei.presentation.orders.OrderOutActivity.this, viewProduct);
                comp3350.ei.presentation.orders.OrderOutActivity.this.finish();
            }
        } else if (requestCode == comp3350.ei.presentation.orders.OrderOutActivity.ADD_ITEM_REQUEST && resultCode == RESULT_OK && data != null) {
            StoreProductParcel choose = data.getParcelableExtra("product");
            StoreProduct selected = choose.getStoreProduct();
            int quantity = parser.getIntFromString("quantity", data.getStringExtra("quantity"));

            if(quantity>selected.getQuantity()){
                Messages.fatalError(this, "the inventory is not enough for the supply.");
            }
            else if (selected != null) {
                order.addStoreProduct(selected, quantity);
                productList.add(selected);
                quantityList.add((Integer) quantity);
                productArrayAdapter.notifyDataSetChanged();
            } else
                Messages.fatalError(this, "no return from addItems.");
        }

    }

    private boolean addOrder() {
        if(createOrderFromEditText()&&order != null) {
            accessOrders.insertOrder(order);
            return true;
        }
        return false;
    }


    private void setUpRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.order_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(productArrayAdapter);

        setUpSwipeController();

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }

    private void setUpSwipeController() {
        swipeActions = new SwipeControllerActions() {
            @Override
            public void onLeftClicked(int position) {
                int amountToAdd = -1;
                currentStoreProduct = position;
                StoreProduct product = productList.get(currentStoreProduct);
                int quantityOrdered = quantityList.get(position);
                if (quantityOrdered + amountToAdd >= 0) {
                    quantityList.set(position, quantityOrdered + amountToAdd);
                    order.addStoreProduct(product, amountToAdd);
                    productArrayAdapter.notifyItemChanged(position);
                }
            }

            @Override
            public void onRightClicked(final int position) {
                int amountToAdd = 1;
                currentStoreProduct = position;
                StoreProduct product = productList.get(currentStoreProduct);
                int quantityOrdered = quantityList.get(position);
                quantityList.set(position, quantityOrdered + amountToAdd);
                order.addStoreProduct(product, amountToAdd);
                productArrayAdapter.notifyItemChanged(position);
            }
        };

        SwipeButton leftButton = new SwipeButton(Color.RED, Color.WHITE, "Remove 1", 60);
        SwipeButton rightButton = new SwipeButton(Color.GREEN, Color.WHITE, "Add 1", 60);

        swipeController = new SwipeController(swipeActions, leftButton, rightButton);
    }

    private boolean createOrderFromEditText() {
        EditText editYear = findViewById(R.id.change_year);
        EditText editMonth = findViewById(R.id.change_month);
        EditText editDay = findViewById(R.id.change_day);
        String orderYear = editYear.getText().toString();
        String orderMonth = editMonth.getText().toString();
        String orderDay = editDay.getText().toString();
        if(orderYear.length()==0){
            Messages.warning(this, "please enter order year");
            return false;
        }
        if(orderMonth.length()==0){
            Messages.warning(this, "please enter order month");
            return false;
        }
        if(orderDay.length()==0){
            Messages.warning(this, "please enter order day");
            return false;
        }

        if(orderMonth.length()==1)
            orderMonth="0"+orderMonth;
        if(orderDay.length()==1)
            orderDay="0"+orderDay;

        int year = Integer.parseInt(orderYear);
        int month = Integer.parseInt(orderMonth);
        int day = Integer.parseInt(orderDay);

        if(year<2000||year>2100) {
            Messages.warning(this, "Input format error: year");
            return false;
        }
        if(month>12||month<1) {
            Messages.warning(this, "Input format error: month");
            return false;
        }
        if(day<1||day>getDayOfMonth(year,month)) {
            Messages.warning(this, "Input format error: day");
            return false;
        }
        if(order.getProducts().size()==0){
            Messages.warning(this, "Empty product list");
            return false;
        }

        try {
            order.setYear(orderYear);
            order.setMonth(orderMonth);
            order.setDay(orderDay);
            order.finalizeOrder(new AccessStoreProducts());
        } catch (final Exception e) {
            Messages.fatalError(this, e.getMessage());
            return false;
        }
        return true;
    }

    private int getDayOfMonth(int year,int month){
        Calendar c = Calendar.getInstance();
        c.set(year, month, 0);
        return c.get(Calendar.DAY_OF_MONTH);
    }
}