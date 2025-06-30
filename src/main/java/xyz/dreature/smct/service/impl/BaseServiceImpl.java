package xyz.dreature.smct.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.dreature.smct.mapper.BaseMapper;
import xyz.dreature.smct.service.BaseService;

@Service
public abstract class BaseServiceImpl<T> implements BaseService<T> {
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected SqlSession sqlSession;
    @Autowired
    protected BaseMapper<T> baseMapper;
}
