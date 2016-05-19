package com.example.andy.hellobmi.database;

import com.example.andy.hellobmi.dbframework.DataTable;
import com.example.andy.hellobmi.dbframework.TableRow;

/**
 * Created by Andy on 2016/5/13.
 */
public class Punch extends TableRow {
    private String _日期;
    private String _姓名;

    public Punch(DataTable dataTable) {
        super(dataTable);
    }

    public String 日期(){
        return _日期;
    }

    public void 日期(String _日期){
        this._日期 = _日期;
        this.setFields("日期", this._日期);
    }

    public String 姓名() {
        return _姓名;
    }

    public void 姓名(String _姓名){
        this._姓名 = _姓名;
        this.setFields("姓名", this._姓名);
    }
}
