package comp3350.ei.persistence.hsqldb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import comp3350.ei.objects.Product;
import comp3350.ei.objects.Store;
import comp3350.ei.objects.StoreProduct;
import comp3350.ei.persistence.StoreProductPersistence;

public class StoreProductPersistenceHSQLDB implements StoreProductPersistence {
    private final String dbPath;

    public StoreProductPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private StoreProduct fromResultSet(final ResultSet rs) throws SQLException {
        final int quantity = rs.getInt("quantity");
        final double price = rs.getBigDecimal("price").doubleValue();

        final int productId = rs.getInt("product_id");
        final String name = rs.getString("products.name");
        final String category = rs.getString("category");
        final String description = rs.getString("description");
        final String unit = rs.getString("unit");
        final String picture = rs.getString("picture");

        final int storeID = rs.getInt("store_id");
        final String storeName = rs.getString("stores.name");

        Product product = new Product(productId, name, category, description, unit, picture);
        Store store = new Store(storeID, storeName);
        return new StoreProduct(store, product, price, quantity);

    }


    public List<StoreProduct> getStoreProduct(String productName) {
        final List<StoreProduct> storeProducts = new ArrayList<>();
        try (final Connection c = ConnectionHSQLDB.connect(dbPath)) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM products, storeProducts WHERE products.name=storeProduct.name AND name = ?");
            st.setString(1, productName);

            final ResultSet rs = st.executeQuery();

            while (rs.next()) {
                final StoreProduct record = fromResultSet(rs);
                storeProducts.add(record);
            }

            rs.close();
            st.close();

            return storeProducts;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

    }

    @Override
    public List<StoreProduct> getStoreProductSequential() {
        final List<StoreProduct> storeProducts = new ArrayList<>();
        //String storeID="1";

        try (final Connection c = ConnectionHSQLDB.connect(dbPath)) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM storeProducts INNER JOIN products on storeProducts.product_id = products.id INNER JOIN stores on stores.id=store_id");
            while (rs.next()) {
                final StoreProduct storeProduct = fromResultSet(rs);
                storeProducts.add(storeProduct);
            }
            rs.close();
            st.close();

            //c.close();

            return storeProducts;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public StoreProduct insertStoreProduct(StoreProduct currentStoreProduct) {
        if (currentStoreProduct == null) {
            return null;
        }

        try (final Connection c = ConnectionHSQLDB.connect(dbPath)) {
            final PreparedStatement st = c.prepareStatement("INSERT INTO storeProducts VALUES(?, ?, ?, ?)");
            st.setInt(1, currentStoreProduct.getStore().getStoreId());
            st.setInt(2, currentStoreProduct.getProductId());
            st.setDouble(3, currentStoreProduct.getPrice());
            st.setInt(4, currentStoreProduct.getQuantity());
            st.executeUpdate();
            st.close();
            return currentStoreProduct;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void deleteStoreProduct(StoreProduct currentStoreProduct) {
        try (final Connection c = ConnectionHSQLDB.connect(dbPath)) {
            int productID = currentStoreProduct.getProductId();
            int storeID = currentStoreProduct.getStore().getStoreId();
            final PreparedStatement sc = c.prepareStatement("DELETE FROM storeProducts WHERE product_id = ? AND store_id = ?");
            sc.setInt(1, productID);
            sc.setInt(2, storeID);
            sc.executeUpdate();

            //shouldn't delete product (may still be a product in another store)
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public StoreProduct getStoreProductById(int storeID, int productID) {
        StoreProduct product = null;
        try (final Connection c = ConnectionHSQLDB.connect(dbPath)) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM storeProducts WHERE store_id = ? AND product_id = ?");
            st.setInt(1, storeID);
            st.setInt(2, productID);
            final ResultSet rs = st.executeQuery();

            if (rs.next()) {
                product = fromResultSet(rs);
            }
            return product;

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void updateStoreProduct(StoreProduct storeProduct) {
        try (final Connection c = ConnectionHSQLDB.connect(dbPath)) {
            final PreparedStatement st = c.prepareStatement("UPDATE storeProducts SET quantity = ?, price = ? WHERE product_id = ?");
            st.setInt(1, storeProduct.getQuantity());
            st.setDouble(2, storeProduct.getPrice());
            st.setInt(3, storeProduct.getProductId());

            st.executeUpdate();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<String> getStoreProductCategory() {
        final List<String> categories = new ArrayList<>();

        try (final Connection c = ConnectionHSQLDB.connect(dbPath)) {
            final PreparedStatement st = c.prepareStatement("SELECT DISTINCT Category FROM storeProducts INNER JOIN products on storeProducts.product_id = products.id");
            final ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String category = rs.getString("Category");
                categories.add(category);
            }
            rs.close();
            st.close();

            return categories;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<StoreProduct> getProductByCategory(String category) {
        final List<StoreProduct> storeProducts = new ArrayList<>();

        try (final Connection c = ConnectionHSQLDB.connect(dbPath)) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM storeProducts INNER JOIN products on storeProducts.product_id = products.id where products.category = ?");
            st.setString(1, category);
            final ResultSet rs = st.executeQuery();

            while (rs.next()) {
                final StoreProduct storeProduct = fromResultSet(rs);
                storeProducts.add(storeProduct);
            }
            rs.close();
            st.close();

            return storeProducts;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
