/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intertec.model.dao;

import java.util.List;

/**
 *
 * @author William
 */
public interface DAO<E> {
    
    Boolean save(E obj);
    
    Boolean saveMultiple(List objs);

    List selectAll();
    
    Integer countAll();
    
    Object findByName(String name);
    
    Boolean deleteALL();
    
    Object findReverseLike(String name);
}
