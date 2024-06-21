package manHanLou.service;

import manHanLou.dao.DinningTableDao;
import manHanLou.domain.DinningTable;

import java.util.List;

/**
 * @author 黄钰琪
 * @version 1.0
 * @time 2024/6/18 16:37
 */
public class DinningTableService {
    private DinningTableDao dinningTableDao = new DinningTableDao();

    //业务1：返回所有餐桌的编号和状态（可以直接返回餐桌对象集合吧）
    public List<DinningTable> getDinningTableInfo() {
        return dinningTableDao.QueryMany("select id,state from diningTable", DinningTable.class);
    }

    //业务2：检测该餐桌是否存在
    public boolean exitsDinningTable(int id) {
        Object o = dinningTableDao.QueryScalar("select id from diningTable where id = ?", id);
        if (o != null) {
            return true;
        } else {
            return false;
        }
    }

    //业务3：检测餐桌当下状态并返回餐桌对象
    public DinningTable getDinningTable(int id) {
        DinningTable dinningTable = dinningTableDao.QuerySingle("select * from diningTable where id = ?", DinningTable.class, id);
        return dinningTable;
    }

    //业务4：预定餐桌
    public boolean orderDinningTable(DinningTable dinningTable) {
        int update = dinningTableDao.update("update diningTable set state = ?, orderName = ?, orderTel = ? where id = ?", dinningTable.getState(), dinningTable.getOrderName(), dinningTable.getOrderTel(), dinningTable.getId());
        return update > 0 ? true : false;
    }

    //根据餐桌ID更换餐桌状态

    public boolean updateTableState(int id, String state) {
        int update = dinningTableDao.update("update diningTable set state = ? where id = ?", state, id);
        return update > 0;
    }

    //根据餐桌号置空餐桌
    public boolean cleanDiningTable(int id) {
        int update = dinningTableDao.update("update diningTable set state = ?, orderName = ?, orderTel = ? where id = ?", "空", "", "", id);
        return update > 0;
    }


}
