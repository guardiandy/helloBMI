package com.example.andy.hellobmi.dbframework;

/**
 * Created by Andy on 2016/5/4.
 */
public class TableColumn {
    private String columnName;
    private FieldType fieldType;
    private FieldInit fieldInit;
    private KeyType keyType;

    public enum FieldType {
        text, real, integer
    }

    public enum FieldInit {
        allowNull, notNull, autoIncerment
    }

    public enum KeyType {
        none, primary
    }

    public TableColumn(String columnName, FieldType fieldType){
        this.columnName = columnName;
        this.fieldType = fieldType;
        this.fieldInit = fieldInit.allowNull;
        this.keyType = keyType.none;
    }

    public TableColumn(String columnName, FieldType fieldType, FieldInit fieldInit){
        this.columnName = columnName;
        this.fieldType = fieldType;
        this.fieldInit = fieldInit;
        this.keyType = keyType.none;
    }

    public TableColumn(String columnName,FieldType fieldType, FieldInit fieldInit, KeyType keyType){
        this.columnName = columnName;
        this.fieldType = fieldType;
        this.fieldInit = fieldInit;
        this.keyType = keyType;
    }

    public String getColumnName(){
        return this.columnName;
    }
    public KeyType getKey(){
        return this.keyType;
    }

    public String getFieldType(){
        switch (fieldType){
            case text:
                return " TEXT";
            case real:
                return  " REAL";
            case integer:
                return  " INTEGER";
        }
        return "";
    }

    public String getKeyType(){
        switch (keyType){
            case none:
                return "";
            case primary:
                return " PRIMARY KEY";
        }
        return "";
    }

    public String getFieldInit(){
        switch (fieldInit){
            case notNull:
                return " NOT NULL";
            case allowNull:
                return "";
            case autoIncerment:
                return " AUTOINCREMENT";
        }
        return "";
    }

}
