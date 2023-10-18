package com.user.service;

import com.common.dto.user.UserDTO;
import com.common.vo.user.UserVO;
import com.user.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    User createUser(UserDTO userDTO);

    User updateUser(Long userId, UserDTO updatedUserDTO);

    ResponseEntity<String> deleteUser(Long userId);

    UserVO getUserById(Long userId);

    List<UserVO> getAllUsers();
}