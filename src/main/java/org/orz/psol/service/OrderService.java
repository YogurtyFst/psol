package org.orz.psol.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.orz.psol.mapper.dbMapper.OrderItemMapper;
import org.orz.psol.mapper.dbMapper.OrderMapper;
import org.orz.psol.mapper.dbMapper.ProductChoiceMapper;
import org.orz.psol.mapper.dbMapper.ProductMapper;
import org.orz.psol.model.RespBean;
import org.orz.psol.model.dbModel.Order;
import org.orz.psol.model.dbModel.OrderItem;
import org.orz.psol.model.dbModel.Product;
import org.orz.psol.model.dbModel.ProductChoice;
import org.orz.psol.utils.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class OrderService {

    @Autowired
    ProductChoiceMapper productChoiceMapper;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderItemMapper orderItemMapper;

    public RespBean checkout1(int num, String choiceId, String userId, int addressId) {
        QueryWrapper<ProductChoice> wrapper = new QueryWrapper<>();
        wrapper.eq("choice_id", choiceId);
        ProductChoice choice = productChoiceMapper.selectOne(wrapper);

        if (choice.getStorage() < num)
            return RespBean.error("库存不足");

        QueryWrapper<Product> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("id", choice.getProductId());
        Product product = productMapper.selectOne(wrapper1);

        String id = UUIDGenerator.getUUID();
        float price = num * choice.getPrice();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date datetime = new Date(System.currentTimeMillis());
        String date = sdf.format(datetime);
        orderMapper.create(id, userId, product.getStoreId(), addressId, "已发货", price , date);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(id);
        orderItem.setProductId(product.getId());
        orderItem.setChoiceId(choiceId);
        orderItem.setNumber(num);
        orderItemMapper.insert(orderItem);

        UpdateWrapper<ProductChoice> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("choice_id", choiceId);
        QueryWrapper<ProductChoice> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("choice_id",choiceId);
        ProductChoice productChoice = productChoiceMapper.selectOne(wrapper2);
        productChoice.setStorage(productChoice.getStorage()-num);
        productChoiceMapper.update(productChoice, updateWrapper);

        return RespBean.ok("订单创建成功!");
    }
}
