package cn.tedu.store.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.tedu.store.bean.ResponseResult;
import cn.tedu.store.bean.User;
import cn.tedu.store.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Resource
	private UserService userService;
	
	
	
	@RequestMapping("/address.do")
	public String showAddress() {
		return "address";
	}
	@RequestMapping("register.do")
	public String showRegister() {
		return "register";
	}
	@RequestMapping("login.do")
	public String showLogin() {
		
		System.out.println(11111);
		return "login";
	}
	
	@RequestMapping("/user_password.do")
	public String showUpdatePassword() {
		return "user_password";
	}
	
	
	
	
	@RequestMapping("/checkUsername.do")
	@ResponseBody
	public ResponseResult<Void> checkUsername(String username) {
		//声明返回值
		ResponseResult<Void> rr = new ResponseResult<Void>();
		//判断用户名是否已经被注册
		User user = userService.findUserByUsername(username);
		String[] messages = {"当前用户名可以正常使用","当前用户名已经被注册"};
		if(user==null) {
			//没有找到用户名匹配的记录，即没有被注册
			rr.setState(ResponseResult.STATE_OK);
			rr.setMessage(messages[0]);
		}else {
			rr.setState(ResponseResult.STATE_ERROR);
			rr.setMessage(messages[1]);
		}
		
		
		//返回
		return rr;
	}
	
	
	
	@RequestMapping(
			method=RequestMethod.POST,
			value="/checkLoginUsername.do")
	@ResponseBody
	public ResponseResult<Void> checkLoginUsername(String username) {
		
		System.out.println(username);
		
		
		//声明返回值
		ResponseResult<Void> rr = new ResponseResult<Void>();
		//判断用户名是否已经被注册
		User user = userService.findUserByUsername(username);
		String[] messages = {"用户名不存在","用户名存在"};
		
		//判断用户名是否存在
		if(user==null) {
			//没有找到用户名匹配的记录，即没有被注册
			//用户名不存在
			rr.setState(ResponseResult.STATE_ERROR);
			rr.setMessage(messages[0]);
		}else {
			//用户名存在
			rr.setState(ResponseResult.STATE_OK);
			rr.setMessage(messages[1]);
		}
		//返回
		return rr;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping("checkPhone.do")
	@ResponseBody
	public ResponseResult<Void> checkPhone(String phone){
		ResponseResult<Void> rr = new ResponseResult<Void>();
		
		if(userService.checkPhoneExists(phone)) {
			rr.setState(ResponseResult.STATE_ERROR);
			rr.setMessage("当前电话已存在");
		}else {
			rr.setState(ResponseResult.STATE_OK);
			rr.setMessage("当前电话可以正常使用");
		}
		return rr;
	}
	
	
	
	@RequestMapping("checkEmail.do")
	@ResponseBody
	public ResponseResult<Void> checkEmail(String email){
		ResponseResult<Void> rr = new ResponseResult<Void>();
		if(userService.checkEmailExists(email)) {
			rr.setState(ResponseResult.STATE_ERROR);
			rr.setMessage("当前邮箱已存在");
		}else {
			rr.setState(ResponseResult.STATE_OK);
			rr.setMessage("当前邮箱可以正常使用");
		}
		return rr;
	}
	
	
	
	
	@RequestMapping("/handleRegister.do")
	@ResponseBody
	public ResponseResult<Void> handleRegister(
			@RequestParam("uname")String username,
			@RequestParam("upwd")String password,
			String phone,String email,
			@RequestParam("upwdconfirm")String confirm
			){
		System.out.println("showhandleRegister");
		
		ResponseResult<Void> rr = new ResponseResult<Void>();
		userService.register(username, password, phone, email);
		rr.setState(ResponseResult.STATE_OK);
		return rr;
	}
	
	@RequestMapping("/handleLogin.do")
	@ResponseBody
	public ResponseResult<Void> handleLogin(@RequestParam("lname")String username,@RequestParam("lwd")String password,HttpSession session){
		
		
		ResponseResult<Void> rr = new ResponseResult<Void>();
		User u = userService.login(username, password);
		if(u==null) {
			rr.setState(ResponseResult.STATE_ERROR);
			rr.setMessage("用户名或密码错误");
		}else {
			rr.setState(ResponseResult.STATE_OK);
			rr.setMessage("登录成功");
			session.setAttribute("uid",u.getId());
			session.setAttribute("username",username);
		}
		return rr;
	}
	
	
	
	@RequestMapping("logout.do")
	public String logout(HttpSession session) {
		session.invalidate();
		
		//session.setAttribuyte("username",null);
		//session.removeAttribute("username");
		
		return "redirect:../user/login.do";
	}
	
	
	
	@RequestMapping("/user_center.do")
	public String showUserCenter(ModelMap modelMap,HttpSession session) {
		//从Session中获取当前登录的用户的用户名
		String username = session.getAttribute("username").toString();
		//通过用户名获取查询用户的完整信息
		User user = userService.findUserByUsername(username);
		modelMap.addAttribute("user",user);
		return "user_center";
	}
	
	
	@RequestMapping("update_user_info.do")
	@ResponseBody
	public ResponseResult<Void> updateUserInfo(String username,String phone,String email,HttpSession session){
		ResponseResult<Void> rr = new ResponseResult<Void>();
		System.out.println(username==null);
		Integer id = Integer.valueOf(session.getAttribute("uid").toString());
		userService.updateUserInfo(username, phone, email, id);
		rr.setState(1);
		System.out.println(username);
		System.out.println(phone);
		System.out.println(email);
		return rr;
	}
	
	
		@RequestMapping(method=RequestMethod.POST,value="update_password.do")
		@ResponseBody
		public ResponseResult<Void> handleUpdatePassword(
		    @RequestParam(value="old_password") String oldPassword,
		    @RequestParam(value="new_password") String newPassword,
		    HttpSession session) {
		    // 声明结果
		    ResponseResult<Void> rr = new ResponseResult<Void>();

		    // 从Session中获取当前登录的用户的id
		    Integer uid = Integer.valueOf(
		        session.getAttribute("uid").toString());
		    // 调用userService执行，并获取结果
		    int state = userService.updatePassword(
		        uid, oldPassword, newPassword);
		    // 处理结果
		    String message = state == 1 ? "修改密码成功" : "修改密码失败！原密码错误";
		    rr.setState(state);
		    rr.setMessage(message);

		    // 返回结果
		    return rr;
		}
	
	
		/**
		 * 下载Excel：xlsx
		 * application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
		 */
		@RequestMapping(value="downloadExcel.do",produces="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
		@ResponseBody
		public byte[] downloadExcel(HttpServletResponse response) throws Exception {
			response.setHeader("Content-Disposition","attachment;filename=\"ok.xlsx\"");
			byte[] bytes = createExcel();
			return bytes;
		}
		/**
		 * 利用Excel API POI 创建Excel对象
		 */
		private byte[] createExcel() throws IOException{
			//创建工作簿
			XSSFWorkbook workbook = new XSSFWorkbook();
			//在工作簿中添加工作表
			XSSFSheet sheet1 = workbook.createSheet("花名册");
			//在工作表中添加两行
			XSSFRow head = sheet1.createRow(0);
			XSSFRow row = sheet1.createRow(1);
			//第一行作为表头
			XSSFCell c0 = head.createCell(0);
			//表头第一个各自添加“编号”
			c0.setCellValue("编号");
			head.createCell(1).setCellValue("姓名");
			head.createCell(2).setCellValue("年龄");
			
			row.createCell(0).setCellValue(1);
			row.createCell(1).setCellValue("范传奇");
			row.createCell(2).setCellValue(12);
			//将Excel对象保存为bytes
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			workbook.write(out);
			
			out.close();
			byte[] bytes = out.toByteArray();
			return bytes;
			
		}
		
		
		
		/**
		 * 弹框提示下在图片
		 * Content-Disposition:attachment;filename="fname.ext"
		 * @throws Exception 
		 */
		@RequestMapping(value="downloadImage.do",produces="image/png")
		@ResponseBody
		public byte[] downloadImage(HttpServletResponse response) throws Exception {
			response.setHeader("Content-Disposition","attachment;filename=\"ok.png\"");
			byte[] bytes = createPng("OK");
			
			/*response.setHeader("Content-Disposition","attachment;filename=\"ok.png\"");
			File file = new File("src/main/webapp/web/404.html");
			byte[] bytes = new byte[(int) file.length()];
			FileInputStream fis = new FileInputStream(file);
			int len = fis.read(bytes);
			System.out.println(len);
			fis.close();*/
			return bytes;
		}
		
		
		
		
		/**
		 * 生成验证码图片控制器
		 * 其中value="code.do"用于映射URL
		 * consumes="image/png"用于设置响应头中的Content-Type属性
		 * 返回值byte[]会被注解@ResponseBody自动处理放到响应消息的Body
		 * 中发送到客户端
		 * @ResponseBody 还会根据byte[]的长度自动设置响应头部的Content-Length属性
		 * @return
		 * @throws IOException 
		 */
		@RequestMapping(value="code.do",produces="image/png")
		@ResponseBody
		public byte[] code(HttpSession session) throws IOException {
			String code = genCode(4);
			session.setAttribute("code", code);
			byte[] png = createPng(code);
			
			return png;
		}
		
		private byte[] createPng(String code) throws IOException {
			//1.利用BufferedImage创建img对象
			BufferedImage img = new BufferedImage(100,40,BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D g = img.createGraphics();
			Random random = new Random();
			//生成随机颜色
			Color c = new Color(random.nextInt(0xffffff));
			//设置背景生成色
			g.setColor(c);
			//填充颜色
			g.fillRect(0, 0, 100, 40);
			
			for(int i=0;i<500;i++) {
				int x = random.nextInt(100);
				int y = random.nextInt(40);
				int rgb = random.nextInt(0xffffff);
				img.setRGB(x, y, rgb);
			}
			//设置平滑抗锯齿绘制
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			//设置字体大小
			g.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,30));
			//设置字体随机色
			g.setColor(new Color(random.nextInt(0xffffff)));
			
			g.drawString(code, 10, 30);
			//随机绘制10条线段
			for(int i = 0;i<10;i++) {
				int x1 = random.nextInt(100);
				int y1 = random.nextInt(40);
				int x2 = random.nextInt(100);
				int y2 = random.nextInt(40);
				g.drawLine(x1,y1,x2,y2);
			}
			
			
			//2.利用ImageIO将img编码为png
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(img, "png", out);
			byte[] bytes = out.toByteArray();
			out.close();
			return bytes;
		}
		private static String chs = "345678abcdefhjkmnpqrstuvwxABCDEFGHJL";
		private String genCode(int length) {
			char[] code = new char[length];
			Random random = new Random();
			for(int i = 0;i<code.length;i++) {
				code[i] = chs.charAt(random.nextInt(chs.length()));
			}
			return new String(code);
			
		}
		
		
		@RequestMapping("checkCode.do")
		@ResponseBody
		public ResponseResult<Void> checkCode(String code,HttpSession session){
			ResponseResult<Void> rr = new ResponseResult<Void>();
			String c = (String) session.getAttribute("code");
			if(c!=null&&c.equalsIgnoreCase(code)) {
				rr.setState(ResponseResult.STATE_OK);
				rr.setMessage("验证码通过");
			}else {
				rr.setState(ResponseResult.STATE_ERROR);
				rr.setMessage("验证码错误");
			}
			return rr;
		}
		
		
		
		
		@RequestMapping("uploadForm.do")
		public String uploadForm() {
			return "upload";
		}
		
		
		@RequestMapping(value="upload.do",method=RequestMethod.POST)
		@ResponseBody
		public ResponseResult<Void> upload(@RequestParam("userfile1")MultipartFile image,
				@RequestParam("username")String username,
				HttpServletRequest request) throws Exception{
			//打桩输出上载结果
			System.out.println(username);
			System.out.println(image);
			//获取上载文件信息
			System.out.println(image.getContentType());
			System.out.println(image.getName());
			System.out.println(image.getOriginalFilename());
			System.out.println(image.getSize());
			//保存到文件系统
			String path = "/images/upload";//web路径
			
			//创建文件夹
			path = request.getServletContext().getRealPath(path);
			System.out.println(path);
			File dir = new File(path);
			dir.mkdir();
			File file = new File(path,image.getOriginalFilename());
			image.transferTo(file);
			ResponseResult<Void> rr = new ResponseResult<Void>();
			rr.setState(ResponseResult.STATE_OK);
			rr.setMessage("上载成功");
			return rr;
		}
		
		@RequestMapping(value="uploadImages.do",method=RequestMethod.POST)
		@ResponseBody
		public ResponseResult<Void> upload(@RequestParam("images")MultipartFile[] images,
				HttpServletRequest request) throws Exception{
			//打桩输出上载结果
			System.out.println(images);
			//获取上载文件信息
			//保存到文件系统
			
			String path = "/images/upload";//web路径
			path = request.getServletContext().getRealPath(path);
			File dir = new File(path);
			dir.mkdir();
			for(MultipartFile image :images) {
				
				File file = new File(path,image.getOriginalFilename());
				image.transferTo(file);
				
				System.out.println(image.getContentType());
				System.out.println(image.getName());
				System.out.println(image.getOriginalFilename());
				System.out.println(image.getSize());
				
			}
			
			//创建文件夹
			System.out.println(path);
			ResponseResult<Void> rr = new ResponseResult<Void>();
			rr.setState(ResponseResult.STATE_OK);
			rr.setMessage("上载成功");
			return rr;
		}
		
}





















