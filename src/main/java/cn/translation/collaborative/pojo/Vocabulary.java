package cn.translation.collaborative.pojo;

import java.io.Serializable;
import java.util.Date;

public class Vocabulary implements Serializable {
    private Integer vid;

    private String abbreviation;

    private String word;

    private String translation;

    private Integer translator;

    private Date time;

    private Integer group;

    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public Integer getTranslator() {
        return translator;
    }

    public void setTranslator(Integer translator) {
        this.translator = translator;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "Vocabulary{" +
                "vid=" + vid +
                ", word='" + word + '\'' +
                ", abbreviation='" + abbreviation + '\'' +
                ", translation='" + translation + '\'' +
                ", translator=" + translator +
                ", time=" + time +
                ", group=" + group +
                '}';
    }
}