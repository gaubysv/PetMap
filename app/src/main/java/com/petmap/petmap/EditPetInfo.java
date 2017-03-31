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

public class EditPetInfo extends AppCompatActivity {

    public static boolean isDateSet;

    private EditText editText_newName;
    private EditText editText_newPhoneNo;
    private EditText editText_newCollarCode;

    private Button Button_newDate;
    private Button Button_save;
    private Button Button_cancel;

    private DataBaseHelper myDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pet_info);

        relateViews();
        setListeners();
    }

    private void relateViews() {
        editText_newName = (EditText)findViewById(R.id.editText_newName);
        editText_newPhoneNo = (EditText)findViewById(R.id.editText_newPhoneNo);
        editText_newCollarCode = (EditText)findViewById(R.id.editText_newCollarCode);

        Button_newDate = (Button)findViewById(R.id.button_newDate);
        Button_save = (Button)findViewById(R.id.button_save);
        Button_cancel = (Button)findViewById(R.id.button_cancel);

        isDateSet = false;
    }

    private void setListeners() {
        Button_newDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate();
            }
        });

        Button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveButtonAction();
            }
        });

        Button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allertBox();
            }
        });
    }

    private void allertBox() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Are you sure you want to leave?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        launchActivity_PetInfo();
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

    private void launchActivity_PetInfo() {
        Intent intent = new Intent(this, PetInfo.class);
        startActivity(intent);
    }

    private void saveButtonAction() {
        String newName = editText_newName.getText().toString();
        String newPhoneNo = editText_newPhoneNo.getText().toString();
        String newCollarCode = editText_newCollarCode.getText().toString();

        if (newName.length() > 0) {
            PetInfo.selectedPet.setName(newName);
        }

        if (newPhoneNo.toString().length() > 0) {
            PetInfo.selectedPet.setTelNo(newPhoneNo);
        }

        if (newCollarCode.toString().length() > 0) {
            PetInfo.selectedPet.setCode(newCollarCode);
        }

        if (isDateSet) {
            PetInfo.selectedPet.setYear(PetDataSetup.year);
            PetInfo.selectedPet.setMonth(PetDataSetup.month);
            PetInfo.selectedPet.setDay(PetDataSetup.day);
        }

        myDataBase = new DataBaseHelper(this);
        myDataBase.updatePet(PetInfo.selectedPet);

        launchActivity_PetInfo();
    }

    @Override
    public void onBackPressed() {
        launchActivity_PetInfo();
    }

    private void pickDate(){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }
}
