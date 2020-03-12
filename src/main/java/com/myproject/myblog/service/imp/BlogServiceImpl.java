package com.myproject.myblog.service.imp;

import com.myproject.myblog.NotFoundException;
import com.myproject.myblog.dao.BlogRepository;
import com.myproject.myblog.po.Blog;
import com.myproject.myblog.po.Type;
import com.myproject.myblog.service.BlogService;
import com.myproject.myblog.util.MarkdownUitls;
import com.myproject.myblog.util.NatureUitls;
import com.myproject.myblog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * @program: my-blog
 * @description:
 * @author: zhan
 * @create: 2020-02-28 16:59
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    /**
     * markdown 转换html
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogRepository.findOne(id);
        if ( blog == null){
            throw  new NotFoundException("该博客不存在");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        b.setContent(MarkdownUitls.markdownToHtmlExtensions(b.getContent()));
        //浏览次数 +1
        blogRepository.updateViews(id);
        return b;
    }

    @Transactional
    @Override
    public Blog selectBlogById(Long id) {
        return blogRepository.findOne(id);
    }

    @Transactional
    @Override
    public Blog selectBlogByTitle(String title) {
        return blogRepository.findByTitle(title);
    }

    @Transactional
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {//需要nwe 一个 Specification对象，这是findAll方法的第一个参数
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                //root表示要查询的对象,这里是Blog,把Blog映射成了root,在root中可以获取表的一些表中的字段
                //criteriaQuery 查询的容器,把查询的条件放到里面来
                //CriteriaBuilder 用于构建查询条件,字段之间是什么关系，如何生成一个查询条件，每一个查询条件都是什么方式
                //Predicate 单独每一条查询条件的详细描述 整个 where xxx=xx and yyy=yy ...
                //组合动态查询条件
                List<Predicate> predicates = new ArrayList<>();
                //1.判断标题是否值为空 且 值不为 null，确定是否需要该查询条件(有值则需要该查询条件)
                if(!"".equals(blog.getTitle()) && blog.getTitle() != null){
                    Predicate p1 = cb.like(root.<String>get("title"),"%"+blog.getTitle()+"%");//参数1:表中的什么字段 参数2:要like什么,即传过来的实际值
                    predicates.add(p1);
                }
                //2.判断分类是否为空,分类实际上是判断id 是否为null，id是主键所以不存在" "
                if(blog.getTypeId() != null){
                    Predicate p2 = cb.equal(root.<Type>get("type").get("id"), blog.getTypeId());
                    predicates.add(p2);
                }
                //3.判断是否推荐，recommend 是boolean 类型,直接判断 true/false,true则当做条件进行查询
                if (blog.isRecommend()){
                    Predicate p3 = cb.equal(root.<Boolean>get("recommend"), blog.isRecommend());
                    predicates.add(p3);
                }
                //where 里面的参数需要 CriteriaQuery<?>，这里需要把集合转换成数组
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }
    //标签首页，根据标签展示Blog
    @Transactional
    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override //JPA使用
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                //这是一个关联啊查询
                /**
                 * CriteriaBuilder 安全查询创建工厂
                 * CriteriaQuery 安全查询主语句
                 */
                Join join = root.join("tags");//blog表与tag表来进行关联，tags 是 blog中的属性对象使他们构成关联关系
                //CriteriaBuilder 也可以用来查询
                return cb.equal(join.get("id"),tagId);//返回一个page对象, 条件:blog和tag表中 tagId相同的
            }
        }, pageable);
    }

    @Transactional
    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    //全局查询
    @Transactional
    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query, pageable);
    }

    @Transactional
    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"updateTime");
        Pageable pageable = new PageRequest(0, size, sort);//取第一页数据，一页多少数据，按什么排序
        return blogRepository.findTop(pageable);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        //判断是否有id，没id则是新增，有id则是修改
        if (blog.getId() == null){
            blog.setCreatTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }

        blog.setUpdateTime(new Date());
        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog editBlog(Long id, Blog blog) {
        Blog b = blogRepository.findOne(id);
        if ( b == null){
            throw new NotFoundException("该博客不存在");
        }
        //把blog的值 复制到 b, 复制过程中 NatureUitls.getNullPropertyNames(blog) 这些属性为null 的 进行忽略，不复制
        BeanUtils.copyProperties(blog, b, NatureUitls.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogRepository.save(b);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.delete(id);
    }


    //归档首页blog查询
    @Override
    public Map<String, List<Blog>> archivesBlog() {
        //获取年份集合
        List<String> years = blogRepository.findGroupYear();
        Map<String,List<Blog>> ap = new HashMap<>();
        //遍历年份集合，把每个年份下的blog放入 Map中
        for (String year : years){
            ap.put(year, blogRepository.findByYear(year));
        }
        return ap;
    }

    //查询一共有多少blog


    @Override
    public Long countBlog() {
        return blogRepository.count();//count() 直接查询返回 blog表里数据条数
    }
}
