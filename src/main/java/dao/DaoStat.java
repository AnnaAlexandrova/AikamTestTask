package dao;

import controller.ReadingJsonController;
import com.github.tsohr.JSONObject;
import com.github.tsohr.JSONArray;
import com.github.tsohr.JSONTokener;

import java.sql.*;

public class DaoStat {
    private String dbUrl;
    private String login;
    private String password;
    private String className = "org.postgresql.Driver";
    private double totalExpenses;
    private double avgExpenses;
    private int customersCount;

    public DaoStat() {
        String jsonText = ReadingJsonController.readJsonFile("DbLoginParam.json");

        if (jsonText == null) {
            System.out.println("Ошибка в файле DbLoginParam.json");
        } else {
            JSONObject jsonObject = new JSONObject(new JSONTokener(jsonText));

            this.dbUrl = jsonObject.get("url").toString();
            this.login = jsonObject.get("login").toString();
            this.password = jsonObject.get("password").toString();
            this.totalExpenses = 0;
            this.avgExpenses = 0;
            this.customersCount = 0;
        }
    }

    public double getTotalExpenses() {
        return totalExpenses;
    }

    public double getAvgExpenses() {
        return avgExpenses;
    }

    public JSONArray getCustomersInfo(JSONObject inputObj) {
        try (Connection connection = DriverManager.getConnection(dbUrl, login, password)) {
            Class.forName(className);

            if (connection == null) {
                System.out.println("Нет соединения с базой данных!");
                return null;
            } else {
                String sqlQuery = "SELECT customers.id, CONCAT (customers.lastName, ' ', customers.firstName) AS customerName, " +
                        "CAST(SUM(goods.price) AS numeric) AS totalSum FROM  customers, goods, purchases  " +
                        "WHERE purchases.customerId = customers.id " +
                        "AND purchases.goodId = goods.id " +
                        "AND purchases.purchaseDate BETWEEN CAST (? AS date) AND CAST (? AS date) " +
                        "GROUP BY customers.id, customers.lastName, customers.firstName " +
                        "ORDER BY SUM(goods.price) DESC;";

                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                statement.setString(1, inputObj.get("startDate").toString());
                statement.setString(2, inputObj.get("endDate").toString());

                ResultSet resultSet = statement.executeQuery();

                JSONArray result = new JSONArray();

                while (resultSet.next()) {
                    JSONObject resultObj = new JSONObject();
                    int customerId = resultSet.getInt("id");

                    resultObj.put("name", resultSet.getString("customerName"));

                    JSONArray jsonArray = getPurchasesInfo(customerId, inputObj);
                    if (jsonArray == null) {
                        return null;
                    }
                    resultObj.put("purchases", jsonArray);

                    double totalSum = resultSet.getDouble("totalSum");
                    resultObj.put("totalExpenses", totalSum);

                    customersCount++;
                    totalExpenses += totalSum;

                    result.put(resultObj);
                }

                if (customersCount == 0) {
                    avgExpenses = 0;
                } else {
                    avgExpenses = totalExpenses / customersCount;
                }

                return result;
            }
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver не найден.");
            return null;
        } catch (SQLException e) {
            System.out.println("Соединение прервано");
            return null;
        }
    }

    private JSONArray getPurchasesInfo(int customerId, JSONObject inputObj) {
        try (Connection connection = DriverManager.getConnection(dbUrl, login, password)) {
            Class.forName(className);

            if (connection == null) {
                System.out.println("Нет соединения с базой данных!");
                return null;
            } else {
                String sqlQuery = "SELECT goods.goodName, CAST(SUM(goods.price) AS numeric) AS price " +
                        "FROM  customers, goods, purchases  " +
                        "WHERE customers.id = ? " +
                        "AND purchases.customerId = customers.id " +
                        "AND purchases.goodId = goods.id " +
                        "AND purchases.purchaseDate BETWEEN CAST (? AS date) AND CAST (? AS date) " +
                        "GROUP BY goods.goodName " +
                        "ORDER BY SUM(goods.price) DESC;";

                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                statement.setInt(1, customerId);
                statement.setString(2, inputObj.get("startDate").toString());
                statement.setString(3, inputObj.get("endDate").toString());

                ResultSet resultSet = statement.executeQuery();

                JSONArray result = new JSONArray();

                while (resultSet.next()) {
                    JSONObject resultObj = new JSONObject();

                    resultObj.put("name", resultSet.getString("goodName"));
                    resultObj.put("expenses", resultSet.getString("price"));

                    result.put(resultObj);
                }

                return result;
            }
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver не найден.");
            return null;
        } catch (SQLException e) {
            System.out.println("Соединение прервано");
            return null;
        }
    }
}
