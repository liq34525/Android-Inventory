package comp3350.ei.presentation.orders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import comp3350.ei.R;
import comp3350.ei.business.ProductImages;
import comp3350.ei.objects.StoreProduct;
import comp3350.ei.presentation.ImageViewManager;
import comp3350.ei.presentation.StoreProductParcel;
import comp3350.ei.presentation.inventory.ProductActivity;

public class StoreProductOrderAdapter extends RecyclerView.Adapter<StoreProductOrderAdapter.StoreProductListViewHolder> {
    private List<StoreProduct> storeProducts;
    private Context context;
    private final ProductImages productImages;
    private ImageViewManager imageViewManager;
    private List<Integer> quantities;

    public class StoreProductListViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{
        TextView name;
        TextView description;
        TextView quantity;
        TextView price;
        ImageView picture;
        View view;

        StoreProductListViewHolder(View view) {
            super(view);

            picture = view.findViewById(R.id.image_product);
            name = view.findViewById(R.id.text_product_name);
            description = view.findViewById(R.id.text_product_descr);
            quantity = view.findViewById(R.id.text_product_quantity);
            price = view.findViewById(R.id.text_product_price);

            this.view = view;
            this.view.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            return true;
        }

    }

    public StoreProductOrderAdapter( Context context, List<StoreProduct> storeProductList, List<Integer> quantities) {
        this.storeProducts = storeProductList;
        this.quantities = quantities;
        this.context = context;
        this.productImages = new ProductImages(context);
        this.imageViewManager = new ImageViewManager(context);
    }

    @Override
    public StoreProductListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_layout, parent, false);

        return new StoreProductListViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final StoreProductListViewHolder holder, final int position) {
        StoreProduct storeProduct = storeProducts.get(position);
        int quantity = quantities.get(position);

        String fileName = storeProduct.getPicture();
        Bitmap bitmap = productImages.getBitmap(fileName);
        imageViewManager.setImageView(holder.picture, bitmap);

        holder.name.setText(storeProduct.getProductName());
        holder.description.setText(storeProduct.getDescription());
        holder.quantity.setText(String.valueOf(quantity));
        holder.price.setText(String.valueOf(storeProduct.getPrice()*quantity));

        // long press the view to view the product in detail
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                StoreProduct selected = storeProducts.get(position);
                Intent viewProduct = new Intent(context,
                        ProductActivity.class);
                viewProduct.putExtra("StoreProduct", new StoreProductParcel(selected));

                context.startActivity(viewProduct);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return storeProducts.size();
    }
}
