package com.example.andy.hellobmi.dbframework;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andy on 2016/5/6.
 */
public abstract class TableRow {
    private DataTable dataTable;
    private Map<String, String> fields;
    private boolean success;

    public TableRow(DataTable dataTable) {
        this.dataTable = dataTable;
        //初始化每個欄位的值
        fields = new HashMap<>();
        for (TableColumn col : this.dataTable.getTableColumns()) {
            fields.put(col.getColumnName(), "");
        }
    }

    public void setSuccess(boolean success){
        this.success = success;
    }

    public boolean isSuccess(){
        return success;
    }

    public Map<String, String> getFields(){
        return fields;
    }

    public String getFields(String fieldName){
        return fields.get(fieldName);
    }

    public void setFields(String fieldName, String value) {
        fields.put(fieldName, value);
    }
}

