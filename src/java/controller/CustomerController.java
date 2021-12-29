/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import id.berkah.admin.controller.LovController;
import implement.DemoImplement;
import java.util.HashMap;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author RIRI
 */
public class CustomerController extends SelectorComposer<Component> {
    
    private ListCustomersController listCustomersController;

    public ListCustomersController getListCustomersController() {
        return listCustomersController;
    }

    public void setListCustomersController(ListCustomersController listCustomersController) {
        this.listCustomersController = listCustomersController;
    }
    
    @Wire
    Window entry_customer;
    
    @Wire
    Textbox customer_code, customer_description, customer_address;
    
    @Wire
    Textbox country_id, country_code, country_description;
    
    @Wire
    Textbox province_id, province_code, province_description;
    
    @Wire
    Textbox city_id, city_code, city_description;
    
    @Wire
    Textbox email, phone_number, contact_person, zip_code;
    
    @Wire 
    Textbox company_id, company_code, company_description;
    
    DemoImplement imp = new DemoImplement();
    
    Map args;
    
    @Override
    public void doAfterCompose(Component comp) throws Exception 
    {
        super.doAfterCompose(comp);        
        args = Executions.getCurrent().getArg();
     
        if (args.containsKey("ModuleMainController")) {
		setListCustomersController((ListCustomersController) args.get("ModuleMainController"));
		// Set THIS controller BACK TO THE MainController 
		getListCustomersController().setCustomerController(this);
                       
	}
        
        if (!args.containsKey("pid")) 
        {
            entry_customer.setTitle("Add Customer");              
        }
        else 
        {
            entry_customer.setTitle("Edit Customer");
            country_id.setValue(args.get("pcountry_id").toString());                
            country_code.setValue((String) args.get("pcountry_code"));                
            country_description.setValue((String) args.get("pcountry_description"));

            province_id.setValue(args.get("pprovince_id").toString());                
            province_code.setValue((String) args.get("pprovince_code"));                
            province_description.setValue((String) args.get("pprovince_description"));

            city_id.setValue(args.get("pcity_id").toString());                
            city_code.setValue((String) args.get("pcity_code"));                
            city_description.setValue((String) args.get("pcity_description"));


            company_id.setValue(args.get("pcompany_id").toString());                
            company_code.setValue((String) args.get("pcompany_code"));
            company_description.setValue((String) args.get("pcompany_description"));  
            
            customer_address.setValue((String) args.get("pcustomer_address"));                 
            customer_code.setValue((String) args.get("pcustomer_code"));                
            customer_description.setValue((String) args.get("pcustomer_description"));
            email.setValue((String) args.get("pemail"));                
            phone_number.setValue((String) args.get("pphone_number"));                
            zip_code.setValue((String) args.get("pzip_code"));                
            contact_person.setValue((String) args.get("pcontact_person"));

        }
    }
    
    
    
    
    @Listen(Events.ON_CLICK + "=#btnSave")
    public void btnSave_onClick(){
        
        HashMap<String, Object> map = new HashMap<String, Object>();
        
        if (!country_id.getValue().isEmpty() && !province_id.getValue().isEmpty() && 
                !city_id.getValue().isEmpty() && !company_id.getValue().isEmpty()
                ){
           if (!args.containsKey("pid")) 
        {
            map.put("pcustomer_code", customer_code.getValue().isEmpty() ? null : customer_code.getText());
            map.put("pcustomer_description", customer_description.getValue().isEmpty()? null : customer_description.getText());
            map.put("pcustomer_address", customer_address.getValue().isEmpty()? null : customer_address.getText());
            map.put("pcountry_id", country_id.getValue().isEmpty()? null : new Long(country_id.getText()));
            map.put("pprovince_id", province_id.getValue().isEmpty()? null : new Long(province_id.getText()));
            map.put("pcity_id", city_id.getValue().isEmpty()? null : new Long(city_id.getText()));
            map.put("pzip_code", zip_code.getValue().isEmpty()? null : zip_code.getText());
            map.put("pcontact_person", contact_person.getValue().isEmpty()? null : contact_person.getText());
            map.put("pphone_number", phone_number.getValue().isEmpty()? null : phone_number.getText());
            map.put("pemail", email.getValue().isEmpty()? null : email.getText());
            map.put("pcompany_id", company_id.getValue().isEmpty()? null : new Long(company_id.getText()));
            
            imp.customers_oninsert(map);
            Messagebox.show(map.get("outmsg").toString());             
        }
        else 
        {   
            map.put("pid", args.get("pid"));
            map.put("pcustomer_code", customer_code.getValue().isEmpty() ? null : customer_code.getText());
            map.put("pcustomer_description", customer_description.getValue().isEmpty()? null : customer_description.getText());
            map.put("pcustomer_address", customer_address.getValue().isEmpty()? null : customer_address.getText());
            map.put("pcountry_id", country_id.getValue().isEmpty()? null : new Long(country_id.getText()));
            map.put("pprovince_id", province_id.getValue().isEmpty()? null : new Long(province_id.getText()));
            map.put("pcity_id", city_id.getValue().isEmpty()? null : new Long(city_id.getText()));
            map.put("pzip_code", zip_code.getValue().isEmpty()? null : zip_code.getText());
            map.put("pcontact_person", contact_person.getValue().isEmpty()? null : contact_person.getText());
            map.put("pphone_number", phone_number.getValue().isEmpty()? null : phone_number.getText());
            map.put("pemail", email.getValue().isEmpty()? null : email.getText());
            map.put("pcompany_id", company_id.getValue().isEmpty()? null : new Long(company_id.getText()));
                   
            imp.customers_onupdate(map);
            Messagebox.show(map.get("outmsg").toString());    
        }
            entry_customer.detach();
            getListCustomersController().showListCustomer();
        } else {
            Messagebox.show("Fill all the field, Please!");
        }
        
      
         
    }
    
                
    @Listen(Events.ON_CLICK + "=#btnClose")
        public void btnClose_onClick(){
            entry_customer.detach();
        }
    
    
    
    @Listen(Events.ON_CLICK + "=#btnLovCountry")
    public void showLovCountry()
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        
        String query = "select id, country_code, country_description "
                      + "from countries "
                      + "where upper(country_code) like upper('%?%') or upper(country_description) like upper('%?%') "
                      + "order by country_code "
                      + "Limit param1 offset param2";
        
        composerLov.setQuery(query);
        composerLov.setQueryTotal("select count(*) from countries where upper(country_code) like upper('%?%') or upper(country_description) like upper('%?%')");
        composerLov.setSelectedColumn(new int[]{
            0, 1, 2
        });
        composerLov.setColumnWidth(new String[]{
            "50px", "300px"
        });

        composerLov.setComponentTransferData(new Textbox[]{
            country_id, country_code, country_description
        });
        composerLov.setHiddenColumn(new int[]{
            0
        });

//        composerLov.setEventListener((EventListener) (Event t) -> {
//        });
        composerLov.setTitle("List of Countries");
        composerLov.setWidth("500px");
        composerLov.setHeight("350px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);
        Window w = (Window) Executions.createComponents("/components/lov.zul", null, map);
        w.setParent(entry_customer);
        w.doModal();
    }
    
    @Listen(Events.ON_CLICK + "=#btnLovProvince")
    public void showLovProvince()
    {
             if(country_id.getValue().isEmpty()){
            Messagebox.show("Select Country First, Please!");
        } else {
        
        HashMap<String, Object> map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        
        String query = "select id, province_code, province_description "
                      + "from provinces "
                      + "where country_id = " + country_id.getValue();
//                      + " And upper(province_code) like upper('%?%') or upper(province_description) like upper('%?%') "
//                      + "order by province_code"
//                      + "Limit param1 offset param2";
        
        composerLov.setQuery(query);
        composerLov.setQueryTotal("select count(*) from provinces where country_id = " + country_id.getValue());
//                + "And upper(province_code) like upper('%?%') or upper(province_description) like upper('%?%')");
        composerLov.setSelectedColumn(new int[]{
            0, 1, 2
        });
        composerLov.setColumnWidth(new String[]{
            "50px", "300px"
        });

        composerLov.setComponentTransferData(new Textbox[]{
            province_id, province_code, province_description
        });
        composerLov.setHiddenColumn(new int[]{
            0
        });

//        composerLov.setEventListener((EventListener) (Event t) -> {
//        });
        composerLov.setTitle("List of Provinces");
        composerLov.setWidth("500px");
        composerLov.setHeight("350px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);
        Window w = (Window) Executions.createComponents("/components/lov.zul", null, map);
        w.setParent(entry_customer);
        w.doModal();
    }}
    
    
    @Listen(Events.ON_CLICK + "=#btnLovCity")
    public void showLovCity()
    {
             if(province_id.getValue().isEmpty()){
            Messagebox.show("Select Province First, Please!");
        } else {
        
        HashMap<String, Object> map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        
        String query = "select id, city_code, city_description "
                      + "from cities "
                      + "where province_id = " + province_id.getValue();
//                      + " And upper(province_code) like upper('%?%') or upper(province_description) like upper('%?%') "
//                      + "order by province_code"
//                      + "Limit param1 offset param2";
        
        composerLov.setQuery(query);
        composerLov.setQueryTotal("select count(*) from cities where province_id = " + province_id.getValue());
//                + "And upper(province_code) like upper('%?%') or upper(province_description) like upper('%?%')");
        composerLov.setSelectedColumn(new int[]{
            0, 1, 2
        });
        composerLov.setColumnWidth(new String[]{
            "50px", "300px"
        });

        composerLov.setComponentTransferData(new Textbox[]{
            city_id, city_code, city_description
        });
        composerLov.setHiddenColumn(new int[]{
            0
        });

//        composerLov.setEventListener((EventListener) (Event t) -> {
//        });
        composerLov.setTitle("List of Cities");
        composerLov.setWidth("500px");
        composerLov.setHeight("350px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);
        Window w = (Window) Executions.createComponents("/components/lov.zul", null, map);
        w.setParent(entry_customer);
        w.doModal();
    }
    }
    
    @Listen(Events.ON_CLICK + "=#btnLovCompany")
    public void showLovCompany()
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        
        String query = "select id, company_code, company_description "
                      + "from companies";
//                      + "where province_id = " + province_id.getValue();
//                      + " And upper(province_code) like upper('%?%') or upper(province_description) like upper('%?%') "
//                      + "order by province_code"
//                      + "Limit param1 offset param2";
        
        composerLov.setQuery(query);
        composerLov.setQueryTotal("select count(*) from companies");
//                + "And upper(province_code) like upper('%?%') or upper(province_description) like upper('%?%')");
        composerLov.setSelectedColumn(new int[]{
            0, 1, 2
        });
        composerLov.setColumnWidth(new String[]{
            "50px", "300px"
        });

        composerLov.setComponentTransferData(new Textbox[]{
            company_id, company_code, company_description
        });
        composerLov.setHiddenColumn(new int[]{
            0
        });

//        composerLov.setEventListener((EventListener) (Event t) -> {
//        });
        composerLov.setTitle("List of Companies");
        composerLov.setWidth("500px");
        composerLov.setHeight("350px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);
        Window w = (Window) Executions.createComponents("/components/lov.zul", null, map);
        w.setParent(entry_customer);
        w.doModal();
    }
    
}
