import java.net.InterfaceAddress;

public class ArrayDeque<T> implements Deque<T>{
    private int size;
    private T[] items;
    private int next;
    private int nextFirst;
    private int nextLast;
    public ArrayDeque() {
        nextFirst = 4;
        nextLast = 5;
        items = (T[]) new Object[8];
        size = 0;
    }
    @Override
    public void addFirst(T x){
        if(size==items.length){
            resize(items.length*2);
        }
        items[nextFirst]=x;
        size++;
        nextFirst=re_index(nextFirst-1);
    }
    @Override
    public void addLast(T x ){
        if(size==items.length){
            resize(items.length*2);
        }
        items[nextLast]=x;
        size++;
        nextLast=re_index(nextLast+1);
    }
    @Override
    public T get(int index){
        int get_index=re_index(index+nextFirst+1);
        return items[get_index];
    }
    @Override
    public int size(){
        return size;
    }@Override
    public boolean isEmpty(){
        return size==0;
    }
    @Override
    public void printDeque(){
        int current_index=re_index(nextFirst+1);
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
    @Override
    public T removeFirst(){
        if(size!=0) {
            size--;
            nextFirst = re_index(nextFirst + 1);
            T result = items[nextFirst];
            items[nextFirst] = null;
            if(size<items.length/4 && items.length > 8){
                resize(items.length/2);
            }
            return result;
        }else{
            return null;
        }
    }
    @Override
    public T removeLast(){
        if(size!=0) {
            size--;
            nextLast = re_index(nextLast - 1);
            T result = items[nextLast];
            items[nextLast] = null;
            if(size<items.length/4 && items.length > 8){
                resize(items.length/2);
            }
            return result;
        }else{
            return null;
        }
    }
    //    public void  resize(int capacity){
//        Item[] a =(Item[]) new Object[capacity];
//        System.arraycopy(items,0,a,0,nextLast);
//        int i =nextLast+items.length;
//        System.arraycopy(items,nextFirst+1,a,nextLast+items.length,items.length-nextLast);
//        items=a;
//        nextFirst=re_index(i-1);
//    }
    private void resize(int capacity){
        T[] a = (T[]) new Object[capacity];
        int current_index=re_index(nextFirst+1);
        int start_index= current_index;
        int i = 0;
        while(items[current_index] != null){
            a[i] = items[current_index];
            i++;
            current_index=re_index(current_index+1);
            if(current_index == start_index){
                break;
            }
        }
        items=a;
        nextFirst=items.length-1;
        nextLast=size;
    }

    private int re_index(int original_index){
        if(original_index >= items.length){
            return original_index-items.length;
        }else if(original_index<0){
            return original_index+items.length;
        }else{
            return original_index;
        }
    }
//    public static void main(String[] args){
//        ArrayDeque<Integer> test=new ArrayDeque<>();
//        int i = 10;
//        while(i > 0 ){
//            test.addFirst(i);
//            i--;
//        }
//        test.printDeque();
//    }

}
