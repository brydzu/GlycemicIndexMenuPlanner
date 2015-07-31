package com.naelteam.glycemicindexmenuplanner;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.naelteam.glycemicindexmenuplanner.fragment.EditMenuFragment;
import com.naelteam.glycemicindexmenuplanner.fragment.ListGIFragment;
import com.naelteam.glycemicindexmenuplanner.model.Product;


public class MainActivity extends AppCompatActivity implements AppCompatActivityInterface{

    private final static String TAG = MainActivity.class.getSimpleName();

    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dumpDeviceInfos();

        initFragment(savedInstanceState);
    }

    private void dumpDeviceInfos(){
        final Configuration configuration = getResources().getConfiguration();
        final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        Log.d(TAG, "dumpDeviceInfos - screenWidthDp = " + configuration.screenWidthDp);
        Log.d(TAG, "dumpDeviceInfos - screenHeightDp = " + configuration.screenHeightDp);
        Log.d(TAG, "dumpDeviceInfos - density = " + displayMetrics.density);
        Log.d(TAG, "dumpDeviceInfos - densityDpi = " + displayMetrics.densityDpi);
        Log.d(TAG, "dumpDeviceInfos - widthPixels = " + displayMetrics.widthPixels);
        Log.d(TAG, "dumpDeviceInfos - heightPixels = " + displayMetrics.heightPixels);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume - ");
    }

    @Override
    public void onInitDrawerLayout() {
        initDrawerLayout();
    }

    @Override
    public void showNavigationDrawer() {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    public void hideNavigationDrawer() {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TAG, "onPause - ");
    }

    private void initFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.main_content, ListGIFragment.newInstance());
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onDisplayGlycemicIndexDetails(Product product) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //fragmentTransaction.replace(R.id.main_content, DetailsGIFragment.newInstance(product));
        fragmentTransaction.replace(R.id.main_content, EditMenuFragment.newInstance());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onSearchDetailGIError() {
        getSupportFragmentManager().popBackStack();

        Toast.makeText(this, "Error on getting this product", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setToolbar(Toolbar toolbar) {
        mToolbar = toolbar;
    }

    private void initDrawerLayout() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

 /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
