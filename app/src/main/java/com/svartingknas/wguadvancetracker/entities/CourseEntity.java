package com.svartingknas.wguadvancetracker.entities;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.svartingknas.wguadvancetracker.database.DateTypeConverter;

import java.util.Date;

@Entity(tableName = "courses_table")
public class CourseEntity {

    @PrimaryKey
    private int id;

//    @NonNull
//    @ColumnInfo(name = "course_title")
    private String courseTitle;

//    @NonNull
//    @ColumnInfo(name = "course_startDate")
    @TypeConverters(DateTypeConverter.class)
    private Date courseStartDate;

//    @NonNull
//    @ColumnInfo(name = "course_endDate")
    @TypeConverters(DateTypeConverter.class)
    private Date courseEndDate;

//    @NonNull
//    @ColumnInfo(name = "course_status")
    private String courseStatus;
    //    @NonNull
//    @ColumnInfo(name = "mentor_name")
    private String mentorName;

    //    @NonNull
//    @ColumnInfo(name = "mentor_phone")
    private String mentorPhone;

    //    @NonNull
//    @ColumnInfo(name = "mentor_email")
    private String mentorEmail;

//    @ColumnInfo(name = "course_termId")
    private int courseTermId;


    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseTitle='" + courseTitle + '\'' +
                ", courseStartDate='" + courseStartDate + '\'' +
                ", courseEndDate='" + courseEndDate + '\'' +
                ", courseStatus='" + courseStatus + '\'' +
                ", mentorName='" + mentorName + '\'' +
                ", mentorPhone='" + mentorPhone + '\'' +
                ", mentorEmail='" + mentorEmail + '\'' +
                ", courseTermId=" + courseTermId +
                '}';
    }

    public CourseEntity(int id, @NonNull String courseTitle, @NonNull Date courseStartDate, @NonNull Date courseEndDate, @NonNull String courseStatus,
                        @NonNull String mentorName, @NonNull String mentorEmail, @NonNull String mentorPhone, int courseTermId) {
        this.id = id;
        this.courseTitle = courseTitle;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.courseStatus = courseStatus;
        this.mentorName = mentorName;
        this.mentorPhone = mentorPhone;
        this.mentorEmail = mentorEmail;
        this.courseTermId = courseTermId;
    }

//    public CourseEntity(int id, String courseTitle, Date courseStartDate, Date courseEndDate,int courseTermId) {
//        this.id = id;
//        this.courseTitle = courseTitle;
//        this.courseStartDate = courseStartDate;
//        this.courseEndDate = courseEndDate;
//        this.courseTermId = courseTermId;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public Date getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(Date courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public Date getCourseEndDate() {
        return courseEndDate;
    }

    public void setCourseEndDate(Date courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public int getCourseTermId() {
        return courseTermId;
    }

    @NonNull
    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(@NonNull String mentorName) {
        this.mentorName = mentorName;
    }

    @NonNull
    public String getMentorPhone() {
        return mentorPhone;
    }

    public void setMentorPhone(@NonNull String mentorPhone) {
        this.mentorPhone = mentorPhone;
    }

    @NonNull
    public String getMentorEmail() {
        return mentorEmail;
    }

    public void setMentorEmail(@NonNull String mentorEmail) {
        this.mentorEmail = mentorEmail;
    }

    public void setCourseTermId(int courseTermId) {
        this.courseTermId = courseTermId;
    }

}


