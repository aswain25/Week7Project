package com.example.admin.server;

/**
 * Created by Admin on 3/28/2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 3/28/2018.
 */

public class LocalDataSource<T> extends SQLiteOpenHelper
{
    public static final String GETALL = "SELECT * FROM ";

    public String NAME;
    public String TABLE;
    public String primaryKeyName;
    public String primaryKeyType;
    public String[] columnNames;
    public String[] columnTypes;
    public int version;
    public String GENDER = "gender";

    private String GetCreateTableCommand()
    {
        String result = "CREATE TABLE " + TABLE + "(";
        result += primaryKeyName + " " + primaryKeyType + " PRIMARY KEY, ";
        for(int i = 0; i < columnNames.length; i++)
        {
            result += columnNames[i] + " " + columnTypes[i];
            if (i != columnNames.length - 1)
                result += ", ";
        }
        result += ");";
        return result;
        //return "CREATE TABLE " + TABLE + "(" + FIRST_NAME + " TEXT, " + LAST_NAME + " TEXT, " + GENDER + " TEXT)";
    }

    public LocalDataSource(Context context, DataContract contract)
    {
        super(context, contract.name, null, contract.version);
        this.NAME = contract.name;
        this.TABLE = contract.name;
        this.primaryKeyName = contract.primaryKeyName;
        this.primaryKeyType = contract.primaryKeyType;
        this.columnNames = contract.columnNames;
        this.columnTypes = contract.columnTypes;
        this.version = contract.version;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(GetCreateTableCommand());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }

    public long savePerson(T person) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        SQLiteDatabase database = getWritableDatabase();

        //create the content value for saving the object
        ContentValues contentValues = new ContentValues();
        //contentValues.put(primaryKeyName, 0);
        try
        {
            for (int i = 0; i < columnNames.length; i++)
                contentValues.put(columnNames[i], (String)person.getClass().getMethod("get" + columnNames[i], null).invoke(person));
        }
        catch (Exception e){throw e;}


        //instert the object in the table
        long rowNumber = database.insert(TABLE, null, contentValues);

        database.close();
        return rowNumber;
    }

    public long updatePerson(T newValue) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        SQLiteDatabase database = getWritableDatabase();

        //create the content value for saving the object
        ContentValues contentValues = new ContentValues();
        try
        {
            for (int i = 0; i < columnNames.length; i++)
            {
                contentValues.put(columnNames[i], (String)newValue.getClass().getMethod("get" + columnNames[i], null).invoke(newValue));
            }
        }
        catch (Exception e){throw e;}


        //instert the object in the table
        int id = (int)newValue.getClass().getMethod("get" + primaryKeyName, null).invoke(newValue);
        long rowNumber = database.update(TABLE, contentValues, primaryKeyName + " = " + String.valueOf(id), null);
        //long rowNumber = database.update(TABLE, contentValues, "FirstName = '" + oldValue.getFirstName() + "'", null);//.insert(TABLE, null, contentValues);

        database.close();
        return rowNumber;
    }

    public List<T> getAllPerson(Class<T> type) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        SQLiteDatabase database = getWritableDatabase();
        List<T> personList = new ArrayList<>();
        Cursor cursor = database.rawQuery(GETALL + TABLE,null);

        int id = 0;
        if (cursor.moveToFirst())
        {
            do
            {
                try
                {
                    Class[] argTypes = new Class[columnNames.length + 1];
                    Object[] args = new Object[columnNames.length + 1];

                    args[0] = cursor.getInt(0);
                    argTypes[0] = int.class;
                    for (int i = 1;i < args.length; i++)
                    {
                        args[i] = cursor.getString(i);
                        argTypes[i] = String.class;
                    }

                    //Type typeArgs = getClass().getTypeParameters()[0];
                    //Class<T> type = type;
                    Constructor<T> constructor = type.getConstructor(argTypes);
                    T person =(T)constructor.newInstance(args);//  new Person(cursor.getString(0), cursor.getString(1), cursor.getString(2));
                    personList.add(person);
                }
                catch (Exception e){throw e;}
                id++;
            }
            while (cursor.moveToNext());
        }
        database.close();
        return personList;
    }
}
