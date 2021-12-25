/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import implement.AuthenticationServiceImpl;
import java.util.Map;
import model.UserCredential;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;
import service.AuthenticationService;

/**
 *
 * @author RIRI
 */
public class AuthenticationInit implements Initiator{
    AuthenticationService authService = new AuthenticationServiceImpl();
	
	public void doInit(Page page, Map<String, Object> args) throws Exception {
		
		UserCredential cre = authService.getUserCredential();
		if(cre==null || cre.isAnonymous()){
			Executions.sendRedirect("/login.zul");
			return;
		} 
	}
}
