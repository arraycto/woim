package zone.czh.woi.woim.base.obj.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
*@ClassName: Contact
*@Description: None
*@author woi
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Contact {
    @Id
    @GeneratedValue(generator = "JDBC")
    Long id;
    @Column
    String hostUid;
    @Column
    String contactUid;
    @Column
    String alias;
}
