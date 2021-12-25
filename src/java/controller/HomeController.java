/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import implement.DemoImplement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import model.City;
import model.Company;
import model.Country;
import model.Customer;
import model.DetailSO;
import model.HeaderSO;
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
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author RIRI
 */
public class HomeController extends SelectorComposer<Component> {
    @Wire
    Listbox listboxItem, 
            listboxCountries, 
            listboxProvinces, 
            listboxCities, 
            listboxCompanies, 
            listboxCustomers,
            listboxHeaderSO,
            listboxDetailSO;
    
    @Wire
    Textbox searchItem, 
            searchCountry, 
            searchProvince, 
            searchCity, 
            searchCompany,
            searchCustomer;

    
    
    List<Item> listItem, listSearchItem;
    List<Country> listCountry, listSearchCountry;    
    List<Province> listProvince, listSearchProvince;    
    List<City> listCity, listSearchCity;    
    List<Company> listCompany, listSearchCompany;    
    List<Customer> listCustomer, listSearchCustomer;
    List<HeaderSO> listHeaderSO;    
    List<DetailSO> listDetailSO;
    boolean isDisabled = true;
        
    SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    
    Long idParameter;
    
    DemoImplement imp = new DemoImplement();


    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        
        
        showListItem();
//        showListCountry();
//        showListProvince();
//        showListCity();        
//        showListCompany();
//        showListCustomer();
//        showListHeaderSO();
//        showListDetailSO();

    }

    @Listen(Events.ON_CLICK + "=#btnEditItem")
    public void btnEditItem_onUpdate(Item t)
    {
        controller.HomeController controller = new controller.HomeController();
  	HashMap<String, Object> map = new HashMap<String, Object>();
   
    	map.put("pid", t.getId());    	
        map.put("pcode", t.getCode());       
        map.put("pdescription", t.getDescription());    
        map.put("puom", t.getUom());        
        map.put("pprice", t.getPrice());
        map.put("controller", controller);
        
    	final Window window = (Window) Executions.createComponents("/components/entry_item.zul", null, map);
        window.doHighlighted();
        window.setPosition("center");
        
  
      
    }
    
    
    @Listen(Events.ON_CLICK + "=#btnRefreshItem")
    public void btnRefreshItem_OnClick()
    {
       showListItem();
    }
    
    @Listen(Events.ON_CLICK + "=#btnFindItem")
    public void btnFindItem_OnClick(){
//        final Window window = (Window) Executions.createComponents("search.zul", null, null);
//        window.doHighlighted();
//        window.setPosition("center");
    if (!searchItem.getValue().isEmpty()){
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("psearch", searchItem.getText());
        listSearchItem = imp.findListItem(map);
        
        refreshHeader();
        setListboxRenderer();
          for(int i = 0; i < listSearchItem.size(); i++){
            System.out.println("code = " + listSearchItem.get(i).getCode()+ " Desc = " + listSearchItem.get(i).getDescription());
        }
    } 
   
    }
    
//    @Listen(Events.ON_CLICK + "=#btnNewItem")
//    public void btnNewItem_OnClick(Event e)
//    {
//        final Window window = (Window) Executions.createComponents("entry_item.zul", null, null);
//        window.doHighlighted();
//        window.setPosition("center");
//    }
//    
    
    @Listen(Events.ON_CLICK + "=#btnNewCountry")
    public void btnNewCountry_OnClick(Event e)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        final Window window = (Window) Executions.createComponents("entry_country.zul", null, map);
        window.doHighlighted();
        window.setPosition("center");
    }
    
    
    @Listen(Events.ON_CLICK + "=#btnEditCountry")
    public void btnEditCountry_onUpdate(Country t)
    {
////        controller.ItemController controller = new controller.ItemController();
  	HashMap<String, Object> map = new HashMap<String, Object>();
//        HashMap map = new HashMap<String, Object>();
//    
    	map.put("pid", t.getId());    	
        map.put("pcountry_code", t.getCountry_code());       
        map.put("pcountry_description", t.getCountry_description());    

        
    	final Window window = (Window) Executions.createComponents("entry_country.zul", null, map);
        window.doHighlighted();
        window.setPosition("center");
        
    }
    
    @Listen(Events.ON_CLICK + "=#btnRefreshCountry")
    public void btnRefreshCountry_OnClick()
    {
       showListCountry();
   
    }

    
    
    @Listen(Events.ON_CLICK + "=#btnNewProvince")
    public void btnNewProvince_OnClick(Event e)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        final Window window = (Window) Executions.createComponents("entry_province.zul", null, map);
        window.doHighlighted();
        window.setPosition("center");
      
    }
    
    @Listen(Events.ON_CLICK + "=#btnEditProvince")
    public void btnEditProvince_onUpdate(Province t)
    {
////        controller.ItemController controller = new controller.ItemController();
  	HashMap<String, Object> map = new HashMap<String, Object>();
//        HashMap map = new HashMap<String, Object>();
//    
    	map.put("pid", t.getId());    
        map.put("pcountry_id", t.getCountry_id());
        map.put("pcountry_code", t.getCountry_code());        
        map.put("pcountry_description", t.getCountry_description());
        map.put("pprovince_code", t.getProvince_code());       
        map.put("pprovince_description", t.getProvince_description());    

        
    	final Window window = (Window) Executions.createComponents("entry_province.zul", null, map);
        window.doHighlighted();
        window.setPosition("center");      
    }
    
    
    
    @Listen(Events.ON_CLICK + "=#btnRefreshProvince")
    public void btnRefreshProvince_OnClick()
    {
       showListProvince();
    }
    
    @Listen(Events.ON_CLICK + "=#btnNewCity")
    public void btnNewCity_OnClick(Event e)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        final Window window = (Window) Executions.createComponents("entry_city.zul", null, map);
        window.doHighlighted();
        window.setPosition("center");
      
    }
    
       @Listen(Events.ON_CLICK + "=#btnEditCity")
    public void btnEditCity_onUpdate(City t)
    {
////        controller.ItemController controller = new controller.ItemController();
  	HashMap<String, Object> map = new HashMap<String, Object>();
//        HashMap map = new HashMap<String, Object>();
//    
    	map.put("pid", t.getId());    	
        map.put("pprovince_id", t.getProvince_id());
        map.put("pprovince_code", t.getProvince_code());       
        map.put("pprovince_description", t.getProvince_description());     
        map.put("pcity_code", t.getCity_code());       
        map.put("pcity_description", t.getCity_description());    
  

        
    	final Window window = (Window) Executions.createComponents("entry_city.zul", null, map);
        window.doHighlighted();
        window.setPosition("center");
              
    }
    
    @Listen(Events.ON_CLICK + "=#btnRefreshCity")
    public void btnRefreshCity_OnClick()
    {
       showListCity();
    }

    
    
    @Listen(Events.ON_CLICK + "=#btnNewCompany")
    public void btnNewCompany_OnClick(Event e)
    {
        final Window window = (Window) Executions.createComponents("entry_company.zul", null, null);
        window.doHighlighted();
        window.setPosition("center");
    }
    
    
    @Listen(Events.ON_CLICK + "=#btnEditCompany")
    public void btnEditCompany_onUpdate(Company t)
    {
////        controller.ItemController controller = new controller.ItemController();
  	HashMap<String, Object> map = new HashMap<String, Object>();
//        HashMap map = new HashMap<String, Object>();
//    
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
        map.put("pcompany_code", t.getCompany_code());        
        map.put("pcompany_description", t.getCompany_description());
        map.put("pcompany_address", t.getCompany_address());
        map.put("pemail", t.getEmail());        
        map.put("pphone_number", t.getPhone_number());        
        map.put("pzip_code", t.getZip_code());
    	final Window window = (Window) Executions.createComponents("entry_company.zul", null, map);
        window.doHighlighted();
        window.setPosition("center");
    }
    
    @Listen(Events.ON_CLICK + "=#btnRefreshCompany")
    public void btnRefreshCompany_OnClick()
    {
       showListCompany();
    }
    
    
    @Listen(Events.ON_CLICK + "=#btnNewCustomer")
    public void btnNewCustomer_OnClick(Event e)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        final Window window = (Window) Executions.createComponents("entry_customer.zul", null, map);
        window.doHighlighted();
        window.setPosition("center");
      
    }
    
       @Listen(Events.ON_CLICK + "=#btnEditCustomer")
    public void btnEditCustomer_onUpdate(Customer t)
    {
////        controller.ItemController controller = new controller.ItemController();
  	HashMap<String, Object> map = new HashMap<String, Object>();
//        HashMap map = new HashMap<String, Object>();
//    
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

    	final Window window = (Window) Executions.createComponents("entry_customer.zul", null, map);
        window.doHighlighted();
        window.setPosition("center");
    }
    
    @Listen(Events.ON_CLICK + "=#btnRefreshCustomer")
    public void btnRefreshCustomer_OnClick()
    {
       showListCustomer();
    }
    
    
    @Listen(Events.ON_CLICK + "=#btnNewHeaderSO")
    public void btnNewHeaderSO_OnClick(Event e)
    {  HashMap<String, Object> map = new HashMap<String, Object>();
        final Window window = (Window) Executions.createComponents("master_so.zul", null, map);
        window.doHighlighted();
        window.setPosition("center");
      
    }
    
    
    @Listen(Events.ON_CLICK + "=#btnEditHeaderSO")
    public void btnEditHeaderSO_OnClick(HeaderSO t)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("pid", t.getId());    	
        map.put("pso_number", t.getSo_number());
        map.put("pcustomer_id", t.getCustomer_id()); 
        map.put("pcustomer_code", t.getCustomer_code());        
        map.put("pcustomer_description", t.getCustomer_description());
        map.put("pso_date", t.getSo_date());        
        map.put("pstatus", t.getStatus());        


        final Window window = (Window) Executions.createComponents("master_so.zul", null, map);
        window.doHighlighted();
        window.setPosition("center");
      
    }
    
    @Listen(Events.ON_CLICK + "=#btnRefreshHeaderSO")
    public void btnRefreshHeaderSO_OnClick()
    {
       showListHeaderSO();
    }
    
    
    @Listen(Events.ON_CLICK + "=#btnNewDetailSO")
    public void btnNewDetailSO_OnClick(Event e)
    {
        final Window window = (Window) Executions.createComponents("detail_so.zul", null, null);
        window.doHighlighted();
        window.setPosition("center");
      
    }
    
    @Listen(Events.ON_CLICK + "=#btnNewItem")
    public void btnNewItem_OnClick(Event e)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        final Window window = (Window) Executions.createComponents("/components/entry_item.zul", null, map);
        window.doHighlighted();
        window.setPosition("center");
    }
    
    
   @Listen(Events.ON_OK + "= #searchItem")
   public void searchListener(){
       if(searchItem.getValue().isEmpty()){
           listSearchItem = null;
       } 
       else {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("psearch", searchItem.getText());
            listSearchItem = imp.findListItem(map);
       }
        refreshHeader();
        setListboxRenderer();
   }
   
    @Listen(Events.ON_CLICK + "= #clearSearch")
    public void clearSearch(){
       searchItem.setValue("");
           listSearchItem = null;
     
        refreshHeader();
        setListboxRenderer();
   }
    
    
    @Listen(Events.ON_OK + "= #searchCountry")
   public void searchCountryListener(){
       if(searchCountry.getValue().isEmpty()){
           listSearchCountry = null;
       } 
       else {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("psearch", searchCountry.getText());
            listSearchCountry = imp.findListCountry(map);
       }
        refreshHeaderCountry();
        setListboxCountryRenderer();
   }
   
    @Listen(Events.ON_CLICK + "= #clearSearchCountry")
    public void clearSearchCountry(){
       searchCountry.setValue("");
           listSearchCountry = null;
     
        refreshHeaderCountry();
        setListboxCountryRenderer();
   }
    
    public void showListItem() 
    {
        listItem = imp.getListItem();
        
        refreshHeader();
        setListboxRenderer();
    }
    
    
       @Listen(Events.ON_OK + "= #searchProvince")
   public void searchListenerProvince(){
       if(searchProvince.getValue().isEmpty()){
           listSearchProvince = null;
       } 
       else {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("psearch", searchProvince.getText());
            listSearchProvince = imp.findListProvince(map);
       }
        refreshHeaderProvince();
        setListboxProvinceRenderer();
   }
   
    @Listen(Events.ON_CLICK + "= #clearSearchProvince")
    public void clearSearchProvince(){
       searchProvince.setValue("");
           listSearchProvince = null;
     
        refreshHeaderProvince();
        setListboxProvinceRenderer();
    }
    
    @Listen(Events.ON_OK + "= #searchCity")
    public void searchCityListener(){
       if(searchCity.getValue().isEmpty()){
           listSearchCity = null;
       } 
       else {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("psearch", searchCity.getText());
            listSearchCity = imp.findListCity(map);
       }
          
        refreshHeaderCity();
        setListboxCityRenderer();
   }
   
    @Listen(Events.ON_CLICK + "= #clearSearchCity")
    public void clearSearchCity(){
       searchCity.setValue("");
           listSearchCity = null;
     
        refreshHeaderCity();
        setListboxCityRenderer();
   }
    
    
     @Listen(Events.ON_OK + "= #searchCompany")
   public void searchCompanyListener(){
       if(searchCompany.getValue().isEmpty()){
           listSearchCompany = null;
       } 
       else {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("psearch", searchCompany.getText());
            listSearchCompany = imp.findListCompany(map);
       }
    
        refreshHeaderCompany();
        setListboxCompanyRenderer();
   }
   
    @Listen(Events.ON_CLICK + "= #clearSearchCompany")
    public void clearSearchCompany(){
       searchCompany.setValue("");
           listSearchCompany = null;
     
        refreshHeaderCompany();
        setListboxCompanyRenderer();
   }
    
      
   @Listen(Events.ON_OK + "= #searchCustomer")
   public void searchCustomerListener(){
       if(searchCustomer.getValue().isEmpty()){
           listSearchCustomer = null;
       } 
       else {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("psearch", searchCustomer.getText());
            listSearchCustomer = imp.findListCustomer(map);
       }
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
    
    
     public void refreshHeader() 
    {
        if(listSearchItem != null ){
            listboxItem.setModel(new ListModelList<Item>(listSearchItem));
        }else{
            listboxItem.setModel(new ListModelList<Item>(listItem));
        }
    }
    
       public void setListboxRenderer() 
    {
        listboxItem.setItemRenderer(new ListitemRenderer<Item>() {
            @Override
            public void render(Listitem lstm, final Item t, int i) throws Exception 
            {
                
                final Button button = new Button();
                button.setImage("/img/Delete24.png");
                button.addEventListener("onClick", new EventListener() {
                    public void onEvent(Event e) throws Exception {
                        
                        Messagebox.show("Hapus Item "+ t.getDescription() +" ?", 
                        "Question", Messagebox.YES | Messagebox.CANCEL,
                        Messagebox.QUESTION,
                        new org.zkoss.zk.ui.event.EventListener(){
	 		public void onEvent(Event e){
                            HashMap<String, Object> map = new HashMap<String, Object>();
                            map.put("pid", t.getId());
	 			if(Messagebox.ON_YES.equals(e.getName())){
                                    
                                            imp.items_ondelete(map);
                                            showListItem();
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
                            btnEditItem_onUpdate(t);
                    }
                });
                Listcell editCell = new Listcell();
                editCell.appendChild(editBtn);
                lstm.appendChild(editCell);
                
                final Textbox textboxCode = new Textbox();
                textboxCode.setValue(t.getCode());
                textboxCode.setInplace(true);
                Listcell cellCode = new Listcell();
                cellCode.appendChild(textboxCode);
                lstm.appendChild(cellCode);
                
                final Textbox textboxDescription = new Textbox();
                textboxDescription.setValue(t.getDescription());
                textboxDescription.setInplace(true);
                Listcell cellDescription = new Listcell();
                cellDescription.appendChild(textboxDescription);
                lstm.appendChild(cellDescription);
                
                final Textbox textboxUom = new Textbox();
                textboxUom.setValue(t.getUom());
                textboxUom.setInplace(true);
                Listcell cellUom = new Listcell();
                cellUom.appendChild(textboxUom);
                lstm.appendChild(cellUom);
                
                final Textbox textboxPrice = new Textbox();
                textboxPrice.setValue(t.getPrice().toString());
                textboxPrice.setInplace(true);
                Listcell cellPrice = new Listcell();
                cellPrice.appendChild(textboxPrice);
                lstm.appendChild(cellPrice);
               
                lstm.addEventListener("onClick", new EventListener() {
                    public void onEvent(Event e) throws Exception {
                        textboxCode.isInplace();
                        textboxDescription.isInplace();
                        textboxUom.isInplace(); 
                        textboxPrice.isInplace();
                          
                    }
                });
            }

        });
    }
      
    public void showListCountry() 
    {
       listCountry = imp.getListCountry();
        
        refreshHeaderCountry();
        setListboxCountryRenderer();
    }
    
    public void refreshHeaderCountry() 
    {
      if(listSearchCountry != null){
          listboxCountries.setModel(new ListModelList<Country>(listSearchCountry));
      }else{
        listboxCountries.setModel(new ListModelList<Country>(listCountry));
      }
    }
    
    public void setListboxCountryRenderer() 
    {
        listboxCountries.setItemRenderer(new ListitemRenderer<Country>() {
            @Override
            public void render(Listitem lstm, final Country t, int i) throws Exception 
            {
                final Button button = new Button();
                button.setImage("/img/Delete24.png");
                button.addEventListener("onClick", new EventListener() {
                    public void onEvent(Event e) throws Exception {
                        
                        Messagebox.show("Hapus Country "+t.getCountry_description() + " ?", 
                        "Question", Messagebox.YES | Messagebox.CANCEL,
                        Messagebox.QUESTION,
                        new org.zkoss.zk.ui.event.EventListener(){
	 		public void onEvent(Event e){
                            HashMap<String, Object> map = new HashMap<String, Object>();
                            map.put("pid", t.getId());
	 			if(Messagebox.ON_YES.equals(e.getName())){
                                    
                                            imp.countries_ondelete(map);
                                            showListCountry();
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
//                        btnEditItem_onUpdate(t);
                            btnEditCountry_onUpdate(t);
                    }
                });
                Listcell editCell = new Listcell();
                editCell.appendChild(editBtn);
                lstm.appendChild(editCell);
                
               
                new Listcell(t.getCountry_code()).setParent(lstm);
                new Listcell(t.getCountry_description()).setParent(lstm);                
            }
        });
    }
       
       
    public void showListProvince() 
    {
        listProvince = imp.getListProvinces();
        
        refreshHeaderProvince();
        setListboxProvinceRenderer();
    }
    
     public void refreshHeaderProvince() 
    {
        if(listSearchProvince != null){
            listboxProvinces.setModel(new ListModelList<Province>(listSearchProvince));
        }else{
        listboxProvinces.setModel(new ListModelList<Province>(listProvince));
        }
    }
    
       public void setListboxProvinceRenderer() 
    {
        listboxProvinces.setItemRenderer(new ListitemRenderer<Province>() {
            @Override
            public void render(Listitem lstm, final Province t, int i) throws Exception 
            {
                
        
                final Button button = new Button();
                button.setImage("/img/Delete24.png");
                button.addEventListener("onClick", new EventListener() {
                    public void onEvent(Event e) throws Exception {
                        
                        Messagebox.show("Hapus Province "+ t.getProvince_description() +" ?", 
                        "Question", Messagebox.YES | Messagebox.CANCEL,
                        Messagebox.QUESTION,
                        new org.zkoss.zk.ui.event.EventListener(){
	 		public void onEvent(Event e){
                            HashMap<String, Object> map = new HashMap<String, Object>();
                            map.put("pid", t.getId());
	 			if(Messagebox.ON_YES.equals(e.getName())){
                                    
                                            imp.provinces_ondelete(map);
                                           showListProvince();
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
//                        btnEditItem_onUpdate(t);
                        btnEditProvince_onUpdate(t);
                    }
                });
                Listcell editCell = new Listcell();
                editCell.appendChild(editBtn);
                lstm.appendChild(editCell);
                
               
                new Listcell(t.getProvince_code()).setParent(lstm);
                new Listcell(t.getProvince_description()).setParent(lstm);                
            }
        });
    }
       
       
       public void showListCity() 
    {
        listCity = imp.getListCities();
        
        refreshHeaderCity();
        setListboxCityRenderer();
    }
    
     public void refreshHeaderCity() 
    {
        if(listSearchCity != null){
            listboxCities.setModel(new ListModelList<City>(listSearchCity));
        }else{
            listboxCities.setModel(new ListModelList<City>(listCity));
        }
    }
    
       public void setListboxCityRenderer() 
    {
        listboxCities.setItemRenderer(new ListitemRenderer<City>() {
            @Override
            public void render(Listitem lstm, final City t, int i) throws Exception 
            {
                final Button button = new Button();
                button.setImage("/img/Delete24.png");
                button.addEventListener("onClick", new EventListener() {
                    public void onEvent(Event e) throws Exception {
                        
                        Messagebox.show("Hapus City "+t.getCity_description()+ " ?", 
                        "Question", Messagebox.YES | Messagebox.CANCEL,
                        Messagebox.QUESTION,
                        new org.zkoss.zk.ui.event.EventListener(){
	 		public void onEvent(Event e){
                            HashMap<String, Object> map = new HashMap<String, Object>();
                            map.put("pid", t.getId());
	 			if(Messagebox.ON_YES.equals(e.getName())){
                                    
                                            imp.cities_ondelete(map);
                                           showListCity();
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
                        btnEditCity_onUpdate(t);
                    }
                });
                Listcell editCell = new Listcell();
                editCell.appendChild(editBtn);
                lstm.appendChild(editCell);
                new Listcell(t.getCity_code()).setParent(lstm);
                new Listcell(t.getCity_description()).setParent(lstm);                
            }
        });
    }
       
       
          
       public void showListCompany() 
    {
        listCompany = imp.getListCompanies();
        
        refreshHeaderCompany();
        setListboxCompanyRenderer();
    }
    
     public void refreshHeaderCompany() 
    {
        if(listSearchCompany != null){
            listboxCompanies.setModel(new ListModelList<Company>(listSearchCompany));
        }else{
            listboxCompanies.setModel(new ListModelList<Company>(listCompany));
        }
    }
    
       public void setListboxCompanyRenderer() 
    {
        listboxCompanies.setItemRenderer(new ListitemRenderer<Company>() {
            @Override
            public void render(Listitem lstm, final Company t, int i) throws Exception 
            {
                
                final Button button = new Button();
                button.setImage("/img/Delete24.png");
                button.addEventListener("onClick", new EventListener() {
                    public void onEvent(Event e) throws Exception {
                        
                        Messagebox.show("Hapus Company "+ t.getCompany_description() +" ?", 
                        "Question", Messagebox.YES | Messagebox.CANCEL,
                        Messagebox.QUESTION,
                        new org.zkoss.zk.ui.event.EventListener(){
	 		public void onEvent(Event e){
                            HashMap<String, Object> map = new HashMap<String, Object>();
                            map.put("pid", t.getId());
	 			if(Messagebox.ON_YES.equals(e.getName())){
                                            imp.companies_ondelete(map);
                                            showListCompany();
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
                        btnEditCompany_onUpdate(t);
                    }
                });
                Listcell editCell = new Listcell();
                editCell.appendChild(editBtn);
                lstm.appendChild(editCell);
                
               
                new Listcell(t.getCompany_code()).setParent(lstm);
                new Listcell(t.getCompany_description()).setParent(lstm);                 
                new Listcell(t.getCompany_address()).setParent(lstm);                  
                new Listcell(t.getEmail()).setParent(lstm);                     
                new Listcell(t.getPhone_number()).setParent(lstm);                       
                new Listcell(t.getZip_code()).setParent(lstm);                
         
               
            }
        });
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
                                           Messagebox.show(map.toString());
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
                new Listcell(t.getEmail()).setParent(lstm);                     
                new Listcell(t.getPhone_number()).setParent(lstm);                
                new Listcell(t.getContact_person()).setParent(lstm);                       
                new Listcell(t.getZip_code()).setParent(lstm);                
            }
        });
    }
       
       public void showListHeaderSO() 
    {
        listHeaderSO = imp.getListHeaderSO();
        refreshHeaderSO();
        setListboxHeaderSORenderer();
    }
    
     public void refreshHeaderSO() 
    {
       listboxHeaderSO.setModel(new ListModelList<HeaderSO>(listHeaderSO));
    }
    
       public void setListboxHeaderSORenderer() 
    {
        listboxHeaderSO.setItemRenderer(new ListitemRenderer<HeaderSO>() {
            @Override
            public void render(Listitem lstm, final HeaderSO t, int i) throws Exception 
            {
                
        
                final Button button = new Button();
                button.setImage("/img/Delete24.png");
                button.addEventListener("onClick", new EventListener() {
                    public void onEvent(Event e) throws Exception {
                        
                        Messagebox.show("Hapus Master Sales Order "+ t.getSo_number()+" ?" , 
                        "Question", Messagebox.YES | Messagebox.CANCEL,
                        Messagebox.QUESTION,
                        new org.zkoss.zk.ui.event.EventListener(){
	 		public void onEvent(Event e){
                            HashMap<String, Object> map = new HashMap<String, Object>();
                            map.put("pid", t.getId());
	 			if(Messagebox.ON_YES.equals(e.getName())){
                                    
                                            imp.header_so_ondelete(map);
                                            showListHeaderSO();
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
                        btnEditHeaderSO_OnClick(t);
                       
                    }
                });
                Listcell editCell = new Listcell();
                editCell.appendChild(editBtn);
                lstm.appendChild(editCell);
               
                new Listcell(t.getSo_number()).setParent(lstm);
                new Listcell(sd.format(t.getSo_date())).setParent(lstm);                 
                new Listcell(t.getCustomer_id().toString()).setParent(lstm);                  
                new Listcell(t.getStatus().toString()).setParent(lstm);                     
                new Listcell(sd.format(t.getCreation_date())).setParent(lstm);                
                          
            }
        });
    }
       
//      public void showListDetailSO() 
//    {
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("pid", args.get("id"));
//        listDetailSO = imp.getListDetailSO();
//        
//        refreshHeaderDetailSO();
//        setListboxDetailSORenderer();
//    }
//    
//     public void refreshHeaderDetailSO() 
//    {
//       listboxDetailSO.setModel(new ListModelList<DetailSO>(listDetailSO));
//    }
//    
//       public void setListboxDetailSORenderer() 
//    {
//        listboxDetailSO.setItemRenderer(new ListitemRenderer<DetailSO>() {
//            @Override
//            public void render(Listitem lstm, final DetailSO t, int i) throws Exception 
//            {
//                
//        
//                final Button button = new Button();
//                button.setImage("/img/Delete24.png");
//                button.addEventListener("onClick", new EventListener() {
//                    public void onEvent(Event e) throws Exception {
//                        
//                        Messagebox.show("Hapus Sales Order?", 
//                        "Question", Messagebox.CANCEL | Messagebox.YES,
//                        Messagebox.QUESTION,
//                        new org.zkoss.zk.ui.event.EventListener(){
//	 		public void onEvent(Event e){
//                            HashMap<String, Object> map = new HashMap<String, Object>();
//                            map.put("pid", t.getId());
//	 			if(Messagebox.ON_YES.equals(e.getName())){
//                                           imp.detail_so_ondelete(map);
//                                           showListDetailSO();
//	 			}else if(Messagebox.ON_CANCEL.equals(e.getName())){
//	 				
//	 			}
//                            }
//                        });
//                    }
//                });
//                Listcell cell = new Listcell();
//                cell.appendChild(button);
//                lstm.appendChild(cell);
//                
//                final Button editBtn = new Button();
//                editBtn.setImage("/img/Edit24.png");
//                 editBtn.addEventListener("onClick", new EventListener() {
//                    public void onEvent(Event e) throws Exception {
////                        btnEditItem_onUpdate(t);
//                    }
//                });
//                Listcell editCell = new Listcell();
//                editCell.appendChild(editBtn);
//                lstm.appendChild(editCell);
//               
//                new Listcell(t.getSo_id().toString()).setParent(lstm);
//                new Listcell(t.getLine().toString()).setParent(lstm);                 
//                new Listcell(t.getItem_id().toString()).setParent(lstm);                  
//                new Listcell(t.getQuantity().toString()).setParent(lstm);                     
//                new Listcell(t.getPrice().toString()).setParent(lstm); 
//                new Listcell(t.getDiscount().toString()).setParent(lstm);                 
//                new Listcell(t.getTax_amount().toString()).setParent(lstm);  
// 
//                          
//            }
//        });
//    }
       
       public void test(){
           Messagebox.show("Lol");
       }
}
