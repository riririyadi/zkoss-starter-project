/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implement;

import model.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import service.UserInfoService;
/**
 *
 * @author RIRI
 */
public class UserInfoServiceImpl  implements UserInfoService, Serializable{
	private static final long serialVersionUID = 1L;
	
        DemoImplement imp = new DemoImplement();
	static protected List<User> userList;
//	static{
////		userList.add(new User(new Long(1),"1234","Anonymous","anonumous@your.com"));
////		userList.add(new User(new Long(2),"1234","Admin","admin@your.com"));
//		userList.add(new User(new Long(3),"testing1234","Riri Riyadi","riririyadii@gmail.com"));
//	}
	
	/** synchronized is just because we use static userList in this demo to prevent concurrent access **/
	public synchronized User findUser(String email){
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("pemail", email);
            userList = imp.login(map);  
		int s = userList.size();
		for(int i=0;i<s;i++){
			User u = userList.get(i);
			if(email.equals(u.getEmail())){
				return User.clone(u);
			}
		}
		return null;
	}
	
	/** synchronized is just because we use static userList in this demo to prevent concurrent access **/
	public synchronized User updateUser(User user){
		int s = userList.size();
		for(int i=0;i<s;i++){
			User u = userList.get(i);
			if(user.getEmail().equals(u.getEmail())){
				userList.set(i,u = User.clone(user));
				return u;
			}
		}
		throw new RuntimeException("user not found "+user.getEmail());
	}
}