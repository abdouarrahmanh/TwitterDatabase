import java.util.Iterator;
import java.util.List;

/**
 * Created by joefischman on 11/25/15.
 */
public class ItemCount implements Comparable<ItemCount> {
    Object key;
    int counter;

    public ItemCount(Object key, int counter){
        this.key = key;
        this.counter= counter;
    }

    public Object getObject() {
        return key;
    }

    public int getCount() {
        return counter;
    }

    @Override
    public int compareTo(ItemCount o) {
        if(this.counter>o.counter){
            return 1;
        }
        else if(this.counter<o.counter){
            return -1;
        }
        else{
            return 0;
        }}

    @Override
    public String toString() {
        return key.toString()+"\t"+counter +"\n";

    }

}