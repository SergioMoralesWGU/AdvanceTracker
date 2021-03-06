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

import com.svartingknas.wguadvancetracker.CourseDetail;
import com.svartingknas.wguadvancetracker.CourseListActivity;
import com.svartingknas.wguadvancetracker.R;
import com.svartingknas.wguadvancetracker.database.CourseDao;
import com.svartingknas.wguadvancetracker.database.InventoryManagementRepository;
import com.svartingknas.wguadvancetracker.entities.CourseEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    class CourseViewHolder extends RecyclerView.ViewHolder{
        //COURSE LIST ITEMS
        private final TextView clCourseTitle;
        private final TextView clCourseTermid;
        private final TextView clCourseStartDate;
        private final TextView clCourseEndDate;
        private final ImageButton clCourseDeleteBtn;

        private CourseViewHolder(View itemView) {
            super(itemView);
            // COURSE LIST
            clCourseTermid = itemView.findViewById(R.id.course_term_id);
            clCourseTitle = itemView.findViewById(R.id.tv_course_name);
            clCourseStartDate = itemView.findViewById(R.id.tv_course_start_date);
            clCourseEndDate = itemView.findViewById(R.id.tv_course_end_date);
            clCourseDeleteBtn = itemView.findViewById(R.id.btn_course_list_delete);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String pattern = "MM/dd/yyyy";
                    DateFormat dateFormat = new SimpleDateFormat(pattern);


                    int position = getAdapterPosition();
                    final CourseEntity current = courseList.get(position);
                    Intent intent = new Intent(context, CourseDetail.class);
                    intent.putExtra("courseId", current.getId());
                    intent.putExtra("courseTitle", current.getCourseTitle());
                    intent.putExtra("courseStatus", current.getCourseStatus());
                    intent.putExtra("courseStartDate", dateFormat.format(current.getCourseStartDate()));
                    intent.putExtra("courseEndDate", dateFormat.format(current.getCourseEndDate()));
                    intent.putExtra("mentorName", current.getMentorName());
                    intent.putExtra("mentorPhone", current.getMentorPhone());
                    intent.putExtra("mentorEmail", current.getMentorEmail());
                    intent.putExtra("termId", current.getCourseTermId());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
            clCourseDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InventoryManagementRepository.delete(courseList.get(getAdapterPosition()));
                }
            });

        }
    }

    private final LayoutInflater courseInflater;
    private final Context context;
    private List<CourseEntity> courseList;

    public CourseAdapter(Context context){
        courseInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = courseInflater.inflate(R.layout.course_list_rv, parent, false);
//        View detailView = courseInflater.inflate(R.layout.content_course_detail, parent, false);

        return new CourseViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        String pattern = "MM/dd/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        if (courseList != null){
            CourseEntity current = courseList.get(position);
            holder.clCourseTitle.setText(current.getCourseTitle());
            holder.clCourseTermid.setText(Integer.toString(current.getCourseTermId()));
            holder.clCourseStartDate.setText(df.format(current.getCourseStartDate()));
            holder.clCourseEndDate.setText(df.format(current.getCourseEndDate()));

        } else {
            holder.clCourseTitle.setText("no data");
            holder.clCourseTermid.setText("no data");
            holder.clCourseStartDate.setText("no data");
            holder.clCourseEndDate.setText("no data");

        }

    }

    public void setCourses(List<CourseEntity> courses) {
        courseList = courses;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (courseList != null)
            return courseList.size();
        else return 0;
    }


}
