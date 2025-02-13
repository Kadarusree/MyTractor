package com.rakesh.mytractor.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

import com.rakesh.mytractor.model.Tractor;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "myTractor";

    private static final String TABLE_USERS = "users";
    private static final String KEY_ID = "id";
    private static final String KEY_SURNAME = "surname";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_DOB = "dob";

    public static final String TABLE_TRACTORS = "tractors";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_COMPANY = "company";
    public static final String COLUMN_MODEL = "model";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_TYPE = "type";


    public static final String TABLE_WORK_LOGS = "work_logs";
    public static final String WORK_LOG_ID = "id";
    public static final String WORK_LOG_TRACTOR_ID = "tractor_id";
    public static final String WORK_LOG_TYPE = "work_type";
    public static final String WORK_LOG_NAME = "work_name";
    public static final String WORK_LOG_PAYMENT_TYPE = "payment_type";
    public static final String WORK_LOG_EXPENSES = "work_expenses";
    public static final String WORK_LOG_REMARKS = "remarks";

    private static final String TABLE_CREATE_TRACTORS =
            "CREATE TABLE " + TABLE_TRACTORS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_COMPANY + " TEXT, " +
                    COLUMN_MODEL + " TEXT, " +
                    COLUMN_YEAR + " INTEGER, " +
                    COLUMN_TYPE + " TEXT);";

    private static final String TABLE_CREATE_WORK_LOGS =
            "CREATE TABLE " + TABLE_WORK_LOGS + " (" +
                    WORK_LOG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    WORK_LOG_TRACTOR_ID + " INTEGER, " +
                    WORK_LOG_TYPE + " TEXT, " +
                    WORK_LOG_NAME + " TEXT, " +
                    WORK_LOG_PAYMENT_TYPE + " TEXT, " +
                    WORK_LOG_EXPENSES + " REAL, " +
                    WORK_LOG_REMARKS + " TEXT, " +
                    "FOREIGN KEY(" + WORK_LOG_TRACTOR_ID + ") REFERENCES " + TABLE_TRACTORS + "(" + COLUMN_ID + "));";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_SURNAME + " TEXT,"
                + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_PASSWORD + " TEXT,"
                + KEY_DOB + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(TABLE_CREATE_TRACTORS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRACTORS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORK_LOGS);

        onCreate(db);
    }

    public long addUser(String surname, String name, String email, String password, String dob) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SURNAME, surname);
        values.put(KEY_NAME, name);
        values.put(KEY_EMAIL, email);
        values.put(KEY_PASSWORD, password);
        values.put(KEY_DOB, dob);
        long id = db.insert(TABLE_USERS, null, values);
        db.close();
        return id;
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{KEY_ID},
                KEY_EMAIL + "=? AND " + KEY_PASSWORD + "=?",
                new String[]{email, password}, null, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount > 0;
    }

    public long getUserIdByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{KEY_ID},
                KEY_EMAIL + "=?", new String[]{email}, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(KEY_ID);
            if (columnIndex != -1) {
                long userId = cursor.getLong(columnIndex);
                cursor.close();
                return userId;
            }
        }
        cursor.close();
        return -1;
    }


    public long addTractor(String name, String company, String model, int year, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_COMPANY, company);
        values.put(COLUMN_MODEL, model);
        values.put(COLUMN_YEAR, year);
        values.put(COLUMN_TYPE, type);
        long id = db.insert(TABLE_TRACTORS, null, values);
        db.close();
        return id;
    }


    public List<Tractor> getAllTractors() {
        List<Tractor> tractorList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TRACTORS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Tractor tractor = new Tractor();
                tractor.setId(cursor.getInt(0));
                tractor.setName(cursor.getString(1));
                tractor.setCompany(cursor.getString(2));
                tractor.setModel(cursor.getString(3));
                tractor.setYear(cursor.getInt(4));
                tractor.setType(cursor.getString(5));
                tractorList.add(tractor);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tractorList;
    }


    public List<String> getAllTractorNames (){
        List<String> tractorNames = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TRACTORS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                tractorNames.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tractorNames;
    }

    public long addWorkLog(int tractorId, String workType, String workName, String paymentType, double workExpenses, String remarks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(WORK_LOG_TRACTOR_ID, tractorId);
        values.put(WORK_LOG_TYPE, workType);
        values.put(WORK_LOG_NAME, workName);
        values.put(WORK_LOG_PAYMENT_TYPE, paymentType);
        values.put(WORK_LOG_EXPENSES, workExpenses);
        values.put(WORK_LOG_REMARKS, remarks);
        long id = db.insert(TABLE_WORK_LOGS, null, values);
        db.close();
        return id;
    }
}