package com.hollysmart.newsapplication

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.hollysmart.navigationmodule.constant.Anim
import com.hollysmart.navigationmodule.utils.NavigationUtil
import com.hollysmart.navigationmodule.view.EasyNavigationBar
import com.hollysmart.newsmodule.bean.LanmuBean
import com.hollysmart.newsmodule.fragment.DongtaiFragment
import com.hollysmart.newsmodule.value.NewsConfig
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var navigationBar: EasyNavigationBar

    private lateinit var menuLayout: LinearLayout
    private lateinit var cancelImageView: View
    private val mHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//        Thread {
//            //耗时任务，比如加载网络数据
//            runOnUiThread { // 这里可以睡几秒钟，如果要放广告的话
//                 sleep(3000);
//
//            }
//        }.start()


        setContentView(R.layout.activity_main)
        navigationBar = findViewById(R.id.navigationBar)
        initNavigation()

    }

    private fun initNavigation() {

        //底部导航名称
        val tabText = arrayOf("首页", "发现", "消息", "我的")

        //未选中icon
        val normalIcon =
            intArrayOf(R.mipmap.index, R.mipmap.find, R.mipmap.message, R.mipmap.me)

        //选中时icon
        val selectIcon =
            intArrayOf(R.mipmap.index1, R.mipmap.find1, R.mipmap.message1, R.mipmap.me1)

        //底部fragment
        var fragments = mutableListOf<Fragment>()
        fragments.add(AFragment())
        fragments.add(initNewsMode())
        navigationBar.titleItems(tabText)
            .normalIconItems(normalIcon)
            .selectIconItems(selectIcon)
            .centerImageRes(R.mipmap.add_image)
            .fragmentList(fragments)
            .fragmentManager(supportFragmentManager)
            .centerLayoutRule(EasyNavigationBar.RULE_CENTER)
            .setOnTabClickListener(object : EasyNavigationBar.OnTabClickListener {
                override fun onTabSelectEvent(view: View?, position: Int): Boolean {
                    if (position == 3) {
                        Toast.makeText(this@MainActivity, "请先登录", Toast.LENGTH_SHORT).show()
                        //return true则拦截事件、不进行页面切换
                        return true
                    }
                    return false
                }

                override fun onTabReSelectEvent(view: View?, position: Int): Boolean {
                    return false
                }
            })
            .setOnCenterTabClickListener { //弹出菜单
//                showMunu()
                false
            }
            .mode(EasyNavigationBar.NavigationMode.MODE_ADD)
            .anim(Anim.ZoomIn)
            .build()

//        navigationBar.setAddViewLayout(createAddView())


    }


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


    //仿微博弹出菜单
//    private fun createAddView(): View? {
//
//        //中间加号，仿微博图片和文字集合
//        val menuIconItems =
//            intArrayOf(R.mipmap.pic1, R.mipmap.pic2, R.mipmap.pic3, R.mipmap.pic4)
//        val menuTextItems = arrayOf("文字", "拍摄", "相册", "直播")
//
//        val view = View.inflate(this@MainActivity, R.layout.layout_add_view, null) as ViewGroup
//        menuLayout = view.findViewById<LinearLayout>(R.id.icon_group)
//        cancelImageView = view.findViewById<View>(R.id.cancel_iv)
//        cancelImageView.setOnClickListener(View.OnClickListener { closeAnimation() })
//        for (i in 0..3) {
//            val itemView = View.inflate(this@MainActivity, R.layout.item_icon, null)
//            val menuImage = itemView.findViewById<ImageView>(R.id.menu_icon_iv)
//            val menuText = itemView.findViewById<TextView>(R.id.menu_text_tv)
//            menuImage.setImageResource(menuIconItems[i])
//            menuText.text = menuTextItems[i]
//            val params = LinearLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT
//            )
//            params.weight = 1f
//            itemView.layoutParams = params
//            itemView.visibility = View.GONE
//            menuLayout.addView(itemView)
//        }
//        return view
//    }


//    private fun showMunu() {
//        startAnimation()
//        mHandler.post { //＋ 旋转动画
//            cancelImageView.animate().rotation(90f).duration = 400
//        }
//        //菜单项弹出动画
//        for (i in 0 until menuLayout.childCount) {
//            val child = menuLayout.getChildAt(i)
//            child.visibility = View.INVISIBLE
//            mHandler.postDelayed({
//                child.visibility = View.VISIBLE
//                val fadeAnim: ValueAnimator =
//                    ObjectAnimator.ofFloat(child, "translationY", 600f, 0f)
//                fadeAnim.duration = 500
//                val kickAnimator = KickBackAnimator()
//                kickAnimator.setDuration(500F)
//                fadeAnim.setEvaluator(kickAnimator)
//                fadeAnim.start()
//            }, (i * 50 + 100).toLong())
//        }
//    }


    private fun startAnimation() {
        mHandler.post {
            try {
                //圆形扩展的动画
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val x: Int = NavigationUtil.getScreenWidth(this@MainActivity) / 2
                    val y =
                        (NavigationUtil.getScreenHeith(this@MainActivity) - NavigationUtil.dip2px(
                            this@MainActivity,
                            25F
                        )) as Int
                    val animator = ViewAnimationUtils.createCircularReveal(
                        navigationBar.addViewLayout, x,
                        y, 0f,
                        navigationBar.addViewLayout.height.toFloat()
                    )
                    animator.addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationStart(animation: Animator) {
                            navigationBar.addViewLayout.visibility = View.VISIBLE
                        }

                        override fun onAnimationEnd(animation: Animator) {
                            //							layout.setVisibility(View.VISIBLE);
                        }
                    })
                    animator.duration = 300
                    animator.start()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 关闭window动画
     */
    private fun closeAnimation() {
        mHandler.post { cancelImageView.animate().rotation(0f).duration = 400 }
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val x: Int = NavigationUtil.getScreenWidth(this) / 2
                val y: Int = NavigationUtil.getScreenHeith(this) - NavigationUtil.dip2px(this, 25F)
                val animator = ViewAnimationUtils.createCircularReveal(
                    navigationBar.addViewLayout, x,
                    y,
                    navigationBar.addViewLayout.height.toFloat(), 0f
                )
                animator.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator) {
                        //							layout.setVisibility(View.GONE);
                    }

                    override fun onAnimationEnd(animation: Animator) {
                        navigationBar.addViewLayout.visibility = View.GONE
                        //dismiss();
                    }
                })
                animator.duration = 300
                animator.start()
            }
        } catch (e: Exception) {
        }
    }


    fun getNavigationBar(): EasyNavigationBar? {
        return navigationBar
    }

}