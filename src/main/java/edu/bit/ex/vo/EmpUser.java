package edu.bit.ex.vo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class EmpUser extends User {

	private EmpVO emp; 	   
	
	public EmpUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities); // 디폴트이므로 무조건!
		// 변수명은 안바꿔줘도 상관없다.
	}

	// 생성자
	public EmpUser(EmpVO empVO) {

		super(empVO.getEname(), empVO.getEmpno(), getAuth(empVO));
		// 디폴트이므로 무조건!
		// username, password, authority이다.
		this.emp = empVO;
	}
	
	// 유저가 갖고있는 권한 목록
	// EmpUser에서 getAuth(empVO)함수 호출하려면 static으로 가져와야한다.
	public static Collection<? extends GrantedAuthority> getAuth(EmpVO empVO) {

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		authorities.add(new SimpleGrantedAuthority(empVO.getAuthority()));
		// 하나만 가져오니까 for문 안가져와도된다.
		
		return authorities;
	}
}
