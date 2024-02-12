public class ArrayDeque {
    public int size;
    public int[] items;
    public int next;
    public int nextFirst;
    public int nextLast;
    public ArrayDeque(){
        nextFirst=4;
        nextLast=5;
        items=new int[8];
        size=0;
    }
    public void addFirst(int x){
        if(size==items.length){
            resize(items.length*2);
        }
        items[nextFirst]=x;
        size++;
        nextFirst=re_index(nextFirst-1);
    }
    public void addLast(int x ){
        if(size==items.length){
            resize(items.length*2);
        }
        items[nextLast]=x;
        size++;
        nextLast=re_index(nextLast+1);
    }
    public int get(int index){
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
        while(items[current_index] != 0){
            System.out.println(items[current_index]);
            current_index=re_index(current_index+1);
            if(current_index==start){
                break;
            }
        }
    }
    public int removeFirst(){
        size--;
        nextFirst=re_index(nextFirst+1);
        int result=items[nextFirst];
        items[nextFirst]=0;
        return result;
    }
    public int removeLast(){
        size--;
        nextLast=re_index(nextLast-1);
        int result=items[nextLast];
        items[nextLast]=0;
        return result;
    }
    public void  resize(int capacity){
        int[] a = new int[capacity];
        System.arraycopy(items,0,a,0,nextLast);
        int i =nextLast+items.length;
        System.arraycopy(items,nextFirst+1,a,nextLast+items.length,items.length-nextLast);
        items=a;
        nextFirst=re_index(i-1);
    }
    public int re_index(int original_index){
        if(original_index>=items.length){
            return original_index-items.length;
        }else if(original_index<0){
            return original_index+items.length;
        }else{
            return original_index;
        }
    }
    public static void main(String[] args){
        ArrayDeque test=new ArrayDeque();

    }

}
