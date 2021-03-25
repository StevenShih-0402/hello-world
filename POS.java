/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HWK2;
import java.util.*;
/**
 *
 * @author user
 */
class Item{
    int itemID; String itemName; int unitPrice;
    
    Item(){ }
    Item(int a, String b, int c){
        this.itemID = a;
        this.itemName = b;
        this.unitPrice = c;
    }
    
    public String toString(){
        return itemID + "\t" + itemName + "\t" + unitPrice;
    }
}

class Order{
    Item item; int amount;

    Order(){ }
    Order(Item i, int a){
        this.item = i;
        this.amount = a;
    }
    
    public String toString(){
        if(this.item.itemName.equals("黑咖啡(美式)\t"))
            return item.itemName + item.unitPrice + " x " + this.amount + "\t" + (item.unitPrice * this.amount);
        
        return item.itemName + "\t" + item.unitPrice + " x " + this.amount + "\t" + (item.unitPrice * this.amount);
    }
}

public class POS {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        ArrayList<Order> orderListPool = new ArrayList<>();
        System.out.println("<<<<<<<< QQ飲料店點餐系統>>>>>>>>>");
        
        while(true){

            //step.1:建立頁面OR退出
            System.out.print("(1)建立訂單(2) 離開:");
            int start = input.nextInt();
            
            if(start == 2)
                break;
            else if(start !=1){
                System.out.println("Error command.");
                break;
            }
            
            //step.2:印出菜單 
            System.out.println("-------------------------------");
            System.out.println("<菜單>\n代號(itemID)\t品名(itemName)\t單價(unitPrice)");
            ArrayList<Item> itemList = new ArrayList<>();
            
            Menu(itemList);                                                     //列印整張菜單
           
            //step.3:執行步驟
            ArrayList<Order> orderList = new ArrayList<>();
            
            while(true){
                Scanner in = new Scanner(System.in);
                System.out.print(">(1) 點餐(2) 完成(3) 取消單項(4) 取消訂單: ");
                String choose = in.nextLine();
                
                if(choose.equals("1")){//加入單項
                    
                    System.out.println(">> 輸入代號與數量:");
                    String enter = in.nextLine();
                    
                    String ref = "[\\d]{4}[\\s][\\d]+";                           //防呆設計
                    if(!(enter.matches(ref))){               
                        System.out.println("Error command");
                        continue;
                    }
                    
                    String[] num = enter.split("\\s");

                    guestAdd(num, itemList, orderList, orderListPool);      //加入
                    guestList(orderList);                                               //列印客戶訂單
                }
                     
                else if(choose.equals("2")){//顯示明細
                    System.out.println("**** 列印明細****");
                    
                    Detail(orderList);
                    
                    System.out.println("-------------------------------");
                    break;
                }
                    
                else if(choose.equals("3")){//取消單項
                    System.out.println(">> 輸入編號: ");
                    String id = in.nextLine();
                    
                    String ref = "[\\d]+";                           //防呆設計
                    if(!(id.matches(ref))){               
                        System.out.println("Error command");
                        continue;
                    }
                    
                    orderList.remove((Integer.parseInt(id)-1)); 
                    guestList(orderList);                    
                }
       
                else if(choose.equals("4")){//刪除整張訂單
                    Reset(orderList);       //不一定要，可以直接break
                    break;
                }
                else
                    System.out.println("Error command.");
            }
        }   
    }

    public static void Menu(ArrayList<Item> itemList){
        
        itemList.add(new Item(1001, "阿薩姆奶茶\t", 55));
        itemList.add(new Item(1002, "珍珠奶茶\t", 65));
        itemList.add(new Item(1005, "茉香綠茶\t", 35));
        itemList.add(new Item(1007, "烏梅汁\t", 45));
        itemList.add(new Item(1008, "黑咖啡(美式)\t", 40));
        itemList.add(new Item(1009, "拿鐵\t", 55));

        for(Item ele : itemList)
            System.out.println(ele);
        
        System.out.println();
    }
    
    public static void guestAdd(String[] num, ArrayList<Item> itemList, ArrayList<Order> orderList, ArrayList<Order> orderListPool){
        int id = Integer.parseInt(num[0]);
        int amount = Integer.parseInt(num[1]);
        
        for(int i = 0; i < itemList.size(); i++){
            if(id == itemList.get(i).itemID){
                orderList.add(new Order(itemList.get(i), amount));
                orderListPool.add(new Order(itemList.get(i), amount));
            }
        }
    }
    
    public static void guestList(ArrayList<Order> orderList){
        for(int j = 0; j < orderList.size(); j++){
            System.out.println("     " + "[" + (j+1) + "]" + orderList.get(j));
        }
    }
    
    public static void Detail(ArrayList<Order> orderList){
        int total = 0;
        for(int i = 0; i < orderList.size(); i++){ 
            System.out.println("     " + "[" + (i+1) + "]" + orderList.get(i));
            total += (orderList.get(i).item.unitPrice * orderList.get(i).amount);
        }
        System.out.println("     " + "總價" + "\t\t\t" + total);
    }
    
    public static void Reset(ArrayList<Order> orderList){
//        for(int i = 0; i < orderList.size(); i++){
//            orderList.remove(i);
//        }
            orderList.removeAll(orderList);
    }
    
}