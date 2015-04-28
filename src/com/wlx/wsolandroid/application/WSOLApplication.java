package com.wlx.wsolandroid.application;

import android.app.Application;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.listener.UpdateListener;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.umeng.analytics.MobclickAgent;
import com.wlx.wsolandroid.model.User;

public class WSOLApplication extends Application {
	public LocationClient mLocationClient;
	public GeofenceClient mGeofenceClient;
	public MyLocationListener mMyLocationListener;


    @Override
    public void onCreate() {
        super.onCreate();
        MobclickAgent.openActivityDurationTrack(false);    
      // 后台配置
     	Bmob.initialize(this, "8763a00a263ee5064e8a55be05f72f3a");
     	
     	
     	SDKInitializer.initialize(this); 
     	mLocationClient = new LocationClient(this.getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		mGeofenceClient = new GeofenceClient(getApplicationContext());
       
    }

	
    public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			//Receive Location 
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation){
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\ndirection : ");
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append(location.getDirection());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append("\noperationers : ");
				sb.append(location.getOperators());
			}
			System.out.println("bbbbb------>"+sb.toString());
			BmobGeoPoint point = new BmobGeoPoint(location.getLongitude(), location.getLatitude());
			updateUserLacation(point, location.getAddrStr());
		}


	}
    
    
    
    
    
    
    
    /** 更新用户位置信息 */
	private void updateUserLacation(final BmobGeoPoint point,String address) {	
		User newUser = new User();	
		newUser.setLastGeoPoint(point);
		newUser.setLastAddress(address);
		BmobUser bmobUser = BmobUser.getCurrentUser(this);
		newUser.update(this, bmobUser.getObjectId(), new UpdateListener() {
			@Override
			public void onSuccess() {
				
			}

			@Override
			public void onFailure(int code, String msg) {
				
			}
		});
	}
    
}
