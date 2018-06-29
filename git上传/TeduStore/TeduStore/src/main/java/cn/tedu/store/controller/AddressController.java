package cn.tedu.store.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.store.bean.Address;
import cn.tedu.store.bean.ResponseResult;
import cn.tedu.store.service.AddressService;

@Controller
@RequestMapping("/address")
public class AddressController extends BaseController{

	@Resource
	private AddressService addressService;
	
	@RequestMapping(value="/handle_add_address.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Void> handleAddAddress(Address address,HttpSession session){
		ResponseResult<Void> rr = new ResponseResult<Void>();
		address.setUid(getUidFromSession(session));
		addressService.addAddress(address);
		rr.setState(ResponseResult.STATE_OK);
		rr.setMessage("增加收获人信息成功");
		return rr;
	}
	
	
	@RequestMapping("/list.do")
	@ResponseBody
	public ResponseResult<List<Address>> getAddressList(HttpSession session){
		ResponseResult<List<Address>> rr = new ResponseResult<List<Address>>();
		Integer id = getUidFromSession(session);
		List<Address> list = addressService.getListAddress(id);
		rr.setData(list);
		rr.setState(ResponseResult.STATE_OK);
		return rr;
	}
	
	@RequestMapping("/delete.do")
	@ResponseBody
	public ResponseResult<Void> delete(Integer id,HttpSession session){
		ResponseResult<Void> rr = new ResponseResult<Void>();
		Integer uid = getUidFromSession(session);
		addressService.delte(id, uid);
		rr.setState(ResponseResult.STATE_OK);
		rr.setMessage("删除成功");
		return rr;
	}
	
	@RequestMapping("/get_address.do")
	@ResponseBody
	public ResponseResult<Address> getAddressById(Integer id,HttpSession session){
		ResponseResult<Address> rr = new ResponseResult<Address>();
		Integer uid = getUidFromSession(session);
		Address address = addressService.getAddressById(id, uid);
		if(address == null) {
			rr.setState(ResponseResult.STATE_ERROR);
			rr.setMessage("获取数据失败！请检查数据列表或书信后再次尝试");
		}else {
			rr.setData(address);
			rr.setState(ResponseResult.STATE_OK);
		}
		return rr;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/handle_update_address.do")
	@ResponseBody
	public ResponseResult<Void> handleUpdateAddress(Address address,HttpSession session){
		ResponseResult<Void> rr = new ResponseResult<Void>();
		Integer uid = getUidFromSession(session);
		address.setUid(uid);
		addressService.updateAddressById(address);
		
		rr.setState(ResponseResult.STATE_OK);
		rr.setMessage("编辑成功");
		return rr;
	}
	
	@RequestMapping("/set_default.do")
	@ResponseBody
	public ResponseResult<Void> setDefaultAddress(Integer id,HttpSession session){
		ResponseResult<Void> rr = new ResponseResult<Void>();
		Integer uid = getUidFromSession(session);
		addressService.setDefaultAddress(id, uid);
		rr.setState(ResponseResult.STATE_OK);
		rr.setMessage("修改成功");
		return rr;
	}
	
	
	
	
	
}
