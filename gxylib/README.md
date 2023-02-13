# 业务层工具ui库
## 添加组件时要在此处添加功能的介绍，请遵循此原则
#### 1.coreui：基础ui包
+ (1) dialog：基础dialog，将其封装到baseActivity
+ (2) view：基础自定义view包
+ (3) 封装baseActivity 继承自Rx，处理生命周期，减轻对BaseActivity的封装
+ (4) 支持Mvvm的Activity和Fragment
* MvvmActivity 只有 viewmodel 的Activity
* BindingActivity 只有 databinding 的 Activity
* MvvmBindingActivity 有 viewmodel 和 databinding 的 Activity
+ (5) 支持Mvp的框架 和 BaseMvpActivity 的引入
#### 2.view包：
  -- 带删除button的Editext
####  3.livedatabus：自定义的事件bus
####  4.recycle_adapter：封装的各类adapter
- (1) delegate：支持加载更多的代理类，将adapter与加载更多逻辑解耦
- (2) qadapter：继承自basequickadapter，有支持多item的adapter
- (3) vadapter：支持vlayout的adapter， 有支持多item的adapter
- (4) baseviewhoder 封装了recycleViewHodel
- (5) convert与Multipleitem 多布局的转换器
####  5.业务的util工具类
####  6.将 vlayout 引入本地，做对androidx的支持
####  7.引入AsyncListDiffer局部刷新使用。 如果不用AsyncListDiffer，vlayout在一个adapter 调用notifyDataSetChanged 刷新时，全部adapter都会刷新
####  8.封装基于DialogFragment的git push origin :refs/tags/tag-nameBaseDialog
####  9.新增消息的小红点，BadgeView
####  10.新增替换startActivityForResult的方法
####  11.rxHandler中去掉setHandler方法，新增ActivityEvent生命周期控制的方法
####  12.新增LargeLifecycleLiveData，将LiveData的活跃状态延长到CREATED