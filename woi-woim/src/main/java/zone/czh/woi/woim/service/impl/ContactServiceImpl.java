package zone.czh.woi.woim.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import zone.czh.woi.woim.base.obj.po.Contact;
import zone.czh.woi.woim.base.obj.po.ContactRequest;
import zone.czh.woi.woim.mapper.ContactMapper;
import zone.czh.woi.woim.mapper.ContactRequestMapper;
import zone.czh.woi.woim.service.inter.ContactService;

import java.sql.Timestamp;
import java.util.*;

/**
*@ClassName: ContactServiceImpl
*@Description: None
*@author woi
*/

public class ContactServiceImpl implements ContactService {

    @Autowired
    ContactMapper contactMapper;

    @Autowired
    ContactRequestMapper contactRequestMapper;

    @Override
    public ContactRequest getContactReq(Long contactRequestId) {
        return contactRequestMapper.selectByPrimaryKey(contactRequestId);
    }

    @Override
    public void updContactReqState(Long contactRequestId, Integer state) {
        contactRequestMapper.updateByPrimaryKeySelective(new ContactRequest().setId(contactRequestId).setState(state));
        return ;
    }

    @Override
    public void delContactReq(Long contactRequestId) {
        contactRequestMapper.deleteByPrimaryKey(contactRequestId);
        return ;
    }

    @Override
    public ContactRequest requestContact(String hostUid, String contactUid,String msg) {
        ContactRequest contactRequest = new ContactRequest().setHostUid(hostUid)
                .setContactUid(contactUid)
                .setMsg(msg)
                .setState(ContactRequest.STATE_PENDING)
                .setCreateTime(new Timestamp(System.currentTimeMillis()));
        contactRequestMapper.insert(contactRequest);
        return contactRequest;
    }

    @Override
    public List<ContactRequest> getContactRequestList(String uid) {
        return contactRequestMapper.select(new ContactRequest().setContactUid(uid));
    }

    @Transactional
    @Override
    public void acceptContact(Long contactRequestId) {
        ContactRequest contactRequest = contactRequestMapper.selectByPrimaryKey(contactRequestId);
        if (contactRequest!=null){
            contactRequest.setState(ContactRequest.STATE_ACCEPTED);
            contactRequestMapper.updateByPrimaryKeySelective(contactRequest);
            buildContact(contactRequest.getHostUid(),contactRequest.getContactUid());
        }
    }

    @Override
    public void buildContact(String uid1, String uid2) {
        buildContact(uid1,uid2,"","");
    }

    @Override
    public void buildContact(String uid1, String uid2, String aliasForU1, String aliasForU2) {
        if (contactMapper.selectCount(new Contact().setHostUid(uid1).setContactUid(uid2))==0){
            contactMapper.insert(new Contact().setHostUid(uid1).setContactUid(uid2).setAlias(aliasForU2));
        }
        if (contactMapper.selectCount(new Contact().setHostUid(uid2).setContactUid(uid1))==0){
            contactMapper.insert(new Contact().setHostUid(uid2).setContactUid(uid1).setAlias(aliasForU1));

        }
    }

    @Override
    public void removeContact(String hostUid, String contactUid) {
        contactMapper.delete(new Contact().setHostUid(hostUid).setContactUid(contactUid));
    }

    @Override
    public List<Contact> getContacts(String uid) {
        if (uid==null){
            return null;
        }
        return contactMapper.select(new Contact().setHostUid(uid));
    }
}
