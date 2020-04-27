package in.bitcode.productassignment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentProducts extends Fragment {

    private RecyclerView mRecyclerProducts;
    private ArrayList<Product> mListProducts;
    private AdapterProducts mAdapterProducts;

    private int mCurrentProductPosition;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu( true );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate( R.layout.fragment_products, null );
        mRecyclerProducts = view.findViewById( R.id.recyclerProducts);

        mListProducts = new ArrayList<Product>();

        mAdapterProducts = new AdapterProducts( mListProducts );
        mRecyclerProducts.setAdapter( mAdapterProducts );

        mRecyclerProducts.setLayoutManager(
                new LinearLayoutManager( getActivity(), LinearLayoutManager.VERTICAL, false )
        );

        mAdapterProducts.setOnProductClickListener(
                new MyOnProductClickListener()
        );

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        getActivity().getMenuInflater()
                .inflate( R.menu.main_menu, menu );

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if( item.getItemId() == R.id.menuAddProduct ) {

            FragmentAddProduct fragmentAddProduct =
                    new FragmentAddProduct();

            fragmentAddProduct.setOnProductAddedListener(
                    new MyOnProductAddedListener()
            );

            getFragmentManager().beginTransaction()
                    .add( R.id.mainContainer, fragmentAddProduct, null )
                    .addToBackStack( null )
                    .commit();

        }

        return false;
    }

    private class MyOnProductAddedListener implements FragmentAddProduct.OnProductAddedListener {
        @Override
        public void onProductAdded(Product product) {

            mListProducts.add( product );
            mAdapterProducts.notifyDataSetChanged();

        }
    }

    private class MyOnProductClickListener implements AdapterProducts.OnProductClickListener {
        @Override
        public void onProductClick(Product product, int position ) {

            mCurrentProductPosition = position;

            Bundle params = new Bundle();
            params.putSerializable( Product.KEY_PRODUCT, product );

            FragmentProductDetails fragmentProductDetails =
                    new FragmentProductDetails();
            fragmentProductDetails.setArguments( params );

            fragmentProductDetails.setOnProductActionListener(
                    new MyOnProductActionListener()
            );

            getFragmentManager().beginTransaction()
                    .add( R.id.mainContainer, fragmentProductDetails, null )
                    .addToBackStack( null )
                    .commit();

        }
    }

    private class MyOnProductActionListener implements FragmentProductDetails.OnProductActionListener {
        @Override
        public void onProductDelete(Product product) {

            mListProducts.remove( mCurrentProductPosition );
            mAdapterProducts.notifyDataSetChanged();
            mCurrentProductPosition = -1;

        }

        @Override
        public void onProductEdit(Product oldProduct, Product newProduct) {

            mListProducts.set( mCurrentProductPosition, newProduct );
            mAdapterProducts.notifyDataSetChanged();
            mCurrentProductPosition = -1;

        }
    }
}
