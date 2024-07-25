package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.exception.UserNotFoundException;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers();

    List<UserDto> getUsersByName(String name) throws UserNotFoundException;

    UserDto getUserByPhone(String phoneNumber) throws UserNotFoundException;

    UserDto getUserByEmail(String email) throws UserNotFoundException;

    UserDto getUserById(Integer id) throws UserNotFoundException;

    void addUser(UserDto userDto);

    boolean deleteUser(Integer id);

    List<UserDto> getUsersRespondedToVacancy(Integer vacancyId);

    void applyForVacancy(Long vacancyId);
}

