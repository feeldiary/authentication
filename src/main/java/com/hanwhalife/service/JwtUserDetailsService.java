package com.hanwhalife.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.GrantAdmin;
import org.springframework.security.core.userdetails.GrantUser;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hanwhalife.entity.UserInfo;
import com.hanwhalife.repository.UserRepository;


@Service
public class JwtUserDetailsService implements UserDetailsService 
{
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userNm) throws UsernameNotFoundException 
	{
		ArrayList<GrantedAuthority> aList = new ArrayList<GrantedAuthority>();
		aList.add(new GrantAdmin());
		aList.add(new GrantUser());

		if("admin".equals(userNm)) {
			return new UserInfo("admin", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6", new ArrayList<>());
//							, aList);
		} else {
			throw new UsernameNotFoundException("User not found with username: " + userNm);
		}
	}
	
	public UserInfo loadUserByUserId(String userId) throws UsernameNotFoundException 
	{
		
		List<UserInfo> userList = userRepository.findByUserId(userId);
		
		
		if(true) {

		} else {
			throw new UsernameNotFoundException("User not found with userId: " + userId);
		}
		
		return null;
	}
	
	
	public UserInfo findByUserIdAndPassword(String userId, String password) throws UsernameNotFoundException 
	{
		UserInfo userInfo = userRepository.findByUserIdAndPassword(userId, password);
		
		if(userInfo != null) {
			return userInfo;			
			
		}else {
			throw new UsernameNotFoundException("User not found with userId: " + userId);
		}
	}
 

	public void save(UserInfo userInfo) {
		userRepository.save(userInfo);
		System.out.println("userInfo : "+userInfo.toString());
	}
	
	

}
