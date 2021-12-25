/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import implement.DemoImplement;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import model.HeaderSO;
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
public class ListTransactionsController extends SelectorComposer<Component>{
    
        
    private MasterSOController masterSOController;

    public MasterSOController getMasterSOController() {
        return masterSOController;
    }

    public void setMasterSOController(MasterSOController masterSOController) {
        this.masterSOController = masterSOController;
    }
    
    @Wire
    Listbox listboxHeaderSO;
    
    @Wire
    Textbox searchHeaderSO;
    
    DemoImplement imp = new DemoImplement();
    List<HeaderSO> listHeaderSO; 
    
    SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    
    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        
     showListHeaderSO();


    }
    
    
      @Listen(Events.ON_CLICK + "=#btnNewHeaderSO")
    public void btnNewHeaderSO_OnClick(Event e)
    {  HashMap<String, Object> map = new HashMap<String, Object>();
      map.put("ModuleMainController", this);
        final Window window = (Window) Executions.createComponents("/components/master_so.zul", null, map);
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
        map.put("ModuleMainController", this);


        final Window window = (Window) Executions.createComponents("/components/master_so.zul", null, map);
        window.doHighlighted();
        window.setPosition("center");
      
    }
    
    @Listen(Events.ON_CLICK + "=#btnRefreshHeaderSO")
    public void btnRefreshHeaderSO_OnClick()
    {
       showListHeaderSO();
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
                        
                        Messagebox.show("Hapus Order "+ t.getSo_number()+" ?" , 
                        "Question", Messagebox.YES | Messagebox.CANCEL,
                        Messagebox.QUESTION,
                        new org.zkoss.zk.ui.event.EventListener(){
	 		public void onEvent(Event e){
                            HashMap<String, Object> map = new HashMap<String, Object>();
                            map.put("pid", t.getId());
	 			if(Messagebox.ON_YES.equals(e.getName())){
                                    
                                            imp.header_so_ondelete(map);
                                            showListHeaderSO();
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
                        btnEditHeaderSO_OnClick(t);
                       
                    }
                });
                Listcell editCell = new Listcell();
                editCell.appendChild(editBtn);
                lstm.appendChild(editCell);
               
                new Listcell(t.getSo_number()).setParent(lstm);
                new Listcell(sd.format(t.getSo_date())).setParent(lstm);                 
                new Listcell(t.getCustomer_id().toString()).setParent(lstm);                  
                new Listcell(t.getStatus().equals(1) ? "Order Approved":"Order Accepted").setParent(lstm);                     
                new Listcell(sd.format(t.getCreation_date())).setParent(lstm);                
                          
            }
        });
    }
}
