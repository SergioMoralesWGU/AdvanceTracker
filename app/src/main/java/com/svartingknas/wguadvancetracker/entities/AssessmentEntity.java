package com.svartingknas.wguadvancetracker.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.svartingknas.wguadvancetracker.database.DateTypeConverter;

import java.util.Date;


@Entity(tableName = "assessment_table")
public class AssessmentEntity {
    @PrimaryKey
    private int id;

//    @NonNull
//    @ColumnInfo(name = "assessment_name")
    private String assessmentName;

//    @NonNull
//    @ColumnInfo(name = "assessment_date")
    @TypeConverters(DateTypeConverter.class)
    private Date assessmentDate;

//    @NonNull
//    @ColumnInfo(name = "assessment_type")
    private String assessmentType;

//    @ColumnInfo(name = "assessment_courseId")
    private int assessmentCourseId;

    @Override
    public String toString() {
        return "Assessment{" +
                "id=" + id +
                ", assessmentName='" + assessmentName + '\'' +
                ", assessmentDate='" + assessmentDate + '\'' +
                ", assessmentType='" + assessmentType + '\'' +
                ", assessmentCourseId=" + assessmentCourseId +
                '}';
    }

    public AssessmentEntity(int id, String assessmentName, Date assessmentDate, String assessmentType, int assessmentCourseId) {
        this.id = id;
        this.assessmentName = assessmentName;
        this.assessmentDate = assessmentDate;
        this.assessmentType = assessmentType;
        this.assessmentCourseId = assessmentCourseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(@NonNull String assessmentName) {
        this.assessmentName = assessmentName;
    }

    @NonNull
    public Date getAssessmentDate() {
        return assessmentDate;
    }

    public void setAssessmentDate(@NonNull Date assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    @NonNull
    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(@NonNull String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public int getAssessmentCourseId() {
        return assessmentCourseId;
    }

    public void setAssessmentCourseId(int assessmentCourseId) {
        this.assessmentCourseId = assessmentCourseId;
    }
}