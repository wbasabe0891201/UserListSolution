package com.intertec.service.impl;

import com.intertec.model.dao.DAO;
import com.intertec.model.pojo.Result;
import com.intertec.model.pojo.User;
import com.intertec.service.ServiceUser;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author William
 */
public class ServiceUserImpl implements ServiceUser {

    private DAO userDAO;
    private DAO restrictedDAO;

    private String username;

    private Integer generationTimes = 4;

    @Override
    public Result checkUsername(String username) {
        Result result = new Result();
        this.username = username;

        result.setSuccess(checkUsernameValid());

        if (result.getSuccess()) {
            User user = new User();
            user.setName(username);
            userDAO.save(user);
        } else {
            result.setAltUserNames(generateAltUsernames(null));
        }

        return result;
    }

    private Boolean checkUserNameExists() {
        return userDAO.findByName(username) == null;
    }

    private Boolean checkUsernameValid() {
        User user = (User) restrictedDAO.findByName(username);
        if (user != null) {
            username = username.replace(user.getName(), "");
            return false;
        } else {
            return checkUserNameExists();
        }
    }

    private List<String> generateAltUsernames(List<String> alternativesNames) {
        List<String> altUserNames = new ArrayList<>();
        if (generationTimes-- > 0) {
            if (alternativesNames == null) {
                alternativesNames = new ArrayList<>();
                //numbered usernames
                for (int i = 0; i < 14; i++) {
                    alternativesNames.add(new StringBuilder(username).append(i).toString());
                }
            } else {
                switch (generationTimes) {
                    case 2:
                        //repeat username
                        System.out.println("ENTRO A REPETIR NOMBRES");
                        alternativesNames.add(username + username);
                        alternativesNames.add(username + "_" + username);
                        alternativesNames.add(username + "-" + username);
                        break;
                    case 1:
                        //yeared username
                        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
                        alternativesNames.add(username + year);
                        alternativesNames.add(username + "_" + year);
                        alternativesNames.add(username + "-" + year);
                        alternativesNames.add(year + username);
                        alternativesNames.add(year + "_" + username);
                        alternativesNames.add(year + "-" + username);
                        break;
                }
            }

            //check alternative usernames
            for (Iterator<String> iterator = alternativesNames.iterator(); iterator.hasNext();) {
                String next = iterator.next();
                this.username = next;
                if (!checkUsernameValid()) {
                    iterator.remove();
                }
            }
            if (alternativesNames.size() < 14) {
                generateAltUsernames(alternativesNames);
            }
        }

        altUserNames = alternativesNames;

        return altUserNames;
    }

    public DAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(DAO userDAO) {
        this.userDAO = userDAO;
    }

    public DAO getRestrictedDAO() {
        return restrictedDAO;
    }

    public void setRestrictedDAO(DAO restrictedDAO) {
        this.restrictedDAO = restrictedDAO;
    }

}
