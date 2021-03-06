package  com.yuntongxun.kitsdk.adapter;
import android.content.Context;
import android.database.Cursor;
import android.widget.BaseAdapter;


import junit.framework.Assert;

import java.util.HashMap;
import java.util.Map;

import com.yuntongxun.kitsdk.db.OnMessageChange;

/**
 * 定义抽象适配器，继承扩展不同的消息
 * Created by Jorstin on 2015/3/18.
 */
public abstract class CCPListAdapter<T> extends BaseAdapter implements OnMessageChange {

    /**数据Cursor*/
    private Cursor mCursor;
    /**数据缓存*/
    private Map<Integer, T> mData ;
    /**适配器使用数据类型*/
    protected T t;
    /**上下文对象*/
    protected Context mContext;
    /**数据总数*/
    protected int mCount;
    /**数据改变回调接口*/
    protected OnCursorChangeListener mOnCursorChangeListener;

    /**
     * 构造方法
     * @param ctx
     * @param t
     */
    public CCPListAdapter(Context ctx , T t) {
        mContext = ctx;
        this.t = t;
        this.mCount = -1;
    }

    protected void setCursor(Cursor cursor) {
        mCursor = cursor;
        this.mCount = -1;
    }

    public void initCache() {
        if(mData != null) {
            return ;
        }
        mData = new HashMap<Integer, T>();
    }

    /**
     * 返回一个数据类型Cursor
     * @return
     */
    protected Cursor getCursor() {
        if(mCursor == null) {
            initCursor();
            Assert.assertNotNull(mCursor);
        }
        return mCursor;
    }

    public void setOnCursorChangeListener(OnCursorChangeListener listener) {
        this.mOnCursorChangeListener = listener;
    }

    public void resetListener() {
        this.mOnCursorChangeListener = null;
    }

    /***
     * 关闭数据库
     */
    public void closeCursor() {
        if(mData != null) {
            mData.clear();
        }
        if(mCursor != null) {
            mCursor.close();
        }
        mCount = -1;
    }

    @Override
    public int getCount() {
        if(mCount < 0) {
            mCount = getCursor().getCount();
        }
        return mCount;
    }

    @Override
    public T getItem(int position) {
        if(position < 0 || !getCursor().moveToPosition(position)) {
            return null;
        }

        if(mData == null) {
            return getItem(this.t, getCursor());
        }

        T _t = mData.get(Integer.valueOf(position));
        if(_t == null) {
            _t = getItem(null, getCursor());
        }
        mData.put(Integer.valueOf(position), _t);
        return _t;
    }

    @Override
    public long getItemId(int position) {

        return 0;

    }

    @Override
    public void onChanged(String sessionId) {
        if(mOnCursorChangeListener != null) {
            mOnCursorChangeListener.onCursorChangeBefore();
        }
        closeCursor();
        notifyChange();

        if(mOnCursorChangeListener == null) {
            return ;
        }
        mOnCursorChangeListener.onCursorChangeAfter();
    }

    public abstract void notifyChange();
    public abstract void initCursor();

    public abstract T getItem(T t , Cursor cursor);



    public interface OnListAdapterCallBackListener {
        void OnListAdapterCallBack();
    }


    public interface OnCursorChangeListener {
        void onCursorChangeBefore();
        void onCursorChangeAfter();
    }
}

