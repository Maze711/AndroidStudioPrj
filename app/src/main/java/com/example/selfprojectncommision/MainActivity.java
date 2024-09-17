package com.example.selfprojectncommision;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Spinner cupcakeSpinner, drinkSpinner;
    private EditText quantityCupcake, quantityDrink;
    private TextView subtotalCupcake, subtotalDrink, summaryFood, summaryDrinks, amountDue;
    private Button orderButton;

    private String[] cupcakeFlavors = {"CHOCOLATE", "RAISINS", "PINEAPPLE", "APPLE", "MANGO", "BANANA"};
    private double[] cupcakePrices = {25.0, 50.0, 40.0, 30.0, 35.0, 45.0};

    private String[] drinkFlavors = {"SOFTDRINKS", "ICED TEA", "COFFEE", "APPLE JUICE", "MANGO JUICE", "PINEAPPLE JUICE"};
    private double[] drinkPrices = {25.0, 50.0, 40.0, 30.0, 35.0, 45.0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupSpinners();
        setupOrderButton();
    }

    private void initializeViews() {
        cupcakeSpinner = findViewById(R.id.cupcakeSpinner);
        drinkSpinner = findViewById(R.id.drinkSpinner);
        quantityCupcake = findViewById(R.id.quantityCupcake);
        quantityDrink = findViewById(R.id.quantityDrink);
        subtotalCupcake = findViewById(R.id.subtotalCupcake);
        subtotalDrink = findViewById(R.id.subtotalDrink);
        summaryFood = findViewById(R.id.summaryFood);
        summaryDrinks = findViewById(R.id.summaryDrinks);
        amountDue = findViewById(R.id.amountDue);
        orderButton = findViewById(R.id.orderButton);
    }

    private void setupSpinners() {
        ArrayAdapter<String> cupcakeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cupcakeFlavors);
        cupcakeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cupcakeSpinner.setAdapter(cupcakeAdapter);

        ArrayAdapter<String> drinkAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, drinkFlavors);
        drinkAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        drinkSpinner.setAdapter(drinkAdapter);

        cupcakeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateSubtotal(cupcakePrices[position], quantityCupcake, subtotalCupcake);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        drinkSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateSubtotal(drinkPrices[position], quantityDrink, subtotalDrink);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupOrderButton() {
        orderButton.setOnClickListener(v -> calculateTotal());
    }

    private void updateSubtotal(double price, EditText quantityView, TextView subtotalView) {
        int quantity = Integer.parseInt(quantityView.getText().toString());
        double subtotal = price * quantity;
        subtotalView.setText(String.format("Subtotal: %.2f", subtotal));
        calculateTotal();
    }

    private void calculateTotal() {
        double cupcakeSubtotal = Double.parseDouble(subtotalCupcake.getText().toString().replace("Subtotal: ", ""));
        double drinkSubtotal = Double.parseDouble(subtotalDrink.getText().toString().replace("Subtotal: ", ""));

        summaryFood.setText(String.format("FOOD: %.2f", cupcakeSubtotal));
        summaryDrinks.setText(String.format("DRINKS: %.2f", drinkSubtotal));

        double total = cupcakeSubtotal + drinkSubtotal;
        amountDue.setText(String.format("AMOUNT DUE: %.2f", total));
    }
}