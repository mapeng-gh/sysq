package com.huasheng.sysq.activity.reservation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
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
	
	private Button addBtn;
	
	private TextView previousTV;
	private TextView nextTV;
	private TextView currentTV;
	private TextView totalTV;
	
	private int currentPage;
	private int totalPage;
	
	private LinearLayout bodyLL;
	private RelativeLayout paginationRL;
	private TextView noDataTV;
	
	private EditText searchET;
	private Button searchBtn;
	private String searchStr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_reservation_list);
		
		listView = (ListView)findViewById(R.id.reservation_list);
		addBtn = (Button)findViewById(R.id.reservation_list_add);
		currentTV = (TextView)findViewById(R.id.reservation_list_current_page);
		totalTV = (TextView)findViewById(R.id.reservation_list_total_page);
		previousTV = (TextView)findViewById(R.id.reservation_list_previous);
		nextTV = (TextView)findViewById(R.id.reservation_list_next);
		bodyLL = (LinearLayout)findViewById(R.id.reservation_list_body);
		paginationRL = (RelativeLayout)findViewById(R.id.reservation_list_pagination);
		noDataTV = (TextView)findViewById(R.id.reservation_list_no_data);
		searchET = (EditText)findViewById(R.id.reservation_list_search_et);
		searchBtn = (Button)findViewById(R.id.reservation_list_search);
		
		Page<Reservation> page = ReservationService.searchReservation("", 1,Page.PAGE_SIZE);
		this.refreshListView(page);
		
		addBtn.setOnClickListener(this);
		
		previousTV.setOnClickListener(this);
		nextTV.setOnClickListener(this);
		
		searchBtn.setOnClickListener(this);
		
	}

	@Override
	public void onClick(final View view) {
		if(view.getId() == R.id.reservation_list_add){//添加
			startActivity(new Intent(this,ReservationAddActivity.class));
		}else if(view.getId() == R.id.reservation_list_previous){//上一页
			if(this.currentPage == 1){
				Toast.makeText(this, "已经到第一页",Toast.LENGTH_SHORT).show();
			}else{
				Page<Reservation> page = ReservationService.searchReservation(searchStr,this.currentPage-1,Page.PAGE_SIZE);
				this.refreshListView(page);
			}
			
		}else if(view.getId() == R.id.reservation_list_next){//下一页
			if(this.currentPage == this.totalPage){
				Toast.makeText(this, "已经到最后一页",Toast.LENGTH_SHORT).show();
			}else{
				Page<Reservation> page = ReservationService.searchReservation(searchStr,this.currentPage+1,Page.PAGE_SIZE);
				this.refreshListView(page);
			}
		}else if(view.getId() == R.id.reservation_list_search){//搜索
			searchStr = searchET.getText().toString();
			Page<Reservation> page = ReservationService.searchReservation(searchStr, 1, Page.PAGE_SIZE);
			this.refreshListView(page);
		}else if(view.getId() == R.id.reservation_list_del){
			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
			dialogBuilder.setIcon(R.drawable.reservation_del);
			dialogBuilder.setTitle(this.getString(R.string.reservation_list_item_del_prompt));
			dialogBuilder.setPositiveButton(this.getString(R.string.alert_dialog_ok),new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					int reservationId = ((LinearLayout)view.getParent().getParent().getParent()).getId();
					ReservationService.removeReservation(reservationId);
					Page<Reservation> page = ReservationService.searchReservation("", 1, Page.PAGE_SIZE);
					ReservationListActivity.this.refreshListView(page);
				}
			});
			dialogBuilder.setNegativeButton(this.getString(R.string.alert_dialog_cancel), null);
			dialogBuilder.show();
		}
	}
	
	private void refreshListView(Page<Reservation> page){
		if(page.getData() == null || page.getData().size() == 0){
			bodyLL.setVisibility(View.GONE);
			paginationRL.setVisibility(View.GONE);
			noDataTV.setVisibility(View.VISIBLE);
		}else{
			if(adapter ==null){
				adapter = new ReservationAdapter(this,R.layout.reversation_item, page.getData(),this);
				listView.setAdapter(adapter);
			}else{
				adapter.clear();
				adapter.addAll(page.getData());
			}
			
			currentPage = page.getPageNo();
			totalPage = page.getTotalPages();
			
			currentTV.setText(page.getPageNo()+"");
			totalTV.setText(page.getTotalPages()+"");
		}
	}

}
