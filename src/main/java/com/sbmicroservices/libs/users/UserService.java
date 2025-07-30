package com.sbmicroservices.libs.users;


import com.sbmicroservices.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(Users users) {
        userRepository.save(users);
    }

    public Users getUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with provided id"));
    }

    public Optional<Users> getUserByUserEmailOrUserName(String id, String userName) {
        return userRepository.findByUserEmailOrUserName(id, userName);
    }


    public Optional<Users> getByUserName(String userName){
        return userRepository.findByUserName(userName);
    }



}
