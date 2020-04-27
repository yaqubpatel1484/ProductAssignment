package in.bitcode.productassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private FragmentProducts mFragmentProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentProducts = (FragmentProducts) getSupportFragmentManager()
                .findFragmentById( R.id.fragmentProducts );
    }
}
