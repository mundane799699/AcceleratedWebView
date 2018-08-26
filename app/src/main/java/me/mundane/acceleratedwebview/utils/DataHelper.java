package me.mundane.acceleratedwebview.utils;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.WebResourceResponse;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mundane on 2018/8/26 上午11:46
 */

public class DataHelper {
    
    // http://renyugang.io/post/75
    // http://renyugang.io/wp-content/themes/twentyseventeen/style.css?ver=4.9.8
    // http://renyugang.io/wp-content/themes/twentyseventeen/assets/images/header.jpg
    // http://renyugang.io/wp-content/uploads/2018/06/cropped-ryg.png
    // http://renyugang.io/wp-content/uploads/2018/05/ygs_small.jpg
    // http://0.gravatar.com/avatar/9ec347970e814fad30eac681216dcc5e?s=200&d=monsterid&r=g
    // http://2.gravatar.com/avatar/527fb8232d1c925833c1a74c9a338228?s=200&d=monsterid&r=g
    // http://0.gravatar.com/avatar/f5bbf272059f28d7c8a522e48bc37157?s=200&d=monsterid&r=g
    // http://1.gravatar.com/avatar/799e623541ac888a6b45444180503e14?s=200&d=monsterid&r=g
    // http://1.gravatar.com/avatar/762784f64563e97d7eebe580f425e7bc?s=200&d=monsterid&r=g
    // http://1.gravatar.com/avatar/d14f2d0d63934af4abd8253b020b3625?s=200&d=monsterid&r=g
    // http://0.gravatar.com/avatar/95ccef9f30cd0ef2e2f3b4b577c7f9a5?s=200&d=monsterid&r=g
    // http://1.gravatar.com/avatar/457c3796dba92ea464ad55a25a9c1027?s=200&d=monsterid&r=g
    // http://0.gravatar.com/avatar/fab6cf9350832e789a511146e11e3698?s=200&d=monsterid&r=g
    // http://0.gravatar.com/avatar/0d572f5dce2fba1175f7970c9e76a47f?s=200&d=monsterid&r=g
    // http://2.gravatar.com/avatar/2f7b11077e5c22320f3d8fbb40f2604c?s=200&d=monsterid&r=g
    // http://2.gravatar.com/avatar/8500d206dec8a7ca186836bfa670bb6e?s=200&d=monsterid&r=g
    // http://1.gravatar.com/avatar/a317bc3fcb4234e8b2743b8ea7e18ebd?s=200&d=monsterid&r=g
    // http://1.gravatar.com/avatar/7b8f5227da6477a4e2a7aebe9f65b0a3?s=200&d=monsterid&r=g
    // http://1.gravatar.com/avatar/4bb4fde28ff305c350cb97791ec8c147?s=200&d=monsterid&r=g
    // http://1.gravatar.com/avatar/70696ed7df0fb375cab28a8c197a7643?s=200&d=monsterid&r=g
    // http://renyugang.io/wp-content/uploads/2018/06/cropped-ryg-1-192x192.png
    // http://renyugang.io/wp-content/uploads/2018/06/cropped-ryg-1-32x32.png
    
    private Map<String, String> mMap;
    
    public DataHelper() {
        mMap = new HashMap<>();
        initData();
    }
    
    public WebResourceResponse getReplacedWebResourceResponse(Context context, String url) {
        String localResourcePath = mMap.get(url);
        if (TextUtils.isEmpty(localResourcePath)) {
            return null;
        }
        InputStream is = null;
        try {
            is = context.getApplicationContext().getAssets().open(localResourcePath);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        String mimeType;
        if (url.contains("css")) {
            mimeType = "text/css";
        } else if (url.contains("jpg")) {
            mimeType = "image/jpeg";
        } else {
            mimeType = "image/png";
        }
        WebResourceResponse response = new WebResourceResponse(mimeType, "utf-8", is);
        return response;
    }
    
    public boolean hasLocalResource(String url) {
        return mMap.containsKey(url);
    }
    
    private void initData() {
        String imageDir = "images/";
        String pngSuffix = ".png";
        mMap.put("http://renyugang.io/wp-content/themes/twentyseventeen/style.css?ver=4.9.8",
                "css/style.css");
        mMap.put("http://renyugang.io/wp-content/uploads/2018/06/cropped-ryg.png",
                imageDir + "cropped-ryg.png");
        mMap.put("http://renyugang.io/wp-content/themes/twentyseventeen/assets/images/header.jpg",
                imageDir + "header.jpg");
        mMap.put("http://renyugang.io/wp-content/uploads/2018/05/ygs_small.jpg",
                imageDir + "ygs_small.jpg");
        mMap.put(
                "http://0.gravatar.com/avatar/9ec347970e814fad30eac681216dcc5e?s=200&d=monsterid&r=g",
                imageDir + "9ec347970e814fad30eac681216dcc5e" + pngSuffix);
        mMap.put(
                "http://2.gravatar.com/avatar/527fb8232d1c925833c1a74c9a338228?s=200&d=monsterid&r=g",
                imageDir + "527fb8232d1c925833c1a74c9a338228" + pngSuffix);
        mMap.put(
                "http://0.gravatar.com/avatar/f5bbf272059f28d7c8a522e48bc37157?s=200&d=monsterid&r=g",
                imageDir + "f5bbf272059f28d7c8a522e48bc37157" + pngSuffix);
        mMap.put(
                "http://1.gravatar.com/avatar/799e623541ac888a6b45444180503e14?s=200&d=monsterid&r=g",
                imageDir + "799e623541ac888a6b45444180503e14" + pngSuffix);
        mMap.put(
                "http://1.gravatar.com/avatar/762784f64563e97d7eebe580f425e7bc?s=200&d=monsterid&r=g",
                imageDir + "762784f64563e97d7eebe580f425e7bc" + pngSuffix);
        mMap.put(
                "http://1.gravatar.com/avatar/d14f2d0d63934af4abd8253b020b3625?s=200&d=monsterid&r=g",
                imageDir + "d14f2d0d63934af4abd8253b020b3625" + pngSuffix);
        mMap.put(
                "http://0.gravatar.com/avatar/95ccef9f30cd0ef2e2f3b4b577c7f9a5?s=200&d=monsterid&r=g",
                imageDir + "95ccef9f30cd0ef2e2f3b4b577c7f9a5" + pngSuffix);
        mMap.put(
                "http://1.gravatar.com/avatar/457c3796dba92ea464ad55a25a9c1027?s=200&d=monsterid&r=g",
                imageDir + "457c3796dba92ea464ad55a25a9c1027" + pngSuffix);
        mMap.put(
                "http://0.gravatar.com/avatar/fab6cf9350832e789a511146e11e3698?s=200&d=monsterid&r=g",
                imageDir + "fab6cf9350832e789a511146e11e3698" + pngSuffix);
        
        mMap.put(
                "http://0.gravatar.com/avatar/0d572f5dce2fba1175f7970c9e76a47f?s=200&d=monsterid&r=g",
                imageDir + "0d572f5dce2fba1175f7970c9e76a47f" + pngSuffix);
        mMap.put(
                "http://2.gravatar.com/avatar/2f7b11077e5c22320f3d8fbb40f2604c?s=200&d=monsterid&r=g",
                imageDir + "2f7b11077e5c22320f3d8fbb40f2604c" + pngSuffix);
        mMap.put(
                "http://2.gravatar.com/avatar/8500d206dec8a7ca186836bfa670bb6e?s=200&d=monsterid&r=g",
                imageDir + "8500d206dec8a7ca186836bfa670bb6e" + pngSuffix);
        mMap.put(
                "http://1.gravatar.com/avatar/a317bc3fcb4234e8b2743b8ea7e18ebd?s=200&d=monsterid&r=g",
                imageDir + "a317bc3fcb4234e8b2743b8ea7e18ebd" + pngSuffix);
        mMap.put(
                "http://1.gravatar.com/avatar/7b8f5227da6477a4e2a7aebe9f65b0a3?s=200&d=monsterid&r=g",
                imageDir + "7b8f5227da6477a4e2a7aebe9f65b0a3" + pngSuffix);
        mMap.put(
                "http://1.gravatar.com/avatar/4bb4fde28ff305c350cb97791ec8c147?s=200&d=monsterid&r=g",
                imageDir + "4bb4fde28ff305c350cb97791ec8c147" + pngSuffix);
        mMap.put(
                "http://1.gravatar.com/avatar/70696ed7df0fb375cab28a8c197a7643?s=200&d=monsterid&r=g",
                imageDir + "70696ed7df0fb375cab28a8c197a7643" + pngSuffix);
        mMap.put("http://renyugang.io/wp-content/uploads/2018/06/cropped-ryg-1-192x192.png",
                imageDir + "cropped-ryg-1-192x192.png");
        mMap.put("http://renyugang.io/wp-content/uploads/2018/06/cropped-ryg-1-32x32.png",
                imageDir + "cropped-ryg-1-32x32.png");
    }
}
