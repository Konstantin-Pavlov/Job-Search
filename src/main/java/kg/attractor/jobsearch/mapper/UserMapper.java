package kg.attractor.jobsearch.mapper;


import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.model.User;
import org.springframework.web.bind.annotation.Mapping;
import org.mapstruct.Mapper;

import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

//  @Mapping(source = "parcelType", target = "type")
  UserDto toUserDto(User user);
  User toUser(UserDto userDto);
}
