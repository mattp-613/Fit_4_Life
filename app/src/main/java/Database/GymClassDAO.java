package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

import com.example.fit_4_life.GymClass;





public class GymClassDAO extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";

    // Database Version
    private static final int DATABASE_VERSION = 1;


    // Database Name
    private static final String DATABASE_NAME = "Gym";

    // Table name: GymClass.
    private static final String TABLE_GYMCLASS = "GymClass";


    private static final String COLUMN_CLASS_ID = "Class_Id";
    private static final String COLUMN_CLASS_NAME = "Class_Name";
    private static final String COLUMN_CLASS_DESCRIPTION = "Class_Description";
    private static final String COLUMN_CLASS_DATE = "Class_date";
    private static final String COLUMN_CLASS_STARTTIME = "Class_starttime";
    private static final String COLUMN_CLASS_ENDTIME = "Class_endtime";
    private static final String COLUMN_CLASS_INSTRUCTOR = "Class_Instructor";
    private static final String COLUMN_CLASS_CAPACITY = "Class_Capacity";

    public GymClassDAO(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }




    // Create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Script.
        String script = "CREATE TABLE " + TABLE_GYMCLASS + "("
                + COLUMN_CLASS_ID + " INTEGER PRIMARY KEY, " + COLUMN_CLASS_NAME + " TEXT, " + COLUMN_CLASS_DESCRIPTION + " TEXT, "
                + COLUMN_CLASS_DATE + " TEXT, " + COLUMN_CLASS_STARTTIME + "TEXT, " + COLUMN_CLASS_ENDTIME + " TEXT, " + COLUMN_CLASS_INSTRUCTOR + " INTEGER, " +COLUMN_CLASS_CAPACITY + " INTEGER " + ")";
        // Execute Script.
        db.execSQL(script);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GYMCLASS);

        // Create tables again
        onCreate(db);
    }


    // If Note table has no data
    // default, Insert 2 records.
    public void createDefaultClassesIfNeed()  {
        int count = this.getClassCount();
        if(count ==0 ) {
            GymClass class1 = new GymClass(1,
                    "Karate", "“A high-energy workout that combines\n" +
                    "martial arts with cardio and strength training","Monday", "7:00pm", "8:00pm", 35, 1);
            GymClass class2 = new GymClass(2,
                    "Yoga", "A variety of practices which may include postures (asanas), breathing exercises (pranayama), meditation, mantras, lifestyle changes (e.g., diet, sleep, hygiene), spiritual beliefs, and/or rituals.", "Tuesday", "5:00pm", "6:00pm", 50, 2);
            GymClass class3 = new GymClass(3,
                    "Zumba", "fitness program that combines Latin and international music with dance moves. Zumba routines incorporate interval training — alternating fast and slow rhythms — to help improve cardiovascular fitness.", "Wednesday", "6:00", "7:00pm", 40, 3);
            this.addGymClass(class1);
            this.addGymClass(class2);
            this.addGymClass(class3);
        }
    }


    public void addGymClass(GymClass gymClass) {


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CLASS_NAME, gymClass.getName());
        values.put(COLUMN_CLASS_DESCRIPTION, gymClass.getDescription());
        values.put(COLUMN_CLASS_DATE, gymClass.getDate());
        values.put(COLUMN_CLASS_STARTTIME, gymClass.getStarttime());
        values.put(COLUMN_CLASS_ENDTIME, gymClass.getEndtime());
        values.put(COLUMN_CLASS_INSTRUCTOR, gymClass.getInstructor());
        values.put(COLUMN_CLASS_CAPACITY, gymClass.getCapacity());


        // Inserting Row
        db.insert(TABLE_GYMCLASS, null, values);

        // Closing database connection
        db.close();
    }


    public GymClass getClass(int id) {


        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_GYMCLASS, new String[] { COLUMN_CLASS_ID,
                        COLUMN_CLASS_NAME,COLUMN_CLASS_DESCRIPTION, COLUMN_CLASS_DATE, COLUMN_CLASS_STARTTIME, COLUMN_CLASS_ENDTIME,COLUMN_CLASS_CAPACITY, COLUMN_CLASS_INSTRUCTOR }, COLUMN_CLASS_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        GymClass gymClass = new GymClass(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), Integer.parseInt(cursor.getString(6)), Integer.parseInt(cursor.getString(7)));
        // return note
        return gymClass;
    }


    public List<GymClass> getAllClasses() {


        List<GymClass> classList = new ArrayList<GymClass>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_GYMCLASS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                GymClass gymClass = new GymClass();
                gymClass.setId(Integer.parseInt(cursor.getString(0)));
                gymClass.setName(cursor.getString(1));
                gymClass.setDescription(cursor.getString(2));
                gymClass.setDate(cursor.getString(3));
                gymClass.setStarttime(cursor.getString(4));
                gymClass.setEndtime(cursor.getString(5));
                gymClass.setCapacity(Integer.parseInt(cursor.getString(6)));
                gymClass.setInstructor(Integer.parseInt(cursor.getString(7)));
                // Adding user to list
                classList.add(gymClass);
            } while (cursor.moveToNext());
        }

        // return note list
        return classList;
    }

    public int getClassCount() {


        String countQuery = "SELECT  * FROM " + TABLE_GYMCLASS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }


    public int updateClass(GymClass gymClass) {


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CLASS_NAME, gymClass.getName());
        values.put(COLUMN_CLASS_DESCRIPTION, gymClass.getDescription());
        values.put(COLUMN_CLASS_DATE, gymClass.getDate());
        values.put(COLUMN_CLASS_STARTTIME, gymClass.getStarttime());
        values.put(COLUMN_CLASS_ENDTIME, gymClass.getEndtime());
        values.put(COLUMN_CLASS_INSTRUCTOR, gymClass.getInstructor());
        values.put(COLUMN_CLASS_CAPACITY, gymClass.getCapacity());

        // updating row
        return db.update(TABLE_GYMCLASS, values, COLUMN_CLASS_ID + " = ?",
                new String[]{String.valueOf(gymClass.getId())});
    }

    public void deleteClass(GymClass gymClass) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GYMCLASS, COLUMN_CLASS_ID + " = ?",
                new String[] { String.valueOf(gymClass.getId()) });
        db.close();
    }
}
