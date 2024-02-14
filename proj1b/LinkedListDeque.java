public class LinkedListDeque<T> implements Deque<T>{
    private class TypeNode {
        public T item;
        public TypeNode next;
        public TypeNode pre;
        public TypeNode(T i ,TypeNode p,TypeNode n){
            item=i;
            pre = p;
            next=n;
        }
    }
    private TypeNode sentinel;
    private int size;
    //    public LinkedListDeque(T x){
//        sentinel = new TypeNode(null,null,null);
//        sentinel.next = new TypeNode(x,null,null);
//        sentinel.next.pre=sentinel;
//        sentinel.next.next=sentinel;
//        sentinel.pre=sentinel.next;
//        size = 1;
//    }
    public LinkedListDeque(){
        sentinel = new TypeNode(null, null,null);
        sentinel.next=sentinel;
        sentinel.pre=sentinel;
        size = 0;
    }
    @Override
    public int size(){
        return size;
    }
    @Override
    public void addFirst(T x){
        TypeNode new_node = new TypeNode(x,null,null);
        new_node.next=sentinel.next;
        new_node.pre=sentinel;
        sentinel.next.pre=new_node;
        sentinel.next=new_node;
        size++;
    }
    @Override
    public void addLast(T x ){
        TypeNode new_node = new TypeNode(x,null,null);
        new_node.pre=sentinel.pre;
        new_node.next=sentinel;
        sentinel.pre.next=new_node;
        sentinel.pre=new_node;
        size++;
    }
    @Override
    public boolean isEmpty(){
        return size==0;
    }
    @Override
    public void printDeque(){
        TypeNode current_node = sentinel.next;
        while(current_node.item != null){
            System.out.println(current_node.item);
            current_node=current_node.next;
        }
    }
    @Override
    public T removeFirst(){
        if(sentinel.next.item != null){
            TypeNode remove_node=sentinel.next;
            sentinel.next=sentinel.next.next;
            sentinel.next.pre=sentinel;
            size--;
            return remove_node.item;
        }else {
            return null;
        }
    }
    @Override
    public T removeLast(){
        if (sentinel.pre.item != null){
            TypeNode remove_node =sentinel.pre;
            sentinel.pre=sentinel.pre.pre;
            sentinel.pre.next=sentinel;
            size--;
            return remove_node.item;
        }else{
            return null;
        }
    }
    @Override
    public T get(int index){
        int current_index = 0;
        TypeNode current_node = sentinel.next;
        while(current_index != index){
            current_node=current_node.next;
            current_index++;
        }
        return current_node.item;
    }
    public T getRecursive(int index){
        return getRecursive_helper(sentinel.next,index);
    }
    private T getRecursive_helper(TypeNode current_node,int index){
        if(index==0){
            return current_node.item;
        }else{
            current_node=current_node.next;
            return getRecursive_helper(current_node,index-1);
        }
    }
    private void insert(T i , int position){
        TypeNode current_node = sentinel.next;
        int index = 0;
        while(index != position){
            current_node=current_node.next;
            index++;
        }
        TypeNode insert_node= new TypeNode(i,null,null);
        insert_node.pre=current_node.pre;
        current_node.pre.next=insert_node;
        insert_node.next=current_node;
        current_node.pre=insert_node;
        size++;
    }

//    public static void main(String[] args) {
//        LinkedListDeque<Integer> test = new LinkedListDeque<>();
//        test.addFirst(1);
//        test.addFirst(2);
//        test.addLast(-1);
//        test.insert(100,1);
//        System.out.println(test.size());
//    }
}
