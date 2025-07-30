package com.sbmicroservices.controller;


import com.sbmicroservices.exceptions.UserAlreadyExistsException;
import com.sbmicroservices.exceptions.UserNotFoundException;
import com.sbmicroservices.libs.users.UserService;
import com.sbmicroservices.libs.users.Users;
import com.sbmicroservices.models.request.SignUpUserBody;
import com.sbmicroservices.models.response.SuccessResponse;
import jakarta.ws.rs.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/users")
@Slf4j
public class UserController {


    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<SuccessResponse> signUp(@RequestBody SignUpUserBody body) throws UserAlreadyExistsException {
        log.info("Finding user with email :: {}", body.getUserEmail());
        Optional<Users> existingUser = userService.getUserByUserEmailOrUserName(body.getUserEmail(), body.getUserName());
        log.info("User found :: {}", existingUser.isPresent());
        if(existingUser.isPresent()){
            log.info("User already present ::");
            throw new UserAlreadyExistsException("User already exists with provided email or username.");
        }
        Users users = new Users();
        users.setUserEmail(body.getUserEmail());
        users.setUserName(body.getUserName());
        String encode = passwordEncoder.encode(body.getPassword());
        log.info("Encoded password : {}", encode);
        users.setPassword(encode);
        users.setContactNo(body.getContactNo());

        log.info("saving user with userName :: {}", users.getUserName());
        userService.saveUser(users);

        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK.value(), "User created successfully", true));
    }

    @GetMapping("/getUser")
    public ResponseEntity<Users> getUser(@PathParam(value = "username") String userName) throws UserNotFoundException{
        log.info("Fetching user with userName :: {}", userName);
        Users users = userService.getByUserName(userName).orElseThrow(() -> new UserNotFoundException("User not found with userName : " + userName));
        return ResponseEntity.ok(users);
    }
}
