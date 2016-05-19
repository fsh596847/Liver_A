package com.android.doctor.model;

import java.util.List;

/**
 * Created by Yong on 2016/4/22.
 */
public class DoctorList {


    private List<DoctorEntity> data;

    public List<DoctorEntity> getData() {
        return data;
    }

    public void setData(List<DoctorEntity> data) {
        this.data = data;
    }

    public static class DoctorEntity {
        private String _id;
        private String no_staff;
        private String duid;
        private String username;
        private String mobilephone;
        private String nickname;
        private String password;
        private String pycode;
        private String duuid;
        private String deptid;
        private String deptname;
        private String hosid;
        private String hosname;
        /**
         * title : 医师
         * title_id : 1004
         * titleex : 无
         * teachtitle : 无
         * good : 无
         * experience : 无
         * isOnlineConsultation : 0
         * isPhoneConsultation : 0
         * isReservationPlus : 0
         * teachexperience : 无
         */

        private BaseEntity base;
        private DetailEntity detail;
        /**
         * publicnotice : {"nid":0,"content":""}
         * visittip : {"vid":0,"content":""}
         * stopdiagnosistip : {"sid":0,"content":""}
         */

        private DyncEntity dync;
        /**
         * pfollowers : 0
         * pfollowings : 0
         * followers : 1
         * followings : 0
         * topics : 0
         * suggests : 0
         */

        private StatEntity stat;
        /**
         * subaccountsid : bb0d7e05f7ab11e5bb9bac853d9f54f2
         * subtoken : 069e79932cd091af0d59bbb127db34a2
         * datecreated : 2016-04-01 09:47:45
         * voipaccount : 8001053600000120
         * voippwd : HBLSTIJk
         */

        private YtxsubaccountEntity ytxsubaccount;
        private String gender;
        private String imgurl;
        private String channel_id;
        private String device_type;
        private String source;
        private String imflag;
        private String noticeflag;
        private String lastvisittime;
        private String audit;
        private String open;
        private String defaultgroup;
        private String isregister;
        private String createtime;
        private PstatEntity pstat;
        private String updcount;
        private String pstatcount;
        /**
         * browse : 2
         */

        private AuthEntity auth;
        private String isexpert;


        private List<GroupsEntity> groups;


        private List<DgroupsEntity> dgroups;


        private List<UgroupsEntity> ugroups;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getNo_staff() {
            return no_staff;
        }

        public void setNo_staff(String no_staff) {
            this.no_staff = no_staff;
        }

        public String getDuid() {
            return duid;
        }

        public void setDuid(String duid) {
            this.duid = duid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getMobilephone() {
            return mobilephone;
        }

        public void setMobilephone(String mobilephone) {
            this.mobilephone = mobilephone;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPycode() {
            return pycode;
        }

        public void setPycode(String pycode) {
            this.pycode = pycode;
        }

        public String getDuuid() {
            return duuid;
        }

        public void setDuuid(String duuid) {
            this.duuid = duuid;
        }

        public String getDeptid() {
            return deptid;
        }

        public void setDeptid(String deptid) {
            this.deptid = deptid;
        }

        public String getDeptname() {
            return deptname;
        }

        public void setDeptname(String deptname) {
            this.deptname = deptname;
        }

        public String getHosid() {
            return hosid;
        }

        public void setHosid(String hosid) {
            this.hosid = hosid;
        }

        public String getHosname() {
            return hosname;
        }

        public void setHosname(String hosname) {
            this.hosname = hosname;
        }

        public BaseEntity getBase() {
            return base;
        }

        public void setBase(BaseEntity base) {
            this.base = base;
        }

        public DetailEntity getDetail() {
            return detail;
        }

        public void setDetail(DetailEntity detail) {
            this.detail = detail;
        }

        public DyncEntity getDync() {
            return dync;
        }

        public void setDync(DyncEntity dync) {
            this.dync = dync;
        }

        public StatEntity getStat() {
            return stat;
        }

        public void setStat(StatEntity stat) {
            this.stat = stat;
        }

        public YtxsubaccountEntity getYtxsubaccount() {
            return ytxsubaccount;
        }

        public void setYtxsubaccount(YtxsubaccountEntity ytxsubaccount) {
            this.ytxsubaccount = ytxsubaccount;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getChannel_id() {
            return channel_id;
        }

        public void setChannel_id(String channel_id) {
            this.channel_id = channel_id;
        }

        public String getDevice_type() {
            return device_type;
        }

        public void setDevice_type(String device_type) {
            this.device_type = device_type;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getImflag() {
            return imflag;
        }

        public void setImflag(String imflag) {
            this.imflag = imflag;
        }

        public String getNoticeflag() {
            return noticeflag;
        }

        public void setNoticeflag(String noticeflag) {
            this.noticeflag = noticeflag;
        }

        public String getLastvisittime() {
            return lastvisittime;
        }

        public void setLastvisittime(String lastvisittime) {
            this.lastvisittime = lastvisittime;
        }

        public String getAudit() {
            return audit;
        }

        public void setAudit(String audit) {
            this.audit = audit;
        }

        public String getOpen() {
            return open;
        }

        public void setOpen(String open) {
            this.open = open;
        }

        public String getDefaultgroup() {
            return defaultgroup;
        }

        public void setDefaultgroup(String defaultgroup) {
            this.defaultgroup = defaultgroup;
        }

        public String getIsregister() {
            return isregister;
        }

        public void setIsregister(String isregister) {
            this.isregister = isregister;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public PstatEntity getPstat() {
            return pstat;
        }

        public void setPstat(PstatEntity pstat) {
            this.pstat = pstat;
        }

        public String getUpdcount() {
            return updcount;
        }

        public void setUpdcount(String updcount) {
            this.updcount = updcount;
        }

        public String getPstatcount() {
            return pstatcount;
        }

        public void setPstatcount(String pstatcount) {
            this.pstatcount = pstatcount;
        }

        public AuthEntity getAuth() {
            return auth;
        }

        public void setAuth(AuthEntity auth) {
            this.auth = auth;
        }

        public String getIsexpert() {
            return isexpert;
        }

        public void setIsexpert(String isexpert) {
            this.isexpert = isexpert;
        }

        public List<GroupsEntity> getGroups() {
            return groups;
        }

        public void setGroups(List<GroupsEntity> groups) {
            this.groups = groups;
        }

        public List<DgroupsEntity> getDgroups() {
            return dgroups;
        }

        public void setDgroups(List<DgroupsEntity> dgroups) {
            this.dgroups = dgroups;
        }

        public List<UgroupsEntity> getUgroups() {
            return ugroups;
        }

        public void setUgroups(List<UgroupsEntity> ugroups) {
            this.ugroups = ugroups;
        }

        public static class BaseEntity {

            private String title_id;
            private String title;
            private String teachtitle;
            private String titleex;
            private String good;
            private String experience;
            private String isOnlineConsultation;
            private String teachexperience;

            public String getTitle_id() {
                return title_id;
            }

            public void setTitle_id(String title_id) {
                this.title_id = title_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTeachtitle() {
                return teachtitle;
            }

            public void setTeachtitle(String teachtitle) {
                this.teachtitle = teachtitle;
            }

            public String getTitleex() {
                return titleex;
            }

            public void setTitleex(String titleex) {
                this.titleex = titleex;
            }

            public String getGood() {
                return good;
            }

            public void setGood(String good) {
                this.good = good;
            }

            public String getExperience() {
                return experience;
            }

            public void setExperience(String experience) {
                this.experience = experience;
            }

            public String getIsOnlineConsultation() {
                return isOnlineConsultation;
            }

            public void setIsOnlineConsultation(String isOnlineConsultation) {
                this.isOnlineConsultation = isOnlineConsultation;
            }

            public String getTeachexperience() {
                return teachexperience;
            }

            public void setTeachexperience(String teachexperience) {
                this.teachexperience = teachexperience;
            }
        }

        public static class DetailEntity {
        }

        public static class DyncEntity {
            /**
             * nid : 0
             * content :
             */

            private PublicnoticeEntity publicnotice;
            /**
             * vid : 0
             * content :
             */

            private VisittipEntity visittip;
            /**
             * sid : 0
             * content :
             */

            private StopdiagnosistipEntity stopdiagnosistip;

            public PublicnoticeEntity getPublicnotice() {
                return publicnotice;
            }

            public void setPublicnotice(PublicnoticeEntity publicnotice) {
                this.publicnotice = publicnotice;
            }

            public VisittipEntity getVisittip() {
                return visittip;
            }

            public void setVisittip(VisittipEntity visittip) {
                this.visittip = visittip;
            }

            public StopdiagnosistipEntity getStopdiagnosistip() {
                return stopdiagnosistip;
            }

            public void setStopdiagnosistip(StopdiagnosistipEntity stopdiagnosistip) {
                this.stopdiagnosistip = stopdiagnosistip;
            }

            public static class PublicnoticeEntity {
                private String nid;
                private String content;

                public String getNid() {
                    return nid;
                }

                public void setNid(String nid) {
                    this.nid = nid;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }
            }

            public static class VisittipEntity {
                private String vid;
                private String content;

                public String getVid() {
                    return vid;
                }

                public void setVid(String vid) {
                    this.vid = vid;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }
            }

            public static class StopdiagnosistipEntity {
                private String sid;
                private String content;

                public String getSid() {
                    return sid;
                }

                public void setSid(String sid) {
                    this.sid = sid;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }
            }
        }

        public static class StatEntity {
            private String pfollowers;
            private String pfollowings;
            private String followers;
            private String followings;
            private String topics;
            private String suggests;

            public String getPfollowers() {
                return pfollowers;
            }

            public void setPfollowers(String pfollowers) {
                this.pfollowers = pfollowers;
            }

            public String getPfollowings() {
                return pfollowings;
            }

            public void setPfollowings(String pfollowings) {
                this.pfollowings = pfollowings;
            }

            public String getFollowers() {
                return followers;
            }

            public void setFollowers(String followers) {
                this.followers = followers;
            }

            public String getFollowings() {
                return followings;
            }

            public void setFollowings(String followings) {
                this.followings = followings;
            }

            public String getTopics() {
                return topics;
            }

            public void setTopics(String topics) {
                this.topics = topics;
            }

            public String getSuggests() {
                return suggests;
            }

            public void setSuggests(String suggests) {
                this.suggests = suggests;
            }
        }

        public static class YtxsubaccountEntity {
            private String subaccountsid;
            private String subtoken;
            private String datecreated;
            private String voipaccount;
            private String voippwd;

            public String getSubaccountsid() {
                return subaccountsid;
            }

            public void setSubaccountsid(String subaccountsid) {
                this.subaccountsid = subaccountsid;
            }

            public String getSubtoken() {
                return subtoken;
            }

            public void setSubtoken(String subtoken) {
                this.subtoken = subtoken;
            }

            public String getDatecreated() {
                return datecreated;
            }

            public void setDatecreated(String datecreated) {
                this.datecreated = datecreated;
            }

            public String getVoipaccount() {
                return voipaccount;
            }

            public void setVoipaccount(String voipaccount) {
                this.voipaccount = voipaccount;
            }

            public String getVoippwd() {
                return voippwd;
            }

            public void setVoippwd(String voippwd) {
                this.voippwd = voippwd;
            }
        }

        public static class PstatEntity {
            /**
             * nums : 1
             * name : 脂肪肝
             * alias : 脂肪肝
             */

            private Object diag;
            /**
             * nums : 5
             * name : 在院
             * alias : 在院
             */

            private Object status;
            /**
             * nums : 5
             * name : null
             * alias : null
             */

            private Object region;

            public Object getDiag() {
                return diag;
            }

            public void setDiag(Object diag) {
                this.diag = diag;
            }

            public Object getStatus() {
                return status;
            }

            public void setStatus(Object status) {
                this.status = status;
            }

            public Object getRegion() {
                return region;
            }

            public void setRegion(Object region) {
                this.region = region;
            }

            public static class DiagEntity {
                private String nums;
                private String name;
                private String alias;

                public String getNums() {
                    return nums;
                }

                public void setNums(String nums) {
                    this.nums = nums;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getAlias() {
                    return alias;
                }

                public void setAlias(String alias) {
                    this.alias = alias;
                }
            }

            public static class StatusEntity {
                private String nums;
                private String name;
                private String alias;

                public String getNums() {
                    return nums;
                }

                public void setNums(String nums) {
                    this.nums = nums;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getAlias() {
                    return alias;
                }

                public void setAlias(String alias) {
                    this.alias = alias;
                }
            }

            public static class RegionEntity {
                private String nums;
                private String name;
                private String alias;

                public String getNums() {
                    return nums;
                }

                public void setNums(String nums) {
                    this.nums = nums;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getAlias() {
                    return alias;
                }

                public void setAlias(String alias) {
                    this.alias = alias;
                }
            }
        }

        public static class AuthEntity {
            private String browse;

            public String getBrowse() {
                return browse;
            }

            public void setBrowse(String browse) {
                this.browse = browse;
            }
        }

        public static class GroupsEntity {
            private String groupname;
            private String value;
            private String issys;
            private String isnochange;
            /**
             * groupname : 14岁以下
             * value : 0-14
             * issys : 0
             * colname : age
             * isnochange : 0
             */

            private List<ChildgroupsEntity> childgroups;

            public String getGroupname() {
                return groupname;
            }

            public void setGroupname(String groupname) {
                this.groupname = groupname;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getIssys() {
                return issys;
            }

            public void setIssys(String issys) {
                this.issys = issys;
            }

            public String getIsnochange() {
                return isnochange;
            }

            public void setIsnochange(String isnochange) {
                this.isnochange = isnochange;
            }

            public List<ChildgroupsEntity> getChildgroups() {
                return childgroups;
            }

            public void setChildgroups(List<ChildgroupsEntity> childgroups) {
                this.childgroups = childgroups;
            }

            public static class ChildgroupsEntity {
                private String groupname;
                private String value;
                private String issys;
                private String colname;
                private String isnochange;

                public String getGroupname() {
                    return groupname;
                }

                public void setGroupname(String groupname) {
                    this.groupname = groupname;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }

                public String getIssys() {
                    return issys;
                }

                public void setIssys(String issys) {
                    this.issys = issys;
                }

                public String getColname() {
                    return colname;
                }

                public void setColname(String colname) {
                    this.colname = colname;
                }

                public String getIsnochange() {
                    return isnochange;
                }

                public void setIsnochange(String isnochange) {
                    this.isnochange = isnochange;
                }
            }
        }

        public static class DgroupsEntity {
            private String groupname;
            private String value;
            private String issys;
            private String isnochange;
            /**
             * groupname : 同科
             * value : 同科
             * issys : 1
             * isnochange : 0
             */

            private List<ChildgroupsEntity> childgroups;

            public String getGroupname() {
                return groupname;
            }

            public void setGroupname(String groupname) {
                this.groupname = groupname;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getIssys() {
                return issys;
            }

            public void setIssys(String issys) {
                this.issys = issys;
            }

            public String getIsnochange() {
                return isnochange;
            }

            public void setIsnochange(String isnochange) {
                this.isnochange = isnochange;
            }

            public List<ChildgroupsEntity> getChildgroups() {
                return childgroups;
            }

            public void setChildgroups(List<ChildgroupsEntity> childgroups) {
                this.childgroups = childgroups;
            }

            public static class ChildgroupsEntity {
                private String groupname;
                private String value;
                private String issys;
                private String isnochange;

                public String getGroupname() {
                    return groupname;
                }

                public void setGroupname(String groupname) {
                    this.groupname = groupname;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }

                public String getIssys() {
                    return issys;
                }

                public void setIssys(String issys) {
                    this.issys = issys;
                }

                public String getIsnochange() {
                    return isnochange;
                }

                public void setIsnochange(String isnochange) {
                    this.isnochange = isnochange;
                }
            }
        }

        public static class UgroupsEntity {
            private String groupname;
            private String value;
            private String issys;
            private String isnochange;
            /**
             * groupname : 我的医生
             * value : 我的医生
             * issys : 1
             * isnochange : 0
             */

            private List<ChildgroupsEntity> childgroups;

            public String getGroupname() {
                return groupname;
            }

            public void setGroupname(String groupname) {
                this.groupname = groupname;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getIssys() {
                return issys;
            }

            public void setIssys(String issys) {
                this.issys = issys;
            }

            public String getIsnochange() {
                return isnochange;
            }

            public void setIsnochange(String isnochange) {
                this.isnochange = isnochange;
            }

            public List<ChildgroupsEntity> getChildgroups() {
                return childgroups;
            }

            public void setChildgroups(List<ChildgroupsEntity> childgroups) {
                this.childgroups = childgroups;
            }

            public static class ChildgroupsEntity {
                private String groupname;
                private String value;
                private String issys;
                private String isnochange;

                public String getGroupname() {
                    return groupname;
                }

                public void setGroupname(String groupname) {
                    this.groupname = groupname;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }

                public String getIssys() {
                    return issys;
                }

                public void setIssys(String issys) {
                    this.issys = issys;
                }

                public String getIsnochange() {
                    return isnochange;
                }

                public void setIsnochange(String isnochange) {
                    this.isnochange = isnochange;
                }
            }
        }
    }

    public static class ColGLEntity {
        private String colname;
        private String colvalue;

        public ColGLEntity(String colname, String colvalue) {
            this.colname = colname;
            this.colvalue = colvalue;
        }

        public String getColname() {
            return colname;
        }

        public void setColname(String colname) {
            this.colname = colname;
        }

        public String getColvalue() {
            return colvalue;
        }

        public void setColvalue(String colvalue) {
            this.colvalue = colvalue;
        }
    }
}
