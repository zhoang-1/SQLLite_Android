package com.example.learning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.learning.BeerAdapter.BeerAdapter;
import com.example.learning.Database.Database;
import com.example.learning.Model.Beer;
import com.example.learning.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Database db;
    BeerAdapter adapter;

    ArrayList<Beer> beers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prepareDb();
        loadData();
    }

    private void prepareDb() {
        db = new Database(this);
        db.createFakeData();
    }
    private void loadData() {
        adapter = new BeerAdapter(MainActivity.this, R.layout.item_beer , getDataFromDb());
        binding.lvBeer.setAdapter(adapter);
    }

    private List<Beer> getDataFromDb() {
        beers =new ArrayList<>();
        Cursor cursor =db.queryData("SELECT * FROM" + Database.TBL_NAME);

        while (cursor.moveToNext()){
            beers.add(new Beer(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2)));
        }
        cursor.close();
        return beers;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.mnAdd){
            Dialog dialog =new Dialog(this);

            dialog.setContentView(R.layout.add_beer);
            EditText editName =dialog.findViewById(R.id.edtProductName);
            EditText editPrice = dialog.findViewById(R.id.edtProductPrice);
            Button btnSave =dialog.findViewById(R.id.btnSave);
            Button btnCannel = dialog.findViewById(R.id.btnCancel);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name  =editName.getText().toString();
                    double price =Double.parseDouble(editPrice.getText().toString());
                    db.execSql("INSERT INTO " + Database.TBL_NAME + "VALUE (NULL , '"+name+"' ,' "+price +"');");
                    loadData();
                    dialog.dismiss();
                }
            });
            btnCannel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void openEditDialog(Beer b) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.edit_beer);
        EditText editName =dialog.findViewById(R.id.edtProductName);
        editName.setText(b.getBeerName());
        EditText editPrice = dialog.findViewById(R.id.edtProductPrice);
        editPrice.setText(String.valueOf(b.getBeerPrice()));
        Button btnSave =dialog.findViewById(R.id.btnSave);
        Button btnCannel = dialog.findViewById(R.id.btnCancel);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name  =editName.getText().toString();
                double price =Double.parseDouble(editPrice.getText().toString());
                db.execSql("UPDATE " + Database.TBL_NAME + "SET " + Database.COL_NAME + " =  '  " + name + " ',  " + Database.COL_PRICE + " = " +price +  " WHERE " +Database.COL_CODE + " = "  + b.getBeerCode());
                loadData();
                dialog.dismiss();
            }
        });
        btnCannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    public void openDeleteDialog(Beer b) {
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setTitle("Xac Nham Xoa !");
        builder.setIcon(R.drawable.delete);
        builder.setMessage("Xoa Thuc uong " +b.getBeerName() + " ? " );

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.execSql("DELETE" + Database.TBL_NAME + "WHERE" + Database.COL_CODE + " = " + b.getBeerCode() + ";");
                loadData();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog dialog =builder.create();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
        dialog.show();
    }
}