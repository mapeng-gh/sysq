package com.huasheng.sysq.activity.reservation;

import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;

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
import android.widget.TextView;
import android.widget.TimePicker;

import com.huasheng.sysq.R;
import com.huasheng.sysq.db.ReservationDB;
import com.huasheng.sysq.model.Reservation;
import com.huasheng.sysq.util.BaseActivity;
import com.huasheng.sysq.util.DateTimeUtils;
import com.huasheng.sysq.util.RegexUtils;
import com.huasheng.sysq.util.SysqApplication;

public class ReservationAddActivity extends BaseActivity implements OnClickListener{
	
	private TextView selectDateBtn;
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
		selectDateBtn = (TextView)findViewById(R.id.reservation_add_select_date_btn);
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
		if(view.getId() == R.id.reservation_add_select_date_btn){//选择预约日期
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
			
			Reservation reservation = new Reservation();
			
			String username = usernameET.getText().toString();
			if(StringUtils.isEmpty(StringUtils.trim(username))){
				SysqApplication.showMessage("姓名不能为空");
				return;
			}
			reservation.setUsername(username);
			
			String identityCard = identityCardET.getText().toString();
			if(!RegexUtils.test("[0-9]{18}", identityCard)){
				SysqApplication.showMessage("请填写18位正确的身份证号");
				return;
			}
			reservation.setIdentityCard(identityCard);
			
			String mobile = mobileET.getText().toString();
			if(!RegexUtils.test("[0-9]{10,11}",mobile)){
				SysqApplication.showMessage("请填写10或11位正确电话号码");
				return;
			}
			reservation.setMobile(mobile);
			
			String familyMobile = familyMobileET.getText().toString();
			if(!RegexUtils.test("[0-9]{10,11}",familyMobile)){
				SysqApplication.showMessage("请填写10或11位正确亲属电话号码");
				return;
			}
			reservation.setFamilyMobile(familyMobile);
			
			int type = caseRB.isChecked()?Reservation.TYPE_CASE : Reservation.TYPE_CONTRAST;
			reservation.setType(type);
			
			
			String bookDate = bookDateET.getText().toString();
			if(StringUtils.isEmpty(bookDate)){
				SysqApplication.showMessage("请填写预约时间");
				return;
			}
			reservation.setBookDate(bookDate);
			
			ReservationDB.save(reservation);
			startActivity(new Intent(this,ReservationListActivity.class));
		}
	}
}

