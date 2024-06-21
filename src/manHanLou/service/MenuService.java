package manHanLou.service;

import manHanLou.dao.MenuDao;
import manHanLou.domain.Menu;

import java.util.List;

/**
 * @author 黄钰琪
 * @version 1.0
 * @time 2024/6/18 17:49
 */
public class MenuService {
    private MenuDao menuDao = new MenuDao();

    //业务1：显示菜单中所有菜品
    public List<Menu> getAllMenu(){
        return menuDao.QueryMany("select * from menu",Menu.class);
    }

    //需要方法，根据id返回menu对象
    public Menu getMenuById(int menuId){
        return menuDao.QuerySingle("select * from menu where id = ?",Menu.class,menuId);
    }

}
