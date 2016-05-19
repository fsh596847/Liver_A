package com.android.doctor.model;

import java.util.List;

/**
 * Created by Yong on 2016/5/16.
 */
public class LaborArchList {

    private List<LaborArchEntity> healthrecords;

    public List<LaborArchEntity> getHealthrecords() {
        return healthrecords;
    }

    public void setHealthrecords(List<LaborArchEntity> healthrecords) {
        this.healthrecords = healthrecords;
    }

    public static class LaborArchEntity {
        private String rid;
        private int pid;
        private String card;
        private int recordtype;
        private int datastyle;
        private String begindate;
        private String enddate;
        private String prid;
        private String title;
        private String subtitle;
        private int hosid;
        private String hosname;
        private String doctor;
        private String deptname;
        private String content;
        private int createuid;
        private long createdatetime;
        private String source;
        private int visitid;

        private DetailEntity detail;
        private String _id;
        private List<Integer> recordsubtype;

        public String getRid() {
            return rid;
        }

        public void setRid(String rid) {
            this.rid = rid;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }

        public int getRecordtype() {
            return recordtype;
        }

        public void setRecordtype(int recordtype) {
            this.recordtype = recordtype;
        }

        public int getDatastyle() {
            return datastyle;
        }

        public void setDatastyle(int datastyle) {
            this.datastyle = datastyle;
        }

        public String getBegindate() {
            return begindate;
        }

        public void setBegindate(String begindate) {
            this.begindate = begindate;
        }

        public String getEnddate() {
            return enddate;
        }

        public void setEnddate(String enddate) {
            this.enddate = enddate;
        }

        public String getPrid() {
            return prid;
        }

        public void setPrid(String prid) {
            this.prid = prid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
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

        public String getDoctor() {
            return doctor;
        }

        public void setDoctor(String doctor) {
            this.doctor = doctor;
        }

        public String getDeptname() {
            return deptname;
        }

        public void setDeptname(String deptname) {
            this.deptname = deptname;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getCreateuid() {
            return createuid;
        }

        public void setCreateuid(int createuid) {
            this.createuid = createuid;
        }

        public long getCreatedatetime() {
            return createdatetime;
        }

        public void setCreatedatetime(long createdatetime) {
            this.createdatetime = createdatetime;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public int getVisitid() {
            return visitid;
        }

        public void setVisitid(int visitid) {
            this.visitid = visitid;
        }

        public DetailEntity getDetail() {
            return detail;
        }

        public void setDetail(DetailEntity detail) {
            this.detail = detail;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public List<Integer> getRecordsubtype() {
            return recordsubtype;
        }

        public void setRecordsubtype(List<Integer> recordsubtype) {
            this.recordsubtype = recordsubtype;
        }

        public static class DetailEntity {
            private String testclassnum;
            private String testsample;
            /**
             * DetailId : 2486449
             * code : 4482
             * name : 白细胞计数
             * eng : WBC
             * value : 2.9
             * result : ↓
             * unit : ×10^9/L
             * range : 3.5--9.5
             * testmeans :
             */

            private List<LisEntity> lis;

            public String getTestclassnum() {
                return testclassnum;
            }

            public void setTestclassnum(String testclassnum) {
                this.testclassnum = testclassnum;
            }

            public String getTestsample() {
                return testsample;
            }

            public void setTestsample(String testsample) {
                this.testsample = testsample;
            }

            public List<LisEntity> getLis() {
                return lis;
            }

            public void setLis(List<LisEntity> lis) {
                this.lis = lis;
            }

            public static class LisEntity {
                private int DetailId;
                private int code;
                private String name;
                private String eng;
                private String value;
                private String result;
                private String unit;
                private String range;
                private String testmeans;

                public int getDetailId() {
                    return DetailId;
                }

                public void setDetailId(int DetailId) {
                    this.DetailId = DetailId;
                }

                public int getCode() {
                    return code;
                }

                public void setCode(int code) {
                    this.code = code;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getEng() {
                    return eng;
                }

                public void setEng(String eng) {
                    this.eng = eng;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }

                public String getResult() {
                    return result;
                }

                public void setResult(String result) {
                    this.result = result;
                }

                public String getUnit() {
                    return unit;
                }

                public void setUnit(String unit) {
                    this.unit = unit;
                }

                public String getRange() {
                    return range;
                }

                public void setRange(String range) {
                    this.range = range;
                }

                public String getTestmeans() {
                    return testmeans;
                }

                public void setTestmeans(String testmeans) {
                    this.testmeans = testmeans;
                }
            }
        }
    }
}
