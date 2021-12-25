package controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.impl.Selector;
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
public class DetailController extends SelectorComposer<Component>{
    
    @Wire
    Window detailForm;
    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);       
       
    };
    
    @Listen(Events.ON_CLICK + "=#btnClose")
    public void btnClose_OnClick(){
        detailForm.detach();
    }
    
    
    
}
