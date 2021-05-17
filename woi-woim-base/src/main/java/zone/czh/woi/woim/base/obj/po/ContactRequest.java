package zone.czh.woi.woim.base.obj.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
*@ClassName: ContactRequest
*@Description: None
*@author woi
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ContactRequest {
    public static final int STATE_PENDING = 0;
    public static final int STATE_ACCEPTED = 1;
    @Id
    @GeneratedValue(generator = "JDBC")
    Long id;
    @Column
    String hostUid;
    @Column
    String contactUid;
    @Column
    String msg;
    @Column
    Integer state;
    @Column
    Timestamp createTime;
}
