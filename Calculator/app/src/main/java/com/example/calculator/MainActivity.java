package com.example.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private double num1 = 0;
    private double num2 = 0;
    private String operation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up onClickListener for the buttons
        Button button = findViewById(R.id.buttonEquals);
        button.setOnClickListener(view -> {
            // The 'operation' variable should be final or effectively final
            final String operation = view.getTag().toString();
            if (operation.equals("/") && num2 == 0) {
                Toast.makeText(MainActivity.this, "La division par zéro n'est pas permise", Toast.LENGTH_SHORT).show();
                return;
            }

            // Send the data to ResultActivity
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            intent.putExtra("num1", num1);
            intent.putExtra("num2", num2);
            intent.putExtra("operation", operation);
            startActivityForResult(intent, 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // Retrieve the result from ResultActivity
            final double result = data.getDoubleExtra("resultat", 0);
            Toast.makeText(this, "Le résultat est : " + result, Toast.LENGTH_SHORT).show();
        }
    }
}
