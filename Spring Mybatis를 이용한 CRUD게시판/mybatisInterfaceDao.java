package com.javalec.practice.BDao;

import java.util.ArrayList;

import com.javalec.practice.BDto.BDto;
//==> Dao interface 에서 final 을 쓰지 않는다 //
//==> Dao JDBC Template 에서는 final 사용//
public interface IDao {
	public ArrayList<BDto> list();
	public BDto contentView(int bId);
	public void write(String bName, String bTitle,  String bContent);
	public void delete(int bId);
	public void modify(int bId,String bName,String bTitle,String bContent);
	public void upHit(int bId);
	public int memberCheck(String userid, String passcode);
	
}
