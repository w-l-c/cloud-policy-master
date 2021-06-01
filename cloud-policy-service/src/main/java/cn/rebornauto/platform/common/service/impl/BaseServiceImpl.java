package cn.rebornauto.platform.common.service.impl;

import cn.rebornauto.platform.common.dao.BaseMapper;
import cn.rebornauto.platform.common.service.BaseService;
import cn.rebornauto.platform.common.data.request.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @param <T>实体bean
 * @param <ID>主键id
 * @param <E>搜索对象
 * @param <M>BaseMapper的实现
 */
public abstract class BaseServiceImpl<T, ID extends Serializable, E, M extends BaseMapper<T, ID, E>,Q extends Query> implements BaseService<T, ID, E,Q> {

    @Autowired
    protected M mapper;

    @Override
    @Transactional
    public int save(T t) {
        return mapper.insertSelective(t);
    }

    @Override
    public T findById(ID id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public int update(T t) {
        return mapper.updateByPrimaryKeySelective(t);
    }

    @Override
    public List<T> findPageByExample(E example) {
        return mapper.selectByExample(example);
    }

    @Override
    public long countByByExample(E example) {
        return mapper.countByExample(example);
    }

    @Override
    public int removeById(ID id) {
        return mapper.deleteByPrimaryKey(id);
    }
}
