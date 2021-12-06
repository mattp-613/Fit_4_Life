package Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;

import com.example.fit_4_life.User;

import Users.Admin;

public class AdminDAO extends SQLiteOpenHelper{

    private static final String TAG = "SQLite";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Gym";

    // Table name: Admin.
    private static final String TABLE_ADMIN = "Admin";

    private static final String COLUMN_ADMIN_ID ="Admin_Id";
    private static final String COLUMN_ADMIN_USERNAME ="Admin_Username";
    private static final String COLUMN_ADMIN_PASSWORD = "Admin_Password";


    public AdminDAO(Context context)  {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }




    // Create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Script.
        String script = "CREATE TABLE " + TABLE_ADMIN + "("
                + COLUMN_ADMIN_ID + " INTEGER PRIMARY KEY," + COLUMN_ADMIN_USERNAME + " TEXT,"
                + COLUMN_ADMIN_PASSWORD + "TEXT" + ")";
        // Execute Script.
        db.execSQL(script);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMIN);

        // Create tables again
        onCreate(db);
    }


    // If Note table has no data
    // default, Insert 2 records.
    public void createDefaultAdminIfNeed()  {
        int count = this.getAdminCount();
        if(count ==0 ) {
            Admin admin1 = new Admin(1,
                    "admin", "admin123");

            this.addAdmin(admin1);
        }
    }


    public void addAdmin(Admin admin) {


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ADMIN_USERNAME, admin.getUsername());
        values.put(COLUMN_ADMIN_PASSWORD, admin.getPassword());

        // Inserting Row
        db.insert(TABLE_ADMIN, null, values);

        // Closing database connection
        db.close();
    }


    public User getAdmin(int id) {


        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ADMIN, new String[] { COLUMN_ADMIN_ID,
                        COLUMN_ADMIN_USERNAME, COLUMN_ADMIN_PASSWORD }, COLUMN_ADMIN_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Admin admin = new Admin(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return admin
        return admin;
    }


    public List<Admin> getAllAdmins() {


        List<Admin> adminList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ADMIN;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Admin admin = new Admin();
                admin.setId(Integer.parseInt(cursor.getString(0)));
                admin.setUsername(cursor.getString(1));
                admin.setPassword(cursor.getString(2));
                // Adding admin to list
                adminList.add(admin);
            } while (cursor.moveToNext());
        }

        // return admin list
        return adminList;
    }

    public int getAdminCount() {


        String countQuery = "SELECT  * FROM " + TABLE_ADMIN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }


    public int updateAdmin(Admin admin) {


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ADMIN_USERNAME, admin.getUsername());
        values.put(COLUMN_ADMIN_PASSWORD, admin.getPassword());


        // updating row
        return db.update(TABLE_ADMIN, values, COLUMN_ADMIN_ID + " = ?",
                new String[]{String.valueOf(admin.getId())});
    }

    public void deleteAdmin(Admin admin) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ADMIN, COLUMN_ADMIN_ID + " = ?",
                new String[] { String.valueOf(admin.getId()) });
        db.close();
    }

}
