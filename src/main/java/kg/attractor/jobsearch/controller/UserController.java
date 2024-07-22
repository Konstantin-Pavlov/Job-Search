package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.exception.UserNotFoundException;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("{id}")
    public ResponseEntity<?> getUserById(@PathVariable long id) {
//        try {
//            return ResponseEntity.ok(userService.getUserById(id));
//        } catch (UserNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//        }
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // http://localhost:8089/users/get-users-by-vacancy/2
    @GetMapping("get-users-by-vacancy/{vacancy_id}")
    public ResponseEntity<?> getUsersRespondedToVacancy(@PathVariable Integer vacancy_id) {
        List<UserDto> users = userService.getUsersRespondedToVacancy(vacancy_id);
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(String.format("users with vacancy id %d not found", vacancy_id));
        }
        return ResponseEntity.ok(users);
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
    public ResponseEntity<?> add(@Valid @RequestBody UserDto userDto) {
        userService.addUser(userDto);
        return ResponseEntity.ok("user is valid");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.deleteUser(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}


