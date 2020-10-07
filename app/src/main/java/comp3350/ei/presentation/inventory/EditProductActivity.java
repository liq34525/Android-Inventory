package comp3350.ei.presentation.inventory;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.NavUtils;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.text.InputFilter;

import comp3350.ei.R;
import comp3350.ei.business.AccessStoreProducts;
import comp3350.ei.objects.Product;
import comp3350.ei.objects.Store;
import comp3350.ei.presentation.Camera;
import comp3350.ei.presentation.FieldParser;
import comp3350.ei.objects.StoreProduct;

import comp3350.ei.business.ProductImages;
import comp3350.ei.presentation.DecimalDigitsInputFilter;
import comp3350.ei.presentation.ImageViewManager;
import comp3350.ei.presentation.Messages;
import comp3350.ei.presentation.StoreProductParcel;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

public class EditProductActivity extends Activity {
    private AccessStoreProducts accessStoreProducts;
    private ProductImages productImages;
    private Camera productCamera;
    private StoreProduct storeProduct;
    private FieldParser fieldParser;
    private ImageViewManager imageViewManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        Intent intent = getIntent();

        productCamera = new Camera(this);
        accessStoreProducts = new AccessStoreProducts();
        fieldParser = new FieldParser(this);
        productImages = new ProductImages(this);
        imageViewManager = new ImageViewManager(this);

        StoreProductParcel parcel = intent.getParcelableExtra("StoreProduct");
        storeProduct = parcel.getStoreProduct();

        ((EditText) findViewById(R.id.edit_product_price)).setFilters(new InputFilter[]{new DecimalDigitsInputFilter()});

        setEditFieldsWithCurrInfo();
        setImageFromProduct();
        setUpOnClickListeners();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == Camera.CAMERA_REQUEST && grantResults[0] == PERMISSION_GRANTED) {
            productCamera.createPicture();
        }
    }

    private void setUpOnClickListeners() {
        Button buttonSave = findViewById(R.id.button_save);
        Button buttonRestore = findViewById(R.id.button_restore);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    StoreProduct product = changeInformation();

                    if (product != null) {
                        Intent viewProduct = new Intent(EditProductActivity.this,
                                ProductActivity.class);
                        viewProduct.putExtra("StoreProduct", new StoreProductParcel(product));

                        NavUtils.navigateUpTo(EditProductActivity.this, viewProduct);
                        EditProductActivity.this.finish();
                    }
                } catch (final Exception e) {
                    Messages.fatalError(EditProductActivity.this, e.getMessage());
                }
            }
        });

        buttonRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEditFieldsWithCurrInfo();
            }
        });
    }

    private void setEditFieldsWithCurrInfo() {
        ((EditText) findViewById(R.id.edit_product_name)).setText(storeProduct.getProductName());
        ((EditText) findViewById(R.id.edit_product_category)).setText(storeProduct.getCategory());
        ((EditText) findViewById(R.id.edit_product_description)).setText(storeProduct.getDescription());
        ((EditText) findViewById(R.id.edit_product_price)).setText(String.format("%.2f", storeProduct.getPrice()));
        ((EditText) findViewById(R.id.edit_product_quantity)).setText(String.valueOf(storeProduct.getQuantity()));
        ((EditText) findViewById(R.id.edit_product_unit)).setText(storeProduct.getUnit());
    }

    private StoreProduct changeInformation() {
        boolean changed = false;
        EditText editName = findViewById(R.id.edit_product_name);
        EditText editCategory = findViewById(R.id.edit_product_category);
        EditText editDescription = findViewById(R.id.edit_product_description);
        EditText editPrice = findViewById(R.id.edit_product_price);
        EditText editQuantity = findViewById(R.id.edit_product_quantity);
        EditText editUnit = findViewById(R.id.edit_product_unit);

        String newName = editName.getText().toString();
        String newCategory = editCategory.getText().toString();
        String newDescription = editDescription.getText().toString();
        String price = editPrice.getText().toString();
        String quantity = editQuantity.getText().toString();
        String newUnit = editUnit.getText().toString();

        double newPrice = fieldParser.getDoubleFromString("price", price, 2);
        int newQuantity = fieldParser.getIntFromString("quantity", quantity);

        if (newQuantity < 0 || newPrice < 0) {
            return null;
        }

        if (!newName.equalsIgnoreCase(storeProduct.getProductName())
                && accessStoreProducts.getStoreProduct(newName) != null) {
            Messages.warning(this, "A storeProduct with that name already exists");
            return null;
        }
        if (newName.isEmpty()) {
            Messages.warning(this, "Please enter a storeProduct name");
            return null;
        }

        if (storeProduct == null) {
            Product p = new Product(newName, newCategory, newDescription, newUnit);
            storeProduct = new StoreProduct(new Store(), p, newPrice, newQuantity);
        } else {
            if (!storeProduct.getProductName().equals(newName)) {
                //update the picture name to match new name
                if (storeProduct.getPicture() != null
                        && !storeProduct.getProductName().equals(newName)) {
                    //update the picture image file name
                    productImages.renameImage(storeProduct.getProductName().trim(), newName);
                    //update the picture name in database (Keep unique name)
                    storeProduct.setPicture(newName);
                }
                storeProduct.setName(newName);
                changed = true;
            }
            if (!storeProduct.getCategory().equals(newCategory)) {
                storeProduct.setCategory(newCategory);
                changed = true;
            }
            if (!storeProduct.getDescription().equals(newDescription)) {
                storeProduct.setDescription(newDescription);
                changed = true;
            }

            if (!(storeProduct.getPrice() == newPrice)) {
                storeProduct.setPrice(newPrice);
                changed = true;
            }
            if (!(storeProduct.getQuantity() == newQuantity)) {
                storeProduct.setQuantity(newQuantity);
                changed = true;
            }

            if (!(storeProduct.getUnit().equals(newUnit))) {
                storeProduct.setUnit(newUnit);
                changed = true;
            }
        }

        if (changed) {
            accessStoreProducts.updateDatabase(storeProduct);
        }
        return storeProduct;
    }

    public void onClickBtnCreatePic(View view) {
        productCamera.createPicture();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView imageView = findViewById(R.id.image_product);

        if (requestCode == Camera.CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();

            if (bundle != null) {
                Bitmap imageBitmap = (Bitmap) bundle.get("data");

                productImages.saveImage(imageBitmap, storeProduct.getProductName());
                imageViewManager.setImageView(imageView, imageBitmap);
            }
        }

        if (storeProduct != null) {
            storeProduct.setPicture(storeProduct.getProductName());
            accessStoreProducts.updateDatabase(storeProduct);
        }
    }

    private void setImageFromProduct() {
        ImageView imageView = findViewById(R.id.image_product);
        String fileName = storeProduct.getPicture();

        Bitmap bitmap = productImages.getBitmap(fileName);
        imageViewManager.setImageView(imageView, bitmap);
    }
}
