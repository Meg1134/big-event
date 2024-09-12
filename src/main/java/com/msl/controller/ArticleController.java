package com.msl.controller;


import com.msl.pojo.Article;
import com.msl.pojo.PageBean;
import com.msl.pojo.Result;
import com.msl.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public Result add(@RequestBody @Validated Article article) {
        articleService.add(article);
        return Result.success();
    }

    public Result<PageBean<Article>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String state) {

       PageBean<Article> pb =  articleService.list(pageNum, pageSize, categoryId, state);
       return Result.success(pb);
    }

}
