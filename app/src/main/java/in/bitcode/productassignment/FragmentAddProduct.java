package in.bitcode.productassignment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentAddProduct extends Fragment {

    private EditText mEdtProductId, mEdtProductName, mEdtProductDescription, mEdtProductPrice;
    private ImageView mImgProduct;

    private final int MENU_SAVE = 1, REQ_PICK_IMAGE = 1;
    private String mImgUri = "file:///storage/emulated/0/Pictures/in_flag.jpeg";

    public interface OnProductAddedListener {
        public void onProductAdded( Product product );
    }

    private OnProductAddedListener mOnProductAddedListener;

    public void setOnProductAddedListener(OnProductAddedListener onProductAddedListener) {
        this.mOnProductAddedListener = onProductAddedListener;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu( true );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate( R.layout.product_add_fragment, null );

        mImgProduct = view.findViewById( R.id.imgProduct );
        mEdtProductId = view.findViewById( R.id.edtProductId );
        mEdtProductName = view.findViewById( R.id.edtProductName );
        mEdtProductDescription = view.findViewById( R.id.edtProductDescription );
        mEdtProductPrice = view.findViewById( R.id.edtProductPrice );

        mImgProduct.setOnClickListener( new MyOnImgProductClickListener() );

        if( getArguments() != null ) {
            Product product = (Product) getArguments().getSerializable( Product.KEY_PRODUCT );
            mImgProduct.setImageURI(Uri.parse( product.getImageUrl() ));
            mEdtProductId.setText( product.getId() + "" );
            mEdtProductName.setText( product.getName() );
            mEdtProductDescription.setText( product.getDescription() );
            mEdtProductPrice.setText( product.getPrice()  +"" );

        }

        return  view;

    }

    private class MyOnImgProductClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            Intent intent = new Intent( Intent.ACTION_PICK);
            intent.setType("image/*");

            startActivityForResult( intent, REQ_PICK_IMAGE );

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        MenuItem menuItem = menu.add( 0, MENU_SAVE, 0, "Save");
        menuItem.setShowAsAction( MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.e("tag", "onOptionsItemSelected");

        if( item.getItemId() == MENU_SAVE ){

            if( mOnProductAddedListener != null ) {
                Product newProduct =
                        new Product(
                                Integer.parseInt(mEdtProductId.getText().toString()),
                                mEdtProductName.getText().toString(),
                                mEdtProductDescription.getText().toString(),
                                Integer.parseInt(mEdtProductPrice.getText().toString()),
                                mImgUri
                        );
                mOnProductAddedListener.onProductAdded( newProduct );
            }

            getFragmentManager()
                    .beginTransaction()
                    .remove( this )
                    .commit();

        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( requestCode == REQ_PICK_IMAGE && data != null ) {
            mImgUri = data.getData().toString();
            mImgProduct.setImageURI( data.getData() );
        }
    }
}
