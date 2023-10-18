package com.user.serviceimpl;

import com.common.dto.user.UserDTO;
import com.common.exception.HandleNotFoundException;
import com.common.vo.user.UserVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.CommonMapper;
import com.user.entity.Address;
import com.user.entity.Phone;
import com.user.entity.User;
import com.user.repo.UserRepository;
import com.user.service.AddressService;
import com.user.service.PhoneService;
import com.user.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    AddressService addressService;

    @Autowired
    CommonMapper commonMapper;

    @Autowired
    PhoneService phoneService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(UserDTO userDTO) {
        User user = objectMapper.convertValue(userDTO, User.class);
        User savedUser = userRepository.save(user);
        //save address
        user.getAddressList()
                .forEach(address -> {
                    Address add = objectMapper.convertValue(address, Address.class);
                    add.setUser(savedUser);
                    addressService.createAddress(add);
                });
        //save phone
        user.getPhoneList()
                .forEach(phones -> {
                    Phone phone = objectMapper.convertValue(phones, Phone.class);
                    phone.setUser(savedUser);
                    phoneService.createPhone(phone);
                });
        return savedUser;
    }


    @Override
    public User updateUser(Long userId, UserDTO updatedUserDTO) {
        return userRepository.findById(userId)
                .map(existingUser -> {
                    commonMapper.mapUserForUpdate(updatedUserDTO, existingUser);
                    User savedUser = userRepository.save(existingUser);
                    //update address
                    updatedUserDTO.getAddressList().forEach(address -> {
                        Address add = objectMapper.convertValue(address, Address.class);
                        add.setUser(savedUser);
                        addressService.updateAddress(add);
                    });
                    //update phone
                    updatedUserDTO.getPhoneList().forEach(phone -> {
                        Phone ph = objectMapper.convertValue(phone, Phone.class);
                        ph.setUser(savedUser);
                        phoneService.updatePhone(ph);
                    });
                    return objectMapper.convertValue(updatedUserDTO, User.class);
                }).orElseThrow(() -> new HandleNotFoundException("User not found"));
    }


    @Override
    public ResponseEntity<String> deleteUser(Long userId) {
        userRepository.deleteById(userId);
        return new ResponseEntity<>("User deleted successfully!!", HttpStatus.OK);
    }

    @Override
    public UserVO getUserById(Long userId) {
        return userRepository.findById(userId)
                .map(user -> objectMapper.convertValue(user,UserVO.class))
                .orElseThrow(() -> new HandleNotFoundException("User not found"));
    }

    @Override
    public List<UserVO> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(user -> objectMapper.convertValue(user,UserVO.class))
                .collect(Collectors.toList());
    }

}
