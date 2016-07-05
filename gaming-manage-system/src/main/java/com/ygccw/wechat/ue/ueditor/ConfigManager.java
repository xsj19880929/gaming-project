//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ygccw.wechat.ue.ueditor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public final class ConfigManager {
    private final InputStream in;
    private JSONObject jsonConfig = null;

    private ConfigManager(InputStream in) throws FileNotFoundException, IOException {

        this.in = in;
        this.initEnv();
    }

    private void initEnv() throws FileNotFoundException, IOException {

        String configContent = this.readFile();

        try {
            JSONObject e = new JSONObject(configContent);
            this.jsonConfig = e;
        } catch (Exception var4) {
            this.jsonConfig = null;
        }

    }

    private String readFile() throws IOException {
        StringBuilder builder = new StringBuilder();

        try {
            InputStreamReader reader = new InputStreamReader(in, "UTF-8");
            BufferedReader bfReader = new BufferedReader(reader);
            String tmpContent = null;

            /*while ((tmpContent = bfReader.readLine()) != null) {
                builder.append(tmpContent);
            }
*/
            do {
                tmpContent = bfReader.readLine();
                builder.append(tmpContent);
            } while (tmpContent != null);

            bfReader.close();
        } catch (UnsupportedEncodingException var6) {
            var6.printStackTrace();
        }

        return this.filter(builder.toString());
    }

    private String filter(String input) {
        return input.replaceAll("/\\*[\\s\\S]*?\\*/", "");
    }

    public static ConfigManager getInstance(InputStream in) {
        try {
            return new ConfigManager(in);
        } catch (Exception var4) {
            var4.printStackTrace();
        }
        return null;
    }


    public boolean valid() {
        return this.jsonConfig != null;
    }

    public JSONObject getAllConfig() {
        return this.jsonConfig;
    }

    public Map<String, Object> getConfig(int type) {
        HashMap conf = new HashMap();
        String savePath = null;
        switch (type) {
            case 1:
                conf.put("isBase64", "false");
                conf.put("maxSize", Long.valueOf(this.jsonConfig.getLong("imageMaxSize")));
                conf.put("allowFiles", this.getArray("imageAllowFiles"));
                conf.put("fieldName", this.jsonConfig.getString("imageFieldName"));
                savePath = this.jsonConfig.getString("imagePathFormat");
                break;
            case 2:
                conf.put("filename", "scrawl");
                conf.put("maxSize", Long.valueOf(this.jsonConfig.getLong("scrawlMaxSize")));
                conf.put("fieldName", this.jsonConfig.getString("scrawlFieldName"));
                conf.put("isBase64", "true");
                savePath = this.jsonConfig.getString("scrawlPathFormat");
                break;
            case 3:
                conf.put("maxSize", Long.valueOf(this.jsonConfig.getLong("videoMaxSize")));
                conf.put("allowFiles", this.getArray("videoAllowFiles"));
                conf.put("fieldName", this.jsonConfig.getString("videoFieldName"));
                savePath = this.jsonConfig.getString("videoPathFormat");
                break;
            case 4:
                conf.put("isBase64", "false");
                conf.put("maxSize", Long.valueOf(this.jsonConfig.getLong("fileMaxSize")));
                conf.put("allowFiles", this.getArray("fileAllowFiles"));
                conf.put("fieldName", this.jsonConfig.getString("fileFieldName"));
                savePath = this.jsonConfig.getString("filePathFormat");
                break;
            case 5:
                conf.put("filename", "remote");
                conf.put("filter", this.getArray("catcherLocalDomain"));
                conf.put("maxSize", Long.valueOf(this.jsonConfig.getLong("catcherMaxSize")));
                conf.put("allowFiles", this.getArray("catcherAllowFiles"));
                conf.put("fieldName", this.jsonConfig.getString("catcherFieldName") + "[]");
                savePath = this.jsonConfig.getString("catcherPathFormat");
                break;
            case 6:
                setConfigOfTypeSix(conf);
                break;
            case 7:
                setConfigOfTypeSeven(conf);
                break;
            default:
        }
        conf.put("savePath", savePath);
        return conf;
    }

    private String[] getArray(String key) {
        JSONArray jsonArray = this.jsonConfig.getJSONArray(key);
        String[] result = new String[jsonArray.length()];
        int i = 0;

        for (int len = jsonArray.length(); i < len; ++i) {
            result[i] = jsonArray.getString(i);
        }

        return result;
    }

    private void setConfigOfTypeSix(Map<String, Object> conf) {
        conf.put("allowFiles", this.getArray("fileManagerAllowFiles"));
        conf.put("dir", this.jsonConfig.getString("fileManagerListPath"));
        conf.put("count", Integer.valueOf(this.jsonConfig.getInt("fileManagerListSize")));
    }

    private void setConfigOfTypeSeven(Map<String, Object> conf) {
        conf.put("allowFiles", this.getArray("imageManagerAllowFiles"));
        conf.put("dir", this.jsonConfig.getString("imageManagerListPath"));
        conf.put("count", Integer.valueOf(this.jsonConfig.getInt("imageManagerListSize")));
    }
}
