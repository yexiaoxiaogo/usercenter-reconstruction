package com.github.yexiaoxiaogo.usercenter_reconstruction.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import com.xiaokong.usercenter.business.domain.BusinessRoleRightRelation;
import com.xiaokong.usercenter.business.service.BusinessRoleRightRelationService;
import com.xiaokong.usercenter.landlord.domain.LandlordRoleRightRelation;
import com.xiaokong.usercenter.landlord.service.LandlordRoleRightRelationService;
import com.xiaokong.usercenter.platform.domain.PlatformRoleRightRelation;
import com.xiaokong.usercenter.platform.service.RoleRightService;
import com.xiaokong.usercenter.supplier.domain.SupplierRoleRightRelation;
import com.xiaokong.usercenter.supplier.service.SupplierRoleRightRelationService;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

	/**
	 * This event is executed as late as conceivably possible to indicate that the
	 * application is ready to service requests.
	 */
	@Autowired
	RedisTemplate<String, String> redisTemplate;

	@Autowired
	private RoleRightService roleRightService;

	@Autowired
	private LandlordRoleRightRelationService landlordRoleRightRelationService;

	@Autowired
	private SupplierRoleRightRelationService supplierRoleRightRelationService;
	
	@Autowired
	private BusinessRoleRightRelationService businessRoleRightRelationService;

	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {

		ValueOperations<String, String> operations = redisTemplate.opsForValue();
		// platform角色权限加入缓存
		List<PlatformRoleRightRelation> list = roleRightService.roleRightRelationsList();
		for (PlatformRoleRightRelation relation : list) {
			int roleId = relation.getRoleId();
			int rightId = relation.getRightId();
			String key = "user_center" + "-" + roleId + "-" + rightId;
			System.out.println(key);
			operations.set(key, "true");
		}
		// landlord角色权限加入缓存
		List<LandlordRoleRightRelation> list1 = landlordRoleRightRelationService.getAllRelationsList();
		for (LandlordRoleRightRelation r1 : list1) {
			int roleId = r1.getRoleId();
			int rightId = r1.getRightId();
			String key = "shop_user" + "-" + roleId + "-" + rightId;
			System.out.println(key);
			operations.set(key, "true");
		}
		// supplier角色权限加入缓存
		List<SupplierRoleRightRelation> list2 = supplierRoleRightRelationService.getAllList();
		for (SupplierRoleRightRelation r2 : list2) {
			int roleId = r2.getRoleId();
			int rightId = r2.getRightId();
			String key = "shop_supplier" + "-" + roleId + "-" + rightId;
			System.out.println(key);
			operations.set(key, "true");
		}
		// business角色权限加入缓存
		List<BusinessRoleRightRelation> list3 = businessRoleRightRelationService.getAllList();
		for (BusinessRoleRightRelation r3 : list3) {
			int roleId = r3.getRoleId();
			int rightId = r3.getRightId();
			String key = "shop_business" + "-" + roleId + "-" + rightId;
			System.out.println(key);
			operations.set(key, "true");
		}
		return;
	}

}