package cn.rebornauto.platform.common.service;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.data.request.Query;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T, ID extends Serializable, E,Q extends Query> {

    int save(T t);

    T findById(ID id);

    int update(T t);

    List<T> findPageByExample(E example);

    long countByByExample(E example);

    int removeById(ID id);

    long countQuery(Q query);

    List<T> pageQuery(Pagination pagination,Q query);

}
