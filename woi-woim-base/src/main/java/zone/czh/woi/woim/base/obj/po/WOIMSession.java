package zone.czh.woi.woim.base.obj.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
*@ClassName: WOIMSession
*@Description: None
*@author woi
*/
@Table(name = "woim_session")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class WOIMSession implements Serializable {

    @Id
    @GeneratedValue(generator = "JDBC")
    Long id;
    @Column
    String uid;
    @Column
    String cid;
    @Column
    Integer channelType;
    @Column
    String hostIp;
    @Column
    Integer hostPort;
    @Column
    String remoteIp;
    @Column
    Integer remotePort;
    @Column
    String osName;
    @Column
    String osVersion;
    @Column
    Timestamp connectTime;


    public boolean equalToRemote(String remoteIp,Integer remotePort){
        return Objects.equals(this.remoteIp,remoteIp)&&Objects.equals(this.remotePort,remotePort);
    }

    public boolean exist(){
        return hostIp != null;
    }
}
