package com.intertec.service.impl;

import com.intertec.model.dao.DAO;
import com.intertec.model.pojo.Restricted;
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
        Restricted restricted = (Restricted) restrictedDAO.findReverseLike(username);
        if (restricted != null) {
            username = username.replace(restricted.getName(), "");
            return false;
        } else {
            Boolean checkUserNameExists = checkUserNameExists();
            if (!checkUserNameExists) {
                System.out.println("POR QUE EXISTIA " + username);
            } else {
                System.out.println("QUE VERGA ES ESTA " + username);
            }
            return checkUserNameExists;
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
                    System.out.println("SACO A " + username);
                    iterator.remove();
                }
            }
            if (alternativesNames.size() < 14) {
                generateAltUsernames(alternativesNames);
            }
        }

        if (alternativesNames.size() > 14) {
            altUserNames = alternativesNames.subList(0, 13);
        } else {
            altUserNames = alternativesNames;
        }
        
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
