// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.newland.beecode.domain;

import com.newland.beecode.domain.Organization;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Component;

privileged aspect OrganizationDataOnDemand_Roo_DataOnDemand {
    
    declare @type: OrganizationDataOnDemand: @Component;
    
    private Random OrganizationDataOnDemand.rnd = new java.security.SecureRandom();
    
    private List<Organization> OrganizationDataOnDemand.data;
    
    public Organization OrganizationDataOnDemand.getNewTransientOrganization(int index) {
        com.newland.beecode.domain.Organization obj = new com.newland.beecode.domain.Organization();
        java.lang.String organizationDN = "organizationDN_" + index;
        if (organizationDN.length() > 16) {
            organizationDN  = organizationDN.substring(0, 16);
        }
        obj.setOrganizationDN(organizationDN);
        java.lang.String organizationCN = "organizationCN_" + index;
        if (organizationCN.length() > 32) {
            organizationCN  = organizationCN.substring(0, 32);
        }
        obj.setOrganizationCN(organizationCN);
        return obj;
    }
    
    public Organization OrganizationDataOnDemand.getSpecificOrganization(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        Organization obj = data.get(index);
        return Organization.findOrganization(obj.getId());
    }
    
    public Organization OrganizationDataOnDemand.getRandomOrganization() {
        init();
        Organization obj = data.get(rnd.nextInt(data.size()));
        return Organization.findOrganization(obj.getId());
    }
    
    public boolean OrganizationDataOnDemand.modifyOrganization(Organization obj) {
        return false;
    }
    
    public void OrganizationDataOnDemand.init() {
        data = com.newland.beecode.domain.Organization.findOrganizationEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'Organization' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new java.util.ArrayList<com.newland.beecode.domain.Organization>();
        for (int i = 0; i < 10; i++) {
            com.newland.beecode.domain.Organization obj = getNewTransientOrganization(i);
            obj.persist();
            obj.flush();
            data.add(obj);
        }
    }
    
}
