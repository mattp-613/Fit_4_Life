package com.example.fit_4_life;

import org.junit.Test;

import static org.junit.Assert.*;

import Database.MemberDAO;
import Database.UserDAO;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void testAdminUserName() {
        UserDAO db = new UserDAO(MainActivity.this);
        db.createDefaultUsersIfNeed();
        User user = db.getUserByUsername("admin");
        assertEquals(user.getPassword(), "admin123");



    }
    public void testGetUserByUsername() {
        UserDAO db = new UserDAO(MainActivity.this);
        db.createDefaultUsersIfNeed();
        User user = db.getUserByUsername("member");
        assertEquals(user.getPassword(), "member123");
    }

}
