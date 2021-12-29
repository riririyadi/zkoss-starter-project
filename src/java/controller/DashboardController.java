/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import implement.DemoImplement;
import java.util.List;
import model.MostPopularItem;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;

/**
 *
 * @author RIRI
 */
public class DashboardController extends SelectorComposer<Component>{
    @Wire
    Div dashboard;
    @Wire
    Label   total_items, 
            total_transactions, 
            total_countries, 
            total_customers, 
            num_of_sold_units,
            item_name,
            num_of_transactions,
            num_of_countries;
    
    Integer totalItems, totalTransactions,  totalCountries, totalCustomers;
    
    DemoImplement imp = new DemoImplement();
    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        totalItems = imp.onCountTotalItems();
        total_items.setValue(totalItems.toString());
        
        totalTransactions = imp.onCountTotalHeaderSO();
        total_transactions.setValue(totalTransactions.toString());
        
        totalCountries = imp.onCountTotalCountries();
        total_countries.setValue(totalCountries.toString());
        
              
        totalCustomers = imp.onCountTotalCustomers();
        total_customers.setValue(totalCustomers.toString());
        
        List<MostPopularItem> list = imp.getListMostPopularItem();
        MostPopularItem mpi = null;
        if (list != null ) { mpi = list.get(0);}
        item_name.setValue(mpi.getItem_name());
        num_of_sold_units.setValue(mpi.getNum_of_sold_units().toString());
        num_of_transactions.setValue(mpi.getNum_of_transactions().toString());
        num_of_countries.setValue(mpi.getNum_of_countries().toString());
        
    }
    
    
    
}
