package me.shumon.twittertagsearches;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

//import android.app.Activity;
//import android.view.Menu;
//import android.view.MenuItem;

import android.content.DialogInterface;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends ListActivity {
    private static final String SEARCHES = "searches";

    private EditText queryEditText; // EditText where user enters a query
    private EditText tagEditText; // EditText where user tags a query
    private SharedPreferences savedSearches; // user's favorite searches
    private ArrayList<String> tags; // list of tags for saved searches
    private ArrayAdapter<String> adapter; // binds tags to ListView


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queryEditText = (EditText) findViewById(R.id.queryEditText);
        tagEditText = (EditText) findViewById(R.id.tagEditText);

        savedSearches = getSharedPreferences(SEARCHES, MODE_PRIVATE);

        tags = new ArrayList<String>(savedSearches.getAll().keySet());
        Collections.sort(tags, String.CASE_INSENSITIVE_ORDER);

        //ArrayAdapter to bind tags into ListView
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, tags);
        setListAdapter(adapter);

        ImageButton saveButton = (ImageButton) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(saveButtonListener);

        getListView().setOnItemClickListener(itemClickListener);
        getListView().setOnItemLongClickListener(itemLongClickListener);

    } //end onCreate


    public OnClickListener saveButtonListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (queryEditText.getText().length() > 0 &&
                    tagEditText.getText().length() > 0)
            {
                addTaggedSearch(queryEditText.getText().toString(), tagEditText.getText().toString());
                queryEditText.setText(""); // clear field for new search
                tagEditText.setText(""); // clear field for new search

                ((InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromInputMethod
                        (tagEditText.getWindowToken(), 0);
            } // end if

            else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setMessage(R.string.missingMessage);

                builder.setPositiveButton(R.string.ok, null);

                AlertDialog errorDialog = builder.create();

                errorDialog.show();
            } // end else
        } // end onClick
    }; // end OnClickListener

    private void addTaggedSearch(String query, String tag){
        SharedPreferences.Editor prefEditor = savedSearches.edit();
        prefEditor.putString(tag, query);
        prefEditor.apply();

        if (!tags.contains(tag))
        {
            tags.add(tag);
            Collections.sort(tags, String.CASE_INSENSITIVE_ORDER);
            adapter.notifyDataSetChanged(); // rebind tags to list view.
        }
    }


    OnItemClickListener itemClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String tag = ((TextView) view).getText().toString();
            String urlString = getString(R.string.searchURL) +
                    Uri.encode(savedSearches.getString(tag, ""), "UTF-8");

            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
            startActivity(webIntent);
        }
    };

    OnItemLongClickListener itemLongClickListener = new OnItemLongClickListener() {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            // String that user tapped long
            final String tag = ((TextView) view).getText().toString();

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            //alertDialog title
            builder.setTitle(getString(R.string.shareEditDeleteTitle, tag));

            builder.setItems(R.array.dialog_items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {

                    switch (which)
                    {
                        case 0: //share
                            shareSearch(tag);
                            break;

                        case 1: //eidt
                            tagEditText.setText(tag);
                            queryEditText.setText(savedSearches.getString(tag, ""));
                            break;

                        case 2:
                            deleteSearch(tag);
                            break;

                    }
                } // end DialogInterface.OnClickListener
            }); // end call to builder.setItems


            // set the AlertDialog's negative Button
            builder.setNegativeButton(getString(R.string.cancel),
                new DialogInterface.OnClickListener() {

                    // called when the "Cancel" Button is clicked
                    public void onClick(DialogInterface dialog, int id)  {

                        dialog.cancel(); // dismiss the AlertDialog
                    }
                }
            ); // end call to setNegativeButton

            builder.create().show(); // display alert dialog

            return true;
        } // end method onItemClick
    }; // end OnItemLongClickListener

//    private void shareSearch(String tag){
//
//        String urlString = getString(R.string.searchURL) + Uri.encode(savedSearches.getString(tag, ""), "UTF-8");
//
//        Intent shareIntent = new Intent();
//        shareIntent.setAction(Intent.ACTION_SEND);
//        shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.shareMessage, urlString));
//        shareIntent.setType("text/plain");
//
//        startActivity(Intent.createChooser(shareIntent, getString(R.string.shareSearch)));
//    }


    private void shareSearch(String tag)
    {
        // create the URL representing the search
        String urlString = getString(R.string.searchURL) +
                Uri.encode(savedSearches.getString(tag, ""), "UTF-8");

        // create Intent to share urlString
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.shareSubject));
        shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.shareMessage, urlString));
        shareIntent.setType("text/plain");

        // display apps that can share text
        startActivity(Intent.createChooser(shareIntent,
                getString(R.string.shareSearch)));
    }


    private void deleteSearch(final String tag)   {

        // create a new AlertDialog
        AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(this);

        // set the AlertDialog's message
        confirmBuilder.setMessage(getString(R.string.confirmMessage, tag));

        // set the AlertDialog's negative Button
        confirmBuilder.setNegativeButton(getString(R.string.cancel),
                new DialogInterface.OnClickListener()
        {
            // called when "Cancel" Button is clicked
            public void onClick(DialogInterface dialog, int id) {

                dialog.cancel(); // dismiss dialog
            }
        }); // end call to setNegativeButton

        // set the AlertDialog's positive Button
        confirmBuilder.setPositiveButton(getString(R.string.delete),
                new DialogInterface.OnClickListener()
        {

            // called when "Cancel" Button is clicked
            public void onClick(DialogInterface dialog, int id)

            {
                tags.remove(tag); // remove tag from tags

                // get SharedPreferences.Editor to remove saved search
                SharedPreferences.Editor preferencesEditor = savedSearches.edit();
                preferencesEditor.remove(tag); // remove search
                preferencesEditor.apply(); // saves the changes

                // rebind tags ArrayList to ListView to show updated list
                adapter.notifyDataSetChanged();
            }

        } // end OnClickListener

        ); // end call to setPositiveButton

        confirmBuilder.create().show(); // display AlertDialog

    } // end method deleteSearch




//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
