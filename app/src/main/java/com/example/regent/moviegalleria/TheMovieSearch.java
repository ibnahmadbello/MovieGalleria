package com.example.regent.moviegalleria;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class TheMovieSearch {

    public byte[] getUrlBytes(String movieUrl) throws IOException{

        URL url = new URL(movieUrl);
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            InputStream inputStream = httpsURLConnection.getInputStream();

            if (httpsURLConnection.getResponseCode() != HttpsURLConnection.HTTP_OK){
                throw new IOException(httpsURLConnection.getResponseMessage() + ": with " + movieUrl);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = inputStream.read(buffer)) > 0){
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
            return outputStream.toByteArray();
        } finally {
            httpsURLConnection.disconnect();
        }

    }
    public String getUrlString(String movieUrl) throws IOException{
        return new String(getUrlBytes(movieUrl));
    }
}
