package col106.assignment5;
import java.util.Comparator;

public class StockMgmt implements StockMgmtInterface {
	//!!!!!!!*********DON'T CREATE YOUR OWN OBJECTS OF LLMergeSort*********!!!!!!!
	//use these only this object to sort
	LLMergeSort mergeSortobj;

	LinkedList<CategoryNode> categories;

	//DO NOT MNODIFY THIS CONSTRUCTOR
	public StockMgmt() {

		mergeSortobj = new LLMergeSort();
		categories = new LinkedList<CategoryNode>();

		categories.add(new CategoryNode(1, "mobile"));
		categories.add(new CategoryNode(2, "utensils"));
		categories.add(new CategoryNode(3, "sanitary"));
		categories.add(new CategoryNode(4, "medicalEquipment"));
		categories.add(new CategoryNode(5, "clothes"));

	}

	public void addItem(int categoryId, int itemId, String itemName, int stock) {
		//Your code goes here
		Node<CategoryNode> headc=categories.getHead();
		while(headc!=null){
			CategoryNode c=headc.getData();
			if(c.categoryId==categoryId){
				LinkedList<ItemNode> ls=c.getLinkedListOfCategory();
				ItemNode ob=new ItemNode(itemId, itemName, stock);
				ls.add(ob);
				break;
			}
			headc=headc.next;
		}
	}

	public void addItemTransaction(int categoryId, int itemId, String itemName, int numItemPurchased, int day, int month, int year) {
		//Your code goes here
		Node<CategoryNode> headc=categories.getHead();
		while(headc!=null){
			CategoryNode c=headc.getData();
			if(c.getCategoryId()==categoryId){
				Node<ItemNode> hi=c.itemList.getHead();
				while(hi!=null){
					ItemNode it=hi.getData();
					if(it.itemId==itemId){
						PurchaseNode pn=new PurchaseNode(numItemPurchased, day, month, year);
						it.addTransaction(pn);
						break;
					}
					hi=hi.next;
				}
				break;
			}
			headc=headc.next;
		}
	}

	//Query 1
	public LinkedList<ItemNode> sortByLastDate() {
		//Your code goes here
		LinkedList<ItemNode> ans=new LinkedList<ItemNode>();
		Node<CategoryNode> headc=categories.getHead();
		while(headc!=null){
			Node<ItemNode> headi=headc.getData().itemList.getHead();
			while(headi!=null){
				ans.add(headi.getData());
				headi=headi.next;
			}
			headc=headc.next;
		}
		Comparator<ItemNode> c=new Comparator<ItemNode>(){
			@Override
			public int compare(ItemNode a,ItemNode b){
				int d1=a.latestpurchase.day,d2=b.latestpurchase.day;
				int m1=a.latestpurchase.month,m2=b.latestpurchase.month;
				int y1=a.latestpurchase.year,y2=b.latestpurchase.year;
				if(d1==d2 && m1==m2 && y1==y2){
					int val=a.itemName.compareTo(b.itemName);
					if(val>0) return 1;
					else return 0;
				}
				else{
					if(y1>y2) return 0;
					else if(y1==y2 && m1>m2) return 0;
					else if(y1==y2 && m1==m2 && d1>d2) return 0;
					else return 1;
				}
			}
		};
		ans=mergeSortobj.MergeSort(ans, c);
		return ans;
	}

	//Query 2
	public LinkedList<ItemNode> sortByPurchasePeriod(int day1, int month1, int year1, int day2, int month2, int year2) {
		//Your code goes here
		Node<CategoryNode> headc=categories.getHead();
		LinkedList<ItemNode> ans=new LinkedList<>();
		while(headc!=null){
			Node<ItemNode> headi=headc.getData().itemList.getHead();
			while(headi!=null){
				ans.add(headi.getData());
				headi=headi.next;
			}
			headc=headc.next;
		}
		Comparator<ItemNode> c=new Comparator<ItemNode>(){
			public int compare(ItemNode a,ItemNode b){
				// find count, first and last year for every item-node
				Node<PurchaseNode> purchasea=a.getPurchaseHead();
				Node<PurchaseNode> purchaseb=b.getPurchaseHead();
				float cnta=0,cntb=0,yearla=Integer.MIN_VALUE,yearfa=Integer.MAX_VALUE;
				float yearlb=Integer.MIN_VALUE,yearfb=Integer.MAX_VALUE;
				float vala=0,valb=0;
				while(purchasea!=null){
					int day=purchasea.getData().getDate().day;
					int month=purchasea.getData().getDate().month;
					int year=purchasea.getData().getDate().year;
					if(year>=year1 && year<=year2){
						if(year==year1){
							if(month>=month1){
								if(month==month1){
									if(day>=day1){
										cnta+=purchasea.getData().numItemPurchased;
										if(yearla<year) yearla=year;
										if(yearfa>year) yearfa=year;
									}
								}
								else{
									cnta+=purchasea.getData().numItemPurchased;
									if(yearla<year) yearla=year;
									if(yearfa>year) yearfa=year;
								}
							}
						}
						else if(year==year2){
							if(month<=month2){
								if(month==month2){
									if(day<=day2){
										cnta+=purchasea.getData().numItemPurchased;
										if(yearla<year) yearla=year;
										if(yearfa>year) yearfa=year;
									}
								}
								else{
									cnta+=purchasea.getData().numItemPurchased;
									if(yearla<year) yearla=year;
									if(yearfa>year) yearfa=year;
								}
							}
						}
						else{
							cnta+=purchasea.getData().numItemPurchased;
							if(yearla<year) yearla=year;
							if(yearfa>year) yearfa=year;
						}
					}
					purchasea=purchasea.next;
				}
				if(cnta!=0){
					float diff=yearla-yearfa+1;
					vala=(float)((float)cnta/(float)diff);
					
				} 
				//System.out.print(a.itemName+" cnta "+cnta+" vala "+vala+" ");
				while(purchaseb!=null){
					int day=purchaseb.getData().getDate().day;
					int month=purchaseb.getData().getDate().month;
					int year=purchaseb.getData().getDate().year;
					if(year>=year1 && year<=year2){
						if(year==year1){
							if(month>=month1){
								if(month==month1){
									if(day>=day1){
										cntb+=purchaseb.getData().numItemPurchased;
										if(yearlb<year) yearlb=year;
										if(yearfb>year) yearfb=year;
									}
								}
								else{
									cntb+=purchaseb.getData().numItemPurchased;
									if(yearlb<year) yearlb=year;
									if(yearfb>year) yearfb=year;
								}
							}
						}
						else if(year==year2){
							if(month<=month2){
								if(month==month2){
									if(day<=day2){
										cntb+=purchaseb.getData().numItemPurchased;
										if(yearlb<year) yearlb=year;
										if(yearfb>year) yearfb=year;
									}
								}
								else{
									cntb+=purchaseb.getData().numItemPurchased;
									if(yearlb<year) yearlb=year;
									if(yearfb>year) yearfb=year;
								}
							}
						}
						else{
							cntb+=purchaseb.getData().numItemPurchased;
							if(yearlb<year) yearlb=year;
							if(yearfb>year) yearfb=year;
						}
					}
					purchaseb=purchaseb.next;
				}
				if(cntb!=0){
					float diff=yearlb-yearfb+1;
					valb=(float)((float)cntb/(float)diff);
					
				}
				//System.out.println(b.itemName+" cntb "+cntb+" valb "+valb);
				// Now we have vala and valb for comparision
				if(vala==valb){
					int temp=a.itemName.compareTo(b.itemName);
					if(temp>0) return 1;
					else return 0;
				}
				else{
					if(vala>valb) return 0;
					else return 1;
				}
			}
		};
		ans=mergeSortobj.MergeSort(ans, c);
		return ans;
	}

	//Query 3
	public LinkedList<ItemNode> sortByStockForCategory(int category) {
		//Your code goes here
		Node<CategoryNode> headc=categories.getHead();
		LinkedList<ItemNode> ans=new LinkedList<>();
		while(headc!=null){
			if(headc.getData().categoryId==category){
				Node<ItemNode> headi=headc.data.itemList.getHead();
				while(headi!=null){
					ans.add(headi.getData());
					headi=headi.next;
				}
				break;
			}
			headc=headc.next;
		}
		Comparator<ItemNode> c=new Comparator<ItemNode>(){
			@Override
			public int compare(ItemNode a,ItemNode b){
				if(a.stock==b.stock){
					int temp=a.getItemName().compareTo(b.getItemName());
					if(temp>0) return 1;
					else return 0;
				}
				else{
					if(a.stock>b.stock) return 1;
					else return 0;
				}

			}
		};
		ans=mergeSortobj.MergeSort(ans, c);
		return ans;
	}

	//Query 4
	public LinkedList<ItemNode> filterByCategorySortByDate(int category) {
		//Your code goes here
		Node<CategoryNode> headc=categories.getHead();
		LinkedList<ItemNode> ans=new LinkedList<>();
		while(headc!=null){
			if(headc.getData().categoryId==category){
				Node<ItemNode> headi=headc.data.itemList.getHead();
				while(headi!=null){
					ans.add(headi.getData());
					headi=headi.next;
				}
				break;
			}
			headc=headc.next;
		}
		Comparator<ItemNode> c=new Comparator<ItemNode>(){
			@Override
			public int compare(ItemNode a,ItemNode b){
				int d1=a.latestpurchase.day,d2=b.latestpurchase.day;
				int m1=a.latestpurchase.month,m2=b.latestpurchase.month;
				int y1=a.latestpurchase.year,y2=b.latestpurchase.year;
				if(d1==d2 && m1==m2 && y1==y2){
					int val=a.itemName.compareTo(b.itemName);
					if(val>0) return 1;
					else return 0;
				}
				else{
					if(y1>y2) return 1;
					else if(y1==y2 && m1>m2) return 1;
					else if(y1==y2 && m1==m2 && d1>d2) return 1;
					else return 0;
				}
			}
		};
		ans=mergeSortobj.MergeSort(ans, c);
		return ans;
	}

	//!!!!!*****DO NOT MODIFY THIS METHOD*****!!!!!//
	public LinkedList<ItemNode> checkMergeSort() {
		LinkedList<ItemNode> retLst = mergeSortobj.getGlobalList();
		mergeSortobj.clearGlobalList();
		return retLst;
	}
}
