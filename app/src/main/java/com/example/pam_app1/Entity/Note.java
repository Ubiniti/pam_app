package com.example.pam_app1.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Note {
    public static final String DATE_FORMAT = "yyyy-dd-MM HH:mm";

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "updated_at")
    public String updatedAt;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "content")
    public String content;

    @ColumnInfo(name = "location")
    public String location;

    @ColumnInfo(name = "user_id")
    public int userId;
}
