package com.huasheng.sysq.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import android.content.ContentValues;
import android.database.Cursor;

import com.huasheng.sysq.model.Reservation;
import com.huasheng.sysq.util.Constants;

public class ReservationDB {

	public static List<Reservation> search(Reservation reservation,String searchType,Integer offset,Integer limit) throws ParseException{
		
		//处理过滤条件
		String selection = (String)whereSql(reservation,searchType).get("selection");
		String[] selectionArgs = (String[])whereSql(reservation,searchType).get("selectionArgs");
		
		//处理分页
		String limitStr = null;
		if(offset != null && limit != null){
			limitStr = offset + "," + limit;
		}
		
		//遍历数据
		Cursor cursor = SysQOpenHelper.getDatabase().query(Constants.TABLE_RESERVATION, null, selection, selectionArgs, null, null, null,limitStr);
		List<Reservation> data = new ArrayList<Reservation>();
		if(cursor.moveToFirst()){
			do{
				data.add(fill(cursor));//填充数据
			}while(cursor.moveToNext());
		}
		cursor.close();
		
		return data;
	}
	
	public static int size(Reservation reservation,String searchType){
		
		//处理过滤条件
		String selection = (String)whereSql(reservation,searchType).get("selection");
		String[] selectionArgs = (String[])whereSql(reservation,searchType).get("selectionArgs");
		
		Cursor cursor = SysQOpenHelper.getDatabase().query(Constants.TABLE_RESERVATION, null, selection, selectionArgs, null, null, null);
		return cursor.getCount();
	}
	
	public static void save(Reservation reservation){
		
		ContentValues values = new ContentValues();
		values.put(Constants.TABLE_RESERVATION_COLUMN_NAME, reservation.getUsername());
		values.put(Constants.TABLE_RESERVATION_COLUMN_IDENTITY_CARD, reservation.getIdentityCard());
		values.put(Constants.TABLE_RESERVATION_COLUMN_MOBILE, reservation.getMobile());
		values.put(Constants.TABLE_RESERVATION_COLUMN_FAMILY_MOBILE, reservation.getFamilyMobile());
		values.put(Constants.TABLE_RESERVATION_COLUMN_TYPE, reservation.getType());
		values.put(Constants.TABLE_RESERVATION_COLUMN_BOOK_DATE, reservation.getBookDate());
		values.put(Constants.TABLE_RESERVATION_COLUMN_STATUS, reservation.getStatus());
		
		SysQOpenHelper.getDatabase().insert(Constants.TABLE_RESERVATION, null, values);
	}
	
	private static Reservation fill(Cursor cursor) throws ParseException{
		Reservation reservation = new Reservation();
		
		reservation.setId(cursor.getInt(cursor.getColumnIndex("id")));
		reservation.setUsername(cursor.getString(cursor.getColumnIndex(Constants.TABLE_RESERVATION_COLUMN_NAME)));
		reservation.setIdentityCard(cursor.getString(cursor.getColumnIndex(Constants.TABLE_RESERVATION_COLUMN_IDENTITY_CARD)));
		reservation.setMobile(cursor.getString(cursor.getColumnIndex(Constants.TABLE_RESERVATION_COLUMN_MOBILE)));
		reservation.setFamilyMobile(cursor.getString(cursor.getColumnIndex(Constants.TABLE_RESERVATION_COLUMN_FAMILY_MOBILE)));
		reservation.setType(cursor.getInt(cursor.getColumnIndex(Constants.TABLE_RESERVATION_COLUMN_TYPE)));
		reservation.setBookDate(cursor.getString(cursor.getColumnIndex("book_date")));
		reservation.setStatus(cursor.getInt(cursor.getColumnIndex(Constants.TABLE_RESERVATION_COLUMN_STATUS)));
		
		return reservation;
	}
	
	/**
	 * 动态生成selection和selectionArgs
	 * 
	 * 目前只处理了name
	 * 
	 * @param reservation
	 * @return
	 */
	private static Map<String,Object> whereSql(Reservation reservation,String searchType){
		
		String selection = null;
		String[] selectionArgs = null;
		
		List<String> selectionList = new ArrayList<String>();
		List<String> selectionArgsList = new ArrayList<String>();
		
		String username = reservation.getUsername();
		if(username != null && !username.equals("") && !username.trim().equals("")){
			selectionList.add(Constants.TABLE_RESERVATION_COLUMN_NAME + " like ?");
			selectionArgsList.add(username);
		}
		
		if(selectionList.size() > 0){
			selection = StringUtils.join(selectionList, " " + searchType + " ");
		}
		if(selectionArgsList.size() > 0){
			selectionArgs = selectionArgsList.toArray(new String[selectionArgsList.size()]);
		}
		
		Map<String,Object> selectionMap = new HashMap<String,Object>();
		selectionMap.put("selection", selection);
		selectionMap.put("selectionArgs", selectionArgs);
		
		return selectionMap;
	}
	
}
