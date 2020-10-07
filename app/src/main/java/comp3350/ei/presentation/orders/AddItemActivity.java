package comp3350.ei.presentation.orders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import comp3350.ei.R;
import comp3350.ei.business.AccessStoreProducts;
import comp3350.ei.objects.OrderIn;
import comp3350.ei.objects.StoreProduct;
import comp3350.ei.presentation.Messages;
import comp3350.ei.presentation.StoreProductListAdapter;
import comp3350.ei.presentation.StoreProductParcel;


public class AddItemActivity extends Activity {
    private List<StoreProduct> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);

        AccessStoreProducts accessStoreProducts = new AccessStoreProducts();
        productList = new ArrayList<>();
        productList.addAll(accessStoreProducts.getStoreProducts());
        StoreProductListAdapter productArrayAdapter = new StoreProductListAdapter(this, productList);

        try {
            final ListView listView = findViewById(R.id.list_item);
            listView.setAdapter(productArrayAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    final StoreProduct selected = productList.get(position);
                    if (selected != null) {
                        Intent askQuantity = new Intent(AddItemActivity.this, AskQuantity.class);
                        askQuantity.putExtra("selected", new StoreProductParcel(selected));
                        startActivityForResult(askQuantity, OrderInActivity.ADD_ITEM_REQUEST);
                    }
                }
            });
        } catch (final Exception e) {
            Messages.fatalError(this, "onCreate crashed\n" + e.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null && requestCode == OrderInActivity.ADD_ITEM_REQUEST && resultCode == RESULT_OK) {
            Intent viewProduct = new Intent(AddItemActivity.this, OrderIn.class);
            StoreProductParcel parcel = data.getParcelableExtra("product");
            String quantity = data.getStringExtra("quantity");
            viewProduct.putExtra("product", parcel);
            viewProduct.putExtra("quantity", quantity);
            setResult(RESULT_OK, viewProduct);
            NavUtils.navigateUpTo(AddItemActivity.this, viewProduct);
            AddItemActivity.this.finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}