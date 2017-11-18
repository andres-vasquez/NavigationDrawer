package com.example.android_instructor.navigationdrawer;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class MainActivity extends AppCompatActivity {

    //IDs de los items de navegacion
    private static final int DRAWER_ITEM_UNO = 1;
    private static final int DRAWER_ITEM_DOS = 2;
    private static final int DRAWER_ITEM_TRES = 3;
    private static final int DRAWER_ITEM_CUATRO = 4;

    //Definimos el entorno
    private Drawer drawer;
    private Context context;
    private FrameLayout contenedor;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context=this;

        //Antes de comenzar
        //Adicionar el tema  android:theme="@style/MaterialDrawerTheme.Light.DarkToolbar el manifest

        // Handle Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Contenedor es el espacio central
        contenedor=(FrameLayout)findViewById(R.id.contenedor);

        //Definimos el header

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        new ProfileDrawerItem().
                                withName("Mi usuario").
                                withEmail("usuario@email.com").
                                withIcon("https://firebasestorage.googleapis.com/v0/b/clasetekhne.appspot.com/o/nilton%2F1510968111865_trueno.jpg?alt=media&token=32ce89ac-9d67-4126-be81-cd9df7e71c97")
                )
                .build();

        //Definimos el Navigacion Drawer
        drawer = new DrawerBuilder(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        new DividerDrawerItem(),
                        //Deinimos los items de navegacion
                        new PrimaryDrawerItem().
                                withIdentifier(DRAWER_ITEM_UNO).
                                withName(R.string.item_uno).
                                withTextColor(getResources().getColor(R.color.primary)).
                                withIconColor(getResources().getColor(R.color.primary)).
                                withSelectedTextColor(getResources().getColor(R.color.colorAccent)).
                                withSelectedIconColor(getResources().getColor(R.color.colorAccent)).
                                withIcon(FontAwesome.Icon.faw_home),
                        new PrimaryDrawerItem().
                                withIdentifier(DRAWER_ITEM_DOS).
                                withName(R.string.item_dos).
                                withTextColor(getResources().getColor(R.color.primary)).
                                withIconColor(getResources().getColor(R.color.primary)).
                                withSelectedTextColor(getResources().getColor(R.color.colorAccent)).
                                withSelectedIconColor(getResources().getColor(R.color.colorAccent)).
                                withIcon(FontAwesome.Icon.faw_newspaper_o),
                        new PrimaryDrawerItem().
                                withIdentifier(DRAWER_ITEM_TRES).
                                withName(R.string.item_tres).
                                withTextColor(getResources().getColor(R.color.primary)).
                                withIconColor(getResources().getColor(R.color.primary)).
                                withSelectedTextColor(getResources().getColor(R.color.colorAccent)).
                                withSelectedIconColor(getResources().getColor(R.color.colorAccent)).
                                withIcon(FontAwesome.Icon.faw_calendar)
                ).addStickyDrawerItems(
                        //Este item se encuentra en la parte inferior
                        new SecondaryDrawerItem()
                                .withName(R.string.item_cuatro)
                                .withIdentifier(DRAWER_ITEM_CUATRO)
                                .withIcon(FontAwesome.Icon.faw_info)
                                .withTextColor(getResources().getColor(R.color.primary))
                                .withIconColor(getResources().getColor(R.color.primary))
                                .withSelectedTextColor(getResources().getColor(R.color.colorAccent))
                                .withSelectedIconColor(getResources().getColor(R.color.colorAccent))
                )
                //Accion Click sobre los items de menu
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener()
                {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        seleccionartItem(position);
                        return false;
                    }
                })
                .withSelectedItem(0)
                .withSavedInstance(savedInstanceState)
                .build();

        //Al inicializar se selecciona el primer item
        seleccionartItem(DRAWER_ITEM_UNO);
    }

    //Funcion de seleccion de item
    private void seleccionartItem(int i)
    {

        Fragment seleccionado = null;
        Toast.makeText(context,"Selecciono el item N "+i,Toast.LENGTH_LONG).show();
        switch (i){
            case DRAWER_ITEM_UNO:
                seleccionado = new UnoFragment();
                break;
            case DRAWER_ITEM_DOS:
                seleccionado = new DosFragment();
                break;
            case DRAWER_ITEM_TRES:
                seleccionado = new TresFragment();
                break;
            case DRAWER_ITEM_CUATRO:
                seleccionado = new CuatroFragment();
                break;
            default:
                break;
        }

        if(seleccionado!=null){
            FragmentManager fm =getSupportFragmentManager();
            fm.beginTransaction()
                    .replace(R.id.contenedor,seleccionado)
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //Guardamos la info del drawer
        if (drawer != null) {
            outState = drawer.saveInstanceState(outState);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Boton hamburguesa en el ActionBar para Abrir y Cerrar el Navigation Drawer
        if(item.getItemId()==android.R.id.home) {
            if (drawer.isDrawerOpen())
                drawer.closeDrawer();
            else
                drawer.openDrawer();
        }
        return super.onOptionsItemSelected(item);
    }
}
