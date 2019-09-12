package aisino.web.mapper;

import java.util.List;
import java.util.Map;

public interface IndexMapper {

    List<Map<String, Object>> userData(Map<String, Object> map);

    Long userCount(Map<String, Object> map);

    void insert(Map map);
}
