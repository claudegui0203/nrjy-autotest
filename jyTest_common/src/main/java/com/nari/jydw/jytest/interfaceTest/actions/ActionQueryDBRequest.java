package com.nari.jydw.jytest.interfaceTest.actions;

import com.nari.jydw.jytest.interfaceTest.utils.DBUtil;
import com.nari.jydw.jytest.interfaceTest.utils.LogUtil;
import org.testng.Assert;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class ActionQueryDBRequest extends AbstractTestAction {
    private String paramSql = "";
    private String paramTableName = "";
    private boolean expectedIsResultExist = true;
    private Map<String, Object> expectedResult = new HashMap<>();
    private ResultSet resultSet;

    @Override
    protected void prepareParameters() {
        if ((paramSql.isEmpty()) && (this.paramTableName.isEmpty())) {
            Assert.fail("ActionQueryDBRequest: Parameters paramSql and paramTableName cannot be empty at same time!!!");
        }

        if (expectedResult == null) {
            Assert.fail("ActionQueryDBRequest: Parameters expectedField cannot be null!!!");
        }

        if (paramSql.isEmpty()) {
            paramSql = String.valueOf(generateQuerySql());
        }
    }

    @Override
    protected void sendRequest() {
        LogUtil.info("ActionQueryDBRequest: Query SQL = " + paramSql);
        resultSet = DBUtil.getInstance().ExecuteSql(paramSql);
    }

    @Override
    protected void verify() {
        LogUtil.info("verify: Excepted result = " + expectedResult.toString());

        try {
            if (expectedIsResultExist) {
                Assert.assertTrue(isMapContained(transferResultSet2Map()), "ActionQueryDBRequest: Expected there is record in DB. But cannot find from DB!!!");
            } else {
                Assert.assertFalse(isMapContained(transferResultSet2Map()), "ActionQueryDBRequest: Expected no record in DB. But there is record from DB!!!");
            }

            resultSet.close();
        } catch (SQLException e) {
            LogUtil.error("Transfer resultSet to map fail. Reason = " + e.getMessage());
        }
    }

    private String mergeQuerySql(String sql, String key, Object value, boolean isFirst) {
        if (! isFirst) {
            sql = sql + " AND ";
        }

        if (value instanceof String) {
            String expectedValue = (String) value;
            sql = sql + key + "=\"" + expectedValue + "\"";
        } else if (value instanceof Integer) {
            int expectedValue = (int) value;
            sql = sql + key + "=" + expectedValue;
        } else if (value instanceof Long) {
            Long expectedValue = (Long) value;
            sql = sql + key + "=" + expectedValue;
        } else {
            sql = sql + key +  "=" + value.toString();
        }

        return sql;
    }

    private AtomicReference<String> generateQuerySql() {
        AtomicBoolean isFirst = new AtomicBoolean(true);
        AtomicReference<String> sql = new AtomicReference<>("SELECT * FROM " + paramTableName + " WHERE ");
        expectedResult.forEach((key, value) -> {
            sql.set(mergeQuerySql(sql.get(), key, value, isFirst.get()));

            if (isFirst.get())
                isFirst.set(false);
        });

        return sql;
    }

    private Map<String, Object> transferResultSet2Map() throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        Map<String, Object> queryResult = new HashMap<>();

        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                Object columnValue = resultSet.getObject(i);
                queryResult.put(columnName, columnValue);
            }
        }

        LogUtil.info("transferResultSet2Map: After transfer, resultSet map contact " + queryResult.toString());
        return queryResult;
    }

    private boolean isMapContained(Map<String, Object> queryResult) {
        for (Map.Entry<String, Object> entry : expectedResult.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (! queryResult.containsKey(key) || ! queryResult.containsValue(value)) {
                LogUtil.info("isMapContained: Because query DB result is not include key " + key + " or value " + value + " , return false!");
                return false;
            }
        }

        return true;
    }
}
