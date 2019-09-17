package aisino.web.controller;



import aisino.web.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

import java.util.Map;

@Controller
@RequestMapping("hhb")
public class IndexController {
    @Autowired
    private IndexService indexService;

    @RequestMapping("/login")
    public String login(Model model) {
        ArrayList<String> list = new ArrayList<>();
        list.add("Async：简洁优雅的异步之道,在异步处理方案中，目前最为简洁优雅的便是async函数（以下简称A函数）。www.baidu.com");
        list.add("H5 前端性能测试实践,H5 页面发版灵活，轻量，又具有跨平台的特性，在业务上有很多应用场景。www.baidu.com");
        list.add("学习Python的建议,Python是最容易入门的编程语言。www.baidu.com");
        model.addAttribute("articleList",list);
        return "aisino/login";
    }

    @RequestMapping("userData")
    @ResponseBody
    public Map<String, Object> userData(@RequestParam Map map){

        Map<String,Object> remap= this.indexService.userData(map);
        return remap;
    }

    @RequestMapping("insert")
    @ResponseBody
    public void insert(@RequestParam Map map){
       this.indexService.insert(map);
    }

    @RequestMapping("layout")
    public String layout(){
        return "aisino/layout";
    }


    @RequestMapping("idnex")
    public String idnex(){
        return "aisino/index";
    }


}
