package comp3350.ei.business;

import android.graphics.BitmapFactory;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;

public class ProductImages {
    private String imagePath = "";

    static public final String IMAGE_DIR = "/product_images";
    static public final String FILE_EXTENSION = ".jpg";

    public ProductImages(Context context) {
        if (context != null) {
            imagePath = context.getFilesDir().toString() + IMAGE_DIR;
        }
        createImageDirectory();
    }

    //create a image directory on device
    private void createImageDirectory() {
        File myDir = new File(imagePath);
        try {
            myDir.mkdirs(); //create dir if not exit
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveImage(Bitmap finalBitmap, String fileName) {
        fileName = fileName + FILE_EXTENSION;
        File file = new File(imagePath, fileName);

        if (file.exists()) {
            file.delete();
        }

        try {
            file.createNewFile();

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //future use
    /*
    public void deleteImage(String fileName) {

        //should name file with productID, so name will be unique.
        fileName = fileName + FILE_EXTENSION;
        File file = new File(imagePath, fileName);
        if (file.exists()) {
            file.delete();
        }
    }
    */

    public void renameImage(String fileName, String newFileName) {
        fileName = fileName + FILE_EXTENSION;
        newFileName = newFileName + FILE_EXTENSION;

        File file = new File(imagePath, fileName);
        File newFile = new File(imagePath, newFileName);

        try {
            if (file.exists()) {
                file.renameTo(newFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap getBitmap(String pictureName) {
        Bitmap bitmap = null;
        String newFileName = pictureName + FILE_EXTENSION;

        if (pictureName != null && pictureName.length() > 0) {
            File file = new File(imagePath, newFileName);

            if (file.exists()) {
                bitmap = BitmapFactory.decodeFile(file.toString());
            }
        }
        return bitmap;
    }
}
