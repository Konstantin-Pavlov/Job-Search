package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.exception.UserNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    void uploadAvatar(Integer userId, MultipartFile file) throws IOException;

    Resource getAvatarFileResource(Integer userId);

    String getContentType(Resource resource);
}

