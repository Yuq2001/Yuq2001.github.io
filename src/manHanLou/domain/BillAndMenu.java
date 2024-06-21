package manHanLou.domain;

import java.util.Date;

/**
 * @author 黄钰琪
 * @version 1.0
 * @time 2024/6/19 15:04
 */
public class BillAndMenu {
    private Integer id;
    private Integer menuId;
    private String menuName;
    private Integer nums;
    private Double money;
    private Integer diningTableId;
    private Date billDate;
    private String state;

    public BillAndMenu() {
    }

    public BillAndMenu(Integer id, Integer menuId, String menuName, Integer nums, Double money, Integer diningTableId, Date billDate, String state) {
        this.id = id;
        this.menuId = menuId;
        this.menuName = menuName;
        this.nums = nums;
        this.money = money;
        this.diningTableId = diningTableId;
        this.billDate = billDate;
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getNums() {
        return nums;
    }

    public void setNums(Integer nums) {
        this.nums = nums;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Integer getDiningTableId() {
        return diningTableId;
    }

    public void setDiningTableId(Integer diningTableId) {
        this.diningTableId = diningTableId;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "BillAndMenu{" +
                "id=" + id +
                ", menuId=" + menuId +
                ", menuName='" + menuName + '\'' +
                ", nums=" + nums +
                ", money=" + money +
                ", diningTableId=" + diningTableId +
                ", billDate=" + billDate +
                ", state='" + state + '\'' +
                '}';
    }
}
