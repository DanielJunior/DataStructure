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
public class LinkedList<E extends Comparable> implements List<E> {

    private int length;
    private Node first;

    private class Node<E extends Comparable> {

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
        first = null;
    }

    @Override
    public void add(E e) {
        if (first == null && first.content.compareTo(e) != 0) {
            Node<E> tmp = new Node(e);
            first = tmp;
            length++;
        } else if (first.content.compareTo(e) == 0) {
            return;
        } else {
            add(first, e);
        }
    }

    private void add(Node first, E e) {
        if (first.next == null && first.content.compareTo(e) != 0) {
            Node<E> tmp = new Node<>(e);
            first.next = tmp;
            length++;
            return;
        } else if (first.content.compareTo(e) == 0) {
            return;
        }
        add(first.next, e);
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

}