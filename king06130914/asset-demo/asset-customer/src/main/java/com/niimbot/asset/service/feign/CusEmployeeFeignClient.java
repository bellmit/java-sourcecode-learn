package com.niimbot.asset.service.feign;

import com.niimbot.asset.framework.utils.PageUtils;
import com.niimbot.system.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 员工管理feign客户端
 *
 * @author xie.wei
 * @Date 2020/11/12
 */
@FeignClient(url = "${feign.remote}", name = "asset-demo")
public interface CusEmployeeFeignClient {

    @GetMapping("server/system/employee/adminInfo")
    CusEmployeeDto getAdminInfo();

    /**
     * 新增员工
     *
     * @param employeeDto
     * @return 是否成功
     */
    @PostMapping(value = "server/system/employee")
    Boolean save(CusEmployeeOptDto employeeDto);

    /**
     * 修改员工
     *
     * @param employeeDto
     * @return 是否成功
     */
    @PutMapping(value = "server/system/employee")
    Boolean edit(CusEmployeeOptDto employeeDto);

    /**
     * 删除员工
     *
     * @param employ
     * @return 是否删除成功
     */
    @DeleteMapping(value = "server/system/employee")
    Boolean remove(RemoveEmployDto employ);

    /**
     * 根据条件查询员工分页列表
     *
     * @param dto 查询参数
     * @return 列表信息
     */
    @GetMapping(value = "server/system/employee/list")
    List<CusEmployeeDto> list(@SpringQueryMap CusEmployeeQueryDto dto);

    /**
     * 根据条件查询员工分页列表
     *
     * @param dto 查询参数
     * @return 列表信息
     */
    @GetMapping(value = "server/system/employee/act/list")
    List<CusEmployeeDto> actList(@SpringQueryMap CusEmployeeQueryDto dto);


    /**
     * 根据条件查询员工分页列表
     *
     * @param dto 查询参数
     * @return 列表信息
     */
    @GetMapping(value = "server/system/employee/page")
    PageUtils<CusEmployeeDto> page(@SpringQueryMap CusEmployeeQueryDto dto);

    /**
     * 新增获取员工推荐工号
     *
     * @return 推荐工号
     */
    @GetMapping(value = "server/system/employee/recommendEmpNo")
    String recommendEmpNo();

    /**
     * 检查手机号是否存在
     *
     * @param mobile 手机号
     * @return 是否存在
     */
    @GetMapping(value = "server/system/employee/checkPhone")
    CusEmployeeDto checkMobile(@RequestParam("mobile") String mobile);

    /**
     * 检查邮箱是否存在
     *
     * @param email 邮箱
     * @return 是否存在
     */
    @GetMapping(value = "server/system/employee/checkEmail")
    CusEmployeeDto checkEmail(@RequestParam("email") String email);

    /**
     * 查询员工
     *
     * @param empId 员工Id
     * @return 员工信息
     */
    @GetMapping(value = "server/system/employee/{empId}")
    CusEmployeeDto getInfo(@PathVariable("empId") Long empId);

    /**
     * 修改用户名
     *
     * @param username 名称
     * @return 结果
     */
    @PutMapping(value = "server/system/employee/changeName")
    Boolean changeName(@RequestParam("username") String username);

    /**
     * 根据员工id集合查询员工
     *
     * @param ids 查询参数
     * @return 组织
     */
    @PostMapping(value = "server/system/employee/listByIds")
    List<CusEmployeeDto> listByIds(List<Long> ids);

    /**
     * 修改头像
     *
     * @param image image url
     * @return url
     */
    @GetMapping(value = "server/system/employee/changeImage")
    Boolean changeImage(@RequestParam("image") String image);

    /**
     * 组织员工关联查询
     *
     * @param orgIds 组织Id
     * @return 组织员工集合
     */
    @GetMapping(value = "server/system/employee/orgEmpList")
    List<CusEmployeeDto> orgEmpList(@RequestParam(value = "orgIds", required = false) List<Long> orgIds);

    /**
     * 根据组织id获取主管信息
     *
     * @param orgId 组织id
     * @return 主管信息
     */
    @GetMapping(value = "server/system/employee/getDirectorByOrgId")
    List<CusEmployeeDto> getDirectorByOrgId(@RequestParam("orgId") Long orgId);

    /**
     * 新增获取员工推荐工号
     *
     * @return 推荐工号
     */
    @GetMapping(value = "server/system/employee/getRecommendEmpNo/{companyId}")
    String getRecommendEmpNo(@PathVariable("companyId") Long companyId);

    /**
     * 获取当前用户信息
     *
     * @return 用户信息
     */
    @GetMapping(value = "server/system/employee/currentUserInfo")
    CusEmployeeDto currentUserInfo();

    /**
     * 查询会员详情
     *
     * @param companyId 企业id
     * @return 会员详情
     */
    @GetMapping("server/system/company/detail/{companyId}")
    CompanyDetailDto getCompanyDetail(@PathVariable("companyId") Long companyId);

    /**
     * 修改邮箱
     *
     * @param email 邮箱
     * @return 结果
     */
    @PutMapping("server/system/employee/changeEmail/{email}")
    Boolean changeEmail(@PathVariable("email") String email);

}
