package zone.czh.woi.woim.base.obj.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
*@ClassName: ChatGroup
*@Description: None
*@author woi
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ChatGroup {
    @Id
    Long id;
    @Column
    String name;
    @Column
    String avatar;
    @Column
    String description;



}
