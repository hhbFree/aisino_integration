package aisino.mongo.service.impl;


import aisino.mongo.service.MessageDAO;
import aisino.mongo.vo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageDAO messageDAO;

    public List<Message> queryMessageList(Long fromId, Long toId, Integer page,
                                          Integer rows) {
        List<Message> list = this.messageDAO.findListByFromAndTo(fromId, toId,
                page, rows);
        for (Message message : list) {
            if (message.getStatus().intValue() == 1) {
                this.messageDAO.updateMessageState(message.getId(), 2);
            }
        }
        return list;
    }

}