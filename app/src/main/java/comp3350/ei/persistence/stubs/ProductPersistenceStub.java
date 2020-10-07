package comp3350.ei.persistence.stubs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import comp3350.ei.objects.Product;
import comp3350.ei.persistence.ProductPersistenceInterface.ProductDeletePersistence;
import comp3350.ei.persistence.ProductPersistenceInterface.ProductRetrievePersistence;
import comp3350.ei.persistence.ProductPersistenceInterface.ProductUpdatePersistence;

public class ProductPersistenceStub implements ProductUpdatePersistence, ProductRetrievePersistence, ProductDeletePersistence {
    private final List<Product> products;

    public ProductPersistenceStub() {
        this.products = new ArrayList<>();

        products.add(new Product("Milk powder", "Dairy", "High quality", "lbs", null));
        products.add(new Product("Banana", "Fruit", "Yellow and tasty", "lbs", null));
        products.add(new Product("Chicken wings", "Meat", "Raw chicken wings", "pcs", null));
        products.add(new Product("Milk", "Dairy", "2.0% Milk", "l", null));
        products.add(new Product("Apple", "Fruit", "Red apple", "", null));

    }

    public ProductPersistenceStub getProductPersistenceStub(){
        return this;
    }

    @Override
    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    @Override
    public List<Product> getProductSequential() {
        return Collections.unmodifiableList(products);
    }

    @Override
    public List<Product> getProductRandom(Product currentCourse) {
        List<Product> newCourses = new ArrayList<>();
        int index;

        index = products.indexOf(currentCourse);
        if (index >= 0) {
            newCourses.add(products.get(index));
        }
        return newCourses;
    }

    @Override
    public Product getProductById(int productId) {
        for (Product prod : products) {
            if (prod.getId() == productId) {
                return prod;
            }
        }
        return null;
    }

    @Override
    public Product getProduct(String productName) {
        for (Product prod : products) {
            if (prod.getName().equalsIgnoreCase(productName)) {
                return prod;
            }
        }
        return null;
    }

    @Override
    public Product insertProduct(Product product) {
        Product duplicate = null;

        for (Product prod : products) {
            if (prod.getName().equalsIgnoreCase(product.getName())) {
                duplicate = prod;
            }
        }

        if (duplicate == null) {
            products.add(product);
            return product;
        } else {
            return duplicate;
        }
    }

    @Override
    public void updateProduct(Product product) {

    }

    @Override
    public void updateProductPicture(int productId, String picture) {

    }

    @Override
    public void deleteProduct(Product currentProduct) {
        int index;

        index = products.indexOf(currentProduct);
        if (index >= 0) {
            products.remove(index);
        }
    }

    @Override
    public void deleteProduct(String productName) {
        for (Product prod : products) {
            if (prod.getName().equalsIgnoreCase(productName)) {
                deleteProduct(prod);
            }
        }
    }

    public Set<String> getProductCategory() {
        return null;
    }

    public List<Product> getProductByCategory(String category) {
        return null;
    }
}
