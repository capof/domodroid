package org.database;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.provider.DmdContentProvider;
import org.widgets.Entity_Area;
import org.widgets.Entity_Feature;
import org.widgets.Entity_Icon;
import org.widgets.Entity_Map;
import org.widgets.Entity_Room;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;



public class DomodroidDB {

	private Activity context;

	public DomodroidDB(Activity context){
		this.context = context;
	}

	public void updateDb(){
		context.getContentResolver().delete(DmdContentProvider.CONTENT_URI_UPGRADE_FEATURE_STATE, null, null);
	}


	public void insertArea(JSONObject json) throws JSONException{
		ContentValues values = new ContentValues();
		JSONArray itemArray = json.getJSONArray("area");
		for (int i =0; i < itemArray.length(); i++){
			values.put("description", itemArray.getJSONObject(i).getString("description").toString());
			values.put("id", itemArray.getJSONObject(i).getInt("id"));
			values.put("name", itemArray.getJSONObject(i).getString("name").toString());
			context.getContentResolver().insert(DmdContentProvider.CONTENT_URI_INSERT_AREA, values);
		}
	}

	public void insertRoom(JSONObject json) throws JSONException{
		ContentValues values = new ContentValues();
		JSONArray itemArray = json.getJSONArray("room");
		int area_id;
		for (int i =0; i < itemArray.length(); i++){
			if(itemArray.getJSONObject(i).getString("area_id").equals(""))area_id=0;
			else area_id=itemArray.getJSONObject(i).getInt("area_id");
			values.put("area_id", area_id);
			values.put("description", itemArray.getJSONObject(i).getString("description").toString());
			values.put("id", itemArray.getJSONObject(i).getInt("id"));
			values.put("name", itemArray.getJSONObject(i).getString("name").toString());
			context.getContentResolver().insert(DmdContentProvider.CONTENT_URI_INSERT_ROOM, values);
		}
	}

	public void insertIcon(JSONObject json) throws JSONException{
		ContentValues values = new ContentValues();
		JSONArray itemArray = json.getJSONArray("ui_config");
		for (int i =0; i < itemArray.length(); i++){
			values.put("name", itemArray.getJSONObject(i).getString("name").toString());
			values.put("value", itemArray.getJSONObject(i).getString("value").toString());
			values.put("reference", itemArray.getJSONObject(i).getInt("reference"));
			context.getContentResolver().insert(DmdContentProvider.CONTENT_URI_INSERT_ICON, values);
		}
	}

	public void insertFeature(JSONObject json) throws JSONException{
		ContentValues values = new ContentValues();
		JSONArray itemArray = json.getJSONArray("feature");
		for (int i =0; i < itemArray.length(); i++){
			values.put("device_feature_model_id", itemArray.getJSONObject(i).getString("device_feature_model_id").toString());
			values.put("id", itemArray.getJSONObject(i).getInt("id"));
			values.put("device_id", itemArray.getJSONObject(i).getJSONObject("device").getInt("id"));
			values.put("device_usage_id", itemArray.getJSONObject(i).getJSONObject("device").getString("device_usage_id").toString());
			values.put("address", itemArray.getJSONObject(i).getJSONObject("device").getString("address").toString());
			values.put("device_type_id", itemArray.getJSONObject(i).getJSONObject("device").getString("device_type_id").toString());
			values.put("description", itemArray.getJSONObject(i).getJSONObject("device").getString("description").toString());
			values.put("name", itemArray.getJSONObject(i).getJSONObject("device").getString("name").toString());
			values.put("state_key", itemArray.getJSONObject(i).getJSONObject("device_feature_model").getString("stat_key"));
			values.put("parameters", itemArray.getJSONObject(i).getJSONObject("device_feature_model").getString("parameters"));
			values.put("value_type", itemArray.getJSONObject(i).getJSONObject("device_feature_model").getString("value_type"));
			context.getContentResolver().insert(DmdContentProvider.CONTENT_URI_INSERT_FEATURE, values);
		}
	}



	public void insertFeatureAssociation(JSONObject json) throws JSONException{
		ContentValues values = new ContentValues();
		JSONArray itemArray = json.getJSONArray("feature_association");
		for (int i =0; i < itemArray.length(); i++){
			values.put("place_id", itemArray.getJSONObject(i).getInt("place_id"));
			values.put("place_type", itemArray.getJSONObject(i).getString("place_type"));
			values.put("device_feature_id", itemArray.getJSONObject(i).getInt("device_feature_id"));
			values.put("id", itemArray.getJSONObject(i).getInt("id"));
			values.put("device_feature", itemArray.getJSONObject(i).getString("device_feature"));
			context.getContentResolver().insert(DmdContentProvider.CONTENT_URI_INSERT_FEATURE_ASSOCIATION, values);
		}
	}


	public void insertFeatureState(JSONObject json) throws JSONException{
		ContentValues values = new ContentValues();
		JSONArray itemArray = json.getJSONArray("stats");
		String[] projection = {"COUNT(*)"};
		
		for (int i =0; i < itemArray.length(); i++){
			Cursor curs=null;
			curs = context.managedQuery(DmdContentProvider.CONTENT_URI_REQUEST_FEATURE_STATE, projection, "device_id = ? AND key = ?", new String [] {itemArray.getJSONObject(i).getString("device_id")+"",itemArray.getJSONObject(i).getString("skey")}, null);
			curs.moveToFirst();
			values.put("device_id", itemArray.getJSONObject(i).getInt("device_id"));
			values.put("key", itemArray.getJSONObject(i).getString("skey"));
			values.put("value", itemArray.getJSONObject(i).getString("value"));
			if(curs.getInt(0)==0){
				context.getContentResolver().insert(DmdContentProvider.CONTENT_URI_INSERT_FEATURE_STATE, values);
				Log.e("debuuuuuuuuuggg insert", itemArray.getJSONObject(i).getInt("device_id")+" "+itemArray.getJSONObject(i).getString("skey")+" "+itemArray.getJSONObject(i).getString("value"));
			}else{
				context.getContentResolver().update(DmdContentProvider.CONTENT_URI_UPDATE_FEATURE_STATE, values, "device_id = ? AND key = ?", new String [] {itemArray.getJSONObject(i).getString("device_id")+"",itemArray.getJSONObject(i).getString("skey")});
				Log.e("debuuuuuuuuuggg update", itemArray.getJSONObject(i).getInt("device_id")+" "+itemArray.getJSONObject(i).getString("skey")+" "+itemArray.getJSONObject(i).getString("value"));
			}
			curs.close();
		}
	}


	public void insertFeatureMap(int id, int posx, int posy, String map){
		ContentValues values = new ContentValues();
		values.put("id", id);
		values.put("posx", posx);
		values.put("posy", posy);
		values.put("map", map);
		context.getContentResolver().insert(DmdContentProvider.CONTENT_URI_INSERT_FEATURE_MAP, values);
	}



	////////////////// REQUEST



	public Entity_Area[] requestArea() {
		String[] projection = {"description", "id", "name"};
		Entity_Area[] areas=null;
		Cursor curs=null;	
		try {
			curs = context.managedQuery(DmdContentProvider.CONTENT_URI_REQUEST_AREA, projection, null, null, null);
			areas=new Entity_Area[curs.getCount()];
			int count=curs.getCount();
			for(int i=0;i<count;i++) {
				curs.moveToPosition(i);
				areas[i]=new Entity_Area(curs.getString(0),curs.getInt(1),curs.getString(2));
			}
		} catch (Exception e) {
			Log.e("request area error", "");
			e.printStackTrace();
		}
		curs.close();
		return areas;
	}


	public Entity_Room[] requestRoom(int area_id) {
		Entity_Room[] rooms=null;
		String[] projection = { "area_id", "description", "id", "name"};
		Cursor curs=null;	
		try {
			curs = context.managedQuery(DmdContentProvider.CONTENT_URI_REQUEST_ROOM, projection, "area_id="+ area_id, null, null);
			rooms=new Entity_Room[curs.getCount()];
			int count=curs.getCount();
			for(int i=0;i<count;i++) {
				curs.moveToPosition(i);
				rooms[i]=new Entity_Room(curs.getInt(0),curs.getString(1),
						curs.getInt(2),curs.getString(3));
			}
		} catch (Exception e) {
			Log.e("request room", "");
			e.printStackTrace();
		}
		curs.close();
		return rooms;
	}

	public Entity_Icon requestIcons(int reference, String name) {
		Cursor curs=null;
		Entity_Icon icon = null;
		try {
			String[] projection = { "name", "value", "reference"};
			curs = context.managedQuery(DmdContentProvider.CONTENT_URI_REQUEST_ICON, projection, "reference = ? AND name = ?" , new String[] { reference+"", name }, null);
			curs.moveToFirst();
			icon=new Entity_Icon(curs.getString(0), curs.getString(1), curs.getInt(2));
		} catch (Exception e) {
			Log.e("request icon error", "");
			e.printStackTrace();
		}
		curs.close();
		return icon;
	}


	public Entity_Feature[] requestFeatures(int id, String zone) {
		Cursor curs=null;
		Entity_Feature[] features=null;
		try {
			curs = context.managedQuery(DmdContentProvider.CONTENT_URI_REQUEST_FEATURE_ID, null, null, new String[] {id+"",zone}, null);
			features=new Entity_Feature[curs.getCount()];
			int count=curs.getCount();
			for(int i=0;i<count;i++) {
				curs.moveToPosition(i);
				features[i]=new Entity_Feature(curs.getString(0),curs.getInt(1),curs.getInt(2),curs.getString(3),curs.getString(4),curs.getString(5),curs.getString(6),curs.getString(7),curs.getString(8),curs.getString(9),curs.getString(10));
			}
		} catch (Exception e) {
			Log.e("request feature error", "");
			e.printStackTrace();
		}
		curs.close();
		return features;
	}


	public Entity_Map[] requestFeatures(String map){
		Cursor curs=null;
		Entity_Map[] features=null;
		try {
			curs = context.managedQuery(DmdContentProvider.CONTENT_URI_REQUEST_FEATURE_MAP, null, null, null, null);
			features=new Entity_Map[curs.getCount()];
			int count=curs.getCount();
			for(int i=0;i<count;i++) {
				curs.moveToPosition(i);
				features[i]=new Entity_Map(curs.getString(0),curs.getInt(1),curs.getInt(2),curs.getString(3),curs.getString(4),curs.getString(5),curs.getString(6),curs.getString(7),curs.getString(8),curs.getString(9),curs.getString(10),curs.getInt(12),curs.getInt(13),curs.getString(14));
			}
		} catch (Exception e) {
			Log.e("request feature map error", "");
			e.printStackTrace();
		}
		curs.close();
		return features;
	}


	public Entity_Feature[] requestFeatures(){
		Cursor curs=null;
		Entity_Feature[] features=null;
		try {
			curs = context.managedQuery(DmdContentProvider.CONTENT_URI_REQUEST_FEATURE_ALL, null, null, null, null);
			features=new Entity_Feature[curs.getCount()];
			int count=curs.getCount();
			for(int i=0;i<count;i++) {
				curs.moveToPosition(i);
				features[i]=new Entity_Feature(curs.getString(0),curs.getInt(1),curs.getInt(2),curs.getString(3),curs.getString(4),curs.getString(5),curs.getString(6),curs.getString(7),curs.getString(8),curs.getString(9),curs.getString(10));
			}
		} catch (Exception e) {
			Log.e("request feature error", "");
			e.printStackTrace();
		}
		curs.close();
		return features;
	}


	public String requestFeatureState(int device_id, String key){
		String state = null;
		String[] projection = {"value"};			
			try {
				Cursor curs=null;
				curs = context.managedQuery(DmdContentProvider.CONTENT_URI_REQUEST_FEATURE_STATE, projection, "device_id = ? AND key = ?", new String [] {device_id+"", key}, null);
				curs.moveToPosition(0);
				Log.e("reque", device_id+ " "+key);
				state=curs.getString(0);
				curs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		

		return state;
	}
}