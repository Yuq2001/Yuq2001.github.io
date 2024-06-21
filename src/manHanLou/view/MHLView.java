package manHanLou.view;

import manHanLou.domain.*;
import manHanLou.service.BillService;
import manHanLou.service.DinningTableService;
import manHanLou.service.EmployeeService;
import manHanLou.service.MenuService;
import manHanLou.utils.Utility;

import java.util.List;

/**
 * @author 黄钰琪
 * @version 1.0
 * @time 2024/6/18 13:58
 */
public class MHLView {
    private boolean loop = true; // 控制是否退出主菜单
    private EmployeeService employeeService = new EmployeeService();
    private DinningTableService dinningTableService = new DinningTableService();
    private MenuService menuService = new MenuService();
    private BillService billService = new BillService();


    //显示所有餐桌状态
    public void showDinningTableState() {
        List<DinningTable> dinningTableInfo = dinningTableService.getDinningTableInfo();
        System.out.println("餐桌编号\t\t餐桌状态");
        for (DinningTable dinningTable : dinningTableInfo) {
            System.out.println(dinningTable.getId() + "\t\t\t" + dinningTable.getState());
        }
    }

    //预定餐桌
    public void orderTable() {
        System.out.println("请输入要预定的餐桌编号(-1退出)：");
        int tableId = Utility.readInt();
        if (tableId == -1) {
            System.out.println("您已退出餐桌预定");
            return;
        }
        char confirmOrder = Utility.readConfirmSelection();
        if (confirmOrder == 'N') {
            System.out.println("您已退出餐桌预定");
            return;
        }
        if (!(dinningTableService.exitsDinningTable(tableId))) {
            System.out.println("对不起，没有这张餐桌！");
            return;
        }
        DinningTable dinningTable = dinningTableService.getDinningTable(tableId);
        if (!("空".equals(dinningTable.getState()))) {
            System.out.println("对不起，这张餐桌已经被预定！");
        }
        System.out.println("请输入预定人姓名：");
        String orderName = Utility.readString(50);
        System.out.println("请输入预定人电话：");
        String orderTel = Utility.readString(50);
        dinningTable.setState("已预定");
        dinningTable.setOrderName(orderName);
        dinningTable.setOrderTel(orderTel);
        if (!dinningTableService.orderDinningTable(dinningTable)) {
            System.out.println("数据库出错，预定失败！");
        }

    }

    //显示所有菜品
    public void showAllMenu() {
        System.out.println("菜品编号\t\t菜品名\t\t\t类别\t\t\t价格");
        List<Menu> allMenu = menuService.getAllMenu();
        for (Menu menu : allMenu) {
            System.out.println(menu.getId() + "\t\t\t" + menu.getName() + "\t\t\t" + menu.getType() + "\t\t\t" + menu.getPrice());
        }
    }

    //完成点餐
    public void orderMenu() {
        System.out.println("===========点餐服务============");
        System.out.print("请输入点餐的桌号(-1退出)：");
        int tableId = Utility.readInt();
        if (tableId == -1) {
            System.out.println("您已退出点餐！");
            return;
        }
        DinningTable dinningTable = dinningTableService.getDinningTable(tableId);
        if(dinningTable == null){
            System.out.println("对不起，没有这张餐桌！");
            return;
        }
        System.out.print("请输入点餐的菜品号(-1退出)：");
        int menuId = Utility.readInt();
        if(menuId == -1){
            System.out.println("您已退出点餐！");
            return;
        }
        Menu menuById = menuService.getMenuById(menuId);
        if(menuById == null){
            System.out.println("对不起，没有这道菜！");
            return;
        }
        System.out.print("请输入点餐的数量(-1退出)：");
        int menuNums = Utility.readInt();
        if(menuNums == -1){
            System.out.println("您已退出点餐！");
            return;
        }
        if(billService.orderMenu(menuId,menuNums,tableId)){
            System.out.println("点餐成功！");
        }else {
            System.out.println("点餐成功！");
        }
    }

    //显示账单
    public void listBill(){
        System.out.println("编号\t\t菜品号\t菜品量\t\t金额\t\t桌号\t\t下单时间\t\t\t\t\t\t状态");
        List<Bill> bills = billService.billList();
        for (Bill bill : bills) {
            System.out.println(bill.getId()+"\t\t"+bill.getMenuId()+"\t\t"+bill.getNums()+"\t\t\t"+bill.getMoney()+"\t"+bill.getDiningTableId()+"\t\t"+bill.getBillDate()+"\t\t"+bill.getState());

        }
    }

    //显示带菜品名的账单
    public void listBillAndMenu(){
        System.out.println("编号\t\t菜品号\t菜品名\t\t菜品量\t\t金额\t\t桌号\t\t下单时间\t\t\t\t\t\t状态");
        List<BillAndMenu> billAndMenus = billService.billAndMenuList();
        for (BillAndMenu billAndMenu : billAndMenus) {
            System.out.println(billAndMenu.getId()+"\t\t"+billAndMenu.getMenuId()+"\t\t"+billAndMenu.getMenuName()+"\t\t"+billAndMenu.getNums()+"\t\t\t"+billAndMenu.getMoney()+"\t"+billAndMenu.getDiningTableId()+"\t\t"+billAndMenu.getBillDate()+"\t\t"+billAndMenu.getState());
        }
    }

    //结账
    public void checkOut(){
        System.out.println("===========结账服务============");
        System.out.print("请选择要结账的餐桌编号(-1退出)：");
        int diningTableId = Utility.readInt();
        if(diningTableId == -1){
            System.out.println("您已退出结账服务！");
            return;
        }
        List<Bill> billsByDiningTableId = billService.getBillByDiningTableId(diningTableId);
        if(billsByDiningTableId.isEmpty()){
            System.out.println("此餐桌没有待结账账单！");
            return;
        }
        System.out.print("结账方式(现金/支付宝/微信)(回车退出):");
        String state = Utility.readString(50,"");
        if(state.isEmpty()){
            System.out.println("您已退出结账服务！");
            return;
        }
        char identify = Utility.readConfirmSelection();
        if(identify == 'N'){
            System.out.println("您已退出结账服务！");
            return;
        }
        for (Bill bill : billsByDiningTableId) {
            bill.setState(state);
        }
        int[] ints = billService.updateBills(billsByDiningTableId);
        for (int anInt : ints) {
            if(anInt < 0) {
                System.out.println("数据库错误，结账失败！");
                return;
            }
        }
        boolean b = dinningTableService.cleanDiningTable(diningTableId);
        if(b){
            System.out.println("餐桌"+diningTableId+"号已完成结账");
        }else {
            System.out.println("数据库错误，置空餐桌失败！");
        }
    }

    //显示主菜单
    public void mainMenu() {
        while (loop) {
            System.out.println("===========满汉楼============");
            System.out.println("\t\t1. 进入系统");
            System.out.println("\t\t2. 退出系统");
            System.out.print("请输入你的选择(1/2)：");
            int mainMenu_choose = Utility.readInt();
            switch (mainMenu_choose) {
                case 1:
                    System.out.print("请输入员工号：");
                    String empId = Utility.readString(50);
                    System.out.print("请输入密  码：");
                    String empPwd = Utility.readString(50);
                    //发送给数据库进行验证
                    Employee emp = employeeService.getEmployeeByIdAndPwd(empId, empPwd);
                    if (emp != null) {
                        System.out.println("==========登录成功[" + emp.getName() + "]===========");
                        while (loop) {
                            System.out.println("========满汉楼二级菜单=========");
                            System.out.println("\t\t1. 显示餐桌状态");
                            System.out.println("\t\t2. 预定餐桌");
                            System.out.println("\t\t3. 显示所有菜品");
                            System.out.println("\t\t4. 点餐服务");
                            System.out.println("\t\t5. 查看账单");
                            System.out.println("\t\t6. 结账");
                            System.out.println("\t\t7. 退出满汉楼");
                            System.out.print("请输入你的选择(1-7)：");
                            int secondMenu_choose = Utility.readInt();
                            switch (secondMenu_choose) {
                                case 1:
                                    showDinningTableState();
                                    break;
                                case 2:
                                    orderTable();
                                    break;
                                case 3:
                                    showAllMenu();
                                    break;
                                case 4:
                                    orderMenu();
                                    break;
                                case 5:
//                                    listBill();
                                    listBillAndMenu();
                                    break;
                                case 6:
                                    checkOut();
                                    break;
                                case 7:
                                    System.out.println("\t\t7. 退出满汉楼");
                                    loop = false;
                                    break;
                                default:
                                    System.out.println("输入有误，请重新输入");
                            }
                        }
                    } else {
                        System.out.println("登录失败，请重新登录");
                    }
                    break;
                case 2:
                    loop = false;
                    break;
                default:
                    System.out.println("输入有误，请重新输入");
            }
        }
    }

    public static void main(String[] args) {
        new MHLView().mainMenu();
    }

}
