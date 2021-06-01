package cn.rebornauto.platform.common.dao;

import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import java.util.Objects;
import java.util.function.Function;

public class MysqlPaginationAdapter<R> {
    private SelectModel selectModel;
    private Function<SelectStatementProvider, R> mapperMethod;
    private int limit;
    private int offset;

    public R execute() {
        return mapperMethod.apply(selectStatement());
    }

    private SelectStatementProvider selectStatement() {
        SelectStatementProvider sel = selectModel.render(RenderingStrategy.MYBATIS3);
        return new MysqlPaginationSelectStatementProvider(sel, offset, limit);
    }

    private MysqlPaginationAdapter(SelectModel selectModel,
                                   Function<SelectStatementProvider, R> mapperMethod,
                                   int offset, int limit) {

        this.selectModel = Objects.requireNonNull(selectModel);
        this.mapperMethod = Objects.requireNonNull(mapperMethod);

        this.offset = offset;
        this.limit = limit;
    }

    public static <R> MysqlPaginationAdapter<R> of
            (SelectModel selectModel,
             Function<SelectStatementProvider, R> mapperMethod,
             int offset,
             int limit) {

        return new MysqlPaginationAdapter<>(selectModel, mapperMethod, offset, limit);
    }

    public static <R> Function<SelectModel, MysqlPaginationAdapter<R>> adapterFunction(
            Function<SelectStatementProvider, R> mapperMethod,
            int offset,
            int limit) {
        return selectModel ->
                MysqlPaginationAdapter.of(selectModel, mapperMethod, offset, limit);

    }

    /**
     *
     @SelectProvider(type=SqlProviderAdapter.class, method="select")
     @Results(id="BaseResult", value = {
     @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
     @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
     @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
     @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
     @Result(column="created_time", property="created_time", jdbcType=JdbcType.TIMESTAMP),
     @Result(column="updated_time", property="updated_time", jdbcType=JdbcType.TIMESTAMP),
     @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR)
     })
     List<User> selectMany(SelectStatementProvider selectStatement);

     default QueryExpressionDSL<MysqlPaginationAdapter<List<User>>> pageable2(){
     return SelectDSL.select(MysqlPaginationAdapter.adapterFunction(this::selectMany, 0, 10),
     id, name, password, status, created_time, updated_time, remark
     ).from(user);

     }

     **/

}