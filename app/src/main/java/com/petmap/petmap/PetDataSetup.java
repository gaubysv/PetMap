package com.petmap.petmap;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class PetDataSetup extends AppCompatActivity{

    public static boolean dateIsSet;
    public static int year;
    public static int month;
    public static int day;

    private Button button_setBirthDate;
    private Button button_save;
    private Button button_calcel;

    private DataBaseHelper myDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_data_setup);

        relateViews();
        setListeners();
    }

    private void relateViews() {
        button_setBirthDate = (Button)findViewById(R.id.button_birth_date);
        button_save = (Button)findViewById(R.id.button_save);
        button_calcel = (Button)findViewById(R.id.button_cancel);

        dateIsSet = false;
    }

    private void setListeners() {
        button_setBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate();
            }
        });

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSaveAction();
            }
        });

        button_calcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allertBox();
            }
        });
    }

    private void buttonSaveAction() {
        myDataBase = new DataBaseHelper(this);
        Pet pet = createPet();
        myDataBase.addPet(pet);
        Toast.makeText(getApplicationContext(), "New pet added",
                Toast.LENGTH_SHORT).show();
        launchActivity_MainActivity();
    }

    private void launchActivity_MainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void pickDate(){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    private Pet createPet() {
        EditText txt_petName= (EditText)findViewById(R.id.editText_petName);
        EditText txt_collarPhoneNo = (EditText)findViewById(R.id.editText_phoneNo);
        EditText txt_collarCode = (EditText)findViewById(R.id.editText_code);

        String name = txt_petName.getText().toString();
        String telNo = txt_collarPhoneNo.getText().toString();
        String code = txt_collarCode.getText().toString();

        Pet pet = new Pet(name, telNo, code);

        if (!dateIsSet) {
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);

            pet.setYear(year);
            pet.setMonth(month);
            pet.setDay(day);
        } else {
            pet.setYear(year);
            pet.setMonth(month);
            pet.setDay(day);
            dateIsSet = false;
        }
        return pet;
    }

    @Override
    public void onBackPressed() {
        launchActivity_MainActivity();
    }

    private void allertBox() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Are you sure you want to leave?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
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
}
