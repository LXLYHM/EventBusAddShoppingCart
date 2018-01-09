package com.dawnling.www.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dawnling.www.R;
import com.dawnling.www.bean.ProductAnimEvent;
import com.dawnling.www.bean.ProductListBean;
import com.dawnling.www.data.AppCart;
import com.dawnling.www.utils.ImageLoader;
import com.dawnling.www.widget.MyEditText;

import org.greenrobot.eventbus.EventBus;

import static java.lang.Integer.parseInt;

/**
 * Created by LXL on 2017/12/8.
 * http://my.csdn.net/lxlyhm
 * https://github.com/LXLYHM
 * http://www.jianshu.com/u/8fd63a0d4c4c
 */
public class ProductAdapter extends BaseQuickAdapter<ProductListBean, BaseViewHolder> {

    public ProductAdapter(){
        super(R.layout.item_product);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ProductListBean item) {
        helper.setText(R.id.productsName, item.name);
        ImageLoader.getInstance().load(item.cover_image_url, (ImageView) helper.getView(R.id.imgProducts));

        final MyEditText etNumber = helper.getView(R.id.et_number);//数量
        etNumber.clearFocus();//清除焦点
        etNumber.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    etNumber.setCursorVisible(true);// 再次点击显示光标
                }
                return false;
            }
        });

        etNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!"".equals(s.toString())) {
                    int buy_num = parseInt(s.toString());
                    item.buy_num = buy_num;
                    if (buy_num > 0) {
                        AppCart.onChangeOne(item);//发送购物车变化
                    }
                    if (buy_num == 0){
                        AppCart.onRemove(item);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        etNumber.setText(item.buy_num + "");
        // 加
        helper.getView(R.id.bt_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.buy_num++;
                if (item.buy_num > 0){
                    EventBus.getDefault().post(new ProductAnimEvent(helper.getView(R.id.imgProducts), item.cover_image_url));
                }
                etNumber.setText(item.buy_num + "");
            }
        });
        // 减
        helper.getView(R.id.bt_float).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.buy_num == 0) {
                    return;
                }
                item.buy_num--;
                etNumber.setText(item.buy_num + "");
            }
        });
    }
}
