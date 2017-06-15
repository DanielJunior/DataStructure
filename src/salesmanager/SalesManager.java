/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager;

import salesmanager.models.Sale;
import salesmanager.storage.Storage;
import salesmanager.utils.DateUtils;

/**
 *
 * @author danieljunior
 */
public class SalesManager {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Storage storage = new Storage();
        for (int i = 0; i < 100; i++) {
            Sale s = new Sale();
            s.setBranchCode((int) (Math.random() * 3));
            s.setDate(DateUtils.getRandomDate("01/16", "12/16"));
            s.setSalesmanCode((int) (Math.random() * 3));
            s.setValue(Math.random() * 100);
            storage.insert(s);
        }
        double branchsSold = storage.getTotalSoldFromBranchs(0, 1);
        double seasonSold = storage.getTotalSoldFromSeason("10/16", "12/16");
        double intersect = storage.getTotalSoldFromBranchsAndSeason(0, 1, "10/16", "12/16");

        System.out.println("Sold from branchs 0,1: " + branchsSold);
        System.out.println("Sold from date 10/16 - 12/16: " + seasonSold);
        System.out.println("Sold from branchs 0,1 in 10/16 - 12/16: " + intersect);
        
        System.out.println("*************************");
        branchsSold = storage.getTotalSoldFromBranchs(0, 1);
        seasonSold = storage.getTotalSoldFromSeason("09/16", "12/16");
        intersect = storage.getTotalSoldFromBranchsAndSeason(0, 1, "09/16", "12/16");

        System.out.println("Sold from branchs 0,2: " + branchsSold);
        System.out.println("Sold from date 09/16 - 12/16: " + seasonSold);
        System.out.println("Sold from branchs 0,1 in 09/16 - 12/16: " + intersect);
        
    }

}
