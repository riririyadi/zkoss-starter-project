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
import model.Company;
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
public class ListCompaniesController extends SelectorComposer<Component> {
    
    private CompanyController companyController;

    public CompanyController getCompanyController() {
        return companyController;
    }

    public void setCompanyController(CompanyController companyController) {
        this.companyController = companyController;
    }
    
    @Wire
    Listbox listboxCompanies;
    
    @Wire
    Div companies_div;
    
    @Wire
    Textbox searchCompany,
            country_id, country_code, country_description,
            province_id, province_code, province_description,
            city_id, city_code, city_description;
    
    @Wire
    Paging userPaging;
    
    List<Company> listCompany, listSearchCompany;   
    
    DemoImplement imp = new DemoImplement();
    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        showListCompany();
    }
           
    
    Integer totalRecord = 0;    
    Integer pageSize = 10;
    Integer currentPage = 1;

     
    @Listen(Events.ON_OK + "= #searchCompany")
    public void searchCompanyListener(){
   
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("psearch", searchCompany.getValue());
        totalRecord = imp.onCountCompanies(map);
        System.out.println("total record " + totalRecord.toString());
        userPaging.setTotalSize(totalRecord);
//        pageSize = listboxCountries.getPageSize();
        userPaging.setPageSize(10);
        showListCompanyonDemand(searchCompany.getValue());

        
    }
    
     @Listen("onPaging=#userPaging")
    public void onPagingUserPaging(PagingEvent pe) throws ParseException{
      currentPage = pe.getActivePage() + 1;
      showListCompanyonDemand(searchCompany.getValue());
    }
    
      public void showListCompanyonDemand(String psearch) 
    {
        HashMap<String, Object> mapPaging = new HashMap<String, Object>();
        mapPaging.put("pageSize", pageSize);        
        mapPaging.put("pageNumber", currentPage);
        mapPaging.put("psearch", psearch);
        listSearchCompany = imp.findListCompany(mapPaging);
   
        refreshHeaderCompany();
        setListboxCompanyRenderer();

    }
    
    
       @Listen(Events.ON_CLICK + "=#btnNewCompany")
    public void btnNewCompany_OnClick(Event e)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("ModuleMainController", this);
        final Window window = (Window) Executions.createComponents("/components/entry_company.zul", null, map);
        window.doHighlighted();
        window.setPosition("center");
    }
    
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
       
        listSearchCompany = imp.getListCompaniesbyFilter(map);
        refreshHeaderCompany();
        setListboxCompanyRenderer();
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

        listSearchCompany = null;
        refreshHeaderCompany();
        setListboxCompanyRenderer();
    }
    
       
//     @Listen(Events.ON_OK + "= #searchCompany")
//   public void searchCompanyListener(){
//       if(searchCompany.getValue().isEmpty()){
//           listSearchCompany = null;
//       } 
//       else {
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("psearch", searchCompany.getText());
//            listSearchCompany = imp.findListCompany(map);
//       }
//    
//        refreshHeaderCompany();
//        setListboxCompanyRenderer();
//   }
   
    @Listen(Events.ON_CLICK + "= #clearSearchCompany")
    public void clearSearchCompany(){
       searchCompany.setValue("");
           listSearchCompany = null;
     
        refreshHeaderCompany();
        setListboxCompanyRenderer();
   }
    
    @Listen(Events.ON_CLICK + "=#btnEditCompany")
    public void btnEditCompany_onUpdate(Company t)
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
        map.put("pcompany_code", t.getCompany_code());        
        map.put("pcompany_description", t.getCompany_description());
        map.put("pcompany_address", t.getCompany_address());
        map.put("pemail", t.getEmail());        
        map.put("pphone_number", t.getPhone_number());        
        map.put("pzip_code", t.getZip_code());
        map.put("ModuleMainController", this);
          
    	final Window window = (Window) Executions.createComponents("/components/entry_company.zul", null, map);
        window.doHighlighted();
        window.setPosition("center");
    }
    
    @Listen(Events.ON_CLICK + "=#btnRefreshCompany")
    public void btnRefreshCompany_OnClick()
    {
       showListCompany();
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
                                            Messagebox.show(map.get("outmsg").toString());
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
        w.setParent(companies_div);
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
        w.setParent(companies_div);
        w.doModal();
        }
    }
    
    
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
        w.setParent(companies_div);
        w.doModal();
        }
    }
    
       
}
