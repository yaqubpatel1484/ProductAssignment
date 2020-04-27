package in.bitcode.productassignment;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterProducts extends RecyclerView.Adapter<AdapterProducts.ProductHolder> {

    private ArrayList<Product> mListProducts;
    public AdapterProducts( ArrayList<Product> listProducts ) {
        mListProducts = listProducts;
    }

    public interface OnProductClickListener {
        public void onProductClick( Product product, int position );
    }

    private OnProductClickListener mOnProductClickListener;

    public void setOnProductClickListener(OnProductClickListener onProductClickListener) {
        this.mOnProductClickListener = onProductClickListener;
    }

    class ProductHolder extends RecyclerView.ViewHolder {

        public ImageView mImgProduct;
        public TextView mTxtProductName, mTxtProductPrice;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);

            mImgProduct = itemView.findViewById( R.id.imgProduct );
            mTxtProductName = itemView.findViewById( R.id.txtProductName );
            mTxtProductPrice = itemView.findViewById( R.id.txtProductPrice );

            itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if( mOnProductClickListener != null ){
                                mOnProductClickListener.onProductClick( mListProducts.get( getAdapterPosition() ), getAdapterPosition() );
                            }

                        }
                    }
            );
        }
    }

    @Override
    public int getItemCount() {

        if( mListProducts != null ) {
            return mListProducts.size();
        }

        return 0;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from( parent.getContext() )
                .inflate( R.layout.product, null );

        return new ProductHolder( view );

    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {

        Product product = mListProducts.get( position );

        holder.mImgProduct.setImageURI(Uri.parse( product.getImageUrl() ) );
        holder.mTxtProductName.setText( product.getName() );
        holder.mTxtProductPrice.setText( product.getPrice() + "" );

    }
}
