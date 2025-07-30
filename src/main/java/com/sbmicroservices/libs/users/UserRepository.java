package com.sbmicroservices.libs.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<Users, Long> {



    Optional<Users> findByUserEmailOrUserName(String email, String userName);

    Optional<Users> findByUserName(String userName);
}
