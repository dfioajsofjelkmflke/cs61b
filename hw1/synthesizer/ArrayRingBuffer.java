// TODO: Make sure to make this class a part of the synthesizer package
package synthesizer;
import java.util.Iterator;

//TODO: Make sure to make this class and all of its methods public
//TODO: Make sure to make this class extend AbstractBoundedQueue<t>
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
        this.first =0;
        this.last = 0 ;
        this.fillCount = 0;
        this.capacity = capacity;
        rb = (T[]) new Object[capacity];
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update last.
        // add item to the end
        if(isFull()){
            throw new RuntimeException("Ring buffer out upperflow");
        }
        rb[last++]= x;
        if(last==capacity){
            last=0;
        }
        fillCount +=1;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and update
        //delete and return item from the front
        if(isEmpty()){
            throw new RuntimeException("Ring buffer underflow");
        }
        T item = rb[first++];
        if(first == capacity){
            first = 0 ;
        }
        fillCount--;
        return item;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        // TODO: Return the first item. None of your instance variables should change.
        return rb[first];
    }

    // TODO: When you get to part 5, implement the needed code to support iteration.
    public Iterator<T> iterator(){
        return new BufferIterator();
    }
    private class BufferIterator implements Iterator<T>{
        private int pos;
        private int num;
        public BufferIterator(){
            pos = first ;
            num = 0;
        }
        @Override
        public boolean hasNext(){
            return num<fillCount;
        }
        @Override
        public T next(){
            T item = rb[pos];
            pos++;
            if(pos==capacity){
                pos = 0;
            }
            num++;
            return item;
        }
    }

//    public static void main(String [] args){
//        ArrayRingBuffer<Integer> test = new ArrayRingBuffer<>(2);
//        test.enqueue(1);
//        test.enqueue(2);
//        test.dequeue();
//
//    }

}
