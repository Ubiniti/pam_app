package com.example.pam_app1.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pam_app1.Entity.Note;
import com.example.pam_app1.Entity.User;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM note WHERE uid = :id LIMIT 1")
    Note findOneById(int id);

    @Query("SELECT * FROM note WHERE user_id = :userId")
    Note[] findAllByUserId(int userId);

    @Query("SELECT * FROM note WHERE user_id = :userId ORDER BY updated_at DESC")
    Note[] findAllByUserIdSortByDate(int userId);

    @Insert()
    void insertNote(Note note);

    @Query("UPDATE note SET title = :title, content = :content WHERE uid = :id")
    void updateNote(int id, String title, String content);

    @Query("DELETE FROM note WHERE uid = :id")
    void deleteNote(int id);
}
