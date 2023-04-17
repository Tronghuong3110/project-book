package com.bookstore.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "bill_detail")
public class BillDetailEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "bigint")
	private Long id;
	
	@Column(name = "name_receiver", columnDefinition = "nvarchar(150)")
	private String nameReceiver;
	
	@Column(name = "village", columnDefinition = "nvarchar(255)")
	private String village;
	
	@Column(name = "commune", columnDefinition = "nvarchar(255)")
	private String commune;
	
	@Column(name = "district", columnDefinition = "nvarchar(255)")
	private String district;
	
	@Column(name = "consious", columnDefinition = "nvarchar(255)")
	private String consious;
	
	@Column(name = "phone_receiver", length = 12)
	private String phoneReceiver;
	
	@OneToOne
	@JoinColumn(name = "bill_id")
	private BillEntity bill;

	public String getNameReceiver() {
		return nameReceiver;
	}

	public void setNameReceiver(String nameReceiver) {
		this.nameReceiver = nameReceiver;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getCommune() {
		return commune;
	}

	public void setCommune(String commune) {
		this.commune = commune;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getConsious() {
		return consious;
	}

	public void setConsious(String consious) {
		this.consious = consious;
	}

	public String getPhoneReceiver() {
		return phoneReceiver;
	}

	public void setPhoneReceiver(String phoneReceiver) {
		this.phoneReceiver = phoneReceiver;
	}

	public BillEntity getBill() {
		return bill;
	}

	public void setBill(BillEntity bill) {
		this.bill = bill;
	}

	public Long getId() {
		return id;
	}
	
}
