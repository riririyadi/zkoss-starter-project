/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import model.User;

/**
 *
 * @author RIRI
 */
public interface UserInfoService {

	/** find user by account **/
	public User findUser(String email);
	
	/** update user **/
	public User updateUser(User user);
}