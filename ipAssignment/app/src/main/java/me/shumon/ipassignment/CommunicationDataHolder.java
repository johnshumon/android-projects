package me.shumon.ipassignment;

/**
 * Created by kutimuti on 2/17/15.
 */
public final class CommunicationDataHolder {
    private static int mBlockColor = 0;
    private static int mBlockCount = 0;
    private static HomeFragmentItemDescription mSelectedBlock = null;

    public static void setBlockColor(int color){
        mBlockColor = color;
    }

    public static int getBlockColor(){
        return mBlockColor;
    }

    public static void setBlockCount (int count){
        mBlockCount = count;
    }

    public static int getBlockCount(){
        return  mBlockCount;
    }

    public static void setSelectedBlock(HomeFragmentItemDescription aBlock){
        mSelectedBlock = aBlock;
    }

    public static HomeFragmentItemDescription getSelectedBlock(){
        return mSelectedBlock;
    }
}
