/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newland.beecode.dao;

import com.intensoft.dao.hibernate.SimpleHibernateTemplate;
import com.newland.beecode.domain.Roles;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author skylai
 */
@Repository("roleDao")
public class RoleDao extends SimpleHibernateTemplate<Roles, Long> {

    public List<Roles> findRolesEntries(int firstResult, int maxResults) {

        Query query = createQuery("select o from MarketingAct o");
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);

        return query.list();

    }
    public long countRoleses() {
        //return entityManager().createQuery("select count(o) from Roles o", Long.class).getSingleResult();
        return this.findLong("select count(o) from Roles o");
    }
}
