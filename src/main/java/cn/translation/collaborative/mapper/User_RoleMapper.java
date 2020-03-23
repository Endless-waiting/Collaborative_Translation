package cn.translation.collaborative.mapper;

import cn.translation.collaborative.pojo.User_Role;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface User_RoleMapper {
    int deleteByPrimaryKey(Integer uid);

    int insert(User_Role record);

    int insertSelective(User_Role record);

    User_Role selectByPrimaryKey(Integer uid);

    int updateByPrimaryKeySelective(User_Role record);

    int updateByPrimaryKey(User_Role record);
}