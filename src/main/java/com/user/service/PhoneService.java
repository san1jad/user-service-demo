package com.user.service;

import com.user.entity.Phone;

import java.util.List;

public interface PhoneService {
    List<Phone> getAllPhones();
    Phone getPhoneById(Long id);
    Phone createPhone(Phone phone);
    Phone updatePhone(Phone phone);
    void deletePhone(Long id);
}
