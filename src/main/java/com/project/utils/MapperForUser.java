package com.project.utils;

import com.project.model.FullUser;
import com.project.entity.UserEntity;

public class MapperForUser {

    public static FullUser mapperFromUserEntityToUserDTO(UserEntity inputUserEntity){
        return new FullUser(String.valueOf(inputUserEntity.getId()),
                inputUserEntity.getUsername(),
                inputUserEntity.getPassword(),
                inputUserEntity.getRole());
    }
}
