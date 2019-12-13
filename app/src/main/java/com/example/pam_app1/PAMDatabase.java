package com.example.pam_app1;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.pam_app1.Dao.NoteDao;
import com.example.pam_app1.Dao.UserDao;
import com.example.pam_app1.Entity.Note;
import com.example.pam_app1.Entity.User;

@Database(entities = {User.class, Note.class}, version = 4)
public abstract class PAMDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract NoteDao notesDao();

    private static PAMDatabase pamDB;

    public static PAMDatabase getInstance(Context context) {
        if (PAMDatabase.pamDB == null) {
            PAMDatabase.pamDB = PAMDatabase.buildDatabaseInstance(context);
        }

        return PAMDatabase.pamDB;
    }

    private static PAMDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context, PAMDatabase.class, Constants.DB_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }
}
