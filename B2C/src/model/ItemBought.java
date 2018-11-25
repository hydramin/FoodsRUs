package model;

public class ItemBought {
	private ItemBean item;
	private int quantity;
	
	public ItemBought() {
		super();		
	}

	public ItemBean getItem() {
		return item;
	}

	public void setItem(ItemBean item) {
		this.item = item;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void addQuantity(String quantity){
		try {
			int q = Integer.parseInt(quantity);
			this.setQuantity(this.quantity+q);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public String toString() {
		return "ItemBought [item=" + item + ", quantity=" + quantity + "]";
	}

	@Override
	public boolean equals(Object obj) {
		ItemBought geek = (ItemBought) obj;        
        return (this.item == geek.item);
	}
	
	public static void main(String[] args) {
		// String productId, String productName, String unitPrice,String _quantity) {
		ItemBought bean1 = Engine.getInstance().createItem("123asdf","xyz","1.23","34");
		ItemBought bean2 = Engine.getInstance().createItem("123asdf","xyzer","15.23","534");
		if(bean1.equals(bean1)) {System.out.println("equals");}else  System.out.println("not equal");
	}
}
