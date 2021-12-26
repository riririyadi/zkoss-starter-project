/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author RIRI
 */
public class UserCredential implements Serializable{
	private static final long serialVersionUID = 1L;
	
	String email;
	String name;
	
	Set<String> roles = new HashSet<String>();

	public UserCredential(String email, String name) {
		this.email = email;
		this.name = name;
	}

	public UserCredential() {
		this.email = "anonymous";
		this.name = "Anonymous";
		roles.add("anonymous");
	}

	public boolean isAnonymous() {
		return hasRole("anonymous") || "anonymous".equals(email);
	}

	public String getAccount() {
		return email;
	}

	public void setAccount(String account) {
		this.email = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean hasRole(String role){
		return roles.contains(role);
	}
	
	public void addRole(String role){
		roles.add(role);
	}

}