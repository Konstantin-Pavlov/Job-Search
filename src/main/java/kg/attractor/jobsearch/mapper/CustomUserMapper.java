package kg.attractor.jobsearch.mapper;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.UserDtoWithAvatarUploading;

public class CustomUserMapper {
    public static UserDto toUserDto(UserDtoWithAvatarUploading userDtoWithAvatarUploading){
        return UserDto.builder()
                .name(userDtoWithAvatarUploading.getName())
                .age(userDtoWithAvatarUploading.getAge())
                .email(userDtoWithAvatarUploading.getEmail())
                .password(userDtoWithAvatarUploading.getPassword())
                .phoneNumber(userDtoWithAvatarUploading.getPhoneNumber())
                .avatar(userDtoWithAvatarUploading.getAvatar().getOriginalFilename())
                .accountType(userDtoWithAvatarUploading.getAccountType())
                .enabled(userDtoWithAvatarUploading.isEnabled())
                .build();
    }
}
