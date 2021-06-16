package edu.bit.ex.service;

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
public class LoginService {

	@Inject
	private UserMapper userMapper;

	public UserVO selectUser(UserVO userVO) throws Exception {
		log.info("selectUser ..");
		return userMapper.getUser(userVO);
	}

}