package me.shumon.ipassignment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import me.shumon.tabbedapp.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScreenOneFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScreenOneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScreenOneFragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static ImageView imageView;
    private static TextView textView;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScreenOneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScreenOneFragment newInstance(String param1, String param2) {
        ScreenOneFragment fragment = new ScreenOneFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    public ScreenOneFragment() {
        // Required empty public constructor
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_screen_one, container, false);

        Button red  = (Button) view.findViewById(R.id.redButton);
        Button blue  = (Button) view.findViewById(R.id.blueButton);
        imageView = (ImageView) view.findViewById(R.id.image_view_logo);
        textView = (TextView) view.findViewById(R.id.screenOneFooterText);

        Log.i("ScreenOne On create: ", "" + CommunicationDataHolder.getBlockColor());

        red.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (CommunicationDataHolder.getBlockCount() > 0){
                    imageView.setBackgroundColor(Color.RED);
                    textView.setText("Current color is RED");
                    CommunicationDataHolder.setBlockColor(Color.RED);
                }
                else {
                    Toast.makeText(getActivity(), "No available boxes to swap colors!", Toast.LENGTH_LONG).show();
                }


            }

        });

        blue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (CommunicationDataHolder.getBlockCount() > 0){
                    imageView.setBackgroundColor(Color.BLUE);
                    textView.setText("Current color is BLUE");
                    CommunicationDataHolder.setBlockColor(Color.BLUE);
                }
                else {
                    Toast.makeText(getActivity(), "No available boxes to swap colors!", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    public void changeColor(){

        if (CommunicationDataHolder.getBlockCount() > 0) {
            if (CommunicationDataHolder.getBlockColor() == -16776961) {
                textView.setText("Current color is BLUE");
                imageView.setBackgroundColor(Color.BLUE);
            }
            else if (CommunicationDataHolder.getBlockColor() == -65536) {
                textView.setText("Current color is RED");
                imageView.setBackgroundColor(Color.RED);
            }
            else if (CommunicationDataHolder.getBlockColor() == -16711936) {
                textView.setText("Current color is GREEN");
                imageView.setBackgroundColor(Color.GREEN);
            }

        }
        else {
            imageView.setBackgroundColor(0);
            textView.setText("No box is selected yet");
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
    public void onResume() {
        super.onResume();
        Log.i("ScreenOne On resume: ", "" + CommunicationDataHolder.getBlockColor());
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
