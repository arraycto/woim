package zone.czh.woi.woim.constant;
/**
*@ClassName: WOIMUrl
*@Description: None
*@author woi
*/
public class WOIMUrl {
    public static class Push{
        public static final String MODULE = "/push";
        public static final String PATH_PUSH_DISTRIBUTED = "/push-distributed";
    }

    public static class Session{
        public static final String MODULE = "/session";
        public static final String PATH_CLOSE_SESSION = "/close";
        public static final String PATH_KICK_OFF = "/kick-off";
    }

    public static class Message {
        public static final String MODULE = "/message";
        public static final String PATH_PULL_OFFLINE_MESSAGE = "/pull/offline-msg";
        public static final String PATH_PULL_OFFLINE_MSG_GROUP = "/pull/offline-msg/group";
        public static final String PATH_OFFLINE_PRIVATE_MSG = "/pull/offline-private-msg";
        public static final String PATH_PULL_OFFLINE_GROUP_MSG = "/pull/offline-group-msg";
        public static final String PATH_OFFLINE_MSG_INFO = "/offline-msg/info";
    }

    public static class User{
        public static final String MODULE = "/user";
        public static final String PATH_CONTACTS_LIST = "/contact/list";
        public static final String PATH_REQUEST_CONTACT = "/request/contact";
        public static final String PATH_ACCEPT_CONTACT_REQ = "/accept/contact-req";
        public static final String PATH_CONTACT_REQ_LIST = "/contact-req/list";
        public static final String PATH_CHAT_GROUP_LIST = "/chat-group/list";
    }

    public static class ChatGroup{
        public static final String MODULE = "/chat-group";
        public static final String PATH_CREATE = "/create";
        public static final String PATH_UPD = "/upd";
        public static final String PATH_REMOVE = "/remove";
        public static final String PATH_TRANSFER = "/transfer";
        public static final String PATH_ADD_MEMBER = "/add-member";
        public static final String PATH_REMOVE_MEMBER = "/remove-member";
        public static final String PATH_PROMOTE_MEMBER = "/promote-member";
        public static final String PATH_DEMOTE_MEMBER = "/demote-member";
    }

    public static class Distributed{
        public static final String MODULE = "/woim-distributed";
        public static final String PATH_PUSH = "/push";
        public static final String PATH_CLOSE_SESSION = "/close-session";
    }
}
