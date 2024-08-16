package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.UserWithAvatarFileDto;
import kg.attractor.jobsearch.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    List<UserDto> getUsers();

    UserDto getUserByName(String name) throws UserNotFoundException;

    UserDto getUserByPhone(String phoneNumber) throws UserNotFoundException;

    UserDto getUserByEmail(String email) throws UserNotFoundException;

    UserDto getUserById(long id) throws UserNotFoundException;

    void addUser(UserDto userDto);
    void addUserWithAvatar(UserWithAvatarFileDto userDtoWithAvatarUploading) throws UserNotFoundException, IOException;

    boolean deleteUser(Long id);

    List<UserDto> getUsersRespondedToVacancy(Integer vacancyId);

    void applyForVacancy(Long vacancyId, ResumeDto resumeDto);

    void saveAvatar(Integer userId, MultipartFile avatar) throws IOException, UserNotFoundException;

    ResponseEntity<?> getAvatar(Integer userId) throws UserNotFoundException;
}

