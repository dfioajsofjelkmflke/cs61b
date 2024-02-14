import java.util.Currency;

public class Palindrome {
    public Deque<Character> wordToDeque(String word){
        Deque<Character> d= new ArrayDeque<>();
        for(int i =0 ;i< word.length();i+=1){
            char ch =word.charAt(i);
            d.addLast(ch);
        }
        return d;
    }
    public boolean isPalindrome(String word){
        Deque<Character> d = wordToDeque(word);
        while(! d.isEmpty()){
            if(d.size()==1){
                return true;
            }
            char first = d.removeFirst();
            char last = d.removeLast();
            if(first != last){
                return false;
            }
        }
        return true;
    }
    public boolean isPalindrome(String word, CharacterComparator cc){
        Deque<Character> d = wordToDeque(word);
        while( ! d.isEmpty()){
            if(d.size() == 1){
                return true;
            }
            char first = d.removeFirst();
            char last = d.removeLast();
            if(! cc.equalChars(first,last)){
                return false;
            }
        }
        return true;
    }
}