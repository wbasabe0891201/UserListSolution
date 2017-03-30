/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intertec.service.impl;

import com.intertec.model.dao.DAO;
import com.intertec.model.pojo.Restricted;
import com.intertec.model.pojo.Result;
import java.util.Collections;

/**
 *
 * @author William
 */
public class ServiceRestrictedImpl implements com.intertec.service.ServiceRestricted {
    
    private DAO restrictedDAO;

    @Override
    public Result insertNewRestriction(String restricted) {
        Result result = new Result();
        
        if (restrictedDAO.findByName(restricted) == null) {
            Restricted restrictedObj = new Restricted();
            restrictedObj.setName(restricted);
            result.setSuccess(restrictedDAO.save(restrictedObj));
        } else {
            result.setSuccess(Boolean.FALSE);
            result.setAltUserNames(Collections.singletonList("The restricted word already exists!"));
        }
        
        return result;
    }

    public DAO getRestrictedDAO() {
        return restrictedDAO;
    }

    public void setRestrictedDAO(DAO restrictedDAO) {
        this.restrictedDAO = restrictedDAO;
    }
}
