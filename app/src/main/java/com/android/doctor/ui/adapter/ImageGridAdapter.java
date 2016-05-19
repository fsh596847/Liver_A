package com.android.doctor.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.helper.FileUtils;
import com.android.doctor.helper.ImageHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yong on 2016/5/10.
 */
public class ImageGridAdapter extends BaseAdapter implements View.OnClickListener{
    private LayoutInflater inflater; // 视图容器
    private int selectedPosition = -1;// 选中的位置
    private boolean shape;
    private List<String> data = new ArrayList<>();
    private Context context;

    public boolean isShape() {
        return shape;
    }

    public void setShape(boolean shape) {
        this.shape = shape;
    }

    public ImageGridAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }


    public int getCount() {
        return data == null ? 1 : data.size() + 1;
    }

    public Object getItem(int pos) {
        if (pos < 0 || data == null || data.size() <= pos) {
            return null;
        }
        return  data.get(pos);
    }

    public void setData(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public List<String> getData() {
        return data;
    }

    public void addItem(String path) {
        this.data.add(path);
        notifyDataSetChanged();
    }

    public void removeItem(int pos) {
        this.data.remove(pos);
        notifyDataSetChanged();
    }

    public long getItemId(int arg0) {
        return 0;
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    /**
     * ListView Item设置
     */
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_tpub_image, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.item_image);
            holder.del = (TextView) convertView.findViewById(R.id.tv_delete);

            holder.del.setOnClickListener(this);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.del.setTag(position);
        if (position == data.size()) {
            holder.image.setImageBitmap(BitmapFactory.decodeResource(
                    context.getResources(), R.drawable.icon_addpic_unfocused));
            holder.del.setVisibility(View.GONE);
            if (position == 9) {
                holder.image.setVisibility(View.GONE);
            }
        } else {
            Bitmap bitmap = ImageHelper.getBitmapByPath(data.get(position));
            holder.image.setImageBitmap(bitmap);
            holder.del.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    public class ViewHolder {
        public ImageView image;
        public TextView del;
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    notifyDataSetChanged();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_delete) {
            removeItem((Integer) v.getTag());
        }
    }
}
