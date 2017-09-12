package com.liang.tind.mycv.model;

import java.util.List;

/**
 * Created by Sherlock on 2017/9/5.
 */

public class CvInfoBean extends BaseBean {


    /**
     * CvDetailInfo : {"BasicInfo":{"name":"梁天德","phone":"15659827282","jobIntention":"Android","email":"tind.liang@foxmail.com"},"EducationInfo":[{"schoolName":"集美大学","timeRange":"2011.09~2015.07","degree":"本科","eduDesc":"期间取得大学英语四级证书，国家计算机等级二级证书（C语言），获优秀共青团员、优秀学生干部、二等奖学金。\n出于对编程的兴趣，修了C语言课程，考取了国家计算机二级，后对安卓较为感兴趣，自学了JavaSE和Android开发知识。"}],"ProfessionalSkills":[{"skillDesc":"1.比较熟悉Android和Java API,理解面向对象编程思想，对Html5有一定认识。"},{"skillDesc":"2.掌握VP、MVVM架构，策略、工厂、观察者等常用的设计模式。"},{"skillDesc":"3.掌握retrofit，rxjava，databinding，Glide，design，LitePal等流行框架"},{"skillDesc":"4.熟悉安卓界面布局、UI控件、动画绘制、具备自定义控件能力"},{"skillDesc":"5.掌握Socket和HTTP协议、JSON解析，掌握网络编程。"},{"skillDesc":"6.分析解决问题、定位Bug、ANR分析能力较好，有极尽性能优化意识。"}],"JobExperience":[{"timeRange":"2016/8~至今","campanyName":"上海锐至信息技术有限公司","jobName":"安卓开发工程师","jobDesc":"驻中国移动项目部开发OA系统应用\u201c移动OA\u201d和\u201c4A门户\u201d，更新维护项目代码，新功能模块代码。"},{"timeRange":"2015/8~2016/7 ","campanyName":"上海九逸科技有限公司","jobName":"安卓开发程序员","jobDesc":"负责更新维护公司\u201c业绩通\u201d产品，修改测试组返回的BUG，新增功能代码编写。"}],"Homepages":"Githup : https://github.com/DarkSherlock \nCSDN：http://blog.csdn.net/tinderliang \n简书：http://www.jianshu.com/u/5be39d429e58","ProjectInstruction":[{"projectName":"一、我的简历","copyRight":"个人","downloadUrl":"https://www.pgyer.com/rcWA","devTool":"Studio，SVN，SDK版本26。","projectDesc":"个人为了展示个人简历信息并集成使用目前流行框架的APP","projectTechnology":"1.项目采用MVP+Dagger2模式。\n2.网络请求使用Retrofit2.0+Rxjava2.0+RxLifeCycle2.0+OKhttp3.0+GSON；\n3.UI使用Material design 风格,Toolbar+DrawerLayout+沉浸式状态栏;\n4.使用LeakCanary 检测内存泄漏，性能优化。\n5.腾讯Bugly SDK 实现Crash，ANR信息收集，全量更新，热更新。\n6.开源日志打印库Logger。\n7.GlideImageView实现加载圆形图片，监听进度，图片预览和高清。\n8.动态权限申请使用Permissionsdispatcher+studio插件。\n9.使用 webview封装库agentweb来加载个人主页信息，防止内存泄漏和一些遗留bug。\n10.使用阿里巴巴开源的Vlayout实现多类型复杂recyclerview布局。\n11.使用Lambda表达式使代码更简洁。\n12.简历附件下载；第三方项目中直接打开QQ消息界面。"},{"projectName":"二、移动OA","copyRight":"上海锐至信息技术有限公司","downloadUrl":"http://211.136.166.47:9080/OAAdmin/CMOA.html","devTool":"Eclipse转Studio，SVN，SDK版本21。","projectDesc":"面向上海中国移动员工的企业办公软件，有处理公文，培训课程，新闻资讯，请销假，邮件，附件下载，通讯录等功能。","projectTechnology":"1.短信验证登录，集成听云SDK检测性能,集成百度地图SDK实现定位。\n2.自定义View,九宫格密码锁屏 。\n3.自定义UncaughtExceptionHandler捕获全局异常，保存错误信息到SD卡下次启动上传到服务器。\n4.自定义textview实现右上角红色待办数量提醒。\n5.downloadmanager实现office附件下载，打开。\n6.使用pulltorefreshlistview开源项目实现列表下拉刷新。\n7.expandlistview实现多级列表显示公文详情。\n8.nineandroid实现伸缩展示的值动画。\n9.GPS定位获取经纬度进行课程签到。\n10.DragSortListview实现拖拉滑动控件。"},{"projectName":"三、业绩通","copyRight":"上海九逸科技有限公司","downloadUrl":"https://www.pgyer.com/rcWA","devTool":"Eclipse转Studio，SVN，SDK版本19。","projectDesc":"上海九逸科技有限公司开发的面向企业的门店管理软件，Emoji表情，消息推送，地图定位，考勤管理，业绩PK，在线培训，工作日志，订单发货，在线收银等功能。","projectTechnology":"1.闪屏页用自定义ShimmerTextView实现字体闪亮，验证登录。\n2.引导页用AnimatorSetb执行ObjectAnimator渐隐，缩放动画。\n3.自定义EditTextView实现带有删除编辑文字功能的搜索框。\n4.用开源项目pulltoRefreshListView实现下拉刷新，上拉加载更多。\n5.用开源项目swipemenulistview实现类似QQ的可侧滑删除的Listview。\n6.使用pulltorefreshlistview开源项目实现列表下拉刷新。\n7.用FlexibleRatingBar星星评级控件实现巡店打分功能。\n8.用开源项目GestureImageView实现预览图片时可以手势缩放图片。\n9.第三方控件Sidebar实现通讯录（A~Z）排序。\n10.用Notification和AlarmManager实现闹钟提醒功能。\n11.自定义WheelView实现圆形轮盘设置时间日期功能。\n12.webview实现查看公司主页，VideoView播放视频，MediaPlayer播放音频功能。\n13.hellocharts开源项目实现业绩图表查询功能，zxing项目实现扫描二维码功能。\n14.集成极光推送SDK实现消息推送，集成高德地图SDK实现店铺地图定位，显示天气信息功能。"},{"projectName":"四、夏洛克天气","copyRight":"个人","downloadUrl":"","devTool":"Studio，GIT，SDK版本25。","projectDesc":"为了熟悉框架自己开发的天气预报练手项目。","projectTechnology":"1.项目采用MVVM模式，使用简书作者Kelin封装DataBinding的MVVM Light Toolkit库；\n2.网络请求使用Retrofit2.0+Rxjava2.0+OKhttp3.0+GSON；\n3.数据库采用OrmLite框架；\n4.UI使用Material design 风格,ToolBar+DrawerLayout+沉浸式状态栏;\n5.使用Lambda表达式使代码更简洁;\n6.使用高德地图用于定位;\n7.CityPicker实现热门城市与全部城市列表的索引与搜索。\n8.腾讯Bugly SDK 实现Crash收集，ANR信息收集，全量更新，热更新。"}],"selfEvaluation":"1.较好的学习能力和孜孜不倦的学习热情，追求极客精神。\n2.有良好的沟通能力、理解能力及逻辑思维，良好的英语读写能力，能快速学习相关行业知识。\n3.工作以来积累的经验能使我较为迅速地定位bug,修复bug,较强的解决问题能力。"}
     */

    private CvDetailInfoEntity CvDetailInfo;

    public CvDetailInfoEntity getCvDetailInfo() {
        return CvDetailInfo;
    }

    public void setCvDetailInfo(CvDetailInfoEntity CvDetailInfo) {
        this.CvDetailInfo = CvDetailInfo;
    }

    public static class CvDetailInfoEntity {
        /**
         * BasicInfo : {"name":"梁天德","phone":"15659827282","jobIntention":"Android","email":"tind.liang@foxmail.com"}
         * EducationInfo : [{"schoolName":"集美大学","timeRange":"2011.09~2015.07","degree":"本科","eduDesc":"期间取得大学英语四级证书，国家计算机等级二级证书（C语言），获优秀共青团员、优秀学生干部、二等奖学金。\n出于对编程的兴趣，修了C语言课程，考取了国家计算机二级，后对安卓较为感兴趣，自学了JavaSE和Android开发知识。"}]
         * ProfessionalSkills : [{"skillDesc":"1.比较熟悉Android和Java API,理解面向对象编程思想，对Html5有一定认识。"},{"skillDesc":"2.掌握VP、MVVM架构，策略、工厂、观察者等常用的设计模式。"},{"skillDesc":"3.掌握retrofit，rxjava，databinding，Glide，design，LitePal等流行框架"},{"skillDesc":"4.熟悉安卓界面布局、UI控件、动画绘制、具备自定义控件能力"},{"skillDesc":"5.掌握Socket和HTTP协议、JSON解析，掌握网络编程。"},{"skillDesc":"6.分析解决问题、定位Bug、ANR分析能力较好，有极尽性能优化意识。"}]
         * JobExperience : [{"timeRange":"2016/8~至今","campanyName":"上海锐至信息技术有限公司","jobName":"安卓开发工程师","jobDesc":"驻中国移动项目部开发OA系统应用\u201c移动OA\u201d和\u201c4A门户\u201d，更新维护项目代码，新功能模块代码。"},{"timeRange":"2015/8~2016/7 ","campanyName":"上海九逸科技有限公司","jobName":"安卓开发程序员","jobDesc":"负责更新维护公司\u201c业绩通\u201d产品，修改测试组返回的BUG，新增功能代码编写。"}]
         * Homepages : Githup : https://github.com/DarkSherlock
         CSDN：http://blog.csdn.net/tinderliang
         简书：http://www.jianshu.com/u/5be39d429e58
         * ProjectInstruction : [{"projectName":"一、我的简历","copyRight":"个人","downloadUrl":"https://www.pgyer.com/rcWA","devTool":"Studio，SVN，SDK版本26。","projectDesc":"个人为了展示个人简历信息并集成使用目前流行框架的APP","projectTechnology":"1.项目采用MVP+Dagger2模式。\n2.网络请求使用Retrofit2.0+Rxjava2.0+RxLifeCycle2.0+OKhttp3.0+GSON；\n3.UI使用Material design 风格,Toolbar+DrawerLayout+沉浸式状态栏;\n4.使用LeakCanary 检测内存泄漏，性能优化。\n5.腾讯Bugly SDK 实现Crash，ANR信息收集，全量更新，热更新。\n6.开源日志打印库Logger。\n7.GlideImageView实现加载圆形图片，监听进度，图片预览和高清。\n8.动态权限申请使用Permissionsdispatcher+studio插件。\n9.使用 webview封装库agentweb来加载个人主页信息，防止内存泄漏和一些遗留bug。\n10.使用阿里巴巴开源的Vlayout实现多类型复杂recyclerview布局。\n11.使用Lambda表达式使代码更简洁。\n12.简历附件下载；第三方项目中直接打开QQ消息界面。"},{"projectName":"二、移动OA","copyRight":"上海锐至信息技术有限公司","downloadUrl":"http://211.136.166.47:9080/OAAdmin/CMOA.html","devTool":"Eclipse转Studio，SVN，SDK版本21。","projectDesc":"面向上海中国移动员工的企业办公软件，有处理公文，培训课程，新闻资讯，请销假，邮件，附件下载，通讯录等功能。","projectTechnology":"1.短信验证登录，集成听云SDK检测性能,集成百度地图SDK实现定位。\n2.自定义View,九宫格密码锁屏 。\n3.自定义UncaughtExceptionHandler捕获全局异常，保存错误信息到SD卡下次启动上传到服务器。\n4.自定义textview实现右上角红色待办数量提醒。\n5.downloadmanager实现office附件下载，打开。\n6.使用pulltorefreshlistview开源项目实现列表下拉刷新。\n7.expandlistview实现多级列表显示公文详情。\n8.nineandroid实现伸缩展示的值动画。\n9.GPS定位获取经纬度进行课程签到。\n10.DragSortListview实现拖拉滑动控件。"},{"projectName":"三、业绩通","copyRight":"上海九逸科技有限公司","downloadUrl":"https://www.pgyer.com/rcWA","devTool":"Eclipse转Studio，SVN，SDK版本19。","projectDesc":"上海九逸科技有限公司开发的面向企业的门店管理软件，Emoji表情，消息推送，地图定位，考勤管理，业绩PK，在线培训，工作日志，订单发货，在线收银等功能。","projectTechnology":"1.闪屏页用自定义ShimmerTextView实现字体闪亮，验证登录。\n2.引导页用AnimatorSetb执行ObjectAnimator渐隐，缩放动画。\n3.自定义EditTextView实现带有删除编辑文字功能的搜索框。\n4.用开源项目pulltoRefreshListView实现下拉刷新，上拉加载更多。\n5.用开源项目swipemenulistview实现类似QQ的可侧滑删除的Listview。\n6.使用pulltorefreshlistview开源项目实现列表下拉刷新。\n7.用FlexibleRatingBar星星评级控件实现巡店打分功能。\n8.用开源项目GestureImageView实现预览图片时可以手势缩放图片。\n9.第三方控件Sidebar实现通讯录（A~Z）排序。\n10.用Notification和AlarmManager实现闹钟提醒功能。\n11.自定义WheelView实现圆形轮盘设置时间日期功能。\n12.webview实现查看公司主页，VideoView播放视频，MediaPlayer播放音频功能。\n13.hellocharts开源项目实现业绩图表查询功能，zxing项目实现扫描二维码功能。\n14.集成极光推送SDK实现消息推送，集成高德地图SDK实现店铺地图定位，显示天气信息功能。"},{"projectName":"四、夏洛克天气","copyRight":"个人","downloadUrl":"","devTool":"Studio，GIT，SDK版本25。","projectDesc":"为了熟悉框架自己开发的天气预报练手项目。","projectTechnology":"1.项目采用MVVM模式，使用简书作者Kelin封装DataBinding的MVVM Light Toolkit库；\n2.网络请求使用Retrofit2.0+Rxjava2.0+OKhttp3.0+GSON；\n3.数据库采用OrmLite框架；\n4.UI使用Material design 风格,ToolBar+DrawerLayout+沉浸式状态栏;\n5.使用Lambda表达式使代码更简洁;\n6.使用高德地图用于定位;\n7.CityPicker实现热门城市与全部城市列表的索引与搜索。\n8.腾讯Bugly SDK 实现Crash收集，ANR信息收集，全量更新，热更新。"}]
         * selfEvaluation : 1.较好的学习能力和孜孜不倦的学习热情，追求极客精神。
         2.有良好的沟通能力、理解能力及逻辑思维，良好的英语读写能力，能快速学习相关行业知识。
         3.工作以来积累的经验能使我较为迅速地定位bug,修复bug,较强的解决问题能力。
         */

        private BasicInfoEntity BasicInfo;
        private String Homepages;
        private String selfEvaluation;
        private List<EducationInfoEntity> EducationInfo;
        private List<ProfessionalSkillsEntity> ProfessionalSkills;
        private List<JobExperienceEntity> JobExperience;
        private List<ProjectInstructionEntity> ProjectInstruction;

        public BasicInfoEntity getBasicInfo() {
            return BasicInfo;
        }

        public void setBasicInfo(BasicInfoEntity BasicInfo) {
            this.BasicInfo = BasicInfo;
        }

        public String getHomepages() {
            return Homepages;
        }

        public void setHomepages(String Homepages) {
            this.Homepages = Homepages;
        }

        public String getSelfEvaluation() {
            return selfEvaluation;
        }

        public void setSelfEvaluation(String selfEvaluation) {
            this.selfEvaluation = selfEvaluation;
        }

        public List<EducationInfoEntity> getEducationInfo() {
            return EducationInfo;
        }

        public void setEducationInfo(List<EducationInfoEntity> EducationInfo) {
            this.EducationInfo = EducationInfo;
        }

        public List<ProfessionalSkillsEntity> getProfessionalSkills() {
            return ProfessionalSkills;
        }

        public void setProfessionalSkills(List<ProfessionalSkillsEntity> ProfessionalSkills) {
            this.ProfessionalSkills = ProfessionalSkills;
        }

        public List<JobExperienceEntity> getJobExperience() {
            return JobExperience;
        }

        public void setJobExperience(List<JobExperienceEntity> JobExperience) {
            this.JobExperience = JobExperience;
        }

        public List<ProjectInstructionEntity> getProjectInstruction() {
            return ProjectInstruction;
        }

        public void setProjectInstruction(List<ProjectInstructionEntity> ProjectInstruction) {
            this.ProjectInstruction = ProjectInstruction;
        }

        public static class BasicInfoEntity {
            /**
             * name : 梁天德
             * phone : 15659827282
             * jobIntention : Android
             * email : tind.liang@foxmail.com
             */

            private String name;
            private String phone;
            private String jobIntention;
            private String email;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getJobIntention() {
                return jobIntention;
            }

            public void setJobIntention(String jobIntention) {
                this.jobIntention = jobIntention;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }
        }

        public static class EducationInfoEntity {
            /**
             * schoolName : 集美大学
             * timeRange : 2011.09~2015.07
             * degree : 本科
             * eduDesc : 期间取得大学英语四级证书，国家计算机等级二级证书（C语言），获优秀共青团员、优秀学生干部、二等奖学金。
             出于对编程的兴趣，修了C语言课程，考取了国家计算机二级，后对安卓较为感兴趣，自学了JavaSE和Android开发知识。
             */

            private String schoolName;
            private String timeRange;
            private String degree;
            private String eduDesc;

            public String getSchoolName() {
                return schoolName;
            }

            public void setSchoolName(String schoolName) {
                this.schoolName = schoolName;
            }

            public String getTimeRange() {
                return timeRange;
            }

            public void setTimeRange(String timeRange) {
                this.timeRange = timeRange;
            }

            public String getDegree() {
                return degree;
            }

            public void setDegree(String degree) {
                this.degree = degree;
            }

            public String getEduDesc() {
                return eduDesc;
            }

            public void setEduDesc(String eduDesc) {
                this.eduDesc = eduDesc;
            }
        }

        public static class ProfessionalSkillsEntity {
            /**
             * skillDesc : 1.比较熟悉Android和Java API,理解面向对象编程思想，对Html5有一定认识。
             */

            private String skillDesc;

            public String getSkillDesc() {
                return skillDesc;
            }

            public void setSkillDesc(String skillDesc) {
                this.skillDesc = skillDesc;
            }
        }

        public static class JobExperienceEntity {
            /**
             * timeRange : 2016/8~至今
             * campanyName : 上海锐至信息技术有限公司
             * jobName : 安卓开发工程师
             * jobDesc : 驻中国移动项目部开发OA系统应用“移动OA”和“4A门户”，更新维护项目代码，新功能模块代码。
             */

            private String timeRange;
            private String campanyName;
            private String jobName;
            private String jobDesc;

            public String getTimeRange() {
                return timeRange;
            }

            public void setTimeRange(String timeRange) {
                this.timeRange = timeRange;
            }

            public String getCampanyName() {
                return campanyName;
            }

            public void setCampanyName(String campanyName) {
                this.campanyName = campanyName;
            }

            public String getJobName() {
                return jobName;
            }

            public void setJobName(String jobName) {
                this.jobName = jobName;
            }

            public String getJobDesc() {
                return jobDesc;
            }

            public void setJobDesc(String jobDesc) {
                this.jobDesc = jobDesc;
            }
        }

        public static class ProjectInstructionEntity {
            /**
             * projectName : 一、我的简历
             * copyRight : 个人
             * downloadUrl : https://www.pgyer.com/rcWA
             * devTool : Studio，SVN，SDK版本26。
             * projectDesc : 个人为了展示个人简历信息并集成使用目前流行框架的APP
             * projectTechnology : 1.项目采用MVP+Dagger2模式。
             2.网络请求使用Retrofit2.0+Rxjava2.0+RxLifeCycle2.0+OKhttp3.0+GSON；
             3.UI使用Material design 风格,Toolbar+DrawerLayout+沉浸式状态栏;
             4.使用LeakCanary 检测内存泄漏，性能优化。
             5.腾讯Bugly SDK 实现Crash，ANR信息收集，全量更新，热更新。
             6.开源日志打印库Logger。
             7.GlideImageView实现加载圆形图片，监听进度，图片预览和高清。
             8.动态权限申请使用Permissionsdispatcher+studio插件。
             9.使用 webview封装库agentweb来加载个人主页信息，防止内存泄漏和一些遗留bug。
             10.使用阿里巴巴开源的Vlayout实现多类型复杂recyclerview布局。
             11.使用Lambda表达式使代码更简洁。
             12.简历附件下载；第三方项目中直接打开QQ消息界面。
             */

            private String projectName;
            private String copyRight;
            private String downloadUrl;
            private String devTool;
            private String projectDesc;
            private String projectTechnology;

            public String getProjectName() {
                return projectName;
            }

            public void setProjectName(String projectName) {
                this.projectName = projectName;
            }

            public String getCopyRight() {
                return copyRight;
            }

            public void setCopyRight(String copyRight) {
                this.copyRight = copyRight;
            }

            public String getDownloadUrl() {
                return downloadUrl;
            }

            public void setDownloadUrl(String downloadUrl) {
                this.downloadUrl = downloadUrl;
            }

            public String getDevTool() {
                return devTool;
            }

            public void setDevTool(String devTool) {
                this.devTool = devTool;
            }

            public String getProjectDesc() {
                return projectDesc;
            }

            public void setProjectDesc(String projectDesc) {
                this.projectDesc = projectDesc;
            }

            public String getProjectTechnology() {
                return projectTechnology;
            }

            public void setProjectTechnology(String projectTechnology) {
                this.projectTechnology = projectTechnology;
            }
        }
    }
}
