package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.UserDao;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.exception.UserNotFoundException;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Override
    public List<UserDto> getUsers() {
        List<User> users = userDao.getUser();
        List<UserDto> dtos = new ArrayList<>();
        users.forEach(e -> dtos.add(UserDto.builder()
                .id(e.getId())
                .name(e.getName())
                .age(e.getAge())
                .email(e.getEmail())
                .password(e.getPassword())
                .phoneNumber(e.getPhoneNumber())
                .avatar(e.getAvatar())
                .accountType(e.getAccountType())
                .build()));
        return dtos;
    }

    @Override
    public UserDto getUserByName(String name) throws UserNotFoundException {
        User user = userDao.getUserByName(name).orElseThrow(
                () -> new UserNotFoundException("Can't find user with name: " + name)
        );
        return UserDto.builder().
                id(user.getId())
                .name(user.getName())
                .password(user.getPassword())
                .build();
    }

    @Override
    public UserDto getUserByPhone(String phoneNumber) throws UserNotFoundException {
        User user = userDao.getUserByPhone(phoneNumber).orElseThrow(
                () -> new UserNotFoundException("Can't find user with phone: " + phoneNumber)
        );
        return UserDto.builder().
                id(user.getId())
                .name(user.getName())
                .password(user.getPassword())
                .build();
    }

    @Override
    public UserDto getUserByEmail(String email) throws UserNotFoundException {
        User user = userDao.getUserByEmail(email).orElseThrow(
                () -> new UserNotFoundException("Can't find user with email: " + email)
        );
        return UserDto.builder().
                id(user.getId())
                .name(user.getName())
                .password(user.getPassword())
                .build();
    }


    @Override
    public UserDto getUserById(int id) throws UserNotFoundException {
        User user = userDao.getUserById(id).orElseThrow(
                () -> new UserNotFoundException("Can't find user with ID: " + id)
        );
        return UserDto.builder().
                id(user.getId())
                .name(user.getName())
                .password(user.getPassword())
                .build();
    }

    @Override
    public void addUser(UserDto userDto) {
        User user = new User();
//        User.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setAge(userDto.getAge());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setAvatar(userDto.getAvatar());
        user.setAccountType(userDto.getAccountType());

        userDao.addUser(user);
        if (user.getAccountType().equals("applicant")) {
            log.info("added applicant with email " + user.getEmail());
        } else {
            log.info("added employer with email " + user.getEmail());
        }
    }

    @Override
    public boolean deleteUser(Long id) {
        Optional<User> user = userDao.getUserById(id);
        if (user.isPresent()) {
            userDao.delete(id);
            log.info("user deleted: " + user.get().getName());
            return true;
        }
        log.info(String.format("user with id %d not found", id));
        return false;
    }

}
