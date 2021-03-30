package col106.assignment5;

public class ItemNode implements ItemInterface{

	int itemId;
	String itemName;
	int stock;
	LinkedList<PurchaseNode> purchaseTransactions;
	DateNode latestpurchase;

	public ItemNode(int itemId, String itemName, int stock){
		this.itemId = itemId;
		this.itemName = itemName;
		this.stock = stock;
		purchaseTransactions=new LinkedList<PurchaseNode>();
		latestpurchase=new DateNode(1, 8, 1970);
	}

	public int getItemId(){
		//Enter your code here
		return this.itemId;
	}

	public  String getItemName(){
		//Enter your code here
		return this.itemName;
	}

	public int getStockLeft(){
		//Enter your code here
		return this.stock;
	}

	public void addTransaction(PurchaseNode purchaseT){
		//Enter your code here
		purchaseTransactions.add(purchaseT);
		this.stock=this.stock-purchaseT.numItemsPurchased();
		DateNode newdate=purchaseT.getDate();
		int newday=newdate.day;
		int newmon=newdate.month;
		int newyr=newdate.year;
		if(newyr>latestpurchase.year){
			latestpurchase.day=newday;
			latestpurchase.month=newmon;
			latestpurchase.year=newyr;
		}
		if(newyr==latestpurchase.year && newmon>latestpurchase.month){
			latestpurchase.day=newday;
			latestpurchase.month=newmon;
			latestpurchase.year=newyr;
		}
		if(newyr==latestpurchase.year && newmon==latestpurchase.month && newday>latestpurchase.day){
			latestpurchase.day=newday;
			latestpurchase.month=newmon;
			latestpurchase.year=newyr;
		}
	}

	public Node<PurchaseNode> getPurchaseHead(){
		//Enter your code here
		Node<PurchaseNode> ans=purchaseTransactions.getHead();
		return ans;
	}

}
