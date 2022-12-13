package online.liuyang1024.mybatisplus.POJO;

import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

@Data
public class Product {
    private Long id;
    private String name;
    private Integer price;


    @Version
    private Integer version;
}
