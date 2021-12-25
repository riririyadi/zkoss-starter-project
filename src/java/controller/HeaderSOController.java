package controller;


import id.berkah.admin.controller.LovController;
import implement.DemoImplement;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
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
public class HeaderSOController extends SelectorComposer<Component> {
    @Wire
    Window winSalesOrder;
    
    @Wire
    Textbox so_number;
    
    @Wire
    Textbox customer_id, customer_code, customer_description;
    
    @Wire
    Datebox so_date;
    
    
    @Wire
    Combobox status;
    
    DemoImplement imp = new DemoImplement();
   

    
    SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    
    @Listen(Events.ON_CLICK + "=#btnSave")
    public void btnSave_onClick(){
        
        HashMap<String, Object> map = new HashMap<String, Object>();
                
            map.put("pso_number", so_number.getValue().isEmpty() ? null : so_number.getText());
            map.put("pso_date", sd.format(so_date.getValue()));
            map.put("pcustomer_id", customer_id.getValue().isEmpty() ? null : new Long(customer_id.getText()));
            map.put("pstatus", status.getSelectedIndex());
                                
            imp.header_so_oninsert(map);
            Messagebox.show(map.toString());
            
            winSalesOrder.detach();
      
            
    }
    
    @Listen(Events.ON_CLICK + "=#btnClose")
        public void btnClose_onClick(){
            winSalesOrder.detach();
        }
    
    
    @Listen(Events.ON_CLICK + "=#btnLovCustomer")
    public void showLovCustomer()
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        
        String query = "select id, customer_code, customer_description "
                      + "from customers";
//                      + "where province_id = " + province_id.getValue();
//                      + " And upper(province_code) like upper('%?%') or upper(province_description) like upper('%?%') "
//                      + "order by province_code"
//                      + "Limit param1 offset param2";
        
        composerLov.setQuery(query);
        composerLov.setQueryTotal("select count(*) from customers");
//                + "And upper(province_code) like upper('%?%') or upper(province_description) like upper('%?%')");
        composerLov.setSelectedColumn(new int[]{
            0, 1, 2
        });
        composerLov.setColumnWidth(new String[]{
            "50px", "300px"
        });

        composerLov.setComponentTransferData(new Textbox[]{
            customer_id, customer_code, customer_description
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
        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(winSalesOrder);
        w.doModal();
    }
    
    
}
