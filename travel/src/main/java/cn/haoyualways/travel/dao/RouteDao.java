package cn.haoyualways.travel.dao;

import cn.haoyualways.travel.domain.Route;

import java.util.List;

public interface RouteDao {
    /*
    根据cid查询总记录数
     */
    public int findTotalCount(int cid,String rname);

    /*
    根据cid,start,pageSize 查询当前页的数据集合
     */
    public List<Route> findByPage(int cid,int start,int pageSize,String rname);

    /**
     * 根据id查询
     * @param rid
     * @return
     */
    public Route findOne(int rid);
}
