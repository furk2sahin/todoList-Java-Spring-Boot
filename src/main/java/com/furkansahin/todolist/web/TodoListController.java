package com.furkansahin.todolist.web;

import com.furkansahin.todolist.service.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TodoListController {

    @Autowired
    private TodoListService todoListService;

    @RequestMapping("/todolists")
    public ModelAndView getTodoLists(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("todolists", todoListService.findLists());
        modelAndView.setViewName("todolists");
        return modelAndView;
    }

    @RequestMapping("/pcs")
    @ResponseBody
    public String welcome(){
        return "welcome!";
    }
}
