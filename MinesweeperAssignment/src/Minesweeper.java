import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Minesweeper implements ActionListener  {
	JFrame frame = new JFrame("Minesweeper");
	JButton reset = new JButton("Reset");
	JButton[][] buttons = new JButton[9][9];
	
	int [][] counts = new int[9][9];
	Container grid = new Container();
	final int MINE = 30;
	
	public Minesweeper(){
		frame.setSize(400,400);
		frame.setLayout(new BorderLayout());
		frame.add(reset, BorderLayout.NORTH);
		reset.addActionListener(this);
		//Button Grid
		grid.setLayout(new GridLayout(9,9));
		for(int a=0; a<buttons.length; a++){
			for(int b=0; b<buttons[0].length; b++){
				buttons[a][b] = new JButton();
				buttons[a][b].addActionListener(this);
				grid.add(buttons[a][b]);
			}
		}
		frame.add(grid, BorderLayout.CENTER);
		createRandomMines();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	
	public static void main(String[] args){
		new Minesweeper();
	}
	
	public void createRandomMines(){
		//initialized list of random pairs
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int x = 0; x<counts.length; x++){
			for(int y = 0; y<counts[0].length; y++){
				list.add(x*100+y);
			}
		}
		//reset counts, pick out 30 mines
		counts = new int[20][20];
		for(int a = 0; a<30; a++){
			int choice = (int)(Math.random()*list.size());
			counts[list.get(choice)/100][list.get(choice)%100]=MINE;
			list.remove(choice);
		}
		//Neighbor counts
		for(int x=0; x<counts.length; x++){
			for(int y=0; y<counts[0].length; y++){
				if(counts[x][y] != MINE){
				int neighborcount = 0;
				if(x>0 && y>0 && counts[x-1][y-1] == MINE){//up left
					neighborcount ++;
					
				}
				if(y>0 && counts[x][y-1]==MINE){//up
					neighborcount++;
				}
				if(x<counts.length - 1 && y<counts[0].length - 1 && counts[x+1][y+1]==MINE){//down right
					neighborcount++;
				}
				if(x>0 && counts[x-1][y] == MINE){ //left
					neighborcount ++;
				}
				if(x<counts.length -1 && counts[x+1][y] == MINE){ //right
					neighborcount ++;
				}
				if(x>0 && y<counts[0].length-1 && counts[x-1][y+1] == MINE ){
					neighborcount ++;
				}
				if(y<counts[0].length-1 && counts[x][y+1] == MINE){ //down
					neighborcount ++;
				}
				if(x<counts.length -1 && y<counts[0].length-1 && counts[x][y+1] == MINE){
					neighborcount ++;
				}
				counts[x][y] = neighborcount;
				}
			}
		}
	}
	
	public void lostGame(){
		for(int x=0; x<buttons.length; x++){
			for(int y=0; y<buttons[0].length; y++){
				if(buttons[x][y].isEnabled()){
					if(counts[x][y] != MINE){
						buttons[x][y].setText(counts[x][y]+"");
						buttons[x][y].setEnabled(false);
					}
					else{
						buttons[x][y].setText("X");
						buttons[x][y].setEnabled(false);
					}
						
				}
			}
		}
	}
	public void clearZeros(ArrayList<Integer> toClear){
		if(toClear.size() == 0){
			return;
		}
		else{
			int x = toClear.get(0)/100;
			int y = toClear.get(0)%100;
			toClear.remove(0);
			
				if(x>0 && y>0 && buttons[x-1][y-1].isEnabled()){
					buttons[x-1][y-1].setText(counts[x-1][y-1] +"");
					buttons[x-1][y-1].setEnabled(false);
					if(counts[x-1][y-1]==0){
						toClear.add((x-1)* 100 + y-1);
					}
				}
				if(y>0 && buttons[x][y-1].isEnabled()){
					buttons[x][y-1].setText(counts[x][y-1] +"");
					buttons[x][y-1].setEnabled(false);
					if(counts[x][y-1]==0){
						toClear.add((x)* 100 + y-1);
					}
				}
				if(x<counts.length - 1 && y>0 && buttons[x+1][y+1].isEnabled()){
					buttons[x+1][y-1].setText(counts[x+1][y-1] +"");
					buttons[x+1][y-1].setEnabled(false);
					if(counts[x+1][y-1]==0){
						toClear.add((x+1)* 100 + y-1);
					}
				}
				if(x>0 && buttons[x-1][y].isEnabled()){
					buttons[x-1][y].setText(counts[x][y] +"");
					buttons[x-1][y].setEnabled(false);
					if(counts[x-1][y]==0){
						toClear.add((x-1)* 100 + y);
					}
				}
				if(x< counts.length-1 && buttons[x][y].isEnabled()){
					buttons[x+1][y].setText(counts[x][y] +"");
					buttons[x+1][y].setEnabled(false);
					if(counts[x+1][y]==0){
						toClear.add((x+1)* 100 + y);
					}
				}
				if(x>0 && y<counts[0].length-1 && buttons[x+1][y-1].isEnabled()){
					buttons[x-1][y+1].setText(counts[x+1][y-1] +"");
					buttons[x-1][y+1].setEnabled(false);
					if(counts[x-1][y+1]==0){
						toClear.add((x-1)* 100 + y+1);
					}
				}
				if(y<counts[0].length-1 && buttons[x+1][y-1].isEnabled()){
					buttons[x][y+1].setText(counts[x+1][y-1] +"");
					buttons[x][y+1].setEnabled(false);
					if(counts[x][y+1]==0){
						toClear.add((x)* 100 + y+1);
					}
				}
				if(x<counts.length -1 && y<counts[0].length-1 && buttons[x+1][y-1].isEnabled()){
					buttons[x+1][y+1].setText(counts[x+1][y-1] +"");
					buttons[x+1][y+1].setEnabled(false);
					if(counts[x+1][y+1]==0){
						toClear.add((x+1)* 100 + y+1);
					}
				}
				
			}
			clearZeros(toClear);
		}
	
	public void checkWin(){
		boolean won = true;
		for(int x=0; x<counts.length; x++){
			for(int y=0; y<counts[0].length; y++){
				if(counts[x][y]!=MINE && buttons[x][y].isEnabled() == true){
					won = false;
				}
			}
		}
		if(won == true){
			JOptionPane.showMessageDialog(frame, "You won the game dog!!");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(reset)){
			//reset the board
			for(int x=0; x<buttons.length; x++){
				for(int y=0; y<buttons[0].length; y++){
					buttons[x][y].setEnabled(true);
					buttons[x][y].setText("");
				}
			}
			createRandomMines();
		}
		else{
			for(int x=0; x<buttons.length; x++){
				for(int y=0; y<buttons[0].length;y++){
					if(e.getSource().equals(buttons[x][y])){
						if(counts[x][y] == MINE){
							buttons[x][y].setForeground(Color.RED);
							buttons[x][y].setText("X");
							//buttons[x][y].setForeground(Color.BLACK);
							lostGame();
						}
						else if(counts[x][y] == 0){
							buttons[x][y].setText(counts[x][y] + "");
							buttons[x][y].setEnabled(false);
							ArrayList<Integer> toClear = new ArrayList<Integer>();
							toClear.add(x*100 + y);
							clearZeros(toClear);
							checkWin();
						}
						else{
						buttons[x][y].setText(counts[x][y] + "");
						buttons[x][y].setEnabled(false);
						checkWin();
						}
					}
				}
			}
		}
		
	}
}
