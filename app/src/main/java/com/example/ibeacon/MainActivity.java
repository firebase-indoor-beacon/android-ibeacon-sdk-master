package com.example.ibeacon;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.SimpleAdapter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.radiusnetworks.ibeacon.IBeaconManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer=findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MessageFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_beacon);
        }
        String url = "http://172.17.135.108:8181/api/product";
        getData(url);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_beacon:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MessageFragment()).commit();
                Intent beacon_Intent = new Intent(this,MonitoringActivity.class);
                this.startActivity(beacon_Intent);
                verifyBluetooth();
                break;
            case R.id.nav_Popular:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new PopularFragment()).commit();
                Intent populer_Intent = new Intent(this,PopolarActivity.class);
                this.startActivity(populer_Intent);
                break;
            case R.id.nav_pttarticle:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new PttarticleFragment()).commit();
                break;
            case R.id.nav_sale:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SaleFragment()).commit();
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    public void onMonitoringClicked(View view) {
        Intent myIntent = new Intent(this, MonitoringActivity.class);
        this.startActivity(myIntent);
        verifyBluetooth();
    }
    private void verifyBluetooth() {

        try {
            if (!IBeaconManager.getInstanceForApplication(this).checkAvailability()) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Bluetooth not enabled");
                builder.setMessage("Please enable bluetooth in settings and restart this application.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        finish();
                        System.exit(0);
                    }

                });
                builder.show();

            }
        } catch (RuntimeException e) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Bluetooth LE not available");
            builder.setMessage("Sorry, this device does not support Bluetooth LE.");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    finish();
                    System.exit(0);
                }

            });
            builder.show();

        }
    }
    public String getData(String urlString){
        String result="";
        //使用JsonObjectRequest類別要求JSON資料
        JsonObjectRequest jsonObjectRequest=
                new JsonObjectRequest(urlString, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            //Velloy採非同步作業，Response.Listener  監聽回應
                            public void onResponse(JSONObject response) {
                                Log.d("回傳結果", "結果=" + response.toString());
                                try {
                                    parseJSON(response);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    //Response.ErrorListener 監聽錯誤
                    public void onErrorResponse(VolleyError error) {
                        Log.d("回傳結果", "錯誤訊息:" + error.toString());
                    }
                });
        Volley.newRequestQueue(this).add(jsonObjectRequest);
        return result;
    }
    private void parseJSON(JSONObject jsonObject) throws JSONException{
        ArrayList<Map<String,Object>> list = new ArrayList<Map<String, Object>>();

        JSONArray data = jsonObject.getJSONArray("data");
        for  (int i = 0; i < data.length(); i++){
            HashMap<String,Object> item  = new HashMap<String, Object>();
            JSONObject o = data.getJSONObject(i);
            Log.d("msg","name="+o.get("article_title"));
            item.put("name",o.getString("article_title"));
            item.put("type",o.getString("article_type"));
            list.add(item);
        }
        //create json list
        Gson gson = new Gson();
        String json = gson.toJson(list);
        SharedPreferences sp = getSharedPreferences("Store", MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sp.edit();
        prefEditor.putString("Set",json);
        prefEditor.commit();
    }
}