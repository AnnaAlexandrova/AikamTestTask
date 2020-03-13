package model;

import dao.DaoStat;
import com.github.tsohr.JSONObject;
import com.github.tsohr.JSONArray;

import java.sql.Date;

public class Stat {
    public JSONObject getStat(JSONObject inputObj) {
        JSONObject result = new JSONObject();

        result.put("type", "stat");

        long totalDaysCount = getTotalDays(inputObj);
        result.put("totalDays", totalDaysCount);


        DaoStat daoStat = new DaoStat();
        JSONArray customers = daoStat.getCustomersInfo(inputObj);
        result.put("customers", customers);


        result.put("totalExpenses", daoStat.getTotalExpenses());
        result.put("avgExpenses", daoStat.getAvgExpenses());

        return result;
    }

    private long getTotalDays(JSONObject inputObj) {
        Date date1 = Date.valueOf(inputObj.get("startDate").toString());
        Date date2 = Date.valueOf(inputObj.get("endDate").toString());

        long dateResult = date2.getTime() - date1.getTime();
        return dateResult / 1000 / 3600 / 24;
    }
}
