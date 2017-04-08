package com.huasheng.sysq.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;

import com.huasheng.sysq.model.Version;
import com.huasheng.sysq.util.db.DBConstants;
import com.huasheng.sysq.util.db.SysQOpenHelper;

public class VersionDB {

	public static List<Version> select(){
		Cursor cursor = SysQOpenHelper.getDatabase().query(
				DBConstants.TABLE_VERSION,null,null,null,null,null,null);
		List<Version> versionList = new ArrayList<Version>();
		while(cursor.moveToNext()){
			Version version = fillObjectFromDB(cursor);
			versionList.add(version);
		}
		cursor.close();
		return versionList;
	}
	
	public static Version select(int versionId){
		Cursor cursor = SysQOpenHelper.getDatabase().query(
				DBConstants.TABLE_VERSION,null,"id=?",new String[]{Integer.toString(versionId)},null,null,null);
		Version version = null;
		if(cursor.moveToNext()){
			version = fillObjectFromDB(cursor);
		}
		cursor.close();
		return version;
	}
	
	private static Version fillObjectFromDB(Cursor cursor){
		Version version = new Version();
		version.setId(cursor.getInt(cursor.getColumnIndex("id")));
		version.setName(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_VERSION_NAME)));
		version.setPublishDate(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_VERSION_PUBLISH_DATE)));
		version.setRemark(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_VERSION_REMARK)));
		version.setIsCurrent(cursor.getInt(cursor.getColumnIndex(DBConstants.COLUMN_VERSION_IS_CURRENT)));
		return version;
	}
	
	public static void update(Version version){
		ContentValues values = fillDBFromObject(version);
		SysQOpenHelper.getDatabase().update(DBConstants.TABLE_VERSION, values,"id=?",new String[]{Integer.toString(version.getId())});
	}
	
	private static ContentValues fillDBFromObject(Version version){
		ContentValues values = new ContentValues();
		values.put(DBConstants.COLUMN_VERSION_NAME, version.getName());
		values.put(DBConstants.COLUMN_VERSION_PUBLISH_DATE, version.getPublishDate());
		values.put(DBConstants.COLUMN_VERSION_REMARK, version.getRemark());
		values.put(DBConstants.COLUMN_VERSION_IS_CURRENT, version.getIsCurrent());
		return values;
	}
}
