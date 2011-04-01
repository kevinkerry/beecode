// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.newland.beecode.domain;

import com.newland.beecode.domain.OperDataOnDemand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect OperIntegrationTest_Roo_IntegrationTest {
    
    declare @type: OperIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: OperIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml");
    
    declare @type: OperIntegrationTest: @Transactional;
    
    @Autowired
    private OperDataOnDemand OperIntegrationTest.dod;
    
    @Test
    public void OperIntegrationTest.testCountOpers() {
        org.junit.Assert.assertNotNull("Data on demand for 'Oper' failed to initialize correctly", dod.getRandomOper());
        long count = com.newland.beecode.domain.Oper.countOpers();
        org.junit.Assert.assertTrue("Counter for 'Oper' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void OperIntegrationTest.testFindOper() {
        com.newland.beecode.domain.Oper obj = dod.getRandomOper();
        org.junit.Assert.assertNotNull("Data on demand for 'Oper' failed to initialize correctly", obj);
        java.lang.Long id = obj.getOperNo();
        org.junit.Assert.assertNotNull("Data on demand for 'Oper' failed to provide an identifier", id);
        obj = com.newland.beecode.domain.Oper.findOper(id);
        org.junit.Assert.assertNotNull("Find method for 'Oper' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'Oper' returned the incorrect identifier", id, obj.getOperNo());
    }
    
    @Test
    public void OperIntegrationTest.testFindAllOpers() {
        org.junit.Assert.assertNotNull("Data on demand for 'Oper' failed to initialize correctly", dod.getRandomOper());
        long count = com.newland.beecode.domain.Oper.countOpers();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'Oper', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List<com.newland.beecode.domain.Oper> result = com.newland.beecode.domain.Oper.findAllOpers();
        org.junit.Assert.assertNotNull("Find all method for 'Oper' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'Oper' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void OperIntegrationTest.testFindOperEntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'Oper' failed to initialize correctly", dod.getRandomOper());
        long count = com.newland.beecode.domain.Oper.countOpers();
        if (count > 20) count = 20;
        java.util.List<com.newland.beecode.domain.Oper> result = com.newland.beecode.domain.Oper.findOperEntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'Oper' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'Oper' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void OperIntegrationTest.testFlush() {
        com.newland.beecode.domain.Oper obj = dod.getRandomOper();
        org.junit.Assert.assertNotNull("Data on demand for 'Oper' failed to initialize correctly", obj);
        java.lang.Long id = obj.getOperNo();
        org.junit.Assert.assertNotNull("Data on demand for 'Oper' failed to provide an identifier", id);
        obj = com.newland.beecode.domain.Oper.findOper(id);
        org.junit.Assert.assertNotNull("Find method for 'Oper' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyOper(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'Oper' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void OperIntegrationTest.testMerge() {
        com.newland.beecode.domain.Oper obj = dod.getRandomOper();
        org.junit.Assert.assertNotNull("Data on demand for 'Oper' failed to initialize correctly", obj);
        java.lang.Long id = obj.getOperNo();
        org.junit.Assert.assertNotNull("Data on demand for 'Oper' failed to provide an identifier", id);
        obj = com.newland.beecode.domain.Oper.findOper(id);
        boolean modified =  dod.modifyOper(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        com.newland.beecode.domain.Oper merged = (com.newland.beecode.domain.Oper) obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getOperNo(), id);
        org.junit.Assert.assertTrue("Version for 'Oper' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void OperIntegrationTest.testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'Oper' failed to initialize correctly", dod.getRandomOper());
        com.newland.beecode.domain.Oper obj = dod.getNewTransientOper(Integer.MAX_VALUE);
        org.junit.Assert.assertNotNull("Data on demand for 'Oper' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'Oper' identifier to be null", obj.getOperNo());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'Oper' identifier to no longer be null", obj.getOperNo());
    }
    
    @Test
    public void OperIntegrationTest.testRemove() {
        com.newland.beecode.domain.Oper obj = dod.getRandomOper();
        org.junit.Assert.assertNotNull("Data on demand for 'Oper' failed to initialize correctly", obj);
        java.lang.Long id = obj.getOperNo();
        org.junit.Assert.assertNotNull("Data on demand for 'Oper' failed to provide an identifier", id);
        obj = com.newland.beecode.domain.Oper.findOper(id);
        obj.remove();
        obj.flush();
        org.junit.Assert.assertNull("Failed to remove 'Oper' with identifier '" + id + "'", com.newland.beecode.domain.Oper.findOper(id));
    }
    
}
