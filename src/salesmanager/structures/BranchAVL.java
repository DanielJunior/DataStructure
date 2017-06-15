/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.structures;

import salesmanager.models.Comparable;
import salesmanager.models.Sale;

/**
 *
 * @author danieljr
 */
public class BranchAVL {

    public static int BRANCH_TYPE = 1;
    public static int DATE_TYPE = 2;

    private class Node {

        public int branchCode;
        public List<Sale> branchSales = null;
        public Node left = null;
        public Node right = null;
        public int heigthOver = 0;

        private Node(Sale info) {
            this.branchCode = info.getBranchCode();
            branchSales = new LinkedList();
            branchSales.add(info);
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
            System.out.print("" + branchCode + "/" + over());
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

        private boolean insert(Sale info) {
            return branchSales.add(info);
        }

    }

    private Node root = null;
    private boolean balanced = false;
    private int maxOver = 0;
    private final int type;

    public BranchAVL(boolean balanceada, int type) {
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

    public boolean insert(Sale info) {
        if (root == null) {
            root = new Node(info);
            return true;
        } else {
            return insert(root, info);
        }

    }

    private boolean insert(Node no, Sale info) {
        if (no.branchCode == info.getKey(type)) {
            return no.insert(info);
        } else if (no.branchCode > info.getKey(type)) {
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
        } else if (no.branchCode < info.getKey(type)) {
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
            int p = n1.branchCode;
            List pContent = n1.branchSales;
            int u = n2.branchCode;
            List uContent = n2.branchSales;
            Node t1 = n2.right;
            Node t2 = n2.left;
            Node t3 = n1.left;

            n1.branchCode = u;
            n1.branchSales = uContent;
            n1.right = t1;
            n1.left = n2;
            n2.branchCode = p;
            n2.branchSales = pContent;
            n2.right = t2;
            n2.left = t3;

            n1.heigthOver = 0;
            n2.heigthOver = 0;
        } else if (delta1 < 0) {
            Node n2 = n1.right;
            Node n3 = n2.left;
            int p = n1.branchCode;
            List pContent = n1.branchSales;
            int u = n2.branchCode;
            List uContent = n2.branchSales;
            int v = n3.branchCode;
            List vContent = n3.branchSales;
            Node t1 = n2.right;
            Node t2 = n3.right;
            Node t3 = n3.left;
            Node t4 = n1.left;

            n1.branchCode = v;
            n1.branchSales = vContent;
            n1.right = n2;
            n1.left = n3;
            n2.branchCode = u;
            n2.branchSales = uContent;
            n2.right = t1;
            n2.left = t2;
            n3.branchCode = p;
            n3.branchSales = pContent;
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
            int p = n1.branchCode;
            List pContent = n1.branchSales;
            int z = n2.branchCode;
            List zContent = n2.branchSales;
            Node t1 = n1.right;
            Node t2 = n2.right;
            Node t3 = n2.left;

            n1.branchCode = z;
            n1.branchSales = zContent;
            n1.right = n2;
            n1.left = t3;
            n2.branchCode = p;
            n2.branchSales = pContent;
            n2.right = t1;
            n2.left = t2;

            n1.heigthOver = 0;
            n2.heigthOver = 0;
        } else if (delta > 0) {
            Node n2 = n1.left;
            Node n3 = n2.right;
            int p = n1.branchCode;
            List pContent = n1.branchSales;
            int z = n2.branchCode;
            List zContent = n2.branchSales;
            int y = n3.branchCode;
            List yContent = n3.branchSales;
            Node t1 = n1.right;
            Node t2 = n3.right;
            Node t3 = n3.left;
            Node t4 = n2.left;

            n1.branchCode = y;
            n1.branchSales = yContent;
            n1.right = n2;
            n1.left = n3;
            n2.branchCode = p;
            n2.branchSales = pContent;
            n2.right = t1;
            n2.left = t2;
            n3.branchCode = z;
            n3.branchSales = zContent;
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

    public List search(int chave) {
        if (root != null) {
            return search(root, chave);
        } else {
            return null;
        }
    }

    private List search(Node no, int chave) {
        if (no.branchCode == chave) {
            return no.branchSales;
        } else if (no.branchCode > chave && no.right != null) {
            return search(no.right, chave);
        } else if (no.branchCode < chave && no.left != null) {
            return search(no.left, chave);
        } else {
            return null;
        }
    }

    public List searchBranchRange(int begin, int end) {
        if (root != null) {
            return searchBranchRange(root, begin, end);
        } else {
            return null;
        }
    }

    private List searchBranchRange(Node node, int begin, int end) {
        if (node.branchCode >= begin && node.branchCode <= end) {
            LinkedList list = (LinkedList) node.branchSales;
            if (node.right != null) {
                list = (LinkedList) list.merge(searchBranchRange(node.right, begin, end));
            }
            if (node.left != null) {
                list = (LinkedList) list.merge(searchBranchRange(node.left, begin, end));
            }
            return list;
        } else if (node.branchCode > end && node.right != null) {
            return searchBranchRange(node.right, begin, end);
        } else if (node.branchCode < begin && node.left != null) {
            return searchBranchRange(node.left, begin, end);
        } else {
            return null;
        }
    }

}
