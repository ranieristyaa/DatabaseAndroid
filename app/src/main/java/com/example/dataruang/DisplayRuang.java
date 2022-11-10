package com.example.dataruang;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayRuang extends AppCompatActivity {
    private DBHelper mydb;
    TextView nama;
    TextView kapasitas;
    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_ruang);

        nama = (TextView) findViewById(R.id.editTextNama);
        kapasitas = (TextView) findViewById(R.id.editTextKapasitas);


        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        {
            int Value = extras.getInt("id");

            if (Value > 0) {
                //means this is the view part not the add contact part.
                Cursor rs = mydb.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();

                int ix = rs.getColumnIndex(DBHelper.COLUMN_NAMA);
                String stunama = rs.getString(ix);
                int iz = rs.getColumnIndex(DBHelper.COLUMN_KAPASITAS);
                String stukapasitas = rs.getString(iz);


                if (!rs.isClosed()) {
                    rs.close();
                }
                Button b = (Button) findViewById(R.id.button1);
                b.setVisibility(View.INVISIBLE);

                nama.setText(stunama);
                nama.setFocusable(false);
                nama.setClickable(false);

                kapasitas.setText(stukapasitas);
                kapasitas.setFocusable(false);
                kapasitas.setClickable(false);


            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            int Value = extras.getInt("id");
            if (Value > 0) {
                getMenuInflater().inflate(R.menu.menu_display_ruang, menu);
            } else {
                getMenuInflater().inflate(R.menu.menu_main, menu);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.Edit:
                Button b = (Button) findViewById(R.id.button1);
                b.setVisibility(View.VISIBLE);
                nama.setEnabled(true);
                nama.setFocusableInTouchMode(true);
                nama.setClickable(true);

                kapasitas.setEnabled(true);
                kapasitas.setFocusableInTouchMode(true);
                kapasitas.setClickable(true);


                return true;
            case R.id.Delete:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.deleteContact)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mydb.deleteRuangan(id_To_Update);
                                Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                AlertDialog d = builder.create();
                d.setTitle("Are you sure ?");
                d.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void saveData(View view) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");
            if (Value > 0) {
                if (mydb.updateRuangan(Value, nama.getText().toString(), kapasitas.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Record not updated", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (mydb.addRuangan(nama.getText().toString(),  kapasitas.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Successfully Added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Record not added", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }
    }
}


