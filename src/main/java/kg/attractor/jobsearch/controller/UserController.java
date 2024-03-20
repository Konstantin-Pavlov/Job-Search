package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.exception.UserNotFoundException;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    //http://localhost:8089/users/name?name=John
    @GetMapping("name")
    private ResponseEntity<?> getUserByName(
            @RequestParam(name = "name")
            String name) {
        try {
            UserDto user = userService.getUserByName(name);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //   http://localhost:8089/users/phone?phone=1234567890
    @GetMapping("phone")
    private ResponseEntity<?> getUserByPhone(
            @RequestParam("phone")
            String phone) {
        try {
            UserDto user = userService.getUserByPhone(phone);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //http://localhost:8089/users/email?email=jane@example.com
    @GetMapping("email")
    private ResponseEntity<?> getUserByEmail(
            @RequestParam("email") String email) {
        try {
            UserDto user = userService.getUserByEmail(email);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("add")
    public HttpStatus add(@RequestBody UserDto userDto) {
        userService.addUser(userDto);
        return HttpStatus.OK;
    }


}