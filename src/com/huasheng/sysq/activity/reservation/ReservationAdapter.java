package com.huasheng.sysq.activity.reservation;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huasheng.sysq.R;
import com.huasheng.sysq.model.Reservation;
import com.huasheng.sysq.util.MyApplication;

public class ReservationAdapter extends ArrayAdapter<Reservation>{
	
	private int resource;
	private ReservationListActivity activity;

	public ReservationAdapter(Context context, int resource,List<Reservation> objects,ReservationListActivity activity) {
		super(context, resource, objects);
		this.resource = resource;
		this.activity = activity;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Reservation reservation = getItem(position);
		
		View reservationView;
		if(convertView == null){
			reservationView = LayoutInflater.from(getContext()).inflate(this.resource, null);
		}else{
			reservationView = convertView;
		}
		
		TextView idTV = (TextView)reservationView.findViewById(R.id.reservation_list_id);
		TextView usernameTV = (TextView)reservationView.findViewById(R.id.reservation_list_username);
		TextView mobileTV = (TextView)reservationView.findViewById(R.id.reservation_list_mobile);
		TextView typeTV = (TextView)reservationView.findViewById(R.id.reservation_list_type);
		TextView bookDateTV = (TextView)reservationView.findViewById(R.id.reservation_list_book_date);
		
		idTV.setText(reservation.getId()+"");
		usernameTV.setText(reservation.getUsername());
		mobileTV.setText(reservation.getMobile());
		if(reservation.getType() == Reservation.TYPE_CASE){
			typeTV.setText(MyApplication.getContext().getString(R.string.reservation_add_type_case_text));
		}else if(reservation.getType() == Reservation.TYPE_CONTRAST){
			typeTV.setText(MyApplication.getContext().getString(R.string.reservation_add_type_contrast_text));
		}
		bookDateTV.setText(reservation.getBookDate());
		
		ImageButton delImgBtn = (ImageButton)reservationView.findViewWithTag("reservation_del");
		delImgBtn.setOnClickListener(this.activity);
		((LinearLayout)delImgBtn.getParent().getParent().getParent()).setId(reservation.getId());
		
		return reservationView;
	}

}
