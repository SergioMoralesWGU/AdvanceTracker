package com.svartingknas.wguadvancetracker.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.svartingknas.wguadvancetracker.AssessmentListActivity;
import com.svartingknas.wguadvancetracker.CourseDetail;
import com.svartingknas.wguadvancetracker.CourseListActivity;
import com.svartingknas.wguadvancetracker.R;
import com.svartingknas.wguadvancetracker.database.InventoryManagementRepository;
import com.svartingknas.wguadvancetracker.entities.AssessmentEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import static androidx.core.app.ActivityCompat.startActivityForResult;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {

    class AssessmentViewHolder extends RecyclerView.ViewHolder{
        private final TextView assessmentCourseId;
        private final TextView assessmentName;
        private final TextView assessmentType;
        private final TextView assessmentDate;
        private final ImageButton deleteAssessment;

        private AssessmentViewHolder(View itemView) {
            super(itemView);
            assessmentCourseId = itemView.findViewById(R.id.tv_assessment_courseId);
            assessmentName = itemView.findViewById(R.id.tv_assessment_title);
            assessmentType = itemView.findViewById(R.id.tv_assessment_type);
            assessmentDate = itemView.findViewById(R.id.tv_assessment_date);
            deleteAssessment = itemView.findViewById(R.id.delete_assessment_btn);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final AssessmentEntity current = assessmentList.get(position);
                    Intent intent = new Intent(context, CourseDetail.class);
                    intent.putExtra("assessmentId", current.getId());
                    intent.putExtra("assessmentName", current.getAssessmentName());
                    intent.putExtra("assessmentType", current.getAssessmentType());
                    intent.putExtra("assessmentDate", current.getAssessmentDate());
                    intent.putExtra("assessmentCourseId", current.getAssessmentCourseId());
                    intent.putExtra("position", position);
                    context.startActivity(intent);

                }
            });
            deleteAssessment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AssessmentEntity current = assessmentList.get(getAdapterPosition());
                    InventoryManagementRepository.deleteAssessmentById(current.getId());
                }
            });
        }
    }

    private final LayoutInflater layoutInflater;
    private final Context context;
    private List<AssessmentEntity> assessmentList;

    public AssessmentAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.context=context;
    }

    @Override
    public AssessmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.course_assessment_rv, parent, false);

        return new AssessmentViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull AssessmentViewHolder holder, int position) {
        String pattern = "MM/dd/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);

        if (assessmentList != null){
            AssessmentEntity current = assessmentList.get(position);
            holder.assessmentCourseId.setText(Integer.toString(current.getAssessmentCourseId()));
            holder.assessmentName.setText(current.getAssessmentName());
            holder.assessmentType.setText(current.getAssessmentType());
            holder.assessmentDate.setText(df.format(current.getAssessmentDate()));
        } else {
            holder.assessmentCourseId.setText("No data");
            holder.assessmentName.setText("No data");
            holder.assessmentType.setText("No data");
            holder.assessmentDate.setText("No data");
        }

    }
    public void setAssessments(List<AssessmentEntity> mAssessments) {
        assessmentList = mAssessments;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).

    @Override
    public int getItemCount() {
        if (assessmentList != null)
            return assessmentList.size();
        else return 0;
    }


}
