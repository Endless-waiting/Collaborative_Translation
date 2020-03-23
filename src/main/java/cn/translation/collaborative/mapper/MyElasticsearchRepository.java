package cn.translation.collaborative.mapper;

import cn.translation.collaborative.pojo.SelectVocabulary;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface MyElasticsearchRepository extends ElasticsearchRepository<SelectVocabulary,Integer> {

}
