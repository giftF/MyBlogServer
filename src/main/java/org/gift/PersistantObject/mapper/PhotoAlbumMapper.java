package org.gift.PersistantObject.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gift.PersistantObject.entity.PhotoAlbum;
import org.gift.ValueObject.PhotoAlbum.Catalogue;

import java.util.List;

@Mapper
public interface PhotoAlbumMapper {

    int add(PhotoAlbum photoAlbum);

    int update(PhotoAlbum photoAlbum);

    int delete(@Param("id")Integer id);

    int frontcover(@Param("id")Integer id, @Param("photoid")Integer photoid);

    List<Catalogue> getList(@Param("top")Integer top, @Param("sort")Integer sort, @Param("rowcount")Integer rowcount, @Param("page")Integer page);

    int setfrontcover(@Param("id")Integer id);
}
