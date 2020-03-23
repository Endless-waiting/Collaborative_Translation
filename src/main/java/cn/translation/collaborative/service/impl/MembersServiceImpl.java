package cn.translation.collaborative.service.impl;

import cn.translation.collaborative.mapper.MembersMapper;
import cn.translation.collaborative.pojo.Members;
import cn.translation.collaborative.pojo.ResultOfPage;
import cn.translation.collaborative.service.MembersService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembersServiceImpl implements MembersService {

    @Autowired
    MembersMapper membersMapper;

    @Override
    public List<Members> listAllMembers() {
        List<Members> members = membersMapper.selectAllMembers();
        int index=-1;
        for (int i=0;i<members.size();i++){
            Members user = members.get(i);
            if (user.getMid()==0){
                index = i;
            }
            user.setPassword("******");
        }
        members.remove(index);
        return members;
    }

    @Override
    public ResultOfPage<Members> listPageOfAllMembers(int page, int limit) {
        Page<Members> startPage = PageHelper.startPage(page, limit);
        List<Members> members = membersMapper.selectAllMembers();
        if (members!=null){
            int index =-1;
            for (int i=0;i<members.size();i++){
                Members user = members.get(i);
                user.setPassword("******");
            }
        }
        return new ResultOfPage<Members>(0,"",startPage.getTotal(),startPage.getResult());
    }

    @Override
    public String updataMemberById(Members members) {
        int i = membersMapper.updateByPrimaryKeySelective(members);
        if (i>0) {
            return "success";
        }
        return "error";
    }

    @Override
    public String insertNewMember(Members members) {
        String max = membersMapper.selectMaxAccountOfMembers();
        Integer value = Integer.valueOf(max)+1;
        members.setAccount(value.toString());
        int i = membersMapper.insertSelective(members);
        if (i>0)
            return "success";
        return "error";
    }

    @Override
    public String deleteMemberById(String account) {
        int i = membersMapper.deleteByAccount(account);
        if (i>0){
            return "success";
        }
        return "error";
    }
}