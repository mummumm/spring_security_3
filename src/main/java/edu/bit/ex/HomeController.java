package edu.bit.ex;

import java.security.Principal;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.bit.ex.vo.EmpUser;
import lombok.extern.log4j.Log4j;

@Log4j
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	/*
	 * @GetMapping("/login/loginForm") public String loginForm() {
	 * log.info("Welcome Login Form"); return "login/loginForm2"; }
	 */
	
	@GetMapping("/login/loginForm")
	public String loginForm() {
		log.info("Welcome Login Form");
		return "login/loginForm";
	}
	
	@GetMapping("/user/userHome")
	public void userHome() {
		log.info("userHome");
	}
	
	@GetMapping("/admin/adminHome")
	public void adminHome() {
		log.info("adminHome");
	}
	
	@GetMapping("/login/accessDenied")
	public void accessDenied() {
		log.info("Access is Denied");
	}
	
	@GetMapping("/loginInfo")
	public String loginInfo(Principal principal) {
		log.info("loginInfo...");
		
		// 1. Controller를 통하여 principal객체로 가져오는 방법
		String userId = principal.getName();
		System.out.println("유저 아이디 : " + principal.getName());
		
		// 2. SpringContextHolder를 통하여 가져오는 방법(일반적인 빈에서 사용할 수 있음)
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		userId = auth.getName();
		System.out.println("유저 아이디 : " + userId);
		
		// 3. UserDetails 갖고오기
		UserDetails userDetails = (UserDetails) auth.getAuthorities();
		System.out.println("유저 아이디 : " + userDetails.getUsername());

		// 4. EmpUser 꺼내오기
		EmpUser empUser = (EmpUser) userDetails;
		// userDetails에서 상속관계로 받아왔다.
		System.out.println(empUser.getEmp());
		
		// 5. User클래스로 변환하여 가져오는 방법
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		userId = user.getUsername();
		System.out.println("유저 아이디 : " + userId);
		
		return "home";
		
		// Session -> Spring Security -> SpringContextHoler -> SecurityContext
		// -> Authentication -> Principal -> UserDetails -> User -> EmpUser
		// 순으로 상속이 된다.(8번)
		// (Session : 자기자신페이지만 접근 가능)
		// 이러한 특성때문에 Session이 세션시간동안 모든 Controller나 jsp에 접근가능하다.		 
		// 시간 만료되면 정보 다 사라짐.
	}
	
	
	
}
