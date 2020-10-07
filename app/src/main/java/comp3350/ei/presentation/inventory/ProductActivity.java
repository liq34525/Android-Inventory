package comp3350.ei.presentation.inventory;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

import comp3350.ei.R;
import comp3350.ei.business.ProductImages;
import comp3350.ei.objects.StoreProduct;
import comp3350.ei.presentation.ImageViewManager;
import comp3350.ei.presentation.StoreProductParcel;

public class ProductActivity extends Activity {
    private StoreProduct product;
    private ProductImages productImages;
    private ImageViewManager imageViewManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        productImages = new ProductImages(this);
        imageViewManager = new ImageViewManager(this);

        Intent intent = getIntent();

        StoreProductParcel parcel = intent.getParcelableExtra("StoreProduct");
        product = parcel.getStoreProduct();

        setTextViewsFromProductInfo();
        setImageFromProduct();
    }

    private void setTextViewsFromProductInfo() {
        ((TextView)findViewById(R.id.text_product_name)).setText(product.getProductName());
        ((TextView)findViewById(R.id.text_product_category)).setText(product.getCategory());
        ((TextView)findViewById(R.id.text_product_description)).setText(product.getDescription());
        ((TextView)findViewById(R.id.text_product_price)).setText(String.format("%.2f", product.getPrice()));
        ((TextView)findViewById(R.id.text_product_unit)).setText(product.getUnit());
        ((TextView)findViewById(R.id.text_product_quantity)).setText(String.valueOf(product.getQuantity()));
    }

    private void setImageFromProduct() {
        ImageView imageView = findViewById(R.id.image_product_img);
        String fileName = product.getPicture();

        Bitmap bitmap = productImages.getBitmap(fileName);
        imageViewManager.setImageView(imageView, bitmap);
    }

    public void onClickEditProduct(View view){
        Intent editProduct = new Intent(ProductActivity.this, EditProductActivity.class);
        editProduct.putExtra("StoreProduct",  new StoreProductParcel(product));
        startActivity(editProduct);
    }
}
