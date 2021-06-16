package edu.bit.ex.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.bit.ex.mapper.EmpMapper;
import edu.bit.ex.mapper.MemberMapper;
import edu.bit.ex.vo.EmpUser;
import edu.bit.ex.vo.EmpVO;
import edu.bit.ex.vo.MemberUser;
import edu.bit.ex.vo.MemberVO;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

// principal.emp.sal = 확장하고싶지?
// loadUserByUsername의 return값은 UserDetails
// 자손을 구현하면 확장이 가능하다

@Log4j
@Service
public class EmpDetailsService implements UserDetailsService {

	// IoC 컨테이너에 넣는다.
	@Setter(onMethod_ = @Autowired)
	private EmpMapper empMapper;
	
	@Override
	public UserDetails loadUserByUsername(String ename) throws UsernameNotFoundException {
		// loadUserByUsername(String ename) 받아오려면 
		// 함수 호출 불러오는 주체는 스프링 시큐리티가 함.(적당한 시기에 호출)
		// -> 아이디 비번을 치면 스프링 시큐리티가 필터해준다.(BLAKE 찾으려고)
		// (username이 BLAKE)
		// !비밀번호는 넘겨주지않는다. 유저네임만!
		// -> BLAKE를 넘겨줘서 유저정보를 보여준다.
		
		log.warn("Load User By EmpVO number: " + ename);
		EmpVO vo = empMapper.getEmp(ename);

		log.warn("queried by MemberVO mapper: " + vo);

		return vo == null ? null : new EmpUser(vo);
		// UserDetails가 부모 Empuser가 자식이기때문에 호출가능

	}
}