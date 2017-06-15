/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.structures;

/**
 *
 * @author danieljunior
 * @param <E>
 */
public interface List<E> {
    public boolean add(E e);
    public E remove(E e);
    public E find(E e);
    public boolean isEmpty();
    public int length();
    public void print();
    public void merge(List list);
    public double sum();
}
