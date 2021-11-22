package com.databaseexample.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.databaseexample.Model.DataModel;
import java.util.ArrayList;

public class MyDataBase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="MyDataBase.db";
    public static final int DATABASE_VERSION=1;

    //Create Table Info Variable list
    public static final String tbl_info_nm="Info";
    public static final String col_info_id="Id";
    public static final String col_info_name="Name";
    public MyDataBase(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void createTable(SQLiteDatabase db){
        db.execSQL("CREATE TABLE "+ tbl_info_nm + "("
                        + col_info_id +        " INTEGER PRIMARY KEY ,"
                        + col_info_name +      " TEXT"+ ")"
                );
    }
    public void insert_InfoTable_Data(DataModel model){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(col_info_name,model.getName());
        db.insert(tbl_info_nm,null,contentValues);
    }
    public ArrayList<DataModel> get_All_InfoTable_Data(){
        ArrayList<DataModel> model=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from "+ tbl_info_nm +"",null);

        if (cursor.moveToFirst()){
            do {
                DataModel dataModel=new DataModel();
                dataModel.setName(cursor.getString(cursor.getColumnIndex(col_info_name)));
                model.add(dataModel);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return model;
    }
    public void deleteDataPosition(String position){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(tbl_info_nm,col_info_name + "=\""+position+"\"",null);
    }
}
