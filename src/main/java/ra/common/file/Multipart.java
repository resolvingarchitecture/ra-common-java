package ra.common.file;

import ra.common.JSONSerializable;
import ra.common.FileWrapper;
import ra.common.JSONParser;
import ra.common.JSONPretty;
import ra.common.NamedStreamable;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Multipart implements JSONSerializable {

    private String boundary;
    private static final String LINE_FEED = "\r\n";
    //    private HttpURLConnection httpConn;
    private String charset;
    //    private OutputStream out;
//    private PrintWriter b;
    private StringBuilder b = new StringBuilder();

    public Multipart() {}

    public Multipart(String charset) {
        this.charset = charset;
        boundary = createBoundary();

//        URL url = new URL(requestURL);
//        httpConn = (HttpURLConnection) url.openConnection();
//        httpConn.setUseCaches(false);
//        httpConn.setDoOutput(true);
//        httpConn.setDoInput(true);
//        httpConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
//        httpConn.setRequestProperty("User-Agent", "Client");
//        out = httpConn.getOutputStream();
//        b = new PrintWriter(new OutputStreamWriter(out, charset), true);
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getBoundary() {
        return boundary;
    }

    public String createBoundary() {
        Random r = new Random();
        String allowed = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder b = new StringBuilder();
        for (int i=0; i < 32; i++)
            b.append(allowed.charAt(r.nextInt(allowed.length())));
        return b.toString();
    }

    public void addFormField(String name, String value) {
        b.append("--" + boundary).append(LINE_FEED);
        b.append("Content-Disposition: form-data; name=\"" + name + "\"")
                .append(LINE_FEED);
        b.append("Content-Type: text/plain; charset=" + charset).append(LINE_FEED);
        b.append(LINE_FEED);
        b.append(value).append(LINE_FEED);
//        b.flush();
    }

    public void addSubtree(String path, File dir) throws IOException {
        String dirPath = path + (path.length() > 0 ? "/" : "") + dir.getName();
        addDirectoryPart(dirPath);
        for (File f: dir.listFiles()) {
            if (f.isDirectory())
                addSubtree(dirPath, f);
            else
                addFilePart("file", new FileWrapper(dirPath + "/", f));
        }
    }

    public void addDirectoryPart(String path) {
        try {
            b.append("--" + boundary).append(LINE_FEED);
            b.append("Content-Disposition: file; filename=\"" + URLEncoder.encode(path, "UTF-8") + "\"").append(LINE_FEED);
            b.append("Content-Type: application/x-directory").append(LINE_FEED);
            b.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
            b.append(LINE_FEED);
            b.append(LINE_FEED);
//            b.flush();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public void addFilePart(String fieldName, NamedStreamable uploadFile) throws IOException {
        String fileName = uploadFile.getName();
        b.append("--" + boundary).append(LINE_FEED);
        if (fileName == null)
            b.append("Content-Disposition: file; name=\"" + fieldName + "\";").append(LINE_FEED);
        else
            b.append("Content-Disposition: file; filename=\"" + fileName + "\"").append(LINE_FEED);
        b.append("Content-Type: application/octet-stream").append(LINE_FEED);
        b.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
        b.append(LINE_FEED);
//        b.flush();

//        InputStream inputStream = uploadFile.getInputStream();
//        byte[] buffer = new byte[4096];
//        int r;
//        while ((r = inputStream.read(buffer)) != -1)
//            out.write(buffer, 0, r);
//        out.flush();
//        inputStream.close();

        b.append(LINE_FEED);
//        b.flush();
    }

    public void addHeaderField(String name, String value) {
        b.append(name + ": " + value).append(LINE_FEED);
//        b.flush();
    }

    public String finish() throws IOException {
//        StringBuilder b = new StringBuilder();

        b.append("--" + boundary + "--").append(LINE_FEED);
//        b.close();

//        int status = httpConn.getResponseCode();
//        if (status == HttpURLConnection.HTTP_OK) {
//            BufferedReader reader = new BufferedReader(new InputStreamReader(
//                    httpConn.getInputStream()));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                b.append(line);
//            }
//            reader.close();
//            httpConn.disconnect();
//        } else {
//            try {
//                BufferedReader reader = new BufferedReader(new InputStreamReader(
//                        httpConn.getInputStream()));
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    b.append(line);
//                }
//                reader.close();
//            } catch (Throwable t) {}
//            throw new IOException("Server returned status: " + status + " with body: "+b.toString() + " and Trailer header: "+httpConn.getHeaderFields().get("Trailer"));
//        }

        return b.toString();
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> m = new HashMap<>();
        if(charset!=null) m.put("charset", charset);
        if(boundary!=null) m.put("boundary", boundary);
        return m;
    }

    @Override
    public void fromMap(Map<String, Object> m) {
        if(m.get("charset")!=null) charset = (String)m.get("charset");
        if(m.get("boundary")!=null) boundary = (String)m.get("boundary");
    }

    @Override
    public String toJSON() {
        return JSONPretty.toPretty(JSONParser.toString(toMap()), 4);
    }

    @Override
    public void fromJSON(String json) {
        fromMap((Map<String, Object>) JSONParser.parse(json));
    }

    @Override
    public String toString() {
        return toJSON();
    }
}
