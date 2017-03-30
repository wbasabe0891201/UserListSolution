/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intertec.model.dao;

import com.intertec.model.pojo.Restricted;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author William
 */
public class RestrictedDAO extends NameBaseDAO {
    
    private static final String TABLE_NAME = "restricted";
    public RestrictedDAO() {
        INSERT_QUERY = replaceTableName(INSERT_QUERY, TABLE_NAME);
        DELETE_ALL_QUERY = replaceTableName(DELETE_ALL_QUERY, TABLE_NAME);
        SELECT_ALL_QUERY = replaceTableName(SELECT_ALL_QUERY, TABLE_NAME);
        COUNT_ALL_QUERY = replaceTableName(COUNT_ALL_QUERY, TABLE_NAME);
        SELECT_BY_NAME_QUERY = replaceTableName(SELECT_BY_NAME_QUERY, TABLE_NAME);
        SELECT_BY_REVERSE_LIKE_QUERY = replaceTableName(SELECT_BY_REVERSE_LIKE_QUERY, TABLE_NAME);
    }
    
    @Override
    public Restricted findByName(String name) {
        try {
            PreparedStatement ps = con.prepareStatement(SELECT_BY_NAME_QUERY);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                Restricted restricted = new Restricted();
                restricted.setName(rs.getString(1));
                return restricted;
            } else {
                return null;
            }
        } catch (Exception ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Object findReverseLike(String name) {
        try {
            PreparedStatement ps = con.prepareStatement(SELECT_BY_REVERSE_LIKE_QUERY);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                Restricted user = new Restricted();
                user.setName(rs.getString(1));
                return user;
            } else {
                return null;
            }
        } catch (Exception ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    

}
