package comp3350.ei.business;

import android.util.Log;

import java.util.Collections;
import java.util.List;

import comp3350.ei.application.Services;
import comp3350.ei.objects.Product;
import comp3350.ei.persistence.ProductPersistenceInterface.ProductDeletePersistence;
import comp3350.ei.persistence.ProductPersistenceInterface.ProductRetrievePersistence;
import comp3350.ei.persistence.ProductPersistenceInterface.ProductUpdatePersistence;

public class AccessProducts {
    private final ProductUpdatePersistence productUpdatePersistence;
    private final ProductDeletePersistence productDeletePersistence;
    private final ProductRetrievePersistence productRetrievePersistence;
    private List<Product> products;
    private Product product;
    private int currentProduct;

    public AccessProducts() {
        productUpdatePersistence = Services.getProductUpdatePersistence();
        productDeletePersistence = Services.getProductDeletePersistence();
        productRetrievePersistence = Services.getProductRetrievePersistence();
        products = productRetrievePersistence.getProducts();
        product = null;
        currentProduct = 0;
    }

    public AccessProducts(ProductRetrievePersistence pr,ProductUpdatePersistence pu, ProductDeletePersistence pd) {
        productRetrievePersistence = pr;
        productUpdatePersistence = pu;
        productDeletePersistence = pd;
        products = productRetrievePersistence.getProducts();
        product = null;
        currentProduct = 0;
    }

    public List<Product> getProducts() {
        products = productRetrievePersistence.getProductSequential();
        return Collections.unmodifiableList(products);
    }

    public Product getSequential() {
        if (product == null) {
            products = productRetrievePersistence.getProductSequential();
            currentProduct = 0;
        }
        if (currentProduct < products.size()) {
            product = products.get(currentProduct);
            currentProduct++;
        } else {
            product = null;
            currentProduct = 0;
        }
        return product;
    }

    public Product getProduct(String productName) {
        return productRetrievePersistence.getProduct(productName);
    }

    public Product insertProduct(Product currentProduct) {
        Product product = null;

        if (currentProduct != null) {
            product = getProduct(currentProduct.getName());

            if (product == null) {
                return productUpdatePersistence.insertProduct(currentProduct);
            }
        }
        return product;
    }

    public void deleteProduct(Product currentProduct) {
        productDeletePersistence.deleteProduct(currentProduct);
    }

    public void updateProduct(Product product) {
        productUpdatePersistence.updateProduct(product);
    }
}
