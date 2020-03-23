package cn.translation.collaborative.mapper;

import cn.translation.collaborative.pojo.SelectVocabulary;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SelectVocabularyMapper {
    //获得翻译小组所属的所有单词所有的单词
    List<SelectVocabulary> selectAll(Integer gid);

    //获得翻译小组所属的一些单词
    List<SelectVocabulary> selectSome(Integer gid ,String word);

    //根据ID值获取单词
    SelectVocabulary selectById(Integer vid);
}