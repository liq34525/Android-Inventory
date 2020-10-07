package comp3350.ei.tests.business;

import org.junit.Test;

import comp3350.ei.business.ProductImages;

import static junit.framework.Assert.assertNull;


public class ProductImagesTest {

    @Test
    public void testNullBitmap() {
        ProductImages productImages = new ProductImages(null);

        assertNull(productImages.getBitmap(""));
    }
}
