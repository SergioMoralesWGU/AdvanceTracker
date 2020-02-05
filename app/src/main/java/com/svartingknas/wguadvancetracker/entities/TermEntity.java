package com.svartingknas.wguadvancetracker.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.svartingknas.wguadvancetracker.database.DateTypeConverter;

import java.util.Date;


@Entity(tableName = "terms_table")
public class TermEntity {

    @PrimaryKey
    private int id;

//    @NonNull
//    @ColumnInfo(name = "term_name")
    private String termName;

//    @NonNull
//    @ColumnInfo(name = "term_start")

    @TypeConverters(DateTypeConverter.class)
    private Date termStart;

//    @NonNull
//    @ColumnInfo(name = "term_end")
    @TypeConverters(DateTypeConverter.class)
    private Date termEnd;

    @Override
    public String toString() {
        return "Term{" +
                "id=" + id +
                ", termName='" + termName + '\'' +
                ", termStart='" + termStart + '\'' +
                ", termEnd='" + termEnd + '\'' +
                '}';
    }

    public TermEntity(int id, String termName, Date termStart, Date termEnd) {
        this.id = id;
        this.termName = termName;
        this.termStart = termStart;
        this.termEnd = termEnd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    @NonNull
    public Date getTermStart() {
        return termStart;
    }

    public void setTermStart(Date termStart) {
        this.termStart = termStart;
    }

    @NonNull
    public Date getTermEnd() {
        return termEnd;
    }

    public void setTermEnd(Date termEnd) {
        this.termEnd = termEnd;
    }
}


