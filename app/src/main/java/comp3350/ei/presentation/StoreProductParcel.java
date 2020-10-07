package comp3350.ei.presentation;

import android.os.Parcel;
import android.os.Parcelable;

import comp3350.ei.business.AccessStoreProducts;
import comp3350.ei.objects.Product;
import comp3350.ei.objects.StoreProduct;


public class StoreProductParcel implements Parcelable {
    private StoreProduct storeProduct;

    public StoreProductParcel(StoreProduct product) {
        storeProduct = product;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeStringArray(new String[]{
                storeProduct.getProductName(),
                storeProduct.getCategory(),
                storeProduct.getDescription(),
                String.valueOf(storeProduct.getPrice()),
                String.valueOf(storeProduct.getQuantity()),
                storeProduct.getUnit()});
    }

    public static final Parcelable.Creator<StoreProductParcel> CREATOR
            = new Parcelable.Creator<StoreProductParcel>() {
        public StoreProductParcel createFromParcel(Parcel in) {
            return new StoreProductParcel(in);
        }

        public StoreProductParcel[] newArray(int size) {
            return new StoreProductParcel[size];
        }
    };

    private StoreProductParcel(Parcel in) {
        String[] data = new String[6];
        in.readStringArray(data);

        String name = data[0];
        String category = data[1];
        String description = data[2];
        String price = data[3];
        String quantity = data[4];
        String unit = data[5];

        AccessStoreProducts accessProducts = new AccessStoreProducts();
        this.storeProduct = accessProducts.getStoreProduct(name);

        if (storeProduct == null) {
            Product product = new Product(name, category, description, unit);
            storeProduct = new StoreProduct(null, product);
        }
        else {
            storeProduct.setCategory(category);
            storeProduct.setDescription(description);
            storeProduct.setUnit(unit);
        }

        storeProduct.setPrice(Double.parseDouble(price));
        storeProduct.setQuantity(Integer.parseInt(quantity));
    }

    public StoreProduct getStoreProduct() {
        return storeProduct;
    }
}


