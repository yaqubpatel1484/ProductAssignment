package in.bitcode.productassignment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentProductDetails extends Fragment {

    private Product mProduct;
    private ImageView mImgProduct;
    private TextView mTxtProductId, mTxtProductName, mTxtProductDescription, mTxtProductPrice;

    private final int MENU_DELETE = 11, MENU_EDIT = 12;

    public interface OnProductActionListener {
        public void onProductDelete(Product product);

        public void onProductEdit(Product oldProduct, Product newProduct);
    }

    private OnProductActionListener mOnProductActionListener;

    public void setOnProductActionListener(OnProductActionListener onProductActionListener) {
        this.mOnProductActionListener = onProductActionListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.product_details, null);

        mImgProduct = view.findViewById(R.id.imgProduct);
        mTxtProductId = view.findViewById(R.id.txtProductId);
        mTxtProductName = view.findViewById(R.id.txtProductName);
        mTxtProductDescription = view.findViewById(R.id.txtProductDescription);
        mTxtProductPrice = view.findViewById(R.id.txtProductPrice);

        mProduct = (Product) getArguments().getSerializable(Product.KEY_PRODUCT);
        if (mProduct != null) {
            mImgProduct.setImageURI(Uri.parse(mProduct.getImageUrl()));
            mTxtProductId.setText(mProduct.getId() + "");
            mTxtProductName.setText(mProduct.getName());
            mTxtProductDescription.setText(mProduct.getDescription());
            mTxtProductPrice.setText(mProduct.getPrice() + "");

        }

        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        menu.add(0, MENU_EDIT, 0, "Ed")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0, MENU_DELETE, 0, "Del")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == MENU_DELETE) {

            if (mOnProductActionListener != null) {
                mOnProductActionListener.onProductDelete(mProduct);
                getFragmentManager().popBackStack();
            }
        }
        if (item.getItemId() == MENU_EDIT) {

            FragmentAddProduct fragmentAddProduct =
                    new FragmentAddProduct();

            Bundle params = new Bundle();
            params.putSerializable( Product.KEY_PRODUCT, mProduct );
            fragmentAddProduct.setArguments( params );

            fragmentAddProduct.setOnProductAddedListener(
                    new MyOnProductEditedListener()
            );

            getFragmentManager().beginTransaction()
                    .add( R.id.mainContainer, fragmentAddProduct, null )
                    .addToBackStack( null )
                    .commit();


        }
        return false;
    }

    private class MyOnProductEditedListener implements FragmentAddProduct.OnProductAddedListener {

        @Override
        public void onProductAdded(Product newProduct ) {

            if( mOnProductActionListener != null ){
                mOnProductActionListener.onProductEdit(
                        mProduct, newProduct
                );
                getFragmentManager()
                .beginTransaction()
                .remove( FragmentProductDetails.this )
                .commit();
            }

        }
    }
}
