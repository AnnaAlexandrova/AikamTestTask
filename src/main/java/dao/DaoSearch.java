package dao;

import controller.ReadingJsonController;
import com.github.tsohr.JSONObject;
import com.github.tsohr.JSONArray;
import com.github.tsohr.JSONTokener;

import java.sql.*;

public class DaoSearch {
    private String dbUrl;
    private String login;
    private String password;
    private String className = "org.postgresql.Driver";

    public DaoSearch() {
        String jsonText = ReadingJsonController.readJsonFile("DbLoginParam.json");

        if (jsonText == null) {
            System.out.println("Ошибка в файле DbLoginParam.json");
        } else {
            JSONObject jsonObject = new JSONObject(new JSONTokener(jsonText));

            this.dbUrl = jsonObject.get("url").toString();
            this.login = jsonObject.get("login").toString();
            this.password = jsonObject.get("password").toString();
        }
    }

    public JSONArray findCustomersWithLastName(JSONObject inputObj) {
        try {
            Class.forName(className);

            Connection connection = DriverManager.getConnection(dbUrl, login, password);

            if (connection == null) {
                System.out.println("Нет соединения с базой данных!");
                return null;
            } else {
                String sqlQuery = "SELECT firstName, lastName FROM customers WHERE lastName = ?;";

                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                statement.setString(1, inputObj.get("lastName").toString());

                return getObjects(statement);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver не найден.");
            return null;
        } catch (SQLException e) {
            System.out.println("Соединение прервано");
            return null;
        }
    }

    public JSONArray findCustomersProductNameByMinTimes(JSONObject inputObj) {
        try {
            Class.forName(className);

            Connection connection = DriverManager.getConnection(dbUrl, login, password);

            if (connection == null) {
                System.out.println("Нет соединения с базой данных!");
                return null;
            } else {
                String sqlQuery = "SELECT customers.id, customers.lastName, customers.firstName " +
                        "FROM  customers, goods, purchases  WHERE goods.id = purchases.goodId " +
                        "AND purchases.customerId = customers.id " +
                        "AND goods.goodName = ? " +
                        "GROUP BY customers.id, customers.lastName, customers.firstName " +
                        "HAVING COUNT (goods.goodName) > ?;";

                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                statement.setString(1, inputObj.get("productName").toString());
                statement.setInt(2, inputObj.getInt("minTimes"));

                return getObjects(statement);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver не найден.");
            return null;
        } catch (SQLException e) {
            System.out.println("Соединение прервано");
            return null;
        }
    }

    public JSONArray findMinMaxCostInterval(JSONObject inputObj) {
        try {
            Class.forName(className);

            Connection connection = DriverManager.getConnection(dbUrl, login, password);

            if (connection == null) {
                System.out.println("Нет соединения с базой данных!");
                return null;
            } else {
                String sqlQuery = "SELECT customers.id, customers.lastName, customers.firstName " +
                        "FROM  customers, goods, purchases  WHERE purchases.customerId = customers.id " +
                        "AND purchases.goodId = goods.id " +
                        "GROUP BY customers.id, customers.lastName, customers.firstName " +
                        "HAVING SUM(goods.price) BETWEEN CAST(? AS money) AND CAST(? AS money);";

                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                statement.setInt(1, inputObj.getInt("minExpenses"));
                statement.setInt(2, inputObj.getInt("maxExpenses"));

                return getObjects(statement);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver не найден.");
            return null;
        } catch (SQLException e) {
            System.out.println("Соединение прервано");
            return null;
        }
    }

    public JSONArray findBadCustomers(JSONObject inputObj) {
        try {
            Class.forName(className);
            Connection connection = DriverManager.getConnection(dbUrl, login, password);

            if (connection == null) {
                System.out.println("Нет соединения с базой данных!");
                return null;
            } else {
                String sqlQuery = "SELECT customers.id, customers.lastName, customers.firstName " +
                        "FROM  customers, goods, purchases  WHERE purchases.customerId = customers.id " +
                        "AND purchases.goodId = goods.id " +
                        "GROUP BY customers.id, customers.lastName, customers.firstName " +
                        "ORDER BY COUNT(goods.goodName) LIMIT ?;";

                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                statement.setInt(1, inputObj.getInt("badCustomers"));

                return getObjects(statement);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver не найден.");
            return null;
        } catch (SQLException e) {
            System.out.println("Соединение прервано");
            return null;
        }
    }

    private JSONArray getObjects(PreparedStatement statement) {
        try {
            ResultSet resultSet = statement.executeQuery();

            JSONArray result = new JSONArray();

            while (resultSet.next()) {
                JSONObject resultObj = new JSONObject();

                resultObj.put("lastName", resultSet.getString("lastName"));
                resultObj.put("firstName", resultSet.getString("firstName"));

                result.put(resultObj);
            }

            return result;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
