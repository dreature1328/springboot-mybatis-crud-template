package dreature.smct.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import dreature.smct.service.BaseService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class BaseServiceImpl<T> implements BaseService<T> {
    @Autowired
    protected SqlSession sqlSession;
    @Autowired
    protected ObjectMapper objectMapper;
}
