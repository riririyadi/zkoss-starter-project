package controller;


import id.berkah.admin.controller.LovController;
import implement.DemoImplement;
import java.util.HashMap;
import java.util.Map;
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
public class CityController extends SelectorComposer<Component>{
    
    private ListCitiesController listCitiesController;

    public ListCitiesController getListCitiesController() {
        return listCitiesController;
    }

    public void setListCitiesController(ListCitiesController listCitiesController) {
        this.listCitiesController = listCitiesController;
    }
    
    @Wire
    Window entry_city;
    
    @Wire
    Textbox province_id, province_code, province_description;
    
     @Wire
    Textbox country_id, country_code, country_description;
    
    @Wire
    Textbox city_code, city_description;
    
    DemoImplement imp = new DemoImplement();
    Map args;
    
    @Override
    public void doAfterCompose(Component comp) throws Exception 
    {
        super.doAfterCompose(comp);        
        args = Executions.getCurrent().getArg();
     
        if (args.containsKey("ModuleMainController")) {
		setListCitiesController((ListCitiesController) args.get("ModuleMainController"));
		// Set THIS controller BACK TO THE MainController 
		getListCitiesController().setCityController(this);
                       
	}

        if (!args.containsKey("pid")) 
        {
                entry_city.setTitle("Add City");       
        }
        else 
        {
                entry_city.setTitle("Edit City");      
                province_id.setValue(args.get("pprovince_id").toString());                
                province_code.setValue((String) args.get("pprovince_code"));                
                province_description.setValue((String) args.get("pprovince_description"));
        	city_code.setValue((String) args.get("pcity_code"));
        	city_description.setValue((String) args.get("pcity_description"));        	
             
        }
    }
    
    @Listen(Events.ON_CLICK + "=#btnSave")
    public void btnSave_onClick(){
        
        if(!province_id.getValue().isEmpty()){
        
        HashMap<String, Object> map = new HashMap<String, Object>();
        
        if (!args.containsKey("pid")) 
        {
            map.put("pprovince_id", province_id.getValue().isEmpty() ? null : new Long (province_id.getText()));        
            map.put("pcity_code", city_code.getValue().isEmpty() ? null : city_code.getText());        
            map.put("pcity_description", city_description.getValue().isEmpty() ? null : city_description.getText());
            
            imp.cities_oninsert(map);
            Messagebox.show(map.get("outmsg").toString());                
                    
        }
        else 
        {
            map.put("pid",                  args.get("pid"));
            map.put("pprovince_id", province_id.getValue().isEmpty() ? null : new Long (province_id.getText()));        
            map.put("pcity_code", city_code.getValue().isEmpty() ? null : city_code.getText());        
            map.put("pcity_description", city_description.getValue().isEmpty() ? null : city_description.getText());
            imp.cities_onupdate(map);
            Messagebox.show(map.toString());  
        }
        getListCitiesController().showListCity();
        entry_city.detach();}
        else {
         Messagebox.show("Fill all the field, Please!");
        }
    }
    
    
    @Listen(Events.ON_CLICK + "=#btnClose")
    public void btnClose_onClick(){
        entry_city.detach();
    }
    
      @Listen(Events.ON_CLICK + "=#btnLovProvince")
    public void showLovProvince()
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        
        String query = "select id, province_code, province_description "
                      + "from provinces ";
                    
//                      + " And upper(province_code) like upper('%?%') or upper(province_description) like upper('%?%') "
//                      + "order by province_code"
//                      + "Limit param1 offset param2";
        
        composerLov.setQuery(query);
        composerLov.setQueryTotal("select count(*) from provinces ");
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
        w.setParent(entry_city);
        w.doModal();
    }
}
