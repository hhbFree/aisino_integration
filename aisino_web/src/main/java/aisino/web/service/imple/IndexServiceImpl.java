package aisino.web.service.imple;


import aisino.web.mapper.IndexMapper;
import aisino.web.service.IndexService;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IndexServiceImpl implements IndexService {
    @Resource
    private IndexMapper indexMapper;

    @Override
    @DS("docker_mysql")
    public Map userData(Map page) {
        List<Map<String,Object>> mapList=indexMapper.userData(page);
        Map<String,Object> map=new HashMap<>();
        map.put("rows",mapList);
        Long count=indexMapper.userCount(page);
        map.put("total",count);
        return map;
    }

    @Override
    @DS("docker_mysql")
    public void insert(Map map) {
        indexMapper.insert(map);
    }
}
