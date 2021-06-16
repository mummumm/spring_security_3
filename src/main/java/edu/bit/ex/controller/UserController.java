package edu.bit.ex.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.bit.ex.service.UserService;
import edu.bit.ex.vo.UserVO;
import lombok.extern.log4j.Log4j;


@Log4j
@Controller
public class UserController {
	
	@Autowired
	private UserService userSerivce;
	
	@GetMapping("/user/userForm")
	public void userForm() {
		log.info("userForm() ..");
	}
	
	@PostMapping("/user/addUser")
	public String addUser(UserVO userVO) {
		log.info("user/addUser");
		userSerivce.addUser(userVO);

		return "redirect:/";
	}

	
	
}
