package com.android.doctor.model;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable{
	/**
	 * _id : 5662615cacd45d24200bff29
	 * no_staff :
	 * duid :
	 * username :
	 * mobilephone :
	 * nickname :
	 * password :
	 * pycode :
	 * duuid :
	 * deptid :
	 * deptname :
	 * hosid :
	 * hosname :
	 * groups :
	 * dgroups :
	 * ugroups :
	 * base :
	 * detail :
	 * dync :
	 * stat :
	 * ytxsubaccount :
	 * gender :
	 * imgurl :
	 * device_type :
	 * source :
	 * imflag :
	 * noticeflag :
	 * lastvisittime :
	 * audit :
	 * open :
	 * defaultgroup :
	 * isregister :
	 * createtime :
	 * pstat :
	 * updcount :
	 * pstatcount :
	 * auth :
	 * isexpert :
	 */

	private UserEntity user;
	/**
	 * user :
	 * token :
	 */

	private String token;

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public static class UserEntity implements Serializable{
		private String _id;
		private int no_staff;
		private int duid;
		private String username;
		private String mobilephone;
		private String nickname;
		private String password;
		private String pycode;
		private String duuid;
		private String deptid;
		private String deptname;
		private int hosid;
		private String hosname;
		/**
		 * title : 医师
		 * title_id : 1004
		 * titleex : 特级教授
		 * teachtitle : 特级教授
		 * good : 擅长测试数据
		 * experience : 职业经历测试数据
		 * isOnlineConsultation : 0
		 * isPhoneConsultation : 0
		 * isReservationPlus : 0
		 * teachexperience : 教育经历测试数据
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
		 * followers : 0
		 * followings : 0
		 * topics : 0
		 * suggests : 0
		 */

		private StatEntity stat;
		/**
		 * subaccountsid : 0fed09ef006c11e6bb9bac853d9f54f2
		 * subtoken : 45be01a7733d47677a160b5a21cad803
		 * datecreated : 2016-04-12 13:04:40
		 * voipaccount : 8001053600000131
		 * voippwd : 10PZgd5E
		 */

		private YtxsubaccountEntity ytxsubaccount;
		private int gender;
		private String imgurl;
		private String device_type;
		private String source;
		private int imflag;
		private int noticeflag;
		private long lastvisittime;
		private int audit;
		private int open;
		private String defaultgroup;
		private int isregister;
		private String createtime;
		private PstatEntity pstat;
		private int updcount;
		private int pstatcount;
		/**
		 * browse : 2
		 */

		private AuthEntity auth;
		private int isexpert;
		/**
		 * groupname : 年龄
		 * value : 年龄
		 * issys : 0
		 * isnochange : 0
		 * childgroups :
		 */

		private List<GroupsEntity> groups;
		/**
		 * groupname : 本院
		 * value : 本院
		 * issys : 0
		 * isnochange : 0
		 * childgroups :
		 */

		private List<DgroupsEntity> dgroups;
		/**
		 * groupname : 医生
		 * value : 医生
		 * issys : 0
		 * isnochange : 0
		 * childgroups :
		 */

		private List<UgroupsEntity> ugroups;

		public String get_id() {
			return _id;
		}

		public void set_id(String _id) {
			this._id = _id;
		}

		public int getNo_staff() {
			return no_staff;
		}

		public void setNo_staff(int no_staff) {
			this.no_staff = no_staff;
		}

		public int getDuid() {
			return duid;
		}

		public void setDuid(int duid) {
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

		public int getHosid() {
			return hosid;
		}

		public void setHosid(int hosid) {
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

		public int getGender() {
			return gender;
		}

		public void setGender(int gender) {
			this.gender = gender;
		}

		public String getImgurl() {
			return imgurl;
		}

		public void setImgurl(String imgurl) {
			this.imgurl = imgurl;
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

		public int getImflag() {
			return imflag;
		}

		public void setImflag(int imflag) {
			this.imflag = imflag;
		}

		public int getNoticeflag() {
			return noticeflag;
		}

		public void setNoticeflag(int noticeflag) {
			this.noticeflag = noticeflag;
		}

		public long getLastvisittime() {
			return lastvisittime;
		}

		public void setLastvisittime(long lastvisittime) {
			this.lastvisittime = lastvisittime;
		}

		public int getAudit() {
			return audit;
		}

		public void setAudit(int audit) {
			this.audit = audit;
		}

		public int getOpen() {
			return open;
		}

		public void setOpen(int open) {
			this.open = open;
		}

		public String getDefaultgroup() {
			return defaultgroup;
		}

		public void setDefaultgroup(String defaultgroup) {
			this.defaultgroup = defaultgroup;
		}

		public int getIsregister() {
			return isregister;
		}

		public void setIsregister(int isregister) {
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

		public int getUpdcount() {
			return updcount;
		}

		public void setUpdcount(int updcount) {
			this.updcount = updcount;
		}

		public int getPstatcount() {
			return pstatcount;
		}

		public void setPstatcount(int pstatcount) {
			this.pstatcount = pstatcount;
		}

		public AuthEntity getAuth() {
			return auth;
		}

		public void setAuth(AuthEntity auth) {
			this.auth = auth;
		}

		public int getIsexpert() {
			return isexpert;
		}

		public void setIsexpert(int isexpert) {
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

		public static class BaseEntity implements Serializable{
			private String title;
			private String title_id;
			private String titleex;
			private String teachtitle;
			private String good;
			private String experience;
			private int isOnlineConsultation;
			private int isPhoneConsultation;
			private int isReservationPlus;
			private String teachexperience;

			public String getTitle() {
				return title;
			}

			public void setTitle(String title) {
				this.title = title;
			}

			public String getTitle_id() {
				return title_id;
			}

			public void setTitle_id(String title_id) {
				this.title_id = title_id;
			}

			public String getTitleex() {
				return titleex;
			}

			public void setTitleex(String titleex) {
				this.titleex = titleex;
			}

			public String getTeachtitle() {
				return teachtitle;
			}

			public void setTeachtitle(String teachtitle) {
				this.teachtitle = teachtitle;
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

			public int getIsOnlineConsultation() {
				return isOnlineConsultation;
			}

			public void setIsOnlineConsultation(int isOnlineConsultation) {
				this.isOnlineConsultation = isOnlineConsultation;
			}

			public int getIsPhoneConsultation() {
				return isPhoneConsultation;
			}

			public void setIsPhoneConsultation(int isPhoneConsultation) {
				this.isPhoneConsultation = isPhoneConsultation;
			}

			public int getIsReservationPlus() {
				return isReservationPlus;
			}

			public void setIsReservationPlus(int isReservationPlus) {
				this.isReservationPlus = isReservationPlus;
			}

			public String getTeachexperience() {
				return teachexperience;
			}

			public void setTeachexperience(String teachexperience) {
				this.teachexperience = teachexperience;
			}
		}

		public static class DetailEntity implements Serializable{
		}

		public static class DyncEntity implements Serializable{
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

			public static class PublicnoticeEntity implements Serializable{
				private int nid;
				private String content;

				public int getNid() {
					return nid;
				}

				public void setNid(int nid) {
					this.nid = nid;
				}

				public String getContent() {
					return content;
				}

				public void setContent(String content) {
					this.content = content;
				}
			}

			public static class VisittipEntity implements Serializable{
				private int vid;
				private String content;

				public int getVid() {
					return vid;
				}

				public void setVid(int vid) {
					this.vid = vid;
				}

				public String getContent() {
					return content;
				}

				public void setContent(String content) {
					this.content = content;
				}
			}

			public static class StopdiagnosistipEntity implements Serializable{
				private int sid;
				private String content;

				public int getSid() {
					return sid;
				}

				public void setSid(int sid) {
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

		public static class StatEntity implements Serializable{
			private int pfollowers;
			private int pfollowings;
			private int followers;
			private int followings;
			private int topics;
			private int suggests;

			public int getPfollowers() {
				return pfollowers;
			}

			public void setPfollowers(int pfollowers) {
				this.pfollowers = pfollowers;
			}

			public int getPfollowings() {
				return pfollowings;
			}

			public void setPfollowings(int pfollowings) {
				this.pfollowings = pfollowings;
			}

			public int getFollowers() {
				return followers;
			}

			public void setFollowers(int followers) {
				this.followers = followers;
			}

			public int getFollowings() {
				return followings;
			}

			public void setFollowings(int followings) {
				this.followings = followings;
			}

			public int getTopics() {
				return topics;
			}

			public void setTopics(int topics) {
				this.topics = topics;
			}

			public int getSuggests() {
				return suggests;
			}

			public void setSuggests(int suggests) {
				this.suggests = suggests;
			}
		}

		public static class YtxsubaccountEntity implements Serializable{
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

		public static class PstatEntity implements Serializable{
			private List<?> diag;
			private List<?> status;
			private List<?> region;

			public List<?> getDiag() {
				return diag;
			}

			public void setDiag(List<?> diag) {
				this.diag = diag;
			}

			public List<?> getStatus() {
				return status;
			}

			public void setStatus(List<?> status) {
				this.status = status;
			}

			public List<?> getRegion() {
				return region;
			}

			public void setRegion(List<?> region) {
				this.region = region;
			}
		}

		public static class AuthEntity implements Serializable{
			private int browse;

			public int getBrowse() {
				return browse;
			}

			public void setBrowse(int browse) {
				this.browse = browse;
			}
		}

		public static class GroupsEntity implements Serializable{
			private String groupname;
			private String value;
			private int issys;
			private int isnochange;
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

			public int getIssys() {
				return issys;
			}

			public void setIssys(int issys) {
				this.issys = issys;
			}

			public int getIsnochange() {
				return isnochange;
			}

			public void setIsnochange(int isnochange) {
				this.isnochange = isnochange;
			}

			public List<ChildgroupsEntity> getChildgroups() {
				return childgroups;
			}

			public void setChildgroups(List<ChildgroupsEntity> childgroups) {
				this.childgroups = childgroups;
			}

			public static class ChildgroupsEntity implements Serializable{
				private String groupname;
				private String value;
				private int issys;
				private String colname;
				private int isnochange;

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

				public int getIssys() {
					return issys;
				}

				public void setIssys(int issys) {
					this.issys = issys;
				}

				public String getColname() {
					return colname;
				}

				public void setColname(String colname) {
					this.colname = colname;
				}

				public int getIsnochange() {
					return isnochange;
				}

				public void setIsnochange(int isnochange) {
					this.isnochange = isnochange;
				}

				public String toJson() {
					Gson g = new Gson();
					return g.toJson(this);
				}
			}
		}

		public static class DgroupsEntity implements Serializable{
			private String groupname;
			private String value;
			private int issys;
			private int isnochange;
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

			public int getIssys() {
				return issys;
			}

			public void setIssys(int issys) {
				this.issys = issys;
			}

			public int getIsnochange() {
				return isnochange;
			}

			public void setIsnochange(int isnochange) {
				this.isnochange = isnochange;
			}

			public List<ChildgroupsEntity> getChildgroups() {
				return childgroups;
			}

			public void setChildgroups(List<ChildgroupsEntity> childgroups) {
				this.childgroups = childgroups;
			}

			public static class ChildgroupsEntity implements Serializable{
				private String groupname;
				private String value;
				private int issys;
				private int isnochange;

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

				public int getIssys() {
					return issys;
				}

				public void setIssys(int issys) {
					this.issys = issys;
				}

				public int getIsnochange() {
					return isnochange;
				}

				public void setIsnochange(int isnochange) {
					this.isnochange = isnochange;
				}
			}
		}

		public static class UgroupsEntity implements Serializable{
			private String groupname;
			private String value;
			private int issys;
			private int isnochange;
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

			public int getIssys() {
				return issys;
			}

			public void setIssys(int issys) {
				this.issys = issys;
			}

			public int getIsnochange() {
				return isnochange;
			}

			public void setIsnochange(int isnochange) {
				this.isnochange = isnochange;
			}

			public List<ChildgroupsEntity> getChildgroups() {
				return childgroups;
			}

			public void setChildgroups(List<ChildgroupsEntity> childgroups) {
				this.childgroups = childgroups;
			}

			public static class ChildgroupsEntity implements Serializable{
				private String groupname;
				private String value;
				private int issys;
				private int isnochange;

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

				public int getIssys() {
					return issys;
				}

				public void setIssys(int issys) {
					this.issys = issys;
				}

				public int getIsnochange() {
					return isnochange;
				}

				public void setIsnochange(int isnochange) {
					this.isnochange = isnochange;
				}
			}
		}
	}

	/**
	 *
	 */
	public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
