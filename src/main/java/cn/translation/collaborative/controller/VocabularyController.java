package cn.translation.collaborative.controller;

import cn.translation.collaborative.pojo.ResultOfPage;
import cn.translation.collaborative.pojo.SelectVocabulary;
import cn.translation.collaborative.pojo.Vocabulary;
import cn.translation.collaborative.service.VocabularyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Controller
@RequestMapping("/Vocabulary")
public class VocabularyController {
    @Autowired
    private VocabularyService vocabularyService;
    //跳转页面
    @GetMapping("/{path}")
    public String toPage(@PathVariable String path){
        return "manage/vocabulary/"+path;
    }

    //添加单词
    @RequestMapping("/postNewVocabulary")
    @ResponseBody
    public String postNewVocabulary(Vocabulary vocabulary, String cixing){
        vocabulary.setTranslation(cixing+" "+vocabulary.getTranslation());
        String s = vocabularyService.insertNewVocabulary(vocabulary);
        return s;
    }

    //分页展示单词所有信息
    @RequestMapping("/getVocabulary")
    @ResponseBody
    public ResultOfPage<SelectVocabulary> getVocabulary(int page, int limit){
        ResultOfPage<SelectVocabulary> result = vocabularyService.listPageOfVocabulary(page, limit);
        return result;
    }

    //删除单词
    @ResponseBody
    @RequestMapping("/deleteVocabularyById")
    public String deleteVocabularyById(int vid){
        String s = vocabularyService.deleteVocabularyById(vid);
        return s;
    }

    //修改单词
    @RequestMapping("/putVocabularyById")
    @ResponseBody
    public String putVocabulryById(Vocabulary vocabulary){
        String s = vocabularyService.updataVocabularyById(vocabulary);
        return s;
    }

    //查询单词
    @RequestMapping("/getListOfVocabulary")
    @ResponseBody
    public List<SelectVocabulary> getListOfVocabulary(String string){
        List<SelectVocabulary> vocabularies = vocabularyService.listVocabulary(string);
        System.out.println(vocabularies);
        return vocabularies;
    }

    //初始化ElasticSearch,从MySQL中将数据导入到ES中
    @RequestMapping("/initElasticSearch")
    @ResponseBody
    public String initElasticSearch(){
        String result = vocabularyService.initElasticSearch();
        return result;
    }
    //上传文件
    @RequestMapping("/uploadFile")
    @ResponseBody
    public String uploadFile(MultipartFile file)throws Exception {
        String s = vocabularyService.insertVocabularuByFile(file);
        return s;
    }

    //下载指定文件
    @RequestMapping("/downloadFile")
    public HttpServletResponse downloadFile(HttpServletResponse response){
        try {
            String path = "src/main/resources/upload_vocabulary.xlsx";

            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String fileName = file.getName();
            // 取得文件的后缀名。
            fileName.substring(fileName.lastIndexOf(".")+1).toUpperCase();
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return response;
    }
}
