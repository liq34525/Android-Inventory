package comp3350.ei.presentation.inventory;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import comp3350.ei.R;
import comp3350.ei.business.ProductImages;
import comp3350.ei.business.AccessStoreProducts;
import comp3350.ei.objects.Product;
import comp3350.ei.objects.Store;
import comp3350.ei.presentation.Camera;
import comp3350.ei.presentation.FieldParser;
import comp3350.ei.objects.StoreProduct;
import comp3350.ei.presentation.DecimalDigitsInputFilter;
import comp3350.ei.presentation.Messages;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

public class AddProductActivity extends Activity {
    private AccessStoreProducts accessStoreProducts;
    private ProductImages productImages;
    private Bitmap imageBitmap;
    private FieldParser fieldParser;
    private Camera productCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        accessStoreProducts = new AccessStoreProducts();
        productCamera = new Camera(this);
        productImages = new ProductImages(this);
        fieldParser = new FieldParser(this);

        ((EditText) findViewById(R.id.edit_product_price)).setFilters(new InputFilter[]{new DecimalDigitsInputFilter()});

        setDefaultImage();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == Camera.CAMERA_REQUEST && grantResults[0] == PERMISSION_GRANTED) {
            productCamera.createPicture();
        }
    }

    public void onClickPicture(View view) {
        productCamera.createPicture();
    }

    public void onClickBtnCreateProduct(View view) {
        Intent confirm = new Intent(AddProductActivity.this, Messages.class);
        startActivityForResult(confirm, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Camera.CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();

            if (bundle != null) {
                imageBitmap = (Bitmap) bundle.get("data");

                ImageView imageView = findViewById(R.id.image_product);
                imageView.setImageBitmap(imageBitmap);
            }
        }
        if (requestCode == 0 && resultCode == RESULT_OK) {
            addProduct();
        }
    }

    private void addProduct() {
        StoreProduct product = createStoreProductFromEditText();

        if (product != null) {
            try {
                accessStoreProducts.insertStoreProduct(product);
                product.setPicture(product.getProductName());
                accessStoreProducts.updateDatabase(product);
                NavUtils.navigateUpFromSameTask(this);
            } catch (final Exception e) {
                Messages.fatalError(this, "Error adding product\n" + e.getMessage());
            }
        }
    }

    private StoreProduct createStoreProductFromEditText() {
        EditText editName = findViewById(R.id.edit_product_name);
        EditText editCategory = findViewById(R.id.edit_product_category);
        EditText editDescription = findViewById(R.id.edit_product_description);
        EditText editPrice = findViewById(R.id.edit_product_price);
        EditText editQuantity = findViewById(R.id.edit_product_quantity);
        EditText editUnit = findViewById(R.id.edit_product_unit);

        String name = editName.getText().toString();
        String category = editCategory.getText().toString();
        String description = editDescription.getText().toString();
        String price = editPrice.getText().toString();
        String quantity = editQuantity.getText().toString();
        String unit = editUnit.getText().toString();


        if (accessStoreProducts.getStoreProduct(name) != null) {
            Messages.warning(this, "A product with that name already exists");
            return null;
        }

        if (name.isEmpty()) {
            Messages.warning(this, "Please enter a product name");
            return null;
        }

        double finalPrice = fieldParser.getDoubleFromString("price", price, 2);
        int finalQuantity = fieldParser.getIntFromString("quantity", quantity);

        if (finalQuantity < 0 || finalPrice < 0) {
            return null;
        }

        name = name.trim();

        if (imageBitmap != null) {
            saveImage(name);
        }

        Product product = new Product(name, category, description, unit);
        return new StoreProduct(new Store(), product, finalPrice, finalQuantity);
    }

    private void saveImage(String name) {
        productImages.saveImage(imageBitmap, name);
    }

    private void setDefaultImage() {
        ImageView imageView = findViewById(R.id.image_product);
        imageView.setImageResource(getResources().getIdentifier("@drawable/icon_product_default",
                null, this.getPackageName()));
    }
}
