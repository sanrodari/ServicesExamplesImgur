package com.example.servicesexamplesimgur;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

import com.github.kevinsawicki.http.HttpRequest;

public class BlockingWorker {
	
	public String performQuery() {
		String response = 
				HttpRequest.get("https://api.imgur.com/3/gallery/search?q=cats").
				authorization("Client-ID 9e4bbe3f8557a98").
				// No es recomendado pero lo usamos para aceptar todos los certificados
				trustAllCerts().
				trustAllHosts().
				body();
		
		return response;
	}

	public String download(String link, String id) throws Exception {

		InputStream in = new URL(link).openConnection().getInputStream();
		Bitmap bm = BitmapFactory.decodeStream(in);

		String cacheDir = "/servicesexamplesimgur/";
		File fullCacheDir = new File(Environment.getExternalStorageDirectory(), cacheDir);
		String fileLocalName = id;
		File fileUri = new File(fullCacheDir, fileLocalName);
		FileOutputStream outStream = null;
		outStream = new FileOutputStream(fileUri);
		bm.compress(Bitmap.CompressFormat.JPEG, 75, outStream);
		outStream.flush();
		
		return "/sdcard" + cacheDir + fileLocalName;
	}

}
