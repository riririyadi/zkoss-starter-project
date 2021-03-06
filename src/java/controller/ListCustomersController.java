/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import id.berkah.admin.controller.LovController;
import implement.DemoImplement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.City;
import model.Company;
import model.Country;
import model.Customer;
import model.Item;
import model.Province;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.impl.ParseException;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

/**
 *
 * @author RIRI
 */
public class ListCustomersController extends SelectorComposer<Component> {
    
    
    private CustomerController customerController;

    public CustomerController getCustomerController() {
        return customerController;
    }

    public void setCustomerController(CustomerController customerController) {
        this.customerController = customerController;
    }
    
    
    @Wire
    Listbox  listboxCustomers;
    
    @Wire
    Div customers_div;
    
    @Wire
    Textbox searchCustomer,
            country_id, country_code, country_description,
            province_id, province_code, province_description,
            city_id, city_code, city_description,
            company_id, company_code, company_description;

    @Wire
    Paging userPaging;
  
    List<Customer> listCustomer, listSearchCustomer;
    
    DemoImplement imp = new DemoImplement();
    
    Map args;
    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        showListCustomer();
    }
    
    Integer totalRecord = 0;    
    Integer pageSize = 10;
    Integer currentPage = 1;

     
     @Listen(Events.ON_OK + "= #searchCustomer")
   public void searchCustomerListener(){
     HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("psearch", searchCustomer.getValue());
        totalRecord = imp.onCountCustomers(map);
        System.out.println("total record " + totalRecord.toString());
        userPaging.setTotalSize(totalRecord);
//        pageSize = listboxCountries.getPageSize();
        userPaging.setPageSize(10);
        showListCustomeronDemand(searchCustomer.getValue());
   }
   
     @Listen("onPaging=#userPaging")
    public void onPagingUserPaging(PagingEvent pe) throws ParseException{
      currentPage = pe.getActivePage() + 1;
      showListCustomeronDemand(searchCustomer.getValue());
    }
    
     public void showListCustomeronDemand(String psearch) 
    {
        HashMap<String, Object> mapPaging = new HashMap<String, Object>();
        mapPaging.put("pageSize", pageSize);        
        mapPaging.put("pageNumber", currentPage);
        mapPaging.put("psearch", psearch);
        listSearchCustomer = imp.findListCustomer(mapPaging);
   
        refreshHeaderCustomer();
        setListboxCustomerRenderer();

    }
      
    
     @Listen(Events.ON_CLICK + "=#btnNewCustomer")
    public void btnNewCustomer_OnClick(Event e)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("ModuleMainController", this);
        final Window window = (Window) Executions.createComponents("/components/entry_customer.zul", null, map);
        window.doHighlighted();
        window.setPosition("center");
      
    }
    
       @Listen(Events.ON_CLICK + "=#btnEditCustomer")
    public void btnEditCustomer_onUpdate(Customer t)
    {

  	HashMap<String, Object> map = new HashMap<String, Object>();
   
    	map.put("pid", t.getId());    	
        map.put("pcountry_id", t.getCountry_id());
        map.put("pcountry_code", t.getCountry_code());        
        map.put("pcountry_description", t.getCountry_code());        
        map.put("pprovince_id", t.getProvince_id());        
        map.put("pprovince_code", t.getProvince_code());       
        map.put("pprovince_description", t.getProvince_description());  
        map.put("pcity_id", t.getCity_id());       
        map.put("pcity_code", t.getCity_code());       
        map.put("pcity_description", t.getCity_description());        
        map.put("pcompany_id", t.getCompany_id());
        map.put("pcompany_code", t.getCompany_code());        
        map.put("pcompany_description", t.getCompany_description());
        map.put("pcustomer_address", t.getCustomer_address());        
        map.put("pcustomer_code", t.getCustomer_code());        
        map.put("pcustomer_description", t.getCustomer_description());
        map.put("pemail", t.getEmail());        
        map.put("pphone_number", t.getPhone_number());        
        map.put("pzip_code", t.getZip_code());        
        map.put("pcontact_person", t.getContact_person());
        map.put("ModuleMainController", this);

    	final Window window = (Window) Executions.createComponents("/components/entry_customer.zul", null, map);
        window.doHighlighted();
        window.setPosition("center");
    }
    
    @Listen(Events.ON_CLICK + "=#btnRefreshCustomer")
    public void btnRefreshCustomer_OnClick()
    {
       showListCustomer();
    }
    
//    
//    
//      @Listen(Events.ON_OK + "= #searchCustomer")
//   public void searchCustomerListener(){
//       if(searchCustomer.getValue().isEmpty()){
//           listSearchCustomer = null;
//       } 
//       else {
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("psearch", searchCustomer.getText());
//            listSearchCustomer = imp.findListCustomer(map);
//       }
//        refreshHeaderCustomer();
//        setListboxCustomerRenderer();
//   }
   
    @Listen("onClick =#btnDoFilter")
    public void btnDoFilter_onClick(){
        HashMap<String, Object> map = new HashMap<String, Object>();
        if(!country_id.getValue().isEmpty()){
            map.put("pcountry_id", new Long(country_id.getValue()));
        }
        if(!province_id.getValue().isEmpty()){
            map.put("pprovince_id", new Long(province_id.getValue()));
        }
        if(!city_id.getValue().isEmpty()){
            map.put("pcity_id", new Long(city_id.getValue()));
        } 
        if(!company_id.getValue().isEmpty()){
            map.put("pcity_id", new Long(company_id.getValue()));
        }
        
        
        listSearchCustomer = imp.getListCustomersbyFilter(map);
        refreshHeaderCustomer();
        setListboxCustomerRenderer();
    }
    
    
    @Listen("onClick =#btnDoClear")
    public void btnDoClear_onClick(){
        HashMap<String, Object> map = new HashMap<String, Object>();
        country_id.setValue("");        
        country_code.setValue("");        
        country_description.setValue("");
      
        province_id.setValue("");        
        province_code.setValue("");        
        province_description.setValue("");

        city_id.setValue("");        
        city_code.setValue("");        
        city_description.setValue("");

        company_id.setValue("");        
        company_code.setValue("");        
        company_description.setValue("");

        listSearchCustomer = null;
        refreshHeaderCustomer();
        setListboxCustomerRenderer();
    }
    
    
    @Listen(Events.ON_CLICK + "= #clearSearchCustomer")
    public void clearSearchCustomer(){
       searchCustomer.setValue("");
           listSearchCustomer = null;
           
        refreshHeaderCustomer();
        setListboxCustomerRenderer();
   }
    public void showListCustomer() 
    {
        listCustomer = imp.getListCustomers();
        
        refreshHeaderCustomer();
        setListboxCustomerRenderer();
    }
    
     public void refreshHeaderCustomer() 
    {
       if(listSearchCustomer != null){
            listboxCustomers.setModel(new ListModelList<Customer>(listSearchCustomer));

       }else{
            listboxCustomers.setModel(new ListModelList<Customer>(listCustomer));
       }
    }
    
       public void setListboxCustomerRenderer() 
    {
        listboxCustomers.setItemRenderer(new ListitemRenderer<Customer>() {
            @Override
            public void render(Listitem lstm, final Customer t, int i) throws Exception 
            {
                
        
                final Button button = new Button();
                button.setImage("/img/Delete24.png");
                button.addEventListener("onClick", new EventListener() {
                    public void onEvent(Event e) throws Exception {
                        
                        Messagebox.show("Hapus Customer "+ t.getCompany_description()+" ?", 
                        "Question", Messagebox.YES | Messagebox.CANCEL,
                        Messagebox.QUESTION,
                        new org.zkoss.zk.ui.event.EventListener(){
	 		public void onEvent(Event e){
                            HashMap<String, Object> map = new HashMap<String, Object>();
                            map.put("pid", t.getId());
	 			if(Messagebox.ON_YES.equals(e.getName())){
                                    
                                           imp.customers_ondelete(map);
                                           Messagebox.show(map.get("outmsg").toString());
                                           showListCustomer();
	 			}else if(Messagebox.ON_CANCEL.equals(e.getName())){
	 				
	 			}
                            }
                        });
                    }
                });
                Listcell cell = new Listcell();
                cell.appendChild(button);
                lstm.appendChild(cell);
                
                final Button editBtn = new Button();
                editBtn.setImage("/img/Edit24.png");
                 editBtn.addEventListener("onClick", new EventListener() {
                    public void onEvent(Event e) throws Exception {
                        btnEditCustomer_onUpdate(t);
                    }
                });
                Listcell editCell = new Listcell();
                editCell.appendChild(editBtn);
                lstm.appendChild(editCell);
               
                new Listcell(t.getCustomer_code()).setParent(lstm);
                new Listcell(t.getCustomer_description()).setParent(lstm);                 
                new Listcell(t.getCustomer_address()).setParent(lstm);                  
                new Listcell(t.getPhone_number()).setParent(lstm);                
                new Listcell(t.getEmail()).setParent(lstm);                     
                new Listcell(t.getContact_person()).setParent(lstm);                       
                new Listcell(t.getZip_code()).setParent(lstm);                
            }
        });
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
        w.setParent(customers_div);
        w.doModal();
    }
    
    @Listen(Events.ON_CLICK + "=#btnLovProvince")
    public void showLovProvince()
    {
     
        
        HashMap<String, Object> map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        
        String query = "select id, province_code, province_description "
                      + "from provinces ";
//                      + "where country_id = " + country_id.getValue();
//                      + " And upper(province_code) like upper('%?%') or upper(province_description) like upper('%?%') "
//                      + "order by province_code"
//                      + "Limit param1 offset param2";
        
        composerLov.setQuery(query);
        composerLov.setQueryTotal("select count(*) from provinces");
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
        w.setParent(customers_div);
        w.doModal();
    }
    
    
    @Listen(Events.ON_CLICK + "=#btnLovCity")
    public void showLovCity()
    {
        
        HashMap<String, Object> map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        
        String query = "select id, city_code, city_description "
                      + "from cities ";
//                      + "where province_id = " + province_id.getValue();
//                      + " And upper(province_code) like upper('%?%') or upper(province_description) like upper('%?%') "
//                      + "order by province_code"
//                      + "Limit param1 offset param2";
        
        composerLov.setQuery(query);
        composerLov.setQueryTotal("select count(*) from cities ");
//                + "where province_id = " + province_id.getValue());
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
        w.setParent(customers_div);
        w.doModal();
    
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
        w.setParent(customers_div);
        w.doModal();
    }
    
       
}
