/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intertec.model.pojo;

import java.util.List;

/**
 *
 * @author William
 */
public class Result {
    
    private Boolean success;
    private List<String> altUserNames;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<String> getAltUserNames() {
        return altUserNames;
    }

    public void setAltUserNames(List<String> altUserNames) {
        this.altUserNames = altUserNames;
    }
    
}
