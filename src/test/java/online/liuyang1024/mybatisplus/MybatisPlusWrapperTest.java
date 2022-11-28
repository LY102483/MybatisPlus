package online.liuyang1024.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import online.liuyang1024.mybatisplus.POJO.User;
import online.liuyang1024.mybatisplus.mapper.UserMapper;
import online.liuyang1024.mybatisplus.service.UserService;
import org.apache.ibatis.jdbc.SQL;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

/**
 * Created by LiuYang on 2022/10/28 15:34
 * Email:1024839103ly@gmail.com
 */


@SpringBootTest
public class MybatisPlusWrapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test01(){
        //场景：用户名包含a,年龄在20——30之间，邮箱信息不为null的用户信息
        //范型与实体类相对应
        // SQL：SELECT id,name,age,email,is_deleted FROM user WHERE is_deleted=0 AND (name LIKE ? AND age BETWEEN ? AND ? AND email IS NOT NULL)
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        //查询条件
        queryWrapper.like("name","a").between("age",20,30).isNotNull("email");
        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void test02(){
        //查询用户信息，按照年龄的降序排序，若年龄相同，则按照ID的升序进行排序
        // SQL:SELECT id,name,age,email,is_deleted FROM user WHERE is_deleted=0 ORDER BY age DESC,id ASC
        //Asc升序，Desc降序
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("age").orderByAsc("id");
        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }


    @Test
    public void test03(){
        //删除邮箱地址为null的用户数据
        // SQL:UPDATE user SET is_deleted=1 WHERE is_deleted=0 AND (email IS NULL)
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.isNull("email");
        int res = userMapper.delete(queryWrapper);
        System.out.println("受影响的行数"+res);
    }

    @Test
    public void test04(){
        //将(用户名中包含有a并且年龄大于20)或邮箱为null的用户信息修改
        // SQL:UPDATE user SET name=?, email=? WHERE is_deleted=0 AND (age > ? AND name LIKE ? OR email IS NULL)
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        // greater than大于；less than 小于
        queryWrapper.gt("age",20).like("name","a").or().isNull("email");
        User user=new User();
        user.setName("小明");
        user.setEmail("test@qq.com");
        //第一个参数用来设置修改的内容，第二个参数用来设置修改的条件。
        int res = userMapper.update(user, queryWrapper);
        System.out.println("受影响的行数为"+res);
    }

    @Test
    public void test05(){
        //条件的优先级
        //将用户名中包含有a并且（年龄大于20或邮箱为null）的用户信息修改
        // SQL:UPDATE user SET name=?, email=? WHERE is_deleted=0 AND (name LIKE ? AND (age > ? OR email IS NULL))
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        //mybatisplus中，lambda中的条件优先执行
        queryWrapper.like("name","a")
                //此处使用lambda表达式(不会！！！！！！)

                .and(i->i.gt("age",20).or().isNull("email"));
        User user=new User();
        user.setName("小红");
        user.setEmail("test22@qq.com");
        //第一个参数用来设置修改的内容，第二个参数用来设置修改的条件。
        int res = userMapper.update(user, queryWrapper);
        System.out.println("受影响的行数为"+res);
    }

    @Test
    public void test06(){
        //自定义查询列
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        //需求:查询用户的用户名，邮箱，年龄
        // SQL:SELECT name,email,age FROM user WHERE is_deleted=0
        queryWrapper.select("name","email","age");
        List<Map<String, Object>> res = userMapper.selectMaps(queryWrapper);
        res.forEach(System.out::println);
    }

    @Test
    public void test07(){
        //子查询
        //需求：查询id小于等于100的用户信息
        // SQL:SELECT id,name,age,email,is_deleted FROM user WHERE is_deleted=0 AND (id IN (select id from user where id <= 100))
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.inSql("id","select id from user where id <= 100");
        List<User> res = userMapper.selectList(queryWrapper);
        res.forEach(System.out::println);
    }

    @Test
    public void test08(){
        //通过UpdateWrapper修改数据
        //将用户名中包含有a并且（年龄大于20或邮箱为null）的用户信息修改
        // SQL:UPDATE user SET name=?,email=? WHERE is_deleted=0 AND (name LIKE ? AND (age > ? OR email IS NULL))
        UpdateWrapper<User> userUpdateWrapper=new UpdateWrapper<>();
        userUpdateWrapper.like("name","a")
                .and(i -> i.gt("age",20).or().isNull("email"));
        userUpdateWrapper.set("name","小黑").set("email","abc@qq.com");
        int res = userMapper.update(null, userUpdateWrapper);
        System.out.println("受影响的行数为"+res);
    }

    @Test
    public void test09(){
        //模拟开发中的组装查询
        //对用户查询（年龄大于20，小于30）
        // SQL:SELECT id,name,age,email,is_deleted FROM user WHERE is_deleted=0 AND (age >= ? AND age <= ?)
        String username="";
        Integer ageBegin=20;
        Integer ageEnd=30;
        QueryWrapper<User>  queryWrapper=new QueryWrapper<>();

        if(StringUtils.isNotBlank(username)){  //判断某个字符串是否不为空，不为null，不为空白符
            queryWrapper.like("name",username);
        }
        if(ageBegin!=null){
            queryWrapper.ge("age",ageBegin);
        }
        if(ageEnd!=null){
            queryWrapper.le("age",ageEnd);
        }
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }


    @Test
    public void test10(){
        // 使用condition组装条件
        // SQL:SELECT id,name,age,email,is_deleted FROM user WHERE is_deleted=0 AND (name LIKE ? AND age <= ?)
        String username="a";
        Integer ageBegin=null;
        Integer ageEnd=30;
        QueryWrapper<User>  queryWrapper=new QueryWrapper<>();
        //判断是否满足某个条件，如满足，则进行组装
        queryWrapper.like(StringUtils.isNotBlank(username),"name",username)
                .ge(ageBegin!=null,"age",ageBegin)
                .le(ageEnd!=null,"age",ageEnd);
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);

    }




}
