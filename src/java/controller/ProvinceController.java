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
public class ProvinceController extends SelectorComposer<Component>{
    
    private ListProvincesController listProvinceController;

    public ListProvincesController getListProvinceController() {
        return listProvinceController;
    }

    public void setListProvinceController(ListProvincesController listProvinceController) {
        this.listProvinceController = listProvinceController;
    }

    
    @Wire
    Window entry_province;
    
    @Wire
    Textbox country_id, country_code, country_description;
    
    @Wire
    Textbox province_code, province_description;
    
    DemoImplement imp = new DemoImplement();
    
    Map args;
    
    @Override
    public void doAfterCompose(Component comp) throws Exception 
    {
        super.doAfterCompose(comp);        
        args = Executions.getCurrent().getArg();
     
if (args.containsKey("ModuleMainController")) {
		setListProvinceController((ListProvincesController) args.get("ModuleMainController"));
		// Set THIS controller BACK TO THE MainController 
		getListProvinceController().setProvinceController(this);
                       
	}
        if (!args.containsKey("pid")) 
        {
        	      entry_province.setTitle("Add Province");           
        }
        else 
        {
            entry_province.setTitle("Edit Province");  
        	country_id.setValue(args.get("pcountry_id").toString());        	
                country_code.setValue((String) args.get("pcountry_code"));
        	country_description.setValue((String) args.get("pcountry_description"));        	
                province_code.setValue((String) args.get("pprovince_code"));                
                province_description.setValue((String) args.get("pprovince_description"));
        }
    }
    
    @Listen(Events.ON_CLICK + "=#btnSave")
    public void btnSave_onClick(){
        if(!country_id.getValue().isEmpty()){
            
        HashMap<String, Object> map = new HashMap<String, Object>();
        
         if (!args.containsKey("pid")) 
        {
        map.put("pcountry_id", country_id.getValue().isEmpty() ? null : new Long (country_id.getText()));        
        map.put("pprovince_code", province_code.getValue().isEmpty() ? null : province_code.getText());        
        map.put("pprovince_description", province_description.getValue().isEmpty() ? null : province_description.getText());


        imp.provinces_oninsert(map);
        Messagebox.show(map.get("outmsg").toString()); 
                entry_province.detach();
                getListProvinceController().showListProvince();
        }
        else 
        {
        map.put("pid",                  args.get("pid"));
        map.put("pcountry_id", country_id.getValue().isEmpty() ? null : new Long (country_id.getText()));        
        map.put("pprovince_code", province_code.getValue().isEmpty() ? null : province_code.getText());        
        map.put("pprovince_description", province_description.getValue().isEmpty() ? null : province_description.getText());

        imp.provinces_onupdate(map);
       Messagebox.show(map.get("outmsg").toString()); 
       
        entry_province.detach();
        getListProvinceController().showListProvince();
        } } else {
         Messagebox.show("Fill all the field, Please!");
        }
      

    }
    
    @Listen(Events.ON_CLICK + "=#btnClose")
    public void btnClose_onClick(){
        entry_province.detach();
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
        w.setParent(entry_province);
        w.doModal();
    }
}
