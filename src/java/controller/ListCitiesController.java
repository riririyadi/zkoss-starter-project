/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import implement.DemoImplement;
import java.util.HashMap;
import java.util.List;
import model.City;
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
public class ListCitiesController extends SelectorComposer<Component>{
    
    private CityController cityController;

    public CityController getCityController() {
        return cityController;
    }

    public void setCityController(CityController cityController) {
        this.cityController = cityController;
    }
    
    @Wire
    Listbox   listboxCities;
    
    @Wire
    Textbox searchCity;
    
    @Wire
    Paging userPaging;
    
    List<City> listCity, listSearchCity;
    
    DemoImplement imp = new DemoImplement();
    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        showListCity();        
    }

    Integer totalRecord = 0;    
    Integer pageSize = 10;
    Integer currentPage = 1;

     
     @Listen(Events.ON_OK + "= #searchCity")
   public void searchCountryListener(){
     HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("psearch", searchCity.getValue());
        totalRecord = imp.onCountCities(map);
        System.out.println("total record " + totalRecord.toString());
        userPaging.setTotalSize(totalRecord);
//        pageSize = listboxCountries.getPageSize();
        userPaging.setPageSize(10);
        showListCityonDemand(searchCity.getValue());

        
   }
    
     @Listen("onPaging=#userPaging")
    public void onPagingUserPaging(PagingEvent pe) throws ParseException{
      currentPage = pe.getActivePage() + 1;
      showListCityonDemand(searchCity.getValue());
    }
    
    @Listen(Events.ON_CLICK + "=#btnNewCity")
    public void btnNewCity_OnClick(Event e)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("ModuleMainController", this);
        final Window window = (Window) Executions.createComponents("/components/entry_city.zul", null, map);
        window.doHighlighted();
        window.setPosition("center");
      
    }
    
    @Listen(Events.ON_CLICK + "=#btnEditCity")
    public void btnEditCity_onUpdate(City t)
    {

  	HashMap<String, Object> map = new HashMap<String, Object>();
    	map.put("pid", t.getId());    	
        map.put("pprovince_id", t.getProvince_id());
        map.put("pprovince_code", t.getProvince_code());       
        map.put("pprovince_description", t.getProvince_description());     
        map.put("pcity_code", t.getCity_code());       
        map.put("pcity_description", t.getCity_description());    
        map.put("ModuleMainController", this);
  

        
    	final Window window = (Window) Executions.createComponents("/components/entry_city.zul", null, map);
        window.doHighlighted();
        window.setPosition("center");
              
    }
    
    @Listen(Events.ON_CLICK + "=#btnRefreshCity")
    public void btnRefreshCity_OnClick()
    {
       showListCity();
    }

    
    
//    @Listen(Events.ON_OK + "= #searchCity")
//    public void searchCityListener(){
//       if(searchCity.getValue().isEmpty()){
//           listSearchCity = null;
//       } 
//       else {
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("psearch", searchCity.getText());
//            listSearchCity = imp.findListCity(map);
//       }
//          
//        refreshHeaderCity();
//        setListboxCityRenderer();
//   }
   
    
      public void showListCityonDemand(String psearch) 
    {
        HashMap<String, Object> mapPaging = new HashMap<String, Object>();
        mapPaging.put("pageSize", pageSize);        
        mapPaging.put("pageNumber", currentPage);
        mapPaging.put("psearch", psearch);
        listSearchCity = imp.findListCity(mapPaging);
   
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
       
}
