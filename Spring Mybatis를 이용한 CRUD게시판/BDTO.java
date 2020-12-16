package com.javalec.practice.BDto;

import java.sql.Timestamp;
//Data Transfer Object (DTO)
//==> DATA Transfer 
// Transfer ==> view/Controller 
//ORM 을 이용한 관계 매핑//
//==>ORM이란. Object Relational Mapping, 객체-관계 매핑. 객체와 관계형 데이터베이스의 데이터를 자동으로 매핑(연결)해주는 것을 말한다 ...//
//==> 매핑 역할을 DTO 에서 해주고 , Transfer 역할도 DTO 에서

//순서 1)DAO ==> 2)DTO ==> 3)Controller ==> 4)view  ==>주로, update / select
//순서 2)DAO ==> 2)Controller ==>3)view    ==>주로, create / delete

//==> DTO ==> 1)필드(ORM) ,2)setter( ) ,3)getter( ) ,4)constructor( ) 
//==> DTO가 가진 필드 ,setter , getter ,constructor 역할 ==> 값을 초기에 설정 , 초기화 , 값을 전달 , 
//==> DTO 객체의 내용물 특성 을 보면 transfer 를 위한 내용물 이라는것을 알수있음. 
public class BDto {
	
	  private int bId;
	  private String bName;
	  private String bTitle;
	  private String bContent;
	  private Timestamp bDate;
	  private int bHit;
	  private int bGroup;
	  private int bStep;
	  private int bIndent;
	  
	  public BDto() {
	  
	  }
  public BDto(int bId, String bName, String bTitle, String bContent, Timestamp bDate, int bHit, int bGroup, int bStep,
		int bIndent) {
	//super();
	  	//<-- reset(setting) -->
	this.bId = bId;
	this.bName = bName;
	this.bTitle = bTitle;
	this.bContent = bContent;
	this.bDate = bDate;
	this.bHit = bHit;
	this.bGroup = bGroup;
	this.bStep = bStep;
	this.bIndent = bIndent;
    }
  
  	//<--  transfer(setting / getting)  -->
	public int getbId() {
		return bId;
	}
	public String getbName() {
		return bName;
	}
	public String getbTitle() {
		return bTitle;
	}
	public String getbContent() {
		return bContent;
	}
	public Timestamp getbDate() {
		return bDate;
	}
	public int getbHit() {
		return bHit;
	}
	public int getbGroup() {
		return bGroup;
	}
	public int getbStep() {
		return bStep;
	}
	public int getbIndent() {
		return bIndent;
	}
	public void setbId(int bId) {
		this.bId = bId;
	}
	public void setbName(String bName) {
		this.bName = bName;
	}
	public void setbTitle(String bTitle) {
		this.bTitle = bTitle;
	}
	public void setbContent(String bContent) {
		this.bContent = bContent;
	}
	public void setbDate(Timestamp bDate) {
		this.bDate = bDate;
	}
	public void setbHit(int bHit) {
		this.bHit = bHit;
	}
	public void setbGroup(int bGroup) {
		this.bGroup = bGroup;
	}
	public void setbStep(int bStep) {
		this.bStep = bStep;
	}
	public void setbIndent(int bIndent) {
		this.bIndent = bIndent;
	}
	}
