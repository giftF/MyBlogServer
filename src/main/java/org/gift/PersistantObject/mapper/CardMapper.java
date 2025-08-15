package org.gift.PersistantObject.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gift.PersistantObject.entity.Card;

import java.util.List;

@Mapper
public interface CardMapper {
    // 保存方法
    int save(Card card);

    List<Card> getList();

    int update(@Param("id")String id, @Param("correct")Integer correct, @Param("error")Integer error);
}
