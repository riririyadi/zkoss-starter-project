package controller;


import static controller.ItemController.showLog;
import implement.DemoImplement;
import java.util.HashMap;
import java.util.Map;
import org.apache.ibatis.annotations.AutomapConstructor;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author RIRI
 */
public class CountryController extends SelectorComposer<Component>{
    
     private ListCountriesController listCountriesController;

    public ListCountriesController getListCountriesController() {
        return listCountriesController;
    }

    public void setListCountriesController(ListCountriesController listCountriesController) {
        this.listCountriesController = listCountriesController;
    }
    
    @Wire
    Window entry_country;
    
    @Wire
    Textbox country_code, country_description;
    
    DemoImplement imp = new DemoImplement();

    Map args;
    
     @Override
    public void doAfterCompose(Component comp) throws Exception 
    {
        super.doAfterCompose(comp);        
        args = Executions.getCurrent().getArg();
     
        if (args.containsKey("ModuleMainController")) {
		setListCountriesController((ListCountriesController) args.get("ModuleMainController"));
		// Set THIS controller BACK TO THE MainController 
		getListCountriesController().setCountryController(this);
                       
	}
        if (!args.containsKey("pid")) 
        {
        	 entry_country.setTitle("Add Country");              
        }
        else 
        {
                entry_country.setTitle("Edit Country");  
        	country_code.setValue((String) args.get("pcountry_code"));
        	country_description.setValue((String) args.get("pcountry_description"));        	
               
        }
    }
    
    @Listen(Events.ON_CLICK + "=#btnSave")
    public void btnSave_onClick(){
 
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (!args.containsKey("pid")) 
        {
            map.put("pcountry_code", country_code.getValue().isEmpty() ? null : country_code.getText());        
            map.put("pcountry_description", country_description.getValue().isEmpty() ? null : country_description.getText());
            Messagebox.show(map.toString());
            imp.countries_oninsert(map);
            Messagebox.show(map.get("outmsg").toString());
            entry_country.detach();
             getListCountriesController().showListCountry();
        
          
        }
        else 
        {
            map.put("pid",                  args.get("pid"));
            map.put("pcountry_code",        country_code.getValue().isEmpty() ? null : country_code.getText());
            map.put("pcountry_description", country_description.getValue().isEmpty()? null : country_description.getText());
            
            showLog(map, "sebelum");
            imp.countries_onupdate(map);
            entry_country.detach();
            showLog(map, "sesudah");
            
            Messagebox.show(map.get("outmsg").toString());     	
                 getListCountriesController().showListCountry();
        
        }
     
            
    }
 
    @Listen(Events.ON_CLICK + "=#btnClose")
    public void btnClose_onClick(){
        entry_country.detach();
    }
    
}
