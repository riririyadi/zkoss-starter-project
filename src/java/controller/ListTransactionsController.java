package controller;

import id.berkah.admin.controller.LovController;
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
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
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

    @Wire
    Div transactions_div;
    @Wire
    Listbox listboxHeaderSO;
    @Wire
    Datebox start_date, end_date;
    @Wire
    Textbox searchHeaderSO, customer_id, customer_description, customer_code;
        

    DemoImplement imp = new DemoImplement();
    List<HeaderSO> listHeaderSO, listSearchHeaderSO; 

    SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");


    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
            showListHeaderSO();
    }


    @Listen(Events.ON_CLICK + "=#btnDoFilter")
    public void btnDoFilter_OnClick(Event e)
    {  
        HashMap<String, Object> map = new HashMap<String, Object>();
  
        if(!customer_id.getValue().isEmpty()){
            map.put("pcustomer_id", new Long(customer_id.getText()) );        
        } 
        if (start_date.getValue() != null){
            map.put("pstart_date", sd.format(start_date.getValue()));
        } 
        if (end_date.getValue() != null){
            map.put("pend_date", sd.format(end_date.getValue()));
        }
              
 
        listSearchHeaderSO = imp.getListHeaderSObyFilter(map);
      
        refreshHeaderSO();
        setListboxHeaderSORenderer();

    }
    
        @Listen("onClick =#btnDoClear")
    public void btnDoClear_onClick(){
        HashMap<String, Object> map = new HashMap<String, Object>();
        customer_id.setValue("");        
        customer_code.setValue("");        
        customer_description.setValue("");
        start_date.setValue(null);        
        end_date.setValue(null);        
        listSearchHeaderSO = null;
        refreshHeaderSO();
        setListboxHeaderSORenderer();

    }
    
    
    @Listen(Events.ON_CLICK + "=#btnNewHeaderSO")
    public void btnNewHeaderSO_OnClick(Event e)
    {  
        HashMap<String, Object> map = new HashMap<String, Object>();
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
       if(listSearchHeaderSO != null){
            listboxHeaderSO.setModel(new ListModelList<HeaderSO>(listSearchHeaderSO));
       } else {
            listboxHeaderSO.setModel(new ListModelList<HeaderSO>(listHeaderSO));
       }
       
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
                new Listcell(t.getCustomer_description()).setParent(lstm);                  
                new Listcell(t.getStatus().equals(1) ? "Order Approved":"Order Accepted").setParent(lstm);                     
                new Listcell(sd.format(t.getCreation_date())).setParent(lstm);                

            }
        });
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
        w.setParent(transactions_div);
        w.doModal();}
    
}