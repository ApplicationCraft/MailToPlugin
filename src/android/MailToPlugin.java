package com.applicationcraft.plugins;

import java.io.File;
import java.util.ArrayList;

import org.apache.cordova.api.Plugin;
import org.apache.cordova.api.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.util.Log;

public class MailToPlugin extends Plugin {

	/** List Action */
	private static final String ACTION_MAIL_TO="mailTo";
	
	@Override
	public PluginResult execute(String action, JSONArray data, String callbackId) {
		Log.d("MailToPlugin", "Plugin Called");
		PluginResult result = null;
		if (action.equals(MailToPlugin.ACTION_MAIL_TO)) {
			result = mailTo(data);
        }
		return result;
	}
	
	private String getStringAt(JSONObject data, String name){
		String ret = null;
		try{
			ret = data.getString(name);
			//JSONArray convert JavaScript undefined|null to string "null", fix it
			ret = ( ret.equals("null") ) ? null : ret;
		}
		catch(Exception er){};
		return ret;
	}
	
	private JSONArray getJSONArrayAt(JSONObject data, String name){
		JSONArray ret = null;
		try{
			ret = (JSONArray)data.get(name);
		}
		catch(Exception er){};
		return ret;
	}
	
	private String[] convertJSONArrayToStringArray(JSONArray data) throws JSONException{
		String []ret = null;
		if (data != null){
			int vLen = data.length();
			ret = new String[vLen];
		    for (int i = 0; i < vLen; i++){ 
		    	ret[i] = data.getString(i);
		    }
		}
		return ret;
	}
	
	private PluginResult mailTo(JSONArray data)
	{
		PluginResult result = null;
		try {
			JSONObject jo = data.getJSONObject(0);
			String type = getStringAt(jo, "type");
			String subject = getStringAt(jo, "subject");
			String _isHtml = getStringAt(jo, "isHtml");
			boolean isHtml = (_isHtml == null) ? false : ( _isHtml.equals("yes") );
			String message = getStringAt(jo, "message");
			JSONArray _emails = getJSONArrayAt(jo, "emails");
			JSONArray _ccemails = getJSONArrayAt(jo, "ccemails");
			JSONArray _bccemails = getJSONArrayAt(jo, "bccemails");
			JSONArray _attachments = getJSONArrayAt(jo, "attachments");
			String chtitle = getStringAt(jo, "chtitle");
			
			if (type == null){
				type = (isHtml) ? "text/html" :  "application/octet-stream";
			}

			if (chtitle == null){
				chtitle = "Send mailÉ";
			}
			
			String[] emails = convertJSONArrayToStringArray(_emails);
			String[] ccemails = convertJSONArrayToStringArray(_ccemails);
			String[] bccemails = convertJSONArrayToStringArray(_bccemails);
			String[] attachments = convertJSONArrayToStringArray(_attachments);
			
			int cointAttachments = 0;
			ArrayList<Uri> uris = new ArrayList<Uri>();
			if (attachments != null){
			    for (String file : attachments)
			    {
			        File fileIn = new File(file);
			        Uri u = Uri.fromFile(fileIn);
			        uris.add(u);
			    }
			    cointAttachments = uris.size();
			}
			
			
			Intent sendIntent = new Intent( cointAttachments > 1 ? android.content.Intent.ACTION_SEND_MULTIPLE : android.content.Intent.ACTION_SEND);
			sendIntent.setType(type);
			sendIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
			sendIntent.putExtra(android.content.Intent.EXTRA_TEXT, isHtml ? Html.fromHtml(message) : message );
			if (emails != null)
				sendIntent.putExtra(android.content.Intent.EXTRA_EMAIL, emails);
			if (ccemails != null)
				sendIntent.putExtra(android.content.Intent.EXTRA_CC, ccemails);
			if (bccemails != null)
				sendIntent.putExtra(android.content.Intent.EXTRA_BCC, bccemails);
			if (attachments != null){
				if (cointAttachments == 1){
					sendIntent.putExtra(android.content.Intent.EXTRA_STREAM, uris.get(0));
				}
				else {
					sendIntent.putParcelableArrayListExtra(android.content.Intent.EXTRA_STREAM, uris);
				}
			}
			this.ctx.startActivity(Intent.createChooser(sendIntent, chtitle));
			result = new PluginResult(PluginResult.Status.OK);
		} catch (JSONException e) {
			result = new PluginResult(PluginResult.Status.ERROR);
		}
		return result;
	}

}
