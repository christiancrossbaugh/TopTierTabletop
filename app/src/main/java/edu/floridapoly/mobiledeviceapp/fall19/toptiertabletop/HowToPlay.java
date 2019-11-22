package edu.floridapoly.mobiledeviceapp.fall19.toptiertabletop;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.os.Bundle;

public class HowToPlay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_play);

        WebView browser = (WebView) findViewById(R.id.tutorial);
        browser.loadUrl("http://www.wasd20.net/resources/beginners-guide-to-dd-5th-edition/");
    }
}
