package com.user.serviceimpl;

import com.common.exception.HandleNotFoundException;
import com.user.CommonMapper;
import com.user.entity.Phone;
import com.user.repo.PhoneRepository;
import com.user.service.PhoneService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PhoneServiceImpl implements PhoneService {

    private final PhoneRepository phoneRepository;

    @Autowired
    CommonMapper commonMapper;

    @Autowired
    public PhoneServiceImpl(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    @Override
    public List<Phone> getAllPhones() {
        return phoneRepository.findAll();
    }

    @Override
    public Phone getPhoneById(Long id) {
        Optional<Phone> optionalPhone = phoneRepository.findById(id);
        return optionalPhone.orElseThrow(() -> new HandleNotFoundException("Phone not found"));
    }

    @Override
    public Phone createPhone(Phone phone) {
        return phoneRepository.save(phone);
    }

    @Override
    public Phone updatePhone(Phone newPhone) {

        return phoneRepository.findById(newPhone.getId())
                .map(existingPhone -> {
                            commonMapper.mapPhoneForUpdate(newPhone, existingPhone);
                            return phoneRepository.save(existingPhone);
                        }
                ).orElseThrow(() -> new HandleNotFoundException("Phone for user is not found"));

    }


    @Override
    public void deletePhone(Long id) {
        phoneRepository.deleteById(id);
    }
}
