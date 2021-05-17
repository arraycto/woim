package zone.czh.woi.woim.base.obj.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import zone.czh.woi.woim.base.obj.po.OfflineGroupMsg;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
*@ClassName: GroupMsgGroup
*@Description: None
*@author woi
*/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class GroupMsgGroup {
    private Long groupId;
    private Integer unPull;
    private List<OfflineGroupMsg> groupMsgs;

    public void addMsg(OfflineGroupMsg msg) {
        if (groupMsgs==null){
            groupMsgs = new LinkedList<>();
        }
        groupMsgs.add(msg);
    }

    public void sort(){
        if (groupMsgs!=null){
            Collections.sort(groupMsgs);
        }
    }

    @Override
    public String toString() {
        String s = "";
        s+="type:group msg\ngroupId:"+groupId+"\n";
        for (OfflineGroupMsg msg:groupMsgs){
            s+=msg.toString()+"\n";
        }
        return s;
    }
}
