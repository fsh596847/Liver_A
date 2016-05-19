package com.android.doctor.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yong on 2016/4/23.
 */
public class DoctorTimeSheet {

    private TimeSheetEntity data;

    public TimeSheetEntity getData() {
        return data;
    }

    public void setData(TimeSheetEntity data) {
        this.data = data;
    }

    public static class TimeSheetEntity {
        private String _id;
        private int duid;

        @SerializedName("default")
        private DefaultEntity defaultX;


        @SerializedName("static")
        private StaticEntity staticX;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public int getDuid() {
            return duid;
        }

        public void setDuid(int duid) {
            this.duid = duid;
        }

        public DefaultEntity getDefaultX() {
            return defaultX;
        }

        public void setDefaultX(DefaultEntity defaultX) {
            this.defaultX = defaultX;
        }

        public StaticEntity getStaticX() {
            return staticX;
        }

        public void setStaticX(StaticEntity staticX) {
            this.staticX = staticX;
        }

        public static class DefaultEntity {
            private String style;
            private String alias;
            private String money;
            /**
             * code : 1
             * style : 普通会诊
             * alias : 一般会诊
             * money : 100
             * status : 1
             * remark : 需要患者亲自来到医院
             */

            private List<StylesEntity> styles;

            public String getStyle() {
                return style;
            }

            public void setStyle(String style) {
                this.style = style;
            }

            public String getAlias() {
                return alias;
            }

            public void setAlias(String alias) {
                this.alias = alias;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public List<StylesEntity> getStyles() {
                return styles;
            }

            public void setStyles(List<StylesEntity> styles) {
                this.styles = styles;
            }

            public static class StylesEntity {
                private String code;
                private String style;
                private String alias;
                private String money;
                private String status;
                private String remark;

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getStyle() {
                    return style;
                }

                public void setStyle(String style) {
                    this.style = style;
                }

                public String getAlias() {
                    return alias;
                }

                public void setAlias(String alias) {
                    this.alias = alias;
                }

                public String getMoney() {
                    return money;
                }

                public void setMoney(String money) {
                    this.money = money;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public String getRemark() {
                    return remark;
                }

                public void setRemark(String remark) {
                    this.remark = remark;
                }
            }
        }

        public static class StaticEntity {

            private W1Entity w1;

            private W2Entity w2;

            private W3Entity w3;

            private W4Entity w4;

            private W5Entity w5;

            private W6Entity w6;

            private W7Entity w7;

            public W1Entity getW1() {
                return w1;
            }

            public void setW1(W1Entity w1) {
                this.w1 = w1;
            }

            public W2Entity getW2() {
                return w2;
            }

            public void setW2(W2Entity w2) {
                this.w2 = w2;
            }

            public W3Entity getW3() {
                return w3;
            }

            public void setW3(W3Entity w3) {
                this.w3 = w3;
            }

            public W4Entity getW4() {
                return w4;
            }

            public void setW4(W4Entity w4) {
                this.w4 = w4;
            }

            public W5Entity getW5() {
                return w5;
            }

            public void setW5(W5Entity w5) {
                this.w5 = w5;
            }

            public W6Entity getW6() {
                return w6;
            }

            public void setW6(W6Entity w6) {
                this.w6 = w6;
            }

            public W7Entity getW7() {
                return w7;
            }

            public void setW7(W7Entity w7) {
                this.w7 = w7;
            }

            public List<WeekDayEntity> getWeekDayList() {
                List<WeekDayEntity> list = new ArrayList<>();
                list.add(w1.getWeekDay());
                list.add(w2.getWeekDay());
                list.add(w3.getWeekDay());
                list.add(w4.getWeekDay());
                list.add(w5.getWeekDay());
                list.add(w6.getWeekDay());
                list.add(w7.getWeekDay());
                return list;
            }

            public static class W1Entity {

                private DayTimeEntity w11;

                private DayTimeEntity w12;

                private DayTimeEntity w13;

                private DayTimeEntity w14;

                public DayTimeEntity getW11() {
                    return w11;
                }

                public void setW11(DayTimeEntity w11) {
                    this.w11 = w11;
                }

                public DayTimeEntity getW12() {
                    return w12;
                }

                public void setW12(DayTimeEntity w12) {
                    this.w12 = w12;
                }

                public DayTimeEntity getW13() {
                    return w13;
                }

                public void setW13(DayTimeEntity w13) {
                    this.w13 = w13;
                }

                public DayTimeEntity getW14() {
                    return w14;
                }

                public void setW14(DayTimeEntity w14) {
                    this.w14 = w14;
                }

                public WeekDayEntity getWeekDay() {
                    return new WeekDayEntity(w11, w12, w13, w14);
                }
            }

            public static class W2Entity {

                private DayTimeEntity w21;

                private DayTimeEntity w22;

                private DayTimeEntity w23;

                private DayTimeEntity w24;

                public DayTimeEntity getW21() {
                    return w21;
                }

                public void setW21(DayTimeEntity w21) {
                    this.w21 = w21;
                }

                public DayTimeEntity getW22() {
                    return w22;
                }

                public void setW22(DayTimeEntity w22) {
                    this.w22 = w22;
                }

                public DayTimeEntity getW23() {
                    return w23;
                }

                public void setW23(DayTimeEntity w23) {
                    this.w23 = w23;
                }

                public DayTimeEntity getW24() {
                    return w24;
                }

                public void setW24(DayTimeEntity w24) {
                    this.w24 = w24;
                }

                public WeekDayEntity getWeekDay() {
                    return new WeekDayEntity(w21, w22, w23, w24);
                }
            }

            public static class W3Entity {

                private DayTimeEntity w31;

                private DayTimeEntity w32;

                private DayTimeEntity w33;

                private DayTimeEntity w34;

                public DayTimeEntity getW31() {
                    return w31;
                }

                public void setW31(DayTimeEntity w31) {
                    this.w31 = w31;
                }

                public DayTimeEntity getW32() {
                    return w32;
                }

                public void setW32(DayTimeEntity w32) {
                    this.w32 = w32;
                }

                public DayTimeEntity getW33() {
                    return w33;
                }

                public void setW33(DayTimeEntity w33) {
                    this.w33 = w33;
                }

                public DayTimeEntity getW34() {
                    return w34;
                }

                public void setW34(DayTimeEntity w34) {
                    this.w34 = w34;
                }

                public WeekDayEntity getWeekDay() {
                    return new WeekDayEntity(w31, w32, w33, w34);
                }
            }

            public static class W4Entity {

                private DayTimeEntity w41;

                private DayTimeEntity w42;

                private DayTimeEntity w43;

                private DayTimeEntity w44;

                public DayTimeEntity getW41() {
                    return w41;
                }

                public void setW41(DayTimeEntity w41) {
                    this.w41 = w41;
                }

                public DayTimeEntity getW42() {
                    return w42;
                }

                public void setW42(DayTimeEntity w42) {
                    this.w42 = w42;
                }

                public DayTimeEntity getW43() {
                    return w43;
                }

                public void setW43(DayTimeEntity w43) {
                    this.w43 = w43;
                }

                public DayTimeEntity getW44() {
                    return w44;
                }

                public void setW44(DayTimeEntity w44) {
                    this.w44 = w44;
                }

                public WeekDayEntity getWeekDay() {
                    return new WeekDayEntity(w41, w42, w43, w44);
                }
            }

            public static class W5Entity {

                private DayTimeEntity w51;

                private DayTimeEntity w52;

                private DayTimeEntity w53;

                private DayTimeEntity w54;

                public DayTimeEntity getW51() {
                    return w51;
                }

                public void setW51(DayTimeEntity w51) {
                    this.w51 = w51;
                }

                public DayTimeEntity getW52() {
                    return w52;
                }

                public void setW52(DayTimeEntity w52) {
                    this.w52 = w52;
                }

                public DayTimeEntity getW53() {
                    return w53;
                }

                public void setW53(DayTimeEntity w53) {
                    this.w53 = w53;
                }

                public DayTimeEntity getW54() {
                    return w54;
                }

                public void setW54(DayTimeEntity w54) {
                    this.w54 = w54;
                }

                public WeekDayEntity getWeekDay() {
                    return new WeekDayEntity(w51, w52, w53, w54);
                }
            }

            public static class W6Entity {

                private DayTimeEntity w61;

                private DayTimeEntity w62;

                private DayTimeEntity w63;

                private DayTimeEntity w64;

                public DayTimeEntity getW61() {
                    return w61;
                }

                public void setW61(DayTimeEntity w61) {
                    this.w61 = w61;
                }

                public DayTimeEntity getW62() {
                    return w62;
                }

                public void setW62(DayTimeEntity w62) {
                    this.w62 = w62;
                }

                public DayTimeEntity getW63() {
                    return w63;
                }

                public void setW63(DayTimeEntity w63) {
                    this.w63 = w63;
                }

                public DayTimeEntity getW64() {
                    return w64;
                }

                public void setW64(DayTimeEntity w64) {
                    this.w64 = w64;
                }

                public WeekDayEntity getWeekDay() {
                    return new WeekDayEntity(w61, w62, w63, w64);
                }
            }

            public static class W7Entity {

                private DayTimeEntity w71;

                private DayTimeEntity w72;

                private DayTimeEntity w73;

                private DayTimeEntity w74;

                public DayTimeEntity getW71() {
                    return w71;
                }

                public void setW71(DayTimeEntity w71) {
                    this.w71 = w71;
                }

                public DayTimeEntity getW72() {
                    return w72;
                }

                public void setW72(DayTimeEntity w72) {
                    this.w72 = w72;
                }

                public DayTimeEntity getW73() {
                    return w73;
                }

                public void setW73(DayTimeEntity w73) {
                    this.w73 = w73;
                }

                public DayTimeEntity getW74() {
                    return w74;
                }

                public void setW74(DayTimeEntity w74) {
                    this.w74 = w74;
                }

                public WeekDayEntity getWeekDay() {
                    return new WeekDayEntity(w71, w72, w73, w74);
                }
            }
        }
    }

    public static class DayTimeEntity {
        private String style;
        private String money;
        private String remark;
        private String code;

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

    public static class WeekDayEntity {
        DayTimeEntity d1;
        DayTimeEntity d2;
        DayTimeEntity d3;
        DayTimeEntity d4;

        public WeekDayEntity(DayTimeEntity d1, DayTimeEntity d2, DayTimeEntity d3, DayTimeEntity d4) {
            this.d1 = d1;
            this.d2 = d2;
            this.d3 = d3;
            this.d4 = d4;
        }

        public DayTimeEntity getD1() {
            return d1;
        }

        public void setD1(DayTimeEntity d1) {
            this.d1 = d1;
        }

        public DayTimeEntity getD2() {
            return d2;
        }

        public void setD2(DayTimeEntity d2) {
            this.d2 = d2;
        }

        public DayTimeEntity getD3() {
            return d3;
        }

        public void setD3(DayTimeEntity d3) {
            this.d3 = d3;
        }

        public DayTimeEntity getD4() {
            return d4;
        }

        public void setD4(DayTimeEntity d4) {
            this.d4 = d4;
        }
    }
}
