/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import id.berkah.admin.controller.LovController;
import implement.DemoImplement;
import java.util.HashMap;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author RIRI
 */
public class DetailSOController extends SelectorComposer<Component> {
    
       
    private MasterSOController masterSOController;

    public MasterSOController getMasterSOController() {
        return masterSOController;
    }

    public void setMasterSOController(MasterSOController masterSOController) {
        this.masterSOController = masterSOController;
    }
    
    @Wire
    Window winDetailSalesOrder;
    
    @Wire
    Textbox so_id, so_number;
    
    @Wire
    Textbox item_id, item_code, item_description, item_price;
    
    @Wire
    Textbox quantity, price, price_untouched, tax_amount, line;
    
    @Wire
    Spinner discount;
    
    DemoImplement imp = new DemoImplement();
    
      Map args;
    
     @Override
    public void doAfterCompose(Component comp) throws Exception 
    {
        super.doAfterCompose(comp);        
        args = Executions.getCurrent().getArg();
        
        if (args.containsKey("ModuleMainController")) {
		setMasterSOController((MasterSOController) args.get("ModuleMainController"));
		// Set THIS controller BACK TO THE MainController 
		getMasterSOController().setDetailSOController(this);
                       
	}

        
        if (!args.containsKey("pid")){
       
        	so_number.setValue((String) args.get("pso_number"));
        	so_id.setValue(args.get("pso_id").toString());   	
                            
        }
        else 
        {

            so_id.setValue(args.get("pso_id").toString());
            so_number.setValue((String) args.get("pso_number"));
            item_id.setValue(args.get("pitem_id").toString());
            item_code.setValue((String)args.get("pitem_code"));
            item_description.setValue((String)args.get("pitem_description"));
            item_price.setValue(args.get("pitem_price").toString());
            line.setValue(args.get("pline").toString());
            quantity.setValue(args.get("pquantity").toString());
            price_untouched.setValue(args.get("pprice").toString());            
            price.setValue(args.get("pprice").toString());
            discount.setValue((Integer) args.get("pdiscount"));
            tax_amount.setValue(args.get("ptax_amount").toString());
        }
    }
    
   
    @Listen(Events.ON_CLICK + "=#btnSave")
    public void btnSave_onClick(){
        
        if(!item_id.getValue().isEmpty()){
        
            HashMap<String, Object> map = new HashMap<String, Object>();
               
        if (!args.containsKey("pid")){    
            map.put("pso_id", so_id.getValue().isEmpty() ? null : new Long(so_id.getText()));            
            map.put("pline", line.getValue().isEmpty() ? null : new Integer(line.getText()));
            map.put("pitem_id", item_id.getValue().isEmpty() ? null : new Long(item_id.getText()));
            map.put("pquantity",  quantity.getValue().isEmpty() ? null : new Integer(quantity.getText()));
            map.put("pprice",  price.getValue().isEmpty() ? null : new Long(price.getText()));
            map.put("pdiscount",  discount.getValue());            
            map.put("ptax_amount",  tax_amount.getValue().isEmpty() ? null : new Integer(tax_amount.getText()));
                                
            imp.detail_so_oninsert(map);
            Messagebox.show(map.get("outmsg").toString());
        } else{
            map.put("pid", args.get("pid"));                 
            map.put("pso_id", so_id.getValue().isEmpty() ? null : new Long(so_id.getText()));            
            map.put("pline", line.getValue().isEmpty() ? null : new Integer(line.getText()));
            map.put("pitem_id", item_id.getValue().isEmpty() ? null : new Long(item_id.getText()));
            map.put("pquantity",  quantity.getValue().isEmpty() ? null : new Integer(quantity.getText()));
            map.put("pprice",  price.getValue().isEmpty() ? null : new Long(price.getText()));
            map.put("pdiscount",  discount.getValue());            
            map.put("ptax_amount",  tax_amount.getValue().isEmpty() ? null : new Integer(tax_amount.getText()));
            imp.detail_so_onupdate(map);
            Messagebox.show(map.get("outmsg").toString());
        }
            winDetailSalesOrder.detach();
            getMasterSOController().showListDetailSO();} else {
        Messagebox.show("Input Item First, Please");
        }
                        
    }
    
 
    @Listen(Events.ON_CLICK + "=#btnClose")
    public void btnClose_onClick(){
        winDetailSalesOrder.detach();
    }
        
       
    @Listen("onChange = #quantity")
    public void onChange(){
        Integer itemQuantity = new Integer(quantity.getValue().isEmpty() ? "0" : quantity.getValue());
        Integer itemPrice = new Integer(item_price.getValue().isEmpty() ? "0" : item_price.getValue());
        Long hasil = (long) itemQuantity * itemPrice;
        price.setValue(hasil.toString());
        price_untouched.setValue(hasil.toString());
        Double tax = 0.1 * hasil;
        String plain = String.format("%.0f", tax);
        tax_amount.setValue(plain);
    }
    
    
        
    @Listen("onChange = #discount")
    public void onDiscountChange(){
        
        Double discountNumber = new Double(discount.getValue());
        Double totalPrice_untouched = new Double(price_untouched.getValue().isEmpty() ? "0" : price_untouched.getText());        
        Double totalPrice = new Double(price.getValue().isEmpty() ? "0" : price.getText());
        Double itemQuantity = new Double(quantity.getValue().isEmpty() ? "0" : quantity.getText());
        Double itemPrice = new Double(item_price.getValue().isEmpty() ? "0" : item_price.getText());
        Double hasil = null;
        if (discountNumber != 0) {
            hasil = (Double)  totalPrice_untouched - (totalPrice_untouched * (discountNumber / 100));
        } else {
            hasil = (Double) itemQuantity * itemPrice;
        }
         String priceStr = String.format("%.0f", hasil);
        price.setValue(priceStr);
        Double tax = 0.1 * hasil;
        String plain = String.format("%.0f", tax);
        tax_amount.setValue(plain);
    }
    
    
    
    
    @Listen(Events.ON_CLICK + "=#btnLovHeaderSO")
    public void showLovHeaderSO()
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        
        String query = "select id, so_number "
                      + "from header_so";
//                      + "where province_id = " + province_id.getValue();
//                      + " And upper(province_code) like upper('%?%') or upper(province_description) like upper('%?%') "
//                      + "order by province_code"
//                      + "Limit param1 offset param2";
        
        composerLov.setQuery(query);
        composerLov.setQueryTotal("select count(*) from items");
//                + "And upper(province_code) like upper('%?%') or upper(province_description) like upper('%?%')");
        composerLov.setSelectedColumn(new int[]{
            0, 1
        });
        composerLov.setColumnWidth(new String[]{
            "50px", "300px"
        });

        composerLov.setComponentTransferData(new Textbox[]{
           so_id, so_number
        });
        composerLov.setHiddenColumn(new int[]{
            0
        });

//        composerLov.setEventListener((EventListener) (Event t) -> {
//        });
        composerLov.setTitle("List of Header Sales Order");
        composerLov.setWidth("500px");
        composerLov.setHeight("350px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);
        Window w = (Window) Executions.createComponents("/components/lov.zul", null, map);
        w.setParent(winDetailSalesOrder);
        w.doModal();
    }
    
    
    @Listen(Events.ON_CLICK + "=#btnLovItem")
    public void showLovItem()
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        
        String query = "select id, code, description, price "
                      + "from items";
//                      + "where province_id = " + province_id.getValue();
//                      + " And upper(province_code) like upper('%?%') or upper(province_description) like upper('%?%') "
//                      + "order by province_code"
//                      + "Limit param1 offset param2";
        
        composerLov.setQuery(query);
        composerLov.setQueryTotal("select count(*) from items");
//                + "And upper(province_code) like upper('%?%') or upper(province_description) like upper('%?%')");
        composerLov.setSelectedColumn(new int[]{
            0, 1, 2, 3
        });
        composerLov.setColumnWidth(new String[]{
            "50px", "300px"
        });

        composerLov.setComponentTransferData(new Textbox[]{
            item_id, item_code, item_description, item_price
        });
        composerLov.setHiddenColumn(new int[]{
            0
        });

//        composerLov.setEventListener((EventListener) (Event t) -> {
//        });
        composerLov.setTitle("List of Items");
        composerLov.setWidth("500px");
        composerLov.setHeight("350px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);
        Window w = (Window) Executions.createComponents("/components/lov.zul", null, map);
        w.setParent(winDetailSalesOrder);
        w.doModal();
    }
    
    
}
