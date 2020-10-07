package comp3350.ei.persistence.ProductPersistenceInterface;

import comp3350.ei.objects.Product;

public interface ProductDeletePersistence {
    void deleteProduct(Product currentProduct);

    void deleteProduct(String productName);
}
