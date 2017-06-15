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
public class LinkedList<E extends salesmanager.models.Comparable<E>> implements List<E> {

    private int length;
    private Node first;
    private Node last;

    private class Node<E extends salesmanager.models.Comparable<E>> {

        E content;
        Node next;

        Node(E e) {
            this.content = e;
            next = null;
        }

        Node() {
        }
    }

    public LinkedList() {
        this.length = 0;
        first = last = null;
    }

    @Override
    public boolean add(E e) {
        if (first == null) {
            Node<E> tmp = new Node(e);
            first = tmp;
            last = tmp;
            length++;
            return true;
        } else if (first.content.compareTo(e) == 0) {
            return false;
        } else {
            return add(first, e);
        }
    }

    private boolean add(Node first, E e) {
        if (first.next == null && first.content.compareTo(e) != 0) {
            Node<E> tmp = new Node<>(e);
            first.next = tmp;
            last = tmp;
            length++;
            return true;
        } else if (first.content.compareTo(e) == 0) {
            return false;
        }
        return add(first.next, e);
    }

    @Override
    public E remove(E e) {
        if (first == null) {
            return null;
        } else if (first.content.compareTo(e) == 0) {
            E tmp = (E) first.content;
            first = first.next;
            length--;
            return tmp;
        }
        return remove(first, e);
    }

    private E remove(Node first, E e) {
        if (first == null) {
            return null;
        } else if (first.next != null && first.next.content.compareTo(e) == 0) {
            E tmp = (E) first.next.content;
            first.next = first.next.next;
            length--;
            return tmp;
        }
        return remove(first.next, e);
    }

    @Override
    public E find(E e) {
        if (first == null) {
            return null;
        } else if (first.content.compareTo(e) == 0) {
            return (E) first.content;
        }
        return find(first.next, e);
    }

    private E find(Node first, E e) {
        if (first == null) {
            return null;
        } else if (first.next != null && first.next.content.compareTo(e) == 0) {
            E tmp = (E) first.next.content;
            first.next = first.next.next;
            return tmp;
        }
        return remove(first.next, e);
    }

    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public void print() {
        Node tmp = first;
        while (tmp != null) {
            System.out.println(tmp.content.toString());
            tmp = tmp.next;
        }
    }

    @Override
    public void merge(List list) {
        if (list != null) {
            LinkedList another = (LinkedList) list;
            last.next = another.first;
            last = last.next;
        }
    }

    @Override
    public double sum() {
        double resp = 0;
        Node tmp = first;
        while (tmp != null) {
            resp += tmp.content.getValue();
            tmp = tmp.next;
        }
        return resp;
    }
}
