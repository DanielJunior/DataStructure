/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.models;

import java.util.Date;
import salesmanager.structures.BranchAVL;

/**
 *
 * @author danieljunior
 */
public class Sale implements Comparable<Sale> {

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
        if (this.branchCode == e.getBranchCode() && this.salesmanCode == e.getSalesmanCode()
                && this.value == e.getValue() && this.date.equals(e.getDate())) {
            return 0;
        }
        return 1;
    }

    public void setBranchCode(int branchCode) {
        this.branchCode = branchCode;
    }

    public int getBranchCode() {
        return branchCode;
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
        if (type == BranchAVL.BRANCH_TYPE) {
            return branchCode;
        } else {
            return date.getTime();
        }
    }

    @Override
    public String toString() {
        return "Sale{" + "branchCode=" + branchCode + ", date=" + date + ", salesmanCode=" + salesmanCode + ", value=" + value + '}';
    }
    
    

}
