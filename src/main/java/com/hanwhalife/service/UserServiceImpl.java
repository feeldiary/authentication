package com.hanwhalife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.hanwhalife.entity.UserInfo;
import com.hanwhalife.repository.UserRepository;

public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserInfo saveUser(UserInfo userInfo) {
		// TODO Auto-generated method stub

		return userRepository.save(userInfo);
		
	}

	
	public UserInfo loadUserByUserId(String userId) throws UsernameNotFoundException 
	{
		UserInfo userInfo = userRepository.findByUserId(userId);
		System.out.println("userInfo: "+userInfo.toString());

		if(true) {
		} else {
			throw new UsernameNotFoundException("User not found with userId: " + userId);
		}
		
		return userInfo;
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
 
}
