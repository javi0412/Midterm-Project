package com.ironhack.midtermbankapp.service.impl.users;

import com.ironhack.midtermbankapp.dto.user.ThirdPartyDTO;
import com.ironhack.midtermbankapp.model.Users.ThirdParty;
import com.ironhack.midtermbankapp.repository.users.ThirdPartyRepository;
import com.ironhack.midtermbankapp.service.interfaces.users.IThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ThirdPartyService implements IThirdPartyService {

    @Autowired
    private ThirdPartyRepository thirdPartyRepository;

    @Override
    public List<ThirdParty> getAll() {
        return thirdPartyRepository.findAll();
    }

    @Override
    public ThirdParty getById(long id) {
        if(!thirdPartyRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Third Party user not found");
        }
        return thirdPartyRepository.findById(id).get();
    }

    @Override
    public ThirdParty create(ThirdPartyDTO thirdPartyDTO) {
        ThirdParty thirdParty = new ThirdParty();
        thirdParty.setName(thirdPartyDTO.getName());
        thirdParty.setHashedKey(thirdPartyDTO.getHashedKey());
        return thirdPartyRepository.save(thirdParty);
    }
}
