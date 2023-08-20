package panomete.project.cometopimabe.security.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import panomete.project.cometopimabe.security.entity.Users;
import panomete.project.cometopimabe.security.payload.response.AuthDto;

import java.util.List;

@Mapper
public interface AutoAuthMapper {
    AutoAuthMapper MAPPER = Mappers.getMapper(AutoAuthMapper.class);

    @Mappings({
            @Mapping(target = "id", expression = "java(user.getUserId())"),
            @Mapping(target = "age", expression = "java(user.getAge())"),
            @Mapping(target = "name", expression = "java(user.getFullName())"),
            @Mapping(target = "roles", expression = "java(user.getSimpleAuthorities())")
    })
    AuthDto toAuthDto(Users user);
    List<AuthDto> toAuthDto(List<Users> user);
}
