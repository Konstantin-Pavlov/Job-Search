package kg.attractor.jobsearch.mapper;


import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    //  @Mapping(source = "parcelType", target = "type")
    UserDto toUserDto(User user);

    User toUser(UserDto userDto);

//    got this error for some reason:
//    D:\GD\my_progs\JAVA\mari\Job_Search_old\src\main\java\kg\attractor\jobsearch\mapper\UserMapper.java:25:42
//    java: No property named "avatar" exists in source parameter(s). Type "UserWithAvatarFileDto" has no properties.
//    @Mapping(target = "avatar", source = "avatar", qualifiedByName = "mapAvatar")
//    UserDto toUserDto(UserWithAvatarFileDto userWithAvatarFileDto);
//
//    @Named("mapAvatar")
//    default String mapAvatar(MultipartFile avatar) {
//        return avatar != null ? avatar.getOriginalFilename() : null;
//    }

    // New method to update an existing User entity with values from UserDto
    void updateUserFromDto(UserDto userDto, @MappingTarget User user);
}
