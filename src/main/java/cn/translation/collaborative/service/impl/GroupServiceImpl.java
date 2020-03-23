package cn.translation.collaborative.service.impl;

import cn.translation.collaborative.mapper.GroupMapper;
import cn.translation.collaborative.pojo.Group;
import cn.translation.collaborative.pojo.ResultOfPage;
import cn.translation.collaborative.service.GroupService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupMapper groupMapper;

    @Override
    public ResultOfPage<Group> listPageOfAllGroup(int page, int limit) {

        Page<Group> startPage = PageHelper.startPage(page, limit);
        List<Group> groups = groupMapper.selectAll();

        ResultOfPage<Group> result = new ResultOfPage<>(0, "", startPage.getTotal(), startPage.getResult());
        return result;
    }

    @Override
    public List<Group> listAllGroup() {
        List<Group> groups = groupMapper.selectAll();
        return groups;
    }

    @Override
    public String updataGroupById(Group group) {

        int i = groupMapper.updateByPrimaryKeySelective(group);

        if (i > 0) {
            return "success";
        }
        return "error";
    }

    @Override
    public String deleteGroupById(Integer gid) {
        int delete = groupMapper.deleteByPrimaryKey(gid);
        if (delete>0){
            return "success";
        }
        return "error";
    }

    @Override
    public String insertNewGroup(Group group) {
        int i = groupMapper.insertSelective(group);
        if (i>0){
            return "success";
        }
        return "error";
    }

}
