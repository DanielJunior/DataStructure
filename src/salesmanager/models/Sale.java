/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.models;

import java.util.Date;
import salesmanager.structures.AVL;

/**
 *
 * @author danieljunior
 */
public class Sale implements Comparable<Sale>{
    private int branchCode;
    private Date date;
    private int salesmanCode;
    private double value;

    public Sale(int branch_cod, Date date, int salesman_cod, double value) {
        this.branchCode = branch_cod;
        this.date = date;
        this.salesmanCode = salesman_cod;
        this.value = value;
    }

    public Sale() {
    }
    
    

    @Override
    public int compareTo(Sale e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setBranchCode(int branch_cod) {
        this.branchCode = branch_cod;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getSalesmanCode() {
        return salesmanCode;
    }

    public void setSalesmanCode(int salesmanCode) {
        this.salesmanCode = salesmanCode;
    }

    @Override
    public long getKey(int type) {
        if(type == AVL.BRANCH_TYPE){
            return branchCode;
        }else{
            return date.getTime();
        }
    }

}
