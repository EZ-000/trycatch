package com.ssafy.trycatch.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ssafy.trycatch.user.domain.User;
import com.ssafy.trycatch.user.domain.UserRepository;
import com.ssafy.trycatch.user.service.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private static final Logger log = LoggerFactory.getLogger(UserService.class);
  
  private final UserRepository userRepository;
  
  @Autowired
  public UserService(UserRepository userRepository) {
      this.userRepository = userRepository;
  }

	public User loadUserByUserNodeId(String nodeId){
		return userRepository.findByGithubNodeId(nodeId).get();
	}

	public void enrollUser(User user){
		userRepository.save(user);
	}

  public User findUserById(Long userId) {
      return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
  }
}