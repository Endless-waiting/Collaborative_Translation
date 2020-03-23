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

import java.util.List;

@Controller
@RequestMapping("/Groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private MembersService membersService;

    //跳转页面
    @GetMapping("/{path}")
    public String toPage(@PathVariable String path){
        return "manage/team/"+path;
    }

    //分页展示小组所有信息
    @RequestMapping("/getGroup")
    @ResponseBody
    public ResultOfPage<Group> getGroup(int page, int limit){
        return groupService.listPageOfAllGroup(page, limit);
    }

    //根据id值修改小组信息
    @RequestMapping("/putGroupById")
    @ResponseBody
    public String putGroupById(Group group){
        String result = groupService.updataGroupById(group);
        System.out.println(result);
        return result;
    }

    //根据ID删除小组
    @RequestMapping("/deleteGroupById")
    @ResponseBody
    public String deleteGroupById(int gid){
        return groupService.deleteGroupById(gid);
    }

    //获得所有的员工信息
    @RequestMapping("/getAllMembers")
    @ResponseBody
    public List<Members> getAllMembers(){
        return membersService.listAllMembers();
    }

    //跳转到添加小组信息的页面
    @RequestMapping("/addGroupPage")
    public String addGroupPage(){
        return "manage/team/addGroup";
    }

    //添加新的小组信息
    @RequestMapping("/addGroup")
    @ResponseBody
    public String addGroup(Group group){
        System.out.println(group);
        String s = groupService.insertNewGroup(group);
        System.out.println(s);
        return s;
    }
}