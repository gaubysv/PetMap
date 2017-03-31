package com.petmap.petmap;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PetInfo extends AppCompatActivity {

    private TextView txt_petName;
    private TextView txt_birthDate;
    private TextView txt_collarPhone;

    private Button button_remove;
    private Button button_locate;
    private Button button_edit;

    public static Pet selectedPet;
    private DataBaseHelper myDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_info);

        relateViews();
        setText();
        setListeners();
    }

    private void relateViews() {
        txt_petName = (TextView)findViewById(R.id.textView_petName);
        txt_birthDate = (TextView)findViewById(R.id.textView_birthDate);
        txt_collarPhone = (TextView)findViewById(R.id.textView_collarPhone);

        button_remove = (Button)findViewById(R.id.button_remove);
        button_locate = (Button)findViewById(R.id.button_locate);
        button_edit = (Button)findViewById(R.id.button_edit);
    }

    private void setText() {
        txt_petName.setText(selectedPet.getName());
        txt_birthDate.setText(selectedPet.getYear() + "-" + (selectedPet.getMonth() + 1) + "-" + selectedPet.getDay());
        txt_collarPhone.setText(selectedPet.getTelNo());
    }

    private void setListeners() {
        button_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove();
            }
        });

        button_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity_EditPetInfo();
            }
        });

        button_locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity_MapsActivity();
            }
        });
    }

    private void remove() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Are you sure you want to delete this record?");
        builder1.setCancelable(true);
        myDataBase = new DataBaseHelper(this);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        myDataBase.deletePet(selectedPet);
                        launchActivity_MainActivity();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void launchActivity_MainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void launchActivity_EditPetInfo() {
        Intent intent = new Intent(this, EditPetInfo.class);
        startActivity(intent);
    }

    private void launchActivity_MapsActivity() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        launchActivity_MainActivity();
    }
}
