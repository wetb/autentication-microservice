package co.com.bancolombia.api.mapper;

import co.com.bancolombia.api.dto.request.CreateUserDTO;
import co.com.bancolombia.api.dto.response.UserDTO;
import co.com.bancolombia.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "idUser", ignore = true)
    User userDTOtoUser (CreateUserDTO userDTO);
    UserDTO userToUserDTO(User user);
}
