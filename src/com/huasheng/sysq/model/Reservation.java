package com.huasheng.sysq.model;


/**
 * ԤԼ
 * @author map
 *
 */
public class Reservation {

	private Integer id;
	private String username;
	private String identityCard;
	private String mobile;
	private Integer type;
	private String bookDate;
	private String familyMobile;
	
	public static final Integer TYPE_CASE = 1;
	public static final Integer TYPE_CONTRAST = 2;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getIdentityCard() {
		return identityCard;
	}
	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getBookDate() {
		return bookDate;
	}
	public void setBookDate(String bookDate) {
		this.bookDate = bookDate;
	}
	public String getFamilyMobile() {
		return familyMobile;
	}
	public void setFamilyMobile(String familyMobile) {
		this.familyMobile = familyMobile;
	}
}
