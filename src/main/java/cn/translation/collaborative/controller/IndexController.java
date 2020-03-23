package cn.translation.collaborative.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wangwei
 */
@Controller
public class IndexController {
    @RequestMapping("/")
    public String showIndex(){
        return "index";
    }

    @RequestMapping("/manage")
    public String showMange(){
        return "manage/manager";
    }
}