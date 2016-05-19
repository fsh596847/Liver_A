package com.android.doctor.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yong on 2016/4/15.
 */
public class PlanDeta {


    private List<PlanDetaEntity> data;

    public List<PlanDetaEntity> getData() {
        return data;
    }

    public void setData(List<PlanDetaEntity> data) {
        this.data = data;
    }

    public static class PlanDetaEntity implements Parcelable {
        private String _id;
        private int pid;
        private String tplid;
        private int code;
        private String name;
        private String desc;
        private int isadd;
        private String show;
        private String hint;
        private int wholook;
        private String uuid;
        private int uid;
        private long createtime;
        private int ischange;

        private List<DefaultplanEntity> defaultplan;
        /**
         * uuid : fb27cd50-52ae-4093-afb6-a7b59c8b4c4e
         * defaultshow : 1
         * isedit : 1
         * code : 10000
         * defaultplan : [{"end":0,"begin":0,"beginunit":"","endunit":"","freq":0,"frequnit":"","beginprefix":""}]
         * pcode : 1
         * wholook : 1
         * hint :
         * plan : 0
         * execplan : [{"end":0,"begin":0,"beginunit":"","endunit":"","freq":0,"frequnit":"","beginprefix":""}]
         * inputtype : label
         * name : 饮食
         * content : 禁酒。低脂、优质蛋白、富含维生素的饮食，限制蛋白质摄入量。
         * ischange : 0
         */

        private List<ItemsEntity> items;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public String getTplid() {
            return tplid;
        }

        public void setTplid(String tplid) {
            this.tplid = tplid;
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

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getIsadd() {
            return isadd;
        }

        public void setIsadd(int isadd) {
            this.isadd = isadd;
        }

        public String getShow() {
            return show;
        }

        public void setShow(String show) {
            this.show = show;
        }

        public String getHint() {
            return hint;
        }

        public void setHint(String hint) {
            this.hint = hint;
        }

        public int getWholook() {
            return wholook;
        }

        public void setWholook(int wholook) {
            this.wholook = wholook;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }

        public int getIschange() {
            return ischange;
        }

        public void setIschange(int ischange) {
            this.ischange = ischange;
        }

        public List<DefaultplanEntity> getDefaultplan() {
            return defaultplan;
        }

        public void setDefaultplan(List<DefaultplanEntity> defaultplan) {
            this.defaultplan = defaultplan;
        }

        public List<ItemsEntity> getItems() {
            return items;
        }

        public void setItems(List<ItemsEntity> items) {
            this.items = items;
        }

        public static class DefaultplanEntity implements Parcelable {
            private int end;
            private int begin;
            private String beginunit;
            private String endunit;
            private int freq;
            private String frequnit;
            private String beginprefix;

            public int getEnd() {
                return end;
            }

            public void setEnd(int end) {
                this.end = end;
            }

            public int getBegin() {
                return begin;
            }

            public void setBegin(int begin) {
                this.begin = begin;
            }

            public String getBeginunit() {
                return beginunit;
            }

            public void setBeginunit(String beginunit) {
                this.beginunit = beginunit;
            }

            public String getEndunit() {
                return endunit;
            }

            public void setEndunit(String endunit) {
                this.endunit = endunit;
            }

            public int getFreq() {
                return freq;
            }

            public void setFreq(int freq) {
                this.freq = freq;
            }

            public String getFrequnit() {
                return frequnit;
            }

            public void setFrequnit(String frequnit) {
                this.frequnit = frequnit;
            }

            public String getBeginprefix() {
                return beginprefix;
            }

            public void setBeginprefix(String beginprefix) {
                this.beginprefix = beginprefix;
            }


            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.end);
                dest.writeInt(this.begin);
                dest.writeString(this.beginunit);
                dest.writeString(this.endunit);
                dest.writeInt(this.freq);
                dest.writeString(this.frequnit);
                dest.writeString(this.beginprefix);
            }

            public DefaultplanEntity() {
            }

            private DefaultplanEntity(Parcel in) {
                this.end = in.readInt();
                this.begin = in.readInt();
                this.beginunit = in.readString();
                this.endunit = in.readString();
                this.freq = in.readInt();
                this.frequnit = in.readString();
                this.beginprefix = in.readString();
            }

            public static final Parcelable.Creator<DefaultplanEntity> CREATOR = new Parcelable.Creator<DefaultplanEntity>() {
                public DefaultplanEntity createFromParcel(Parcel source) {
                    return new DefaultplanEntity(source);
                }

                public DefaultplanEntity[] newArray(int size) {
                    return new DefaultplanEntity[size];
                }
            };
        }

        public static class ItemsEntity implements Parcelable {
            private String uuid;
            private int defaultshow;
            private int isedit;
            private int code;
            private int pcode;
            private int wholook;
            private String hint;
            private int plan;
            private String inputtype;
            private String name;
            private String content;
            private int ischange;

            /**
             * 用药医嘱
             */
            private int end;
            private String endunit;
            private String yf;
            private String yl;
            /**
             * end : 0
             * begin : 0
             * beginunit :
             * endunit :
             * freq : 0
             * frequnit :
             * beginprefix :
             */

            private List<DefaultplanEntity> defaultplan;
            /**
             * end : 0
             * begin : 0
             * beginunit :
             * endunit :
             * freq : 0
             * frequnit :
             * beginprefix :
             */

            private List<ExecplanEntity> execplan;

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }

            public int getDefaultshow() {
                return defaultshow;
            }

            public void setDefaultshow(int defaultshow) {
                this.defaultshow = defaultshow;
            }

            public int getIsedit() {
                return isedit;
            }

            public void setIsedit(int isedit) {
                this.isedit = isedit;
            }

            public int getCode() {
                return code;
            }

            public void setCode(int code) {
                this.code = code;
            }

            public int getPcode() {
                return pcode;
            }

            public void setPcode(int pcode) {
                this.pcode = pcode;
            }

            public int getWholook() {
                return wholook;
            }

            public void setWholook(int wholook) {
                this.wholook = wholook;
            }

            public String getHint() {
                return hint;
            }

            public void setHint(String hint) {
                this.hint = hint;
            }

            public int getPlan() {
                return plan;
            }

            public void setPlan(int plan) {
                this.plan = plan;
            }

            public String getInputtype() {
                return inputtype;
            }

            public void setInputtype(String inputtype) {
                this.inputtype = inputtype;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getIschange() {
                return ischange;
            }

            public void setIschange(int ischange) {
                this.ischange = ischange;
            }

            public List<DefaultplanEntity> getDefaultplan() {
                return defaultplan;
            }

            public void setDefaultplan(List<DefaultplanEntity> defaultplan) {
                this.defaultplan = defaultplan;
            }

            public List<ExecplanEntity> getExecplan() {
                return execplan;
            }

            public void setExecplan(List<ExecplanEntity> execplan) {
                this.execplan = execplan;
            }

            public int getEnd() {
                return end;
            }

            public void setEnd(int end) {
                this.end = end;
            }

            public String getYl() {
                return yl;
            }

            public void setYl(String yl) {
                this.yl = yl;
            }

            public String getYf() {
                return yf;
            }

            public void setYf(String yf) {
                this.yf = yf;
            }

            public String getEndunit() {
                return endunit;
            }

            public void setEndunit(String endunit) {
                this.endunit = endunit;
            }


            public static class DefaultplanEntity implements Parcelable {
                private int end;
                private int begin;
                private String beginunit;
                private String endunit;
                private int freq;
                private String frequnit;
                private String beginprefix;

                public int getEnd() {
                    return end;
                }

                public void setEnd(int end) {
                    this.end = end;
                }

                public int getBegin() {
                    return begin;
                }

                public void setBegin(int begin) {
                    this.begin = begin;
                }

                public String getBeginunit() {
                    return beginunit;
                }

                public void setBeginunit(String beginunit) {
                    this.beginunit = beginunit;
                }

                public String getEndunit() {
                    return endunit;
                }

                public void setEndunit(String endunit) {
                    this.endunit = endunit;
                }

                public int getFreq() {
                    return freq;
                }

                public void setFreq(int freq) {
                    this.freq = freq;
                }

                public String getFrequnit() {
                    return frequnit;
                }

                public void setFrequnit(String frequnit) {
                    this.frequnit = frequnit;
                }

                public String getBeginprefix() {
                    return beginprefix;
                }

                public void setBeginprefix(String beginprefix) {
                    this.beginprefix = beginprefix;
                }


                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeInt(this.end);
                    dest.writeInt(this.begin);
                    dest.writeString(this.beginunit);
                    dest.writeString(this.endunit);
                    dest.writeInt(this.freq);
                    dest.writeString(this.frequnit);
                    dest.writeString(this.beginprefix);
                }

                public DefaultplanEntity() {
                    setFreq(1);
                    setFrequnit("月");
                    setBegin(1);
                    setBeginunit("月");
                    setBeginprefix("出院后");
                    setEnd(0);
                    setEndunit("");
                }

                private DefaultplanEntity(Parcel in) {
                    this.end = in.readInt();
                    this.begin = in.readInt();
                    this.beginunit = in.readString();
                    this.endunit = in.readString();
                    this.freq = in.readInt();
                    this.frequnit = in.readString();
                    this.beginprefix = in.readString();
                }

                public static final Parcelable.Creator<DefaultplanEntity> CREATOR = new Parcelable.Creator<DefaultplanEntity>() {
                    public DefaultplanEntity createFromParcel(Parcel source) {
                        return new DefaultplanEntity(source);
                    }

                    public DefaultplanEntity[] newArray(int size) {
                        return new DefaultplanEntity[size];
                    }
                };
            }

            public static class ExecplanEntity implements Parcelable {
                private int end;
                private int begin;
                private String beginunit;
                private String endunit;
                private int freq;
                private String frequnit;
                private String beginprefix;

                public int getEnd() {
                    return end;
                }

                public void setEnd(int end) {
                    this.end = end;
                }

                public int getBegin() {
                    return begin;
                }

                public void setBegin(int begin) {
                    this.begin = begin;
                }

                public String getBeginunit() {
                    return beginunit;
                }

                public void setBeginunit(String beginunit) {
                    this.beginunit = beginunit;
                }

                public String getEndunit() {
                    return endunit;
                }

                public void setEndunit(String endunit) {
                    this.endunit = endunit;
                }

                public int getFreq() {
                    return freq;
                }

                public void setFreq(int freq) {
                    this.freq = freq;
                }

                public String getFrequnit() {
                    return frequnit;
                }

                public void setFrequnit(String frequnit) {
                    this.frequnit = frequnit;
                }

                public String getBeginprefix() {
                    return beginprefix;
                }

                public void setBeginprefix(String beginprefix) {
                    this.beginprefix = beginprefix;
                }


                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeInt(this.end);
                    dest.writeInt(this.begin);
                    dest.writeString(this.beginunit);
                    dest.writeString(this.endunit);
                    dest.writeInt(this.freq);
                    dest.writeString(this.frequnit);
                    dest.writeString(this.beginprefix);
                }

                public ExecplanEntity() {
                    setFreq(1);
                    setFrequnit("月");
                    setBegin(1);
                    setBeginunit("月");
                    setBeginprefix("出院后");
                    setEnd(0);
                    setEndunit("");
                }

                private ExecplanEntity(Parcel in) {
                    this.end = in.readInt();
                    this.begin = in.readInt();
                    this.beginunit = in.readString();
                    this.endunit = in.readString();
                    this.freq = in.readInt();
                    this.frequnit = in.readString();
                    this.beginprefix = in.readString();
                }

                public static final Parcelable.Creator<ExecplanEntity> CREATOR = new Parcelable.Creator<ExecplanEntity>() {
                    public ExecplanEntity createFromParcel(Parcel source) {
                        return new ExecplanEntity(source);
                    }

                    public ExecplanEntity[] newArray(int size) {
                        return new ExecplanEntity[size];
                    }
                };
            }


            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.uuid);
                dest.writeInt(this.defaultshow);
                dest.writeInt(this.isedit);
                dest.writeInt(this.code);
                dest.writeInt(this.pcode);
                dest.writeInt(this.wholook);
                dest.writeString(this.hint);
                dest.writeInt(this.plan);
                dest.writeString(this.inputtype);
                dest.writeString(this.name);
                dest.writeString(this.content);
                dest.writeInt(this.ischange);
                dest.writeTypedList(defaultplan);
                dest.writeTypedList(execplan);

                dest.writeInt(this.end);
                dest.writeString(this.endunit);
                dest.writeString(this.yf);
                dest.writeString(this.yl);
            }

            public ItemsEntity() {
                DefaultplanEntity dp = new DefaultplanEntity();
                List<DefaultplanEntity> dl = new ArrayList<>();
                dl.add(dp);
                ExecplanEntity ep = new ExecplanEntity();
                List<ExecplanEntity> el = new ArrayList<>();
                el.add(ep);
                setDefaultplan(dl);
                setExecplan(el);
            }

            private ItemsEntity(Parcel in) {
                this.uuid = in.readString();
                this.defaultshow = in.readInt();
                this.isedit = in.readInt();
                this.code = in.readInt();
                this.pcode = in.readInt();
                this.wholook = in.readInt();
                this.hint = in.readString();
                this.plan = in.readInt();
                this.inputtype = in.readString();
                this.name = in.readString();
                this.content = in.readString();
                this.ischange = in.readInt();
                this.defaultplan = new ArrayList<>();
                this.execplan = new ArrayList<>();
                in.readTypedList(this.defaultplan, DefaultplanEntity.CREATOR);
                in.readTypedList(this.execplan, ExecplanEntity.CREATOR);

                this.end = in.readInt();
                this.endunit = in.readString();
                this.yf = in.readString();
                this.yl = in.readString();
            }

            public static final Parcelable.Creator<ItemsEntity> CREATOR = new Parcelable.Creator<ItemsEntity>() {
                public ItemsEntity createFromParcel(Parcel source) {
                    return new ItemsEntity(source);
                }

                public ItemsEntity[] newArray(int size) {
                    return new ItemsEntity[size];
                }
            };
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this._id);
            dest.writeInt(this.pid);
            dest.writeString(this.tplid);
            dest.writeInt(this.code);
            dest.writeString(this.name);
            dest.writeString(this.desc);
            dest.writeInt(this.isadd);
            dest.writeString(this.show);
            dest.writeString(this.hint);
            dest.writeInt(this.wholook);
            dest.writeString(this.uuid);
            dest.writeInt(this.uid);
            dest.writeLong(this.createtime);
            dest.writeInt(this.ischange);
            dest.writeTypedList(defaultplan);
            dest.writeTypedList(items);
        }

        public PlanDetaEntity() {
        }

        private PlanDetaEntity(Parcel in) {
            this._id = in.readString();
            this.pid = in.readInt();
            this.tplid = in.readString();
            this.code = in.readInt();
            this.name = in.readString();
            this.desc = in.readString();
            this.isadd = in.readInt();
            this.show = in.readString();
            this.hint = in.readString();
            this.wholook = in.readInt();
            this.uuid = in.readString();
            this.uid = in.readInt();
            this.createtime = in.readLong();
            this.ischange = in.readInt();
            this.defaultplan = new ArrayList<>();
            this.items = new ArrayList<>();
            in.readTypedList(this.defaultplan, DefaultplanEntity.CREATOR);
            in.readTypedList(this.items, ItemsEntity.CREATOR);
        }

        public static final Parcelable.Creator<PlanDetaEntity> CREATOR = new Parcelable.Creator<PlanDetaEntity>() {
            public PlanDetaEntity createFromParcel(Parcel source) {
                return new PlanDetaEntity(source);
            }

            public PlanDetaEntity[] newArray(int size) {
                return new PlanDetaEntity[size];
            }
        };
    }
}
