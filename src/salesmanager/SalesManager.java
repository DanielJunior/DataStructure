/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import salesmanager.models.Sale;
import salesmanager.structures.BranchAVL;
import salesmanager.structures.List;

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
        BranchAVL tree = new BranchAVL(true, BranchAVL.BRANCH_TYPE);
        for (int i = 0; i < 20; i++) {
            Sale s = new Sale();
            s.setBranchCode((int) (Math.random() * 3));
            Date d1 = new Date(20, 0, 1);
            Date d2 = new Date(27, 0, 31);
            long random = ThreadLocalRandom.current().nextLong(d1.getTime(), d2.getTime());
            Date date = new Date(random);
            s.setDate(date);
            s.setSalesmanCode((int) (Math.random() * 3));
            s.setValue(Math.random() * 100);
            tree.insert(s);
        }    
        tree.print();
        List branch0 = tree.search(0);
        branch0.print();
    }

}
