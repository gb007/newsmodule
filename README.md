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
	implementation 'com.github.gb007:newsmodule:1.0.0'
	}

````


## 3.初始化配置信息

### 3.1 Manifest中添加网络请求申请权限，如果请求伟http则需要application中usesCleartextTraffic属性设为true

````   

<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.hollysmart.moduleintegrate">

    <uses-permission android:name="android.permission.INTERNET" />

    <application
        android:name=".IntegrateApplication"
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:usesCleartextTraffic="true"
        android:theme="@style/AppTheme.NoActionBar">

        <activity
            android:name="com.hollysmart.newsmodule.activity.NewsDetailsActivity"
            android:screenOrientation="portrait" />

    </application>

</manifest>

```` 



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
        //新闻模块配置类
        var newsConfig = NewsConfig()
        //服务器地址
        newsConfig.basE_URL = "http://tschangyuan-api.hollysmart.com:60001"
        //文件服务器地址（获取的图片）
        newsConfig.filE_URL = "http://tschangyuan.hollysmart.com/"
        //新闻栏目（id，名称）
        newsConfig.lanmuBeans = columnList
        //新闻栏目tab字体大小
        newsConfig.tabTitleTextSize = 18
        //新闻栏目tab字体颜色
        newsConfig.titleNormalColor = R.color.tab_normal
        //新闻栏目选中tab字体颜色
        newsConfig.titleSelectedColor = R.color.tab_selected
        //新闻栏目tab下划线长度
        newsConfig.indicatorWidth = 26
        //新闻栏目tab下划线高度
        newsConfig.indicatorHeight = 3
        //新闻栏目tab下划线圆角
        newsConfig.indicatorRoundRadius = 1
        //新闻栏目tab下划线颜色
        newsConfig.indicatorColor = R.color.blue
        //用户是否登陆
        newsConfig.isLogin = false
        //用户Id
        newsConfig.userdId = "11111"
        //设备唯一标识
        newsConfig.uuid = "1234567890"
        //javascriptObject,新闻详情页面js方法
//        newsConfig.javascriptObject = null

        dongtaiFragment.config = newsConfig
        
        return dongtaiFragment
    }

````