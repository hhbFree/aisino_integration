package aisino.inter;

import aisino.mongo.service.MessageDAO;
import aisino.mongo.vo.Message;
import aisino.mongo.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMessageDAO {
    @Autowired
    private MessageDAO messageDAO;

    @Test
    public void testSave() {
        Message message = Message.builder()
                .id(ObjectId.get())
                .msg("你好")
                .sendDate(new Date())
                .status(1)
                .from(new User(1001L, "zhangsan"))
                .to(new User(1002L, "lisi"))
                .build();
        this.messageDAO.saveMessage(message);
        message = Message.builder()
                .id(ObjectId.get())
                .msg("你也好")
                .sendDate(new Date())
                .status(1)
                .to(new User(1001L, "zhangsan"))
                .from(new User(1002L, "lisi"))
                .build();
        this.messageDAO.saveMessage(message);
        message = Message.builder()
                .id(ObjectId.get())
                .msg("我在学习开发IM")
                .sendDate(new Date())
                .status(1)
                .from(new User(1001L, "zhangsan"))
                .to(new User(1002L, "lisi"))
                .build();
        this.messageDAO.saveMessage(message);
        message = Message.builder()
                .id(ObjectId.get())
                .msg("那很好啊！")
                .sendDate(new Date())
                .status(1)
                .to(new User(1001L, "zhangsan"))
                .from(new User(1002L, "lisi"))
                .build();
        this.messageDAO.saveMessage(message);
        System.out.println("ok");
    }

    @Test
    public void testQueryById() {
        Message message =
                this.messageDAO.findMessageById("5c0a9109235e193bd4873b92");
        System.out.println(message);
    }

    @Test
    public void testQueryList() {
//        List<Message> list = this.messageDAO.findListByFromAndTo(1001L, 1002L, 2,
//                1);
        List<Message> list = this.messageDAO.findListByFromAndTo(1001L, 1002L, 1,
                2);
        for (Message message : list) {
            System.out.println(message);
        }
    }
}