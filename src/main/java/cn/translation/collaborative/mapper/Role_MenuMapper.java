package cn.translation.collaborative.mapper;

import cn.translation.collaborative.pojo.Role_Menu;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Role_MenuMapper {
    int insert(Role_Menu record);

    int insertSelective(Role_Menu record);
}