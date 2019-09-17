package aisino.mongo.controller;


import aisino.mongo.service.impl.MessageService;
import aisino.mongo.vo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("message")
@CrossOrigin
public class MessageController {
    @Autowired
    private MessageService messageService;

    /**
     * 拉取消息列表
     *
     * @param fromId
     * @param toId
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("history")
    public List<Message> queryMessageList(@RequestParam("fromId") Long fromId,
                                          @RequestParam("toId") Long toId,
                                          @RequestParam(value = "page",
                                                  defaultValue = "1") Integer page,
                                          @RequestParam(value = "rows",
                                                  defaultValue = "10") Integer rows) {
        return this.messageService.queryMessageList(fromId, toId, page, rows);
    }
}