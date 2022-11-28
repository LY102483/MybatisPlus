package online.liuyang1024.mybatisplus;

import online.liuyang1024.mybatisplus.POJO.User;
import online.liuyang1024.mybatisplus.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuYang on 2022/10/25 16:38
 * Email:1024839103ly@gmail.com
 */

@SpringBootTest
public class MybatisPlusServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testGetCount(){
        // 查询总记录数
        // SELECT COUNT( * ) FROM user
        long cnt = userService.count();
        System.out.println("数据库一共有"+cnt+"条数据");
    }


    @Test
    public void testInsertMore(){
        // 批量添加数据
        // INSERT INTO user ( id, name, age ) VALUES ( ?, ?, ? )
        List<User> userList=new ArrayList<>();
        for(int i=0;i<=10;i++){
            User user=new User();
            user.setAge(20+i);
            user.setName("刘杨"+i);
            userList.add(user);
        }
        boolean b = userService.saveBatch(userList);
        System.out.println(b);

    }

}
