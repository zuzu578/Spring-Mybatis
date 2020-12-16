<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC " -//mybatis.org//DTD Mapper 3.0//EN"
	"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
			<!--com.javalec.practice.BDao 패키지에 있는 nterface IDao 를 연결해준다 (mapper)  -->
	<mapper namespace="com.javalec.practice.BDao.IDao">
	
	
							<!-- mapper xml  -->
							<!--xml에서 sql작성  -->
							<!--resultType=> Dao 에서 return 할때 쓰는 타입 임 -->					
							<!--예를들어서 list 는 list에 담아서 dto 타입에 던져주기때문  -->
							
							
	<select id="list" resultType="com.javalec.practice.BDto.BDto">
	<!-- 게시물 목록 가져오기 -->
	select bId,bName,bTitle,bContent,bDate,bHit,bGroup,bStep,bIndent
		from mvc_board order by bId
	</select>
	<select id="contentView" resultType="com.javalec.practice.BDto.BDto">
	<!-- 클릭한 게시물 목록 가져오기 -->
	select bId,bName,bTitle,bContent,bDate,bHit,bGroup,bStep,bIndent
		 from mvc_board where BID =#{param1}
	
	
	</select>
	
	<insert id="write">
	<!-- 게시물 작성하기 -->
	insert into mvc_board(bId,bName,bTitle,bContent,bHit,bGroup,bStep,bIndent) 
	values(mvc_board_seq.nextval,#{param1},#{param2},#{param3},0,mvc_board_seq.currval,0,0)
	</insert>
	
	<delete id="delete">
	<!-- 게시물 삭제하기 -->
	delete from mvc_board where bId=#{param1}
	
	</delete>
	
	<update id="modify">
	<!-- 게시물 수정하기 -->
	update mvc_board set bName=#{param2},bTitle=#{param3},bContent=#{param4} where bId=#{param1}
	</update>
	<update id="upHit">
	<!-- 게시물 조회수 증가시키기 -->
	update mvc_board set bHit=bHit+1 where bId=#{param1}
	</update>
	
	<!-- 회원인지 아닌지 체크하는 sql -->
	<!--  체크할때는 꼭 클래스가 아니어도됨 (단일타입 가능 ) -->
	<select id="memberCheck" resultType="int">
		select count(*) cnt from member where userid=#{param1} and passcode=#{param2}
	</select>
	
</mapper>

	
