public class LinkedListDeque<Type>{
    private class TypeNode {
        public Type item;
        public TypeNode next;
        public TypeNode pre;
        public TypeNode(Type i ,TypeNode p,TypeNode n){
            item=i;
            pre = p;
            next=n;
        }
    }
    public TypeNode sentinel;
    public int size;
    public LinkedListDeque(Type x){
        sentinel = new TypeNode(null,null,null);
        sentinel.next = new TypeNode(x,null,null);
        sentinel.next.pre=sentinel;
        sentinel.next.next=sentinel;
        sentinel.pre=sentinel.next;
        size = 1;
    }
    public LinkedListDeque(){
        sentinel = new TypeNode(null, null,null);
        sentinel.next=sentinel;
        sentinel.pre=sentinel;
        size = 0;
    }
    public int size(){
        return size;
    }
    public void addFirst(Type x){
        TypeNode new_node = new TypeNode(x,null,null);
        new_node.next=sentinel.next;
        new_node.pre=sentinel;
        sentinel.next.pre=new_node;
        sentinel.next=new_node;
        size++;
    }
    public void addLast(Type x ){
        TypeNode new_node = new TypeNode(x,null,null);
        new_node.pre=sentinel.pre;
        new_node.next=sentinel;
        sentinel.pre.next=new_node;
        sentinel.pre=new_node;
        size++;
    }
    public Boolean isEmpty(){
        return size==0;
    }
    public void printDeque(){
        TypeNode current_node = sentinel.next;
        while(current_node.item != null){
            System.out.println(current_node.item);
            current_node=current_node.next;
        }
    }
    public Type removeFirst(){
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

    public Type removeLast(){
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
    public Type get(int index){
        int current_index = 0;
        TypeNode current_node = sentinel.next;
        while(current_index != index){
            current_node=current_node.next;
            current_index++;
        }
        return current_node.item;
    }
    public Type getRecursive(int index){
        return getRecursive_helper(sentinel.next,index);
    }
    public Type getRecursive_helper(TypeNode current_node,int index){
        if(index==0){
            return current_node.item;
        }else{
            current_node=current_node.next;
            return getRecursive_helper(current_node,index-1);
        }
    }
    public void insert(Type i , int position){
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

    public static void main(String[] args) {
        LinkedListDeque<Integer> test = new LinkedListDeque<>(0);
        test.addFirst(1);
        test.addFirst(2);
        test.addLast(-1);
        test.insert(100,1);
        System.out.println(test.size());
    }
}
