/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intertec.service;

import com.intertec.model.dao.DAO;
import com.intertec.model.pojo.Result;
import com.intertec.model.pojo.User;
import com.intertec.service.impl.ServiceUserImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 *
 * @author William
 */
public class ServiceUserTest {
    
    private static ServiceUser serviceUser;
    private static DAO userDAO;
    private static DAO restrictedDAO;
    
    @Before
    public void setUp() {
        serviceUser = new ServiceUserImpl();
        userDAO = mock(DAO.class);
        restrictedDAO = mock(DAO.class);
        ((ServiceUserImpl) serviceUser).setUserDAO(userDAO);
        ((ServiceUserImpl) serviceUser).setRestrictedDAO(restrictedDAO);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of checkUsername method, of class ServiceUser.
     */
    @Test
    public void testCheckUsernameValidUser() {
        //GIVEN
        String unserNameStr = "validUser";
        Result result = null;
        when(userDAO.findByName(unserNameStr)).thenReturn(null);
        when(userDAO.save(any(User.class))).thenReturn(Boolean.TRUE);
        
        //WHEN
        result = serviceUser.checkUsername(unserNameStr);
        
        //THEN
        verify(userDAO, times(1)).findByName(unserNameStr);
        verify(userDAO, times(1)).save(any(User.class));
        assertNotNull("The result should never be NULL", result);
        assertTrue("Because the user is valid the result shoud be SUCCESS'", result.getSuccess());
    }
    
    @Test
    public void testCheckUsernameUserExists() {
        //GIVEN
        String unserNameStr = "existingUserName";
        Result result = null;
        when(userDAO.findByName(unserNameStr)).thenReturn(new User());
        
        //WHEN
        result = serviceUser.checkUsername(unserNameStr);
        
        //THEN
        verify(userDAO, times(1)).findByName(unserNameStr);
        verify(userDAO, never()).save(any(User.class));
        assertFalse("Because the user already exists the result shoud be NOT SUCCESS'", result.getSuccess());
        assertNotNull("Because the user already exists the result's alternative usernames should not be NULL",
                result.getAltUserNames());
        assertTrue("Because the user already exists the result's alternative usernames should have 14 elements",
                result.getAltUserNames().size() == 14);
    }
    
    
    @Test
    public void testCheckUsernameRestricted() {
        //GIVEN
        String unserNameStr = "restrictedUsername";
        Result result = null;
        User auxUser = new User();
        auxUser.setName("restricted");
        when(restrictedDAO.findByName(unserNameStr)).thenReturn(auxUser);
        
        //WHEN
        result = serviceUser.checkUsername(unserNameStr);
        
        //THEN
        verify(userDAO, never()).findByName(unserNameStr);
        verify(userDAO, never()).save(any(User.class));
        verify(restrictedDAO, atLeastOnce()).findByName(unserNameStr);
        assertFalse("Because the user already exists the result shoud be NOT SUCCESS'", result.getSuccess());
        assertTrue("Because the user contyains a restricted word the result's alternative usernames should have 14 elements",
                result.getAltUserNames().size() == 14);
        System.out.println(result.getAltUserNames());
    }
    
    @Test
    public void testCheckUsernameMoreHints() {
        //GIVEN
        String unserNameStr = "restrictedUglyNice";
        Result result = null;
        User auxUser = new User();
        auxUser.setName("restricted");
        User auxUser2 = new User();
        auxUser2.setName("Ugly");
        when(restrictedDAO.findByName(anyString())).then(new Answer<User>() {
            @Override
            public User answer(InvocationOnMock invocation) throws Throwable {
                String userName = (String) invocation.getArguments()[0];
                if (userName.contains("restricted")) {
                    User user = new User();
                    user.setName("restricted");
                    return user;
                } else if (userName.contains("Ugly")) {
                    User user = new User();
                    user.setName("Ugly");
                    return user;
                } else {
                    return null;
                }
            }
        });
        
        //WHEN
        result = serviceUser.checkUsername(unserNameStr);
        
        //THEN
        verify(userDAO, never()).findByName(unserNameStr);
        verify(userDAO, never()).save(any(User.class));
        verify(restrictedDAO, atLeastOnce()).findByName(unserNameStr);
        assertFalse("Because the user already exists the result shoud be NOT SUCCESS'", result.getSuccess());
        System.out.println("RESULTADO = " + result.getAltUserNames());
        assertTrue("Because the user already exists the result's alternative usernames should have 14 elements",
                result.getAltUserNames().size() < 14 && result.getAltUserNames().size() > 0);
    }
    
}
