package com.mxdl.customview.bitmap.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;

import com.mxdl.customview.R;
import com.mxdl.customview.bitmap.test.entity.Person;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class TestCacheActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_cache);
        long maxMemory = Runtime.getRuntime().maxMemory();
        Log.v("MYTAG","maxMemary:"+maxMemory+"b");
        Log.v("MYTAG","maxMemary:"+maxMemory/(1024)+"kb");
        Log.v("MYTAG","maxMemary:"+maxMemory/(1024*1024)+"mb");

        LruCache<String, Person> cache = new LruCache<>(3);
        cache.put("0",new Person("0,",19));
        cache.put("1",new Person("1,",19));
        cache.put("2",new Person("2,",19));

        Person person = cache.get("0");
        //cache.put("3",new Person("3,",19));

        LinkedHashMap<String, Person> linkedHashMap = (LinkedHashMap<String, Person>) cache.snapshot();
        Set<String> set = linkedHashMap.keySet();
        Iterator<String> iterator = set.iterator();
        while(iterator.hasNext()){
            Log.v("MYTAG",linkedHashMap.get(iterator.next()).toString());
        }
        Log.v("MYTAG","===============================-------------------------");

        LruCache<String,String> map = new LruCache<>(3);
        map.put("zhangsan","zhangsan");
        map.put("lisi","lisi");
        map.put("wangwu","wangwu");

        String wangwu = map.get("zhangsan");
        String put = map.put("zaholiu", "zhaoliou");
        String put1 = map.put("zaholiu", "zhaoliou");
        Log.v("MYTAG",put == null ? "null":"not null");
        Log.v("MYTAG",put1 == null ? "null":"not null");

        Set<Map.Entry<String, String>> entrySet = map.snapshot().entrySet();
        Iterator<Map.Entry<String, String>> iterator1 = entrySet.iterator();
        while(iterator1.hasNext()){
            Log.v("MYTAG",iterator1.next().toString());
        }
    }

}
