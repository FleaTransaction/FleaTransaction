package cn.fleatransaction.mapper;



import cn.fleatransaction.common.Dot.labelDto;
import cn.fleatransaction.entity.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProductMapper extends BaseMapper<Product> {

    /**
     * @param id
     * @return
     */
    @Select("SELECT label_name FROM label")
    List<String> getLabel();

    @Select("SELECT child_label_name FROM child_label,label where child_label.label_id=label.label_id AND label_name=#{label_name}")
    List<String> getChildLabel(@Param("label_name") String label_name);
}
