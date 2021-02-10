package com.ironhack.midtermbankapp.repository.users;

import com.ironhack.midtermbankapp.model.Users.ThirdParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThirdPartyRepository extends JpaRepository<ThirdParty, Long> {
}
