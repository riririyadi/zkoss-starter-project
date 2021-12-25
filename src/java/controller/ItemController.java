/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import implement.DemoImplement;
import model.Item;
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

/**
 *
 * @author GP-ERWAN
 */
public class ItemController extends SelectorComposer<Component>{
    
    private ListItemsController listItemsController;

    public ListItemsController getListItemsController() {
        return listItemsController;
    }

    public void setListItemsController(ListItemsController listItemsController) {
        this.listItemsController = listItemsController;
    }
   
    @Wire
    Window entry_item;

    @Wire
    Textbox code, description, uom, price;
	
	
    DemoImplement imp = new DemoImplement();
   
    Map args;
    
  
    
    @Override
    public void doAfterCompose(Component comp) throws Exception 
    {
        super.doAfterCompose(comp);        
        args = Executions.getCurrent().getArg();
        
        
        if (args.containsKey("ModuleMainController")) {
		setListItemsController((ListItemsController) args.get("ModuleMainController"));
		// Set THIS controller BACK TO THE MainController 
		getListItemsController().setItemController(this);
                       
	}

        if (!args.containsKey("pid")) 
        {
        	 entry_item.setTitle("Add Item");               
        }
        else 
        {
                entry_item.setTitle("Edit Item");
        	code.setValue((String) args.get("pcode"));
        	description.setValue((String) args.get("pdescription"));        	
                uom.setValue((String) args.get("puom"));                
                price.setValue(args.get("pprice").toString());
        }
    }
    
    @Listen(Events.ON_CLICK + "=#btnSave")
    public void btnSave_OnClick()
    {
       
        HashMap<String, Object> map = new HashMap<String, Object>();
   
        
        if (!args.containsKey("pid"))
        {
            map.put("pcode",        code.getValue().isEmpty() ? null : code.getText());
            map.put("pdescription", description.getValue().isEmpty()? null : description.getText());
            map.put("puom", uom.getValue().isEmpty()? null : uom.getText());            
            map.put("pprice", price.getValue().isEmpty()? null : new Integer(price.getText()));

            
            showLog(map, "countries_oninsert sebelum");
            
            imp.items_oninsert(map);
           
            showLog(map, "countries_oninsert sesudah");
            
            Messagebox.show(map.get("outmsg").toString());
        }
        else
        {

            map.put("pid",  args.get("pid"));
            map.put("pcode", code.getValue().isEmpty() ? null : code.getText());
            map.put("pdescription", description.getValue().isEmpty()? null : description.getText());
            map.put("puom", uom.getValue().isEmpty()? null : uom.getText());            
            map.put("pprice", price.getValue().isEmpty()? null : Integer.parseInt(price.getText()));
      
  
            showLog(map, "sebelum");
            imp.items_onupdate(map);
      
            showLog(map, "sesudah");
            
            Messagebox.show(map.get("outmsg").toString());
           
        }
        
      entry_item.detach();
      getListItemsController().showListItem();
            
    }
    
    @Listen(Events.ON_CLICK + "=#btnClose")
    public void btnClose_OnClick()
    {
    	entry_item.detach();
    }
    
    public static void showLog(Map<String, Object> map, String title) {
        String process = "**Process : ";
        int lengthDash = process.length() + title.length() + 2;

        System.out.println(process + title + "**");
   
        String format = "%-10s%s%n";
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            System.out.printf(format, key, " := " + value);
        }
     
    }
    
}
