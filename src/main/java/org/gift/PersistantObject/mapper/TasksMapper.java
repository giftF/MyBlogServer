package org.gift.PersistantObject.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gift.PersistantObject.entity.Tasks;

import java.util.List;

@Mapper
public interface TasksMapper {
    int save(Tasks tasks);
    int update(@Param("id")Integer id, @Param("status")String status, @Param("progress")Integer progress);
    List<Tasks> getList();
}
