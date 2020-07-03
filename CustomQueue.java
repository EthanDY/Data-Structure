public class CustomQueue<T> {
    
    public class Node<T> {
        public T t;
        public Node<T> next;
    
        public Node(T t){
            this.t = t;
            this.next = null;
        }
    }

    private Node<T> dummy;
    private Node<T> tail;

    public CustomQueue(T t){
        this.dummy = new Node<T>(t);
        this.dummy.next = new Node<T>(t);
        this.tail = this.dummy.next;
    }

    public boolean isEmpty(){
        return this.dummy.next == null;
    }

    public void enqueue(T t){
        this.tail.next = new Node<T>(t);
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

    public T dequeue(){
        if(this.dummy.next == null){
            return null;
        }else{
            T t = this.dummy.next.t;
            this.dummy.next = this.dummy.next.next;
            return t;
        }
    }    
}