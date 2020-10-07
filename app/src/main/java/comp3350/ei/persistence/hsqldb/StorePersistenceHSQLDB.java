package comp3350.ei.persistence.hsqldb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import comp3350.ei.objects.Store;
import comp3350.ei.persistence.StorePersistence;

public class StorePersistenceHSQLDB implements StorePersistence {

    private final String dbPath;

    public StorePersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    @Override
    public List<Store> getStores() {
        return getStoreSequential();
    }

    @Override
    public List<Store> getStoreSequential() {
        final List<Store> stores = new ArrayList<>();

        try (final Connection c = ConnectionHSQLDB.connect(dbPath)) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM stores");
            while (rs.next())
            {
                final Store store = new Store(rs.getInt("id"),rs.getString("name"));
                stores.add(store);
            }
            rs.close();
            st.close();

            //c.close();

            return stores;
        }
        catch (final SQLException e)
        {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Store getStore(int storeId) {
        Store store=null;
        try (final Connection c = ConnectionHSQLDB.connect(dbPath)) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM stores WHERE id = ?");
            st.setInt(1, storeId);
            final ResultSet rs = st.executeQuery();

            if(rs.next()) {
                store = new Store(rs.getInt("id"),rs.getString("name"));
            }
            return store;

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }


    @Override
    public Store insertStore(Store currStore) {
        try (final Connection c = ConnectionHSQLDB.connect(dbPath)) {

            final PreparedStatement st = c.prepareStatement("INSERT INTO stores VALUES(?)");
            st.setString(2, currStore.getStoreName());
            st.executeUpdate();

            ResultSet rs = st.getGeneratedKeys();

            if (rs.next()) {
                currStore.setStoreId(rs.getInt(1));
            }

            return currStore;

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Store updateStore(Store currStore) {
        Store product = getStore(currStore.getStoreId());
        Store store;

        if(product!=null) {
            try (final Connection c = ConnectionHSQLDB.connect(dbPath)) {

                final PreparedStatement st = c.prepareStatement("UPDATE stores SET name = ? WHERE id = ?");

                st.setString(1, currStore.getStoreName());
                st.setInt(2, currStore.getStoreId());

                st.executeUpdate();

                store = currStore;
            } catch (final SQLException e) {
                throw new PersistenceException(e);
            }
        }
        else
        {
            store = insertStore(currStore);
        }
        return store;
    }

    @Override
    public void deleteStore(Store currStore) {
        try (final Connection c = ConnectionHSQLDB.connect(dbPath)) {
            final PreparedStatement st = c.prepareStatement("DELETE FROM stores WHERE id = ?");
            st.setInt(1, currStore.getStoreId());
            st.executeUpdate();
            st.close();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
