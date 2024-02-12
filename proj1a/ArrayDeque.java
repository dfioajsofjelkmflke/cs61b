import java.net.InterfaceAddress;

public class ArrayDeque<Item> {
    public int size;
    public Item[] items;
    public int next;
    public int nextFirst;
    public int nextLast;
    public ArrayDeque() {
        nextFirst = 4;
        nextLast = 5;
        items = (Item[]) new Object[8];
        size = 0;
    }
    public void addFirst(Item x){
        if(size==items.length){
            resize(items.length*2);
        }
        items[nextFirst]=x;
        size++;
        nextFirst=re_index(nextFirst-1);
    }
    public void addLast(Item x ){
        if(size==items.length){
            resize(items.length*2);
        }
        items[nextLast]=x;
        size++;
        nextLast=re_index(nextLast+1);
    }
    public Item get(int index){
        int get_index=re_index(index+nextFirst+1);
        return items[get_index];
    }
    public int size(){
        return size;
    }
    public boolean isEmpty(){
        return size==0;
    }
    public void printDeque(){
        int current_index=nextFirst+1;
        int last_index=re_index(nextLast-1);
        int start = current_index;
        while(items[current_index] != null){
            System.out.println(items[current_index]);
            current_index=re_index(current_index+1);
            if(current_index==start){
                break;
            }
        }
    }
    public Item removeFirst(){
        size--;
        nextFirst=re_index(nextFirst+1);
        Item result=items[nextFirst];
        items[nextFirst]=null;
        return result;
    }
    public Item removeLast(){
        size--;
        nextLast=re_index(nextLast-1);
        Item result=items[nextLast];
        items[nextLast]=null;
        return result;
    }
    public void  resize(int capacity){
        Item[] a =(Item[]) new Object[capacity];
        System.arraycopy(items,0,a,0,nextLast);
        int i =nextLast+items.length;
        System.arraycopy(items,nextFirst+1,a,nextLast+items.length,items.length-nextLast);
        items=a;
        nextFirst=re_index(i-1);
    }
    public int re_index(int original_index){
        if(original_index >= items.length){
            return original_index-items.length;
        }else if(original_index<0){
            return original_index+items.length;
        }else{
            return original_index;
        }
    }
    public static void main(String[] args){
        ArrayDeque<Integer> test=new ArrayDeque<>();
        int i = 9;
        while(i > 0 ){
            test.addFirst(i);
            i--;
        }
        test.printDeque();
    }

}
