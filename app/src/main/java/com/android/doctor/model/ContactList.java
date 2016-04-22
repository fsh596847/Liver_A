package com.android.doctor.model;

import java.util.List;

/**
 * Created by Yong on 2016/4/21.
 */
public class ContactList {


    private int total;
    /**
     * _id : 56f92596090c5ed99540b272
     * uid : 124
     * utype : 0
     * linktype : 医生
     * linkuuid : 4429a695-3393-49f5-b227-c9b68b86d0c3
     * friendlinkuuid : 1f75a935-9cc5-4d7a-8d0f-79deceebb0cf
     * name : 郭瑛
     * querycode : gy
     * nickname : 郭瑛
     * relationName : 主动添加
     * sex : 0
     * phones : [{"name":"联系人电话","phone":""},{"name":"单位电话","phone":""}]
     * birthday :
     * address :
     * addresses : [{"name":"家庭地址","address":null},{"name":"单位地址","address":""},{"name":"户口所在地","address":""}]
     * email :
     * emails : [{"name":"qq","value":""},{"name":"weixin","value":""}]
     * desc : null
     * cis : {"diagnoseid":"","hosid":""}
     * platform : {"duid":122}
     * createtime : 1459168662561
     */

    private List<ContactEntity> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ContactEntity> getData() {
        return data;
    }

    public void setData(List<ContactEntity> data) {
        this.data = data;
    }

    public static class ContactEntity {
        private String _id;
        private int uid;
        private int utype;
        private String linktype;
        private String linkuuid;
        private String friendlinkuuid;
        private String name;
        private String querycode;
        private String nickname;
        private String relationName;
        private int sex;
        private String birthday;
        private String address;
        private String email;
        private String desc;
        /**
         * diagnoseid :
         * hosid :
         */

        private CisEntity cis;
        /**
         * duid : 122
         */

        private PlatformEntity platform;
        private long createtime;
        /**
         * name : 联系人电话
         * phone :
         */

        private List<PhonesEntity> phones;
        /**
         * name : 家庭地址
         * address : null
         */

        private List<AddressesEntity> addresses;
        /**
         * name : qq
         * value :
         */

        private List<EmailsEntity> emails;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getUtype() {
            return utype;
        }

        public void setUtype(int utype) {
            this.utype = utype;
        }

        public String getLinktype() {
            return linktype;
        }

        public void setLinktype(String linktype) {
            this.linktype = linktype;
        }

        public String getLinkuuid() {
            return linkuuid;
        }

        public void setLinkuuid(String linkuuid) {
            this.linkuuid = linkuuid;
        }

        public String getFriendlinkuuid() {
            return friendlinkuuid;
        }

        public void setFriendlinkuuid(String friendlinkuuid) {
            this.friendlinkuuid = friendlinkuuid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getQuerycode() {
            return querycode;
        }

        public void setQuerycode(String querycode) {
            this.querycode = querycode;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getRelationName() {
            return relationName;
        }

        public void setRelationName(String relationName) {
            this.relationName = relationName;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Object getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public CisEntity getCis() {
            return cis;
        }

        public void setCis(CisEntity cis) {
            this.cis = cis;
        }

        public PlatformEntity getPlatform() {
            return platform;
        }

        public void setPlatform(PlatformEntity platform) {
            this.platform = platform;
        }

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }

        public List<PhonesEntity> getPhones() {
            return phones;
        }

        public void setPhones(List<PhonesEntity> phones) {
            this.phones = phones;
        }

        public List<AddressesEntity> getAddresses() {
            return addresses;
        }

        public void setAddresses(List<AddressesEntity> addresses) {
            this.addresses = addresses;
        }

        public List<EmailsEntity> getEmails() {
            return emails;
        }

        public void setEmails(List<EmailsEntity> emails) {
            this.emails = emails;
        }

        public static class CisEntity {
            private String diagnoseid;
            private String hosid;

            public String getDiagnoseid() {
                return diagnoseid;
            }

            public void setDiagnoseid(String diagnoseid) {
                this.diagnoseid = diagnoseid;
            }

            public String getHosid() {
                return hosid;
            }

            public void setHosid(String hosid) {
                this.hosid = hosid;
            }
        }

        public static class PlatformEntity {
            private int duid;

            public int getDuid() {
                return duid;
            }

            public void setDuid(int duid) {
                this.duid = duid;
            }
        }

        public static class PhonesEntity {
            private String name;
            private String phone;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }
        }

        public static class AddressesEntity {
            private String name;
            private Object address;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Object getAddress() {
                return address;
            }

            public void setAddress(Object address) {
                this.address = address;
            }
        }

        public static class EmailsEntity {
            private String name;
            private String value;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }
}
