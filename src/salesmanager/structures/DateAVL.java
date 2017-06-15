/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.structures;

import salesmanager.models.Sale;
import salesmanager.utils.DateUtils;

/**
 *
 * @author danieljr
 */
public class DateAVL {

    public static int BRANCH_TYPE = 1;
    public static int DATE_TYPE = 2;

    private class Node {

        public long dateTime;
        public List<Sale> salesPerDate = null;
        public Node left = null;
        public Node right = null;
        public int heigthOver = 0;

        private Node(Sale info) {
            this.dateTime = info.getDate().getTime();
            salesPerDate = new LinkedList();
            salesPerDate.add(info);
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
            System.out.print("" + dateTime + "/" + over());
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
            return salesPerDate.add(info);
        }

    }

    private Node root = null;
    private boolean balanced = false;
    private int maxOver = 0;
    private final int type;

    public DateAVL(boolean balanceada, int type) {
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
        if (no.dateTime == info.getKey(type)) {
            return no.insert(info);
        } else if (no.dateTime > info.getKey(type)) {
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
        } else if (no.dateTime < info.getKey(type)) {
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
            long p = n1.dateTime;
            List pContent = n1.salesPerDate;
            long u = n2.dateTime;
            List uContent = n2.salesPerDate;
            Node t1 = n2.right;
            Node t2 = n2.left;
            Node t3 = n1.left;

            n1.dateTime = u;
            n1.salesPerDate = uContent;
            n1.right = t1;
            n1.left = n2;
            n2.dateTime = p;
            n2.salesPerDate = pContent;
            n2.right = t2;
            n2.left = t3;

            n1.heigthOver = 0;
            n2.heigthOver = 0;
        } else if (delta1 < 0) {
            Node n2 = n1.right;
            Node n3 = n2.left;
            long p = n1.dateTime;
            List pContent = n1.salesPerDate;
            long u = n2.dateTime;
            List uContent = n2.salesPerDate;
            long v = n3.dateTime;
            List vContent = n3.salesPerDate;
            Node t1 = n2.right;
            Node t2 = n3.right;
            Node t3 = n3.left;
            Node t4 = n1.left;

            n1.dateTime = v;
            n1.salesPerDate = vContent;
            n1.right = n2;
            n1.left = n3;
            n2.dateTime = u;
            n2.salesPerDate = uContent;
            n2.right = t1;
            n2.left = t2;
            n3.dateTime = p;
            n3.salesPerDate = pContent;
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
            long p = n1.dateTime;
            List pContent = n1.salesPerDate;
            long z = n2.dateTime;
            List zContent = n2.salesPerDate;
            Node t1 = n1.right;
            Node t2 = n2.right;
            Node t3 = n2.left;

            n1.dateTime = z;
            n1.salesPerDate = zContent;
            n1.right = n2;
            n1.left = t3;
            n2.dateTime = p;
            n2.salesPerDate = pContent;
            n2.right = t1;
            n2.left = t2;

            n1.heigthOver = 0;
            n2.heigthOver = 0;
        } else if (delta > 0) {
            Node n2 = n1.left;
            Node n3 = n2.right;
            long p = n1.dateTime;
            List pContent = n1.salesPerDate;
            long z = n2.dateTime;
            List zContent = n2.salesPerDate;
            long y = n3.dateTime;
            List yContent = n3.salesPerDate;
            Node t1 = n1.right;
            Node t2 = n3.right;
            Node t3 = n3.left;
            Node t4 = n2.left;

            n1.dateTime = y;
            n1.salesPerDate = yContent;
            n1.right = n2;
            n1.left = n3;
            n2.dateTime = p;
            n2.salesPerDate = pContent;
            n2.right = t1;
            n2.left = t2;
            n3.dateTime = z;
            n3.salesPerDate = zContent;
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
        if (no.dateTime == chave) {
            return no.salesPerDate;
        } else if (no.dateTime > chave && no.right != null) {
            return search(no.right, chave);
        } else if (no.dateTime < chave && no.right != null) {
            return search(no.left, chave);
        } else {
            return null;
        }
    }

    public List searchDateRange(String date1, String date2) {
        if (root != null) {
            return searchDateRange(root, date1, date2);
        } else {
            return null;
        }
    }

    private List searchDateRange(Node node, String date1, String date2) {
        if (node.dateTime >= DateUtils.getTime(date1) && node.dateTime <= DateUtils.getTime(date2)) {
            LinkedList list = (LinkedList) node.salesPerDate;
            if (node.right != null) {
                list.merge(searchDateRange(node.right, date1, date2));
            }
            if (node.left != null) {
                list.merge(searchDateRange(node.left, date1, date2));
            }
            return list;
        } else if (node.dateTime > DateUtils.getTime(date2) && node.right != null) {
            return searchDateRange(node.right, date1, date2);
        } else if (node.dateTime < DateUtils.getTime(date1) && node.left != null) {
            return searchDateRange(node.left, date1, date2);
        } else {
            return null;
        }
    }

}
