package com.example.ium_gb;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private User currentUser;
    private Button logoutButton;
    private Button viewGradesButton;
    private TextView userNameTextView;
    private TextView weightedAverageTextView;
    private TextView arithmeticMeanTextView;
    private TextView graduationGradeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String email = getIntent().getStringExtra("currentUser");
        currentUser = UserRegistry.getUserByEmail(email);

        logoutButton = findViewById(R.id.logout_button);
        viewGradesButton = findViewById(R.id.view_grades_button);
        userNameTextView = findViewById(R.id.user_name);
        weightedAverageTextView = findViewById(R.id.weighted_average);
        arithmeticMeanTextView = findViewById(R.id.arithmetic_mean);
        graduationGradeTextView = findViewById(R.id.graduation_grade);

        userNameTextView.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
        weightedAverageTextView.setText("Media Ponderata: " + String.format("%.2f", currentUser.getWeightedAverage()));
        arithmeticMeanTextView.setText("Media Aritmetica: " + String.format("%.2f", currentUser.getArithmeticMean()));
        graduationGradeTextView.setText("Voto di Laurea: " + String.format("%.2f", currentUser.getGraduationGrade()));

        logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        viewGradesButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GradesActivity.class);
            intent.putExtra("currentUser", email);
            startActivity(intent);
        });
    }
}
