package com.example.investingmobileapp.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.investingmobileapp.models.PortfolioBondModel;
import com.example.investingmobileapp.models.PortfolioModel;
import com.example.investingmobileapp.models.PortfolioStockModel;
import com.example.investingmobileapp.models.UserModel;

import java.util.ArrayList;
import java.util.List;

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

    public boolean addPortfolio(PortfolioModel portfolioModel){
        long insert;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PORTFOLIO_GOAL, portfolioModel.getGoal());
        cv.put(PORTFOLIO_YEARS, portfolioModel.getYears());
        cv.put(CLIENT_ID, portfolioModel.getId());
        cv.put(ANALYST_ID, portfolioModel.getAnalystId());
        insert = db.insert(PORTFOLIO_TABLE, null, cv);
        if (insert == -1) {
            return false;
        }
        return true;
    }

    public boolean addStockToPortfolio(PortfolioStockModel portfolioStockModel){
        long insert;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        int stock_id = portfolioStockModel.getStock_id();
        int portfolio_id = portfolioStockModel.getPortfolio_id();
        int count = portfolioStockModel.getCount();
        cv.put(STOCK_ID, stock_id);
        cv.put(PORTFOLIO_ID, portfolio_id);
        cv.put(PORTFOLIO_STOCK_COUNT, count);
        insert = db.insert(PORTFOLIO_TABLE, null, cv);
        if (insert == -1) {
            return false;
        }
        return true;
    }

    public boolean addBondToPortfolio(PortfolioBondModel portfolioBondModel){
        long insert;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        int bond_id = portfolioBondModel.getBond_id();
        int portfolio_id = portfolioBondModel.getPortfolio_id();
        int count = portfolioBondModel.getCount();
        cv.put(STOCK_ID, bond_id);
        cv.put(PORTFOLIO_ID, portfolio_id);
        cv.put(PORTFOLIO_STOCK_COUNT, count);
        insert = db.insert(PORTFOLIO_TABLE, null, cv);
        if (insert == -1) {
            return false;
        }
        return true;
    }

    public List<PortfolioModel> getPortfolios(){
        List<PortfolioModel> list = new ArrayList<>();
        String queryString = "SELECT * FROM " + PORTFOLIO_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                int years = cursor.getInt(1);
                String goal = cursor.getString(2);
                int analyst_id = cursor.getInt(3);
                int client_id = cursor.getInt(4);
                PortfolioModel model = new PortfolioModel(id, goal, years, client_id);
                list.add(model);
            } while (cursor.moveToNext());
        } else {

        }
        cursor.close();
        db.close();
        return list;
    }

    public List<UserModel> getClients(){
        List<UserModel> list = new ArrayList<>();
        String queryString = "SELECT * FROM " + CLIENT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String firstName = cursor.getString(1);
                String lastName = cursor.getString(2);
                String email = cursor.getString(3);
                String pass = cursor.getString(4);
                UserModel model = new UserModel(id, firstName, lastName, email, pass);
                list.add(model);
            } while (cursor.moveToNext());
        } else {

        }
        cursor.close();
        db.close();
        return list;
    }

    public List<UserModel> getAnalysts(){
        List<UserModel> list = new ArrayList<>();
        String queryString = "SELECT * FROM " + ANALYST_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String firstName = cursor.getString(1);
                String lastName = cursor.getString(2);
                String email = cursor.getString(3);
                String pass = cursor.getString(4);
                String code = cursor.getString(5);
                UserModel model = new UserModel(id, firstName, lastName, email, pass);
                model.setCode(code);
                list.add(model);
            } while (cursor.moveToNext());
        } else {

        }
        cursor.close();
        db.close();
        return list;
    }

    public List<UserModel> getUsers(){
        List<UserModel> allUsers = new ArrayList<>();
        allUsers.addAll(getClients());
        allUsers.addAll(getAnalysts());
        return allUsers;
    }

    public List<PortfolioStockModel> getStockPortfolios(){
        List<PortfolioStockModel> list = new ArrayList<>();
        String queryString = "SELECT * FROM " + TABLE_PORTFOLIO_STOCK;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                int stock_id = cursor.getInt(1);
                int portfolio_id = cursor.getInt(2);
                int count = cursor.getInt(3);
                PortfolioStockModel model = new PortfolioStockModel(portfolio_id, stock_id, count);
                model.setId(id);
                list.add(model);
            } while (cursor.moveToNext());
        } else {

        }
        cursor.close();
        db.close();
        return list;
    }

    public List<PortfolioBondModel> getBondPortfolios(){
        List<PortfolioBondModel> list = new ArrayList<>();
        String queryString = "SELECT * FROM " + TABLE_PORTFOLIO_BOND;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                int bond_id = cursor.getInt(1);
                int portfolio_id = cursor.getInt(2);
                int count = cursor.getInt(3);
                PortfolioBondModel model = new PortfolioBondModel(portfolio_id, bond_id, count);
                model.setId(id);
                list.add(model);
            } while (cursor.moveToNext());
        } else {

        }
        cursor.close();
        db.close();
        return list;
    }
}
