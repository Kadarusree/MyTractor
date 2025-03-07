package com.rakesh.mytractor.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

import com.rakesh.mytractor.model.Tractor;
import com.rakesh.mytractor.model.WorkLog;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 10;
    private static final String DATABASE_NAME = "TractorApp";

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
    public static final String COLUMN_OWNER = "createdUser";


    public static final String TABLE_WORK_LOGS = "work_logs";
    public static final String WORK_LOG_ID = "id";
    public static final String WORK_LOG_TYPE = "work_type";
    public static final String WORK_LOG_NAME = "work_name";
    public static final String WORK_LOG_PAYMENT_TYPE = "payment_type";
    public static final String WORK_LOG_REMARKS = "remarks";

    private static final String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_SURNAME + " TEXT,"
            + KEY_NAME + " TEXT,"
            + KEY_EMAIL + " TEXT,"
            + KEY_PASSWORD + " TEXT,"
            + KEY_DOB + " TEXT" + ")";

    private static final String TABLE_CREATE_TRACTORS =
            "CREATE TABLE " + TABLE_TRACTORS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_COMPANY + " TEXT, " +
                    COLUMN_MODEL + " TEXT, " +
                    COLUMN_YEAR + " INTEGER, " +
                    COLUMN_TYPE + " TEXT," +
                    COLUMN_OWNER + " TEXT)";

    private static final String TABLE_CREATE_WORK_LOGS =
        "CREATE TABLE " + TABLE_WORK_LOGS + " (" +
        WORK_LOG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        WORK_LOG_TYPE + " TEXT, " +
        WORK_LOG_NAME + " TEXT, " +
        WORK_LOG_PAYMENT_TYPE + " TEXT, " +
        WORK_LOG_REMARKS + " TEXT, " +
        "total_rent REAL, " +
        "trip_expenses REAL, " +
        "work_date TEXT, " +
        "customer_name TEXT, " +
        "tractor TEXT)";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(TABLE_CREATE_TRACTORS);
        db.execSQL(TABLE_CREATE_WORK_LOGS);

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


    public long addTractor(String name, String company, String model, int year, String type, String owner) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_COMPANY, company);
        values.put(COLUMN_MODEL, model);
        values.put(COLUMN_YEAR, year);
        values.put(COLUMN_TYPE, type);
        values.put(COLUMN_OWNER, owner);
        long id = db.insert(TABLE_TRACTORS, null, values);
        db.close();
        return id;
    }


    public List<Tractor> getAllTractors(String userName) {
        List<Tractor> tractorList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TRACTORS, null, COLUMN_OWNER + "=?", new String[]{userName}, null, null, null);

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


    public List<String> getAllTractorNames(String userName) {
        List<String> tractorNames = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TRACTORS, null, COLUMN_OWNER + "=?", new String[]{userName}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                tractorNames.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tractorNames;
    }

    public long addWorkLog(String tractor, String workType, String workName, String rentalType, double totalRent, double tripExpenses, String remarks, String workDate, String customerName) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put("tractor", tractor);
    values.put("work_type", workType);
    values.put("work_name", workName);
    values.put("payment_type", rentalType);
    values.put("total_rent", totalRent);
    values.put("trip_expenses", tripExpenses);
    values.put("remarks", remarks);
    values.put("work_date", workDate);
    values.put("customer_name", customerName);
   long id =  db.insert("work_logs", null, values);
    db.close();
    return id;
}


    public boolean checkUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{KEY_ID}, KEY_EMAIL + "=?", new String[]{email}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }


    public void updateUserPassword(String email, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PASSWORD, newPassword);
        db.update(TABLE_USERS, values, KEY_EMAIL + "=?", new String[]{email});
        db.close();
    }

    public String getUserName(long userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{KEY_NAME}, KEY_ID + "=?", new String[]{String.valueOf(userId)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(KEY_NAME);
            if (columnIndex != -1) {
                String userName = cursor.getString(columnIndex);
                cursor.close();
                return userName;
            }
        }
        cursor.close();
        return null;
    }


    public void deleteTractor(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TRACTORS, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public List<WorkLog> getAllWorkLogs() {
    List<WorkLog> workLogs = new ArrayList<>();
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery("SELECT * FROM work_logs", null);

    if (cursor.moveToFirst()) {
        do {
            WorkLog workLog = new WorkLog();
            workLog.setId(cursor.getInt(0));
            workLog.setWorkType(cursor.getString(1));
            workLog.setWorkName(cursor.getString(2));
            workLog.setRentalType(cursor.getString(3));
            workLog.setRemarks(cursor.getString(4));
            workLog.setTotalRent(cursor.getDouble(5));
            workLog.setTripExpenses(cursor.getDouble(6));
            workLog.setWorkDate(cursor.getString(7));
            workLog.setCustomerName(cursor.getString(8));
            workLog.setTractor(cursor.getString(9));

            workLogs.add(workLog);
        } while (cursor.moveToNext());
    }
    cursor.close();
    db.close();
    return workLogs;
}

public List<WorkLog> getFilteredWorkLogs(String date, String tractor, String customerName, String workType) {
    List<WorkLog> workLogs = new ArrayList<>();
    SQLiteDatabase db = this.getReadableDatabase();
    String query = "SELECT * FROM work_logs WHERE 1=1";

    if (!date.isEmpty()) {
        query += " AND work_date = '" + date + "'";
    }
    if (!tractor.isEmpty()) {
        query += " AND tractor = '" + tractor + "'";
    }
    if (!customerName.isEmpty()) {
        query += " AND customer_name LIKE '%" + customerName + "%'";
    }
    if (!workType.isEmpty()) {
        query += " AND work_type = '" + workType + "'";
    }


    Cursor cursor = db.rawQuery(query, null);

    if (cursor.moveToFirst()) {
        do {
            WorkLog workLog = new WorkLog();
            workLog.setId(cursor.getInt(0));
            workLog.setWorkType(cursor.getString(1));
            workLog.setWorkName(cursor.getString(2));
            workLog.setRentalType(cursor.getString(3));
            workLog.setRemarks(cursor.getString(4));
            workLog.setTotalRent(cursor.getDouble(5));
            workLog.setTripExpenses(cursor.getDouble(6));
            workLog.setWorkDate(cursor.getString(7));
            workLog.setCustomerName(cursor.getString(8));
            workLog.setTractor(cursor.getString(9));
            workLogs.add(workLog);
        } while (cursor.moveToNext());
    }
    cursor.close();
    db.close();
    return workLogs;
}


public int getTractorsCountByUser(String userName) {
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.query(TABLE_TRACTORS, null, COLUMN_OWNER + "=?", new String[]{userName}, null, null, null);
    int count = cursor.getCount();
    cursor.close();
    db.close();
    return count;
  }
}