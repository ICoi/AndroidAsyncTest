package com.example.ico.androidasynctest;

import android.preference.PreferenceActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.*;

import org.apache.http.Header;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends ActionBarActivity {
    private AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        client = new AsyncHttpClient();
        PersistentCookieStore myCookieStore = new PersistentCookieStore(getApplicationContext());
        List<Cookie> cookies = myCookieStore.getCookies();

        if(cookies.isEmpty()){
            // 쿠키값이 존재하지 않는경우 추가하는 로직
            // 이 부분은 server success 이후로 추가하는 편이 나을듯!!
            Toast.makeText(getApplicationContext(),"no cookies",Toast.LENGTH_SHORT).show();

            client.setCookieStore(myCookieStore);
            BasicClientCookie newCookie = new BasicClientCookie("cookiesare", "awesome");
            newCookie.setVersion(1);
            newCookie.setDomain("mydomain.com");
            newCookie.setPath("/");
            myCookieStore.addCookie(newCookie);

        }else{
            // 쿠키 값이 있는 경우 해당 쿠키값 받아오는 거 ㅎㅎ
            Toast.makeText(getApplicationContext(),"HAS cookies // " + cookies.get(0).getName() + " - " + cookies.get(0).getValue(),Toast.LENGTH_SHORT).show();

        }

        client.get("http://namjungnaedle123.cafe24.com:3000/app/menu", new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK".
                String resStr = new String(response);
                try {
                    JSONObject object = new JSONObject(resStr);
                    Toast.makeText(getApplicationContext(),object.getString("status"),Toast.LENGTH_SHORT).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Toast.makeText(getApplicationContext(),"onFailure",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
