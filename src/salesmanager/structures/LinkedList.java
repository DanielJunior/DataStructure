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

    int length;
    Node first;
    Node last;

    class Node<E extends salesmanager.models.Comparable<E>> {

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
        last = null;
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

    private E find(Node node, E e) {
        if (node == null) {
            return null;
        } else {
            while (node != null) {
                if (node.content.compareTo(e) == 0) {
                    return (E) node.content;
                }
                node = node.next;
            }
            return null;
        }
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
    public List merge(List list) {
        if (list != null) {
            List resp = new LinkedList();
            Node tmp = first;
            while (tmp != null) {
                resp.add(tmp.content);
                tmp = tmp.next;
            }
            tmp = list.getFirstNode();
            while (tmp != null) {
                resp.add(tmp.content);
                tmp = tmp.next;
            }
            return resp;
        } else {
            return this;
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

    @Override
    public List intersect(List another
    ) {
        List<E> resp = new LinkedList();
        Node tmp = first;
        while (tmp != null) {
            if (another.find(tmp.content) != null) {
                resp.add((E) tmp.content);
            }
            tmp = tmp.next;
        }
        return resp;
    }

    @Override
    public Node getFirstNode() {
        return first;
    }

}
