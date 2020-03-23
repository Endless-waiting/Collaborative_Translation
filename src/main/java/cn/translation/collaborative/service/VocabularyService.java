package cn.translation.collaborative.service;

import cn.translation.collaborative.pojo.ResultOfPage;
import cn.translation.collaborative.pojo.SelectVocabulary;
import cn.translation.collaborative.pojo.Vocabulary;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VocabularyService {
    //增加单词
    String insertNewVocabulary(Vocabulary vocabulary);

    String insertSomeVocabulary(List<Vocabulary> vocabularyList);

    //根据Id删除单词
    String deleteVocabularyById(Integer vid);

    //修改单词
    String updataVocabularyById(Vocabulary vocabulary);

    //查询单词并分页
    ResultOfPage<SelectVocabulary> listPageOfVocabulary(int page,int limit);

    //查询单词
    List<SelectVocabulary> listVocabulary(String string);

    //初始化elasticsearch ，并将MySQL中的数据导入到elasticsearch中
    String initElasticSearch();

    //以文件的方式添加数据
    String insertVocabularuByFile(MultipartFile file) throws Exception;
}
