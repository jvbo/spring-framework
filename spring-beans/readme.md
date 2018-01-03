## spring-beans总结
### bean的完整生命周期
1. bean自身的方法:这个包括了bean本身调用的方法和通过配置文件中<bean>的init-method和destroy-method指定的方法;
2. bean级生命周期接口方法:这个包括了BeanNameAware,BeanFactoryAware,InitializingBean和DisposableBean这些接口的方法;
3. 容器级生命周期接口方法:这个包括了InstantiationAwareBeanPostProcessor和BeanPostProcessor这两个接口的实现,
一般称它们的实现了为后处理器;
4. 工厂后处理器接口方法:这个包括了AspectJWeavingEnabler,ConfigurationClassPostProcessor,CustomAutowireConfigurer等等非常
有用的工厂后处理器接口的方法;工厂后处理器也是容器级的;在应用上下文装配配置文件之后立即调用;

### 总结
  spring ioc容器的核心是BeanFactory和BeanDefiniton;分别对应对象工厂和依赖配置的概念;虽然我们通常使用的是ApplicationContext的实现类,
但Applicaiton只是封装和扩展了BeanFactory的功能,xml的配置形式只是spring依赖注入的一种常用形式而已,而AnnotationConfigAppliationContext
配合Annotation注解和泛型,早已经提供了更简易的配置方式,AnnotationConfigApplicationContext和AnnotationConfigWebApplicationContext则是
实现无xml配置的核心接口,但无论你使用任何配置,最后都会映射到BeanDefinition;
  其次,这里特别要注意的还是BeanDefinition,bean在xml文件里面的展现形式是<bean id="">...</bean>,当这个节点被加载到内存中,就被抽象
为BeanDefinition了,在xml bean节点中的那些关键字,在BeanDefinition中都有相对应的成员变量;如何把一个xml节点转换成BeanDefinition,
这个工作由BeanDefinitionReader来完成的;spring通过定义BeanDefinition来管理基于spring的应用中的各种对象以及它们之间的相互依赖关系;
BeanDefinition抽象了我们对bean的定义,是让容器起作用的主要数据类型;我们知道在计算机里,所有的功能都是建立在通过数据对现实进行抽象的基础上的;
ioc容器是用BeanDefinition来管理对象依赖关系的,对ioc容器而言,BeanDefinition就是对控制反转模式中管理的对象依赖关系的数据抽象,也是容器实现
控制反转的核心数据结构,有了他们容器才能发挥作用;

### ioc容器启动流程
1. 启动同期refresh();
2. 完成resource定位;
3. 使用DefaultBeanDefinitionDocumentReader读入xml文件,并将xml转换为BeanDefinition;
4. 使用BeanDefinitionParserDelegate进行解析;
5. 使用DefaultListableBeanFactoy进行注册;

### ioc依赖注入流程
1. 得到bean;
2. 在父子容器中寻找bean,没有找到就创建bean;
3. 默认使用SimpleInstantiationStrategy类通过cglib代理生成对象;
4. 通过BeanDefinitionValueResolver类来解析依赖属性;
5. 使用AbstractPropertyAccessor来注入依赖属性;

其实ioc从原理上看很简单,就是把xml文件解析出来,然后放到内存的map里,最后在内置容器里管理bean;
但是看ioc的源码,却发现其非常庞大,看着很吃力;这是因为spring加入了很多特性和为扩展性预留很多的接口,
这些特性和接口,造就了spring无与伦比的功能以及未来无限扩展的可能性,
可以说正是他们将技术的美学已最简单的方法呈现在了人们的面前,当然这也导致了它的复杂性;