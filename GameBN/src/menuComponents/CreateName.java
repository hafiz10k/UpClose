package menuComponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

import game.Game;
import handler.KeyBoardListener;

public class CreateName implements MenuObject, ActionListener{

	private Color titleColor;
	private Font titleFont;

	private Font font;

	private JTextField txtInput = new JTextField("");

	private char[] name = {'A', 'A', 'A', 'A', 'A'};
	private int currentChoice = 0;

	public CreateName() {
		try 
		{

			titleColor = new Color(100, 128, 128);
			titleFont = new Font("Broadway", Font.BOLD, 50);

			font = new Font("Arial", Font.PLAIN, 30);		


		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void render(Graphics graphics) {
		graphics.setColor(Color.WHITE);
		graphics.setFont(titleFont);
		graphics.drawString("What is your name?", 200, 120);

		graphics.setFont(font);	

		for(int i = 0; i < name.length; i++) 
		{
			if (name[currentChoice] == 0) {
				graphics.setColor(Color.RED);
			}
			else {
				graphics.setColor(Color.WHITE);
			}

			graphics.drawString("" + name[0], 200, 300);
			graphics.drawString("" + name[1], 240, 300);
			graphics.drawString("" + name[2], 280, 300);
			graphics.drawString("" + name[3], 320, 300);
			graphics.drawString("" + name[4], 360, 300);

		}
	}

	@Override
	public void update(Game game) {
		try {
			KeyBoardListener keyListener = game.getKeyListener();

			if(keyListener.enter()) {
				String fullName = new String(name);
				System.out.println(fullName);
			}

			if(keyListener.up()) {
				name[currentChoice]++;
			}

			if(keyListener.down()) {
				name[currentChoice]--;
			}

			if(keyListener.left()) {
				currentChoice --;
				if(currentChoice == -1)
				{
					currentChoice = name.length - 1;
				}
			}

			if(keyListener.right()) {
				currentChoice ++;
				if(currentChoice == name.length)
				{
					currentChoice = 0;
				}
			}

			Thread.sleep(120);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		//		input = txtInput.getText(); 
		//       System.out.println(input);
	}

}

