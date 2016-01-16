package com.example.avjindersinghsekhon.minimaltodo;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

/*
@moss this class is a abstract class, which means it can not have any object
however, any class wants to have a customRecycleScrollview have to extend this class
 */
public abstract class CustomRecyclerScrollViewListener extends RecyclerView.OnScrollListener {
    /*
    @moss the distance that scrolled
     */
    int scrollDist = 0;
    boolean isVisible = true;

    /*
    @moss by default, minimum is 20
     */
    static final float MINIMUM = 20;

    /*
    @moss
    this method is to control the distance that user scrolls on the screen
     */
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
//        Log.d("OskarSchindler", "Scroll Distance "+scrollDist);

        if(isVisible && scrollDist>MINIMUM){
            Log.d("OskarSchindler", "Hide "+scrollDist);
            hide();
            scrollDist = 0;
            isVisible = false;
        }
        else if(!isVisible && scrollDist < -MINIMUM){
            Log.d("OskarSchindler", "Show "+scrollDist);
            show();
            scrollDist = 0;
            isVisible =true;
        }
        if((isVisible && dy>0) || (!isVisible && dy<0)){
            Log.d("OskarSchindler", "Add Up "+scrollDist);
            scrollDist += dy;
        }
    }
    public abstract void show();
    public abstract void hide();
}
