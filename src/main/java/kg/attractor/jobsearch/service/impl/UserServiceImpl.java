package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.ResumeDao;
import kg.attractor.jobsearch.dao.UserDao;
import kg.attractor.jobsearch.dao.VacancyDao;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.UserDtoWithAvatarUploading;
import kg.attractor.jobsearch.exception.UserNotFoundException;
import kg.attractor.jobsearch.mapper.CustomUserMapper;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final VacancyDao vacancyDao;
    private final ResumeDao resumeDao;
    private final ResumeService resumeService;
    private final String UPLOAD_DIR = "avatars/";
//    private final UserMapper userMapper = UserMapper.INSTANCE;


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
                .enabled(e.isEnabled())
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
    public UserDto getUserById(long id) {//throws UserNotFoundException {
        User user = userDao.getUserById(id).orElseThrow(
                () -> {
                    log.error("Can't find user with ID: " + id);
                    return new NoSuchElementException("Can't find user with ID: " + id);
                }
        );
        log.info("User found with ID: " + id);
        return UserDto.builder().
                id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .email(user.getEmail())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .avatar(user.getAvatar())
                .accountType(user.getAccountType())
                .build();
    }

    @Override
    public void addUser(UserDto userDto) {
        if (userDto.getAvatar() == null || userDto.getAvatar().isEmpty()) {
            userDto.setAvatar("ava.png");
        }
        User user = new User();
        user.setName(userDto.getName());
        user.setAge(userDto.getAge());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setAvatar(userDto.getAvatar());
        user.setAccountType(userDto.getAccountType());

        userDao.addUser(user);
        if (user.getAccountType().equals("applicant")) {
            log.info("added applicant with email {}", user.getEmail());
        } else {
            log.info("added employer with email {}", user.getEmail());
        }
    }

    @Override
    public void addUserWithAvatar(UserDtoWithAvatarUploading userDtoWithAvatarUploading) throws UserNotFoundException, IOException {
        if (userDtoWithAvatarUploading.getAvatar().isEmpty()) {
            addUser(CustomUserMapper.toUserDto(userDtoWithAvatarUploading));
        } else {
            addUser(CustomUserMapper.toUserDto(userDtoWithAvatarUploading));
            Optional<User> user = userDao.getUserByEmail(userDtoWithAvatarUploading.getEmail());
            if (user.isPresent()) {
                saveAvatar(
                        user.get().getId(),
                        userDtoWithAvatarUploading.getAvatar()
                );
            } else {
                log.error("Can't find user with email: {}", userDtoWithAvatarUploading.getEmail());
                throw new UserNotFoundException("Can't find user with email: " + userDtoWithAvatarUploading.getEmail());
            }
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

    @Override
    public List<UserDto> getUsersRespondedToVacancy(Integer vacancyId) {
        List<User> users = userDao.getUsersRespondedToVacancy(vacancyId);
        List<UserDto> dtos = users.stream()
                .map(user -> UserDto.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .age(user.getAge())
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .phoneNumber(user.getPhoneNumber())
                        .avatar(user.getAvatar())
                        .accountType(user.getAccountType())
                        .build())
                .collect(Collectors.toList());
        if (dtos.isEmpty()) {
            log.error("Can't find users with vacancy id {}", vacancyId);
        } else {
            log.info("found users with vacancy id {}", vacancyId);
        }
        return dtos;
    }

    @Override
    public void applyForVacancy(Long vacancyId, ResumeDto resumeDto) {
        Resume resume = resumeDao.getResumeById(resumeDto.getApplicantId()).orElseThrow(
                () -> new UsernameNotFoundException(
                        "Can't find resume with applicant id: " + resumeDto.getApplicantId()
                )
        );
        Vacancy vacancy = vacancyDao.getVacancyById(vacancyId).orElseThrow(
                () -> new NoSuchElementException("Can't find vacancy with ID: " + vacancyId)
        );
        vacancyDao.applyForVacancy(resume.getId(), vacancy.getId());
        log.info("apply for vacancy {} successfully", vacancy.getName());
    }

    @Override
    public void saveAvatar(Integer userId, MultipartFile avatar) throws IOException, UserNotFoundException {
        if (!avatar.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + avatar.getOriginalFilename();
            // in users table avatar field length is only 45
            // if it's more - we get sql exception
            if (fileName.length() > 45) {
                fileName = fileName.substring(fileName.length() - 45);
            }
            Path filePath = Paths.get(UPLOAD_DIR, fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, avatar.getBytes());

            User user = userDao.getUserById(userId).orElseThrow(
                    () -> new UserNotFoundException("can't find user with id: " + userId));

            userDao.updateAvatar(user.getId(), fileName);
        } else {
            throw new IOException("File is empty");
        }
    }

    @Override
    public ResponseEntity<?> getAvatar(Integer userId) {
        Optional<User> user = userDao.getUserById(userId);
        if (user.isPresent()) {
            try {
                byte[] image = Files.readAllBytes(Paths.get(UPLOAD_DIR + user.get().getAvatar()));
                Resource resource = new ByteArrayResource(image);
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + user.get().getAvatar() + "\"")
                        .contentLength(resource.contentLength())
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("avatar not found");
            }
        }
        return null;
    }

    private void saveDataToUserDto(UserDto userDto, UserDtoWithAvatarUploading userDtoWithAvatarUploading) {
    }

}
