package com.robiultech.blutoothapps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    CheckBox enableButton, visibleButton;
    ImageView img_searchButton;
    TextView textView;
    ListView listView;
    private BluetoothAdapter bluetoothAdapter;
    Set<BluetoothDevice> pairedDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enableButton = findViewById(R.id.enable_bt);
        visibleButton = findViewById(R.id.visible_bt);
        img_searchButton = findViewById(R.id.img_search_bTId);
        textView = findViewById(R.id.tvText);
        listView = findViewById(R.id.listViewId);
        textView.setText(getLocalBluetoothName());
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth not Supported", Toast.LENGTH_SHORT).show();
            finish();
        }
        if (bluetoothAdapter.isEnabled()) {

            enableButton.setChecked(true);
        }
        enableButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheck) {
                if (!isCheck) {
                    bluetoothAdapter.disable();
                    Toast.makeText(MainActivity.this, "Bluetooth turned off", Toast.LENGTH_SHORT).show();

                } else {
                    Intent intentOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

                    startActivityForResult(intentOn, 0);
                    Toast.makeText(MainActivity.this, "Turned on", Toast.LENGTH_SHORT).show();
                }
            }
        });
        visibleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheck) {
                if (isCheck) {
                    Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);

                    startActivityForResult(getVisible, 0);
                    Toast.makeText(MainActivity.this, "Visible for 2 min", Toast.LENGTH_SHORT).show();
                }
            }
        });
        img_searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list();
            }
        });
    }

    public void list() {

        pairedDevice = bluetoothAdapter.getBondedDevices();

        ArrayList list = new ArrayList();
        for (BluetoothDevice bt : pairedDevice) {

            list.add(bt.getName());
        }
        Toast.makeText(this, "Showing list", Toast.LENGTH_SHORT).show();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }

    public String getLocalBluetoothName() {

        if (bluetoothAdapter == null) {

            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }

        String name = bluetoothAdapter.getName();
        if(name==null){
            name= bluetoothAdapter.getAddress();

        }
        return name;
}

}