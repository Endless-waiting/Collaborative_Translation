package cn.translation.collaborative;

import cn.translation.collaborative.mapper.MyElasticsearchRepository;
import cn.translation.collaborative.mapper.SelectVocabularyMapper;
import cn.translation.collaborative.mapper.VocabularyMapper;
import cn.translation.collaborative.pojo.SelectVocabulary;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class TranslationApplicationTests {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private VocabularyMapper vocabularyMapper;

    @Autowired
    private SelectVocabularyMapper selectVocabularyMapper;

    @Autowired
    private MyElasticsearchRepository repository;

    @Value("${elasticsearch.indexName}")
    private String indexName;

    @Value("${spring.datasource.driver-class-name}")
    private String driver;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.name}")
    private String name;

    @Test
    void contextLoads() {
    }

    @Test
    public void testPasswordEncoder(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123456");
        System.out.println(encode);

    }


    @Test
    public void testElasticSearch(){
        String string = "a";
        String i = "0";

        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        BoolQueryBuilder queryBuilder1 = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("word", string));
        BoolQueryBuilder queryBuilder2 = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("abbreviation", string));
        BoolQueryBuilder queryBuilder3 = QueryBuilders.boolQuery().must(QueryBuilders.termsQuery("gid", "0", i));

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.should(queryBuilder1).should(queryBuilder2).should(queryBuilder3);

        queryBuilder.withQuery(boolQueryBuilder);

        Page<SelectVocabulary> search = repository.search(queryBuilder.build());
        System.out.println(search);

    }
}
