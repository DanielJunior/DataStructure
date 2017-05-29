/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.models;

import java.util.Date;

/**
 *
 * @author danieljunior
 */
public class Sale implements Comparable<Sale>{
    private int branch_cod;
    private Date date;
    private int salesman_cod;
    private double value;

    public Sale(int branch_cod, Date date, int salesman_cod, double value) {
        this.branch_cod = branch_cod;
        this.date = date;
        this.salesman_cod = salesman_cod;
        this.value = value;
    }

    public Sale() {
    }
    
    

    @Override
    public int compareTo(Sale e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getBranch_cod() {
        return branch_cod;
    }

    public void setBranch_cod(int branch_cod) {
        this.branch_cod = branch_cod;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getSalesman_cod() {
        return salesman_cod;
    }

    public void setSalesman_cod(int salesman_cod) {
        this.salesman_cod = salesman_cod;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
