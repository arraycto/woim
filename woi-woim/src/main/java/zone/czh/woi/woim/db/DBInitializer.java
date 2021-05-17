package zone.czh.woi.woim.db;
import lombok.AllArgsConstructor;
import zone.czh.woi.woim.mapper.WOIMInitMapper;
/**
*@ClassName: DBInitializer
*@Description: None
*@author woi
*/
@AllArgsConstructor
public class DBInitializer {
    public static final String MODE_NONE="NONE";
    public static final String MODE_NEW="NEW";
    public static final String MODE_SAFE="SAFE";
    private WOIMInitMapper mapper;
    public void init(String mode){
        switch (mode){
            case MODE_NEW:
                dropTables();
                createTables();
                break;
            case MODE_SAFE:
                createTables();
        }
    }
    private void dropTables(){
        mapper.dropWOIMSession();
        mapper.dropContactRequest();
        mapper.dropContact();
        mapper.dropGroupMember();
        mapper.dropChatGroup();
        mapper.dropOfflinePrivateMsg();
        mapper.dropOfflineGroupMsg();
    }
    private void createTables(){
        mapper.createWOIMSession();
        mapper.createContact();
        mapper.createContactRequest();
        mapper.createChatGroup();
        mapper.createGroupMember();
        mapper.createOfflineGroupMsg();
        mapper.createOfflinePrivateMsg();
    }
}
