import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Drones {

	public static void main(String[] args) {
		
		String fileName = "/home/eit/Downloads/busy_day.in", line = null;		
		
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String stringArray = "";
			int rows, cols, dronesCount, turns, payload;
			int numberOfProducts;
			int[] productTypes;
			int numberOfWarehouses;
			
			List<String> list = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
			String[] a = list.toArray(new String[list.size()]); 
			
			String[] row1 = a[0].split("\\s+");
			rows = Integer.parseInt(row1[0]);
			cols = Integer.parseInt(row1[1]);
			dronesCount = Integer.parseInt(row1[2]);
			turns = Integer.parseInt(row1[3]);
			payload = Integer.parseInt(row1[4]);
			
			String[] row2 = a[1].split("\\s+");
			numberOfProducts = Integer.parseInt(row2[0]);
			
			String[] row3 = a[2].split("\\s+");
			
			productTypes = new int[row3.length];
			for(int i = 0; i<row3.length; i++){
				productTypes[i] = Integer.parseInt(row3[i]);
			}
			
			// finished saving producttypes
			
			// saving number of warehouses
			String[] row4 = a[3].split("\\s+");
			numberOfWarehouses = Integer.parseInt(row4[0]);
			
			int startFrom = 4;
			Warehouse[] warehouses = new Warehouse[numberOfWarehouses];
			for(int i =0; i<numberOfWarehouses; i++){
				String[] wareHouseLocation = a[startFrom].split("\\s+");
				
				Point point = new Point(Integer.parseInt(wareHouseLocation[0]), Integer.parseInt(wareHouseLocation[1]));
				Warehouse warehouse = new Warehouse(point);
				
				// store 
				warehouses[i] = warehouse;
				startFrom = startFrom + 2;
			}
			// mapping warehouse to location
			
			// getting orders
			String[] orderRow = a[startFrom].split("\\s+");
			int orderNumber = Integer.parseInt(orderRow[0]);
			
			Order[] orders = new Order[orderNumber];
			startFrom = startFrom + 1;
			
			for(int i =0; i < orderNumber; i++){
				String[] orderLocation = a[startFrom].split("\\s+");
				
				Point point = new Point(Integer.parseInt(orderLocation[0]), Integer.parseInt(orderLocation[1]));	
				Order order = new Order(point );
				
				// store 
				orders[i] = order;
				startFrom = startFrom + 3;
			}
			// mapping orders and their respective information
					
			List<Drone> drones = new ArrayList<Drone>();
			System.out.println(drones.size());
			Double min = distance(new Point(0,0), new Point(rows, cols));
			
			for(int i = 0; i< dronesCount; i++){
				Drone drone = new Drone();
				if(drones.size() == 0){
					Point point = new Point(0, 0);
					drone = new Drone(point);
				}
				
				
				for(int j = 0; j< warehouses.length; j++){
					// find if drone is close to warehouse
					double distance = distance(drone.getPoint(), warehouses[0].getPoint());
					
					if(distance < min){
						min = distance;
					}
				}
				
				System.out.println("MIN is: " + min.toString());
				
			}
			// take one drone [0,0]
			// give it order to pick order to the closest warehouse
			// deliver to the customer
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	static double distance (Point point1, Point point2) {
	    double deltaX = point1.ycoord - point2.ycoord;
	    double deltaY = point1.xcoord - point2.xcoord;
	    double result = Math.sqrt(deltaX*deltaX + deltaY*deltaY);
	    return result; 
	}
}