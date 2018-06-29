package cn.tedu.store.bean;


/*create table t_order_item(
id int auto_increment,
order_id int comment'订单id',
goods_id int comment'商品id',
goods_title varchar(100) comment'商品名称/标题',
goods_image varchar(500) comment'商品的图片',
goods_price int(20) comment'商品单价',
num int comment'购买数量',
primary key(id)
);*/
public class OrderItem {

	private Integer id;
	private Integer orderId;
	private Integer goodsId;
	private String goodsTitle;
	private String goodsImage;
	private Integer goodsPrice;
	private Integer num;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsTitle() {
		return goodsTitle;
	}
	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}
	public String getGoodsImage() {
		return goodsImage;
	}
	public void setGoodsImage(String goodsImage) {
		this.goodsImage = goodsImage;
	}
	public Integer getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(Integer goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((goodsId == null) ? 0 : goodsId.hashCode());
		result = prime * result + ((goodsImage == null) ? 0 : goodsImage.hashCode());
		result = prime * result + ((goodsPrice == null) ? 0 : goodsPrice.hashCode());
		result = prime * result + ((goodsTitle == null) ? 0 : goodsTitle.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((num == null) ? 0 : num.hashCode());
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderItem other = (OrderItem) obj;
		if (goodsId == null) {
			if (other.goodsId != null)
				return false;
		} else if (!goodsId.equals(other.goodsId))
			return false;
		if (goodsImage == null) {
			if (other.goodsImage != null)
				return false;
		} else if (!goodsImage.equals(other.goodsImage))
			return false;
		if (goodsPrice == null) {
			if (other.goodsPrice != null)
				return false;
		} else if (!goodsPrice.equals(other.goodsPrice))
			return false;
		if (goodsTitle == null) {
			if (other.goodsTitle != null)
				return false;
		} else if (!goodsTitle.equals(other.goodsTitle))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (num == null) {
			if (other.num != null)
				return false;
		} else if (!num.equals(other.num))
			return false;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		return true;
	}
	public OrderItem(Integer id, Integer orderId, Integer goodsId, String goodsTitle, String goodsImage,
			Integer goodsPrice, Integer num) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.goodsId = goodsId;
		this.goodsTitle = goodsTitle;
		this.goodsImage = goodsImage;
		this.goodsPrice = goodsPrice;
		this.num = num;
	}
	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", orderId=" + orderId + ", goodsId=" + goodsId + ", goodsTitle=" + goodsTitle
				+ ", goodsImage=" + goodsImage + ", goodsPrice=" + goodsPrice + ", num=" + num + "]";
	}
	
	public OrderItem() {
		
	}
	
	
	
	
	
}
