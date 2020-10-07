package comp3350.ei.presentation.inventory;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import comp3350.ei.R;
import comp3350.ei.business.ProductImages;
import comp3350.ei.objects.StoreProduct;
import comp3350.ei.presentation.ImageViewManager;
import comp3350.ei.presentation.StoreProductParcel;

public class StoreProductAdapter extends RecyclerView.Adapter<StoreProductAdapter.StoreProductViewHolder> {
    private List<StoreProduct> storeProducts, filteredProducts;
    private Context context;
    private final ProductImages productImages;
    private ImageViewManager imageViewManager;
    private String categoryFiler = "all";
    private String keyWordFiler = "";

    public class StoreProductViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView name;
        TextView description;
        TextView quantity;
        TextView price;
        ImageView picture;
        View view;

        StoreProductViewHolder(View view) {
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

    public StoreProductAdapter(Context context, List<StoreProduct> storeProductList) {
        this.storeProducts = storeProductList;
        this.filteredProducts = new ArrayList<>();
        this.filteredProducts.addAll(storeProductList);
        this.context = context;
        this.productImages = new ProductImages(context);
        this.imageViewManager = new ImageViewManager(context);
    }

    @Override
    public StoreProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_layout, parent, false);

        return new StoreProductViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final StoreProductViewHolder holder, final int position) {
        StoreProduct storeProduct = filteredProducts.get(position);

        String fileName = storeProduct.getPicture();

        Bitmap bitmap = productImages.getBitmap(fileName);
        imageViewManager.setImageView(holder.picture, bitmap);

        holder.name.setText(storeProduct.getProductName());
        holder.description.setText(storeProduct.getDescription());
        holder.quantity.setText(String.valueOf(storeProduct.getQuantity()));
        holder.price.setText(String.valueOf(storeProduct.getPrice()));

        // long press the view to view the product in detail
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                StoreProduct selected = filteredProducts.get(position);
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
        return filteredProducts.size();
    }


    public void filterByName(String match) {
        match = match.toLowerCase(Locale.getDefault());
        keyWordFiler = match;
        filteredProducts.clear();
        filteredProducts.addAll(storeProducts);

        filterInventory();
    }

    private void filterInventory() {
        filteredProducts.clear();
        filteredProducts.addAll(storeProducts);
        if (!categoryFiler.equals("all")) {
            if (keyWordFiler.length() > 0) {
                for (StoreProduct sp : storeProducts) {
                    String productCategory = sp.getCategory().toLowerCase(Locale.getDefault());
                    String name = sp.getProductName().toLowerCase(Locale.getDefault());

                    if (!productCategory.equals(categoryFiler) || !name.contains(keyWordFiler)) {
                        filteredProducts.remove(sp);
                    }
                }
            } else {
                for (StoreProduct sp : storeProducts) {
                    String productCategory = sp.getCategory().toLowerCase(Locale.getDefault());

                    if (!productCategory.equals(categoryFiler)) {
                        filteredProducts.remove(sp);
                    }
                }
            }
        } else {
            if (keyWordFiler.length() > 0) {
                for (StoreProduct sp : storeProducts) {
                    String name = sp.getProductName().toLowerCase(Locale.getDefault());

                    if (!name.contains(keyWordFiler)) {
                        filteredProducts.remove(sp);
                    }
                }
            }
        }


        notifyDataSetChanged();
    }

    public void filterByCategory(String category) {
        category = category.toLowerCase(Locale.getDefault());
        categoryFiler = category;

        filterInventory();
    }


    public void updateDate(List<StoreProduct> storeProductList) {
        this.storeProducts = storeProductList;
        filterInventory();
    }

    public void removeItem(int position) {
        storeProducts.remove(filteredProducts.get(position));
        filterInventory();
    }

    StoreProduct getFilterItem(int position) {
        return filteredProducts.get(position);
    }

}
