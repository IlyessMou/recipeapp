package com.example.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private double num1, num2, result;
    private String operation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Retrieve data from MainActivity
        num1 = getIntent().getDoubleExtra("num1", 0);
        num2 = getIntent().getDoubleExtra("num2", 0);
        operation = getIntent().getStringExtra("operation");

        // Perform the operation
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
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    Toast.makeText(this, "Erreur: Division par zéro!", Toast.LENGTH_SHORT).show();
                    result = 0;
                }
                break;
        }

        // Send the result back to MainActivity
        Intent returnIntent = new Intent();
        returnIntent.putExtra("resultat", result);
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
