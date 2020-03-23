package cn.translation.collaborative.pojo;

import org.elasticsearch.search.suggest.completion.context.ContextMapping;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.elasticsearch.core.completion.Completion;

import java.io.Serializable;

@Document(indexName = "selectvocabulary",type = "docs",shards = 1,replicas = 0)
public class SelectVocabulary implements Serializable {

    @Id
    private Integer vid;

    /*@CompletionField(analyzer = "ik", contexts = {@CompletionContext(name = "word",type = ContextMapping.Type.CATEGORY)})*/
    @CompletionField
    private String word;

    @Field(type = FieldType.Text)
    private String abbreviation;

    @Field(type = FieldType.Keyword)
    private String translation;

    @Field(index = false,type = FieldType.Keyword)
    private Integer mid;

    @Field(index = false,type = FieldType.Keyword)
    private String account;

    @Field(index = false,type = FieldType.Keyword)
    private String mname;

    @Field(index = false,type = FieldType.Keyword)
    private String titles;

    @Field(type = FieldType.Keyword)
    private Integer gid;

    public SelectVocabulary() {
    }

    public SelectVocabulary(Integer vid, String word, String abbreviation, String translation, Integer mid, String mname, String titles, Integer gid) {
        this.vid = vid;
        this.word = word;
        this.abbreviation = abbreviation;
        this.translation = translation;
        this.mid = mid;
        this.mname = mname;
        this.titles = titles;
        this.gid = gid;
    }

    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "SelectVocabulary{" +
                "vid=" + vid +
                ", word='" + word + '\'' +
                ", abbreviation='" + abbreviation + '\'' +
                ", translation='" + translation + '\'' +
                ", mid=" + mid +
                ", mname='" + mname + '\'' +
                ", titles='" + titles + '\'' +
                ", gid=" + gid +
                '}';
    }
}
