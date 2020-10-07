package comp3350.ei.persistence.hsqldb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import comp3350.ei.business.AccessStoreProducts;
import comp3350.ei.objects.Order;
import comp3350.ei.objects.OrderIn;
import comp3350.ei.objects.OrderOut;
import comp3350.ei.objects.Store;
import comp3350.ei.objects.StoreProduct;
import comp3350.ei.persistence.OrderPersistence;

public class OrderPersistenceHSQLDB implements OrderPersistence {

    private final String dbPath;
    private HashMap<HashMap<StoreProduct, Integer>,Integer> ordersproductList=new HashMap<>();

    private Order fromResultSet(ResultSet rs) throws SQLException {
        Order order = null;
        int id = rs.getInt("id");
        int storeId = rs.getInt("store_id");
        String type = rs.getString("type");
        String year = rs.getString("year");
        String month = rs.getString("month");
        String day = rs.getString("day");

        if (type.equalsIgnoreCase("in")) {
            order = new OrderIn(id, new Store(storeId), year, month, day);
        }
        else if(type.equalsIgnoreCase("out"))
            order = new OrderOut(id, new Store(storeId), year, month, day);

        return order;
    }

    private PreparedStatement prepareOrderStatement(PreparedStatement st, Order order) throws SQLException {
        int storeId = order.getStore().getStoreId();
        st.setInt(1, storeId);
        String type;
        if (order instanceof OrderIn) {
            type = "in";
        } else if (order instanceof OrderOut) {
            type = "out";
        } else {
            throw new SQLException("Cannot determine order type!");
        }
        st.setString(2, type);
        st.setString(3, order.getYear());
        st.setString(4, order.getMonth());
        st.setString(5, order.getDay());

        return st;
    }

    public OrderPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    @Override
    public List<Order> getOrderSequential() {
        final List<Order> orders = new ArrayList<>();

        try (final Connection c = ConnectionHSQLDB.connect(dbPath)) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM orders");
            while (rs.next()) {
                final Order order = fromResultSet(rs);
                for (Map.Entry<HashMap<StoreProduct, Integer>,Integer> entry : ordersproductList.entrySet()) {
                    if(entry.getValue()==order.getOrderId()) {
                        order.setOrderedProduct(entry.getKey());
                        break;
                    }
                }
                orders.add(order);
            }
            rs.close();
            st.close();

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
        return orders;
    }

    @Override
    public List<Order> getOrderIns() {

        final List<Order> orders = new ArrayList<>();

        try (final Connection c = ConnectionHSQLDB.connect(dbPath)) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM orders WHERE type = ?");
            st.setString(1, "in");
            final ResultSet rs = st.executeQuery();
            while (rs.next()) {
                final Order order = fromResultSet(rs);
                for (Map.Entry<HashMap<StoreProduct, Integer>,Integer> entry : ordersproductList.entrySet()) {
                    if(entry.getValue()==order.getOrderId()) {
                        order.setOrderedProduct(entry.getKey());
                        break;
                    }
                }
                orders.add(order);
            }
            rs.close();
            st.close();

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

        return orders;

    }

    @Override
    public List<Order> getOrderOuts() {

        final List<Order> orders = new ArrayList<>();

        try (final Connection c = ConnectionHSQLDB.connect(dbPath)) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM orders WHERE type = ?");
            st.setString(1, "out");
            final ResultSet rs = st.executeQuery();
            while (rs.next()) {
                final Order order = fromResultSet(rs);
                for (Map.Entry<HashMap<StoreProduct, Integer>,Integer> entry : ordersproductList.entrySet()) {
                    if(entry.getValue()==order.getOrderId()) {
                        order.setOrderedProduct(entry.getKey());
                        break;
                    }
                }
                orders.add(order);
            }
            rs.close();
            st.close();

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

        return orders;

    }

    @Override
    public Order insertOrder(Order currentOrder) {
        if (currentOrder == null) {
            return null;
        }
        try (final Connection c = ConnectionHSQLDB.connect(dbPath)) {
            PreparedStatement st = c.prepareStatement("INSERT INTO orders(store_id, type, year, month, day) VALUES( ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            prepareOrderStatement(st, currentOrder);
            st.executeUpdate();

            ResultSet rs = st.getGeneratedKeys();

            if (rs.next()) {
                int id = rs.getInt(1);
                currentOrder.setOrderId(id);
                if(currentOrder.getProducts()!=null)
                  ordersproductList.put(currentOrder.getProducts(),currentOrder.getOrderId());
            }

            st.close();

            return currentOrder;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Order updateOrder(Order currentOrder) {
        try (final Connection c = ConnectionHSQLDB.connect(dbPath)) {
            final PreparedStatement st = c.prepareStatement("UPDATE orders SET store_id = ?, year = ?, month = ?, day = ? WHERE id = ?");

            st.setInt(1, currentOrder.getStore().getStoreId());
            st.setString(2, currentOrder.getYear());
            st.setString(3, currentOrder.getMonth());
            st.setString(4, currentOrder.getDay());
            st.setInt(5, currentOrder.getOrderId());

            st.executeUpdate();
            st.close();

            return currentOrder;

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void deleteOrder(Order currentOrder) {
        try (final Connection c = ConnectionHSQLDB.connect(dbPath)) {
            final PreparedStatement st = c.prepareStatement("DELETE FROM orders WHERE id = ?");
            st.setInt(1, currentOrder.getOrderId());
            st.executeUpdate();
            st.close();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
