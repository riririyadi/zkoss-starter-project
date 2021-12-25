/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import implement.SidebarPageConfigAjaxbasedImpl;
import model.SidebarPage;
import service.SidebarPageConfig;
import org.zkoss.zk.ui.select.SelectorComposer;

import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Include;
import  org.zkoss.zk.ui.util.GenericComposer;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.West;

/**
 *
 * @author RIRI
 */
public class SidebarAjaxbasedController extends SelectorComposer<Component>{

    @Wire
    Grid sidebar;
    
    
    @Wire
    Button collapsed;
    
    

    //wire service
    SidebarPageConfig pageConfig = new SidebarPageConfigAjaxbasedImpl();

    @Override
    public void doAfterCompose(Component comp) throws Exception{
        super.doAfterCompose(comp);

        //to initial view after view constructed.
        Rows rows = sidebar.getRows();

        for(SidebarPage page:pageConfig.getPages()){
            Row row = constructSidebarRow(page.getName(),page.getLabel(),page.getIconUri(),page.getUri());
            rows.appendChild(row);
        }
    }

    private Row constructSidebarRow(final String name,String label, String imageSrc, final String locationUri) {

        //construct component and hierarchy
        Row row = new Row();
        Image image = new Image(imageSrc);
        Label lab = new Label(label);
       
        row.appendChild(image);
        row.appendChild(lab);

        //set style attribute
        row.setSclass("sidebar-fn");
     

        //new and register listener for events
        EventListener<Event> onActionListener = new SerializableEventListener<Event>(){
            private static final long serialVersionUID = 1L;

            public void onEvent(Event event) throws Exception {
                //redirect current url to new location
                if(locationUri.startsWith("http")){
                    //open a new browser tab
                    Executions.getCurrent().sendRedirect(locationUri);
                }else{
                    //use iterable to find the first include only
                    Include comp = (Include) sidebar.getParent();
                    West west = (West) comp.getParent();
                    Component compo = (Component) west.getFellow("mainContent");
                    Component incl = (Component) compo.getFellow("mainArea");
                    Include include = (Include) incl.getFirstChild();
                     
                    include.setSrc(locationUri);

                    
            
                }
            }
        };
        row.addEventListener(Events.ON_CLICK, onActionListener);

        return row;
    }
    
    
    @Listen("onClick =#collapsed")
    public void collapsed_onClick(){
            Include comp = (Include) sidebar.getParent();
            West west = (West) comp.getParent();
            if( west.getWidth().equals("50px")){ 
                west.setWidth("200px"); 
                collapsed.setImage("/img/left-16.png");
            }else{ 
                west.setWidth("50px");
                collapsed.setImage("/img/right-16.png");
            }
          
    }

}
