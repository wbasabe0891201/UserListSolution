/*  
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intertec.utils;

import com.intertec.model.dao.DAO;
import com.intertec.model.dao.RestrictedDAO;
import com.intertec.model.dao.UserDAO;
import com.intertec.model.pojo.Restricted;
import com.intertec.model.pojo.User;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author William
 */
public class InitDB {
    private Connection con;
    

    public InitDB(DataSource dataSource, DAO userDAO, DAO restrictedDAO) {
        try {
            con = dataSource.getConnection();

            Statement stm = con.createStatement();
            stm.execute("create table if not exists username ("
                    + "name varchar(30) primary key)");

            if (userDAO.countAll() <= 0) {

                userDAO.deleteALL();

                List<User> users = new ArrayList<>();
                User user = new User();
                user.setName("William");
                users.add(user);

                user = new User();
                user.setName("Maylin");
                users.add(user);

                user = new User();
                user.setName("Paola");
                users.add(user);

                userDAO.saveMultiple(users);
            }

            stm.execute("create table if not exists restricted ("
                    + "name varchar(30) primary key)");

            if (restrictedDAO.countAll() <= 0) {

                restrictedDAO.deleteALL();

                List<Restricted> restricteds = new ArrayList();
                Restricted restricted = new Restricted();
                restricted.setName("cannabis");
                restricteds.add(restricted);

                restricted = new Restricted();
                restricted.setName("abuse");
                restricteds.add(restricted);

                restricted = new Restricted();
                restricted.setName("crack");
                restricteds.add(restricted);

                restricted = new Restricted();
                restricted.setName("damn");
                restricteds.add(restricted);

                restricted = new Restricted();
                restricted.setName("drunk");
                restricteds.add(restricted);

                restricted = new Restricted();
                restricted.setName("grass");
                restricteds.add(restricted);

                restrictedDAO.saveMultiple(restricteds);
            }

        } catch (SQLException ex) {
            Logger.getLogger(InitDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(InitDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
