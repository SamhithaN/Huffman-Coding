package huffman;

/*
PROGRAM TO DEMONSTRATE HUFFMAN CODING
INPUT: .txt file (containing Input String to be encoded)
OUTPUT: .txt file that contains the encoded version of Input.
*/

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class Node
{
	char ch;  //an individual character in the Input String
	int freq; 
	
	Node left;  //reference to left child
	Node right; //reference to right child
}

class frequency_comparison implements Comparator<Node>
{
	public int compare(Node x, Node y)
	{
		return x.freq - y.freq;
	}
}


//To store the codes generated for the characters while traversing the Huffman Tree
class code_map
{
	Character alpha;
	String code;
	int i=0;
}
class source
{
	
	static code_map C[];   //to keep a record of the generated codes
	static int iterate=0;
	
	//Traversal of the Huffman Tree for generation of respective Huffman Codes 
	public static void display_code(Node root, String s) 
   { 
 
       if (root.left  == null && root.right== null  && (root.ch!='#' ))
       { 
       
           System.out.println(root.ch + ":" + s);  
           
           C[iterate]=new code_map();
           C[iterate].alpha=root.ch;
           C[iterate].code=s;
           iterate++;
       	
           return; 
          
        } 
 
       // if we go to left then add "0" to the Huffman code. 
       // if we go to the right add"1" to the Huffman code. 
 
       // recursive calls for left and right sub-tree of the generated tree. 
       
       display_code(root.left,s+"0"); 
       display_code(root.right,s+"1"); 
   } 

	public static void main(String args[]) throws IOException
	{
		
		//File containing INPUT STRING 
		File input = new File("E:\\2nd_year_project\\huff.txt");
		Scanner sc = new Scanner(input);
		sc.useDelimiter("\\Z"); 
		String test;
		test=sc.nextLine();
		
		char test_array[];
		test_array=new char[test.length()];
		for(int i=0;i<test.length();i++)
		{	
			test_array[i]=test.charAt(i);		
		}
		
		// HashTable created to map each character with its respective frequency of occurence
		HashMap <Character,Integer> table = new HashMap<Character,Integer>();
		for(int i=0;i<test.length();i++)
		{
			if(table.containsKey(test_array[i]))
			{
				int prev = table.get(test_array[i]);
				prev++;
				table.replace(test_array[i], prev);
			}
			else
			{
				table.put(test_array[i], 1); 
			}
			
		}
		
		System.out.println("Characters and their Frequency of Occurence:");
		System.out.println(table);
		
		
		//Implement MinHeap using Priority Queue
		PriorityQueue <Node> q = new PriorityQueue<Node>(table.size(), new frequency_comparison());
		
		//Obtain Keys(i.e. Unique Characters) from Hash Table sequentially
		Set<Character> keys = table.keySet();
		Iterator<Character> i = keys.iterator();
		
		//Obtain Values corresponding to the Keys(i.e. Frequency of Unique Character) from Hash Table sequentially 
		Collection<Integer> getValues = table.values();
	    Iterator<Integer> j = getValues.iterator();
	    
	    //Create a node for the Huffman Tree using the Character-Frequency pair retrieved from the Hash Table
		while (i.hasNext()) 
		{
			Node huffman_node = new Node();
		    huffman_node.ch=(char)i.next();
		    huffman_node.freq=(int)j.next();
		    
		    huffman_node.left=null;
		    huffman_node.right=null;
		    
		    q.add(huffman_node); //Add each node to the Minimum Priority Queue(Nodes are in ascending order of their frequency attribute)
		}
		
		//Creation of Huffman Tree
		Node root = null; 		
	
	    while (q.size() > 1)
	    { 
	  
	            //Extract the Minm frequency Character First 
	            Node x = q.peek(); 
	            q.poll(); 
	  
	            // Then Extract the next Minm frequency Character 
	            Node y = q.peek(); 
	            q.poll(); 
	  
	            //create a new node, which represents the sum of the frequencies of the two minm extracted nodes
	            Node sum = new Node(); 
	  
	       
	            sum.freq = x.freq + y.freq; 
	            sum.ch = '#'; 
	  
	            // first extracted node as left child of sum node
	            sum.left = x; 
	  
	            // second extracted node as the right child of sum node 
	            sum.right = y; 
	  
	            // marking the sum node as the root node. 
	            root = sum; 
	  
	            // add this node to the priority-queue. 
	            q.add(sum); 
	        } 
	        
	        C = new code_map[table.size()];
	        
	        display_code(root,""); //Traverse the Huffman Tree created
	        
	        encoded(test_array);
	        
	        sc.close();
	}
	
	static void encoded(char input[]) throws IOException
	{
		//create a new file at specified path to store output
		File output = new File("E:\\2nd_year_project\\huff_encoded.txt");
		if(output.createNewFile())
		{
			//create a FileWriter object to write it onto the created file
			FileWriter myWriter = new FileWriter("E:\\2nd_year_project\\huff_encoded.txt");
			
			System.out.println("Encoded Output:");
			for(int i=0;i<input.length;i++)
			{
				for(int j=0;j<C.length;j++)
				{
					if(input[i]==C[j].alpha)
					{
						 myWriter.write(C[j].code);   
						 System.out.print(C[j].code); 
					}
				}
			}
			System.out.println();
			myWriter.close();
		}
			
	}
			
}
