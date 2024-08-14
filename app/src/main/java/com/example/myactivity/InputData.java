package com.example.myactivity;

import android.database.Cursor;
import android.os.Bundle;
import android.content.Intent;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InputData extends AppCompatActivity {

    Button buttonSv;
    EditText textNama, textUmur, textMotto;
    DatabaseHelper dbmaster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_input_data);

        buttonSv = (Button) findViewById(R.id.btnSv);
        textNama = (EditText) findViewById(R.id.txtNama);
        textUmur = (EditText) findViewById(R.id.txtUmur);
        textMotto = (EditText) findViewById(R.id.txtMotto);
        dbmaster = new DatabaseHelper(this);

        Intent intent = getIntent();
        String id = intent.getStringExtra("ID");

        if (id != null) {
            Cursor data = dbmaster.getAllData();
            if (data.moveToFirst()) {
                do {
                    if (data.getString(0).equals(id)) {
                        textNama.setText(data.getString(1));
                        textUmur.setText(data.getString(2));
                        textMotto.setText(data.getString(3));
                        buttonSv.setText("Update Data");
                        break;
                    }
                } while (data.moveToNext());
            }

            buttonSv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isUpdated = dbmaster.updateData(id, textNama.getText().toString(), Integer.parseInt(textUmur.getText().toString()),textMotto.getText().toString());
                    if (isUpdated) {
                        Toast.makeText(InputData.this, "Data Updated", Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else
                        Toast.makeText(InputData.this, "Data Not Updated", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            buttonSv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isInserted = dbmaster.insertData(textNama.getText().toString(), Integer.parseInt(textUmur.getText().toString()),textMotto.getText().toString());
                    if (isInserted) {
                        Toast.makeText(InputData.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else
                        Toast.makeText(InputData.this, "Data Not Inserted", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}