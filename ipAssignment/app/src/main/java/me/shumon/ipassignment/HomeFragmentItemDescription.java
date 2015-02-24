package me.shumon.ipassignment;

/**
 * Created by kutimuti on 2/17/15.
 */
public class HomeFragmentItemDescription {

    private String mBlockId = "";
    private int mBlockColor;

    public HomeFragmentItemDescription() {

    }

    public void setBlockId(String id){
        mBlockId = id;
    }

    public void setBlockColor(int color){
        mBlockColor = color;
    }

    public String getBlockId(){
        return mBlockId;
    }

    public int getBlockColor(){
        return mBlockColor;
    }
}
