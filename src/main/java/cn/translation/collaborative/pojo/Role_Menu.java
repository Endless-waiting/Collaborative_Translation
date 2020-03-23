package cn.translation.collaborative.pojo;

import java.io.Serializable;

public class Role_Menu implements Serializable {
    private Integer rid;

    private Integer mid;

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }
}