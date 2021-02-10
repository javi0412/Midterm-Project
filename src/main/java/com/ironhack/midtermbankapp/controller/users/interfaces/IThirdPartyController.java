package com.ironhack.midtermbankapp.controller.users.interfaces;

import com.ironhack.midtermbankapp.dto.user.ThirdPartyDTO;
import com.ironhack.midtermbankapp.model.Users.ThirdParty;

import java.util.List;

public interface IThirdPartyController {
    List<ThirdParty> getAll();
    ThirdParty getById(long id);
    ThirdParty create(ThirdPartyDTO thirdPartyDTO);
}
