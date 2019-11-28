package com.mxdl.customview.bitmap.test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.util.LruCache;

import com.mxdl.customview.bitmap.DiskLruCache;
import com.mxdl.customview.bitmap.ImageResizer;
import com.mxdl.customview.bitmap.test.entity.Person;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Description: <Test><br>
 * Author:      mxdl<br>
 * Date:        2019/11/28<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class Test {

    private DiskLruCache mDiskLruCache;

    public static void main(String[] args) {
        //test();
        //test2();
    }

    private void test2() {
        File forder = Environment.getDownloadCacheDirectory();
        try {
            mDiskLruCache = DiskLruCache.open(forder, 100, 1, 50 * 1024 * 1024);
            DiskLruCache.Editor editor = mDiskLruCache.edit("mxdl");
            if (editor != null) {
                OutputStream outputStream = editor.newOutputStream(0);
                if (downloadUrlToStream("mxdl", outputStream)) {
                    editor.commit();
                } else {
                    editor.abort();
                }
                mDiskLruCache.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void getFile(String url){
        Bitmap bitmap = null;
        String key = hashKeyFormUrl(url);
        DiskLruCache.Snapshot snapShot = null;
        try {
            snapShot = mDiskLruCache.get(key);
            if (snapShot != null) {
                FileInputStream fileInputStream = (FileInputStream)snapShot.getInputStream(0);
                FileDescriptor fileDescriptor = fileInputStream.getFD();
                bitmap = new ImageResizer().decodeSampledBitmapFromFileDescriptor(fileDescriptor,100,100);
                if (bitmap != null) {
                    //addBitmapToMemoryCache(key,bitmap);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private String hashKeyFormUrl(String url) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(url.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(url.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public boolean downloadUrlToStream(String urlString, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), 1024 * 1);
            out = new BufferedOutputStream(outputStream, 1024 * 1);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (IOException e) {
            Log.e("MYTAG", "downloadBitmap failed." + e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            //MyUtils.close(out);
            //MyUtils.close(in);
        }
        return false;
    }

    private static void test() {
        HashMap<String, String> map1 = new HashMap<>();
        map1.put("zhangsan", "zhangsan");
        map1.put("lisi", "lisi");
        map1.put("wangwu", "wangwu");
        Set<Map.Entry<String, String>> entrySet1 = map1.entrySet();
        Iterator<Map.Entry<String, String>> iterator1 = entrySet1.iterator();
        while (iterator1.hasNext()) {
            System.out.println(iterator1.next().toString());
        }
        System.out.println("==========================================");
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("zhangsan", "zhangsan");
        map.put("lisi", "lisi");
        map.put("wangwu", "wangwu");

        String wangwu = map.get("zhangsan");
        String put = map.put("zhaoliu", "zhaoliu");

        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next().toString());
        }
    }
}
