package com.huasheng.sysq.activity.reservation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huasheng.sysq.R;
import com.huasheng.sysq.model.Page;
import com.huasheng.sysq.model.Reservation;
import com.huasheng.sysq.service.ReservationService;

public class ReservationListActivity extends Activity implements OnClickListener{
	
	private ListView listView;
	private ReservationAdapter adapter;
	
	private ImageButton addImgBtn;
	
	private TextView previousTV;
	private TextView nextTV;
	private TextView currentTV;
	private TextView totalTV;
	
	private int currentPage;
	private int totalPage;
	
	private LinearLayout bodyLL;
	private RelativeLayout paginationRL;
	private TextView noDataTV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_reservation_list);
		
		listView = (ListView)findViewById(R.id.reservation_list);
		addImgBtn = (ImageButton)findViewById(R.id.reservation_list_add);
		currentTV = (TextView)findViewById(R.id.reservation_list_current_page);
		totalTV = (TextView)findViewById(R.id.reservation_list_total_page);
		previousTV = (TextView)findViewById(R.id.reservation_list_previous);
		nextTV = (TextView)findViewById(R.id.reservation_list_next);
		bodyLL = (LinearLayout)findViewById(R.id.reservation_list_body);
		paginationRL = (RelativeLayout)findViewById(R.id.reservation_list_pagination);
		noDataTV = (TextView)findViewById(R.id.reservation_list_no_data);
		
		Page<Reservation> page = ReservationService.searchReservation("", 1,Page.PAGE_SIZE);
		if(page.getData() == null || page.getData().size() == 0){
			bodyLL.setVisibility(View.GONE);
			paginationRL.setVisibility(View.GONE);
			noDataTV.setVisibility(View.VISIBLE);
		}else{
			adapter = new ReservationAdapter(this,R.layout.reversation_item, page.getData());
			listView.setAdapter(adapter);
		}
		
		currentPage = page.getPageNo();
		totalPage = page.getTotalPages();
		currentTV.setText(page.getPageNo()+"");
		totalTV.setText(page.getTotalPages()+"");
		
		addImgBtn.setOnClickListener(this);
		
		previousTV.setOnClickListener(this);
		nextTV.setOnClickListener(this);
		
		
		
	}

	@Override
	public void onClick(View view) {
		if(view.getId() == R.id.reservation_list_add){
			startActivity(new Intent(this,ReservationAddActivity.class));
		}else if(view.getId() == R.id.reservation_list_previous){
			if(this.currentPage == 1){
				Toast.makeText(this, "已经到第一页",Toast.LENGTH_SHORT).show();
			}else{
				Page<Reservation> page = ReservationService.searchReservation("",this.currentPage-1,Page.PAGE_SIZE);
				refreshPage(page);
			}
			
		}else if(view.getId() == R.id.reservation_list_next){
			if(this.currentPage == this.totalPage){
				Toast.makeText(this, "已经到最后一页",Toast.LENGTH_SHORT).show();
			}else{
				Page<Reservation> page = ReservationService.searchReservation("",this.currentPage+1,Page.PAGE_SIZE);
				refreshPage(page);
			}
		}
	}
	
	private void refreshPage(Page<Reservation> page){
		this.adapter.clear();
		this.adapter.addAll(page.getData());
		
		currentPage = page.getPageNo();
		totalPage = page.getTotalPages();
		
		currentTV.setText(page.getPageNo()+"");
		totalTV.setText(page.getTotalPages()+"");
	}

}
