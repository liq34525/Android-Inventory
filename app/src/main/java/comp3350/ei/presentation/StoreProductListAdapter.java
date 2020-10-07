package comp3350.ei.presentation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;

import java.io.File;
import java.util.List;

import comp3350.ei.R;
import comp3350.ei.objects.StoreProduct;

public class StoreProductListAdapter extends ArrayAdapter<StoreProduct> {
    private final Context context;

    public StoreProductListAdapter(Context context, List<StoreProduct> products) {
        super(context, 0, products);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        StoreProduct product = getItem(position);

        if (row == null) {
            // inflate row layout and assign to 'row'
            row = LayoutInflater.from(context).inflate(R.layout.listview_layout, parent,false);
        }

        ImageView picture = row.findViewById(R.id.image_product);
        TextView name = row.findViewById(R.id.text_product_name);
        TextView description = row.findViewById(R.id.text_product_descr);
        TextView quantity = row.findViewById(R.id.text_product_quantity);
        TextView price = row.findViewById(R.id.text_product_price);

        String fileName = product.getPicture();
        String newFileName = product.getProductName() + ".jpg";
        setPicture(picture, fileName, newFileName);

        name.setText(product.getProductName());
        description.setText(product.getDescription());
        quantity.setText(String.valueOf(product.getQuantity()));
        description.setText(product.getDescription());
        price.setText(String.valueOf(product.getPrice()));

        return row;
    }

    private void setPicture(ImageView imageView, String fileName, String newFileName)
    {
        if (fileName != null && fileName.length() > 0) {
            String root = context.getFilesDir().toString();
            File myDir = new File(root + "/product_images");
            File file = new File(myDir, newFileName);

            if (file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(file.toString());
                imageView.setImageBitmap(bitmap);
            }
        }
        else {
            imageView.setImageResource(context.getResources().getIdentifier("@drawable/icon_product_default",
                    null, context.getPackageName()));
        }
    }
}
