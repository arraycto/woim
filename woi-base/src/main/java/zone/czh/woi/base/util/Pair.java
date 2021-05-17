package zone.czh.woi.base.util;

import lombok.Data;
/**
*@ClassName: Pair
*@Description: None
*@author woi
*/
@Data
public class Pair <K,V>{
    K key;
    V value;
    public Pair(K key,V value){
        this.key=key;
        this.value=value;
    }
}
