/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intertec.service;

import com.intertec.model.dao.DAO;
import com.intertec.model.pojo.Restricted;
import com.intertec.model.pojo.Result;
import com.intertec.service.impl.ServiceRestrictedImpl;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author William
 */
public class ServiceRestrictedTest {
    
    private static ServiceRestricted serviceRestricted;
    private static DAO restrictedDAO;
    
    public ServiceRestrictedTest() {
    }
    
    @Before
    public void setUp() {
        serviceRestricted = new ServiceRestrictedImpl();
        restrictedDAO = mock(DAO.class);
        ((ServiceRestrictedImpl) serviceRestricted).setRestrictedDAO(restrictedDAO);
    }
    
    /**
     * Test of insertNewRestriction method, of class ServiceRestricted.
     */
    @Test
    public void testInsertNewRestriction() {
        //GIVEN
        String unserNameStr = "validUser";
        Result result = null;
        when(restrictedDAO.findByName(unserNameStr)).thenReturn(null);
        when(restrictedDAO.save(any(Restricted.class))).thenReturn(Boolean.TRUE);
        
        //WHEN
        result = serviceRestricted.insertNewRestriction(unserNameStr);
        
        //THEN
        verify(restrictedDAO, times(1)).findByName(unserNameStr);
        verify(restrictedDAO, times(1)).save(any(Restricted.class));
        assertNotNull("The result should never be NULL", result);
        assertTrue("Because the restricted word does not exist in db result shoud be SUCCESS'", result.getSuccess());
    }
    
    @Test
    public void testInsertNewRestrictionExists() {
        //GIVEN
        String unserNameStr = "validUser";
        Result result = null;
        when(restrictedDAO.findByName(unserNameStr)).thenReturn(new Restricted());
        
        //WHEN
        result = serviceRestricted.insertNewRestriction(unserNameStr);
        
        //THEN
        verify(restrictedDAO, times(1)).findByName(unserNameStr);
        verify(restrictedDAO, never()).save(any(Restricted.class));
        assertNotNull("The result should never be NULL", result);
        assertFalse("Because the restricted word already exists in db the result should SUCCESS should be FALSE'", result.getSuccess());
        assertTrue("Because the restricted word already exists in db the result should contain 1 message", result.getAltUserNames().size() == 1);
    }

    
}
