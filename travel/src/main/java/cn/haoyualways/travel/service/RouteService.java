package cn.haoyualways.travel.service;

import cn.haoyualways.travel.domain.PageBean;
import cn.haoyualways.travel.domain.Route;

public interface RouteService {
    /*
    分页功能
     */
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname);

    /**
     * 根据id查询
     * @param rid
     * @return
     */
    public Route findOne(String rid);
}
