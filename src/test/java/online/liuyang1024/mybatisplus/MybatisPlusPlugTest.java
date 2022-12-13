package online.liuyang1024.mybatisplus;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import online.liuyang1024.mybatisplus.POJO.Product;
import online.liuyang1024.mybatisplus.POJO.User;
import online.liuyang1024.mybatisplus.enums.SexEnum;
import online.liuyang1024.mybatisplus.mapper.ProductMapper;
import online.liuyang1024.mybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * 测试MybatisPlus中的插件
 */
@SpringBootTest
public class MybatisPlusPlugTest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ProductMapper productMapper;

    @Test
    // 测试分页插件
    public void testPage() {
        // 设置分页参数
        /**
         * current:当前页数
         * size：每页数量
         */
        Page<User> page = new Page<>(3, 5);
        userMapper.selectPage(page, null);
        // 获取分页数据
        List<User> list = page.getRecords();
        list.forEach(System.out::println);
        System.out.println("当前页：" + page.getCurrent());
        System.out.println("每页显示的条数：" + page.getSize());
        System.out.println("总记录数：" + page.getTotal());
        System.out.println("总页数：" + page.getPages());
        System.out.println("是否有上一页：" + page.hasPrevious());
        System.out.println("是否有下一页：" + page.hasNext());
    }

    @Test
    // xml自定义分页
    public void testSelectPageVo() {
        // 设置分页参数
        Page<User> page = new Page<>(10, 5);
        userMapper.selectPageVo(page, 20);
        // 获取分页数据
        List<User> list = page.getRecords();
        list.forEach(System.out::println);
        System.out.println("当前页：" + page.getCurrent());
        System.out.println("每页显示的条数：" + page.getSize());
        System.out.println("总记录数：" + page.getTotal());
        System.out.println("总页数：" + page.getPages());
        System.out.println("是否有上一页：" + page.hasPrevious());
        System.out.println("是否有下一页：" + page.hasNext());
    }




    /**
     * 乐观锁与悲观锁
     */

    /*
    背景介绍：
    一件商品，成本价是80元，售价是100元。老板先是通知小李，说你去把商品价格增加50元。小李正在玩游戏，耽搁了一个小时。
    正好一个小时后，老板觉得商品价格增加到150元，价格太高，可能会影响销量。又通知小王，你把商品价格降低30元。
    此时，小李和小王同时操作商品后台系统。小李操作的时候，系统先取出商品价格100元；小王也在操作，取出的商品价格也是100元。
    小李将价格加了50元，并将100+50=150元存入了数据库；小王将商品减了30元，并将100-30=70元存入了数据库。
    是的，如果没有锁，小李的操作就完全被小王的覆盖了。现在商品价格是70元，比成本价低10元。
    几分钟后，这个商品很快出售了1千多件商品，老板亏1万多。
    上面的故事，如果是乐观锁，小王保存价格前，会检查下价格是否被人修改过了。
    如果被修改过了，则重新取出的被修改后的价格，150元，这样他会将120元存入数据库。
    如果是悲观锁，小李取出数据后，小王只能等小李操作完之后，才能对价格进行操作，也会保证最终的价格是120元。
    */

    @Test
    //修改冲突
    public void testConcurrentUpdate() {
        // 1.小李
        Product p1 = productMapper.selectById(1L);
        System.out.println("小李取出的价格：" + p1.getPrice());

        // 2.小王
        Product p2 = productMapper.selectById(1L);
        System.out.println("小王取出的价格" + p2);

        // 3.小李将价格加了50元，存入数据库
        p1.setPrice(p1.getPrice() + 50);
        int result1 = productMapper.updateById(p1);
        System.out.println("小李修改结果：" + result1);


        // 4.小王将商品价格减少了30，存入了数据库
        p2.setPrice(p2.getPrice() - 30);
        int result2 = productMapper.updateById(p2);
        System.out.println("小王修改的结果" + result2);


        // 最后的结果
        Product p3 = productMapper.selectById(1L);
        // 价格覆盖，最后的结果：70
        System.out.println("最后的结果:" + p3.getPrice());
    }


    @Test
    // 通过乐观锁测试修改冲突
    public void testConcurrentVersionUpdate() {
        // 小李取数据
        Product p1 = productMapper.selectById(1L);

        // 小王取数据
        Product p2 = productMapper.selectById(1L);
        // 小李修改 + 50
        p1.setPrice(p1.getPrice() + 50);
        int result1 = productMapper.updateById(p1);
        System.out.println("小李修改的结果：" + result1);
        // 小王修改 - 30
        p2.setPrice(p2.getPrice() - 30);
        int result2 = productMapper.updateById(p2);
        System.out.println("小王修改的结果：" + result2);
        if (result2 == 0) {
            // 失败重试，重新获取version并更新
            p2 = productMapper.selectById(1L);
            p2.setPrice(p2.getPrice() - 30);
            result2 = productMapper.updateById(p2);
        }
        System.out.println("小王修改重试的结果：" + result2);
        // 老板看价格
        Product p3 = productMapper.selectById(1L);
        System.out.println("老板看价格：" + p3.getPrice());
    }


    /**
     * 通用枚举
     * 例如在数据库中，我们对user表中的性别做出如下约束，男为1，女为2
     */

    @Test
    //插入数据：自动将枚举映射为字段值
    public void testSexEnum(){
        User user = new User();
        user.setName("傲中科技");
        user.setAge(5);
        //设置性别为枚举项，会讲@EnumValue注解所标识的属性值存储到数据可
        user.setSex(SexEnum.MALE);
        int result = userMapper.insert(user);
        System.out.println(result);
    }

    @Test
    // 查询：自动将字段值映射为枚举值
    public void testSelectList(){
        List<User> users = userMapper.selectList(null);
        for (User user : users) {
            System.out.println(user);
        }
    }

}
