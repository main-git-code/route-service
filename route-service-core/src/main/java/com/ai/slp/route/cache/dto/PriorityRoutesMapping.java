package com.ai.slp.route.cache.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 路由组的优先级和路由对应信息
 * Created by xin on 16-4-29.
 */
public class PriorityRoutesMapping {
    private String priorityNumber;
    private List<Route> routeList;

    public PriorityRoutesMapping(String priorityNumber) {
        this.priorityNumber = priorityNumber;
        this.routeList = new ArrayList<Route>();
    }

    public List<Route> getRouteList() {
        return routeList;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriorityRoutesMapping mapping = (PriorityRoutesMapping) o;
        return priorityNumber.equals(mapping.priorityNumber);

    }

    @Override
    public int hashCode() {
        return priorityNumber.hashCode();
    }

    public void addRoute(Route route) {
        this.routeList.add(route);
    }

    public String getPriorityNumber() {
        return this.priorityNumber;
    }

    public String appendAllRouteIds() {
        StringBuilder routedIds = new StringBuilder();
        for (Route route : routeList) {
            routedIds.append(route.getRouteId() + ",");
        }

        if (routedIds.length() > 0) {
            routedIds.deleteCharAt(routedIds.length() - 1);
        }

        return routedIds.toString();
    }

    public void refreshAllRoutesCache() {
        for (Route route : routeList) {
            // 路由要是没有规则，则不需要加入到Redis中
            route.refreshCache();
        }
    }
}
