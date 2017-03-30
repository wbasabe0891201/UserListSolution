/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intertec.model.dao;

import com.intertec.model.pojo.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author William
 */
public abstract class NameBaseDAO<E> implements DAO {
    
    private DataSource dataSource;
    protected Connection con;

    public DataSource getDataSource() {
        return dataSource;
    }
    
    protected String INSERT_QUERY = "INSERT INTO <table_name> (name) values(?)";
    protected String SELECT_ALL_QUERY = "SELECT * FROM <table_name>";
    protected String SELECT_BY_NAME_QUERY = SELECT_ALL_QUERY + " WHERE name = ?";
    protected String COUNT_ALL_QUERY = "SELECT COUNT(*) FROM <table_name>";
    protected String DELETE_ALL_QUERY = "DELETE FROM <table_name>";
    protected String SELECT_BY_REVERSE_LIKE_QUERY = SELECT_ALL_QUERY + " WHERE ? like concat('%', name,'%')";
    
    protected String replaceTableName(String queryStr, String tableName) {
        return queryStr.replace("<table_name>", tableName);
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        try {
            con = dataSource.getConnection();
            if(con == null) {
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    @Override
    public Boolean save(Object obj) {
        try {
            PreparedStatement ps = con.prepareStatement(INSERT_QUERY);
            ps.setString(1, (String) obj.getClass().getMethod("getName").invoke(obj));
            return ps.executeUpdate() > 0;
        } catch (Exception ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @Override
    public Boolean saveMultiple(List objs) {
        try {
            PreparedStatement ps = con.prepareStatement(INSERT_QUERY);
        
            for (E obj : (List<E>)objs) {
                ps.setString(1,  (String) obj.getClass().getMethod("getName").invoke(obj));
                ps.addBatch();
            }
            ps.executeBatch();
            return true;
        } catch(Exception ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @Override
    public List<User> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public Integer countAll() {
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(COUNT_ALL_QUERY);
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return 0;
            }
        } catch(Exception ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        //never shold pass over here
        return -1;
    }

    @Override
    public Boolean deleteALL() {
        try {
            Statement stm = con.createStatement();
            return stm.executeUpdate(DELETE_ALL_QUERY) > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    
}
