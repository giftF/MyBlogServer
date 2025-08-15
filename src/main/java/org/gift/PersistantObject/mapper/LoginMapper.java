package org.gift.PersistantObject.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gift.PersistantObject.entity.Login;

@Mapper
public interface LoginMapper {
    // 保存方法
    int save(@Param("users_id")String users_id, @Param("ip")String ip, @Param("equipment")String equipment);
}
