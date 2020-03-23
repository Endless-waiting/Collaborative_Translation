package cn.translation.collaborative.pojo;

import java.io.Serializable;

public class Menu implements Serializable {
    private Integer mid;

    private Integer superiorMenu;

    private String mname;

    private String url;

    private Integer open;

    private Integer isparent;

    private String icon;

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Integer getSuperiorMenu() {
        return superiorMenu;
    }

    public void setSuperiorMenu(Integer superiorMenu) {
        this.superiorMenu = superiorMenu;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname == null ? null : mname.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getOpen() {
        return open;
    }

    public void setOpen(Integer open) {
        this.open = open;
    }

    public Integer getIsparent() {
        return isparent;
    }

    public void setIsparent(Integer isparent) {
        this.isparent = isparent;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }
}