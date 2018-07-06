package com.github.yexiaoxiaogo.usercenter_reconstruction.aop;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import com.xiaokong.core.base.Result;
import com.xiaokong.core.contant.ResultCode;
import com.xiaokong.core.utils.JsonUtils;
import com.xiaokong.usercenter.business.domain.BusinessUser;
import com.xiaokong.usercenter.business.service.BusinessRoleRightRelationService;
import com.xiaokong.usercenter.business.service.BusinessShopService;
import com.xiaokong.usercenter.business.service.BusinessUserRoleService;
import com.xiaokong.usercenter.landlord.domain.LandlordUser;
import com.xiaokong.usercenter.landlord.service.LandlordRightService;
import com.xiaokong.usercenter.landlord.service.LandlordRoleRightRelationService;
import com.xiaokong.usercenter.landlord.service.LandlordUserRoleRelationService;
import com.xiaokong.usercenter.landlord.service.LandlordRoleService;
import com.xiaokong.usercenter.platform.domain.PlatformUser;
import com.xiaokong.usercenter.platform.service.PlatformRightService;
import com.xiaokong.usercenter.platform.service.PlatformRoleService;
import com.xiaokong.usercenter.platform.service.RoleRightService;
import com.xiaokong.usercenter.platform.service.UserRoleService;
import com.xiaokong.usercenter.supplier.domain.SupplierUser;
import com.xiaokong.usercenter.supplier.service.ShopService;
import com.xiaokong.usercenter.supplier.service.SupplierRoleRightRelationService;
import com.xiaokong.usercenter.supplier.service.SupplierUserRoleService;

@Order(100)
@Aspect
@Component
public class RoleAspect {

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private LandlordUserRoleRelationService landlordUserRoleRelationService;

	@Autowired
	private LandlordRightService landlordRightService;

	@Autowired
	private LandlordRoleRightRelationService landlordRoleRightRelationService;

	@Autowired
	private LandlordRoleService landlordRoleService;

	@Autowired
	private SupplierUserRoleService supplierUserRoleService;

	@Autowired
	private SupplierRoleRightRelationService supplierRoleRightRelationService;

	@Autowired
	private ShopService shopService;

	@Autowired
	private PlatformRightService platformRightService;

	@Autowired
	private PlatformRoleService platformRoleService;

	@Autowired
	private RoleRightService roleRightService;

	@Autowired
	private BusinessUserRoleService businessUserRoleService;

	@Autowired
	private BusinessShopService businessShopService;

	@Autowired
	private BusinessRoleRightRelationService businessRoleRightRelationService;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	// 申明切点,定义拦截规则,定义一个公用方法
	@Pointcut("execution(* com.xiaokong.usercenter.*.controller.*.*CUT(..))")
	public void UserRoleService() {
		// 获得用户id来查询用户角色来判断是否有这个权限
	}

	// 拦截具体实现
	@Around(value = "UserRoleService() && args(*,module,request,..)")
	public Object doBefore(ProceedingJoinPoint proceedingJoinPoint, String module, HttpServletRequest request)
			throws Throwable {
		// 这里连接点拿到前端传过来的token，module，url信息
		String token = (String) proceedingJoinPoint.getArgs()[0];
		String url = request.getRequestURI();

		String json = redisTemplate.opsForValue().get(token);
		// 先进行用户登陆失效判断，返回用户失效信息
		if (StringUtils.isBlank(json)) {
			System.out.println("登录失效");
			return new Result<>(ResultCode.ERROR_NOT_LOGINED).setMessage("Login expired");
		}

		System.out.println("token打出来看一下获取到了没：" + token);
		System.out.println("module打出来看一下获取到了没：" + module);
		System.out.println("url打出来看一下获取到了没：" + url);

		if (isHasRight(token, module, url).isSuccess()) {
			System.out.println("权限确认后跳转回切面进行操作");
			Object object = proceedingJoinPoint.proceed();
			return object;
		}
		return new Result<>(ResultCode.ERROR_AUTH_FAILED);
	}

	public Result<ResultCode> isHasRight(String token, String module, String url) throws Throwable {

		String json = redisTemplate.opsForValue().get(token);

		// 通过判断token头部判断是那个系统的用户
		String type = token.substring(0, token.indexOf("_"));

		if (type.compareTo("PLATFORM") == 0) {
			return isPlatformHasRight(json, module, url);
		}

		if (type.compareTo("SHOP") == 0) {
			return isSupplierHasRight(json, module, url);
		}

		if (type.compareTo("LANDLORD") == 0) {
			return isLandlordHasRight(json, module, url);
		}

		if (type.compareTo("BUSINESS") == 0) {
			return isBusinessHasRight(json, module, url);
		}

		return new Result<>(ResultCode.ERROR_AUTH_FAILED);
	}

	// 判断角色
	public boolean checkRole(List<Integer> roleIds, List<Integer> roleIdList) {
		Boolean count = false;
		for (int aroleId : roleIds) {
			for (int broleId : roleIdList) {
				if (aroleId == broleId) {
					count = true;
				}
			}
		}
		return count;
	}

	public Result<ResultCode> isPlatformHasRight(String json, String module, String url) throws Throwable {

		ValueOperations<String, String> operations = redisTemplate.opsForValue();

		PlatformUser user = JsonUtils.jsonToPojo(json, PlatformUser.class);
		String userId = user.getId();
		System.out.println("是平台用户，用户id：" + userId);
		// 先判断前端传的module属于哪个模块，再去找角色和权限的module
		// 根据用户id，得到用户的角色id，用户可能有多个角色，这里返回数组，这里加入传入的module来筛选角色
		List<Integer> list = userRoleService.getRoleIdListByUserId(userId);
		System.out.println("查询用户id得到的所有角色列表：" + list);
		// 去角色表，根据module筛选角色,得到用户的角色id列表roleIds
		ArrayList<Integer> roleIds = new ArrayList<>();
		if (list.isEmpty()) {
			return new Result<>(ResultCode.ERROR_NOT_SUPPORTED);
		}
		for (int roleID : list) {
			// 角色表操作，根据role_id得到role_module，判断role_module和传入的module是否相等
			Boolean count = platformRoleService.getModuleById(roleID).equals(module);
			if (count) {
				roleIds.add(roleID);
			}
		}
		System.out.println("查询用户id得到的对应module的角色列表：" + roleIds);
		// 如果用户没有分配角色，返回ERROR_NOT_SUPPORTED操作不支持
		if (roleIds.equals(null)) {
			return new Result<>(ResultCode.ERROR_NOT_SUPPORTED);
		}
		// 这里得到了用户的角色信息，先判断用户角色owner是不是admin，如果是，直接返回通过
		for (int roleId : roleIds) {
			String owener = platformRoleService.getOwnerById(roleId);
			if (owener != null && owener.equals("admin")) {
				System.out.println("通过admin判断是否有权限：");
				return new Result<>(ResultCode.SUCCESS);
			}
		}
		// 不是admin，继续判断用户是否有该权限
		// 根据路径和module找到权限id，这里加入权限的module筛选权限
		int rightId = platformRightService.selectRightId(url, module);// SQL里面加入module判断
		System.out.println("访问路径权限的id：" + rightId);
		try {
			// 已知用户角色id=roleIds，权限id= rightId，当成一个key，存入redis，value为Boolean，判断这个关系是否存在
			// 在redis缓存里面查询，是否有角色权限关系
			for (int roleId : roleIds) {
				String key = "user_center" + "-" + roleId + "-" + rightId;
				System.out.println("缓存的值：" + operations.get(key));
				Boolean hasCache = operations.get(key).equals("true");
				System.out.println("通过缓存判断是否有权限：" + hasCache);
				if (hasCache) {
					return new Result<>(ResultCode.SUCCESS);
				}
			}
		} catch (Exception e) {
			// 如果redis挂了 查询数据库，不过redis挂了，用户就不能登陆了。可能也用不上，做个容错
			// 根据权限id得到角色id，这里角色id可能是多个，应该是数组，一个权限可以分配给多个角色，
			List<Integer> roleIdList = roleRightService.selectRoleByRight(rightId);
			System.out.println("查询权限id得到的角色列表：" + roleIdList);
			// 如果权限没有查到角色信息，返回角色没有权限
			if (roleIdList == null) {
				return new Result<>(ResultCode.ERROR_AUTH_FAILED);
			}
			// 对比通过用户得到的roleIds和通过路径得到的roleList是否重合,判断用户是否具有权限
			Boolean count = checkRole(roleIds, roleIdList);
			System.out.println("通过数据库查询是否有权限：" + count);
			if (count) {
				return new Result<>(ResultCode.SUCCESS);
			}
		}

		return new Result<>(ResultCode.ERROR_AUTH_FAILED);

	}

	public Result<ResultCode> isSupplierHasRight(String json, String module, String url) throws Throwable {
		ValueOperations<String, String> operations = redisTemplate.opsForValue();
		SupplierUser user = JsonUtils.jsonToPojo(json, SupplierUser.class);
		String userId = user.getId();
		System.out.println("是供应商用户，用户id：" + userId);
		// 先判断前端传的module属于哪个模块，再去找角色和权限的module
		// 根据用户id，得到用户的角色id，用户可能有多个角色，这里返回数组，这里加入传入的module来筛选角色
		List<Integer> list = supplierUserRoleService.getRoleIds(userId);
		if (list.isEmpty()) {
			return new Result<>(ResultCode.ERROR_NOT_SUPPORTED);
		}
		ArrayList<Integer> roleIds = new ArrayList<>();// roleIds通过用户id得到的用户角色id数组
		for (int id : list) {
			Boolean isModule = shopService.getRoleById(id).getModule().equals(module);
			if (isModule) {
				roleIds.add(id);
			}
		}
		System.out.println("查询用户id得到的角色列表：" + roleIds);
		// 如果用户没有分配角色，返回ERROR_NOT_SUPPORTED操作不支持
		if (roleIds.equals(null)) {
			return new Result<>(ResultCode.ERROR_NOT_SUPPORTED);
		}
		// 这里得到了用户的角色信息，先判断用户角色owner是不是admin，如果是，直接返回通过
		for (int roleId : roleIds) {
			String owener = shopService.getRoleById(roleId).getOwner();
			if (owener != null && owener.equals("admin")) {
				System.out.println("通过admin判断是否有权限：");
				return new Result<>(ResultCode.SUCCESS);
			}
		}
		// 不是admin，继续判断用户是否有该权限
		// 根据路径和module找到权限id，这里加入权限的module筛选权限
		int rightId = shopService.getRightIdByUrl(url, module);
		// 已知用户角色id=roleIds，权限id= rightId，当成一个key，存入redis，value为Boolean，判断这个关系是否存在
		// 在redis缓存里面查询，是否有角色权限关系
		try {
			for (int roleId : roleIds) {
				String key = "shop_supplier" + "-" + roleId + "-" + rightId;
				Boolean hasCache = operations.get(key).equals("true");
				System.out.println("通过缓存判断是否有权限：" + hasCache);
				if (hasCache) {
					return new Result<>(ResultCode.SUCCESS);
				}
			}
		} catch (Exception e) {
			// 如果redis挂了 查询数据库
			// 根据权限id得到角色id，这里角色id可能是多个，应该是数组，一个权限可以分配给多个角色
			List<Integer> roleList = supplierRoleRightRelationService.getRoleIdListByRightId(rightId);
			System.out.println("查询权限id得到的角色列表：");
			// 如果权限没有查到角色信息，返回角色没有权限
			if (roleList == null) {
				return new Result<>(ResultCode.ERROR_AUTH_FAILED);
			}
			// 对比通过用户得到的roleIds和通过路径得到的roleList是否重合,判断用户是否具有权限
			Boolean count = checkRole(roleIds, roleList);
			System.out.println("通过数据库查询是否有权限：" + count);
			if (count) {
				return new Result<>(ResultCode.SUCCESS);
			}
		}

		return new Result<>(ResultCode.ERROR_AUTH_FAILED);

	}

	public Result<ResultCode> isLandlordHasRight(String json, String module, String url) throws Throwable {

		ValueOperations<String, String> operations = redisTemplate.opsForValue();

		LandlordUser user = JsonUtils.jsonToPojo(json, LandlordUser.class);
		String userId = user.getId();
		System.out.println("是公寓用户，用户id：" + userId);
		// 先判断前端传的module属于哪个模块，再去找角色和权限的module
		// 根据用户id，得到用户的角色id，用户可能有多个角色，这里返回数组，这里加入传入的module来筛选角色id
		List<Integer> list = landlordUserRoleRelationService.getRoleIdsByUserId(userId);
		if (list.isEmpty()) {
			return new Result<>(ResultCode.ERROR_NOT_SUPPORTED);
		}
		ArrayList<Integer> roleIds = new ArrayList<>();
		for (int id : list) {
			Boolean isModule = landlordRoleService.getRoleById(id).getModule().equals(module);
			if (isModule) {
				roleIds.add(id);
			}
		}
		System.out.println("查询用户id得到的角色列表：" + roleIds);
		// 如果用户没有分配角色，返回ERROR_NOT_SUPPORTED操作不支持
		if (roleIds.equals(null)) {
			return new Result<>(ResultCode.ERROR_NOT_SUPPORTED);
		}
		// 这里得到了用户的角色信息，先判断用户角色owner是不是admin，如果是，直接返回通过
		for (int roleId : roleIds) {
			String owener = landlordRoleService.getRoleById(roleId).getOwner();
			if (owener != null && owener.equals("admin")) {
				System.out.println("通过admin判断是否有权限：");
				return new Result<>(ResultCode.SUCCESS);
			}
		}
		// 不是admin，继续判断用户是否有该权限
		// 根据路径和module找到权限id，这里加入权限的module筛选权限
		int rightId = landlordRightService.getRightIdByUrlAndModule(url, module);
		System.out.println("访问路径权限的id：" + rightId);
		try {
			// 角色id=roleIds，权限id= rightId
			// 在redis缓存里面查询，是否有角色权限关系
			for (int roleId : roleIds) {
				String key = "shop_user" + "-" + roleId + "-" + rightId;
				Boolean hasCache = operations.get(key).equals("true");
				System.out.println("通过缓存判断是否有权限：" + hasCache);
				if (hasCache) {
					return new Result<>(ResultCode.SUCCESS);
				}
			}
		} catch (Exception e) {
			// 如果redis挂了 查询数据库
			// 根据权限id得到角色id，这里角色id可能是多个，应该是数组，一个权限可以分配给多个角色
			List<Integer> roleList = landlordRoleRightRelationService.getRoleIdByRightId(rightId);
			System.out.println("查询权限id得到的角色列表：");
			// 如果权限没有查到角色信息，返回角色没有权限
			if (roleList == null) {
				return new Result<>(ResultCode.ERROR_AUTH_FAILED);
			}
			// 对比通过用户得到的roleIds和通过路径得到的roleList是否重合,判断用户是否具有权限
			Boolean count = checkRole(roleIds, roleList);
			System.out.println("通过数据库查询是否有权限：" + count);
			if (count) {
				return new Result<>(ResultCode.SUCCESS);
			}
		}
		return new Result<>(ResultCode.ERROR_AUTH_FAILED);
	}

	private Result<ResultCode> isBusinessHasRight(String json, String module, String url) throws Throwable {
		ValueOperations<String, String> operations = redisTemplate.opsForValue();
		BusinessUser user = JsonUtils.jsonToPojo(json, BusinessUser.class);
		String userId = user.getId();
		System.out.println("是商人用户，用户id：" + userId);
		// 先判断前端传的module属于哪个模块，再去找角色和权限的module
		// 根据用户id，得到用户的角色id，用户可能有多个角色，这里返回数组，这里加入传入的module来筛选角色
		List<Integer> list = businessUserRoleService.getRoleIds(userId);
		if (list.isEmpty()) {
			return new Result<>(ResultCode.ERROR_NOT_SUPPORTED);
		}
		ArrayList<Integer> roleIds = new ArrayList<>();// roleIds通过用户id得到的用户角色id数组
		for (int id : list) {
			Boolean isModule = businessShopService.getRoleById(id).getModule().equals(module);
			if (isModule) {
				roleIds.add(id);
			}
		}
		System.out.println("查询用户id得到的角色列表：" + roleIds);
		// 如果用户没有分配角色，返回ERROR_NOT_SUPPORTED操作不支持
		if (roleIds.equals(null)) {
			return new Result<>(ResultCode.ERROR_NOT_SUPPORTED);
		}
		// 这里得到了用户的角色信息，先判断用户角色owner是不是admin，如果是，直接返回通过
		for (int roleId : roleIds) {
			String owener = businessShopService.getRoleById(roleId).getOwner();
			if (owener != null && owener.equals("admin")) {
				System.out.println("通过admin判断是否有权限：");
				return new Result<>(ResultCode.SUCCESS);
			}
		}
		// 不是admin，继续判断用户是否有该权限
		// 根据路径和module找到权限id，这里加入权限的module筛选权限
		int rightId = businessShopService.getRightIdByUrl(url, module);
		// 已知用户角色id=roleIds，权限id= rightId，当成一个key，存入redis，value为Boolean，判断这个关系是否存在
		// 在redis缓存里面查询，是否有角色权限关系
		try {
			for (int roleId : roleIds) {
				String key = "shop_business" + "-" + roleId + "-" + rightId;
				Boolean hasCache = operations.get(key).equals("true");
				System.out.println("通过缓存判断是否有权限：" + hasCache);
				if (hasCache) {
					return new Result<>(ResultCode.SUCCESS);
				}
			}
		} catch (Exception e) {
			// 如果redis挂了 查询数据库
			// 根据权限id得到角色id，这里角色id可能是多个，应该是数组，一个权限可以分配给多个角色
			List<Integer> roleList = businessRoleRightRelationService.getRoleIdListByRightId(rightId);
			System.out.println("查询权限id得到的角色列表：");
			// 如果权限没有查到角色信息，返回角色没有权限
			if (roleList == null) {
				return new Result<>(ResultCode.ERROR_AUTH_FAILED);
			}
			// 对比通过用户得到的roleIds和通过路径得到的roleList是否重合,判断用户是否具有权限
			Boolean count = checkRole(roleIds, roleList);
			System.out.println("通过数据库查询是否有权限：" + count);
			if (count) {
				return new Result<>(ResultCode.SUCCESS);
			}
		}

		return new Result<>(ResultCode.ERROR_AUTH_FAILED);

	}

}
