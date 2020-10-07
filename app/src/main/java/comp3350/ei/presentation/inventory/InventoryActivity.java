package comp3350.ei.presentation.inventory;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.ArrayAdapter;

import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import comp3350.ei.R;
import comp3350.ei.business.AccessStoreProducts;
import comp3350.ei.objects.StoreProduct;
import comp3350.ei.presentation.Messages;
import comp3350.ei.presentation.StoreProductParcel;
import comp3350.ei.presentation.SwipeController.*;

public class InventoryActivity extends Activity implements SearchView.OnQueryTextListener {
    private StoreProductAdapter productArrayAdapter;
    private List<StoreProduct> productList;
    private SwipeController swipeController = null;

    private AccessStoreProducts accessStoreProducts;
    private List<String> categories;
    private Spinner dropdown = null;
    private int currentStoreProduct = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        currentStoreProduct = 0;
        accessStoreProducts = new AccessStoreProducts();
        productList = new ArrayList<>();
        productList.addAll(accessStoreProducts.getStoreProducts());

        productArrayAdapter = new StoreProductAdapter(this, productList);

        try {
            setupRecyclerView();
            setUpDropDownFilter();
            setUpSearch();
        }
        catch (final Exception e) {
            Messages.fatalError(this, e.getMessage());
        }
    }

    private void setUpSearch() {
        SearchView editSearch = findViewById(R.id.search);
        editSearch.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        productArrayAdapter.filterByName(newText);

        return false;
    }

    public void onClickBtnAddProduct(View view) {
        Intent addProduct = new Intent(InventoryActivity.this, AddProductActivity.class);
        InventoryActivity.this.startActivity(addProduct);
    }

    private void setUpDropDownFilter() {
        dropdown = findViewById(R.id.spinner_category);
        categories = accessStoreProducts.getStoreProductCategories();
        categories.add(0, "All");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        // Apply the adapter to the spinner
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                int index = arg0.getSelectedItemPosition();
                productArrayAdapter.filterByCategory(categories.get(index));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

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
        SwipeControllerActions swipeActions = new SwipeControllerActions() {
            @Override
            public void onLeftClicked(final int position) {
                currentStoreProduct = position;
                System.out.println(currentStoreProduct);

                StoreProduct selected = productArrayAdapter.getFilterItem(currentStoreProduct);
                accessStoreProducts.deleteStoreProduct(selected);
                productArrayAdapter.removeItem(currentStoreProduct);
            }

            @Override
            public void onRightClicked(final int position) {
                StoreProduct selected = productList.get(position);
                Intent editProduct = new Intent(InventoryActivity.this,
                        EditProductActivity.class);
                editProduct.putExtra("StoreProduct", new StoreProductParcel(selected));

                InventoryActivity.this.startActivity(editProduct);
            }
        };

        SwipeButton leftButton = new SwipeButton(Color.RED, Color.WHITE, "Delete", 60);
        SwipeButton rightButton = new SwipeButton(Color.GREEN, Color.WHITE, "Edit", 60);

        swipeController = new SwipeController(swipeActions, leftButton, rightButton);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        currentStoreProduct = 0;
        productList.clear();
        productList.addAll(accessStoreProducts.getStoreProducts());
        //update the changes of product to to listView
        productArrayAdapter.updateDate(productList);
        setUpDropDownFilter();  //update new category
    }

}
