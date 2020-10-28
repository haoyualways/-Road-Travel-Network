package cn.haoyualways.travel.service.impl;

import cn.haoyualways.travel.dao.FavoriteDao;
import cn.haoyualways.travel.dao.impl.FavoriteDaoImpl;
import cn.haoyualways.travel.domain.Favorite;
import cn.haoyualways.travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {

    private FavoriteDao dao = new FavoriteDaoImpl();

    @Override
    public boolean isFavorite(String rid, int uid) {

        Favorite favorite = dao.findByRidAndUid(Integer.parseInt(rid), uid);

        return favorite != null ;//有收藏返回true，没有收藏返回false
    }

    @Override
    public void add(String rid, int uid) {
        dao.add(Integer.parseInt(rid),uid);
    }
}
