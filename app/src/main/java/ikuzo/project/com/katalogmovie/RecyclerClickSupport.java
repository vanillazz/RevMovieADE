package ikuzo.project.com.katalogmovie;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Vanillazz on 02/02/2018.
 */

public class RecyclerClickSupport {

    private final RecyclerView mRecyclerView;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;


    private RecyclerClickSupport(RecyclerView recyclerView){
        mRecyclerView = recyclerView;
        mRecyclerView.setTag(R.id.item_click_support, this);
        mRecyclerView.addOnChildAttachStateChangeListener(mAttachListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mOnItemClickListener != null){
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                mOnItemClickListener.onItemClicked(mRecyclerView, holder.getAdapterPosition(), v);
            }
        }
    };

    private View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if(mOnItemLongClickListener != null){
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                return mOnItemLongClickListener.onItemLongClicked(mRecyclerView, holder.getAdapterPosition(), v);
            }
            return false;
        }
    };



    RecyclerView.OnChildAttachStateChangeListener mAttachListener = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(View view) {
            if(mOnItemClickListener != null){
                view.setOnClickListener(mOnClickListener);
            }
            if(mOnItemLongClickListener != null){
                view.setOnLongClickListener(mOnLongClickListener);
            }
        }

        @Override
        public void onChildViewDetachedFromWindow(View view) {

        }
    };


    public static RecyclerClickSupport addTo(RecyclerView view){
        RecyclerClickSupport support = (RecyclerClickSupport) view.getTag(R.id.item_click_support);
        if(support == null){
            support = new RecyclerClickSupport(view);
        }
        return support;
    }

    public static RecyclerClickSupport removeFrom(RecyclerView view){
        RecyclerClickSupport support = (RecyclerClickSupport) view.getTag(R.id.item_click_support);
        if(support != null){
            support.detach(view);
        }
        return support;
    }

    private void detach(RecyclerView view) {
        view.removeOnChildAttachStateChangeListener(mAttachListener);
        view.setTag(R.id.item_click_support, null);
    }

    public RecyclerClickSupport setOnItemClickListener(OnItemClickListener listener){
        mOnItemClickListener = listener;
        return this;
    }

    public RecyclerClickSupport setOnItemLongCLickListener (OnItemLongClickListener listener){
        mOnItemLongClickListener = listener;
        return this;
    }


    public interface OnItemClickListener {
        void onItemClicked(RecyclerView recyclerView, int position, View v);
    }
    public interface OnItemLongClickListener {
        boolean onItemLongClicked(RecyclerView recyclerView, int position, View v);
    }


}
