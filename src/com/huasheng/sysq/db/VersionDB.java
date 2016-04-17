package com.huasheng.sysq.db;

import android.database.Cursor;

import com.huasheng.sysq.model.Version;
import com.huasheng.sysq.util.ColumnConstants;
import com.huasheng.sysq.util.SysQOpenHelper;
import com.huasheng.sysq.util.TableConstants;

public class VersionDB {

	public static Version getCurVersion(){
		Cursor cursor = SysQOpenHelper.getDatabase().query(
				TableConstants.TABLE_VERSION,null,"is_current = 1",null,null,null,null);
		Version curVersion = null;
		if(cursor.moveToNext()){
			curVersion = new Version();
			curVersion.setId(cursor.getInt(cursor.getColumnIndex("id")));
			curVersion.setName(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_VERSION_NAME)));
			curVersion.setPublishDate(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_VERSION_PUBLISH_DATE)));
			curVersion.setRemark(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_VERSION_REMARK)));
			curVersion.setIsCurrent(cursor.getInt(cursor.getColumnIndex(ColumnConstants.COLUMN_VERSION_IS_CURRENT)));
		}
		cursor.close();
		return curVersion;
	}
}
