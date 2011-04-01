// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.newland.beecode.domain;

import com.newland.beecode.domain.MarketingAct;
import com.newland.beecode.domain.MarketingCatalogDataOnDemand;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

privileged aspect MarketingActDataOnDemand_Roo_DataOnDemand {
    
    declare @type: MarketingActDataOnDemand: @Component;
    
    private Random MarketingActDataOnDemand.rnd = new java.security.SecureRandom();
    
    private List<MarketingAct> MarketingActDataOnDemand.data;
    
    @Autowired
    private MarketingCatalogDataOnDemand MarketingActDataOnDemand.marketingCatalogDataOnDemand;
    
    public MarketingAct MarketingActDataOnDemand.getNewTransientMarketingAct(int index) {
        com.newland.beecode.domain.MarketingAct obj = new com.newland.beecode.domain.MarketingAct();
        java.lang.String actName = "actName_" + index;
        if (actName.length() > 30) {
            actName  = actName.substring(0, 30);
        }
        obj.setActName(actName);
        obj.setStartDate(new java.util.GregorianCalendar(java.util.Calendar.getInstance().get(java.util.Calendar.YEAR), java.util.Calendar.getInstance().get(java.util.Calendar.MONTH), java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH), java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY), java.util.Calendar.getInstance().get(java.util.Calendar.MINUTE), java.util.Calendar.getInstance().get(java.util.Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime());
        obj.setEndDate(new java.util.GregorianCalendar(java.util.Calendar.getInstance().get(java.util.Calendar.YEAR), java.util.Calendar.getInstance().get(java.util.Calendar.MONTH), java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH), java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY), java.util.Calendar.getInstance().get(java.util.Calendar.MINUTE), java.util.Calendar.getInstance().get(java.util.Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime());
        java.lang.String actDetail = "actDetail_" + index;
        if (actDetail.length() > 100) {
            actDetail  = actDetail.substring(0, 100);
        }
        obj.setActDetail(actDetail);
        obj.setGenTime(new java.util.GregorianCalendar(java.util.Calendar.getInstance().get(java.util.Calendar.YEAR), java.util.Calendar.getInstance().get(java.util.Calendar.MONTH), java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH), java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY), java.util.Calendar.getInstance().get(java.util.Calendar.MINUTE), java.util.Calendar.getInstance().get(java.util.Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime());
        obj.setSendTime(new java.util.GregorianCalendar(java.util.Calendar.getInstance().get(java.util.Calendar.YEAR), java.util.Calendar.getInstance().get(java.util.Calendar.MONTH), java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH), java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY), java.util.Calendar.getInstance().get(java.util.Calendar.MINUTE), java.util.Calendar.getInstance().get(java.util.Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime());
        obj.setTimes(new Integer(index));
        java.lang.String checkCode = "checkCode_" + index;
        if (checkCode.length() > 20) {
            checkCode  = checkCode.substring(0, 20);
        }
        obj.setCheckCode(checkCode);
        obj.setRebateRate(new Integer(index).floatValue());
        obj.setAmount(new java.math.BigDecimal(index));
        obj.setActStatus(new Integer(index));
        obj.setCouponSum(new Integer(index).longValue());
        obj.setSendSum(new Integer(index).longValue());
        java.lang.String bizNo = "_" + index;
        if (bizNo.length() > 2) {
            bizNo  = bizNo.substring(0, 2);
        }
        obj.setBizNo(bizNo);
        obj.setMarketingCatalog(marketingCatalogDataOnDemand.getRandomMarketingCatalog());
        obj.setOperNo(new Integer(index).longValue());
        java.lang.String mmsContent = "mmsContent_" + index;
        if (mmsContent.length() > 400) {
            mmsContent  = mmsContent.substring(0, 400);
        }
        obj.setMmsContent(mmsContent);
        java.lang.String mmsTitle = "mmsTitle_" + index;
        if (mmsTitle.length() > 40) {
            mmsTitle  = mmsTitle.substring(0, 40);
        }
        obj.setMmsTitle(mmsTitle);
        java.lang.String bindCard = "_" + index;
        if (bindCard.length() > 2) {
            bindCard  = bindCard.substring(0, 2);
        }
        obj.setBindCard(bindCard);
        java.lang.String fileName = "fileName_" + index;
        if (fileName.length() > 30) {
            fileName  = fileName.substring(0, 30);
        }
        obj.setFileName(fileName);
        return obj;
    }
    
    public MarketingAct MarketingActDataOnDemand.getSpecificMarketingAct(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        MarketingAct obj = data.get(index);
        return MarketingAct.findMarketingAct(obj.getActNo());
    }
    
    public MarketingAct MarketingActDataOnDemand.getRandomMarketingAct() {
        init();
        MarketingAct obj = data.get(rnd.nextInt(data.size()));
        return MarketingAct.findMarketingAct(obj.getActNo());
    }
    
    public boolean MarketingActDataOnDemand.modifyMarketingAct(MarketingAct obj) {
        return false;
    }
    
    public void MarketingActDataOnDemand.init() {
        data = com.newland.beecode.domain.MarketingAct.findMarketingActEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'MarketingAct' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new java.util.ArrayList<com.newland.beecode.domain.MarketingAct>();
        for (int i = 0; i < 10; i++) {
            com.newland.beecode.domain.MarketingAct obj = getNewTransientMarketingAct(i);
            obj.persist();
            obj.flush();
            data.add(obj);
        }
    }
    
}
