package cn.translation.collaborative.mapper;

import cn.translation.collaborative.pojo.Group;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GroupMapper {
    int deleteByPrimaryKey(Integer gid);

    int insert(Group record);

    int insertSelective(Group record);

    Group selectByPrimaryKey(Integer gid);

    List<Group> selectAll();

    int updateByPrimaryKeySelective(Group record);

    int updateByPrimaryKey(Group record);
}