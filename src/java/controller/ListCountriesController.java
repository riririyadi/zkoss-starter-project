/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import implement.DemoImplement;
import java.util.HashMap;
import java.util.List;
import model.Country;
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
public class ListCountriesController extends SelectorComposer<Component>{
    
    
    private CountryController countryController;

    public CountryController getCountryController() {
        return countryController;
    }

    public void setCountryController(CountryController countryController) {
        this.countryController = countryController;
    }
    
    @Wire
    Listbox listboxCountries;
    
    @Wire
    Textbox searchCountry;
    
     List<Country> listCountry, listSearchCountry; 
    
       
    DemoImplement imp = new DemoImplement();
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        

        showListCountry();
    }
    
    
        @Listen(Events.ON_CLICK + "=#btnNewCountry")
    public void btnNewCountry_OnClick(Event e)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("ModuleMainController", this);
        final Window window = (Window) Executions.createComponents("/components/entry_country.zul", null, map);
        window.doHighlighted();
        window.setPosition("center");
    }
    
    
    @Listen(Events.ON_CLICK + "=#btnEditCountry")
    public void btnEditCountry_onUpdate(Country t)
    {

  	HashMap<String, Object> map = new HashMap<String, Object>();
  
    	map.put("pid", t.getId());    	
        map.put("pcountry_code", t.getCountry_code());       
        map.put("pcountry_description", t.getCountry_description());    
        map.put("ModuleMainController", this);
        
    	final Window window = (Window) Executions.createComponents("/components/entry_country.zul", null, map);
        window.doHighlighted();
        window.setPosition("center");
        
    }
    
    @Listen(Events.ON_CLICK + "=#btnRefreshCountry")
    public void btnRefreshCountry_OnClick()
    {
       showListCountry();
   
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
                                            Messagebox.show(map.get("outmsg").toString());
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
   
}
