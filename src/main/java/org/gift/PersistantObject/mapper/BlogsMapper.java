package org.gift.PersistantObject.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gift.PersistantObject.entity.Blogs;
import org.gift.ValueObject.Blog.Catalogue;
import org.gift.ValueObject.Blog.ResContent;

import java.util.List;

@Mapper
public interface BlogsMapper {
    // 新增
    int add(Blogs blogs);

    // 修改
    int update(Blogs blogs);

    // 查询列表
    List<Catalogue> getList(@Param("top")Integer top, @Param("sort")Integer sort, @Param("rowcount")Integer rowcount, @Param("page")Integer page);

    // 查询详情
    ResContent content(Blogs blogs);
}
