package com.nari.jydw.jytest.interfaceTest.actions;

import java.util.Map;

public interface ActionQueryDBRequestBuilder extends ActionParameterBuilder<ActionQueryDBRequest> {
    /**
     * This parameter is must for the action, If paramSql is empty, paramTableName must have value.
     * @param paramSql which the table will be queried.
     * @return
     */
    ActionQueryDBRequestBuilder paramSql(String paramSql);

    /**
     * This parameter is must for the action, If paramTableName is empty, paramSql must have value.
     * @param paramTableName which the table will be queried.
     * @return
     */
    ActionQueryDBRequestBuilder paramTableName(String paramTableName);

    /**
     * This parameter is must for the action, if it is null, it will be true.
     * @param expectedIsResultExist Expected result exists or not.
     * @return
     */
    ActionQueryDBRequestBuilder expectedIsResultExist(boolean expectedIsResultExist);

    /**
     * This parameter is must for the action, if it is null, cannot query anything from DB.
     * @param expectedKey and expectedValue means expected key and value in DB.
     * @return
     */
    ActionQueryDBRequestBuilder expectedResult(String expectedKey, Object expectedValue);
}
