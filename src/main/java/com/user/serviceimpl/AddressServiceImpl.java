package com.user.serviceimpl;

import com.common.exception.HandleNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.CommonMapper;
import com.user.entity.Address;
import com.user.repo.AddressRepository;
import com.user.service.AddressService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    CommonMapper commonMapper;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    @Override
    public Address getAddressById(Long id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        return optionalAddress.orElseThrow(() -> new HandleNotFoundException("Address not found"));
    }

    @Override
    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address updateAddress(Address newAddress) {
        return addressRepository.findById(newAddress.getId()).map(existingAdd -> {
                    commonMapper.mapAddressForUpdate(newAddress, existingAdd);
                    return addressRepository.save(existingAdd);
                }
        ).orElseThrow(() -> new HandleNotFoundException("Address for user is not found"));

    }

    @Override
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
}
