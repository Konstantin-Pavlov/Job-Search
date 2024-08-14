package kg.attractor.jobsearch.mapper;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.UserDtoWithAvatarUploadingDto;

public class CustomUserMapper {
    public static UserDto toUserDto(UserDtoWithAvatarUploadingDto userDtoWithAvatarUploadingDto){
        return UserDto.builder()
                .name(userDtoWithAvatarUploadingDto.getName())
                .age(userDtoWithAvatarUploadingDto.getAge())
                .email(userDtoWithAvatarUploadingDto.getEmail())
                .password(userDtoWithAvatarUploadingDto.getPassword())
                .phoneNumber(userDtoWithAvatarUploadingDto.getPhoneNumber())
                .avatar(userDtoWithAvatarUploadingDto.getAvatar().getOriginalFilename())
                .accountType(userDtoWithAvatarUploadingDto.getAccountType())
                .enabled(userDtoWithAvatarUploadingDto.isEnabled())
                .build();
    }
}
