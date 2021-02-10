package com.ironhack.midtermbankapp.service.interfaces.users;

import com.ironhack.midtermbankapp.dto.user.ThirdPartyDTO;
import com.ironhack.midtermbankapp.model.Users.ThirdParty;

import java.util.List;

public interface IThirdPartyService {
    List<ThirdParty> getAll();
    ThirdParty getById(long id);
    ThirdParty create(ThirdPartyDTO thirdPartyDTO);
}
