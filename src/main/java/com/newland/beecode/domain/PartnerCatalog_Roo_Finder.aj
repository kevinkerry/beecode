// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.newland.beecode.domain;

import com.newland.beecode.domain.PartnerCatalog;
import java.lang.String;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

privileged aspect PartnerCatalog_Roo_Finder {
    
    public static TypedQuery<PartnerCatalog> PartnerCatalog.findPartnerCatalogsByCatalogName(String catalogName) {
        if (catalogName == null || catalogName.length() == 0) throw new IllegalArgumentException("The catalogName argument is required");
        EntityManager em = PartnerCatalog.entityManager();
        TypedQuery<PartnerCatalog> q = em.createQuery("SELECT PartnerCatalog FROM PartnerCatalog AS partnercatalog WHERE partnercatalog.catalogName = :catalogName", PartnerCatalog.class);
        q.setParameter("catalogName", catalogName);
        return q;
    }
    
}
