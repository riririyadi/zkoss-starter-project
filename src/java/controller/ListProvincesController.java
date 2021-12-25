package controller;

import implement.DemoImplement;
import java.util.HashMap;
import java.util.List;
import model.Country;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author RIRI
 */
public class ListProvincesController extends SelectorComposer<Component>{
    
    private ProvinceController provinceController;

    public ProvinceController getProvinceController() {
        return provinceController;
    }

    public void setProvinceController(ProvinceController provinceController) {
        this.provinceController = provinceController;
    }
    
    @Wire
    Listbox listboxProvinces;
    
    @Wire
    Textbox searchProvince;
    
     List<Province> listProvince, listSearchProvince; 
    
       
    DemoImplement imp = new DemoImplement();
    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        
        showListProvince();

    }
    
     @Listen(Events.ON_CLICK + "=#btnNewProvince")
    public void btnNewProvince_OnClick(Event e)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("ModuleMainController", this);
        final Window window = (Window) Executions.createComponents("/components/entry_province.zul", null, map);
        window.doHighlighted();
        window.setPosition("center");
      
    }
    
    @Listen(Events.ON_CLICK + "=#btnEditProvince")
    public void btnEditProvince_onUpdate(Province t)
    {

  	HashMap<String, Object> map = new HashMap<String, Object>();
    	map.put("pid", t.getId());    
        map.put("pcountry_id", t.getCountry_id());
        map.put("pcountry_code", t.getCountry_code());        
        map.put("pcountry_description", t.getCountry_description());
        map.put("pprovince_code", t.getProvince_code());       
        map.put("pprovince_description", t.getProvince_description());
        map.put("ModuleMainController", this);

        
    	final Window window = (Window) Executions.createComponents("/components/entry_province.zul", null, map);
        window.doHighlighted();
        window.setPosition("center");      
    }
    
    
    
    @Listen(Events.ON_CLICK + "=#btnRefreshProvince")
    public void btnRefreshProvince_OnClick()
    {
       showListProvince();
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
                                            Messagebox.show(map.get("outmsg").toString());
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
}
