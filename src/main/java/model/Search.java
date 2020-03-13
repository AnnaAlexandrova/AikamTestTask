package model;

import dao.DaoSearch;
import com.github.tsohr.JSONObject;
import com.github.tsohr.JSONArray;

public class Search {
    private String[][] criteria = {
            {"lastName"},
            {"productName", "minTimes"},
            {"minExpenses", "maxExpenses"},
            {"badCustomers"}
    };

    public JSONObject getSearchResult(JSONArray inputArray) {
        JSONObject result = new JSONObject();

        result.put("type", "search");
        result.put("results", createResultArray(inputArray));

        return result;
    }

    private JSONArray createResultArray(JSONArray jsonArray) {
        JSONArray results = new JSONArray();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject criterion = new JSONObject();
            JSONObject currentObj = jsonArray.getJSONObject(i);

            criterion.put("criterion", currentObj);

            String criterionToString = criterionToString(currentObj);

            JSONArray criteriaResults = putResults(criterionToString, currentObj);
            if (criteriaResults == null) {
                return null;
            }

            criterion.put("results", criteriaResults);

            results.put(criterion);
        }

        return results;
    }

    private JSONArray putResults(String criterion, JSONObject jsonObject) {
        DaoSearch daoSearch = new DaoSearch();

        switch (criterion) {
            case "lastName;":
                return daoSearch.findCustomersWithLastName(jsonObject);
            case "productName;minTimes;":
                return daoSearch.findCustomersProductNameByMinTimes(jsonObject);
            case "minExpenses;maxExpenses;":
                return daoSearch.findMinMaxCostInterval(jsonObject);
            case "badCustomers;":
                return daoSearch.findBadCustomers(jsonObject);
            default:
                return null;
        }
    }

    private String criterionToString(JSONObject jsonObject) {
        StringBuilder result = new StringBuilder();
        for (String[] criterion : criteria) {
            for (String s : criterion) {
                if (jsonObject.toString().contains(s)) {
                    result.append(s).append(";");
                }
            }
        }
        return result.toString();
    }
}
