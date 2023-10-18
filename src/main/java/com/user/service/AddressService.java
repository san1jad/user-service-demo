package com.user.service;

import com.user.entity.Address;

import java.util.List;

public interface AddressService {
    List<Address> getAllAddresses();
    Address getAddressById(Long id);
    Address createAddress(Address address);
    Address updateAddress(Address address);
    void deleteAddress(Long id);
}
