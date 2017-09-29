package com.liang.tind.mycv.model;

/**
 * Created by Sherlock on 2017/9/3.
 */

public class JobInfoBean extends BaseBean {


    /**
     * companyName : 上海锐至信息技术有限公司
     * jobName : 安卓开发工程师
     * jobTime : 2年
     */

    private String companyName;
    private String jobName;
    private String jobTime;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobTime() {
        return jobTime;
    }

    public void setJobTime(String jobTime) {
        this.jobTime = jobTime;
    }
}
