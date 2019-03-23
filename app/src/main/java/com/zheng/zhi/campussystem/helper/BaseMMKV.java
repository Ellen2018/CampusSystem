package com.zheng.zhi.campussystem.helper;

import com.tencent.mmkv.MMKV;

public abstract class BaseMMKV {

    private MMKV mmkv;

    public BaseMMKV(String mmkvId){
        mmkv = MMKV.mmkvWithID(mmkvId);
    }

    public abstract String getUniqueString();

    public void saveById(String key, Object value){
        key = key + getUniqueString();
        if (value instanceof String) {
            mmkv.encode(key,(String)value);
        } else if (value instanceof Integer) {
            mmkv.encode(key,(Integer) value);
        } else if (value instanceof Boolean) {
            mmkv.encode(key,(Boolean) value);
        } else if (value instanceof Float) {
            mmkv.encode(key,(Float) value);
        } else if (value instanceof Long) {
            mmkv.encode(key,(Long) value);
        } else {
            mmkv.encode(key,(String)value);
        }
    }

    public Object getValueById(String key, Object defaultValue){
        key = key + getUniqueString();
        Object value;
        if (defaultValue instanceof String) {
            value = mmkv.decodeString(key);
            if(value == null) value = defaultValue;
        } else if (defaultValue instanceof Integer) {
            value = mmkv.getInt(key, (Integer) defaultValue);
        } else if (defaultValue instanceof Boolean) {
            value = mmkv.getBoolean(key, (Boolean) defaultValue);
        } else if (defaultValue instanceof Float) {
            value = mmkv.getFloat(key, (Float) defaultValue );
        } else if (defaultValue instanceof Long) {
            value = mmkv.getLong(key, (Long) defaultValue);
        } else {
            value = mmkv.getString(key, (String) defaultValue);
        }
        return value;
    }

    public void save(String key, Object value){
        if (value instanceof String) {
            mmkv.encode(key,(String)value);
        } else if (value instanceof Integer) {
            mmkv.encode(key,(Integer) value);
        } else if (value instanceof Boolean) {
            mmkv.encode(key,(Boolean) value);
        } else if (value instanceof Float) {
            mmkv.encode(key,(Float) value);
        } else if (value instanceof Long) {
            mmkv.encode(key,(Long) value);
        } else {
            mmkv.encode(key,(String)value);
        }
    }

    public Object getValue(String key, Object defaultValue){
        Object value;
        if (defaultValue instanceof String) {
            value = mmkv.decodeString(key);
            if(value == null) value = defaultValue;
        } else if (defaultValue instanceof Integer) {
            value = mmkv.getInt(key, (Integer) defaultValue);
        } else if (defaultValue instanceof Boolean) {
            value = mmkv.getBoolean(key, (Boolean) defaultValue);
        } else if (defaultValue instanceof Float) {
            value = mmkv.getFloat(key, (Float) defaultValue );
        } else if (defaultValue instanceof Long) {
            value = mmkv.getLong(key, (Long) defaultValue);
        } else {
            value = mmkv.getString(key, (String) defaultValue);
        }
        return value;
    }


}
