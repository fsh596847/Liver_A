package com.android.doctor.model;

import java.util.List;

/**
 * Created by Yong on 2016/4/14.
 */
public class PatientStats {

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * nums : 1
         * name : 酒精肝
         * alias : 酒精肝
         */

        private List<DiagEntity> diag;
        /**
         * nums : 449
         * name : 随诊
         * alias : 随诊
         */

        private List<StatusEntity> status;
        /**
         * nums : 9
         * name : 河北
         * alias : 河北
         */

        private List<RegionEntity> region;

        public List<DiagEntity> getDiag() {
            return diag;
        }

        public void setDiag(List<DiagEntity> diag) {
            this.diag = diag;
        }

        public List<StatusEntity> getStatus() {
            return status;
        }

        public void setStatus(List<StatusEntity> status) {
            this.status = status;
        }

        public List<RegionEntity> getRegion() {
            return region;
        }

        public void setRegion(List<RegionEntity> region) {
            this.region = region;
        }

        public static class DiagEntity {
            private int nums;
            private String name;
            private String alias;

            public int getNums() {
                return nums;
            }

            public void setNums(int nums) {
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
            private int nums;
            private String name;
            private String alias;

            public int getNums() {
                return nums;
            }

            public void setNums(int nums) {
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
            private int nums;
            private String name;
            private String alias;

            public int getNums() {
                return nums;
            }

            public void setNums(int nums) {
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
}
