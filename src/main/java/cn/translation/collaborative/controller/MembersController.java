package cn.translation.collaborative.controller;

import cn.translation.collaborative.pojo.Group;
import cn.translation.collaborative.pojo.Members;
import cn.translation.collaborative.pojo.ResultOfPage;
import cn.translation.collaborative.service.GroupService;
import cn.translation.collaborative.service.MembersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/Members")
public class MembersController {
    @Autowired
    private MembersService membersService;
    @Autowired
    private GroupService groupService;

    //跳转页面
    @GetMapping("/{path}")
    public String toPage(@PathVariable String path){
        return "manage/member/"+path;
    }

    //获得所有单位信息
    @RequestMapping("/getAllInstitute")
    @ResponseBody
    public Map<Integer,String> getAllInstitute(){
        Map<Integer, String> map = new HashMap<>();
        map.put(1,"单位");
        return map;
    }

    //获得所有部门信息
    @RequestMapping("/getAllDepartment")
    @ResponseBody
    public Map<Integer,String> getAllDepartment(){
        Map<Integer, String> map = new HashMap<>();
        map.put(1,"部门");
        return map;
    }

    //获得所有的研究团队信息
    @RequestMapping("/getAllTeam")
    @ResponseBody
    public Map<Integer,String> getAllTeam(){
        Map<Integer, String> map = new HashMap<>();
        map.put(1,"研究团队");
        return map;
    }

    //获得所有的职称级别信息
    @RequestMapping("/getAllTitle")
    @ResponseBody
    public Map<Integer,String> getAllTitle(){
        Map<Integer, String> map = new HashMap<>();
        map.put(1,"初级");
        map.put(2,"中级");
        map.put(3,"高级");
        return map;
    }

    //获得所有的翻译小组信息
    @RequestMapping("/getAllGroup")
    @ResponseBody
    public List<Group> getAllGroup(){
        List<Group> groups = groupService.listAllGroup();
        return groups;
    }

    //获得所有的成员信息
    @RequestMapping("/getAllMembers")
    @ResponseBody
    public ResultOfPage<Members> getAllMembers(){
        List<Members> members = membersService.listAllMembers();
        return new ResultOfPage<Members>(0,"",members.size(),members);
    }

    //跳转到新增页
    @RequestMapping("/PostMemberPage")
    public String postMemberPage() {
        return "manage/member/addMember";
    }

    //新增人员信息
    @RequestMapping("/postMember")
    @ResponseBody
    public String postMember(Members members) {
        System.out.println(members);
        String s = membersService.insertNewMember(members);
        return s;
    }

    //修改人员信息
    @RequestMapping("/putMemberById")
    @ResponseBody
    public String putMemberById(Members members){
        System.out.println(members);
        return membersService.updataMemberById(members);
    }

    //根据ID删除人员信息
    @RequestMapping("/deleteMemberById")
    @ResponseBody
    public String deleteMemberById(String account){

        return membersService.deleteMemberById(account);
    }
}
