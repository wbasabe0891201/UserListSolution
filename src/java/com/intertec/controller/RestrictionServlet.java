/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intertec.controller;

import com.intertec.model.pojo.Result;
import com.intertec.service.ServiceRestricted;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author William
 */
@WebServlet(name = "RestrictionServlet", urlPatterns = {"/RestrictionServlet"})
public class RestrictionServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());

        StringBuilder responseStr = new StringBuilder();
        responseStr.append("{ ");
        
        String restricted = request.getParameter("restricted");
        
        ServiceRestricted serviceRestrricted = (ServiceRestricted) context.getBean("serviceRestricted");
        Result result = serviceRestrricted.insertNewRestriction(restricted);
        
        responseStr.append("\"success\": ").append(result.getSuccess());
        
        
        if (!result.getSuccess()) {
            responseStr.append(", \"message\": [ ");
            boolean first = true;
            for (String altUserName : result.getAltUserNames()) {
                if (!first) {
                    responseStr.append(", ");
                } else {
                    first = false;
                }
                responseStr.append("\"").append(altUserName).append("\"");
            }
            responseStr.append(" ]");
        }
        
        responseStr.append(" }");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println(responseStr.toString());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
