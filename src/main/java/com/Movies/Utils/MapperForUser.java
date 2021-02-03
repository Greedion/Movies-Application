package com.Movies.Utils;

import com.Movies.DataTransferObject.UserDTO;
import com.Movies.Entity.UserEntity;

public class MapperForUser {

    public static UserDTO mapperFromUserEntityToUserDTO(UserEntity inputUserEntity){
        return new UserDTO(String.valueOf(inputUserEntity.getId()),
                inputUserEntity.getUsername(),
                inputUserEntity.getPassword(),
                inputUserEntity.getRole());
    }
}
