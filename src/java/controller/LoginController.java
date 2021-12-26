package controller;

import implement.AuthenticationServiceNextImpl;
import implement.DemoImplement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.User;
import model.UserCredential;
import model.Users;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import service.AuthenticationService;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author RIRI
 */
public class LoginController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	
	//wire components
	@Wire
	Textbox email;
	@Wire
	Textbox password;
	@Wire
	Label message;
        @Wire
	Button visibility, login;
	
        DemoImplement imp = new DemoImplement();
	//services
	AuthenticationService authService = new AuthenticationServiceNextImpl();

	@Listen("onClick=#login; onOK=#password; onOK=#email" )
	public void doLogin(){
                login.setLabel("Please wait...");
		String nm = email.getValue().trim();
		String pd = password.getValue().trim();
		
		if(!authService.login(nm,pd)){
			message.setValue("invalid email or password");
                        message.setSclass("login-error");
                        login.setLabel("Login");
			return;
		}
		
                Executions.sendRedirect("/home.zul");
		
	}
        
        
	@Listen("onClick=#visibility" )
	public void toggleVisibility(){
            
            if(password.getType().equals("password")) { 
                password.setType("text");
                visibility.setImage("/img/visible-16.png");
            }else{  
                password.setType("password");
                visibility.setImage("/img/invisible-16.png");
            }
        }
}

