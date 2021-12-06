package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


import Users.Member;

public class MemberDAO extends SQLiteOpenHelper {


    private static final String TAG = "SQLite";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Gym";

    // Table name: Member.
    private static final String TABLE_MEMBER = "Member";

    private static final String COLUMN_MEMBER_ID ="Member_Id";
    private static final String COLUMN_MEMBER_USERNAME ="Member_Username";
    private static final String COLUMN_MEMBER_PASSWORD = "Member_Password";
    private static final String COLUMN_MEMBER_GYMCLASS = "Member_Class";


    public MemberDAO(Context context)  {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }




    // Create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Script.
        String script = "CREATE TABLE " + TABLE_MEMBER + "("
                + COLUMN_MEMBER_ID + " INTEGER PRIMARY KEY," + COLUMN_MEMBER_USERNAME + " TEXT,"
                + COLUMN_MEMBER_PASSWORD + " TEXT," +COLUMN_MEMBER_GYMCLASS + "TEXT" + ")";
        // Execute Script.
        db.execSQL(script);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMBER);

        // Create tables again
        onCreate(db);
    }


    // If Note table has no data
    // default, Insert 2 records.
    public void createDefaultInstructorsIfNeed()  {
        int count = this.getMemberCount();
        if(count ==0 ) {
            Member member1 = new Member(1,
                    "member", "member123", "Karate");

            this.addMember(member1);

        }
    }


    public void addMember(Member member) {


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_MEMBER_USERNAME, member.getUsername());
        values.put(COLUMN_MEMBER_PASSWORD, member.getPassword());
        values.put(COLUMN_MEMBER_GYMCLASS, member.getGymClass());

        // Inserting Row
        db.insert(TABLE_MEMBER, null, values);

        // Closing database connection
        db.close();
    }


    public Member getMember(int id) {


        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_MEMBER, new String[] { COLUMN_MEMBER_ID,
                        COLUMN_MEMBER_USERNAME, COLUMN_MEMBER_PASSWORD, COLUMN_MEMBER_GYMCLASS }, COLUMN_MEMBER_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Member member = new Member(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));
        // return instructor
        return member;
    }


    public List<Member> getAllMembers() {


        List<Member> memberList = new ArrayList<Member>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MEMBER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Member member = new Member();
                member.setId(Integer.parseInt(cursor.getString(0)));
                member.setUsername(cursor.getString(1));
                member.setPassword(cursor.getString(2));
                member.setGymClass(cursor.getString(3));
                // Adding member to list
                memberList.add(member);
            } while (cursor.moveToNext());
        }

        // return member list
        return memberList;
    }

    public int getMemberCount() {


        String countQuery = "SELECT  * FROM " + TABLE_MEMBER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }


    public int updateMember(Member member) {


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_MEMBER_USERNAME, member.getUsername());
        values.put(COLUMN_MEMBER_PASSWORD, member.getPassword());
        values.put(COLUMN_MEMBER_GYMCLASS, member.getGymClass());


        // updating row
        return db.update(TABLE_MEMBER, values, COLUMN_MEMBER_ID + " = ?",
                new String[]{String.valueOf(member.getId())});
    }

    public void deleteMember(Member member) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MEMBER, COLUMN_MEMBER_ID + " = ?",
                new String[] { String.valueOf(member.getId()) });
        db.close();
    }
}
