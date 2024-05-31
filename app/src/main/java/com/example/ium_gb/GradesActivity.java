package com.example.ium_gb;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;

public class GradesActivity extends AppCompatActivity implements ExamAdapter.OnExamListener {

    private User currentUser;
    private ExamAdapter examAdapter;
    private TextView noExamsMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        String email = getIntent().getStringExtra("currentUser");
        currentUser = UserRegistry.getUserByEmail(email);

        noExamsMessage = findViewById(R.id.no_exams_message);
        RecyclerView recyclerView = findViewById(R.id.exam_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        examAdapter = new ExamAdapter(this, currentUser.getExams(), this);
        recyclerView.setAdapter(examAdapter);

        updateNoExamsMessageVisibility();

        ImageView addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> openAddExamDialog());

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> navigateBackToMain());
    }

    private void openAddExamDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Aggiungi Esame");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_add_exam, (ViewGroup) findViewById(android.R.id.content), false);
        final TextInputEditText inputName = viewInflated.findViewById(R.id.input_exam_name);
        final TextInputEditText inputCfu = viewInflated.findViewById(R.id.input_exam_cfu);
        final TextInputEditText inputGrade = viewInflated.findViewById(R.id.input_exam_grade);

        builder.setView(viewInflated);

        builder.setPositiveButton(android.R.string.ok, null); // Set null to override later

        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.cancel());

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(dialogInterface -> {
            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(view -> {
                boolean isValid = true;
                String examName = inputName.getText().toString();
                String cfuText = inputCfu.getText().toString();
                String gradeText = inputGrade.getText().toString();

                if (TextUtils.isEmpty(examName)) {
                    inputName.setError("Il nome dell'esame è obbligatorio");
                    isValid = false;
                }

                if (TextUtils.isEmpty(cfuText)) {
                    inputCfu.setError("Il CFU è obbligatorio");
                    isValid = false;
                }

                int examCfu = 0;
                if (!TextUtils.isEmpty(cfuText)) {
                    examCfu = Integer.parseInt(cfuText);
                }

                int examGrade = -1;

                if (!TextUtils.isEmpty(gradeText)) {
                    int gradeValue = Integer.parseInt(gradeText);
                    if (gradeValue >= 18 && gradeValue <= 30) {
                        examGrade = gradeValue;
                    } else {
                        inputGrade.setError("Il voto deve essere tra 18 e 30");
                        isValid = false;
                    }
                }

                if (isValid) {
                    Exam exam = new Exam(examName, examCfu, examGrade);
                    currentUser.addExam(exam);
                    examAdapter.notifyDataSetChanged();
                    updateNoExamsMessageVisibility();
                    dialog.dismiss();
                } else {
                    Toast.makeText(this, "Correggi i campi evidenziati", Toast.LENGTH_SHORT).show();
                }
            });
        });

        dialog.show();
    }

    private void openEditExamDialog(int position) {
        Exam exam = currentUser.getExams().get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Modifica Esame");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_add_exam, (ViewGroup) findViewById(android.R.id.content), false);
        final TextInputEditText inputName = viewInflated.findViewById(R.id.input_exam_name);
        final TextInputEditText inputCfu = viewInflated.findViewById(R.id.input_exam_cfu);
        final TextInputEditText inputGrade = viewInflated.findViewById(R.id.input_exam_grade);

        inputName.setText(exam.getName());
        inputCfu.setText(String.valueOf(exam.getCfu()));
        inputGrade.setText(exam.getGrade() == -1 ? "" : String.valueOf(exam.getGrade()));

        builder.setView(viewInflated);

        builder.setPositiveButton(android.R.string.ok, null); // Set null to override later

        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.cancel());

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(dialogInterface -> {
            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(view -> {
                boolean isValid = true;
                String examName = inputName.getText().toString();
                String cfuText = inputCfu.getText().toString();
                String gradeText = inputGrade.getText().toString();

                if (TextUtils.isEmpty(examName)) {
                    inputName.setError("Il nome dell'esame è obbligatorio");
                    isValid = false;
                }

                if (TextUtils.isEmpty(cfuText)) {
                    inputCfu.setError("Il CFU è obbligatorio");
                    isValid = false;
                }

                int examCfu = 0;
                if (!TextUtils.isEmpty(cfuText)) {
                    examCfu = Integer.parseInt(cfuText);
                }

                int examGrade = -1;

                if (!TextUtils.isEmpty(gradeText)) {
                    int gradeValue = Integer.parseInt(gradeText);
                    if (gradeValue >= 18 && gradeValue <= 30) {
                        examGrade = gradeValue;
                    } else {
                        inputGrade.setError("Il voto deve essere tra 18 e 30");
                        isValid = false;
                    }
                }

                if (isValid) {
                    exam.setName(examName);
                    exam.setCfu(examCfu);
                    exam.setGrade(TextUtils.isEmpty(inputGrade.getText().toString()) ? -1 : Integer.parseInt(inputGrade.getText().toString()));
                    examAdapter.notifyItemChanged(position);
                    dialog.dismiss();
                } else {
                    Toast.makeText(this, "Correggi i campi evidenziati", Toast.LENGTH_SHORT).show();
                }
            });
        });

        dialog.show();
    }

    private void confirmDeleteExam(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Elimina Esame")
                .setMessage("Sei sicuro di voler eliminare questo esame?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    currentUser.getExams().remove(position);
                    examAdapter.notifyItemRemoved(position);
                    updateNoExamsMessageVisibility();
                })
                .setNegativeButton(android.R.string.no, (dialog, which) -> dialog.cancel())
                .show();
    }

    private void navigateBackToMain() {
        Intent intent = new Intent(GradesActivity.this, MainActivity.class);
        intent.putExtra("currentUser", currentUser.getEmail());
        startActivity(intent);
        finish();
    }

    private void updateNoExamsMessageVisibility() {
        if (currentUser.getExams().isEmpty()) {
            noExamsMessage.setVisibility(View.VISIBLE);
        } else {
            noExamsMessage.setVisibility(View.GONE);
        }
    }

    @Override
    public void onEditClick(int position) {
        openEditExamDialog(position);
    }

    @Override
    public void onDeleteClick(int position) {
        confirmDeleteExam(position);
    }
}
