/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import implement.DemoImplement;
import static java.util.Collections.list;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Company;
import model.DetailSO;
import model.HeaderSO;
import model.Item;
import model.User;
import model.Users;

/**
 *
 * @author ASUS
 */
public class Test {
    public static void main(String[] args){
        DemoImplement imp = new DemoImplement();
//        List<HeaderSO> list = imp.getListHeaderSO();        
      
        
        //insert list
     HashMap<String, Object> map = new HashMap<String, Object>();
                            map.put("pemail", "riririyadii@gmail.com" );
                            map.put("ppassword", "testing1234" );
                         
////        map.put("pid", 3);
//        map.put("pcustomer_code", "SPH");
//        map.put("pcustomer_description", "SPHORA");
//        map.put("pcustomer_address", "Jl. Kenari");
//        map.put("pcountry_id", 2);           
//        map.put("pprovince_id", 2);          
//        map.put("pcity_id", 3);
//        map.put("pzip_code", "15000");        
//        map.put("pcontact_person", "0231393949");
//        map.put("pemail", "erajaya@team.com");
//        map.put("pphone_number", "0232131311");
//        map.put("psearch", "tfs");
//        List<Item> list = imp.findListItem(map);
      
//        map.put("pid", 17);
//        List<DetailSO> list = imp.getListDetailSO(map);
//       
//      pcustomer_code character varying,
//	pcustomer_description character varying,
//	pcustomer_address character varying,
//	pcountry_id bigint,
//	pprovince_id bigint,
//	pcity_id bigint,
//	pzip_code character varying,
//	pcontact_person character varying,
//	pphone_number character varying,
//	pemail character varying,
//	pcompany_id bigint,
        
       List<User> list = imp.login(map);
//        
      

            
 
        for(int i = 0; i < list.size(); i++){
            System.out.println("id = " + list.get(i).getId()+ " name = " + list.get(i).getName());
        }
        
        
    }
}
