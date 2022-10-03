package org.gift.PersistantObject.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gift.PersistantObject.entity.LoginRecord;


@Mapper
public interface LoginRecordMapper {
    Integer SelectCount(@Param("ip")String ip);

    int save(LoginRecord loginRecord);
}
