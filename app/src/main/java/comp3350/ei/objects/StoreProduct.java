package comp3350.ei.objects;

public class StoreProduct {
    private Product product;
    private Store store;
    private int price;
    private int quantity;

    public StoreProduct(Store store, Product product) {
        this.product = product;
        this.store = store;
        this.quantity = 0;
        this.price = 0;
    }

    public StoreProduct(Store store, Product product, double price, int quantity) {
        this.product = product;
        this.price = (int)(price * 100);
        this.quantity = quantity;
        this.store = store;
        if (price < 0)
            this.price = 0;
        if (quantity < 0)
            this.quantity = 0;
    }

    public Store getStore() {
        return (store);
    }

    public void setStore(final Store store) {
        this.store = store;
    }

    public int getProductId() {
        if (product != null) {
            return (product.getId());
        }
        return -1;
    }

    public String getProductName() {
        if (product != null) {
            return (product.getName());
        }

        return null;
    }

    public String getDescription() {
        if (product != null) {
            return (product.getDescription());
        }

        return "";
    }

    public void setDescription(final String desc) {
        product.setDescription(desc);
    }

    public double getPrice()
    {
        return ((double)price/100.0);
    }

    public void setPrice(final double newPrice) {
        price = (int)(newPrice * 100);
    }

    public String getUnit() {
        String unit = "";

        if (product != null) {
            if (product.getUnit() != null) {
                unit = product.getUnit();
            }
        }

        return unit;
    }

    public void setUnit(final String unit) {
        product.setUnit(unit);
    }

    public String getCategory() {
        String cat = "";

        if (product != null) {

            String storedCat = product.getCategory();
            if (storedCat != null && !storedCat.equalsIgnoreCase("")) {
                cat = product.getCategory();
            }
        }

        return cat;
    }

    public void setCategory(final String category) {
        product.setCategory(category);
    }

    public String getPicture() {
        return product.getPicture();
    }

    public void setPicture(final String picture) { product.setPicture(picture); }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return (this.quantity);
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }

    public void setName(final String name) {
        product.setName(name);
    }

    public void setId(final int id) {product.setId(id); }

    public String toString () {
        return product+"$: "+price+" Q: "+quantity;
    }

}
