package cn.haoyualways.travel.service.impl;

import cn.haoyualways.travel.dao.CategoryDao;
import cn.haoyualways.travel.dao.impl.CategoryDaoImpl;
import cn.haoyualways.travel.domain.Category;
import cn.haoyualways.travel.service.CategoryService;
import cn.haoyualways.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao dao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        //从redis中查询
        Jedis jedis = JedisUtil.getJedis();
        //判断查询的集合是否为空
        //Set<String> category = jedis.zrange("category", 0, -1);
        Set<Tuple> category = jedis.zrangeWithScores("category", 0, -1);
        List<Category> cs = null;
        //判断查询的集合是否为空
        if (category == null || category.size() == 0){
            System.out.println("从数据库中查询");
            //如果为空,查询数据库,并将数据存入Redis
            //从数据库查询
            cs = dao.findAll();
            //将集合存入redis中,
            for (int i = 0; i < cs.size() ; i++) {
                jedis.zadd("category",cs.get(i).getCid(),cs.get(i).getCname());
            }
        }else {
            System.out.println("从Jedis中查询");
            cs = new ArrayList<Category>();
            for (Tuple tuple : category) {
                Category categorys = new Category();
                categorys.setCname(tuple.getElement());
                categorys.setCid((int) tuple.getScore());
                cs.add(categorys);
            }
        }
        //如果为空
        return cs;
    }
}
