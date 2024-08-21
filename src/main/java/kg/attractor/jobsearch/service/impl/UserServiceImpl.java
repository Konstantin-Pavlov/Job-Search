package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.ResumeDao;
import kg.attractor.jobsearch.dao.UserDao;
import kg.attractor.jobsearch.dao.VacancyDao;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.UserWithAvatarFileDto;
import kg.attractor.jobsearch.exception.UserNotFoundException;
import kg.attractor.jobsearch.mapper.UserMapper;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.repository.ResumeRepository;
import kg.attractor.jobsearch.repository.UserRepository;
import kg.attractor.jobsearch.repository.VacancyRepository;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.util.ConsoleColors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final VacancyDao vacancyDao;
    private final ResumeDao resumeDao;

    private final UserRepository userRepository;
    private final VacancyRepository vacancyRepository;
    private final ResumeRepository resumeRepository;

    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Value("${app.avatar_dir}")
    private String AVATAR_DIR;

//    private final String UPLOAD_DIR = "avatars";

    @Override
    public List<UserDto> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    @Override
    public List<UserDto> getUsersByName(String name) {
        return userRepository.findByName(name)
                .stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    @Override
    public UserDto getUserByPhone(String phoneNumber) throws UserNotFoundException {
        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(
                () -> new UserNotFoundException("Can't find user with phone: " + phoneNumber)
        );
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("Can't find user with email: " + email)
        );
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto getUserById(Integer id) {//throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Can't find user with ID: {}", id);
                    return new NoSuchElementException("Can't find user with ID: " + id);
                }
        );
        log.info("User found with ID: {}", id);
        return userMapper.toUserDto(user);
    }

    @Override
    public void addUser(UserDto userDto) {
        if (userDto.getAvatar() == null || userDto.getAvatar().isEmpty()) {
            userDto.setAvatar("default_user_avatar.png");
            log.info(ConsoleColors.YELLOW +
                            "user with email {} didn't choose avatar, so default avatar is set" +
                            ConsoleColors.RESET,
                    userDto.getEmail());
        }
        User user = userMapper.toUser(userDto);
        userDao.addUser(user);
        if (user.getAccountType().equals("applicant")) {
            log.info("added applicant with email {}", user.getEmail());
        } else {
            log.info("added employer with email {}", user.getEmail());
        }
    }

    @Override
    public void addUserWithAvatar(UserWithAvatarFileDto userWithAvatarFileDto) throws UserNotFoundException, IOException {
        if (userWithAvatarFileDto.getAvatar().isEmpty()) {
            addUser(userMapper.toUserDto(userWithAvatarFileDto));
        } else {
            addUser(userMapper.toUserDto(userWithAvatarFileDto));
            Optional<User> user = userRepository.findByEmail(userWithAvatarFileDto.getEmail());
            if (user.isPresent()) {
                saveAvatar(
                        user.get().getId(),
                        userWithAvatarFileDto.getAvatar()
                );
                UserDto userWithUpdatedAvatar = getUserById(user.get().getId());
                log.info(ConsoleColors.YELLOW_BACKGROUND + "saved avatar {}" + ConsoleColors.RESET, userWithUpdatedAvatar.getAvatar());
            } else {
                log.error("Can't find user with email: {}", userWithAvatarFileDto.getEmail());
                throw new UserNotFoundException("Can't find user with email: " + userWithAvatarFileDto.getEmail());
            }
        }
    }

    @Override
    public List<UserDto> getUsersRespondedToVacancy(Integer vacancyId) {
        List<UserDto> users = userRepository.findUsersRespondedToVacancy(vacancyId)
                .stream()
                .map(userMapper::toUserDto)
                .toList();
        if (users.isEmpty()) {
            log.error("Can't find users with vacancy id {}", vacancyId);
        } else {
            log.info("found users with vacancy id {}", vacancyId);
        }
        return users;
    }

    @Override
    public void applyForVacancy(Integer vacancyId, ResumeDto resumeDto) {
        Resume resume = resumeRepository.findById(resumeDto.getApplicantId()).orElseThrow(
                () -> new UsernameNotFoundException(
                        "Can't find resume with applicant id: " + resumeDto.getApplicantId()
                )
        );
        Vacancy vacancy = vacancyRepository.findById(vacancyId).orElseThrow(
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
            Path filePath = Paths.get(AVATAR_DIR, fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, avatar.getBytes());

            User user = userRepository.findById(userId).orElseThrow(
                    () -> new UserNotFoundException("can't find user with id: " + userId));

            userRepository.updateAvatar(user.getId(), fileName);

            log.warn(ConsoleColors.YELLOW_BACKGROUND + "saved avatar to path {}" + ConsoleColors.RESET, filePath.getFileName().getFileName());
        } else {
            throw new IOException("File is empty");
        }
    }

    @Override
    public ResponseEntity<Resource> getAvatar(Integer userId) throws UserNotFoundException {
        Optional<User> user = userDao.getUserById(userId);
        if (user.isPresent()) {
            try {
                byte[] image = Files.readAllBytes(Paths.get(AVATAR_DIR + user.get().getAvatar()));
                Resource resource = new ByteArrayResource(image);
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + user.get().getAvatar() + "\"")
                        .contentLength(resource.contentLength())
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
        } else {
            log.error("Can't find user with id {}", userId);
            throw new UserNotFoundException(String.format("user with id %d not found", userId));
        }
    }

    @Override
    public void updateUser(UserDto userDto) {
        // todo - implement
    }

    @Override
    public boolean deleteUser(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            log.info("user deleted: {}", user.get().getName());
            return true;
        }
        log.info(String.format("user with id %d not found", id));
        return false;
    }

}
