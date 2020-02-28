package com.hhf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hhf.entity.Book;
import com.hhf.mapper.BookMapper;
import com.hhf.service.MySPI;
import com.hhf.utils.ResultUtils;
import com.sun.corba.se.spi.activation.Server;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

@Service
public class BookService implements InitializingBean {

    @Autowired
    private BookMapper bookMapper;

    @PostConstruct
    public void init(){
        System.out.println("PostConstruct方法执行...");
        ServiceLoader<MySPI> load = ServiceLoader.load(MySPI.class);
        Iterator<MySPI> iterator = load.iterator();
        while(iterator.hasNext()){
            MySPI next = iterator.next();
            System.out.println(next.toSay());
        }
    }


    public Map<String, Object> updateCount(Long id) {
        QueryWrapper<Book> queryWrapper=new QueryWrapper<Book>();
        UpdateWrapper<Book> updateWrapper=new UpdateWrapper<Book>();
        if(id!=null){
            queryWrapper.eq("id",id);
            updateWrapper.eq("id",id);
            Book book = bookMapper.selectOne(queryWrapper);
            Book updateBook=new Book();
            updateBook.setCount(book.getCount()-1);
            int update = bookMapper.update(updateBook, updateWrapper);
            if(update==0){
                return ResultUtils.getFailResult("更新失败");
            }
        }else {
            return ResultUtils.getFailResult("id为空");
        }
        return ResultUtils.getSuccessResult("成功");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet方法执行...");
        ServiceLoader<MySPI> load = ServiceLoader.load(MySPI.class);
        Iterator<MySPI> iterator = load.iterator();
        while(iterator.hasNext()){
            MySPI next = iterator.next();
            System.out.println(next.toSay());
        }
    }
}
