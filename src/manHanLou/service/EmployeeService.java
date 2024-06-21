package manHanLou.service;

import manHanLou.dao.EmployeeDao;
import manHanLou.domain.Employee;

/**
 * @author 黄钰琪
 * @version 1.0
 * @time 2024/6/18 16:17
 */
public class EmployeeService {
    private EmployeeDao employeeDao = new EmployeeDao();

    //方法1：根据雇员用户名和密码返回一个Employee类的对象，如果查询不到，返回null
    public Employee getEmployeeByIdAndPwd(String empId, String pwd){
        Employee employee = employeeDao.QuerySingle("select * from employee where empId = ? and pwd = md5(?)", Employee.class, empId, pwd);
        return employee;
    }



}
