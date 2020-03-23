package cn.translation.collaborative.service.impl;

import cn.translation.collaborative.mapper.MembersMapper;
import cn.translation.collaborative.mapper.MyElasticsearchRepository;
import cn.translation.collaborative.mapper.SelectVocabularyMapper;
import cn.translation.collaborative.mapper.VocabularyMapper;
import cn.translation.collaborative.pojo.Members;
import cn.translation.collaborative.pojo.ResultOfPage;
import cn.translation.collaborative.pojo.SelectVocabulary;
import cn.translation.collaborative.pojo.Vocabulary;
import cn.translation.collaborative.service.VocabularyService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

@Service
public class VocabularyServiceImpl implements VocabularyService {
    @Autowired
    private VocabularyMapper vocabularyMapper;

    @Autowired
    private SelectVocabularyMapper selectVocabularyMapper;

    @Autowired
    private MembersMapper membersMapper;

    @Autowired
    private MyElasticsearchRepository myElasticsearchRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Value("${elasticsearch.indexName}")
    private String indexName;

    @Override
    @Transactional
    public String insertNewVocabulary(Vocabulary vocabulary) {

        long time = System.currentTimeMillis();
        Date date = new Date(time);
        vocabulary.setTime(date);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String account = authentication.getName();
        Members members = membersMapper.selectByAccount(account);
        vocabulary.setTranslator(members.getGid());
        vocabulary.setGroup(members.getGid());
        vocabulary.setTranslator(members.getMid());

        System.out.println(vocabulary);
        int insertSelective = vocabularyMapper.insertSelective(vocabulary);

        System.out.println(vocabulary.getVid() + "用于测试mybatis是否返回主键");
        SelectVocabulary voc = new SelectVocabulary(vocabulary.getVid(),
                vocabulary.getWord(),
                vocabulary.getAbbreviation(),
                vocabulary.getTranslation(),
                members.getMid(),
                members.getMname(),
                members.getTitles(),
                members.getGid());
        myElasticsearchRepository.save(voc);
        if (insertSelective > 0) {
            return "success";
        }
        return "error";
    }

    @Override
    @Transactional
    public String insertSomeVocabulary(List<Vocabulary> vocabularyList) {
        int count = 0;
        long time = System.currentTimeMillis();
        Date date = new Date(time);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String account = authentication.getName();
        Members members = membersMapper.selectByAccount(account);
        Integer translator = members.getMid();
        Integer gid = members.getGid();

        List<SelectVocabulary> selectVocabularyList = new ArrayList<>();

        ArrayList<IndexQuery> queries = new ArrayList<>();

        for (Vocabulary vocabulary : vocabularyList) {
            vocabulary.setTime(date);
            vocabulary.setTranslator(translator);
            vocabulary.setGroup(gid);
            int i = vocabularyMapper.insertSelective(vocabulary);
            if (i > 0) {
                count++;
            }
            SelectVocabulary voc = new SelectVocabulary(vocabulary.getVid(),
                    vocabulary.getWord(),
                    vocabulary.getAbbreviation(),
                    vocabulary.getTranslation(),
                    members.getMid(),
                    members.getMname(),
                    members.getTitles(),
                    members.getGid());
            selectVocabularyList.add(voc);
        }
        if (count == vocabularyList.size()) {
            myElasticsearchRepository.saveAll(selectVocabularyList);
            elasticsearchTemplate.refresh(indexName);
            return "success";
        }
        return "error"; }


    @Override
    @Transactional
    public String deleteVocabularyById(Integer vid) {
        int i = vocabularyMapper.deleteByPrimaryKey(vid);
        myElasticsearchRepository.deleteById(vid);
        if (i > 0) {
            return "success";
        }
        return "error";
    }

    @Override
    @Transactional
    public String updataVocabularyById(Vocabulary vocabulary) {
        int i = vocabularyMapper.updateByPrimaryKeySelective(vocabulary);
        if (i > 0) {
            SelectVocabulary selectVocabulary = selectVocabularyMapper.selectById(vocabulary.getVid());
            myElasticsearchRepository.save(selectVocabulary);
            return "success";
        }
        return "error";
    }

    @Override
    public ResultOfPage<SelectVocabulary> listPageOfVocabulary(int page, int limit) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String account = authentication.getName();
        Members members = membersMapper.selectByAccount(account);

        Page<SelectVocabulary> startPage = PageHelper.startPage(page, limit);
        List<SelectVocabulary> selectVocabularyList = selectVocabularyMapper.selectAll(members.getGid());
        ResultOfPage<SelectVocabulary> result = new ResultOfPage<SelectVocabulary>(0, "", startPage.getTotal(), startPage.getResult());

        return result;
    }

    //核心搜索方法
    @Override
    public List<SelectVocabulary> listVocabulary(String string) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean authenticated = authentication.isAuthenticated();

        String account;
        if (!authenticated) {
            account = authentication.getName();
        } else {
            account = "000000";
        }

        Members members = membersMapper.selectByAccount(account);
        int gid = members.getGid();

        QueryBuilder multiMatchQuery = QueryBuilders.multiMatchQuery(string, "word", "abbreviation");
        QueryBuilder prefixQuery = QueryBuilders.prefixQuery("word", string);
        Iterator<SelectVocabulary> multiMatchIterator = myElasticsearchRepository.search(multiMatchQuery).iterator();

        //返回值存储集合
        List<SelectVocabulary> list = new ArrayList<>();

        if (multiMatchIterator == null || !multiMatchIterator.hasNext()) {
            Iterator<SelectVocabulary> searchIterator = myElasticsearchRepository.search(prefixQuery).iterator();
            while (searchIterator.hasNext()){
                SelectVocabulary vocabulary = searchIterator.next();
                if (vocabulary.getGid() == gid || vocabulary.getGid() == 0) {
                    if (vocabulary.getAbbreviation() == null)
                        vocabulary.setAbbreviation("");
                    if (vocabulary.getTitles() == null)
                        vocabulary.setTitles("");
                    if ("管理员".equals(vocabulary.getMname())) {
                        vocabulary.setMname("");
                    }
                    list.add(vocabulary);
                }
            }
            return list;
        }

        while (multiMatchIterator.hasNext()) {
            SelectVocabulary vocabulary = multiMatchIterator.next();
            if (vocabulary.getGid() == gid || vocabulary.getGid() == 0) {
                if (vocabulary.getAbbreviation() == null)
                    vocabulary.setAbbreviation("");
                if (vocabulary.getTitles() == null)
                    vocabulary.setTitles("");
                if ("管理员".equals(vocabulary.getMname())) {
                    vocabulary.setMname("");
                }
                list.add(vocabulary);
            }
        }
        return list;


    }

    @Override
    public String initElasticSearch() {
        try {
            boolean exists = elasticsearchTemplate.indexExists(indexName);
            if (exists) {
                elasticsearchTemplate.deleteIndex(indexName);
            }
            elasticsearchTemplate.createIndex(indexName);
            elasticsearchTemplate.putMapping(SelectVocabulary.class);
        } catch (Exception e) {
            System.out.println("elasticsearch 索引创建失败");
            return "elasticsearch 索引创建失败，请重试！";
        }
        try {
            int count = 0;
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String account = authentication.getName();
            Members members = membersMapper.selectByAccount(account);

            List<SelectVocabulary> list = selectVocabularyMapper.selectAll(members.getGid());
            myElasticsearchRepository.saveAll(list);
            elasticsearchTemplate.refresh(indexName);
        } catch (Exception e) {
            System.out.println("elasticsearch数据导入出错");
            return "elasticsearch数据导入出错，请重试！";
        }
        return "success";
    }

    @Override
    public String insertVocabularuByFile(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        XSSFWorkbook xssfSheets = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = xssfSheets.getSheet("sheet1");
        int lastRowNum = sheet.getLastRowNum();
        int firstRowNum = sheet.getFirstRowNum();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String account = authentication.getName();
        Members members = membersMapper.selectByAccount(account);

        long currentTimeMillis = System.currentTimeMillis();
        Date date = new Date(currentTimeMillis);

        List<Vocabulary> vocabularyList = new ArrayList<>();
        //遍历数据
        //获取列名
        XSSFRow col = sheet.getRow(0);
        for (int row = 1; row <= lastRowNum; row++) {
            Vocabulary vocabulary = new Vocabulary();
            XSSFRow sheetRow = sheet.getRow(row);

            if (sheetRow.getCell(0) != null) {
                vocabulary.setAbbreviation(sheetRow.getCell(0).toString());
            }
            vocabulary.setWord(sheetRow.getCell(1).toString() + sheetRow.getCell(1).toString());
            vocabulary.setTranslation(sheetRow.getCell(3).toString());
            vocabulary.setTranslator(members.getMid());
            vocabulary.setGroup(members.getGid());
            vocabulary.setTime(date);

            vocabularyList.add(vocabulary);
        }

        String s = insertSomeVocabulary(vocabularyList);
        return s;
    }
}