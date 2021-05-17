package zone.czh.woi.woim.base.obj.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import zone.czh.woi.woim.base.obj.po.OfflinePrivateMsg;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
*@ClassName: PrivateMsgGroup
*@Description: None
*@author woi
*/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PrivateMsgGroup {
    private String srcUid;
    private Integer unPull;
    private List<OfflinePrivateMsg> privateMsgs;

    public void addMsg(OfflinePrivateMsg msg) {
        if (privateMsgs==null){
            privateMsgs = new LinkedList<>();
        }
        privateMsgs.add(msg);
    }

    public void sort(){
        if (privateMsgs!=null){
            Collections.sort(privateMsgs);
        }
    }

    @Override
    public String toString() {
        String s = "";
        s+="type:private msg\n" +
                "srcUid:"+srcUid+"\n";
        for (OfflinePrivateMsg msg:privateMsgs){
            s+=msg.toString()+"\n";
        }
        return s;
    }
}
