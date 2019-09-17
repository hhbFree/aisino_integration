package aisino.mongo.service.impl;


import aisino.mongo.service.MessageDAO;
import aisino.mongo.vo.Message;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class MessageDAOImpl implements MessageDAO {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Message> findListByFromAndTo(Long fromId, Long toId, Integer page,
                                             Integer rows) {
        Criteria fromList =
                Criteria.where("from.id").is(fromId).and("to.id").is(toId);
        Criteria toList =
                Criteria.where("from.id").is(toId).and("to.id").is(fromId);
        Criteria criteria = new Criteria().orOperator(fromList, toList);
        PageRequest pageRequest = PageRequest.of(page - 1, rows,
                Sort.by(Sort.Direction.ASC, "send_date"));
        Query query = new Query(criteria).with(pageRequest);
        System.out.println(query);
        return this.mongoTemplate.find(query, Message.class);
    }

    @Override
    public Message findMessageById(String id) {
        return this.mongoTemplate.findById(new ObjectId(id), Message.class);
    }

    @Override
    public UpdateResult updateMessageState(ObjectId id, Integer status) {
        Query query = Query.query(Criteria.where("id").is(id));
        Update update = Update.update("status", status);
        if (status.intValue() == 1) {
            update.set("send_date", new Date());
        } else if (status.intValue() == 2) {
            update.set("read_date", new Date());
        }
        return this.mongoTemplate.updateFirst(query, update, Message.class);
    }

    @Override
    public Message saveMessage(Message message) {
        message.setId(ObjectId.get());
        message.setSendDate(new Date());
        message.setStatus(1);
        this.mongoTemplate.save(message);
        return message;
    }

    @Override
    public DeleteResult deleteMessage(String id) {
        Query query = Query.query(Criteria.where("id").is(id));
        return this.mongoTemplate.remove(query, Message.class);
    }
}