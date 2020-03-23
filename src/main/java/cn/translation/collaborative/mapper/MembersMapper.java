package cn.translation.collaborative.mapper;

import cn.translation.collaborative.pojo.Members;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MembersMapper {
    int deleteByPrimaryKey(Integer mid);

    int deleteByAccount(String account);

    int insert(Members record);

    int insertSelective(Members record);

    Members selectByPrimaryKey(Integer mid);

    Members selectByAccount(String account);

    String selectMaxAccountOfMembers();

    List<Members> selectAllMembers();

    String selectRoleByAccount(String account);

    int updateByPrimaryKeySelective(Members record);

    int updateByPrimaryKey(Members record);
}