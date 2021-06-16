package edu.bit.ex.service;

import java.sql.SQLException;

import javax.inject.Inject;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.bit.ex.mapper.UserMapper;
import edu.bit.ex.vo.UserVO;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@NoArgsConstructor
@Service
public class TxService {

	@Inject // @Autowired도 가능
	private UserMapper userMapper;

	// 정상동작
	public void txTest1() {
		log.info("transionTest1().. 테스트");

		userMapper.deleteAuthorities();
		userMapper.deleteUsers(); 
		// 둘이 순서바꾸면 에러난다. 1:n의 관계인데
		// n부터 지우고 1을 지워야하기때문이다.
		// 부모를 먼저 지우면 오류나기때문이다.

		UserVO user = new UserVO();
		user.setUsername("abcd");
		user.setPassword("1111");

		userMapper.insertUser(user); // 해당 유저를 넣는다.

		user.setUsername("efg");
		user.setPassword("2222");

		userMapper.insertUser(user);

	}
	
	public void txTest2() {
		log.info("transionTest2().. 테스트");

		userMapper.deleteAuthorities();
		userMapper.deleteUsers(); 

		UserVO user = new UserVO();
		user.setUsername("abcd");
		user.setPassword("1111");

		userMapper.insertUser(user); // 해당 유저를 넣는다.

		user.setUsername("efg");
		user.setPassword("2222");

		userMapper.insertUser(null); // 일부러 에러를 냄
		// null로 넣으면 실제 DB에서 생성되는 것은
		// "abcd" "1111"만 생성됌
		
		// 하나만 에러나도 전부가 롤백해야된다.

	}
	
	@Transactional
	public void txTest3() {
		log.info("transionTest3().. 테스트");

		userMapper.deleteAuthorities();
		userMapper.deleteUsers(); 

		UserVO user = new UserVO();
		user.setUsername("abcd");
		user.setPassword("1111");

		userMapper.insertUser(user);

		user.setUsername("efg");
		user.setPassword("2222");

		userMapper.insertUser(null);

	}

	   // uncheckedExeption(롤백 함)
	   @Transactional
	   public void txTest4() {
	      
	      userMapper.deleteAuthorities();
	      userMapper.deleteUsers();
	      
	      throw new RuntimeException("RuntimeException for rollback");
	   }
	   
	   // CheckedExeption 테스트(롤백 안함)
	   // 개발자가 기본적으로 에러 처리를 한다는 의미임(try catch..)
	   // 로드존슨이 그렇게 만들었음.
	   @Transactional 
	   public void txTest5() throws SQLException {
	      
	      userMapper.deleteAuthorities();
	      userMapper.deleteUsers();

	      throw new SQLException("SQLException for rollback");
	      // 반드시 예외처리를 꼭 해줘야한다. 
	      // or try catch로 잡아줘야한다.
	   }
	   
	   // @Transactional
	   // public void txTest5() {
	      
	   // try {
	   //   userMapper.deleteAuthorities();
	   //   userMapper.deleteUsers();

	   //   throw new SQLException("SQLException for rollback");
	   // } catch(Exception e) {
	   // 	개발자가 기본적으로 에러처리에 대한 코딩을 한다는 의미임
	   // }
	   
	   // @Transactional의 rollbackFor 옵션을 이용하면 Rollback이 되는 클래스를 지정가능함.
	   @Transactional(rollbackFor = SQLException.class)
	   public void txTest6() throws SQLException {
	      
	      userMapper.deleteAuthorities();
	      userMapper.deleteUsers();

	      throw new SQLException("SQLException for rollback");

	   }

	   //@Transactional의 rollbackFor 옵션을 이용하면 Rollback이 되는 클래스를 지정가능함.
	   // Exception예외로 롤백을 하려면 다음과 같이 지정하면됩니다. @Transactional(rollbackFor = Exception.class) 
	   // 여러개의 예외를 지정할 수도 있습니다. @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
	   // polymorphism 적용됌.(Exception이 부모 checked,uncheck이 자식)
	   @Transactional(rollbackFor = Exception.class)    
	   public void txTest7() throws SQLException {
		   
	      userMapper.deleteAuthorities();
	      userMapper.deleteUsers();

	      throw new SQLException("SQLException for rollback");
	   }
	   
	   // @Transactional(rollbackFor = Exception.class)    
	   // public void txTest7() throws SQLException {
	   //	try {
	   //    	userMapper.deleteAuthorities();
	   //    	userMapper.deleteUsers();
	   //
	   // } catch(Exception e) {
	   //		e.printStackTrace();
	   //    
	   // }
	   
	   
}