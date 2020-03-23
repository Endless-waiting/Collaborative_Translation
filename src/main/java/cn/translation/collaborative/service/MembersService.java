package cn.translation.collaborative.service;

import cn.translation.collaborative.pojo.Members;
import cn.translation.collaborative.pojo.ResultOfPage;

import java.util.List;

public interface MembersService {
    //查询所有人员
    List<Members> listAllMembers();

    //查询所有人员并分页显示
    ResultOfPage<Members> listPageOfAllMembers(int page, int limit);

    //修改人员信息
    String updataMemberById(Members members);

    //新增人员信息
    String insertNewMember(Members members);

    //根据account删除人员
    String deleteMemberById(String account);
}
