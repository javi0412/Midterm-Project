package com.ironhack.midtermbankapp.repository.users;

import com.ironhack.midtermbankapp.model.Users.ThirdParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThirdPartyRepository extends JpaRepository<ThirdParty, Long> {
    Optional<ThirdParty> findByHashedKey(Integer hashedKey);
}
