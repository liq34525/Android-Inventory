package comp3350.ei.persistence.ProductPersistenceInterface;

import comp3350.ei.objects.Product;

public interface ProductUpdatePersistence {
    //Product updateProduct(Product currentProduct);
    void updateProduct(Product product);

    void updateProductPicture(int productId, String picture);

    Product insertProduct(Product currentProduct);
}
