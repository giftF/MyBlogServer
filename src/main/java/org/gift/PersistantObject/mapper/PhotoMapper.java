package org.gift.PersistantObject.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gift.PersistantObject.entity.Photo;
import org.gift.ValueObject.Photo.Catalogue;

import java.util.List;

@Mapper
public interface PhotoMapper {

    int add(Photo photo);

    int update(Photo photo);

    int sort(@Param("photoalbum_id")Integer photoalbum_id, @Param("index")Integer index, @Param("tp")Integer tp);

    int delete(@Param("id")Integer id);

    Photo selectByID(@Param("id")Integer id);

    List<Catalogue> getList(@Param("id")Integer id, @Param("rowcount")Integer rowcount, @Param("page")Integer page);

}
