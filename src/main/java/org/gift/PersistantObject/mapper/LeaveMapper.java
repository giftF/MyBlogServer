package org.gift.PersistantObject.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gift.PersistantObject.entity.Leave;
import org.gift.ValueObject.Message.ResList;
import org.gift.ValueObject.Message.ResStatistics;

import java.util.List;

@Mapper
public interface LeaveMapper {
    // 新增留言
    int add(Leave leave);

    // 查看留言总数
    ResStatistics statistics();

    // 设置留言已读
    int read(@Param("id")Integer id);

    // 留言列表
    List<Leave> getList(@Param("rownum")Integer rownum, @Param("page")Integer page);
}
