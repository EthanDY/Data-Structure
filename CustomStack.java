public class CustomStack<T> {
    
    public class Node<T> {
        public T t;
        public Node<T> next;
        public Node<T> prev;
    
        public Node(T t){
            this.t = t;
            this.next = null;
            this.prev = null;
        }
    }

    private Node<T> dummy;
    private Node<T> tail;

    public CustomStack(T t){
        this.dummy = new Node<T>(t);
        this.dummy.prev = null;
        this.dummy.next = new Node<T>(t);
        this.dummy.next.prev = this.dummy;
        this.tail = this.dummy.next;
    }

    public boolean isEmpty(){
        return this.dummy.next == null;
    }

    public void push(T t){
        this.tail.next = new Node<T>(t);
        this.tail.next.prev = this.tail;
        this.tail = this.tail.next;
    }

    public void print(){
        Node<T> ptr = this.dummy.next;
        if(ptr == null){
            System.out.println("Empty Stack");
        }
        while(ptr != null){
            System.out.print(ptr.t + " ");
            ptr = ptr.next;
        }
    }

    public T pop(){
        Node<T> prev = this.tail.prev;
        prev.next = null;
        T t = tail.t;
        this.tail = prev;
        return t;
    }
}

