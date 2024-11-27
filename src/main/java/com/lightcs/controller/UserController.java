package com.lightcs.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lightcs.annotation.CheckAuth;
import com.lightcs.common.BaseResponse;
import com.lightcs.common.PaginationBuilder;
import com.lightcs.common.ResultBuilder;
import com.lightcs.enums.ErrorCode;
import com.lightcs.enums.UserRoleEnum;
import com.lightcs.exception.BusinessException;
import com.lightcs.exception.ThrowUtils;
import com.lightcs.model.dto.user.*;
import com.lightcs.model.entity.User;
import com.lightcs.model.vo.LoginUserVO;
import com.lightcs.model.vo.UserVO;
import com.lightcs.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.lightcs.enums.UserRoleEnum.ADMIN;

/**
 * @Author: peak-like
 * @Description: 用户控制层
 * @DateTime: 2024/11/25 11:59
 **/
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/current")
    public BaseResponse<LoginUserVO> current(HttpServletRequest request){
        LoginUserVO currentLoginUser = userService.getLoginUserVO(request);
        return  ResultBuilder.success(currentLoginUser);
    }
    //region 登录相关
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@Validated @RequestBody UserRegisterRequest registerRequest){
        String userPassword = registerRequest.getUserPassword();
        String confirmPassword = registerRequest.getConfirmPassword();
        // 校验密码
        if(!userPassword.equals(confirmPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"两次输入的密码不一致");
        }
        Long userId = userService.userRegister(registerRequest.getUserAccount(), userPassword);
        return  ResultBuilder.success(userId);
    }
    @PostMapping("/login")
    public BaseResponse<UserVO> userLogin(@Validated @RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
        UserVO userVO = userService.userLogin(userLoginRequest.getUserAccount(), userLoginRequest.getUserPassword(), request);
        return  ResultBuilder.success(userVO);
    }

    @GetMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request){
        boolean result = userService.userLogout(request);
        return ResultBuilder.success(result);
    }
    //endregion


    //region 用户相关
    @PostMapping("/add")
    public BaseResponse<Long> addUser(UserAddRequest userAddRequest){
        if(userAddRequest==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = new User();
        BeanUtil.copyProperties(userAddRequest,user);

        String rawPassword = "123456";
        //todo 加密
        String encryptPassword = rawPassword;
        user.setUserPassword(encryptPassword);

        boolean result = userService.save(user);
        ThrowUtils.throwIf(!result,ErrorCode.OPERATION_ERROR);
        return ResultBuilder.success(user.getId());
    }
    @DeleteMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestParam long id){
        if(id<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = userService.removeById(id);
        return ResultBuilder.success(result);
    }
    @PostMapping("/update")
    public BaseResponse<Boolean> updateUser(@RequestBody UserUpdateRequest userUpdateRequest){
        if(userUpdateRequest==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = new User();
        BeanUtil.copyProperties(userUpdateRequest,user);
        boolean result = userService.updateById(user);
        return ResultBuilder.success(result);
    }
    @GetMapping("/get/{id}")
    public BaseResponse<UserVO> getUserById(@PathVariable long id){
        if(id<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getById(id);
        ThrowUtils.throwIf(user==null,ErrorCode.NOT_FOUND_ERROR,"未找到id为"+id+"的用户");
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(user,userVO);
        return ResultBuilder.success(userVO);
    }
    //endregion


    //region 管理员相关
    @GetMapping("/list")
    @CheckAuth(mustRole = ADMIN)
    public BaseResponse<Map<String,Object>> listUser(UserQueryRequest userQueryRequest){
        int current = userQueryRequest.getCurrent();
        int pageSize = userQueryRequest.getPageSize();
        Page<User> page = userService.page(new Page<>(current,pageSize),userService.getQueryWrapper(userQueryRequest));//MP的分页查询

        Map<String, Object> result = PaginationBuilder.build(page.getRecords(), page.getTotal(), current, pageSize);
        return ResultBuilder.success(result);
    }
    @GetMapping("/getUser/{id}")
    @CheckAuth(mustRole = ADMIN)
    public BaseResponse<User> getUser(@PathVariable long id){
        if(id<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getById(id);
        ThrowUtils.throwIf(user==null,ErrorCode.NOT_FOUND_ERROR,"未找到id为"+id+"的用户");
        return ResultBuilder.success(user);
    }
    //endregion

}
