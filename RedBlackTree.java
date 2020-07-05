public class RedBlackTree {
    private static final int RED = 0;
    private static final int BLACK = 1;
    
    public class Node{
        public int key;
        public int color;
        public Node p;       // parent
        public Node left;    // left_child
        public Node right;   // right_child

        public Node(int t){
            this.key = t;
            this.p = nil;
            this.left = nil;
            this.right = nil;
            this.color = RED; // Default color is red
        }

    }

    private final Node nil = new Node(-1);
    private Node root;
    
    public RedBlackTree(int t){
        this.nil.color = BLACK;
        this.root = new Node(t);
        this.root.color = BLACK;
    }

    private void left_rotate(Node x){
        Node y = x.right;
        x.right = y.left;
        if(y.left != this.nil){
            y.left.p = x;
        }
        y.p = x.p;
        if(x.p == this.nil){
            this.root = y;
        }else if(x == x.p.left){
            x.p.left = y;
        }else {
            x.p.right = y;
        }
        y.left = x;
        x.p = y;
    }

    private void right_rotate(Node y){
        Node x = y.left;
        y.left = x.right;
        if(x.right != this.nil){
            x.right.p = y;
        }
        x.p = y.p;
        if(y.p == this.nil){
            this.root = x;
        }else if(y == y.p.left){
            y.p.left = x;
        }else{
            y.p.right = x;
        }
        x.right = y;
        y.p = x;
    }

    public void insert(int t){
        Node z = new Node(t);
        Node y = this.nil;
        Node x = this.root;
        while(x != this.nil){
            y = x;
            if(z.key < x.key){
                x = x.left;
            }else{
                x = x.right;
            }
        }
        z.p = y;
        if(y == this.nil){
            this.root = z;
        }else if(z.key < y.key){
            y.left = z;
        }else{
            y.right = z;
        }
        insert_fixup(z);
    }

    private void insert_fixup(Node z){
        while (z.p.color == RED){
            if(z.p == z.p.p.left){
                Node y = z.p.p.right;
                if(y.color == RED){
                    z.p.color = BLACK;
                    y.color = BLACK;
                    z.p.p.color = RED;
                    z = z.p.p;
                    continue;
                } else if (z == z.p.right){
                    z = z.p;
                    this.left_rotate(z);
                }
                z.p.color = BLACK;
                z.p.p.color = RED;
                this.right_rotate(z.p.p);
            }else{
                Node y = z.p.p.left;
                
                if(y.color == RED){
                    z.p.color = BLACK;
                    y.color = BLACK;
                    z.p.p.color = RED;
                    z = z.p.p;
                    continue;
                }
                else if (z == z.p.left){
                    z = z.p;
                    this.right_rotate(z);
                }
                z.p.color = BLACK;
                z.p.p.color = RED;
                this.left_rotate(z.p.p);
            }
        }
        this.root.color = BLACK;
    }

    public void print(){
        this.printHelper(this.root);
    }

    public void printHelper(Node node){
        if(node != this.nil){
            printHelper(node.left);
            System.out.println(node.key);
            printHelper(node.right);
        }
    }

    private void transplant(Node u, Node v){
        if(u.p == this.nil){
            this.root = v;
        }else if(u == u.p.left){
            u.p.left = v;
        }else{
            u.p.right = v;
        }
        v.p = u.p;
    }

    public void delete(int k){
        Node z = this.search(k);
        if(z == this.nil){
            System.out.println("No value found");
        }
        Node y = z;
        Node x = null;
        int y_original_color = z.color;
        if(z.left == this.nil){
            x = z.right;
            this.transplant(z, z.right);
        }else if(z.right == this.nil){
            x = z.left;
            this.transplant(z, z.left);
        }else{
            y = this.minimum(z.right);
            y_original_color = y.color;
            x = y.right;
            if (y.p == z){
                x.p = y;
            } else {
              this.transplant(y,y.right);
              y.right = z.right;
              y.right.p = y;
            }
            this.transplant(z, y);
            y.left = z.left;
            y.left.p = y;
            y.color = z.color;
        }
        if (y_original_color == BLACK){
            this.delete_fixup(x);
        }
    }

    private void delete_fixup(Node x){
        while(x != this.root && x.color == BLACK){
            if (x == x.p.left){
                Node w = x.p.right;
                if (w.color == RED){
                    w.color = BLACK;
                    x.p.color = RED;
                    this.left_rotate(x.p);
                    w = x.p.right;
                }
                if (w.left.color == BLACK && w.right.color == BLACK){
                    w.color = RED;
                    x = x.p;
                    continue;
                }
                else if (w.right.color == BLACK){
                    w.left.color = BLACK;
                    w.color = RED;
                    right_rotate(w);
                    w = x.p.right;
                }
                if(w.right.color == RED) {
                    w.color = x.p.color;
                    x.p.color = BLACK;
                    w.right.color = BLACK;
                    left_rotate(x.p);
                    x = this.root;
                }
            } else {
                Node w = x.p.left;
                if (w.color == RED){
                    w.color = BLACK;
                    x.p.color = RED;
                    this.right_rotate(x.p);
                    w = x.p.left;
                }
                if (w.right.color == BLACK && w.left.color == BLACK){
                    w.color = RED;
                    x = x.p;
                    continue;
                }
                else if (w.left.color == BLACK){
                    w.right.color = BLACK;
                    w.color = RED;
                    left_rotate(w);
                    w = x.p.left;
                }
                if (w.left.color == RED){
                    w.color = x.p.color;
                    x.p.color = BLACK;
                    w.left.color = BLACK;
                    right_rotate(x.p);
                    x = this.root;
                }
            }
        }
        x.color = BLACK;
    }

    public Node search(int key){
        return this.search_helper(this.root, key);
    }

    private Node search_helper(Node n, int key){
        if(n == this.nil || key == n.key){
            return n;
        }
        if (key < n.key){
            return this.search_helper(n.left, key);
        }else{
            return this.search_helper(n.right, key);
        }
    }

    private Node minimum(Node z){
        while(z.left != this.nil){
            z = z.left;
        }
        return z;
    }
}