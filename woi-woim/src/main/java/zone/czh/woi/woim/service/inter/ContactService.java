package zone.czh.woi.woim.service.inter;

import zone.czh.woi.woim.base.obj.po.Contact;
import zone.czh.woi.woim.base.obj.po.ContactRequest;

import java.util.List;

/**
*@ClassName: ContactService
*@Description: None
*@author woi
*/
public interface ContactService {
    void updContactReqState(Long contactRequestId, Integer state);
    void delContactReq(Long contactRequestId);
    ContactRequest requestContact(String hostUid, String contactUid, String msg);
    List<ContactRequest> getContactRequestList(String uid);
    void acceptContact(Long contactRequestId);
    void buildContact(String uid1, String uid2);
    void buildContact(String uid1, String uid2, String aliasForU1, String aliasForU2);
    void removeContact(String hostUid, String contactUid);
    List<Contact> getContacts(String uid);
    ContactRequest getContactReq(Long contactRequestId);

}
