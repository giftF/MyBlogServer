package org.gift.PersistantObject.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gift.PersistantObject.entity.Plans;

import java.util.List;

@Mapper
public interface PlansMapper {
    //
    int save(@Param("message")String message);

    //
    List<Plans> selectAllPlans();

    //
    int updatePlan(@Param("id")int id, @Param("tp")int tp);
}
