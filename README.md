# newsmodule模块使用


## 1.在工程的根目录build.gradle中添加jitpack库依赖

````

allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}
	
````


## 2.在需要引用此类库模块的build.gradle中引入依赖

 ````
dependencies {
	implementation 'com.github.gb007:startmodule:1.0.0'
	}

````


## 3.初始化配置信息

### 3.1 Manifest中添加新闻详情页面Activity, 

````
        <activity
            android:name="com.hollysmart.newsmodule.activity.NewsDetailsActivity"
            android:screenOrientation="portrait" />

````


### 3.2 初始化newsmodule模块的新闻Fragment的配置信息

````
    private fun initNewsMode(): Fragment {

        val columnList = mutableListOf<LanmuBean>(LanmuBean("34", "推荐"),
              LanmuBean("35", "体育资讯"),LanmuBean("100", "赛事活动"),
            LanmuBean("143", "协会"),LanmuBean("36", "健康知识")
            )
        
        var dongtaiFragment = DongtaiFragment()
        var newsConfig = NewsConfig()
        newsConfig.basE_URL = "http://tschangyuan-api.hollysmart.com:60001"
        newsConfig.filE_URL = "http://tschangyuan.hollysmart.com/"
        newsConfig.lanmuBeans = columnList
        newsConfig.tabTitleTextSize = 18
        newsConfig.titleNormalColor = R.color.tab_normal
        newsConfig.titleSelectedColor = R.color.tab_selected
        newsConfig.indicatorWidth = 26
        newsConfig.indicatorHeight = 3
        newsConfig.indicatorRoundRadius = 1
        newsConfig.indicatorColor = R.color.blue
        newsConfig.isLogin = false
        newsConfig.userdId = "11111"
        newsConfig.uuid = "1234567890"

        dongtaiFragment.config = newsConfig
        return dongtaiFragment
    }

````