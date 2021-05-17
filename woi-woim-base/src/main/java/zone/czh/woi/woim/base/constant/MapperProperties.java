package zone.czh.woi.woim.base.constant;

import javax.persistence.Column;
import javax.persistence.Id;

public class MapperProperties {
    public static class OfflinePrivateMsg{
        public static final String ID = "id";
        public static final String SRC_UID = "srcUid";
        public static final String DEST_UID = "destUid";
        public static final String TYPE = "type";
        public static final String CONTENT = "content";
        public static final String CREATE_TIME = "createTime";
    }

    public static class OfflineGroupMsg{
        public static final String ID = "id";
        public static final String SRC_UID = "srcUid";
        public static final String DEST_UID = "destUid";
        public static final String GROUP_ID = "groupId";
        public static final String TYPE = "type";
        public static final String CONTENT = "content";
        public static final String CREATE_TIME = "createTime";
    }

    public static class ChatGroup{
        public static final String ID="id";
        public static final String NAME="name";
        public static final String AVATAR="avatar";
        public static final String DESC="desc";
    }
}
