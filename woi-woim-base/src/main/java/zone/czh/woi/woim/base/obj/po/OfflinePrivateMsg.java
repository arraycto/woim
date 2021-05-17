package zone.czh.woi.woim.base.obj.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import zone.czh.woi.woim.base.obj.vo.PrivateMsg;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
*@ClassName: OfflinePrivateMsg
*@Description: None
*@author woi
*/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OfflinePrivateMsg implements Comparable<OfflinePrivateMsg>{
    @Id
    @GeneratedValue(generator = "JDBC")
    Long id;
    @Column
    String srcUid;
    @Column
    String destUid;
    @Column
    Integer type;
    @Column
    String content;
    @Column
    Timestamp createTime;

    public OfflinePrivateMsg(PrivateMsg msg){
        srcUid = msg.getSrcUid();
        destUid = msg.getDestUid();
        type = msg.getType();
        content = msg.getContent();
        createTime = new Timestamp(msg.getTimestamp());
    }

    @Override
    public String toString() {
        return createTime.toString()+"\n"+srcUid+":"+content;
    }

    @Override
    public int compareTo(OfflinePrivateMsg o) {
        return createTime.compareTo(o.createTime);
    }

}
