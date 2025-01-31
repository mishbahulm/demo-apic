package id.co.mii.mockup.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import id.co.mii.mockup.dto.request.User;
import id.co.mii.mockup.dto.response.BaseResponse;
import id.co.mii.mockup.dto.response.UserResponse;
import id.co.mii.mockup.dto.response.UsersResponse;

@RestController
@RequestMapping("/users")
public class UserController {

    private final Map<Long, User> users = new HashMap<>();
    private final AtomicLong userIdCounter = new AtomicLong(1);
    
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> createUser(@RequestBody User user) {
        Long id = userIdCounter.getAndIncrement();
        user.setId(id);
        users.put(id, user);

        UserResponse response = buildResponse(user, "2012500", "Successful", HttpStatus.CREATED);

        return new ResponseEntity<>(response, response.getResponseStatus());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> getAllUsers() {
        
        UsersResponse response = new UsersResponse();

        response.setUsersData(users.values());
        response.setResponseCode("2003000");
        response.setResponseMessage("Success");
        response.setResponseStatus(HttpStatus.OK);

        return new ResponseEntity<>(response, response.getResponseStatus());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> getUserById(@PathVariable Long id) {

        if (users.containsKey(id)) {
            UserResponse response = buildResponse(users.get(id), "2002400", "Successful", HttpStatus.OK);
            return new ResponseEntity<>(response, response.getResponseStatus());
        }

        UserResponse response = buildResponse(null, "4040116", "User id "+id+" not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, response.getResponseStatus());
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> searchUser(@RequestParam Long id) {
        if (users.containsKey(id)) {
            UserResponse response = buildResponse(users.get(id), "2002400", "Successful", HttpStatus.OK);
            return new ResponseEntity<>(response, response.getResponseStatus());
        }

        UserResponse response = buildResponse(null, "4040116", "User id "+id+" not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, response.getResponseStatus());
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        if (users.containsKey(id)) {
            updatedUser.setId(id);
            users.put(id, updatedUser);

            UserResponse response = buildResponse(updatedUser, "2022600", "Successful", HttpStatus.ACCEPTED);

            return new ResponseEntity<>(response, response.getResponseStatus());
        }

        UserResponse response = buildResponse(null, "4040116", "User id "+id+" not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, response.getResponseStatus());
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> deleteUser(@PathVariable Long id) {
        if (users.containsKey(id)) {
            User deletedUser = users.get(id);
            if (users.remove(id) != null) {                
                UserResponse response = buildResponse(deletedUser, "2002700", "User deleted successfully", HttpStatus.OK);
                return new ResponseEntity<>(response, response.getResponseStatus());
            }
        }
        
        UserResponse response = buildResponse(null, "4040116", "User id "+id+" not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, response.getResponseStatus());
    }

    private UserResponse buildResponse(User user, String code, String message, HttpStatus httpStatus) {
        UserResponse response = new UserResponse();

        response.setUserData(user);
        response.setResponseCode(code);
        response.setResponseMessage(message);
        response.setResponseStatus(httpStatus);
        return response;
    }
}
