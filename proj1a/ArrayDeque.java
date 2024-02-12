public class ArrayDeque<Item> {
    private int size;
    private Item[] items;
    private int next;
    private int nextFirst;
    private int nextLast;
    public ArrayDeque() {
        nextFirst = 4;
        nextLast = 5;
        items = (Item[]) new Object[8];
        size = 0;
    }
    private void addFirst(Item x){
        if(size==items.length){
            resize(items.length*2);
        }
        items[nextFirst]=x;
        size++;
        nextFirst=re_index(nextFirst-1);
    }
    private void addLast(Item x ){
        if(size==items.length){
            resize(items.length*2);
        }
        items[nextLast]=x;
        size++;
        nextLast=re_index(nextLast+1);
    }
    private Item get(int index){
        int get_index=re_index(index+nextFirst+1);
        return items[get_index];
    }
    private int size(){
        return size;
    }
    private boolean isEmpty(){
        return size==0;
    }
    private void printDeque(){
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
    private Item removeFirst(){
        size--;
        nextFirst=re_index(nextFirst+1);
        Item result=items[nextFirst];
        items[nextFirst]=null;
        return result;
    }
    private Item removeLast(){
        size--;
        nextLast=re_index(nextLast-1);
        Item result=items[nextLast];
        items[nextLast]=null;
        return result;
    }
    private void  resize(int capacity){
        Item[] a =(Item[]) new Object[capacity];
        System.arraycopy(items,0,a,0,nextLast);
        int i =nextLast+items.length;
        int start_index=re_index(nextFirst+1);
        System.arraycopy(items,start_index,a,nextLast+items.length,items.length-nextLast);
        items=a;
        nextFirst=re_index(i-1);
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
    public static void main(String[] args){
        ArrayDeque<String> test=new ArrayDeque<>();
    }

}
