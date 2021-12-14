package com.example.investingmobileapp.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.investingmobileapp.models.UserModel;

import javax.xml.transform.sax.SAXResult;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String ANALYST_TABLE = "analyst";
    public static final String PORTFOLIO_STOCK_ID = "id";
    public static final String PORTFOLIO_BOND_ID = "id";
    public static final String PORTFOLIO_ID = PORTFOLIO_STOCK_ID;
    public static final String STOCK_ID = PORTFOLIO_ID;
    public static final String CLIENT_ID = STOCK_ID;
    public static final String ANALYST_ID = CLIENT_ID;
    public static final String BOND_ID = PORTFOLIO_ID;
    public static final String CLIENT_FIRST_NAME = "firstName";
    public static final String ANALYST_FIRST_NAME = CLIENT_FIRST_NAME;
    public static final String CLIENT_LAST_NAME = "lastName";
    public static final String ANALYST_LAST_NAME = CLIENT_LAST_NAME;
    public static final String CLIENT_EMAIL = "email";
    public static final String ANALYST_EMAIL = CLIENT_EMAIL;
    public static final String CLIENT_PASS = "pass";
    public static final String ANALYST_PASS = CLIENT_PASS;
    public static final String ANALYST_CODE = "code";
    public static final String CLIENT_TABLE = "client";
    public static final String STOCK_TABLE = "stock";
    public static final String STOCK_NAME = "stockName";
    public static final String STOCK_DESC = "stockDesc";
    public static final String BOND_TABLE = "bond";
    public static final String BOND_NAME = "bondName";
    public static final String BOND_DESC = "bondDesc";
    public static final String PORTFOLIO_TABLE = "portfolio";
    public static final String PORTFOLIO_YEARS = "years";
    public static final String PORTFOLIO_GOAL = "goal";
    public static final String PORTFOLIO_ANALYST_ID = "analystId";
    public static final String PORTFOLIO_CLIENT_ID = "clientId";
    public static final String TABLE_PORTFOLIO_STOCK = "portfolio_stock";
    public static final String TABLE_PORTFOLIO_BOND = "portfolio_bond";
    public static final String PORTFOLIO_STOCK_COUNT = "count";
    public static final String PORTFOLIO_BOND_COUNT = "count";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "investment.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createAnalystStatement = "create TABLE " + ANALYST_TABLE + "(" + ANALYST_ID + " SERIAL PRIMARY KEY, " + ANALYST_FIRST_NAME + " VARCHAR(255) NOT NULL, " + ANALYST_LAST_NAME + " VARCHAR(255) NOT NULL, " + ANALYST_EMAIL + " VARCHAR(255) NOT NULL, " + ANALYST_PASS + " VARCHAR(255) NOT NULL, " + ANALYST_CODE + " INTEGER)";
        String createClientStatement = "create TABLE " + CLIENT_TABLE + "(" + CLIENT_ID + " SERIAL PRIMARY KEY, " + CLIENT_FIRST_NAME + " VARCHAR(255) NOT NULL, " + CLIENT_LAST_NAME + " VARCHAR(255) NOT NULL, " + CLIENT_EMAIL + " VARCHAR(255) NOT NULL, " + CLIENT_PASS + " VARCHAR(255) NOT NULL);";
        String createStockStatement = "create TABLE " + STOCK_TABLE + "(" + STOCK_ID + " SERIAL PRIMARY KEY, " + STOCK_NAME + " VARCHAR(255) NOT NULL," + STOCK_DESC + " VARCHAR(255) NOT NULL);";
        String createBondStatement = "create TABLE " + BOND_TABLE + "(" + BOND_ID + " SERIAL PRIMARY KEY, " + BOND_NAME + " VARCHAR(255) NOT NULL," + BOND_DESC + " VARCHAR(255) NOT NULL);";
        String createPortfolioStatement = "create TABLE " + PORTFOLIO_TABLE + "(" + PORTFOLIO_ID + " SERIAL PRIMARY KEY, " + PORTFOLIO_YEARS + " INTEGER NOT NULL, " + PORTFOLIO_GOAL + " VARCHAR(255) NOT NULL, " + PORTFOLIO_ANALYST_ID + " INTEGER, " + PORTFOLIO_CLIENT_ID + " INTEGER, FOREIGN KEY ("+ PORTFOLIO_ANALYST_ID + ") REFERENCES " + ANALYST_TABLE + "(" + ANALYST_ID + "), FOREIGN KEY (" + PORTFOLIO_CLIENT_ID + ") REFERENCES " + CLIENT_TABLE + "("+CLIENT_ID + "));";
        String createPortfolioStockStatement = "create TABLE " + TABLE_PORTFOLIO_STOCK + "(" + PORTFOLIO_STOCK_ID + " SERIAL PRIMARY KEY, " + STOCK_ID +" int NOT NULL, " + PORTFOLIO_ID + " int NOT NULL, " + PORTFOLIO_STOCK_COUNT + " INTEGER, FOREIGN KEY ( " + PORTFOLIO_ID + ") REFERENCES " + PORTFOLIO_TABLE + "(" + PORTFOLIO_ID +"), FOREIGN KEY (" + STOCK_ID + ") REFERENCES " + STOCK_TABLE + "("+STOCK_ID+"));";
        String createPortfolioBondStatement = "create TABLE " + TABLE_PORTFOLIO_BOND + "(" + PORTFOLIO_BOND_ID + " SERIAL PRIMARY KEY, " + BOND_ID +" int NOT NULL, " + PORTFOLIO_ID + " int NOT NULL, " + PORTFOLIO_BOND_COUNT + " INTEGER, FOREIGN KEY ( " + PORTFOLIO_ID + ") REFERENCES " + PORTFOLIO_TABLE + "(" + PORTFOLIO_ID +"), FOREIGN KEY (" + BOND_ID + ") REFERENCES " + BOND_TABLE + "("+BOND_ID+"));";
        db.execSQL(createAnalystStatement);
        db.execSQL(createClientStatement);
        db.execSQL(createStockStatement);
        db.execSQL(createBondStatement);
        db.execSQL(createPortfolioStatement);
        db.execSQL(createPortfolioStockStatement);
        db.execSQL(createPortfolioBondStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addUser(UserModel userModel){
        long insert;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CLIENT_FIRST_NAME, userModel.getFirstName());
        cv.put(CLIENT_LAST_NAME, userModel.getLastname());
        cv.put(CLIENT_EMAIL, userModel.getEmail());
        cv.put(CLIENT_PASS, userModel.getPassword());
        if (userModel.getCode() != null) {
            cv.put(ANALYST_CODE, userModel.getCode());
            insert = db.insert(ANALYST_TABLE, null, cv);
        } else {
            insert = db.insert(CLIENT_TABLE, null, cv);
        }
        if (insert == -1) {
            return false;
        }
        return true;
    }
}
