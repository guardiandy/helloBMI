package com.example.andy.hellobmi.dbframework;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Andy on 2016/5/4.
 */
public abstract class DataTable {
    private String tableName;
    private Set<TableColumn> tableColumns;
    private SQLiteDatabase database;

    public DataTable(SQLiteDatabase database, String tableName) {
        this.tableName = tableName;
        this.database = database;
        tableColumns = new HashSet<>();
    }

    public Set<TableColumn> getTableColumns() {
        return tableColumns;
    }

    public void addColumn(TableColumn tableColumn) {
        tableColumns.add(tableColumn);
    }

    public boolean isTableExists() {
        Cursor cursor = database.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);
        if (cursor == null)
            return false;
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    public boolean CreateTable() {
        if (this.isTableExists()) return false;

        String createDBString = "CREATE TABLE " + tableName + " (";
        for (TableColumn col : tableColumns) {
            if (col.getKey() == TableColumn.KeyType.primary) {
                createDBString = createDBString + col.getColumnName() + col.getFieldType() + col.getKeyType() + col.getFieldInit();
            } else {
                createDBString = createDBString + col.getColumnName() + col.getFieldType() + col.getFieldInit();
            }
            createDBString = createDBString + ",";
        }
        createDBString = createDBString + ")";
        createDBString = createDBString.replace(",)", ")");
        database.execSQL(createDBString);
        return true;
    }

    public void dropTable() {

        database.execSQL("DROP TABLE IF EXISTS " + tableName);

    }
    public long insert(TableRow row){
        ContentValues cv = new ContentValues();
        for(TableColumn col : tableColumns){
            String columnName = col.getColumnName();
            cv.put(columnName, row.getFields(columnName));
        }
        long rowId = database.insert(tableName, null, cv);
        row.setSuccess(rowId != -1);
        return rowId;
    }
}
