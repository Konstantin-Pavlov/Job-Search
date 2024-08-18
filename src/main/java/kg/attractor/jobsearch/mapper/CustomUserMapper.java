package kg.attractor.jobsearch.mapper;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.UserWithAvatarFileDto;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.security.CustomUserDetails;
import kg.attractor.jobsearch.util.CustomMultipartFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class CustomUserMapper {
    @Value("${app.avatar_dir}")
    private static String UPLOAD_DIR;

    public static UserDto toUserDto(UserWithAvatarFileDto userWithAvatarFileDto) {
        return UserDto.builder()
                .name(userWithAvatarFileDto.getName())
                .age(userWithAvatarFileDto.getAge())
                .email(userWithAvatarFileDto.getEmail())
                .password(userWithAvatarFileDto.getPassword())
                .phoneNumber(userWithAvatarFileDto.getPhoneNumber())
                .avatar(userWithAvatarFileDto.getAvatar().getOriginalFilename())
                .accountType(userWithAvatarFileDto.getAccountType())
                .enabled(userWithAvatarFileDto.isEnabled())
                .build();
    }

    public static User fromUserDto(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .age(userDto.getAge())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .phoneNumber(userDto.getPhoneNumber())
                .avatar(userDto.getAvatar())
                .accountType(userDto.getAccountType())
                .enabled(userDto.isEnabled())
                .build();
    }

    public static UserDto toUserDto(CustomUserDetails userDetails) {
        return UserDto.builder()
                .id(userDetails.getId())
                .name(userDetails.getUsername())
                .age(userDetails.getAge())
                .email(userDetails.getEmail())
                .password(userDetails.getPassword())
                .phoneNumber(userDetails.getPhoneNumber())
                .avatar(userDetails.getAvatar())
                .accountType(userDetails.getAccountType())
                .enabled(userDetails.isEnabled())
                .build();
    }

    public static UserWithAvatarFileDto toUserWithAvatarFileDto(CustomUserDetails userDetails) throws IOException {

        MultipartFile avatar = getUserAvatar(userDetails);

        return UserWithAvatarFileDto.builder()
                .id(userDetails.getId())
                .name(userDetails.getUsername())
                .age(userDetails.getAge())
                .email(userDetails.getEmail())
                .password(userDetails.getPassword())
                .phoneNumber(userDetails.getPhoneNumber())
                .avatar(avatar)
                .accountType(userDetails.getAccountType())
                .enabled(userDetails.isEnabled())
                .build();
    }

    private static MultipartFile getUserAvatar(CustomUserDetails userDetails) throws IOException {
        // todo - move to util
        MultipartFile avatar = null;
        if (userDetails.getAvatar() != null) {
            byte[] image = Files.readAllBytes(Paths.get(UPLOAD_DIR + userDetails.getAvatar()));
            avatar = new CustomMultipartFile(
                    image,
                    userDetails.getAvatar(),
                    "image/jpeg"
            );
        }
        return avatar;
    }
}
