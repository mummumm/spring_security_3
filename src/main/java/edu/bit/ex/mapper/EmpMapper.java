package edu.bit.ex.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.bit.ex.vo.EmpVO;
import edu.bit.ex.vo.MemberVO;
import edu.bit.ex.vo.UserVO;


@Mapper
public interface EmpMapper  {
	
	// 마이바티스 3번째 방법 이용!
	@Select("select * from emp where ename = #{ename}")
	// 원래는 empno에서 가져와야하는건데 비밀번호는 넘겨주지않기때문에 이름으로 받아와야한다.
	
	public EmpVO getEmp(String ename); 
	// username(스프링 시큐리티) = id

}