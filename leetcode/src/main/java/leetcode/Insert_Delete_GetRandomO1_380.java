package leetcode;

import java.util.*;

public class Insert_Delete_GetRandomO1_380 {

    private Map<Integer,Integer> map;
    List<Integer> list;
    Random random = new Random();
    int cur;

    public Insert_Delete_GetRandomO1_380() {
        map = new HashMap<>();
        list = new ArrayList<>();
        cur = 0;
    }

    public boolean insert(int val) {
        if(map.containsKey(val)){
            return false;
        }
        map.put(val,cur);
        list.add(cur,val);
        cur++;
        return true;
    }

    public boolean remove(int val) {
        if (!map.containsKey(val)){
            return false;
        }
        int temp = map.get(val);
        list.set(temp,list.get(--cur));
        map.put(list.get(temp),temp);
        map.remove(val);
        return true;
    }

    public int getRandom() {
        return list.get(random.nextInt(cur));
    }
}
