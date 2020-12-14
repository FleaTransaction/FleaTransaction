package cn.fleatransaction.shiro;

import cn.fleatransaction.entity.User;
import cn.fleatransaction.service.IUserService;
import cn.fleatransaction.service.impl.UserService;
import cn.fleatransaction.util.JwtUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AccountRealm extends AuthorizingRealm {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    IUserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }



    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) authenticationToken;

        String userId = jwtUtils.getClaimByToken((String)jwtToken.getPrincipal()).getSubject();
        User user = userService.getById(Integer.valueOf(userId));
        if (user == null){
            throw new UnknownAccountException("账户不存在");
        }
        AccountProfile profile = new AccountProfile();
        BeanUtils.copyProperties(user, profile);


        return new SimpleAuthenticationInfo(profile, jwtToken.getCredentials(), getName());
    }
}
