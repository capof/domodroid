/*
 * This file is part of Domodroid.
 * 
 * Domodroid is Copyright (C) 2011 Pierre LAINE, Maxime CHOFARDET
 * 
 * Domodroid is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * Domodroid is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * Domodroid. If not, see <http://www.gnu.org/licenses/>.
 */
package widgets;

import activities.Gradients_Manager;
import activities.Graphics_Manager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Graphical_Feature extends FrameLayout{

	private FrameLayout imgPan;
	private LinearLayout background;
	private LinearLayout infoPan;
	private ImageView img;
	private TextView name;
	private TextView description;
	private int id;
	private int session_type;

	public Graphical_Feature(Context context,int id,String name_room, String description_room, String icon, int widgetSize, int session_type) {
		super(context);
		this.id = id;
		this.session_type = session_type;
		this.setPadding(5, 5, 5, 5);

		//panel with border	
		background = new LinearLayout(context);
		if(widgetSize==0)
			background.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		else 
			background.setLayoutParams(new LayoutParams(widgetSize,LayoutParams.WRAP_CONTENT));
		
		background.setBackgroundDrawable(Gradients_Manager.LoadDrawable("black",background.getHeight()));

		//panel to set img with padding left
		imgPan = new FrameLayout(context);
		imgPan.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.FILL_PARENT));
		imgPan.setPadding(5, 8, 10, 10);
		//img
		img = new ImageView(context);
		img.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT,Gravity.CENTER));
		img.setBackgroundResource(Graphics_Manager.Icones_Agent(icon, 0));

		//info panel
		infoPan=new LinearLayout(context);
		infoPan.setOrientation(LinearLayout.VERTICAL);
		infoPan.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
		infoPan.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
		infoPan.setPadding(0, 0, 10, 0);


		//name of room
		name=new TextView(context);
		name.setText(name_room);
		name.setTextSize(18);
		name.setTextColor(Color.WHITE);
		name.setGravity(Gravity.RIGHT);

		//description
		description=new TextView(context);
		description.setText(description_room);
		name.setTextSize(17);
		description.setGravity(Gravity.RIGHT);

		infoPan.addView(name);
		infoPan.addView(description);
		imgPan.addView(img);

		background.addView(imgPan);
		background.addView(infoPan);

		this.addView(background);
	}

	public int getId() {
		return id;
	}
}

