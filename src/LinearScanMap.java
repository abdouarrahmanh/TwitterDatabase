import java.util.*;

/**
 * Created by Abdou on 11/26/2015.
 */
public class LinearScanMap<K,V> extends AbstractMap<K,V> {

    Set<Entry<K,V>> internalSet;

    public LinearScanMap(){
        this.internalSet = new HashSet<>();
    }

    public Set<Entry<K,V>> entrySet(){
        return internalSet;
    }

    public V put(K key, V value){
        AbstractMap.Entry entry = new AbstractMap.SimpleEntry<>(key,value);
        V prevValue = null;
        for(Entry e:internalSet){
            if(e.getKey().equals(key)){
                prevValue = (V) e.getValue();

            }
        }
        internalSet.add(entry);
        return prevValue;
    }

    public V get(Object key){
        V targetValue = null;
        for(Entry e:internalSet){
            if(e.getKey().equals(key)){
                targetValue =  (V) e.getValue();
            }
        }
        return targetValue;
    }
}