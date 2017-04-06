package com.huasheng.sysq.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.database.Cursor;

import com.huasheng.sysq.util.db.SysQOpenHelper;

public class StaticsDB {

	public static List<Map<String,String>> execSQL(String sql,String[] args){
		
		Cursor cursor = SysQOpenHelper.getDatabase().rawQuery(sql, args);
		
		List<Map<String,String>> data = new ArrayList<Map<String,String>>();
		
		while(cursor.moveToNext()){
			
			Map<String,String> row = new HashMap<String,String>();
			
			String[] names = cursor.getColumnNames();
			
			for(String name : names){
				
				String value = cursor.getString(cursor.getColumnIndex(name));
				row.put(name, value);
			}
			
			data.add(row);
		}
		cursor.close();
		
		return data;
	}
}
