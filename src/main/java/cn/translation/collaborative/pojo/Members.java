package cn.translation.collaborative.pojo;

import java.io.Serializable;

public class Members implements Serializable {
    private Integer mid;

    private String account;

    private String mname;

    private String password;

    private String institute;

    private String department;

    private String team;

    private String titles;

    private Integer gid;

    public Integer getMid() {
        return mid;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname == null ? null : mname.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute == null ? null : institute.trim();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles == null ? null : titles.trim();
    }

    @Override
    public String toString() {
        return "Members{" +
                "mid=" + mid +
                ", account='" + account + '\'' +
                ", mname='" + mname + '\'' +
                ", password='" + password + '\'' +
                ", institute='" + institute + '\'' +
                ", department='" + department + '\'' +
                ", gid='" + gid + '\'' +
                ", titles='" + titles + '\'' +
                '}';
    }
}