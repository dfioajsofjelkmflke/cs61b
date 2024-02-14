public class OffByN implements CharacterComparator{
    int N;
    public OffByN(int N){
        this.N= N;
    }
    public boolean equalChars(char x , char y){
        int diff = Math.abs(x-y);
        if(diff == N){
            return true;
        }else{
            return false;
        }
    }
}
