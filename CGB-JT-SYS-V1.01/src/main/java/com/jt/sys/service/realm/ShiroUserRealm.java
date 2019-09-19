package com.jt.sys.service.realm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.jt.sys.dao.SysMenuDao;
import com.jt.sys.dao.SysRoleMenuDao;
import com.jt.sys.dao.SysUserDao;
import com.jt.sys.dao.SysUserRoleDao;
import com.jt.sys.entity.SysUser;
/**
 *Realm为Shiro框架中的核心业务组件之一
 *通过此对象可以完成数据业务的获取以及封装
 */
@Service
public class ShiroUserRealm extends AuthorizingRealm{
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	@Autowired  
	private SysUserRoleDao sysUserRoleDao;
	@Autowired
	private SysMenuDao sysMenuDao;


	/**
	 * 设置凭证(Credentials)匹配器
	 */
	@Override
	public void setCredentialsMatcher(
			CredentialsMatcher credentialsMatcher) {
		HashedCredentialsMatcher cMatcher=new HashedCredentialsMatcher();
		cMatcher.setHashAlgorithmName("MD5");
		//设置加密的次数(这个次数应该与保存密码时那个加密次数一致)
		//cMatcher.setHashIterations(5);
		super.setCredentialsMatcher(cMatcher);
	}

	/**在此方法中完成认证信息的获取以及封装*/
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		//1.获取用户身份对象(例如用户名)
		String username=(String)token.getPrincipal();
		System.out.println("username="+username);
		//2.基于用户名从数据库查询记录
		SysUser user=sysUserDao.findUserByUserName(username);
		//3.对查询结果进行验证,用户不存在则抛出异常
		if(user==null)
			throw new AuthenticationException("用户不存在");
		if(user.getValid()==0)
			throw new AuthenticationException("用户已被禁用");
		//4.对数据库查询出的相关信息进行封装
		ByteSource credentialsSalt=ByteSource.Util.bytes(user.getSalt());
		SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(
				user,//principal (身份对象)
				user.getPassword(), //hashedCredentials(已加密的密码)
				credentialsSalt,//credentialsSalt (盐)
				this.getName());
		System.out.println(user.getUsername()+"/"+user.getPassword());
		//5.返回封装结果(传递给认证管理器)
		return info;
	}
	
	/**在此方法中完成授权信息的获取以及封装*/
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		System.out.println("doGetAuthorizationInfo");
		//1.获取登录用户具有的权限信息
		//1.1获取用户身份对象
		SysUser user=(SysUser)principals.getPrimaryPrincipal();
		//1.2基于用户id获取角色id(可能多个):查中间表sys_user_roles;
		List<Integer> roleIds=sysUserRoleDao.findRoleIdsByUserId(user.getId());
		//1.3基于角色id获取菜单id(可能多个):查中间表sys_role_menus
		List<Integer> menuIds=sysRoleMenuDao.findMenuIdsByRoleId(
						roleIds.toArray(new Integer[]{}));
		//1.4基于菜单id获取权限标识(permission sys:user:valid):查询菜单表sys_menus;
		List<String> permissions=sysMenuDao.findPermissions(
						menuIds.toArray(new Integer[]{}));
		//2.对权限信息封装
		SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
		//3.返回封装结果(授权管理器);
		Set<String> set=new HashSet<>();
 		for(String per:permissions){
			if(!StringUtils.isEmpty(per)){
				set.add(per);
			}
		} 
		info.setStringPermissions(set);
		return info;
	}
}
