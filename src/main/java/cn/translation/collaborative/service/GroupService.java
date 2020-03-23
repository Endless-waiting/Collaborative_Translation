package cn.translation.collaborative.service;

import cn.translation.collaborative.pojo.Group;
import cn.translation.collaborative.pojo.ResultOfPage;

import java.util.List;

public interface GroupService {

    //分页查询小组信息
    ResultOfPage<Group> listPageOfAllGroup(int page, int limit);

    //查询所有的小组信息
    List<Group> listAllGroup();

    //更新小组数据
    String updataGroupById(Group group);

    //根据id删除小组
    String deleteGroupById(Integer gid);

    //新增小组信息
    String insertNewGroup(Group group);

}
