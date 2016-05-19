package com.example.andy.hellobmi.database;

import android.database.sqlite.SQLiteDatabase;

import com.example.andy.hellobmi.dbframework.TableColumn;
import com.example.andy.hellobmi.dbframework.DataTable;

/**
 * Created by Andy on 2016/5/4.
 */
public class PunchTable extends DataTable {
    public PunchTable(SQLiteDatabase database, String tableName){
        super(database, tableName);
        addColumn(new TableColumn("日期", TableColumn.FieldType.integer, TableColumn.FieldInit.notNull, TableColumn.KeyType.primary));
        addColumn(new TableColumn("姓名", TableColumn.FieldType.text));
        CreateTable();
    }
}

