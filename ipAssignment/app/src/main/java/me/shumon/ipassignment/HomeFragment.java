package me.shumon.ipassignment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;

import me.shumon.tabbedapp.R;


//import me.shumon.tabbedapp.dummy.DummyContent;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class HomeFragment extends Fragment {


    private HomeFragmentAdapter adapter = null;
    private TextView mNoOfBlocks = null;
    private HomeFragmentItemDescription aBlockDescription;
    private Button add;
    private Button delete;

    private static int blockid = 0;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        final GridView gridView = (GridView)view.findViewById(R.id.gridViewItem);

        if(null == adapter)
            adapter = new HomeFragmentAdapter(inflater.getContext(), getFragmentManager());



        add = (Button) view.findViewById(R.id.add);
        delete = (Button) view.findViewById(R.id.delete);
        mNoOfBlocks = (TextView) view.findViewById(R.id.homeFooterText);

        mNoOfBlocks.setText("There are " + CommunicationDataHolder.getBlockCount() + " boxes");

        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                aBlockDescription = new HomeFragmentItemDescription();
                aBlockDescription.setBlockId("" + blockid);

                aBlockDescription.setBlockColor(Color.GREEN);
                CommunicationDataHolder.setBlockColor(Color.GREEN);

                adapter.add(aBlockDescription);

                blockid++;

                CommunicationDataHolder.setBlockCount(adapter.getCount());
                mNoOfBlocks.setText("There are " + CommunicationDataHolder.getBlockCount() + " boxes");
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (CommunicationDataHolder.getBlockCount() > 0) {

                    if (null != CommunicationDataHolder.getSelectedBlock()) {
                        adapter.remove(CommunicationDataHolder.getSelectedBlock());
                        CommunicationDataHolder.setSelectedBlock(null);
                    }
                    else {

                        adapter.remove((HomeFragmentItemDescription) adapter.getItem(adapter.getCount() - 1));

                        //update the data holder only if a block left in the grid after deleting one.
                        if(adapter.getCount() > 0) {
                            CommunicationDataHolder.setBlockColor(((HomeFragmentItemDescription) adapter.getItem(adapter.getCount() - 1)).getBlockColor());
                        }

                    }

                    CommunicationDataHolder.setBlockCount(adapter.getCount());
                    mNoOfBlocks.setText("There are " + CommunicationDataHolder.getBlockCount() + " boxes");
                }
                else {
                    Toast.makeText(getActivity(), "There's nothing to delete", Toast.LENGTH_LONG).show();
                }

            }
        });



        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

               CommunicationDataHolder.setBlockColor(((HomeFragmentItemDescription) adapter.getItem(position)).getBlockColor());
               CommunicationDataHolder.setSelectedBlock((HomeFragmentItemDescription) adapter.getItem(position));


                Toast.makeText(getActivity(), "Box selected, larger view created on screen 1. press delete button to delete.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }


    public void changeSelectedBlockColor(){
        if(adapter.getCount() > 0) {
            if (null != CommunicationDataHolder.getSelectedBlock()) {
                //changes the selected block color permanently
                CommunicationDataHolder.getSelectedBlock().setBlockColor(CommunicationDataHolder.getBlockColor());
                //updates the UI
                adapter.listItemChangedNotify();
            }
            else {
                //changes the last block color permanently
                ((HomeFragmentItemDescription) adapter.getItem(adapter.getCount() - 1)).setBlockColor(CommunicationDataHolder.getBlockColor());
                //updates the UI
                adapter.listItemChangedNotify();
            }
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

}
