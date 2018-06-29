package cn.tedu.store.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.tedu.store.bean.Address;
import cn.tedu.store.mapper.AddressMapper;

@Service("addressService")
public class AddressServiceImpl implements AddressService{
	@Resource
	private AddressMapper addressMapper;
	@Resource 
	private DictService dictService;

	public void addAddress(Address address) {
		String district = generateAddressDistrict(address);
		address.setRecvDistrict(district);
		//是否默认
		Integer n = addressMapper.getRecordCountByUid(address.getUid());
		if(n==0) {
			address.setIsDefault(1);
		}
		addressMapper.addAddress(address);
	}

	private String generateAddressDistrict(Address address) {
		String provinceName = dictService.getProvinceNamebyCode(address.getRecvProvince());
		String cityName = dictService.getCityNameByCode(address.getRecvCity());
		String areaName = dictService.getAreaNameByCode(address.getRecvArea());
		String district = provinceName+","+cityName+"," +areaName;
		return district;
	}

	public List<Address> getListAddress(Integer id) {
		List<Address> list = addressMapper.getAddressList(id);
		return list;
	}

	public void delte(Integer id, Integer uid) {
		//获取将要删除的数据的信息，先保存到内存中的变量中
		Address addr = addressMapper.getAddressById(id, uid);
		//执行删除
		addressMapper.delete(id,uid);
		//获取当前用户的收货人数据的数量
		Integer count = addressMapper.getRecordCountByUid(uid);
		//判断数量，如果为0，则不需要考虑再将那条设置为默认的问题
		if(count==0) {
			return;
		}
		//判断刚才删除的数据是否为默认收货人
		if(addr.getIsDefault()==1) {
			//默认收货人数据已经被删除，则找到现有的数据中的第一条
			Integer newDefaultId = addressMapper.getFirstRecoreId(uid);
			//设置新的默认收货人
			addressMapper.setDefaultAddress(newDefaultId, uid);
		}
		//不需要写else
		//如果刚才删除的
		
	}

	
	
	public Address getAddressById(Integer id, Integer uid) {
		return addressMapper.getAddressById(id, uid);
	}

	public void updateAddressById(Address address) {
		String district = generateAddressDistrict(address);
		address.setRecvDistrict(district);
		addressMapper.updateAddressById(address);
	}

	public void setDefaultAddress(Integer id, Integer uid) {
		addressMapper.cancelAllDefaultAddress(uid);
		addressMapper.setDefaultAddress(id, uid);
	}
	
	
}
