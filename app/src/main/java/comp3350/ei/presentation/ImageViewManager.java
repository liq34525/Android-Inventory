package comp3350.ei.presentation;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

public class ImageViewManager {
    private final Context context;
    private String defaultImage = "@drawable/icon_product_default";
    private int defaultPicResId;

    public ImageViewManager(Context cont) {
        this.context = cont;
        defaultPicResId = context.getResources().getIdentifier(defaultImage,
                null, context.getPackageName());
    }

    public ImageViewManager(Context cont, String defaultImageResource) {
        this.defaultImage = defaultImageResource;
        this.context = cont;
        defaultPicResId = context.getResources().getIdentifier(defaultImage,
                null, context.getPackageName());
    }

    public void setImageView(ImageView imageView, Bitmap bitmap) {
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }
        else {
            imageView.setImageResource(defaultPicResId);
        }
    }
}
