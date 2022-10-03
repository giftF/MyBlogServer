package org.gift.PersistantObject.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gift.PersistantObject.entity.Deploy;

import java.util.List;

@Mapper
public interface DeployMapper {
    // 查询列表
    List<Deploy> selectByClassify(@Param("classify")String classify);

    Deploy selectByIDorKey(Deploy deploy);
}
