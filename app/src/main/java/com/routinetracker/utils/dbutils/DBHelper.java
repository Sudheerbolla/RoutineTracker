package com.routinetracker.utils.dbutils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.routinetracker.models.UsersModel;
import com.routinetracker.utils.AppStorage;
import com.routinetracker.utils.Constants;
import com.routinetracker.utils.PasswordsUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class DBHelper {

    private DatabaseHandler databaseHandler;
    private Context context;

    public DBHelper(Context context) {
        this.context = context;
        databaseHandler = DatabaseHandler.getInstance(context);
    }

    public synchronized long addUser(JSONObject jsonObject) {
        databaseHandler.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            String password = jsonObject.getString(Constants.USER_PASSWORD);
            byte[] salt = PasswordsUtils.getNextSalt();
            char[] pass = password.toCharArray();
            byte[] hashPassword = PasswordsUtils.hash(pass, salt);
            values.put(TableUsers.name, jsonObject.getString(Constants.USER_NAME));
            values.put(TableUsers.dob, jsonObject.getString(Constants.USER_DOB));
            values.put(TableUsers.favNumber, jsonObject.getInt(Constants.USER_FAV_NUMBER));
            values.put(TableUsers.emailId, jsonObject.getString(Constants.USER_EMAIL));
            values.put(TableUsers.userType, "C");
            values.put(TableUsers.encryption, salt);
            values.put(TableUsers.password, hashPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
/*
            if (deviceModel.id != -1) {
                values.put(TableUsers.id, deviceModel.id);
                return databaseHandler.updateData(
                        TableUsers.TABLE_NAME, values, TableUsers.id + "=?", new String[]{deviceModel.id + ""}
                );
            } else
                return databaseHandler.insertData(TableUsers.TABLE_NAME, values);
*/
            long id = databaseHandler.insertData(TableUsers.TABLE_NAME, values);
            if (id != -1) {
                AppStorage.getInstance(context).setValue(AppStorage.SP_USER_ID, id);
                AppStorage.getInstance(context).setValue(AppStorage.SP_USER_NAME, jsonObject.getString(Constants.USER_NAME));
                AppStorage.getInstance(context).setValue(AppStorage.SP_USER_EMAIL, jsonObject.getString(Constants.USER_EMAIL));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public synchronized boolean performLogin(String emailAddress, String password) {
        String selectQuery = "";
        selectQuery = "select * FROM " + TableUsers.TABLE_NAME + " WHERE " + TableUsers.emailId + "='" + emailAddress + "'";
        databaseHandler.getReadableDatabase();
        Cursor cursor = databaseHandler.selectData(selectQuery, true);
        if (cursor != null && cursor.moveToFirst()) {
            UsersModel usersModel = new UsersModel();
            usersModel.name = cursor.getString(cursor.getColumnIndex(TableUsers.name));
            usersModel.userType = cursor.getString(cursor.getColumnIndex(TableUsers.userType));
            usersModel.emailAddress = cursor.getString(cursor.getColumnIndex(TableUsers.emailId));
            usersModel.id = cursor.getLong(cursor.getColumnIndex(TableUsers.id));
            char[] pass = password.toCharArray();
            if (PasswordsUtils.isExpectedPassword(pass, cursor.getBlob(cursor.getColumnIndex(TableUsers.encryption)), cursor.getBlob(cursor.getColumnIndex(TableUsers.password)))) {
                AppStorage.getInstance(context).setValue(AppStorage.SP_USER_ID, usersModel.id);
                AppStorage.getInstance(context).setValue(AppStorage.SP_USER_NAME, usersModel.name);
                AppStorage.getInstance(context).setValue(AppStorage.SP_USER_EMAIL, usersModel.emailAddress);
                return true;
            }
            return false;
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return false;
    }

    /*

    public synchronized long addDeviceModel(DeviceModel deviceModel) {
        databaseHandler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TableUsers.name, deviceModel.name);
        values.put(TableUsers.emailId, deviceModel.deviceAddress);
        try {
            if (deviceModel.id != -1) {
                values.put(TableUsers.id, deviceModel.id);
                return databaseHandler.updateData(
                        TableUsers.TABLE_NAME, values, TableUsers.id + "=?", new String[]{deviceModel.id + ""}
                );
            } else
                return databaseHandler.insertData(TableUsers.TABLE_NAME, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public synchronized DeviceModel getDeviceModel(String macAddress) {
        String selectQuery = "";
        selectQuery = "select * FROM " + TableUsers.TABLE_NAME + " WHERE " + TableUsers.emailId + "='" + macAddress + "'";
        databaseHandler.getReadableDatabase();
        Cursor cursor = databaseHandler.selectData(selectQuery, true);
        if (cursor != null && cursor.moveToFirst()) {
            DeviceModel deviceModel = new DeviceModel();
            deviceModel.name = cursor.getString(cursor.getColumnIndex(TableUsers.name));
            deviceModel.deviceAddress = cursor.getString(cursor.getColumnIndex(TableUsers.emailId));
            deviceModel.id = cursor.getLong(cursor.getColumnIndex(TableUsers.id));
            return deviceModel;
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return null;
    }
*/

    /**
     * Get list of records in ascending order for each individual table
     */
   /* public synchronized ArrayList<TransferModel> getTransferModelsList(boolean isIncoming) {
        boolean showFinished = AppStorage.getInstance(BaseApplication.getInstance()).getValue(AppStorage.SP_SHOW_FINISHED, false);
        ArrayList<TransferModel> arrayList = new ArrayList<>();
        String selectQuery = "";
        if (showFinished) {
            selectQuery = "select * FROM " + TableTransferModel.TABLE_NAME + " WHERE " + TableTransferModel.isIncoming + "='" + (isIncoming ? 0 : 1) + "'" + " ORDER BY " + TableTransferModel.id + " DESC LIMIT 20";
        } else {
            selectQuery = "select * FROM " + TableTransferModel.TABLE_NAME + " WHERE " + TableTransferModel.status + "='" + 0 + (isIncoming ? ("' AND " + TableTransferModel.isIncoming + "='" + 0 + "'") : "'") + " ORDER BY " + TableTransferModel.id + " DESC LIMIT 20";
        }
        databaseHandler.getReadableDatabase();
        Cursor cursor = databaseHandler.selectData(selectQuery, true);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                TransferModel salesReportsModel = new TransferModel();
                salesReportsModel.name = cursor.getString(cursor.getColumnIndex(TableTransferModel.name));
                salesReportsModel.timeStamp = cursor.getString(cursor.getColumnIndex(TableTransferModel.timeStamp));
                salesReportsModel.folderLocation = cursor.getString(cursor.getColumnIndex(TableTransferModel.folderPath));
                salesReportsModel.status = cursor.getInt(cursor.getColumnIndex(TableTransferModel.status));
                salesReportsModel.progress = cursor.getInt(cursor.getColumnIndex(TableTransferModel.progress));
                salesReportsModel.rawData = cursor.getString(cursor.getColumnIndex(TableTransferModel.rawData));
                salesReportsModel.id = cursor.getLong(cursor.getColumnIndex(TableTransferModel.id));
                salesReportsModel.size = Long.parseLong(cursor.getString(cursor.getColumnIndex(TableTransferModel.size)));
                salesReportsModel.isIncoming = cursor.getInt(cursor.getColumnIndex(TableTransferModel.isIncoming)) == 0;
                arrayList.add(salesReportsModel);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return arrayList;
    }*/
    public synchronized Cursor selectData(String tableName) {
        databaseHandler.getReadableDatabase();
        return databaseHandler.selectData(tableName);
    }

    public void closeDb() {
        databaseHandler.clearDB();
        databaseHandler.close();
    }

    public synchronized void deleteAll() {
        databaseHandler.clearAllTables();
        databaseHandler.close();
    }

}
