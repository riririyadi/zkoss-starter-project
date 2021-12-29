/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import id.berkah.admin.controller.LovController;
import implement.DemoImplement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.*;
import java.util.List;
import model.Customer;
import model.DetailSO;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
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
public class MasterSOController extends SelectorComposer<Component> {
    
     private DetailSOController detailSOController;     
     
     private ListTransactionsController listTransactionsController;

    public ListTransactionsController getListTransactionsController() {
        return listTransactionsController;
    }

    public void setListTransactionsController(ListTransactionsController listTransactionsController) {
        this.listTransactionsController = listTransactionsController;
    }

    @Wire
    Window master_so;

    public DetailSOController getDetailSOController() {
        return detailSOController;
    }

    public void setDetailSOController(DetailSOController detailSOController) {
        this.detailSOController = detailSOController;
    }
    
    @Wire
    Datebox so_date;
    
    @Wire
    Textbox so_number, status;
    
    @Wire
    Button TambahItem, tambahDetail;
    
    @Wire
    Textbox customer_id, customer_code, customer_description;
    
    @Wire
    Listbox listboxDetailSO;
    
    List<DetailSO> listDetailSO;
    
    Boolean isModif, isEdit;
    
    DemoImplement imp = new DemoImplement();
     
    SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    Map args;
    Map invoiceData;
    Date date = new Date();
//To change body of generated methods, choose Tools | Templates.
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        args = Executions.getCurrent().getArg();

        
        if (args.containsKey("ModuleMainController")) {
            setListTransactionsController((ListTransactionsController) args.get("ModuleMainController"));

//            getListTransactionsController().setMasterSOController(this);
                       
	}
        
        if (!args.containsKey("pid")) {
            so_date.setValue(date);

        } else {
      
            so_number.setValue((String) args.get("pso_number"));
            so_date.setValue((Date) args.get("pso_date"));
            status.setValue(args.get("pstatus").equals(1) ? "Order Approved":"Order Accepted");
            customer_id.setValue(args.get("pcustomer_id").toString());
            customer_code.setValue((String)args.get("pcustomer_code"));
            customer_description.setValue((String) args.get("pcustomer_description"));
            showListDetailSO();
        }
    }
    
     
    
  
    
    @Listen(Events.ON_CLICK + "= #btnNew")
      public void btnNew_onClick(){
          args.clear();
            so_number.setValue("");
            so_date.setValue(date);
            status.setValue("");
            customer_id.setValue("");
            customer_code.setValue("");
            customer_description.setValue("");
            showListDetailSO();
      }

    @Listen(Events.ON_CLICK + "=#btnSave")
    public void btnSave_onClick(){
       
        if(!customer_id.getValue().isEmpty()){

        
        HashMap<String, Object> map = new HashMap<String, Object>();
        
        if(!args.containsKey("pid")){
                
                Date date = new Date();
                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                Integer year  = localDate.getYear();
                Integer month = localDate.getMonthValue();
                Integer day   = localDate.getDayOfMonth();    
                    
                
               
                Integer minimum = 200;
                Random ran = new Random();
                Integer ranNumber = minimum + ran.nextInt(1000 - 0 + 1);
                String so_number_now = String.join(".", year.toString(),month.toString(), day.toString(), ranNumber.toString());

                so_number.setValue(so_number_now);
                status.setValue("Order Accepted");
                map.put("pso_number", so_number.getValue().isEmpty() ? null : so_number.getText());
                map.put("pso_date", sd.format(so_date.getValue()));
                map.put("pcustomer_id", customer_id.getValue().isEmpty() ? null : new Long(customer_id.getText()));
                map.put("pstatus", 0);
                                
                imp.header_so_oninsert(map);
                Messagebox.show(map.get("outmsg").toString());
                getListTransactionsController().showListHeaderSO();
                
                HashMap<String, Object> mapReturnedValue = new HashMap<String, Object>();
                mapReturnedValue.put("pid", map.get("outid"));                
                mapReturnedValue.put("pso_number", map.get("pso_number"));                
                mapReturnedValue.put("pcustomer_id", map.get("pcustomer_id"));
                mapReturnedValue.put("pso_date", map.get("pso_date"));
                mapReturnedValue.put("pstatus", map.get("pstatus"));
                args = mapReturnedValue;
                }
        } else {
         Messagebox.show("Input Customer First, Please!");
        }
    }
     @Listen(Events.ON_CLICK + "= #tambahDetail")
    public void doNewItem() {
        if(so_number.getValue().isEmpty()){
            return;    
        }
        
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("pso_number", so_number.getValue().isEmpty()? null : so_number.getText());        
        map.put("pso_id", args.get("pid"));
        map.put("ModuleMainController", this);

        final Window window = (Window) Executions.createComponents("/components/detail_so.zul", null, map);
        window.doHighlighted();
        window.setPosition("center");
       
       
    }
    
    
     public void btnEditDetailSO_onUpdate(DetailSO t){
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("pid", t.getId());
        map.put("pso_number", so_number.getValue().isEmpty()? null : so_number.getText());        
        map.put("pso_id", t.getSo_id());
        map.put("pline",t.getLine());         
        map.put("pitem_id",t.getItem_id());        
        map.put("pitem_code", t.getItem_code());
        map.put("pitem_description", t.getItem_description());        
        map.put("pitem_price", t.getItem_price());
        map.put("pprice", t.getPrice()); 
        map.put("pquantity", t.getQuantity());        
        map.put("pdiscount", t.getDiscount());
        map.put("ptax_amount", t.getTax_amount());
        map.put("ModuleMainController", this);
      

        final Window window = (Window) Executions.createComponents("/components/detail_so.zul", null, map);
        window.doHighlighted();
        window.setPosition("center");
     }
    
      @Listen(Events.ON_CLICK + "= #btnClose")
      public void btnClose_onClick(){
          master_so.detach();
      }
          
      @Listen(Events.ON_CLICK + "= #btnApprove")
      public void btnApprove_onClick(){
       

            if(args.containsKey("pid")){
              if(args.get("pstatus").equals(0)){
                Messagebox.show("Approve Order Number "+ args.get("pso_number").toString() +" ?" , 
                        "Question", Messagebox.YES | Messagebox.CANCEL,
                        Messagebox.QUESTION,
                        new org.zkoss.zk.ui.event.EventListener(){
	 		public void onEvent(Event e){
                            HashMap<String, Object> map = new HashMap<String, Object>();
                            map.put("pid", args.get("pid"));
                            map.put("pso_number", args.get("pso_number"));
                            map.put("pso_date", sd.format(so_date.getValue()));            
                            map.put("pcustomer_id", args.get("pcustomer_id"));            
                            map.put("pstatus", 1);
                            
	 			if(Messagebox.ON_YES.equals(e.getName())){
                                    
                                            imp.header_so_onupdate(map);
                                            getListTransactionsController().showListHeaderSO();
                                            Messagebox.show(map.get("outmsg").toString());
                                                 status.setValue("Order Approved");
	 			}else if(Messagebox.ON_CANCEL.equals(e.getName())){
	 				
	 			}
                            }
                        });
            
              }
              
          }else{}

      }
         
      
   
      public void showListDetailSO() 
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("pid", args.get("pid"));
        listDetailSO = imp.getListDetailSO(map);
        
        refreshHeaderDetailSO();
        setListboxDetailSORenderer();
    }
    
     public void refreshHeaderDetailSO() 
    {
       listboxDetailSO.setModel(new ListModelList<DetailSO>(listDetailSO));
    }
    
       public void setListboxDetailSORenderer() 
    {
        listboxDetailSO.setItemRenderer(new ListitemRenderer<DetailSO>() {
            @Override
            public void render(Listitem lstm, final DetailSO t, int i) throws Exception 
            {
                
        
                final Button button = new Button();
                button.setImage("/img/Delete24.png");
                button.addEventListener("onClick", new EventListener() {
                    public void onEvent(Event e) throws Exception {
                        
                        Messagebox.show("Hapus Detail Order?", 
                        "Question", Messagebox.CANCEL | Messagebox.YES,
                        Messagebox.QUESTION,
                        new org.zkoss.zk.ui.event.EventListener(){
	 		public void onEvent(Event e){
                            HashMap<String, Object> map = new HashMap<String, Object>();
                            map.put("pid", t.getId());
	 			if(Messagebox.ON_YES.equals(e.getName())){
                                           imp.detail_so_ondelete(map);
                                           showListDetailSO();
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
                           btnEditDetailSO_onUpdate(t);
                    }
                });
                Listcell editCell = new Listcell();
                editCell.appendChild(editBtn);
                lstm.appendChild(editCell);
               
             
                new Listcell(t.getLine().toString()).setParent(lstm);                 
                new Listcell(t.getItem_id().toString()).setParent(lstm);                  
                new Listcell(t.getQuantity().toString()).setParent(lstm);                     
                new Listcell(t.getPrice().toString()).setParent(lstm); 
                new Listcell(t.getDiscount().toString()).setParent(lstm);                 
                new Listcell(t.getTax_amount().toString()).setParent(lstm);  
 
                          
            }
        });
    }
 
        public void doSaveUpdateDeleteItem(DetailSO t) {
        Map map = new HashMap();
        map.put("pso_id", t.getSo_id());
        map.put("pitem_id", t.getItem_id());
        map.put("pline", t.getLine());
        map.put("pquantity", t.getQuantity());
        map.put("pprice", t.getPrice());
        map.put("pdiscount", t.getDiscount());
        map.put("ptax_amount", t.getTax_amount());
        if (t.getIS_NEW()) {
//            if(con.isSaved()){
            Map out = imp.detail_so_oninsert(map);
            showListDetailSO();
            isModif = false;
         
            isEdit = false;
            Messagebox.show("Save Success");
            System.out.println("DATA BERHASIL DI INPUT");
//            }
        } else if (t.getIS_EDIT()) {
//            if(con.isUpdate()){
        map.put("pid", t.getId());
        map.put("pso_id", t.getSo_id());
        map.put("pitem_id", t.getItem_id());
        map.put("pline", t.getLine());
        map.put("pquantity", t.getQuantity());
        map.put("pprice", t.getPrice());
        map.put("pdiscount", t.getDiscount());
        map.put("ptax_amount", t.getTax_amount());
            Map out = imp.detail_so_onupdate(map);
            System.out.println("DATA BERHASIL DI EDIT");
            Messagebox.show("Update Success");
            showListDetailSO();
            isModif = false;
           
            isEdit = false;
//            }
        } else {
//            if(con.isDeleted()){
            map.put("pid", t.getId());
            Map out = imp.detail_so_ondelete(map);
            Messagebox.show("Delete Success");
            showListDetailSO();
            System.out.println("DATA BERHASIL DI HAPUS");
//            }
        }
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
        composerLov.setTitle("List of Customers");
        composerLov.setWidth("500px");
        composerLov.setHeight("350px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);
        Window w = (Window) Executions.createComponents("/components/lov.zul", null, map);
        w.setParent(master_so);
        w.doModal();}
    
}
