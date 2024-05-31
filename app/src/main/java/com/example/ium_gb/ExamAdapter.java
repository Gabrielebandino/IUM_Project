package com.example.ium_gb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.ExamViewHolder> {

    private Context context;
    private ArrayList<Exam> examList;
    private OnExamListener onExamListener;

    public ExamAdapter(Context context, ArrayList<Exam> examList, OnExamListener onExamListener) {
        this.context = context;
        this.examList = examList;
        this.onExamListener = onExamListener;
    }

    @NonNull
    @Override
    public ExamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_exam, parent, false);
        return new ExamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExamViewHolder holder, int position) {
        Exam exam = examList.get(position);
        holder.examName.setText(exam.getName());
        holder.examCfu.setText("CFU: " + exam.getCfu());
        holder.examGrade.setText("Voto: " + (exam.getGrade() == -1 ? "Non sostenuto" : exam.getGrade()));

        holder.editIcon.setOnClickListener(v -> onExamListener.onEditClick(position));
        holder.deleteIcon.setOnClickListener(v -> onExamListener.onDeleteClick(position));
    }

    @Override
    public int getItemCount() {
        return examList.size();
    }

    public static class ExamViewHolder extends RecyclerView.ViewHolder {
        TextView examName, examCfu, examGrade;
        ImageView editIcon, deleteIcon;

        public ExamViewHolder(@NonNull View itemView) {
            super(itemView);
            examName = itemView.findViewById(R.id.exam_name);
            examCfu = itemView.findViewById(R.id.exam_cfu);
            examGrade = itemView.findViewById(R.id.exam_grade);
            editIcon = itemView.findViewById(R.id.edit_icon);
            deleteIcon = itemView.findViewById(R.id.delete_icon);
        }
    }

    public interface OnExamListener {
        void onEditClick(int position);
        void onDeleteClick(int position);
    }
}
