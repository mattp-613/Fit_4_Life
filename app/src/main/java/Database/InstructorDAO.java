package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fit_4_life.User;

import java.util.ArrayList;
import java.util.List;

import Users.Instructor;

public class InstructorDAO extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Gym";

    // Table name: Instructor.
    private static final String TABLE_INSTRUCTOR = "Instructor";

    private static final String COLUMN_INSTRUCTOR_ID ="Instructor_Id";
    private static final String COLUMN_INSTRUCTOR_USERNAME ="Instructor_Username";
    private static final String COLUMN_INSTRUCTOR_PASSWORD = "Instructor_Password";
    private static final String COLUMN_INSTRUCTOR_GYMCLASS = "Instructor_Class";


    public InstructorDAO(Context context)  {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }




    // Create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Script.
        String script = "CREATE TABLE " + TABLE_INSTRUCTOR + "("
                + COLUMN_INSTRUCTOR_ID + " INTEGER PRIMARY KEY," + COLUMN_INSTRUCTOR_USERNAME + " TEXT,"
                + COLUMN_INSTRUCTOR_PASSWORD + " TEXT," +COLUMN_INSTRUCTOR_GYMCLASS + "TEXT" + ")";
        // Execute Script.
        db.execSQL(script);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INSTRUCTOR);

        // Create tables again
        onCreate(db);
    }


    // If Note table has no data
    // default, Insert 2 records.
    public void createDefaultInstructorsIfNeed()  {
        int count = this.getInstructorCount();
        if(count ==0 ) {
            Instructor instructor1 = new Instructor(1,
                    "instructor", "instructor123", "Karate");

            this.addInstructor(instructor1);

        }
    }


    public void addInstructor(Instructor instructor) {


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_INSTRUCTOR_USERNAME, instructor.getUsername());
        values.put(COLUMN_INSTRUCTOR_PASSWORD, instructor.getPassword());
        values.put(COLUMN_INSTRUCTOR_GYMCLASS, instructor.getGymClass());

        // Inserting Row
        db.insert(TABLE_INSTRUCTOR, null, values);

        // Closing database connection
        db.close();
    }


    public Instructor getInstructor(int id) {


        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_INSTRUCTOR, new String[] { COLUMN_INSTRUCTOR_ID,
                        COLUMN_INSTRUCTOR_USERNAME, COLUMN_INSTRUCTOR_PASSWORD, COLUMN_INSTRUCTOR_GYMCLASS }, COLUMN_INSTRUCTOR_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Instructor instructor = new Instructor(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));
        // return instructor
        return instructor;
    }


    public List<Instructor> getAllInstructors() {


        List<Instructor> instructorList = new ArrayList<Instructor>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_INSTRUCTOR;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Instructor instructor = new Instructor();
                instructor.setId(Integer.parseInt(cursor.getString(0)));
                instructor.setUsername(cursor.getString(1));
                instructor.setPassword(cursor.getString(2));
                instructor.setGymClass(cursor.getString(3));
                // Adding user to list
                instructorList.add(instructor);
            } while (cursor.moveToNext());
        }

        // return instructor list
        return instructorList;
    }

    public int getInstructorCount() {


        String countQuery = "SELECT  * FROM " + TABLE_INSTRUCTOR;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }


    public int updateInstructor(Instructor instructor) {


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_INSTRUCTOR_USERNAME, instructor.getUsername());
        values.put(COLUMN_INSTRUCTOR_PASSWORD, instructor.getPassword());
        values.put(COLUMN_INSTRUCTOR_GYMCLASS, instructor.getGymClass());


        // updating row
        return db.update(TABLE_INSTRUCTOR, values, COLUMN_INSTRUCTOR_ID + " = ?",
                new String[]{String.valueOf(instructor.getId())});
    }

    public void deleteInstructor(Instructor instructor) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INSTRUCTOR, COLUMN_INSTRUCTOR_ID + " = ?",
                new String[] { String.valueOf(instructor.getId()) });
        db.close();
    }

}
