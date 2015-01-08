package com.example.alie.myapplication.activity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alie.myapplication.Constants;
import com.example.alie.myapplication.LazyAdapter;
import com.example.alie.myapplication.XMLParser;
import com.example.alie.myapplication.R;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    String msg = "Android : ";
    ListView listView ;

    // All static variables
    static final String URL = "http://api.androidhive.info/music/music.xml";
    // XML node keys
    static final String KEY_SONG = "song"; // parent node
    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_ARTIST = "artist";
    public static final String KEY_DURATION = "duration";
    public static final String KEY_THUMB_URL = "thumb_url";

    LazyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        
        ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

        XMLParser parser = new XMLParser();
        String xml = parser.getXmlFromUrl(URL); // getting XML from URL
        Document doc = parser.getDomElement(xml); // getting DOM element

        NodeList nl = doc.getElementsByTagName(KEY_SONG);
        // looping through all song nodes <song>
        for (int i = 0; i < nl.getLength(); i++) {
            // creating new HashMap
            HashMap<String, String> map = new HashMap<String, String>();
            Element e = (Element) nl.item(i);
            // adding each child node to HashMap key => value
            map.put(KEY_ID, parser.getValue(e, KEY_ID));
            map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));
            map.put(KEY_ARTIST, parser.getValue(e, KEY_ARTIST));
            map.put(KEY_DURATION, parser.getValue(e, KEY_DURATION));
            map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL));

            // adding HashList to ArrayList
            songsList.add(map);
        }


        listView=(ListView)findViewById(R.id.listview);

        // Getting adapter by passing xml data ArrayList
        adapter=new LazyAdapter(this, songsList);
        listView.setAdapter(adapter);


        // Click event for single list row
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.d(msg, "Click Item : " + id);

            }
        });
//        try{
//            HttpClient Client = new DefaultHttpClient();
//
//            String URL = Constants.URL_SERVER+"/api/project/get_projects?key=" + Constants.User.USER_KEY;
//            Log.d(msg, "Url Get : " + URL);
//            try
//            {
//                String respondServer = "";
//
//                HttpGet httpget = new HttpGet(URL);
//                ResponseHandler<String> responseHandler = new BasicResponseHandler();
//                respondServer = Client.execute(httpget, responseHandler);
//
//                JSONObject a = new JSONObject(respondServer);
//                if(a.optInt("status", 0) == 1) {
////                    Log.d(msg, "Data Project : " + respondServer);
//                }
//            }
//            catch(Exception ex)
//            {
//                ex.printStackTrace();
//            }
//        }
//        catch(Exception ex)
//        {
//            ex.printStackTrace();
//        }
//
//        final ListView listview = (ListView) findViewById(R.id.listview);
//        String[] values = new String[] {
//                "Android",
//                "iPhone",
//                "WindowsMobile",
//        };
//
//        final ArrayList<String> list = new ArrayList<String>();
//        for (int i = 0; i < values.length; ++i) {
//            list.add(values[i]);
//        }
//
//        final StableArrayAdapter adapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, list);
//        listview.setAdapter(adapter);
//
//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//            @Override
//            public void onItemClick(AdapterView<?> parent, final View view,
//                                    int position, long id) {
//                Log.d(msg, "Click Item : " + id);
//            }
//
//        });
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void creteProjectAction(View view) {
//        startActivity(new Intent(getApplicationContext(), AddProjectActivity.class));
        Log.d(msg, "Create project");
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
