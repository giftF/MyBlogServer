package org.gift.PersistantObject.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gift.PersistantObject.entity.Users;

@Mapper
public interface UsersMapper {

    // 保存方法
    int save(Users users);

    Users selectUserforPassword(@Param("username")String username, @Param("password")String password);
}
