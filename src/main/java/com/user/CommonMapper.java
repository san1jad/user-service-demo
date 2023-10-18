package com.user;

import com.common.dto.user.UserDTO;
import com.user.entity.Address;
import com.user.entity.Phone;
import com.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class CommonMapper {

    public void mapPhoneForUpdate(Phone newPhone, Phone existingPhone) {
        existingPhone.setPhoneNumber(newPhone.getPhoneNumber());
        existingPhone.setType(newPhone.getType());
        existingPhone.setUser(newPhone.getUser());
    }

    public void mapAddressForUpdate(Address newAddress, Address existingAdd) {
        existingAdd.setStreet(newAddress.getStreet());
        existingAdd.setCity(newAddress.getCity());
        existingAdd.setState(newAddress.getState());
        existingAdd.setPostalCode(newAddress.getPostalCode());
        existingAdd.setCountry(newAddress.getCountry());
        existingAdd.setUser(newAddress.getUser());
    }

    public void mapUserForUpdate(UserDTO updatedUserDTO, User existingUser) {
        existingUser.setUsername(updatedUserDTO.getUsername());
        existingUser.setEmail(updatedUserDTO.getEmail());
        existingUser.setPassword(updatedUserDTO.getPassword());
    }
}
