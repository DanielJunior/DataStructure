/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.storage;

import salesmanager.models.Sale;
import salesmanager.structures.BranchAVL;
import salesmanager.structures.DateAVL;
import salesmanager.structures.List;

/**
 *
 * @author danieljr
 */
public class Storage {
    
    BranchAVL branchAVL;
    DateAVL dateAVL;
    
    public Storage() {
        branchAVL = new BranchAVL(true, BranchAVL.BRANCH_TYPE);
        dateAVL = new DateAVL(true, DateAVL.DATE_TYPE);
    }
    
    public boolean insert(Sale sale) {
        return branchAVL.insert(sale) && dateAVL.insert(sale);
    }
    
    public double getTotalSoldFromBranchs(int branch1, int branch2) {
        List resp = branchAVL.searchBranchRange(branch1, branch2);
        return resp.sum();
    }
    
    public void printBranchSold(int branchCod) {
        List branchSales = branchAVL.search(branchCod);
        branchSales.print();
    }
}
