package comp3350.ei.persistence.ProductPersistenceInterface;

import java.util.List;
import java.util.Set;

import comp3350.ei.objects.Product;

public interface ProductRetrievePersistence {
    List<Product> getProducts();

    List<Product> getProductSequential();

    List<Product> getProductRandom(Product currentProduct);

    List<Product> getProductByCategory(String category);

    Set<String> getProductCategory();

    Product getProduct(String productName);

    Product getProductById(int productId);
}
