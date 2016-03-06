package com.huasheng.sysq.activity.reservation;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;

import com.huasheng.sysq.R;
import com.huasheng.sysq.db.ReservationDB;
import com.huasheng.sysq.model.Reservation;
import com.huasheng.sysq.util.DateTimeUtils;

public class ReservationAddActivity extends Activity implements OnClickListener{
	
	private Button selectDateBtn;
	private EditText bookDateET;
	
	private Button submitBtn;
	
	private EditText usernameET;
	private EditText identityCardET;
	private EditText mobileET;
	private EditText familyMobileET;
	private RadioButton caseRB;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_reservation_add);
		
		bookDateET = (EditText)findViewById(R.id.reservation_add_book_date_et);
		selectDateBtn = (Button)findViewById(R.id.reservation_add_select_date_btn);
		selectDateBtn.setOnClickListener(this);
		
		submitBtn = (Button)findViewById(R.id.reservation_add_submit);
		submitBtn.setOnClickListener(this);
		
		usernameET = (EditText)findViewById(R.id.reservation_add_username);
		identityCardET = (EditText)findViewById(R.id.reservation_add_identity_card);
		mobileET = (EditText)findViewById(R.id.reservation_add_mobile);
		familyMobileET = (EditText)findViewById(R.id.reservation_add_family_mobile);
		caseRB = (RadioButton)findViewById(R.id.reservation_add_type_case);
	}

	@Override
	public void onClick(View view) {
		if(view.getId() == R.id.reservation_add_select_date_btn){
			Calendar now = Calendar.getInstance();
			int year = now.get(Calendar.YEAR);
			int month = now.get(Calendar.MONTH);
			int day = now.get(Calendar.DAY_OF_MONTH);
			DatePickerDialog dateDialog = new DatePickerDialog(this, new OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker datePicker, final int year, final int month, final int day) {
					Calendar now = Calendar.getInstance();
					int hour = now.get(Calendar.HOUR);
					int minute = now.get(Calendar.MINUTE);
					TimePickerDialog timeDialog = new TimePickerDialog(ReservationAddActivity.this, new OnTimeSetListener() {
						
						@Override
						public void onTimeSet(TimePicker timePicker, int hour, int minute) {
							bookDateET.setText(year + 
													"-" + 
													DateTimeUtils.showDoubleNumber(month+1) + 
													"-" +
													DateTimeUtils.showDoubleNumber(day) + 
													" " +
													DateTimeUtils.showDoubleNumber(hour) + 
													":" + 
													DateTimeUtils.showDoubleNumber(minute));
						}
					}, hour, minute, true);
					timeDialog.show();
				}
			}, year, month,day);
			dateDialog.show();
		}else if(view.getId() == R.id.reservation_add_submit){
			String username = usernameET.getText().toString();
			String identityCard = identityCardET.getText().toString();
			String mobile = mobileET.getText().toString();
			String familyMobile = familyMobileET.getText().toString();
			int type = caseRB.isChecked()?Reservation.TYPE_CASE : Reservation.TYPE_CONTRAST;
			String bookDate = bookDateET.getText().toString();
			
			Reservation reservation = new Reservation();
			reservation.setUsername(username);
			reservation.setIdentityCard(identityCard);
			reservation.setMobile(mobile);
			reservation.setFamilyMobile(familyMobile);
			reservation.setType(type);
			reservation.setBookDate(bookDate);
			reservation.setStatus(Reservation.STATUS_BOOKING);
			
			ReservationDB.save(reservation);
			startActivity(new Intent(this,ReservationListActivity.class));
		}
	}
}

