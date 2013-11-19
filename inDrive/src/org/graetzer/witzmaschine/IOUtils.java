package org.graetzer.witzmaschine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.content.Context;
import android.util.Log;

public class IOUtils {
	public static String getContentsOfFile(Context ctx, String name) {
		if (!ctx.getFileStreamPath(name).exists())
			return null;

		StringBuffer fileData = new StringBuffer();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					ctx.openFileInput(name)));
			char[] buf = new char[1024];
			int numRead = 0;

			while ((numRead = reader.read(buf)) != -1) {
				fileData.append(String.valueOf(buf, 0, numRead));
			}

		} catch (Exception e) {
			Log.e("IOUtils", "Can't read file", e);
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
			}
		}

		return fileData.toString();
	}

	public static void writeStringToFile(Context ctx, String name, String value) {
		if (ctx == null) return;
		if (value == null) {
			ctx.deleteFile(name);
			return;
		}
		
		OutputStreamWriter o = null;
		try {
			o = new OutputStreamWriter(ctx.openFileOutput(name, 0));
			o.write(value);
			o.flush();
		} catch (Exception e) {
			Log.e("IOUtils", "Can't read file", e);
		} finally {
			try {
				if (o != null)
					o.close();
			} catch (IOException e) {
			}
		}
	}
}
