	@Controller
	public class BController{
	
	@Autowired
	private SqlSession sqlSession;
	//==> XML에서 초기에 설정해준 SqlSession<==

	//==>Read<==
	@RequestMapping("/list")
	//==>게시글 리스트를 가져온다 <==

	public String list(Model model) {
		IDao dao = sqlSession.getMapper(IDao.class);
		//==>Dao interface , Xml에서 가져온 데이터값을 Dto 에 전달해준다(return)
		ArrayList<BDto>dtos = dao.list( );
		//==>Dto 에서는 전달받은 데이터를 setting 해줘서 Controller에서는 model 로 view 에 전달
		model.addAttribute("dtos",dtos);
		//==>처리해준 데이터를 list.jsp에 전달해주고 종료<==//
		return "list";

	}
			//==>Create (글쓰기 화면이 나오게한다)<==//
	@RequestMapping("/write_view")
		public String write_view(HttpServletRequest req, Model model) {
			//==> session 선언  <==//
			//==> 글쓰기 화면은 로그인을 했을때만 보여주게 하기 위해서임<==//
			HttpSession session = req.getSession( );
			if(session.getAttribute("uid") == null || sessiongetAttribute("uid").equals("")) {
				return "redirect:login";
			}
			
			retrun "write_view";
		}
		//==>Create(게시글 올리기)<==//
	@RequestMapping(value="/write",method=RequestMethod.POST)
		public String write(HttpServletRequest req , Model model) {
		//==> write_view.jsp 에서 던져준 데이터들을 getParameter( ) 이용해서 받는다<==//

			String bName = req.getParameter("bName");
			String bTitle = req.getParameter("bTitle");
			String bContent = req.getParameter("bContent");
			//==>getParameter( ) 를한 데이터들을 Dao interface에 전달을 해준다( 매개변수 )<==//
			IDao dao = sqlSession.getMapper(IDao.class);
			//==>Dao interface , xml 에서  DB 처리를 하고 끝낸다<==//
			dao.write(bName,bTitle,bContent);
			return "redirect:list";
		}
		//==>Read(클릭한 게시물의 게시물 내용을 읽어온다)<==//
		@RequestMapping("/content_view")
		//==>list.jsp의 <a href="content_view?bId=${dto.bId}"> 을 통해서 간접적으로 queryString 에 게시물번호를 입력하게됨<==// 
		public String content_view ( HttpServletRequest req , Model model ) { 
			int bId = Integer.parseInt(req.getParameter("bId"));
			IDao dao = sqlSession.getMapper(IDao.class);
			dao.upHit(bId);//==>조회할때마다 조회수 증가시킴
		//==>DaoInterface ==> XML ==>DaoInterface ==>Dto 로 데이터를 전달해준다 
			BDto dto = dao.contentView(bId);
			model.addAttribute("content_view",dto);
			return "content_view";

		}

		//==>update<==//
		//==>게시글 수정하기 페이지 보여주기 <==//
		//==>게시글 수정은 로그인 한 유저에게만 보여지게 해야한다<==//
		@RequestMapping("/modify_view")
		public String modify_view(HttpServletRequest req, Model model) {
			//==>세션을 사용해서 현재 보여지는 게시글 수정페이지를 보여준다<==//
			HttpSession session = req.getSession( );
			if(session.getAttribute("uid")==null || session.getAttribute("uid").equals("")){
				//==> 로그인 안되면 로그인 페이지로 보내는거 <==//
				return "redirect:login";
			}
			//==> 현재 보여지는 게시글의 게시글 번호 를 기준으로 수정을해야하기 때문에<==//
			//==>getParameter( ) 를 이용해서 bId 를 받아서 
			//==> Dao Interface ==> XML ==> Dto 데이터 전달 
			int bId = Integer.parseInt(req.getParameter("bId"));
			IDao dao = sqlSession.getMapper(IDao.class);
			BDto dto = dao.contentView(bId);
			model.addAttribute("modify_view,dto);
			return "modify_view";

		}
	@RequestMapping(value="/modify",method=RequestMethod.POST)
		public String modify(HttpServletRequest req , Model model) {
			//==> modify_view.jsp에서 form(POST방식으로)전달해준 데이터들을 
			//==>getParameter( ) 로 전달 받음 <==//
			String bId= req.getParameter("bId");
			String bName= req.getParameter("bName");
			String bTitle= req.getParameter("bTitle");
			String bContent= req.getParameter("bContent");
			IDao dao =  sqlSession.getMapper(IDao.class);
			dao.modify(InTeger.parseInt(bId),bName,bTitle,bContent);
			return "redirect:list";
		}

	@RequestMapping(value="/delete")
	public String delete(HttpServletRequest req , Model model) {
		HttpSession session = req.getSession( );
		if(session.getAttribute("uid")==null || session.getAttribute("uid").equals("")) {
			return "redirect:login";
		}
		//==> 게시물삭제 기준 내가 현재 보고있는 게시물 ( 게시물 고유 번호(bId)를 기준으로 삭제 )
		String bId = req.getParameter("bId");
		IDao dao = sqlSession.getMapper(IDao.class);
		dao.delete(Inteter.parseInt("bId"));
		return "redirect:list";
	}








	@RequestMapping(value="/member_check",method = RequestMethod.POST)
		//==> Login page 에서 전달한 값이 POST 라는 의미 <==//
		public String doCheck(HttpServletRequest req , Model model) {
			//==>userid,passcode : login.jsp에서 form으로 전달해준값 , 그값들을 getParameter를 통해서 받는다 <==//
			String userid = req.getParameter("userid");
			String passcode = req.getParameter("passcode");
			//받은값들을 session 이용해서 유효성 검사를 함//
			//session = 한번 로그인하면 그 내역이 계속 유지되면서 글쓰기 , 수정 , 삭제를 로그인 한번 만함으로써 유저의
			//정보를 유지하게 해주는 역할
				//==>세션 사용을 위한 준비<==//
			HttpSession session = req.getSession( );
			IDao dao = sqlSession.getMapper(IDao.class);
				//==>getParameter한 값들을 dao interface에 매개변수로 전달 , cnt 변수에 담음//
			int cnt = dao.membertCheck(userid,passcode);
				//==> 이 값들을 Dao interface ==> XML에서 해당 유저가 테이블에 존재하는지 0 , 1 숫자를 리턴함으로써 확인해줌
			if(cnt ==1) {
				//==>회원정보가 존재 한다<==//
				//==>세션에 유저의 정보를 담아준다<==//
				session.setAttribute("uid",userid);
			}else{
				//==>테이블에 회원정보가 없다(0)<==//
				return "redirect:login";
				//==> 회원정보가 없으므로 다시 로그인 하게 만든다<==//
			}
			//==>로그인 성공 하면 , 글목록으로 가게끔한다<==//
			return "redirect:list";
	}
	@RequestMapping("/logout")
	//==>로그아웃을 할때<==//
	//==>로그아웃한다 == 세션을 끊는다 <==//
		public String doLogout(HttpServletRequest req, Model model) {
			HttpSession session = req.getSession( );
			//==>세션을 끊는다<==//
			session.invalidate( );
			model.addAttribute("logout","Y");
			return "redirect:list";
		}

}