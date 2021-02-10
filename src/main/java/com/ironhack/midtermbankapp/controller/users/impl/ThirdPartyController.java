package com.ironhack.midtermbankapp.controller.users.impl;

import com.ironhack.midtermbankapp.controller.users.interfaces.IThirdPartyController;
import com.ironhack.midtermbankapp.dto.user.ThirdPartyDTO;
import com.ironhack.midtermbankapp.model.Users.ThirdParty;
import com.ironhack.midtermbankapp.service.interfaces.users.IThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ThirdPartyController implements IThirdPartyController {

   @Autowired
   private IThirdPartyService thirdPartyService;

    @Override
    @GetMapping("/third-party")
    @ResponseStatus(HttpStatus.OK)
    public List<ThirdParty> getAll() {
        return thirdPartyService.getAll();
    }

    @Override
    @GetMapping("/third-party/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ThirdParty getById(@PathVariable long id) {
        return thirdPartyService.getById(id);
    }

    @Override
    @PostMapping("/third-party")
    @ResponseStatus(HttpStatus.CREATED)
    public ThirdParty create(@RequestBody @Valid ThirdPartyDTO thirdPartyDTO) {
        return thirdPartyService.create(thirdPartyDTO);
    }
}
