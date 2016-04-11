package com.ps.comunio.comuniops;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TabHost;


public class Home_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Button btTwitter;
    Button btFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Pestañas
        Resources res = getResources();
        TabHost tabs = (TabHost) findViewById(android.R.id.tabhost);
        tabs.setup();
        TabHost.TabSpec spec = tabs.newTabSpec("Pestaña 1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Social");
        tabs.addTab(spec);

        tabs.setup();
        TabHost.TabSpec spec1 = tabs.newTabSpec("Pestaña 2");
        spec1.setContent(R.id.tab2);
        spec1.setIndicator("Noticias");
        tabs.addTab(spec1);

        //Listeners de los botones de Home
        btTwitter= (Button) findViewById(R.id.button);
        btTwitter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent j = new Intent(Home_Activity.this, Twitter_Activity.class);
                startActivity(j);
            }
        });

        btFacebook= (Button) findViewById(R.id.button2);
        btFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(Home_Activity.this, Facebook_Activity.class);

                startActivity(j);
            }
        });


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_ligas) {
            String usr2 = getIntent().getStringExtra("user");
            Intent i = new Intent(Home_Activity.this, Liga_Activity.class);
            i.putExtra("user", usr2);
            startActivity(i);
        } else if (id == R.id.nav_miequipo) {
            String usr = getIntent().getStringExtra("user");
            Intent i = new Intent(Home_Activity.this, Equipo_Activity.class);
            i.putExtra("user", usr);
            startActivity(i);
        } else if (id == R.id.nav_mercado) {
            //Intent usuario
            String usr1 = getIntent().getStringExtra("user");
            Intent i = new Intent(Home_Activity.this, Mercado_Activity.class);
            i.putExtra("user", usr1);
            startActivity(i);
        } else if (id == R.id.nav_jornada) {
            Intent i = new Intent(Home_Activity.this, Jornadas_Activity.class);
            startActivity(i);
        } else if (id == R.id.nav_jugadores) {
            Intent i = new Intent(this, Jugadores_Activity.class);
            startActivity(i);
        }else if (id == R.id.nav_cerrarsesion){
        Intent i = new Intent(Home_Activity.this, Login_Activity.class);
        i.putExtra("user", "");
        startActivity(i);
        finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
