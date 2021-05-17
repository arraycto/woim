package zone.czh.woi.woim.base.obj.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import zone.czh.woi.woim.base.obj.vo.GroupMsg;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
*@ClassName: OfflineGroupMsg
*@Description: None
*@author woi
*/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OfflineGroupMsg implements Comparable<OfflineGroupMsg>{
    @Id
    @GeneratedValue(generator = "JDBC")
    Long id;
    @Column
    String srcUid;
    @Column
    String destUid;//由于群消息最终是触达每个用户的，且每个用户的连接状态不一，因此离线消息必须存在目的用户
    @Column
    Long groupId;
    @Column
    Integer type;
    @Column
    String content;
    @Column
    Timestamp createTime;

    public OfflineGroupMsg(GroupMsg msg, String destUid){
        this.srcUid = msg.getSrcUid();
        this.destUid = destUid;
        this.groupId = msg.getGroupId();
        this.type = msg.getType();
        this.content = msg.getContent();
        this. createTime = new Timestamp(msg.getTimestamp());
    }

    @Override
    public String toString() {
        return createTime.toString()+"\n"+srcUid+":"+content;
    }

    @Override
    public int compareTo(OfflineGroupMsg o) {
        return createTime.compareTo(o.createTime);
    }

}
