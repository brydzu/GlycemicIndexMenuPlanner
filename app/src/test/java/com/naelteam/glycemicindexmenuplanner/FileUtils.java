package com.naelteam.glycemicindexmenuplanner;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by fab on 03/07/15.
 */
public class FileUtils {

    public static String loadAssetFile(Context context, String path) throws IOException {
        BufferedReader reader = null;
        String data = null;
        try {
            reader = new BufferedReader(new InputStreamReader(context.getResources().getAssets().open(path)));
            String str;
            while ((str = reader.readLine()) != null) {
                data += str;
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return data;
    }
}
