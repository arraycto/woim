package zone.czh.woi.woim.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface WOIMInitMapper {
    @Update("DROP TABLE IF EXISTS `group_member`")
    void dropGroupMember();
    @Update("DROP TABLE IF EXISTS `chat_group`")
    void dropChatGroup();
    @Update("DROP TABLE IF EXISTS `contact`")
    void dropContact();
    @Update("DROP TABLE IF EXISTS `contact_request`")
    void dropContactRequest();
    @Update("DROP TABLE IF EXISTS `offline_group_msg`")
    void dropOfflineGroupMsg();
    @Update("DROP TABLE IF EXISTS `offline_private_msg`")
    void dropOfflinePrivateMsg();
    @Update("DROP TABLE IF EXISTS `woim_session`")
    void dropWOIMSession();

    @Update("CREATE TABLE IF NOT EXISTS `chat_group`  (\n" +
            "  `id` bigint(20) NOT NULL,\n" +
            "  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,\n" +
            "  `avatar` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,\n" +
            "  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,\n" +
            "  PRIMARY KEY (`id`) USING BTREE\n" +
            ") ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;")
    void createChatGroup();
    @Update("CREATE TABLE IF NOT EXISTS `group_member`  (\n" +
            "  `id` bigint(20) NOT NULL AUTO_INCREMENT,\n" +
            "  `group_id` bigint(20) NOT NULL,\n" +
            "  `uid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,\n" +
            "  `group_nick_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,\n" +
            "  `role` int(11) NOT NULL DEFAULT 2,\n" +
            "  PRIMARY KEY (`id`) USING BTREE,\n" +
            "  INDEX `group_member_uid`(`uid`) USING BTREE,\n" +
            "  INDEX `gourp_member_group_id`(`group_id`) USING BTREE,\n" +
            "  CONSTRAINT `gourp_member_group_id` FOREIGN KEY (`group_id`) REFERENCES `chat_group` (`id`) ON DELETE CASCADE ON UPDATE CASCADE\n" +
            ") ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;")
    void createGroupMember();
    @Update("CREATE TABLE IF NOT EXISTS `contact`  (\n" +
            "  `id` bigint(20) NOT NULL AUTO_INCREMENT,\n" +
            "  `host_uid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,\n" +
            "  `contact_uid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,\n" +
            "  `alias` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',\n" +
            "  PRIMARY KEY (`id`) USING BTREE\n" +
            ") ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;")
    void createContact();
    @Update("CREATE TABLE IF NOT EXISTS `contact_request`  (\n" +
            "  `id` bigint(20) NOT NULL AUTO_INCREMENT,\n" +
            "  `host_uid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,\n" +
            "  `contact_uid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,\n" +
            "  `msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '',\n" +
            "  `state` int(11) NOT NULL DEFAULT 1 COMMENT '0/pending 1/accept',\n" +
            "  `create_time` datetime NOT NULL,\n" +
            "  PRIMARY KEY (`id`) USING BTREE\n" +
            ") ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;")
    void createContactRequest();
    @Update("CREATE TABLE IF NOT EXISTS `offline_group_msg`  (\n" +
            "  `id` bigint(20) NOT NULL AUTO_INCREMENT,\n" +
            "  `src_uid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,\n" +
            "  `dest_uid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,\n" +
            "  `group_id` bigint(20) NOT NULL,\n" +
            "  `type` int(11) NOT NULL,\n" +
            "  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,\n" +
            "  `create_time` datetime NOT NULL,\n" +
            "  PRIMARY KEY (`id`) USING BTREE,\n" +
            "  INDEX `offline_msg_src_dest_uid`(`src_uid`, `group_id`) USING BTREE,\n" +
            "  INDEX `offline_msg_dest_uid`(`group_id`) USING BTREE,\n" +
            "  INDEX `offline_group_msg_dest_uid`(`dest_uid`) USING BTREE\n" +
            ") ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;")
    void createOfflineGroupMsg();
    @Update("CREATE TABLE IF NOT EXISTS `offline_private_msg`  (\n" +
            "  `id` bigint(20) NOT NULL AUTO_INCREMENT,\n" +
            "  `src_uid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,\n" +
            "  `dest_uid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,\n" +
            "  `type` int(11) NOT NULL,\n" +
            "  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,\n" +
            "  `create_time` datetime NOT NULL,\n" +
            "  PRIMARY KEY (`id`) USING BTREE,\n" +
            "  INDEX `offline_msg_src_dest_uid`(`src_uid`, `dest_uid`) USING BTREE,\n" +
            "  INDEX `offline_private_msg_dest_uid`(`dest_uid`) USING BTREE\n" +
            ") ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;")
    void createOfflinePrivateMsg();
    @Update("CREATE TABLE IF NOT EXISTS `woim_session`  (\n" +
            "  `id` bigint(20) NOT NULL AUTO_INCREMENT,\n" +
            "  `uid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,\n" +
            "  `cid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,\n" +
            "  `channel_type` int(11) NOT NULL,\n" +
            "  `host_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,\n" +
            "  `host_port` int(11) NOT NULL,\n" +
            "  `remote_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,\n" +
            "  `remote_port` int(11) NOT NULL,\n" +
            "  `os_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',\n" +
            "  `os_version` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',\n" +
            "  `connect_time` datetime NOT NULL,\n" +
            "  PRIMARY KEY (`id`) USING BTREE\n" +
            ") ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;")
    void createWOIMSession();
}
