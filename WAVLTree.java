/**
 *
 * WAVLTree
 *
 * An implementation of a WAVL Tree with
 * distinct integer keys and info
 *Gilad Fudim - 311245450
 *Guy Mozes - 204589725
 *java8
 */
public class WAVLTree {
	public WAVLNode root = new WAVLNode();
	public int count=0;
  /**
   * public boolean empty()
   *
   * returns true if and only if the tree is empty
   *
   */
  public boolean empty()
  {
	if(this.root.info==null && this.root.rank==-1)  
		return true;
	return false;
  }
 /**
   * public String search(int k)
   *
   * returns the info of an item with key k if it exists in the tree
   * otherwise, returns null
   */
  public String search(int k)
  {
	  WAVLNode node=this.root;
	  while(true){
		  if(node.info==null && node.rank==-1){
			  return null;
		  }
		  if(node.key==k&&node.rank!=-1){
			  return node.info;
		  }
		  else if(node.key>k){
			  node=node.left;
		  }	
		  else if(node.key<k){
			  node=node.right;
		  }
	  }
  }
  /**
   * public int insert(int k, String i)
   *
   * inserts an item with key k and info i to the WAVL tree.
   * the tree must remain valid (keep its invariants).
   * returns the number of rebalancing operations, or 0 if no rebalancing operations were necessary.
   * returns -1 if an item with key k already exists in the tree.
   */
   public int insert(int k, String i) {
	   WAVLNode node=this.root;
	   /// setting up a new node if the tree is empty
	   if(node.info==null && node.rank==-1){
		   createNewNode(node,k,i);
		   return 0;
	   }
	   /// searching place for the new node
	   while(true){
		   if(node.key==k&&node.rank!=-1){//search if there is already a node with that key
			   return -1;
		   }
		   else if(node.info==null && node.rank==-1){
			   break;
		   }
		   else if(node.key>k){
			   node=node.left;
		   } 
		   else if(node.key<k){
			   node=node.right;
		   }
		}
	   createNewNode(node,k,i);//setting up a new node
	   if(node.parent.rank!=0){
		   return 0;
	   }
	   node.parent.rank+=1;
	   return 1+ChooseCase(node.parent);
	   	   
   }
   /**
   *
   * Setting up a new node with a key value k and info i
   *
   */
   public void createNewNode(WAVLNode node,int k,String i){
	   node.key=k;
	   node.info=i;
	   node.right=new WAVLNode();
	   node.right.parent=node;
	   node.left=new WAVLNode();
	   node.left.parent=node;		   
	   node.rank+=1;
	   this.count++;
   }
   /**
   *
   * Choose how to balance the tree after an insert of a new node
   * returns the number of rotations, after choosing the case of how to balance the tree 
   *
   */
   private int ChooseCase(WAVLNode node){
	   if(node.parent==null){
		   return 0;
	   }
	   else if(node.parent.left==node){//insert new left node
		   return ChooseCaseLeft(node);
	   }
	   else{//insert new right node 
		   return ChooseCaseRight(node);
	   }
   }
   /**
   *
   * Choose how to balance the tree after an insert of a new node at a right side of an other node
   * returns the number of rotations, after choosing the case of how to balance the tree 
   *
   */
   private int ChooseCaseRight(WAVLNode node){
			if(node.parent.rank==node.rank){
				if(node.rank-node.right.rank==2 && node.rank-node.left.rank==1 && node.parent.rank-node.parent.left.rank==2){//Case 3 - Double Rotation Left and then Right
					return DoubleRotation(node.left);
				}
				else if(node.rank-node.right.rank==1 && node.rank-node.left.rank==2&&node.parent.left.rank+2==node.parent.rank){//Case 2 - Single Rotation to Right
					return SingleRotation(node);
				}
				else{
					node.parent.rank+=1;
					return 1+ChooseCase(node.parent);
				}
			}  
			else{
				return 0;
			}
   }
   /**
   *
   * Choose how to balance the tree after an insert of a new node at a left side of an other node
   * returns the number of rotations, after choosing the case of how to balance the tree 
   *
   */
   private int ChooseCaseLeft(WAVLNode node){
			if(node.parent.rank==node.rank){
				if(node.rank-node.left.rank==2 && node.rank-node.right.rank==1&& node.parent.rank-node.parent.right.rank==2){//Case 3 - Double Rotation Left and then Right
					return DoubleRotation(node.right); 
				}
				else if(node.rank-node.left.rank==1 && node.rank-node.right.rank==2&&node.parent.right.rank+2==node.parent.rank){//Case 2 - Single Rotation to Right
					return SingleRotation(node);
				}
				else{
					node.parent.rank+=1;
					return 1+ChooseCase(node.parent);
				}
			} 
			else{
				return 0;
			}
   }
   /**
   *
   * Choose if to rotate to left or to the right
   * returns 1 - number of rotations 
   *
   */
   public int SingleRotation(WAVLNode node){
	   if(node.parent.left==node){//left node
		   return SingleRotationRight(node);
	   }
	   else{//right node
		   return SingleRotationLeft(node);
	   }
   }
   /**
   *
   * Single Rotation to the right after an insert of a new node
   * SingleRotationRight gets a node that has to be the root of the new tree after the rotation
   * returns 1 - number of rotations
   */
   private int SingleRotationRight(WAVLNode node){
	   //node-x will be the root
	   WAVLNode nodeb = node.right;
	   WAVLNode nodey = node.parent;
	   if(nodey.parent==null){
		   this.root=node;
	   }
	   else{
		   if(nodey.parent.right==nodey){
			   nodey.parent.right=node;
		   }
		   else{
			   nodey.parent.left=node;
		   }
	   }
	   node.parent = nodey.parent;
	   node.right=nodey;
	   nodey.parent=node;
	   nodey.left=nodeb;
	   nodeb.parent=nodey;
	   nodey.rank-=1;	
	   return 1;
   }
   /**
   *
   * Single Rotation to the left after an insert of a new node
   * SingleRotationLeft gets a node that has to be the root of the new tree after the rotation
   * returns 1 - number of rotations
   */
   private int SingleRotationLeft(WAVLNode node){
	 //node-y will be the root
	   WAVLNode nodeb = node.left;
	   WAVLNode nodex = node.parent;
	   if(nodex.parent==null){
		   this.root=node;
	   }
	   else{
		   if(nodex.parent.right==nodex){
			   nodex.parent.right=node;
		   }
		   else{
			   nodex.parent.left=node;
		   }
	   }
	   node.parent = nodex.parent;
	   node.left=nodex;
	   nodex.parent=node;
	   nodex.right=nodeb;
	   nodeb.parent=nodex;
	   nodex.rank-=1;
	   return 1;
   }
	/**
	 *
	 * Double Rotation  - choosing between the cases of the double rotation
	 * Double Rotation  gets a node that has to be the root of the new tree after the rotation
	 * returns 2 - number of rotations
	 */
   public int DoubleRotation(WAVLNode node){
	   if(node.parent.left==node){//left node
		   return DoubleRotationRightLeft(node);
	   }
	   else{//right node
		   return DoubleRotationLeftRight(node);
	   }
   }
   /**
   *
   * Double Rotation LeftRight - after an insert of a new node, does a rotation first to the left and after to the right 
   * Double Rotation LeftRight gets a node that has to be the root of the new tree after the rotation
   * returns 2 - number of rotations
   */
   private int DoubleRotationLeftRight(WAVLNode node){
	   SingleRotationLeft(node);
	   SingleRotationRight(node);
	   node.rank+=1;
	   return 2;
   }
   /**
   *
   * Double Rotation RightLeft - after an insert of a new node, does a rotation first to the left and after to the right
   * Double Rotation RightLeft gets a node that has to be the root of the new tree after the rotation
   * returns 2 - number of rotations
   */
   private int DoubleRotationRightLeft(WAVLNode node){
	   SingleRotationRight(node);
	   SingleRotationLeft(node);
	   node.rank+=1;
	   return 2;
   }
  /**
   * public int delete(int k)
   *
   * deletes an item with key k from the binary tree, if it is there;
   * the tree must remain valid (keep its invariants).
   * returns the number of rebalancing operations, or 0 if no rebalancing operations were needed.
   * returns -1 if an item with key k was not found in the tree.
   */
   public int delete(int k)
   {
	   WAVLNode node=this.root;
	   //searching for the node with key k
	   while(true){
		   	if(node.info==null && node.rank==-1){
				return -1;
			}
		   	else if(node.key==k&&node.rank!=-1){
				break;
			}
			else if(node.key>k){
				node=node.left;
			}	
			  
			else if(node.key<k){
				node=node.right;
			}
		}
	   this.count--;
	   return ChooseCaseDelete(node);
	   

   }
    /**
     *
     * ChooseCaseDelete - choosing between the cases if the node is left child or right child of his parent
     * ChooseCaseDelete gets a node that has to be removed
     * returns number of rebalancing operations
     *
     */
   private int ChooseCaseDelete(WAVLNode node){
	   if(node.parent==null){//root
		   return DeleteCaseLeft(node);
	   }
	   else if(node.parent.left==node){//left node
		   return DeleteCaseLeft(node);
	   }
	   else{//right node
		   return DeleteCaseRight(node);
	   }	
   }
    /**
     *
     * DeleteCaseLeft - removing the node from the tree and rebalancing
     * DeleteCaseLeft gets a node that has to be removed and delete him than calling the rebalance method
     * returns number of rebalancing operations
     *
     */
   private int DeleteCaseLeft(WAVLNode node){
	   if(node.parent==null){// take care of the root
		   if(node.right.info!=null){
			   WAVLNode tmp_node=node.right;
			   while(tmp_node.left.info!=null){
				   tmp_node=tmp_node.left;
			   }
			   node.info=tmp_node.info;
			   node.key=tmp_node.key;
			   tmp_node.info=null;
			   return ChooseCaseDelete(tmp_node);
		   }
		   else{
			   node.left.parent=null;
			   this.root=node.left;  
		   }
	   }
	   else if(node.parent.rank==1){//leaf
		   if(node.parent.right.rank==0 && node.rank==0){
			   node.parent.left=node.left;
			   return 0;
		   }
		   else if(node.rank==0 && node.parent.right.rank==-1){
			   node.parent.left=node.left;
			   node.parent.rank-=1;
			  return 1+ChooseRebalance(node.parent);
		   }
	   }
	   //general
	   else if(node.parent.rank>=2){
		   if(node.rank==0){///leaf
			   node.parent.left=node.left;
			   node.left.parent=node.parent;
			   return ChooseRebalance(node.left);
		   }
		   else if(node.rank==1&&node.left.rank==0&&node.right.info==null){
			   node.parent.left=node.left;
			   node.left.parent=node.parent;
			   return 0;
		   }
		   else if(node.rank==1 && node.right.rank==0&& node.left.info==null){
			   node.key=node.right.key;
			   node.info=node.right.info;
			   return ChooseCaseDelete(node.right);
		   }
		   else if(node.rank-node.left.rank==1 && node.parent.rank-node.rank==1){
			   if(node.right.info!=null){
				   WAVLNode tmp_node=node.right;
				   while(tmp_node.left.info!=null){
					   tmp_node=tmp_node.left;
				   }
				   String temp_node_info=node.info;
				   int temp_node_key=node.key;
				   node.info=tmp_node.info;
				   node.key=tmp_node.key;
				   tmp_node.info=temp_node_info;
				   tmp_node.key=temp_node_key;
				   return ChooseCaseDelete(tmp_node);
			   }
			   else{
				   node.left.parent=node.parent;
				   node.parent.left=node.left;
				   return ChooseRebalance(node.left);
			   }
		   }
		   else if(node.parent.rank-node.rank==2 &&node.rank-node.left.rank==1 && node.rank-node.right.rank==2){
			   node.left.parent=node.parent;
			   node.parent.left=node.left;
			   return ChooseRebalance(node.left);
		   }
	   }
	   return 0;
   }
   /**
   *
   * DeleteCaseRight - removing the node from the tree and rebalancing
   * DeleteCaseRight gets a node that has to be removed and delete him than calling the rebalance method
   * returns number of rebalancing operations
   *
   */
   private int DeleteCaseRight(WAVLNode node){
	   if(node.parent.rank==1){//leaf
		   if(node.parent.left.rank==0 && node.rank==0){
			   node.parent.right=node.right;
			   return 0;
		   }
		   else if(node.rank==0 && node.parent.left.rank==-1){
			   node.parent.right=node.right;
			   node.parent.rank-=1;
			  return 1+ ChooseRebalance(node.parent);
		   }
	   }
	   //general
	   else if(node.parent.rank>=2){
		   if(node.rank==0){///leaf
			   node.parent.right=node.right;
			   node.right.parent=node.parent;
			   return ChooseRebalance(node.right);
		   }
		   else if(node.rank==1&&node.right.rank==0&&node.left.info==null){
			   node.parent.right=node.right;
			   node.right.parent=node.parent;
			   return 0;
		   }		   
		   else if(node.rank-node.right.rank==1 && node.parent.rank-node.rank==1){
			   if(node.left.info!=null){
				   WAVLNode tmp_node=node.left;
				   while(tmp_node.right.info!=null){
					   tmp_node=tmp_node.right;
				   }
				   String temp_node_info=node.info;
				   int temp_node_key=node.key;
				   node.info=tmp_node.info;
				   node.key=tmp_node.key;
				   tmp_node.info=temp_node_info;
				   tmp_node.key=temp_node_key;
				   return ChooseCaseDelete(tmp_node);
			   }
			   else{
				   node.right.parent=node.parent;
				   node.parent.right=node.right;
				   return ChooseRebalance(node.right);
			   }
		   }
		   else if(node.parent.rank-node.rank==2 &&node.rank-node.right.rank==1 && node.rank-node.left.rank==2){
			   node.right.parent=node.parent;
			   node.parent.right=node.right;
			   return ChooseRebalance(node.right);
		   }
	   }
	   return 0;
   }
   /**
    * 
    * Choosing the option to re-balance the tree after deleting a node 
    * this function chooses the side of the tree that needs to be re-balanced
    * returns number of rebalancing operations
    * 
    */
   private int ChooseRebalance(WAVLNode node){
	   if(node.parent==null){
		   return 0;
	   }
	   else if(node.parent.left==node){
		   return RebalanceLeft(node);
	   }
	   else{
		   return RebalanceRight(node);
	   }
   }
   /**
    * 
    * Choosing the case how to re-balance the tree after deleting a node 
    * returns number of rebalancing operations
    * 
    */
   private int RebalanceLeft(WAVLNode node) {
	   if(node.parent.rank-node.rank==3 && node.parent.rank-node.parent.right.rank==2){
		   node.parent.rank-=1;
		   return 1+ ChooseRebalance(node.parent);
	   }
	   else if(node.parent.rank-node.rank==3 && node.parent.rank-node.parent.right.rank==1){
		   if(node.parent.right.rank-node.parent.right.right.rank==2 &&node.parent.right.rank-node.parent.right.left.rank==2){
			   node.parent.rank-=1;
			   node.parent.right.rank-=1;
			   return 1+ ChooseRebalance(node.parent);
		   }
		   else if(node.parent.right.rank-node.parent.right.right.rank==1){
			    SingleRotationLeft(node.parent.right);
			    node.parent.parent.rank+=1;
			    if(node.rank==-1){//1-->-1
			    	if(node.parent.right.rank==-1 && node.parent.left.rank==-1){
				    	node.parent.rank-=1;
				    	return 1+ ChooseRebalance(node.parent);
				    }
			    }
			    return 1;
		   }
		   else if(node.parent.right.rank-node.parent.right.right.rank==2 && node.parent.right.rank-node.parent.right.left.rank==1){
			   DoubleRotationRightLeft(node.parent.right.left);
			   node.parent.rank-=1;
			   node.parent.parent.rank+=1;
			   return 2;
		   }
		   
	   }   
	return 0;
   }
   /**
    * 
    * Choosing the case how to re-balance the tree after deleting a node 
    * returns number of rebalancing operations
    * 
    */
   private int RebalanceRight(WAVLNode node){
		   if(node.parent.rank-node.rank==3 && node.parent.rank-node.parent.left.rank==2){
			   node.parent.rank-=1;
			   return 1+ChooseRebalance(node.parent);
		   }
		   else if(node.parent.rank-node.rank==3 && node.parent.rank-node.parent.left.rank==1){
			   if(node.parent.left.rank-node.parent.left.left.rank==2 &&node.parent.left.rank-node.parent.left.right.rank==2){
				   node.parent.rank-=1;
				   node.parent.left.rank-=1;
				   return 1+ChooseRebalance(node.parent);
			   }
			   else if(node.parent.left.rank-node.parent.left.left.rank==1){
				    SingleRotationRight(node.parent.left);
				    node.parent.parent.rank+=1;
				    if(node.rank==-1){
				    	if(node.parent.right.rank==-1 && node.parent.left.rank==-1){
					    	node.parent.rank-=1;
					    	return 1+ ChooseRebalance(node.parent);
					    }
				    }
				    return 1;
			   }
			   else if(node.parent.left.rank-node.parent.left.left.rank==2 && node.parent.left.rank-node.parent.left.right.rank==1){
				   DoubleRotationLeftRight(node.parent.left.right);
				   node.parent.rank-=1;
				   node.parent.parent.rank+=1;
				   return 2;
			   }
		   }   
		return 0;
	   }
   /**
    * public String min()
    *
    * Returns the info of the item with the smallest key in the tree,
    * or null if the tree is empty
    */
   public String min()
   {
	   WAVLNode node=this.root;
	   while(node.left.rank!=-1){
		   node=node.left;
	   }
	   return node.info;
   }
   /**
    * public String max()
    *
    * Returns the info of the item with the largest key in the tree,
    * or null if the tree is empty
    */
   public String max()
   {
	   WAVLNode node=this.root;
	   while(node.right.rank!=-1){
		   node=node.right;
	   }
	   return node.info;
   }
  /**
   * public int[] keysToArray()
   *
   * Returns a sorted array which contains all keys in the tree,
   * or an empty array if the tree is empty.
   */
  public int[] keysToArray()
  {
	  	WAVLNode[] arr=create_nodes_arr(this.root);
	  	int[] keys= new int[this.count];
	  	for(int i = 0;i<this.count;i++){
	  		keys[i]=arr[i].key;
	  	}
	  	return keys;
  }
   /**
   * public String[] infoToArray()
   *
   * Returns an array which contains all info in the tree,
   * sorted by their respective keys,
   * or an empty array if the tree is empty.
   */
  public String[] infoToArray()
  {
	  	WAVLNode[] arr=create_nodes_arr(this.root);
	  	String[] infos= new String[this.count];
	  	for(int i = 0;i<this.count;i++){
	  		infos[i]=arr[i].info;
	  	}
	  	return infos;                   
  }
  /**
  *
  * Returns an array which contains all the nodes in the tree,
  * sorted by their respective keys,
  * or an empty array if the tree is empty.
  */
  private WAVLNode[] create_nodes_arr(WAVLNode node) {
	WAVLNode[] nodes_arr = new WAVLNode[this.count];
    if(this.count==0){
        return nodes_arr;
    }
	int i=0;
	rec_nodes_arr(nodes_arr,node,i);
	return nodes_arr;
	
  }
  private int rec_nodes_arr(WAVLNode[] nodes_arr,WAVLNode node,int i){
	  int counter=0;
	  while(node.left.rank!=-1){
		  node=node.left;
		  counter++;
	  }
	  while(counter>=0){
		  nodes_arr[i]=node;
		  i++;
		  if(node.right.rank!=-1){
			  i=rec_nodes_arr(nodes_arr,node.right,i);
		  }
		  node=node.parent;
		  counter--;
	  }
	  return i;
  }
   /**
    * public int size()
    *
    * Returns the number of nodes in the tree.
    *
    * precondition: none
    * postcondition: none
    */
   public int size()
   {	
	   return this.count;
   }
  /**
   * public class WAVLNode
   *
   * If you wish to implement classes other than WAVLTree
   * (for example WAVLNode), do it in this file, not in 
   * another file.
   * This is an example which can be deleted if no such classes are necessary.
   */
  public class WAVLNode{
	  public String info=null;
	  public int key;
	  public WAVLNode right;
	  public WAVLNode left;
	  public WAVLNode parent;
	  public int rank=-1;
  }
}