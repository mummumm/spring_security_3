package edu.bit.ex.vo;

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
public class MemberUser extends User {

	private MemberVO member; // User안에 get함수(authList와 회원정보 가지고있음)
	   
		//기본적으로 부모의 생성자를 호출해야만 정상적으로 작동
		// principal.member.username 이런식으로 들어옴(get함수)
		// (User를 확장하면 MemberUser에 정보가 다 들어간다.) 
	
	public MemberUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public MemberUser(MemberVO memberVO) {

		super(memberVO.getUsername(), memberVO.getPassword(), getAuth(memberVO));

		this.member = memberVO;
	}

	// 유저가 갖고 있는 권한 목록
	public static Collection<? extends GrantedAuthority> getAuth(MemberVO memberVO) {

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (AuthVO auth : memberVO.getAuthList()) {
			authorities.add(new SimpleGrantedAuthority(auth.getAuthority()));
		}

		return authorities;
		// <GrantedAuthority>안에 auth를 넣어서 
		// auth.getAuthority()을 이용 super를 통해 부모에게 넣어준다. 
		// principal 객체가 됌.
	}
}
