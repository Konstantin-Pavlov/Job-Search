package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.exception.UserNotFoundException;
import kg.attractor.jobsearch.mapper.UserMapper;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.repository.UserRepository;
import kg.attractor.jobsearch.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    UserMapper userMapper = UserMapper.INSTANCE;

    @Override
    public List<UserDto> getUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserDto).toList();
    }

    @Override
    public UserDto getUserById(Integer id) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(id).orElseThrow(() -> {
            log.error("Can't find user with ID: {}", id);
            return new NoSuchElementException("Can't find user with ID: " + id);
        }));
        log.info("User found with ID: {}", id);
        return userMapper.toUserDto(user.get());
    }

    @Override
    public List<UserDto> getUsersByName(String name) throws UserNotFoundException {
        List<User> users = userRepository.findByName(name);
        if (users.isEmpty()) {
            log.error("Can't find user with name: {}", name);
            throw new UserNotFoundException("Users with name " + name + " not found");
        }
        log.info("User found with name: {}", name);
        return users.stream().map(userMapper::toUserDto).toList();
    }

    @Override
    public UserDto getUserByPhone(String phoneNumber) throws UserNotFoundException {
        Optional<User> user = userRepository.findByPhoneNumber(phoneNumber);
        if (user.isEmpty()) {
            log.error("Can't find user with phone number: {}", phoneNumber);
            throw new UserNotFoundException("Users with phone number " + phoneNumber + " not found");
        }
        log.info("User found with phone number: {}", phoneNumber);
        return userMapper.toUserDto(user.get());
    }

    @Override
    public UserDto getUserByEmail(String email) throws UserNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            log.error("Can't find user with email: {}", email);
            throw new UserNotFoundException("Users with email " + email + " not found");
        }
        log.info("User found with email: {}", email);
        return userMapper.toUserDto(user.get());
    }

    @Override
    public List<UserDto> getUsersRespondedToVacancy(Integer vacancyId) {
        List<User> users = userRepository.findUsersRespondedToVacancy(vacancyId);
        if (users.isEmpty()) {
            log.error("Can't find users with vacancy id {}", vacancyId);
        } else {
            log.info("found users with vacancy id {}", vacancyId);
        }
        return users.stream().map(userMapper::toUserDto).toList();
    }

    @Override
    public void addUser(UserDto userDto) {
        User user = userMapper.toUser(userDto);
        userRepository.save(user);
        if (user.getAccountType().equals("applicant")) {
            log.info("added applicant with email {}", user.getEmail());
        } else {
            log.info("added employer with email {}", user.getEmail());
        }
    }

    @Override
    public boolean deleteUser(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            log.error("not possible to delete user because user with ID: {} nor found", id);
            return false;
        }
        userRepository.delete(user.get());
        return true;
    }

    @Override
    public void applyForVacancy(Long vacancyId) {
// todo - implement
    }

}
