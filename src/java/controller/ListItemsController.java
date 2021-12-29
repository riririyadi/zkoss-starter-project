/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import implement.DemoImplement;
import java.util.HashMap;
import java.util.List;
import model.Item;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.impl.ParseException;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

/**
 *
 * @author RIRI
 */
public class ListItemsController extends SelectorComposer<Component> {

    public ItemController getItemController() {
        return itemController;
    }

    public void setItemController(ItemController itemController) {
        this.itemController = itemController;
    }
    
    private ItemController itemController;
    
    @Wire
    Listbox listboxItems;
    
    @Wire
    Textbox searchItem;
    
    @Wire
    Paging userPaging;
              
    List<Item> listItem, listSearchItem;
    
    DemoImplement imp = new DemoImplement();
    Integer totalRecord = 0;    
    Integer pageSize = 10;
    Integer currentPage = 1;
    
    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        showListItem();
    }

    @Listen(Events.ON_CLICK + "=#btnEditItem")
    public void btnEditItem_onUpdate(Item t)
    {
     
  	HashMap<String, Object> map = new HashMap<String, Object>();
    	map.put("pid", t.getId());    	
        map.put("pcode", t.getCode());       
        map.put("pdescription", t.getDescription());    
        map.put("puom", t.getUom());        
        map.put("pprice", t.getPrice());
        map.put("ModuleMainController", this);
    	final Window window = (Window) Executions.createComponents("/components/entry_item.zul", null, map);
        window.doHighlighted();
        window.setPosition("center");
      
    }
    
    
    @Listen(Events.ON_CLICK + "=#btnRefreshItem")
    public void btnRefreshItem_OnClick()
    {
       showListItem();
    }
    
    @Listen(Events.ON_CLICK + "=#btnFindItem")
    public void btnFindItem_OnClick(){

        if (!searchItem.getValue().isEmpty()){
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("psearch", searchItem.getText());
            listSearchItem = imp.findListItem(map);
            refreshHeaderItem();
            setListboxItemsRenderer();
        } 
   
    }
    
      @Listen(Events.ON_CLICK + "=#btnNewItem")
    public void btnNewItem_OnClick(Event e)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("ModuleMainController", this);
        final Window window = (Window) Executions.createComponents("/components/entry_item.zul", null, map);
        window.doHighlighted();
        window.setPosition("center");
    }
    
    
       @Listen(Events.ON_OK + "= #searchItem")
   public void searchItemListener(){
     HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("psearch", searchItem.getValue());
        totalRecord = imp.onCountItems(map);
        System.out.println("total record " + totalRecord.toString());
        userPaging.setTotalSize(totalRecord);
//        pageSize = listboxCountries.getPageSize();
        userPaging.setPageSize(10);
        showListItemonDemand(searchItem.getValue());
        
   }
   
      @Listen("onPaging=#userPaging")
    public void onPagingUserPaging(PagingEvent pe) throws ParseException{
      currentPage = pe.getActivePage() + 1;
      showListItemonDemand(searchItem.getValue());
    }
    
   
     public void showListItemonDemand(String psearch) 
    {
        HashMap<String, Object> mapPaging = new HashMap<String, Object>();
        mapPaging.put("pageSize", pageSize);        
        mapPaging.put("pageNumber", currentPage);
        mapPaging.put("psearch", psearch);
        listSearchItem = imp.findListItem(mapPaging);
   
        refreshHeaderItem();
        setListboxItemsRenderer();

    }
    
    
    
//   @Listen(Events.ON_OK + "= #searchItem")
//   public void searchListener(){
//       if(searchItem.getValue().isEmpty()){
//           listSearchItem = null;
//       } 
//       else {
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("psearch", searchItem.getText());
//            listSearchItem = imp.findListItem(map);
//       }
//        refreshHeaderItem();
//        setListboxItemsRenderer();
//   }
   
    @Listen(Events.ON_CLICK + "= #clearSearch")
    public void clearSearch(){
        searchItem.setValue("");
        listSearchItem = null;
     
        refreshHeaderItem();
        setListboxItemsRenderer();
   }
    
      public void showListItem() 
    {
        listItem = imp.getListItem();
        
        refreshHeaderItem();
        setListboxItemsRenderer();
    }
    
    
     public void refreshHeaderItem() 
    {
        if(listSearchItem != null ){
            listboxItems.setModel(new ListModelList<Item>(listSearchItem));
        }else{
            listboxItems.setModel(new ListModelList<Item>(listItem));
        }
    }
    
       public void setListboxItemsRenderer() 
    {
        listboxItems.setItemRenderer(new ListitemRenderer<Item>() {
            @Override
            public void render(Listitem lstm, final Item t, int i) throws Exception 
            {
                 final Button button = new Button();
                button.setImage("/img/Delete24.png");
                button.addEventListener("onClick", new EventListener() {
                    public void onEvent(Event e) throws Exception {
                        
                        Messagebox.show("Hapus Company "+ t.getDescription()+" ?", 
                        "Question", Messagebox.YES | Messagebox.CANCEL,
                        Messagebox.QUESTION,
                        new org.zkoss.zk.ui.event.EventListener(){
	 		public void onEvent(Event e){
                            HashMap<String, Object> map = new HashMap<String, Object>();
                            map.put("pid", t.getId());
	 			if(Messagebox.ON_YES.equals(e.getName())){
                                            imp.companies_ondelete(map);
                                            showListItem();
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
                        btnEditItem_onUpdate(t);
                    }
                });
                Listcell editCell = new Listcell();
                editCell.appendChild(editBtn);
                lstm.appendChild(editCell);
                
               
                new Listcell(t.getCode()).setParent(lstm);
                new Listcell(t.getDescription()).setParent(lstm);                 
                new Listcell(t.getUom()).setParent(lstm);                  
                new Listcell(t.getPrice().toString()).setParent(lstm);                     
                  
         
               
            }

        });
    }
    
}
