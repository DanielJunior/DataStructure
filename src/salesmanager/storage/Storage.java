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

    public double getTotalSoldFromBranchs(int branchCode1, int branchCode2) {
        List resp = branchAVL.searchBranchRange(branchCode1, branchCode2);
//        resp.print();
        return resp != null ? resp.sum() : 0;
    }

    public double getTotalSoldFromSeason(String date1, String date2) {
        List resp = dateAVL.searchDateRange(date1, date2);
//        resp.print();
        return resp != null ? resp.sum() : 0;
    }

    public double getTotalSoldFromBranchsAndSeason(int branchCode1, int branchCode2, String date1, String date2) {
        List branchSales = branchAVL.searchBranchRange(branchCode1, branchCode2);
        List datesSales = dateAVL.searchDateRange(date1, date2);
        List intersect = branchSales.intersect(datesSales);
//        intersect.print();
        return intersect != null ? intersect.sum() : 0;
    }

    public void printBranchSold(int branchCod) {
        List branchSales = branchAVL.search(branchCod);
        branchSales.print();
    }
}
