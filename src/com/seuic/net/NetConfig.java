package com.seuic.net;

import java.io.Serializable;

public class NetConfig implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String Ssid = null;
	private String Psk = null;
	private String Key_mgmt = null;
	private String Priority = null;
	private boolean isSaved = false;
	private boolean isWEP = false;
	
	public NetConfig(String ssid, String Psk, String Key_mgmt, String priority,boolean isSaved){
		setSsid(ssid);
		setPsk(Psk);
		setKey_mgmt(Key_mgmt);
		setPriority(priority);
		setIsSaved(isSaved);
	}
	public NetConfig(){
		
	}
	public void setSsid(String Ssid){
		this.Ssid = Ssid;
	}
	public String getSsid(){
		return Ssid;
	}
	public void setPsk(String Psk){
		this.Psk = Psk;
	}
	public String getPsk(){
		return Psk;
	}
	public void setKey_mgmt(String Key_mgmt){
		this.Key_mgmt = Key_mgmt.replace("_", "-");
	}
	public String getKey_mgmt(){
		return Key_mgmt;
	}
	public void setPriority(String Priority){
		this.Priority = Priority;
	}
	public String getPriority(){
		return Priority;
	}
	public void setIsSaved(boolean isSaved){
		this.isSaved = isSaved;
	}
	public boolean getIsSaved(){
		return isSaved;
	}
	public void setIsWep(boolean isWEP){
		this.isWEP = isWEP;
	}
	public boolean getIsWep(){
		return isWEP;
	}
	@Override
	public String toString()
	{
		if (Psk != null)
		{
			String head = "ctrl_interface=DIR=/var/run/wpa_supplicant GROUP=1010"+"\n"+
							"update_config=1"+"\n\n";
			if (isWEP)
			{
				String config = head+ "network={"+"\n\t"+
						"ssid=\""+Ssid+"\"\n\t"+
						"key_mgmt="+Key_mgmt+"\n\t"+
						"auth_alg=OPEN SHARED\n\t"+
						"wep_key0=\""+Psk+"\"\n\t"+
						"priority="+Priority+"\n"+
						"}\n";
				return config;
			}else {
				String config = head+ "network={"+"\n\t"+
						"ssid=\""+Ssid+"\"\n\t"+
						"key_mgmt="+Key_mgmt+"\n\t"+
						"psk=\""+Psk+"\"\n\t"+
						"priority="+Priority+"\n"+
						"}\n";
				return config;
			}
			
			
		}else {
			String head = "ctrl_interface=DIR=/var/run/wpa_supplicant GROUP=1010"+"\n"+
					"update_config=1"+"\n\n";
			String config = head + "network={"+"\n\t"+
					"ssid=\""+Ssid+"\"\n\t"+
					"key_mgmt="+Key_mgmt+"\n\t"+
					"priority="+Priority+"\n"+
					"}\n";
			return config;
		}
	}
}
