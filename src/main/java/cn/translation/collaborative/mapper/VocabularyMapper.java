package cn.translation.collaborative.mapper;

import cn.translation.collaborative.pojo.Vocabulary;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VocabularyMapper {
    int deleteByPrimaryKey(Integer vid);

    int insert(Vocabulary record);

    int insertSelective(Vocabulary record);

    Vocabulary selectByPrimaryKey(Integer vid);

    int updateByPrimaryKeySelective(Vocabulary record);

    int updateByPrimaryKey(Vocabulary record);
}