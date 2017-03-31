package com.petmap.petmap;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private Button button_add;
    private ListView listView;

    private List<Pet> pets;
    private DataBaseHelper myDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDataBase = new DataBaseHelper(this);
        pets = myDataBase.getAllPets();

        relateViews();
        setListeners();
        populateListView();

        listView.setOnItemClickListener(MainActivity.this);
    }

    private void relateViews() {
        button_add = (Button)findViewById(R.id.button_add);
    }

    private void setListeners() {
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity_PetDataSetup();
            }
        });

        //listView.setOnItemClickListener(MainActivity.this);
    }

    // Launches PetDataSetup activity
    private void launchActivity_PetDataSetup() {
        Intent intent = new Intent(this, PetDataSetup.class);
        startActivity(intent);
    }

    // Launches PetInfo activity
    private void launchActivity_PetInfo() {
        Intent intent = new Intent(this, PetInfo.class);
        startActivity(intent);
    }

    // This method is called when ListView item is selected
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // Save selected item
        PetInfo.selectedPet = pets.get(position);

        launchActivity_PetInfo();
    }

    private void populateListView() {
        ArrayAdapter<Pet> adapter = new MyListAdapter();
        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

    // This class is necessary to work with ListView
    private class MyListAdapter extends ArrayAdapter<Pet> {
        public MyListAdapter() {
            super(MainActivity.this, R.layout.item_view, pets);
        }

        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
            }

            Pet currentPet = pets.get(position);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView_petImage);
            imageView.setImageResource(R.drawable.paw);

            TextView textViewPetName = (TextView) itemView.findViewById(R.id.textView_petName);
            textViewPetName.setText(currentPet.getName());

            return itemView;
        }

    }

    // This method is called when user press back key
    // Allert dialog will pop up asking if user wants to leave
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Are you sure you want to leave?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        moveTaskToBack(true);
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
