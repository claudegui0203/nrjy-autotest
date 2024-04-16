package com.nari.jydw.jytest.interfaceTest.utils;

import com.nari.jydw.jytest.common.TestParametersUtil;
import lombok.Getter;
import org.testng.Assert;
import java.sql.*;

@Getter
public class DBUtil {
    private ResultSet resultSet;
    private final Connection connection;
    private final Statement statement;
    private static DBUtil instance;

    private DBUtil() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String url = "jdbc:mysql://" + TestParametersUtil.getInstance().getTestParameters().getDBUrl() + "/fuse_system?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&rewriteBatchedStatements=true";
        String root = TestParametersUtil.getInstance().getTestParameters().getDBUser();
        String password = TestParametersUtil.getInstance().getTestParameters().getDBPassword();

        if ((TestParametersUtil.getInstance().getTestParameters().getDBUser().isEmpty()) || (TestParametersUtil.getInstance().getTestParameters().getDBPassword().isEmpty())) {
            Assert.fail("DBUtil::Cannot get DB info from configuration!!");
        }

        try {
            connection = DriverManager.getConnection(url, root, password);
            statement = (Statement) connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DBUtil getInstance() {
        if (instance == null) {
            instance = new DBUtil();
        }
        return instance;
    }

    public Boolean ExecuteSqlNoResult(String sql) {
        try {
            return statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet ExecuteSql(String sql) {
        try {
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
