package comp3350.ei.persistence.hsqldb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.util.Set;
import java.util.HashSet;

import comp3350.ei.objects.Product;
import comp3350.ei.persistence.ProductPersistenceInterface.ProductDeletePersistence;
import comp3350.ei.persistence.ProductPersistenceInterface.ProductRetrievePersistence;
import comp3350.ei.persistence.ProductPersistenceInterface.ProductUpdatePersistence;

public class ProductPersistenceHSQLDB implements ProductDeletePersistence, ProductUpdatePersistence, ProductRetrievePersistence {

    private final String dbPath;

    public ProductPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

private Product fromResultSet(final ResultSet rs) throws SQLException {
        final String productName = rs.getString("name");
        final String productCategory = rs.getString("category");
        final String productDescription = rs.getString("description");
        final String productUnit = rs.getString("unit");
        final String productPicture = rs.getString("picture");
        final int productId = rs.getInt("id");

        return new Product(productId, productName, productCategory, productDescription,
                productUnit, productPicture);
    }

    @Override
    public List<Product> getProducts() {
        return getProductSequential();
    }

    @Override
    public List<Product> getProductSequential() {
        final List<Product> products = new ArrayList<>();

        try (final Connection c = ConnectionHSQLDB.connect(dbPath)) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM products");
            while (rs.next()) {
                final Product product = fromResultSet(rs);
                products.add(product);
            }
            rs.close();
            st.close();

            return products;
        }
        catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<Product> getProductRandom(Product currentProduct) {
        final List<Product> products = new ArrayList<>();

        try (final Connection c = ConnectionHSQLDB.connect(dbPath)) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM products WHERE name=?");
            st.setString(1, currentProduct.getName());

            final ResultSet rs = st.executeQuery();
            while (rs.next()) {
                final Product product = fromResultSet(rs);
                products.add(product);
            }
            rs.close();
            st.close();

            return products;
        }
        catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Product insertProduct(Product currentProduct) {

        int id;
        try (final Connection c = ConnectionHSQLDB.connect(dbPath)) {
            final PreparedStatement st = c.prepareStatement("INSERT INTO products(NAME, CATEGORY, DESCRIPTION, UNIT, PICTURE) VALUES(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, currentProduct.getName());
            st.setString(2, currentProduct.getCategory());
            st.setString(3, currentProduct.getDescription());
            st.setString(4, currentProduct.getUnit());
            st.setString(5, currentProduct.getPicture());
            //ID is auto assigned
            //st.setInt(7, currentProduct.getId());


            st.executeUpdate();


            ResultSet rs = st.getGeneratedKeys();
           if(rs.next())
           {
               id=rs.getInt(1);
               currentProduct.setId(id);
           }

            return currentProduct;

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void deleteProduct(Product currentProduct) {

        try (final Connection c = ConnectionHSQLDB.connect(dbPath)) {
            final PreparedStatement sc = c.prepareStatement("DELETE FROM products WHERE name = ?");
            sc.setString(1, currentProduct.getName());
            sc.executeUpdate();
            final PreparedStatement st = c.prepareStatement("DELETE FROM products WHERE name = ?");
            st.setString(1, currentProduct.getName());
            st.executeUpdate();
        }
        catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    public void deleteProduct(String productName) {

        try (final Connection c = ConnectionHSQLDB.connect(dbPath)) {
            final PreparedStatement sc = c.prepareStatement("DELETE FROM products WHERE name = ?");
            sc.setString(1, productName);
            sc.executeUpdate();
            final PreparedStatement st = c.prepareStatement("DELETE FROM products WHERE name = ?");
            st.setString(1, productName);
            st.executeUpdate();
            st.close();
        }
        catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    public Product getProduct(String productName) {
        Product product = null;

        try (final Connection c = ConnectionHSQLDB.connect(dbPath)) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM products WHERE name = ?");
            st.setString(1, productName);
            final ResultSet rs = st.executeQuery();

            if (rs.next()) {
                product = fromResultSet(rs);
            }
            rs.close();
            st.close();
            return product;

        }
        catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    public Product getProductById(int productId) {
        Product product = null;
        try (final Connection c = ConnectionHSQLDB.connect(dbPath)) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM products WHERE id = ?");
            st.setInt(1, productId);
            final ResultSet rs = st.executeQuery();

            if (rs.next()) {
                product = fromResultSet(rs);
            }
            rs.close();
            st.close();
            return product;

        }
        catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    public void updateProductPicture(int productId, String picture) {
        try (final Connection c = ConnectionHSQLDB.connect(dbPath)) {
            final PreparedStatement st = c.prepareStatement("UPDATE products SET picture = ? WHERE id = ?");
            st.setString(1, picture);
            st.setInt(2, productId);
            st.executeUpdate();
            st.close();
        }
        catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    public void updateProduct(Product product) {
        try (final Connection c = ConnectionHSQLDB.connect(dbPath)) {
            final PreparedStatement st = c.prepareStatement("UPDATE products SET name = ?, description = ?, category = ?, unit = ?,  picture = ? WHERE id = ?");

            st.setString(1, product.getName());
            st.setString(2, product.getDescription());
            st.setString(3, product.getCategory());
            st.setString(4, product.getUnit());
            st.setString(5, product.getPicture());
            st.setInt(6, product.getId());

            st.executeUpdate();
            st.close();

        }
        catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    public Set<String> getProductCategory() {
        final Set<String> categories = new HashSet<>();

        try (final Connection c = ConnectionHSQLDB.connect(dbPath)) {
            final PreparedStatement st = c.prepareStatement("SELECT Category FROM products");
            final ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String category = rs.getString("Category");
                categories.add(category);
            }
            rs.close();
            st.close();

            return categories;
        }
        catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<Product> getProductByCategory(String category) {
        final List<Product> products = new ArrayList<>();

        try (final Connection c = ConnectionHSQLDB.connect(dbPath)) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM products where category = ?");
            st.setString(1, category);
            final ResultSet rs = st.executeQuery();

            while (rs.next()) {
                final Product product = fromResultSet(rs);
                products.add(product);
            }
            rs.close();
            st.close();

            return products;
        }
        catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }
}

