package com.example.pam_app1.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pam_app1.Entity.User;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user WHERE username = :username LIMIT 1")
    User findOneByUsername(String username);

    @Query("SELECT * FROM user WHERE username = :username AND password = :password LIMIT 1")
    User findOneWithCredentials(String username, String password);

    @Insert()
    void registerUser(User user);
}
