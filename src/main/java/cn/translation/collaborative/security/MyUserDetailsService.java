package cn.translation.collaborative.security;

import cn.translation.collaborative.mapper.MembersMapper;
import cn.translation.collaborative.pojo.Members;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private MembersMapper membersMapper;
    /**
     * 用户认证方法
     * @param s 客户端登陆请求中的登录名
     * @return UserDetail接口的实现类型，一般不会特意创建一个自定义的失恋类型，经常采用security剔红的实现类User
     * @throws UsernameNotFoundException
     * User在构造的时候，一般使用3个参数的构造方法。
     * username 用户的登录名，可以使用请求传递的和查询得到的
     * password 使用查询得到的登录密码，因为security会在当前方法返回后，自动调用passwordEncode中的校验逻辑，
     * authorities 一般使用security剔红的工具类，将查询道德权限信息创建为对应的集合、
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        // 1 访问数据库，查询用户
        Members member = membersMapper.selectByAccount(s);
        // 2 校验查询结果
        if (member == null){
            throw new UsernameNotFoundException("查询不到该用户");
        }
        String role = membersMapper.selectRoleByAccount(s);
        System.out.println(member.getAccount());
        // 3 返回User对象
        return new User(member.getAccount(),member.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(role));
    }
}
