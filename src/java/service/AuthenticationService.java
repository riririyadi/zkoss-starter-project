/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import model.UserCredential;
/**
 *
 * @author RIRI
 */
public interface AuthenticationService {
    /**login with account and password**/
	public boolean login(String email, String password);
	
	/**logout current user**/
	public void logout();
	
	/**get current user credential**/
	public UserCredential getUserCredential();
}
