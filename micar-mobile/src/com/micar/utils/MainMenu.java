package com.micar.utils;

import java.util.ArrayList;

import android.content.Context;

import com.micar.activity.R;
import com.micar.adapter.MenuItems;

public class MainMenu {
	
	
	
	public static ArrayList<MenuItems> Items;

    public static void LoadModel(Context context) {
    	
        Items = new ArrayList<MenuItems>();
        
        String[] mPlanetTitles = context.getResources().getStringArray(R.array.menu_array);
        for (int i=0; i< mPlanetTitles.length; i++){
        Items.add(new MenuItems(i+1, R.drawable.navigation_next_item, mPlanetTitles[i]));
        
        }
    }

    public static MenuItems GetbyId(int id){

        for(MenuItems item : Items) {
            if (item.Id == id) {
                return item;
            }
        }
        return null;
    }
}
