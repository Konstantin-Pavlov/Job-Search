package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.exception.UserNotFoundException;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers();

    UserDto getUserByName(String name) throws UserNotFoundException;

    UserDto getUserByPhone(String phoneNumber) throws UserNotFoundException;

    UserDto getUserByEmail(String email) throws UserNotFoundException;

    UserDto getUserById(long id); //throws UserNotFoundException;

    void addUser(UserDto userDto);

    boolean deleteUser(Long id);

    List<UserDto> getUsersRespondedToVacancy(Integer vacancyId);
}

