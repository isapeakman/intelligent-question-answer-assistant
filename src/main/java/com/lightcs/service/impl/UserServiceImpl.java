package com.lightcs.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lightcs.common.enums.ErrorCode;
import com.lightcs.common.exception.BusinessException;
import com.lightcs.model.dto.user.UserQueryRequest;
import com.lightcs.model.entity.User;
import com.lightcs.model.vo.LoginUserVO;
import com.lightcs.model.vo.UserVO;
import com.lightcs.service.UserService;
import com.lightcs.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.lightcs.common.constant.UserConstants.USER_LOGIN_STATUS;
import static com.lightcs.common.enums.UserRoleEnum.ADMIN;
import static com.lightcs.common.enums.UserRoleEnum.getEnumByValue;

/**
* @author chinese
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2024-11-25 11:53:32
*/
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public Long userRegister(String userAccount, String userPassword) {

        // 锁定账户
        synchronized (userAccount.intern()){
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_account",userAccount);
            Long count = userMapper.selectCount(queryWrapper);//获取数目
            if(count>0){
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"该账户名已存在");
            }
            User user = new User();
            user.setUserAccount(userAccount);
            //todo 加密
            user.setUserPassword(userPassword);
            userMapper.insert(user);  //会将插入的属性自动填充到user
            return user.getId();
        }
    }

    @Override
    public UserVO userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // todo 密码加密
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account",userAccount);
        queryWrapper.eq("user_password",userPassword);
        User user = userMapper.selectOne(queryWrapper);
        if(user==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户名或密码错误");
        }
        request.getSession().setAttribute(USER_LOGIN_STATUS,user);

        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(user,userVO);
        return userVO;
    }

    @Override
    public boolean userLogout(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute(USER_LOGIN_STATUS);
        if(user==null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        request.getSession().removeAttribute(USER_LOGIN_STATUS);
        return true;
    }

    /**
     *  获取登录用户的视图信息
     * @param request 请求
     * @return 用户视图信息
     */
    public LoginUserVO getLoginUserVO(HttpServletRequest request){
        User user = getUser(request);
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtil.copyProperties(user,loginUserVO);
        return loginUserVO;
    }

    /**
     * 获取当前登录用户信息
     * @param request 请求
     * @return 用户信息
     */
    public User getUser(HttpServletRequest request){
        // 先判断是否已登录
        User user = (User) request.getSession().getAttribute(USER_LOGIN_STATUS);
        if(user==null || user.getId()==null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 数据库查询用户信息
        Long userId = user.getId();
        User currentUser = userMapper.selectById(userId);
        if(currentUser==null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"该用户不存在");
        }
        return currentUser;
    }

    /**
     *  获取查询条件
     * @param request 请求
     * @return 查询条件
     */
    public QueryWrapper<User> getQueryWrapper(UserQueryRequest request){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        String username = request.getUsername();
        String userRole = request.getUserRole();
        String sortField = request.getSortField();
        String sorter = request.getSorter();

        queryWrapper.eq(request.getId()!=null,"id",request.getId());
        queryWrapper.like(StringUtils.isNotBlank(username),"username",request.getUsername());
        queryWrapper.eq(StringUtils.isNotBlank(userRole),"user_role",request.getUserRole());
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                "desc".equals(sorter),sortField);//1.是否进行排序；2.true表示降序排序；false表示升序排序；3.排序的字段名
        return queryWrapper;
    }

    @Override
    public Boolean isAdmin(HttpServletRequest request) {
        User user = this.getUser(request);
        return getEnumByValue(user.getUserRole()) == ADMIN;
    }
}




