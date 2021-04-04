package com.company.main;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Map2D <R,C,V> {
    HashMap<R,HashMap<C,V>> map=new HashMap<>();

    public V put(R rowKey, C columnKey, V value) {
        if(rowKey==null||columnKey==null)
            throw new NullPointerException();

        if(map.keySet().contains(rowKey)){
            map.get(rowKey).put(columnKey,value);
            return null;
        }
        else{
            map.put(rowKey,new HashMap<C,V>());
            return map.get(rowKey).put(columnKey,value);
        }
    }

    public V get(R rowKey, C columnKey) {
        if (map.containsKey(rowKey))
            return map.get(rowKey).get(columnKey);
        else
            return null;
    }

    public V getOrDefault(R rowKey, C columnKey, V defaultValue) {
        V result=get(rowKey,columnKey);
        if(result==null)
            return defaultValue;
        return result;
    }

    public V remove(R rowKey, C columnKey) {
        if(map.containsKey(rowKey)){
            V result = map.get(rowKey).get(columnKey);
            if(result==null){
                return null;
            }
            else{
                map.get(rowKey).remove(columnKey,result);
                return result;
            }
        }
        return null;
    }

    public boolean isEmpty() {
        if(size()==0)
            return true;
        else
            return false;
    }

    public boolean nonEmpty() {
        return !isEmpty();
    }

    public int size() {
        int s=0;
        for(R k: map.keySet())
            s+=map.get(k).size();
        return s;
    }

    public void clear() {
        for(R k: map.keySet())
            map.get(k).clear();
    }

    public Map<C, V> rowView(R rowKey) {
        Map<C,V> result=map.get(rowKey);
        return result;
    }

    public Map<R, V> columnView(C columnKey) {
        Map<R,V> result=new HashMap<>();
        for(R k: map.keySet())
            for(C k2: map.get(k).keySet())
                if(k2==columnKey)
                    result.put(k,map.get(k).get(k2));
        return result;
    }

    public boolean hasValue(V value) {
        for(R k: map.keySet())
            for(C k2: map.get(k).keySet())
                if(get(k,k2)!=null)
                    return true;

        return false;
    }

    public boolean hasKey(R rowKey, C columnKey) {
        if(get(rowKey,columnKey)!=null)
            return true;
        else
            return false;
    }

    public boolean hasRow(R rowKey) {
        if(map.containsKey(rowKey))
            if(map.get(rowKey).size()!=0)
                return true;

        return false;
    }

    public boolean hasColumn(C columnKey) {
        for(R k: map.keySet())
            for(C k2: map.get(k).keySet())
                if(k2==columnKey)
                    if(map.get(k).get(k2)!=null)
                        return true;
        return false;
    }

    public Map<R, Map<C, V>> rowMapView() {
        HashMap<R,Map<C,V>>result =new HashMap();
        for(R k: map.keySet()){
            result.put(k,new HashMap<C,V>());
            for(C k2: map.get(k).keySet()){
                result.get(k).put(k2,map.get(k).get(k2));
            }
        }
        return result;
    }

    public Map<C, Map<R, V>> columnMapView() {
        HashMap<C,Map<R,V>>result =new HashMap();
        for(R k: map.keySet())
            for(C k2: map.get(k).keySet())
                result.put(k2,new HashMap<R,V>());

        for(R k: map.keySet())
            for(C k2: map.get(k).keySet())
                result.get(k2).put(k,map.get(k).get(k2));

        return result;
    }
    
    public Map2D<R, C, V> fillMapFromRow(Map<? super C, ? super V> target, R rowKey) {
        if(!map.containsKey(rowKey))
            return this;
        for(C k: map.get(rowKey).keySet())
            target.put(k,map.get(rowKey).get(k));

        return this;
    }
    
    public Map2D<R, C, V> fillMapFromColumn(Map<? super R, ? super V> target, C columnKey) {
        for(R k: map.keySet())
            for(C k2: map.get(k).keySet())
                if(k2==columnKey)
                    target.put(k,map.get(k).get(k2));
        return this;
    }
    
    public Map2D<R, C, V> putAll(Map2D<? extends R, ? extends C, ? extends V> source) {
        Map<? extends R, ? extends Map<? extends C, ? extends V>> help=source.rowMapView();
        help.forEach((r,help2)->{
            help2.forEach((c,v)->this.put(r,c,v));
        });
        return this;
    }
    
    public Map2D<R, C, V> putAllToRow(Map<? extends C, ? extends V> source, R rowKey) {
        for(Object k: source.keySet())
            put(rowKey, (C) k,source.get(k));
        return this;
    }
    
    public Map2D<R, C, V> putAllToColumn(Map<? extends R, ? extends V> source, C columnKey) {
        for(Object k: source.keySet())
            put((R) k,columnKey,source.get(k));
        return this;
    }
    
    public <R2, C2, V2> Map2D<R2, C2, V2> copyWithConversion(Function<? super R, ? extends R2> rowFunction, Function<? super C, ? extends C2> columnFunction, Function<? super V, ? extends V2> valueFunction) {
        Map2D<R2,C2,V2> result= new Map2D<>();
        for(var k: map.keySet())
            for(var k2: map.get(k).keySet())
                result.put(rowFunction.apply(k),columnFunction.apply(k2),valueFunction.apply(get(k,k2)));

        return result;
    }
}
