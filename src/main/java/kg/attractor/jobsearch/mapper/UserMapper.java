package kg.attractor.jobsearch.mapper;


import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.UserWithAvatarFileDto;
import kg.attractor.jobsearch.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.web.multipart.MultipartFile;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    //  @Mapping(source = "parcelType", target = "type")
    UserDto toUserDto(User user);

    User toUser(UserDto userDto);

    @Mapping(target = "avatar", source = "avatar", qualifiedByName = "mapAvatar")
    UserDto toUserDto(UserWithAvatarFileDto userWithAvatarFileDto);

    @Named("mapAvatar")
    default String mapAvatar(MultipartFile avatar) {
        return avatar != null ? avatar.getOriginalFilename() : null;
    }
}
