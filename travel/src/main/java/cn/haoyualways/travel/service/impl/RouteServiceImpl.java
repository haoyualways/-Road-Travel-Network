package cn.haoyualways.travel.service.impl;

import cn.haoyualways.travel.dao.FavoriteDao;
import cn.haoyualways.travel.dao.RouteDao;
import cn.haoyualways.travel.dao.RouteImgDao;
import cn.haoyualways.travel.dao.SellerDao;
import cn.haoyualways.travel.dao.impl.FavoriteDaoImpl;
import cn.haoyualways.travel.dao.impl.RouteDaoImpl;
import cn.haoyualways.travel.dao.impl.RouteImgDaoImpl;
import cn.haoyualways.travel.dao.impl.SellerDaoImpl;
import cn.haoyualways.travel.domain.PageBean;
import cn.haoyualways.travel.domain.Route;
import cn.haoyualways.travel.domain.RouteImg;
import cn.haoyualways.travel.domain.Seller;
import cn.haoyualways.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();

    private RouteDao routeDao = new RouteDaoImpl();

    private RouteDao dao = new RouteDaoImpl();

    private SellerDao sellerDao = new SellerDaoImpl();

    private FavoriteDao favoriteDao  = new FavoriteDaoImpl();

    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String ranem) {
        //封装PageBean
        PageBean<Route> pb = new PageBean<Route>();
        //设置当前页码
        pb.setCurrentPage(currentPage);
        //设置每页显示条数
        pb.setPageSize(pageSize);

        //设置总记录数
        int totalCount = dao.findTotalCount(cid,ranem);
        pb.setTotalCount(totalCount);
        //设置当前页显示的数据集合
        int start = (currentPage - 1) * pageSize;//开始的记录数
        List<Route> list = dao.findByPage(cid,start,pageSize,ranem);
        pb.setList(list);
        //设置总页数 = 总记录数/每页显示条数
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize :(totalCount / pageSize) + 1 ;
        pb.setTotalPage(totalPage);

        return pb;
    }

    @Override
    public Route findOne(String rid) {
        //1.根据id去route表中查询route对象
        Route route = routeDao.findOne(Integer.parseInt(rid));

        //2.根据route的id 查询图片集合信息
        List<RouteImg> routeImgList = routeImgDao.findByRid(route.getRid());
        //2.2将集合设置到route对象
        route.setRouteImgList(routeImgList);
        //3.根据route的sid（商家id）查询商家对象
        Seller seller = sellerDao.findById(route.getSid());
        route.setSeller(seller);

        //4. 查询收藏次数
        int count = favoriteDao.findCountByRid(route.getRid());
        route.setCount(count);


        return route;
    }
}
