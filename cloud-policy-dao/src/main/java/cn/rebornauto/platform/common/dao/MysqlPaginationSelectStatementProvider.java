package cn.rebornauto.platform.common.dao;

import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;

import java.util.HashMap;
import java.util.Map;

import static org.mybatis.dynamic.sql.util.StringUtilities.spaceBefore;

public class MysqlPaginationSelectStatementProvider implements SelectStatementProvider {
    private Map<String, Object> parameters = new HashMap<>();
    private String selectStatement;

    public MysqlPaginationSelectStatementProvider(SelectStatementProvider delegate,
                                                  long offset,
                                                  long limit) {
        parameters.putAll(delegate.getParameters());
        parameters.put("p_offset", offset);
        parameters.put("p_limit", limit);
        String pagestr = "limit #{parameters.p_limit} offset #{parameters.p_offset}";
        selectStatement = delegate.getSelectStatement() +spaceBefore(pagestr);
    }

    @Override
    public Map<String, Object> getParameters() {
        return parameters;
    }

    @Override
    public String getSelectStatement() {
        return selectStatement;
    }
}