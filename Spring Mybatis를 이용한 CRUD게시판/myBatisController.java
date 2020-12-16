package com.javalec.practice.controller;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javalec.practice.BDao.BDao;
import com.javalec.practice.BDao.IDao;
import com.javalec.practice.BDto.BDto;
import com.javalec.practice.util.Constant;

@Controller
public class BController {
//mybatis 를 이용한 게시판//
	//Spring Session check ==> 글쓰기 , 수정 , 삭제 ==> 로그인한 유저만 할수있게 하는 기능 //
	//DB에서 유저 정보를 조회 , 유효한 회원 정보를 session에 담아주고 , 글쓰기 , 수정 , 삭제 를 할때 검사
	//하게끔해서 기능을 상황에따라 제한 / 이용 가능 하게 할수있음 
	@Autowired 
	private SqlSession sqlSession;
	//@Autowired private SqlSession sqlSession; ==> servlet-context.xml 에서 설정한
	//mybatis 를 사용한다 (Autowired)
	//DAO 인터페이스 가 ==> DAO XML 에서 데이터를 가져온다 ==> Controller 에 데이터를 전달
	//Controller에서 DAO 인터페이스한테 데이터를 전달 ==> XML에 전달 ==> DB에전달 
	//DAO interface ==> override , 실질적으로 Data 를 다루는 일은 XML에서 하고 , DAO interface 는 정의
	//되기만하고, 역할은 controller에서 값을 받아오거나 , controller에게 값을 전달해주는 매개 역할을 함 
	
	
							//Read//
					//==> 글 목록 읽어오기 <==//
	@RequestMapping("/list")
	
	public String list(Model model) {
		//==> XML에서 select 문을 이용해서 DAO Interface 한테 데이터 값을 전달 , DAO Interface는 그값을 다시
		//컨트롤러에 꽂아주는 역할함
		IDao dao = sqlSession.getMapper(IDao.class);
		ArrayList<BDto>dtos = dao.list();
		model.addAttribute("dtos",dtos);
		
		return "list";
	}
	//create 화면 나오게 하기//
	@RequestMapping("/write_view") // 새글 쓰기  화면 나오게 하기 
	public String write_view(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession();
		if(session.getAttribute("uid") == null ||session.getAttribute("uid").equals("")) {
			return "login";
		}
		System.out.println("write_view()");
		System.out.println("글쓰기 ");
		
		return "write_view";
	}
	
		@RequestMapping(value="/write",method=RequestMethod.POST) //게시글 저장(등록)//
		public String write(HttpServletRequest req, Model model) {
			
			String bName = req.getParameter("bName");
			String bTitle = req.getParameter("bTitle");
			String bContent = req.getParameter("bContent");
			IDao dao = sqlSession.getMapper(IDao.class);
			dao.write(bName,bTitle,bContent);
			return "redirect:list";
			
			
		}
		//read//
		//클릭한 게시물의 게시물 내용을 읽어오기//
		@RequestMapping("/content_view")
		public String content_view(HttpServletRequest req, Model model) {
			System.out.println("content_view()");
			int bId=Integer.parseInt(req.getParameter("bId"));
			IDao dao = sqlSession.getMapper(IDao.class);
			dao.upHit(bId);//==> 게시물 조회수 +1
			BDto dto = dao.contentView(bId);
			model.addAttribute("content_view",dto);
			return "content_view";
		}
		
		//update//
		@RequestMapping("/modify_view")
		
		public String modify_view(HttpServletRequest req, Model model) {
			HttpSession session = req.getSession();
			if(session.getAttribute("uid") == null ||session.getAttribute("uid").equals("")) {
				return "redirect:login";
			}
			System.out.println("content_view()");
			
			int bId=Integer.parseInt(req.getParameter("bId"));
			
			IDao dao = sqlSession.getMapper(IDao.class);
			
			BDto dto = dao.contentView(bId);
			model.addAttribute("modify_view",dto);
			return "modify_view";

		}
		@RequestMapping(value="/modify",method=RequestMethod.POST)
		public String modify(HttpServletRequest req, Model model) {
			System.out.println("modify()");
			
			String bId=req.getParameter("bId");
			String bName =req.getParameter("bName");
			String bTitle=req.getParameter("bTitle");
			String bContent=req.getParameter("bContent");
			IDao dao = sqlSession.getMapper(IDao.class);
			
			//==> dao.modify(  ) ==> 매개변수를 받아서 sql 실행//
			dao.modify(Integer.parseInt(bId),bName,bTitle,bContent);
			//==>작업 종료시 list (path(mapping))로 redirect 
			return "redirect:list";
		}
		
		//delete//
		@RequestMapping(value="/delete")
		public String delete(HttpServletRequest req, Model model) {
			HttpSession session = req.getSession();
			//세션의 내용(계정이 null 이거나 공백일때 login 하게끔 한다)//
			if(session.getAttribute("uid") == null ||session.getAttribute("uid").equals("")) {
				return "redirect:login";
			}
			System.out.println("delete()"); 
			String bId= req.getParameter("bId");
			IDao dao = sqlSession.getMapper(IDao.class);
			
			dao.delete(Integer.parseInt(bId));
		
			return "redirect:list";
		}
		
		@RequestMapping("/login")
		public String doLogin() {
			return "login";
		}
		@RequestMapping(value = "/member_check",method = RequestMethod.POST)
		public String doCheck(HttpServletRequest req, Model model) {
			String userid = req.getParameter("userid");
			String passcode = req.getParameter("passcode");
			//session 이용 
			HttpSession session = req.getSession();
			IDao dao = sqlSession.getMapper(IDao.class);
			int cnt = dao.memberCheck(userid,passcode);
			if(cnt==1) {
				//==> 회원정보 존재 //
				//==> uid담아줌
				session.setAttribute("uid", userid);
			}else {
				//==>회원정보 없음
				return "redirect:login";
			}
			return "redirect:list";
		}
		@RequestMapping("/logout")
		//로그아웃할때
		public String doLogout(HttpServletRequest req , Model model) {
			HttpSession session = req.getSession();
			//로그아웃시 세션종료를 하고 리스트로 돌아간다
			session.invalidate();
			model.addAttribute("logout","Y");
			return "redirect:/list";
			
		}
	}
	

