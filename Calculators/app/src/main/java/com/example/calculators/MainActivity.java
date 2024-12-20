package com.example.calculators;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText num1EditText, num2EditText;
    private Button btnAddition, btnSubtraction, btnMultiplication, btnDivision, btnMemorySave, btnMemoryUse;
    private TextView resultTextView;
    private double result;
    private double memory = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        num1EditText = findViewById(R.id.num1);
        num2EditText = findViewById(R.id.num2);
        btnAddition = findViewById(R.id.btnAddition);
        btnSubtraction = findViewById(R.id.btnSubtraction);
        btnMultiplication = findViewById(R.id.btnMultiplication);
        btnDivision = findViewById(R.id.btnDivision);
        btnMemorySave = findViewById(R.id.btnMemorySave);
        btnMemoryUse = findViewById(R.id.btnMemoryUse);
        resultTextView = findViewById(R.id.resultTextView);

        btnAddition.setOnClickListener(v -> performOperation("+"));
        btnSubtraction.setOnClickListener(v -> performOperation("-"));
        btnMultiplication.setOnClickListener(v -> performOperation("*"));
        btnDivision.setOnClickListener(v -> performOperation("/"));


        btnMemorySave.setOnClickListener(v -> {
            memory = result;
            Toast.makeText(MainActivity.this, "Result saved in memory", Toast.LENGTH_SHORT).show();
        });


        btnMemoryUse.setOnClickListener(v -> {
            num1EditText.setText(String.valueOf(memory));
            Toast.makeText(MainActivity.this, "Memory value used", Toast.LENGTH_SHORT).show();
        });


    }

    private void performOperation(String operation) {
        try {
            double num1 = Double.parseDouble(num1EditText.getText().toString());
            double num2 = Double.parseDouble(num2EditText.getText().toString());

            switch (operation) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    if (num2 == 0) {
                        Toast.makeText(MainActivity.this, "Cannot divide by zero", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    result = num1 / num2;
                    break;
            }

            resultTextView.setText(String.valueOf(result));
        } catch (NumberFormatException e) {
            Toast.makeText(MainActivity.this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
        }
    }
}
