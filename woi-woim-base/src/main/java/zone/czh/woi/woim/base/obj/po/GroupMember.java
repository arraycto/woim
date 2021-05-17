package zone.czh.woi.woim.base.obj.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Id;

/**
*@ClassName: GroupMember
*@Description: None
*@author woi
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class GroupMember {
    public static transient final int ROLE_OWNER = 0;
    public static transient final int ROLE_ADMIN = 1;
    public static transient final int ROLE_MEMBER = 2;
    @Id
    @Accessors(chain = true)
    Long id;
    @Column
    Long groupId;
    @Column
    String uid;
    @Column
    String groupNickName;
    @Column
    Integer role;
}
