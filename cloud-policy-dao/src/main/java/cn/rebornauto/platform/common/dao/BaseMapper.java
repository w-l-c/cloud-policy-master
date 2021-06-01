package cn.rebornauto.platform.common.dao;

import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @param <T>  和数据库对应的bean
 * @param <ID> 主键类型
 * @param <E>  搜索example
 */
public interface BaseMapper<T,ID extends Serializable,E> {

    int countByExample(E example);

    int deleteByExample(E example);

    int deleteByPrimaryKey(ID id);

    int insert(T record);

    int insertSelective(T record);

    List<T> selectByExample(E example);

    T selectByPrimaryKey(ID id);

    int updateByExampleSelective(@Param("record") T record, @Param("example") E example);

    int updateByExample(@Param("record") T record, @Param("example") E example);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);


}