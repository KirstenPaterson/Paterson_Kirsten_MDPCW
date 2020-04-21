package com.example.paterson_kirsten_mdpcw;


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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

//Kirsten Paterson S1828151
/**
 * A simple {@link Fragment} subclass.
 */
public class IListFrag extends ListFragment {
    ArrayList<String> titles;
    ArrayList<String> descriptions;

    IListFrag.ItemSelected activity;

    public IListFrag() {
        // Required empty public constructor
    }

    public interface ItemSelected{

        void onItemSelected(int index);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        activity = (IListFrag.ItemSelected) context;
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

    public class ProcessInBackground extends AsyncTask<Integer, Void, Exception> {

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
                //if deosnt work then change to other link
                URL url = new URL("https://learn-eu-central-1-prod-fleet01-xythos.s3-eu-central-1.amazonaws.com/5c1a4465d06f0/7178906?response-content-disposition=inline%3B%20filename%2A%3DUTF-8%27%27currentIncidents.txt&response-content-type=text%2Fplain&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20200421T111050Z&X-Amz-SignedHeaders=host&X-Amz-Expires=21600&X-Amz-Credential=AKIAZH6WM4PLYI3L4QWN%2F20200421%2Feu-central-1%2Fs3%2Faws4_request&X-Amz-Signature=572af7a632b584750e57f8158160d0ca4f17b9ed475c362630c9a527afdf5fb4");
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
        IDetailFrag detailFrag = new IDetailFrag();

        Bundle bundle = new Bundle();
        bundle.putString("selected_data", selectedData);
        detailFrag.setArguments(bundle);
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.frag_containerB, detailFrag).commit();

    }

}
