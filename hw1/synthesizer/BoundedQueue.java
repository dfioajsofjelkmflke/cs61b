package synthesizer;
import java.util.Iterator;

public interface BoundedQueue <T> extends Iterable<T>{
    int capacity();//return the size of the buffer
    int fillCount();//return the current number of the element in the buffer;
    void enqueue(T x);//add the item x to the end;
    T dequeue();//delete and return item from the front
    T peek();//return but not delete item from  the front
    default boolean isEmpty(){
        return fillCount()==0;
    }
    default boolean isFull(){
        return fillCount()==capacity();
    }
}
