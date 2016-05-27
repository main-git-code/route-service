package com.ai.slp.route.service.dubbo.impl;

import com.ai.opt.base.exception.SystemException;
import com.ai.slp.route.common.entity.RuleItem;
import com.ai.slp.route.core.IRouteSwitcher;
import com.ai.slp.route.core.Route;
import com.ai.slp.route.service.core.interfaces.IRouteCoreService;
import com.ai.slp.route.service.core.params.SaleProductInfo;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class RouteCoreServiceImpl implements IRouteCoreService {

    private Logger logger = LogManager.getLogger(RouteCoreServiceImpl.class);

    @Autowired
    private IRouteSwitcher routeGroup;

    @Override
    public String findRoute(SaleProductInfo saleProductInfo) throws SystemException {
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(RuleItem.AMOUNT.getFieldName(), saleProductInfo.getTotalConsumption());
            jsonObject.addProperty(RuleItem.ORDERCOUNT.getFieldName(), 1);
            Route route = routeGroup.switchRoute(saleProductInfo.getTenantId(), saleProductInfo.getRouteGroupId(),
                    jsonObject.toString());
            if (route != null) {
                return route.getRouteId();
            }
            return null;
        } catch (Exception e) {
            logger.error(e);
            throw new SystemException("Failed to find Supplier.", e);
        }
    }
}