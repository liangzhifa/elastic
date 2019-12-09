package com.zhifa.elastic.tempModel;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Document(indexName = "huawei", type = "phone", shards = 1, replicas = 0, createIndex = false)
public class HuaWeiPhone {

    /**
     * productId : 37428
     * adminBuyButtonMode : {"mobileThirdPartySiteLink":"https://m.vmall.com/product/10086775311605.html?cid=103433","partnerCountryFilterEffective":"false","buttonType":"third-party-site","ecommerceSiteLink":"?productId=null","thirdPartySiteLink":"https://www.vmall.com/product/10086775311605.html?cid=103433","openInNewPage":"true","chooseAPartners":[]}
     * priceDisplayType : 1
     * productName : HUAWEI Mate 30 Pro 5G
     * marketingName : HUAWEI Mate 30 Pro 5G
     * colorsItemMode : [{"colorName":"丹霞橙","img":"/content/dam/huawei-cbg-site/greate-china/cn/mkt/pdp/phones/huawei-mate30-pro-5g/images/mate30-pro-space-orange.png","colorValue":"#e7703d"},{"colorName":"青山黛","img":"/content/dam/huawei-cbg-site/greate-china/cn/mkt/pdp/phones/huawei-mate30-pro-5g/images/mate30-pro-space-forest-green.png","colorValue":"#005662"},{"colorName":"罗兰紫","img":"/content/dam/huawei-cbg-site/greate-china/cn/mkt/pdp/phones/huawei-mate30-pro-5g/images/mate30-pro-cosmic-purple.png","colorValue":"#603765"},{"colorName":"星河银","img":"/content/dam/huawei-cbg-site/greate-china/cn/mkt/pdp/phones/huawei-mate30-pro-5g/images/mate30-pro-space-silver.png","colorValue":"#acb3d4"},{"colorName":"翡冷翠","img":"/content/dam/huawei-cbg-site/greate-china/cn/mkt/pdp/phones/huawei-mate30-pro-5g/images/mate30-pro-emerald-green.png","colorValue":"#00a5a2"},{"colorName":"亮黑色","img":"/content/dam/huawei-cbg-site/greate-china/cn/mkt/pdp/phones/huawei-mate30-pro-5g/images/mate30-pro-space-black.png","colorValue":"#000000"}]
     * canBuy : no
     * productTitle : mate30-pro-5g
     * arrFilter : ["series%@%mate-series"]
     * enableECommerceSetting : false
     * sellingPoints : ["麒麟990 5G SoC 芯片，支持 SA 及 NSA 网络","超感光徕卡电影四摄，256 倍超高速摄影","6.53 英寸环幕屏，27W 华为无线超级快充"]
     * detailLink : http://consumer.huawei.com/cn/phones/mate30-pro-5g.html
     * whetherNewType :
     */
    @Id
    private String productId;

   // @Field(type = FieldType.Object)
   @Transient
    private AdminBuyButtonModeEntity adminBuyButtonMode;

    @Field(type = FieldType.Keyword)
    private String priceDisplayType;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String productName;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String marketingName;

    //@Field(type = FieldType.Nested)
    @Transient
    private List<ColorsItemModeEntity> colorsItemMode;

    @Field(type = FieldType.Keyword)
    private String canBuy;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String productTitle;

    //@Field(type = FieldType.Nested)
    @Transient
    private List<String> arrFilter;

    @Field(type = FieldType.Boolean)
    private boolean enableECommerceSetting;

    //@Field(type = FieldType.Nested)
    @Transient
    private List<String> sellingPoints;

    @Field(type = FieldType.Keyword)
    private String detailLink;

    @Transient
    private String whetherNewType;

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setAdminBuyButtonMode(AdminBuyButtonModeEntity adminBuyButtonMode) {
        this.adminBuyButtonMode = adminBuyButtonMode;
    }

    public void setPriceDisplayType(String priceDisplayType) {
        this.priceDisplayType = priceDisplayType;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setMarketingName(String marketingName) {
        this.marketingName = marketingName;
    }

    public void setColorsItemMode(List<ColorsItemModeEntity> colorsItemMode) {
        this.colorsItemMode = colorsItemMode;
    }

    public void setCanBuy(String canBuy) {
        this.canBuy = canBuy;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public void setArrFilter(List<String> arrFilter) {
        this.arrFilter = arrFilter;
    }

    public void setEnableECommerceSetting(boolean enableECommerceSetting) {
        this.enableECommerceSetting = enableECommerceSetting;
    }

    public void setSellingPoints(List<String> sellingPoints) {
        this.sellingPoints = sellingPoints;
    }

    public void setDetailLink(String detailLink) {
        this.detailLink = detailLink;
    }

    public void setWhetherNewType(String whetherNewType) {
        this.whetherNewType = whetherNewType;
    }

    public String getProductId() {
        return productId;
    }

    public AdminBuyButtonModeEntity getAdminBuyButtonMode() {
        return adminBuyButtonMode;
    }

    public String getPriceDisplayType() {
        return priceDisplayType;
    }

    public String getProductName() {
        return productName;
    }

    public String getMarketingName() {
        return marketingName;
    }

    public List<ColorsItemModeEntity> getColorsItemMode() {
        return colorsItemMode;
    }

    public String getCanBuy() {
        return canBuy;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public List<String> getArrFilter() {
        return arrFilter;
    }

    public boolean isEnableECommerceSetting() {
        return enableECommerceSetting;
    }

    public List<String> getSellingPoints() {
        return sellingPoints;
    }

    public String getDetailLink() {
        return detailLink;
    }

    public String getWhetherNewType() {
        return whetherNewType;
    }

    public class AdminBuyButtonModeEntity {
        /**
         * mobileThirdPartySiteLink : https://m.vmall.com/product/10086775311605.html?cid=103433
         * partnerCountryFilterEffective : false
         * buttonType : third-party-site
         * ecommerceSiteLink : ?productId=null
         * thirdPartySiteLink : https://www.vmall.com/product/10086775311605.html?cid=103433
         * openInNewPage : true
         * chooseAPartners : []
         */
        private String mobileThirdPartySiteLink;
        private String partnerCountryFilterEffective;
        private String buttonType;
        private String ecommerceSiteLink;
        private String thirdPartySiteLink;
        private String openInNewPage;
        private List<?> chooseAPartners;

        public void setMobileThirdPartySiteLink(String mobileThirdPartySiteLink) {
            this.mobileThirdPartySiteLink = mobileThirdPartySiteLink;
        }

        public void setPartnerCountryFilterEffective(String partnerCountryFilterEffective) {
            this.partnerCountryFilterEffective = partnerCountryFilterEffective;
        }

        public void setButtonType(String buttonType) {
            this.buttonType = buttonType;
        }

        public void setEcommerceSiteLink(String ecommerceSiteLink) {
            this.ecommerceSiteLink = ecommerceSiteLink;
        }

        public void setThirdPartySiteLink(String thirdPartySiteLink) {
            this.thirdPartySiteLink = thirdPartySiteLink;
        }

        public void setOpenInNewPage(String openInNewPage) {
            this.openInNewPage = openInNewPage;
        }

        public void setChooseAPartners(List<?> chooseAPartners) {
            this.chooseAPartners = chooseAPartners;
        }

        public String getMobileThirdPartySiteLink() {
            return mobileThirdPartySiteLink;
        }

        public String getPartnerCountryFilterEffective() {
            return partnerCountryFilterEffective;
        }

        public String getButtonType() {
            return buttonType;
        }

        public String getEcommerceSiteLink() {
            return ecommerceSiteLink;
        }

        public String getThirdPartySiteLink() {
            return thirdPartySiteLink;
        }

        public String getOpenInNewPage() {
            return openInNewPage;
        }

        public List<?> getChooseAPartners() {
            return chooseAPartners;
        }
    }

    public class ColorsItemModeEntity {
        /**
         * colorName : 丹霞橙
         * img : /content/dam/huawei-cbg-site/greate-china/cn/mkt/pdp/phones/huawei-mate30-pro-5g/images/mate30-pro-space-orange.png
         * colorValue : #e7703d
         */
        private String colorName;
        private String img;
        private String colorValue;

        public void setColorName(String colorName) {
            this.colorName = colorName;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public void setColorValue(String colorValue) {
            this.colorValue = colorValue;
        }

        public String getColorName() {
            return colorName;
        }

        public String getImg() {
            return img;
        }

        public String getColorValue() {
            return colorValue;
        }
    }
}
