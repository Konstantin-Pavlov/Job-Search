package kg.attractor.jobsearch.mapper;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.UserWithAvatarFileDto;
import kg.attractor.jobsearch.model.User;

public class CustomUserMapper {
    private CustomUserMapper() {
    }

    public static void updateUserFromDto(UserDto userDto, User existingUser) {
        existingUser.setName(userDto.getName());
        existingUser.setAge(userDto.getAge());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setPhoneNumber(userDto.getPhoneNumber());
        existingUser.setAvatar(userDto.getAvatar());
        existingUser.setAccountType(userDto.getAccountType());
        existingUser.setEnabled(userDto.isEnabled());
    }

    public static UserDto toUserDto(User user) {
        if (user == null) {
            return null;
        }

        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .email(user.getEmail())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .avatar(user.getAvatar())
                .accountType(user.getAccountType())
                .enabled(user.isEnabled())
                .build();
    }


    public static UserDto toUserDto(UserWithAvatarFileDto userWithAvatarFileDto) {
        if (userWithAvatarFileDto == null) {
            return null;
        }

        return UserDto.builder()
                .id(userWithAvatarFileDto.getId())
                .name(userWithAvatarFileDto.getName())
                .age(userWithAvatarFileDto.getAge())
                .email(userWithAvatarFileDto.getEmail())
                .password(userWithAvatarFileDto.getPassword())
                .phoneNumber(userWithAvatarFileDto.getPhoneNumber())
                .avatar(userWithAvatarFileDto.getAvatar() != null ? userWithAvatarFileDto.getAvatar().getOriginalFilename() : null)
                .accountType(userWithAvatarFileDto.getAccountType())
                .enabled(userWithAvatarFileDto.isEnabled())
                .build();
    }

    public static User toUser(UserDto userDto) {
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
}
