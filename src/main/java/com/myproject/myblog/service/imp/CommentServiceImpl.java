package com.myproject.myblog.service.imp;

import com.myproject.myblog.dao.CommentRepository;
import com.myproject.myblog.po.Blog;
import com.myproject.myblog.po.Comment;
import com.myproject.myblog.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: my-blog
 * @description: 评论实现类
 * @author: zhan
 * @create: 2020-03-04 14:09
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;



    @Transactional
    @Override //评论列表查询
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort = new Sort(Sort.Direction.ASC, "creatTime");
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId, sort);//这里要拿到所有顶级的评论节点
        System.out.println("顶级评论条数：" + comments.size());
        return eachComment(comments);//按这指定sort来排序
    }

    @Transactional
    @Override //评论提交保存
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();//从前端传递的comment中找 parentCommentId，默认是给了个 -1
        if ( parentCommentId != -1 ){ // 不等于 -1 正式证明有回复评论,则需要设置 父comment
            comment.setParentComment(commentRepository.findOne(parentCommentId));
        }else {//自己第一个评论，则没有父 comment
            comment.setParentComment(null);
        }
        comment.setCreatTime(new Date());
        return commentRepository.save(comment);
    }


    /**
     * 循环每个顶级的评论节点
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments){
        List<Comment> commentsView = new ArrayList<>();//用来放顶级评论
        for (Comment comment : comments){//为了避免对数据库数据进行操作，这里先把进行一个循环复制
            //把每个顶级评论节点找出来并放到 commentView中,
            Comment cmt = new Comment();
            BeanUtils.copyProperties(comment , cmt);
            commentsView.add(cmt);
        }
        //再把所有的1、2、3.....级子节点都同一放到 第一个节点中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     *
     * @param comments root根节点，blog不为空的对象集合，即就是 顶级评论节点
     */
    private void combineChildren(List<Comment> comments){
        /**
         * 找出所有顶级评论节点下面的 子节点,
         * 即：把每一个父节点下面的子节点放在一个集合中，两个父节点就会有两个子节点集合(每一个父节点有一个子节点集合)
         */
        for (Comment comment : comments){
            //Comment 有一个属性 replyComments 是用来表示子节点子节点集合
            List<Comment> replys1 = comment.getReplyComments();
            //再循环，找出每个子节点集合里面的 子节点
            for (Comment reply1 : replys1){
                //循环迭代,最终找到每一个子节点，并把子节点放到 tempReplys集合中
                recursively(reply1);
            }
            //把顶级节点comment的 replyComments子节点集合 改成 tempReplys集合, tempReplys集合 里面装着所有子节点集合
            comment.setReplyComments(tempReplys);
            //每次存完之后就把tempReplys集合 清空，重新进入下一个循环
            tempReplys = new ArrayList<>();
        }
    }

    //存放所有子节点的集合
    private List<Comment> tempReplys = new ArrayList<>();

    /**
     * 递归迭代
     * @param comment 被迭代的对象
     */
    private void recursively(Comment comment){
        //先把一级节点放进 tempReplys中
        tempReplys.add(comment);
        if (comment.getReplyComments().size()>0){//当子节点集合 >0 时，证明还有子节点,则继续进入迭代,没有，则证明已经是最底层节点
            //获取子节点集合
            List<Comment> replys = comment.getReplyComments();
            //继续遍历子节点集合,找子节点
            for (Comment comment1 : replys){
                tempReplys.add(comment1);//只要是子节点就都装tempReplys中，无论下面是否还有子节点
                //找到子节点集合中的子节点a， 把a 当做顶级节点 再往下找子节点
                if (comment1.getReplyComments().size()>0){ //当子节点集合>0，则证明还没有找完，继续进入下一个迭代
                    recursively(comment1);
                }
            }
        }
        System.out.println("在放完子集后：" + tempReplys.size());
    }


}
