package com.example.paterson_kirsten_mdpcw;
//Kirsten Paterson S1828151

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CListFrag extends ListFragment {

    ArrayList<String> titles;
    ArrayList<String> descriptions;

    ItemSelected activity;


    public CListFrag() {
        // Required empty public constructor
    }

    public interface ItemSelected{

        void onItemSelected(int index);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

       activity = (ItemSelected) context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getListView().findViewById(R.id.frag_containerA);
        titles = new ArrayList<String>();
        descriptions = new ArrayList<String>();
        activity.onItemSelected(0);


        new ProcessInBackground().execute();

    }

    public InputStream getInput(URL url){
        try{
            return url.openConnection().getInputStream();

        }
        catch(IOException e){
            return null;
        }

    }

    public class ProcessInBackground extends AsyncTask<Integer, Void, Exception>{

        ProgressDialog progressDialog = new ProgressDialog(getActivity());

        Exception exception = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("Loading feed");
            progressDialog.show();
        }


        @Override
        protected Exception doInBackground(Integer... integers) {
            try {
                //change to incidents after
                URL url = new URL("https://trafficscotland.org/rss/feeds/roadworks.aspx");
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xmlpp = factory.newPullParser();

                xmlpp.setInput(getInput(url), "UTF_8");

                boolean insideItem = false;

                int eventType = xmlpp.getEventType();

                while(eventType !=XmlPullParser.END_DOCUMENT){
                    if (eventType == XmlPullParser.START_TAG){
                        if(xmlpp.getName().equalsIgnoreCase("item")){
                            insideItem = true;
                        }
                        else if(xmlpp.getName().equalsIgnoreCase("title")){
                            if(insideItem){
                                titles.add(xmlpp.nextText());
                            }
                        }
                        else if(xmlpp.getName().equalsIgnoreCase("description")){
                            if (insideItem){
                                descriptions.add(xmlpp.nextText());
                            }
                        }

                    }
                    else if (eventType == XmlPullParser.END_TAG && xmlpp.getName().equalsIgnoreCase("item")){
                        insideItem = false;
                    }
                    eventType = xmlpp.next();
                }

            }catch (MalformedURLException e){
                exception =e;

            }catch(XmlPullParserException e){
                exception =e;


            }catch(IOException e){
                exception =e;
            }

            return exception;
        }

        @Override
        protected void onPostExecute(Exception e) {
            super.onPostExecute(e);

            setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, titles));

            progressDialog.dismiss();



        }
    }


    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        String selectedData = descriptions.get(position);
        CDetailFrag detailFrag = new CDetailFrag();

        Bundle bundle = new Bundle();
        bundle.putString("selected_data", selectedData);
        detailFrag.setArguments(bundle);
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.frag_containerB, detailFrag).commit();

    }
}
