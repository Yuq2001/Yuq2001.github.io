package manHanLou.service;

import manHanLou.dao.BillAndMenuDao;
import manHanLou.dao.BillDao;
import manHanLou.domain.Bill;
import manHanLou.domain.BillAndMenu;
import manHanLou.domain.Menu;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

/**
 * @author 黄钰琪
 * @version 1.0
 * @time 2024/6/18 20:01
 */
public class BillService {
    private BillDao billDao = new BillDao();
    private BillAndMenuDao billAndMenuDao= new BillAndMenuDao();
    private MenuService menuService = new MenuService();
    private DinningTableService dinningTableService = new DinningTableService();

    public boolean orderMenu(int menuId, int nums, int diningTableId){
        //使用UUID方式生成一个账单号
        String billId = UUID.randomUUID().toString();
        Menu menu = menuService.getMenuById(menuId);
        if(menu == null){
            return false;
        }
        Double money = menu.getPrice()*nums;
        int rows = billDao.update("insert into bill values (null, ?, ?, ?, ?, ?, now(),'未结账')",
                billId, menuId, nums, money ,diningTableId);
        if(rows<=0){
            return false;
        }

        return dinningTableService.updateTableState(diningTableId, "就餐中");
    }

    //返回所有的账单
    public List<Bill> billList(){
        return billDao.QueryMany("select * from bill",Bill.class);
    }

    //显示账单
    public List<BillAndMenu> billAndMenuList(){
        return billAndMenuDao.QueryMany("select bill.id as id, menuId, menu.name as menuName, nums,money,diningTableId,billDate,state from bill,menu where bill.menuId = menu.id",BillAndMenu.class);
    }

    //根据餐桌号返回未结账账单
    public List<Bill> getBillByDiningTableId(int diningTableId) {
        return billDao.QueryMany("select * from bill where diningTableId = ? and state = ?",Bill.class,diningTableId,"未结账");
    }

    //修改账单状态
    public int[] updateBills(List<Bill> bills){
        int[] tmp = new int[bills.size()];
        for (int i = 0; i < bills.size(); i++) {
            tmp[i] = billDao.update("update bill set state = ? where diningTableId = ?", bills.get(i).getState(), bills.get(i).getDiningTableId());
        }
        return tmp;
    }



}
