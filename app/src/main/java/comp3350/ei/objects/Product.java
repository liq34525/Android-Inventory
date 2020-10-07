package comp3350.ei.objects;

import java.util.Locale;

public class Product {
    private int productId;
    private String productName; //unique
    private String productCategory;
    private String productDescription;
    private String productUnit;
    private String productPicture;

    public Product() {
        productName = null;
        productCategory = null;
        productDescription = null;
        productUnit = null;
        productPicture = null;
        productId = -1;  //id not initialized
    }

    public Product(final String name) {
        productName = name;
        productCategory = null;
        productDescription = null;
        productUnit = null;
        productPicture = null;
        productId = -1;  //id not initialized
    }

    public Product(int productId) {
        productName = null;
        productCategory = null;
        productDescription = null;
        productUnit = null;
        productPicture = null;
        this.productId = productId;
    }

    public Product(final String name, final String category, final String description,
                   final String unit) {
        this.productName = name;
        this.productCategory = category;
        this.productDescription = description;

        this.productUnit = unit;
        this.productPicture = null;
        this.productId = -1;  //id not initialized
    }

    public Product(final String name, final String category, final String description,
                   final String unit, final String picture) {
        this.productName = name;
        this.productCategory = category;
        this.productDescription = description;
        this.productUnit = unit;
        this.productPicture = picture;
        this.productId = -1;  //id not initialized

        if (productPicture != null) {
            productPicture = productPicture.trim();
        }
    }

    public Product(final int id, final String name, final String category, final String description,
                   final String unit, final String picture) {
        this.productId = id;
        this.productName = name;
        this.productCategory = category;
        this.productDescription = description;
        this.productUnit = unit;
        this.productPicture = picture;

        if (productPicture != null) {
            productPicture = productPicture.trim();
        }
    }

    public int getId() {
        return (productId);
    }

    public String getName() {
        return (productName);
    }

    public String getPicture() {
        return (productPicture);
    }

    public String getCategory() {
        return (productCategory);
    }

    public String getDescription() {
        return (productDescription);
    }

    public String getUnit() {
        if (productUnit == null) {
            return "";
        }
        return productUnit;
    }

    public void setName(String newName) {
        productName = newName;
    }

    public void setCategory(String newCategory) {
        productCategory = newCategory;
    }

    public void setDescription(String newDescription) {
        productDescription = newDescription;
    }

    public void setUnit(String newUnit) {
        productUnit = newUnit;
    }

    public void setPicture(String newPicture) {
        productPicture = newPicture;
    }

    public void setId(int newId) {
        productId = newId;
    }


    public String toString() {
        return String.format(Locale.ENGLISH,
                "Product(Id %d): %s: %s\nCategory: %s, Unit: %s\n",
                productId, productName, productDescription, productCategory, productUnit);
    }

    public boolean equals(Product product) {
        if (product == null)
            return false;
        if (productName.equals(product.productName) && productCategory.equals(product.productCategory) &&
                productDescription.equals(product.productDescription) && productUnit.equals(product.productUnit)) {
            return true;
        }
        return false;
    }
}
