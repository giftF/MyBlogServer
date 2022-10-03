package org.gift.PersistantObject.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.gift.PersistantObject.entity.Login;

@Mapper
public interface LoginMapper {
    // 保存方法
    int save(Login login);
}
