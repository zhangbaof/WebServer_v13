package cn.tedu.store.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.bean.Address;

public interface AddressMapper {

	void addAddress(Address address);
	
	List<Address> getAddressList(Integer id);
	void delete(@Param("id")Integer id,@Param("uid")Integer uid);
	Address getAddressById(@Param("id")Integer id,@Param("uid")Integer uid);
	void updateAddressById(Address address);
	
	void setDefaultAddress(@Param("id")Integer id,@Param("uid")Integer uid);
	void cancelAllDefaultAddress(@Param("uid")Integer uid); 
	
	Integer getFirstRecoreId(Integer uid);
	Integer getRecordCountByUid(Integer uid);
	
}
