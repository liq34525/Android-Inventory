package comp3350.ei.tests.objects;

import org.junit.Test;

import comp3350.ei.objects.Product;

import static org.junit.Assert.*;

public class ProductTest {

    private int testCounter = 0;

    //testing product constructor and getMethods
    @Test
    public void testProductCon2() {
        testCounter++;
        System.out.println("\nStarting test " + testCounter + " on Product");

        Product product;
        String name = "New Product 1";

        product = new Product(name);

        assertNotNull(product);
        assertEquals(name, product.getName());
        assertNull(product.getPicture());
        assertNull(product.getCategory());
        assertNull(product.getDescription());
        assertEquals("", product.getUnit());

        System.out.println("Finished test on Product");
    }

    //testing product constructor and getMethods
    @Test
    public void testProductCon7() {
        testCounter++;
        System.out.println("\nStarting test " + testCounter + " on Product");

        Product product;
        String name = "New Product 3";
        String category = "Category 1";
        String description = "This is new product description";
        String unit = "box";

        product = new Product(name, category, description, unit);

        assertEquals(name, product.getName());
        assertEquals(category, product.getCategory());
        assertEquals(description, product.getDescription());
        assertEquals(unit, product.getUnit());

        System.out.println("Finished test on Product");
    }

    //testing product constructor, getMethods and setName method
    @Test
    public void testProductCon7NameSetter() {
        testCounter++;
        System.out.println("\nStarting test " + testCounter + " on Product");

        Product product;
        String name = "New Product 3";
        String category = "Category 1";
        String description = "This is new product description";
        String unit = "box";
        String newName = "New ProductName";

        product = new Product(name, category, description, unit);

        assertEquals(name, product.getName());
        assertNotEquals(newName, product.getName());

        product.setName(newName);

        assertNotEquals(name, product.getName());
        assertEquals(newName, product.getName());
        assertEquals(category, product.getCategory());
        assertEquals(description, product.getDescription());
        assertEquals(unit, product.getUnit());

        System.out.println("Finished test on Product");
    }

    //testing product constructor, getMethods and setCategory method
    @Test
    public void testProductCon7CategorySetter() {
        testCounter++;
        System.out.println("\nStarting test " + testCounter + " on Product");

        Product product;
        String name = "New Product 3";
        String category = "Category 1";
        String description = "This is new product description";
        String unit = "box";

        product = new Product(name, category, description, unit);

        String newCategory = "Category 2";

        assertEquals(category, product.getCategory());
        assertNotEquals(newCategory, product.getCategory());

        product.setCategory(newCategory);

        assertNotEquals(category, product.getCategory());
        assertEquals(name, product.getName());
        assertEquals(newCategory, product.getCategory());
        assertEquals(description, product.getDescription());
        assertEquals(unit, product.getUnit());

        System.out.println("Finished test on Product");
    }

    //testing product constructor, getMethods and setDescription method
    @Test
    public void testProductCon7DescriptionSetter() {
        testCounter++;
        System.out.println("\nStarting test " + testCounter + " on Product");

        Product product;
        String name = "New Product 3";
        String category = "Category 1";
        String description = "This is new product description";
        String unit = "box";

        product = new Product(name, category, description, unit);

        String newDescription="This is new product description version 2";

        assertEquals(description, product.getDescription());
        assertNotEquals(newDescription, product.getDescription());

        product.setDescription(newDescription);

        assertNotEquals(description, product.getDescription());
        assertEquals(name, product.getName());
        assertEquals(category, product.getCategory());
        assertEquals(newDescription, product.getDescription());
        assertEquals(unit, product.getUnit());

        System.out.println("Finished test on Product");
    }

    //testing toString method
    @Test
    public void testProductToString() {
        testCounter++;
        System.out.println("\nStarting test " + testCounter + " on Product");

        Product product;
        String name = "New Product 3";
        String category = "Category 1";
        String description = "This is new product description";
        String unit = "box";

        product = new Product(name, category, description, unit);

        assertEquals(name, product.getName());
        assertEquals(category, product.getCategory());
        assertEquals(description, product.getDescription());
        assertEquals(unit, product.getUnit());

        assertNotNull(product.toString());

        System.out.println("Finished test on Product");
    }

}
