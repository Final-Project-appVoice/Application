//package com.example.sarah.myproject.Class;
//
//import android.content.Context;
//import android.content.Intent;
//import android.util.Log;
//
//import com.google.gson.JsonArray;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
//import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
//import com.microsoft.windowsazure.mobileservices.TableJsonQueryCallback;
//import com.microsoft.windowsazure.mobileservices.table.MobileServiceJsonTable;
//import com.microsoft.windowsazure.mobileservices.table.TableJsonOperationCallback;
//
//import java.net.MalformedURLException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by Sarah on 12-Mar-15.
// */
//public class StorageService
//{
//    private MobileServiceClient mClient;
//    private MobileServiceJsonTable mTableTables;
//    private String TAG = "TAG";
//    private Context mContext;
//    public StorageService(Context context)
//    {
//        mContext = context;
//        try {
//            mClient = new MobileServiceClient(
//                    "https://appvoice.azure-mobile.net/",
//                    "WkabVQmUbcpntFZOfZgEXqDrfNVqIJ76",
//                    context
//            );
//            mTableTables = mClient.getTable("Patients");
//
//        } catch (MalformedURLException e) {
//            Log.e("TAG", "There was an error creating the Mobile Service. Verify the URL");
//        }
//    }
//
//    public void getTables()
//    {
//        mTableTables.where().execute(new TableJsonQueryCallback() {
//            @Override
//            public void onCompleted(JsonElement result, int count, Exception exception,
//                                    ServiceFilterResponse response) {
//                if (exception != null) {
//                    Log.e(TAG, exception.getCause().getMessage());
//                    return;
//                }
//                JsonArray results = result.getAsJsonArray();
//
//                mTables = new ArrayList<Map<String, String>>();
//                //Loop through the results and get the name of each table
//                for (int i = 0; i < results.size(); i ++) {
//                    JsonElement item = results.get(i);
//                    Map<String, String> map = new HashMap<String, String>();
//                    map.put("TableName", item.getAsJsonObject().getAsJsonPrimitive("TableName").getAsString());
//                    mTables.add(map);
//                }
//                //Broadcast that tables have been loaded
//                Intent broadcast = new Intent();
//                broadcast.setAction("tables.loaded");
//                mContext.sendBroadcast(broadcast);
//            }
//        });
//    }
//
//    public void addTable(String tableName) {
//        JsonObject newTable = new JsonObject();
//        newTable.addProperty("tableName", tableName);
//        TableJsonOperationCallback tableJsonOperationCallback = new TableJsonOperationCallback() {
//            @Override
//            public void onCompleted(JsonObject entity, Exception exception, com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse response)
//            {
//                if (exception != null) {
//                    Log.e(TAG, exception.getCause().getMessage());
//                    return;
//                }
//                //Refetch the tables from the server
//                getTables();
//            }
//
//        };
//        mTableTables.insert(newTable, tableJsonOperationCallback);
//    }
//}
