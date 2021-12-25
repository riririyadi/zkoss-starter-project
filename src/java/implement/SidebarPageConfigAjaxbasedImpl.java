/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implement;

import service.SidebarPageConfig;
import model.SidebarPage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author RIRI
 */
public class SidebarPageConfigAjaxbasedImpl implements SidebarPageConfig{

    	HashMap<String,SidebarPage> pageMap = new LinkedHashMap<String,SidebarPage>();
	public SidebarPageConfigAjaxbasedImpl(){
              
		pageMap.put("fn0",new SidebarPage("Dashboard","Dashboard","/img/dashboard-16.png","/dashboard.zul"));
                pageMap.put("fn1",new SidebarPage("Items","Items","/img/item-icon-16.png","/items.zul"));
		pageMap.put("fn2",new SidebarPage("Countries","Countries","/img/country-16.png","/countries.zul"));
		pageMap.put("fn3",new SidebarPage("Provinces","Provinces","/img/province-16.png","/provinces.zul"));
                pageMap.put("fn4",new SidebarPage("Cities","Cities","/img/city-16.png","/cities.zul"));                
                pageMap.put("fn5",new SidebarPage("Companies","Companies","/img/company-16.png","/companies.zul"));                
                pageMap.put("fn6",new SidebarPage("Customers","Customers","/img/customer-16.png","/customers.zul"));
                pageMap.put("fn7",new SidebarPage("Transactions","Transactions","/img/transaction-16.png","/transactions.zul"));
	
}
	
	public List<SidebarPage> getPages(){
		return new ArrayList<SidebarPage>(pageMap.values());
	}
	
	public SidebarPage getPage(String name){
		return pageMap.get(name);
	}
    
}
