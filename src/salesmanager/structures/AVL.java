/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.structures;

import salesmanager.models.Comparable;

/**
 *
 * @author danieljr
 * @param <E>
 */
public class AVL<E extends Comparable> {

    public static int BRANCH_TYPE = 1;
    public static int DATE_TYPE = 2;

    private class Node<E extends Comparable> {

        public E info = null;
        public Node left = null;
        public Node right = null;
        public int heigthOver = 0;

        private Node(E info) {
            this.info = info;
        }

        private int heigth() {
            return Math.max(heigth(this.right), heigth(this.left)) + 1;
        }

        private int heigth(Node no) {
            if (no != null) {
                return Math.max(heigth(no.right), heigth(no.left)) + 1;
            }
            return 0;
        }

        private int over() {
            if (this.right != null && this.left != null) {
                return Math.abs(this.right.heigth() - this.left.heigth());
            } else if (this.right != null && this.left == null) {
                return Math.abs(this.right.heigth() - 0);
            } else if (this.right == null && this.left != null) {
                return Math.abs(0 - this.left.heigth());
            }
            return 0;
        }

        public void printTree() {
            if (left != null) {
                left.printTree(false, "");
            }
            printNodeValue();
            if (right != null) {
                right.printTree(true, "");
            }
        }

        private void printNodeValue() {
            System.out.print("" + info.getKey(type) + "/" + over());
            System.out.print('\n');
        }

        private void printTree(boolean isRight, String indent) {
            if (left != null) {
                left.printTree(false, indent + (isRight ? " |      " : "        "));
            }
            System.out.print(indent);
            if (isRight) {
                System.out.print(" \\");
            } else {
                System.out.print(" /");
            }
            System.out.print("----- ");
            printNodeValue();
            if (right != null) {
                right.printTree(true, indent + (isRight ? "        " : " |      "));
            }
        }

    }

    private Node root = null;
    private boolean balanced = false;
    private int maxOver = 0;
    private final int type;

    public AVL(boolean balanceada, int type) {
        this.balanced = balanceada;
        this.type = type;
    }

    public void print() {
        root.printTree();
    }

    public int heigth() {
        return heigth(root);
    }

    private int heigth(Node node) {
        if (node != null) {
            return Math.max(heigth(node.right), heigth(node.left)) + 1;
        }
        return 0;
    }

    public int maxOver() {
        maxOver = 0;
        heigth2(root);
        return maxOver;
    }

    private int heigth2(Node no) {
        if (no != null) {
            int alturaDireita = heigth2(no.right);
            int alturaEsquerda = heigth2(no.left);
            int saldoSubArvores = Math.abs(alturaDireita - alturaEsquerda);
            if (saldoSubArvores > maxOver) {
                maxOver = saldoSubArvores;
            }
            return Math.max(alturaDireita, alturaEsquerda) + 1;
        }
        return 0;
    }

    public boolean insert(E info) {
        if (root == null) {
            root = new Node(info);
            return true;
        } else {
            return insert(root, info);
        }

    }

    private boolean insert(Node no, E info) {
        if (no.info.getKey(type) == info.getKey(type)) {
            return false;
        } else if (no.info.getKey(type) > info.getKey(type)) {
            if (no.right == null) {
                no.right = new Node(info);

                no.heigthOver++;

                return true;
            } else {

                int saldoAnteriorU = no.right.heigthOver;
                int saldoAnteriorV = 0;
                if (no.right.left != null) {
                    saldoAnteriorV = no.right.left.heigthOver;
                }

                boolean inserted = insert(no.right, info);

                int saldoPosteriorU = no.right.heigthOver;
                int saldoPosteriorV = 0;
                if (no.right.left != null) {
                    saldoPosteriorV = no.right.left.heigthOver;
                }

                int delta1 = saldoPosteriorU - saldoAnteriorU;
                int delta2 = saldoPosteriorV - saldoAnteriorV;

                if (Math.abs(delta1) > 0 && saldoPosteriorU != 0) {
                    no.heigthOver++;
                    if (Math.abs(no.heigthOver) > 1 && balanced) {
                        balanceRight(no, delta1, delta2);
                    }
                }

                return inserted;
            }
        } else if (no.info.getKey(type) < info.getKey(type)) {
            if (no.left == null) {
                no.left = new Node(info);

                no.heigthOver--;

                return true;
            } else {

                int saldoAnteriorZ = no.left.heigthOver;
                int saldoAnteriorY = 0;
                if (no.left.right != null) {
                    saldoAnteriorY = no.left.right.heigthOver;
                }

                boolean inserted = insert(no.left, info);

                int saldoPosteriorZ = no.left.heigthOver;
                int saldoPosteriorY = 0;
                if (no.left.right != null) {
                    saldoPosteriorY = no.left.right.heigthOver;
                }

                int delta1 = saldoPosteriorZ - saldoAnteriorZ;
                int delta2 = saldoPosteriorY - saldoAnteriorY;

                if (Math.abs(delta1) > 0 && saldoPosteriorZ != 0) {
                    no.heigthOver--;
                    if (Math.abs(no.heigthOver) > 1 && balanced) {
                        balanceLeft(no, delta1, delta2);
                    }
                }

                return inserted;
            }
        } else {
            return false;
        }
    }

    private void balanceRight(Node n1, int delta1, int delta2) {
        if (delta1 > 0) {
            Node n2 = n1.right;
            E p = (E) n1.info;
            E u = (E) n2.info;
            Node t1 = n2.right;
            Node t2 = n2.left;
            Node t3 = n1.left;

            n1.info = u;
            n1.right = t1;
            n1.left = n2;
            n2.info = p;
            n2.right = t2;
            n2.left = t3;

            n1.heigthOver = 0;
            n2.heigthOver = 0;
        } else if (delta1 < 0) {
            Node n2 = n1.right;
            Node n3 = n2.left;
            E p = (E) n1.info;
            E u = (E) n2.info;
            E v = (E) n3.info;
            Node t1 = n2.right;
            Node t2 = n3.right;
            Node t3 = n3.left;
            Node t4 = n1.left;

            n1.info = v;
            n1.right = n2;
            n1.left = n3;
            n2.info = u;
            n2.right = t1;
            n2.left = t2;
            n3.info = p;
            n3.right = t3;
            n3.left = t4;

            n1.heigthOver = 0;
            if (t2 == null && t3 == null) {
                n2.heigthOver = 0;
                n3.heigthOver = 0;
            } else if (delta2 > 0) {
                n2.heigthOver = 0;
                n3.heigthOver = -1;
            } else {
                n2.heigthOver = 1;
                n3.heigthOver = 0;
            }
        }
    }

    private void balanceLeft(Node n1, int delta, int delta2) {
        if (delta < 0) {
            Node n2 = n1.left;
            E p = (E) n1.info;
            E z = (E) n2.info;
            Node t1 = n1.right;
            Node t2 = n2.right;
            Node t3 = n2.left;

            n1.info = z;
            n1.right = n2;
            n1.left = t3;
            n2.info = p;
            n2.right = t1;
            n2.left = t2;

            n1.heigthOver = 0;
            n2.heigthOver = 0;
        } else if (delta > 0) {
            Node n2 = n1.left;
            Node n3 = n2.right;
            E p = (E) n1.info;
            E z = (E) n2.info;
            E y = (E) n3.info;
            Node t1 = n1.right;
            Node t2 = n3.right;
            Node t3 = n3.left;
            Node t4 = n2.left;

            n1.info = y;
            n1.right = n2;
            n1.left = n3;
            n2.info = p;
            n2.right = t1;
            n2.left = t2;
            n3.info = z;
            n3.right = t3;
            n3.left = t4;

            n1.heigthOver = 0;
            if (t2 == null && t3 == null) {
                n2.heigthOver = 0;
                n3.heigthOver = 0;
            } else if (delta2 < 0) {
                n2.heigthOver = 1;
                n3.heigthOver = 0;
            } else {
                n2.heigthOver = 0;
                n3.heigthOver = -1;
            }
        }
    }

    public E search(int chave) {
        if (root != null) {
            return search(root, chave);
        } else {
            return null;
        }
    }

    private E search(Node no, int chave) {
        if (no.info.getKey(type) == chave) {
            return (E) no.info;
        } else if (no.info.getKey(type) > chave && no.right != null) {
            return search(no.right, chave);
        } else if (no.info.getKey(type) < chave && no.right != null) {
            return search(no.left, chave);
        } else {
            return null;
        }
    }

}
