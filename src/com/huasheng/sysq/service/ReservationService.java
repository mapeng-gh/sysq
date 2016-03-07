package com.huasheng.sysq.service;

import java.text.ParseException;
import java.util.List;

import com.huasheng.sysq.db.ReservationDB;
import com.huasheng.sysq.model.Page;
import com.huasheng.sysq.model.Reservation;

public class ReservationService {
	
	public static void removeReservation(int reservationId){
		ReservationDB.delete(reservationId);
	}
	
	public static List<Reservation> listReservation(String searchStr) throws ParseException{
		
		Reservation reservation = new Reservation();
		reservation.setUsername(searchStr);
		
		List<Reservation> data = ReservationDB.search(reservation, "or", null, null);
		
		return data;
	}

	public static Page<Reservation> searchReservation(String searchStr,Integer pageNo,Integer pageSize){
		
		Reservation reservation = new Reservation();
		reservation.setUsername(searchStr);
		
		//分页计算
		Integer offset = null;
		Integer limit = pageSize;
		offset = (pageNo - 1) * pageSize;
		
		//数据查询
		List<Reservation> data = ReservationDB.search(reservation, "or", offset, limit);
		
		//构造page
		Page<Reservation> page = new Page<Reservation>();
		page.setData(data);
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		
		//计算总页数
		int size = ReservationDB.size(reservation, "or");
		int totalPages = size % pageSize == 0 ? size / pageSize : size / pageSize + 1;
		page.setTotalPages(totalPages);
		
		return page;
	}
	
	public static void addReservation(Reservation reservation){
		ReservationDB.save(reservation);
	}
}
