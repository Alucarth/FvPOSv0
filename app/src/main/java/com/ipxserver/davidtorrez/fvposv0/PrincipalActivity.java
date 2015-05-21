package com.ipxserver.davidtorrez.fvposv0;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.ipxserver.davidtorrez.fvposv0.adapter.GridbarAdapter;
import com.ipxserver.davidtorrez.fvposv0.adapter.PagerAdapter;


public class PrincipalActivity extends ActionBarActivity {

    PagerAdapter pagerAdapter;
    ViewPager viewPager;
    GridbarAdapter gridbarAdapter;
    GridView gridbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        viewPager =(ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);

        //Todo: Revisar el tipo de contexto al cual pertenese el gridbarAdapter
        gridbarAdapter = new GridbarAdapter(getApplicationContext());
        gridbar= (GridView) findViewById(R.id.barraSaldo);
        gridbar.setAdapter(gridbarAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);


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
    }
}
