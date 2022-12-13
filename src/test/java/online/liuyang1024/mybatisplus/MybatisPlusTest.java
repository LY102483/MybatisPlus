package online.liuyang1024.mybatisplus;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import online.liuyang1024.mybatisplus.POJO.User;
import online.liuyang1024.mybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LiuYang on 2022/09/17 16:15
 * Email:1024839103ly@gmail.com
 */

@SpringBootTest
public class MybatisPlusTest {
    @Autowired
    private UserMapper userMapper;  //IOC容器只能生成类的bean，不能实现接口的bean，所以会报错




    @Test
    public void testInsert(){
        //新增用户信息
        // INSERT INTO user ( id, name, age, email ) VALUES ( ?, ?, ?, ? )
        User user =new User();
        user.setName("刘杨889");
        user.setAge(21);
        user.setEmail("1024839103@qq.com");
        int res = userMapper.insert(user);
        System.out.println("受影响的行数"+res);
        System.out.println("userID为"+user.getId());//ID为雪花算法自动生成
    }


    @Test
    public void testDeleteById(){
        //通过ID删除数据
        // DELETE FROM user WHERE id=?
        int res = userMapper.deleteById(1584454811556888577L);//加L的原因是因为ID已经超过long类型了
        System.out.println("受影响的行数"+res);
    }


    @Test
    public void testDeleteByMap(){
        // 根据map集合中所设置的条件(and)来进行删除
        // DELETE FROM user WHERE name = ? AND age = ?
        Map<String,Object> map=new HashMap<>();
        map.put("name","刘杨");
        map.put("age",21);
        int res = userMapper.deleteByMap(map);
        System.out.println("受影响的行数"+res);
    }

    @Test
    public void testDeleteBatchIds(){
        //通过ID批量删除数据
        // DELETE FROM user WHERE id IN ( ? , ? , ? )
        List<Long> idList = Arrays.asList(1L, 2L, 3L);//通过asList方法创建了一个list对象，其中添加L的原因是因为实体类中其ID类型就为Long类型
        int res = userMapper.deleteBatchIds(idList);
        System.out.println("受影响的行数"+res);
    }


    @Test
    public void testUpdateById(){
        // 通过ID修改数据
        // UPDATE user SET name=?, email=? WHERE id=?
        User user =new User();
        user.setId(4L);//设置要修改的数据的ID
        user.setName("刘杨");
        user.setEmail("xianren184@qq.com");
        int res = userMapper.updateById(user);//传入的是一个实体
        System.out.println("受影响的行数"+res);
    }


    @Test
    public void testSelectById(){
        // 通过ID查询用户信息
        // SELECT id,name,age,email FROM user WHERE id=?
        User user = userMapper.selectById(1L);
        System.out.println(user);
    }

    @Test
    public void testSelectBatchIds(){
        // 通过多个ID查询多个用户信息
        // SELECT id,name,age,email FROM user WHERE id IN ( ? , ? , ? )
        List<Long> idList = Arrays.asList(1L, 2L, 3L);
        List<User> users = userMapper.selectBatchIds(idList);
        users.forEach(System.out::println);
    }


    @Test
    public void testSelectByMap(){
        //根据map集合中的条件进行查询
        //SELECT id,name,age,email FROM user WHERE name = ? AND age = ?
        Map<String,Object> map=new HashMap<>();
        map.put("name","刘杨");
        map.put("age",21L);
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }


    @Test
    public void testSelectList(){
        //通过条件构造器查询一个list集合，若没有条件，则可以设置null为参数
        // userMapper中继承BaseMapper的范型告诉了mybatisPlus查询的表名
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }


    @Test
    public void testCustomSelect(){
        //测试自定义的查询方法,通过编写mapper.xml实现
        //此处需要注意，如果xml文件的默认地址不在resources/mapper/**/**.xml,则需要到yml中配置mapper-locations
        Map<String, Object> res = userMapper.selectMapById(1L);
        System.out.println(res);
    }



}
