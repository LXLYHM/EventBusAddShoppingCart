package com.dawnling.www.bean;

import java.io.Serializable;

public class ProductListBean implements Serializable{
        /**
         * cover_image_url : http://images.songaidaojia.com/product/4598e8ea4ddc88ee60a1db743aa1c379ef797e4c18142aa4.jpg!thumbnail-120
         * description : 当季水果 巨峰葡萄
         * is_price_state : 0
         * market_id : 1
         * market_product_id : 6
         * name : 巨峰葡萄
         * online : 0
         * original_price : 9.80
         * price : 5.00
         * product_id : 1
         * product_second_type_id : 43
         * product_type_name : 新鲜水果
         * sales_price : 5.00
         * sales_volume : 252
         * spec :  约350g-400g
         * stock : 0
         * unique_code : 05010001
         * unit_name : 份
         * weight : 0
         */

        public String cover_image_url;
        public String is_price_state;
        public int market_id;
        public int market_product_id;
        public String name;
        public String original_price;
        public String product_id;
        public String product_second_type_id;
        public String product_type_name;
        public String unique_code;
        public String unit_name;

        /**
         * 拓展字段
         */
        //在购物车、订单中的数量
        public int buy_num;

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                ProductListBean that = (ProductListBean) o;

                if (market_id != that.market_id) return false;
                if (market_product_id != that.market_product_id) return false;
                if (buy_num != that.buy_num) return false;
                if (cover_image_url != null ? !cover_image_url.equals(that.cover_image_url) : that.cover_image_url != null)
                        return false;
                if (is_price_state != null ? !is_price_state.equals(that.is_price_state) : that.is_price_state != null)
                        return false;
                if (name != null ? !name.equals(that.name) : that.name != null) return false;
                if (original_price != null ? !original_price.equals(that.original_price) : that.original_price != null)
                        return false;
                if (product_id != null ? !product_id.equals(that.product_id) : that.product_id != null)
                        return false;
                if (product_second_type_id != null ? !product_second_type_id.equals(that.product_second_type_id) : that.product_second_type_id != null)
                        return false;
                if (product_type_name != null ? !product_type_name.equals(that.product_type_name) : that.product_type_name != null)
                        return false;
                return unit_name != null ? unit_name.equals(that.unit_name) : that.unit_name == null;

        }

        @Override
        public int hashCode() {
                int result = cover_image_url != null ? cover_image_url.hashCode() : 0;
                result = 31 * result + (is_price_state != null ? is_price_state.hashCode() : 0);
                result = 31 * result + market_id;
                result = 31 * result + market_product_id;
                result = 31 * result + (name != null ? name.hashCode() : 0);
                result = 31 * result + (original_price != null ? original_price.hashCode() : 0);
                result = 31 * result + (product_id != null ? product_id.hashCode() : 0);
                result = 31 * result + (product_second_type_id != null ? product_second_type_id.hashCode() : 0);
                result = 31 * result + (product_type_name != null ? product_type_name.hashCode() : 0);
                result = 31 * result + (unique_code != null ? unique_code.hashCode() : 0);
                result = 31 * result + (unit_name != null ? unit_name.hashCode() : 0);
                result = 31 * result + buy_num;
                return result;
        }
}