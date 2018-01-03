## spring-aop

### ioc容器初始化
  在ioc容器初始化的时候解析xml遇到命名空间就会去找命名空间对应的Handler处理,解析不同的标签调用不同的parse,
这里找到AopNamespaceHandler调用ConfigBeanDefinitionParser,在其Parser方法中注册了一个AspectJAwareAdvisorAutoProxyCreator类,
解析了aop相关的标签,譬如<aop:pointcut><aop:aspect>等

### 代理对象初始化和创建
  因为我们向容器注册了一个AspectJAwareAdvisorProxyCreator类,所以我们在创建bean的时候会执行postProcessorBeforeInstantiation方法,
所以当我们第一次getBean的时候(也就是创建bean的时候),会调用postProcessBeforeInstantiation方法,这个方法做了3件事:
  1. 获取bean对应的Advisor
  2. 获取所有匹配的bean的Advisor,使用AspectJExpressionPointcut匹配;
  3. 创建代理对象,如果被对象继承接口就是用jdk代理,否则默认使用cglib代理;

### 执行代理对象
  JdkDynamicAopProxy实现了InvocationHandler接口,这就说明每个代理类的实例都关联到一个handler,当我们通过代理对象调用一个方法的时候,]
这个方法的调用就会被转发为由InvocationHandler这个接口的invoke方法来进行调用;invoke方法做了一下几件事:
  1. 获取所有的Advisor,封装到ReflectiveMethodInvocation中形成拦截器链;
  2. 如果连接器链为空就执行代理方法;
  3. 如果有拦截器,通过InterceptorAndDynamicMethodMatcher匹配,匹配成功就在拦截器类进行逻辑调用;

### spring aop源码分析
  在spring aop的基本实现中,我们可以看到proxy代理对象的使用,在程序中是一个非常重要的部分,spring aop充分利用java的Proxy,反射
以及第三方的cglib这些方案,通过这些技术,完成了aop的AopProxy代理对象的生成;
  回顾整个源码的实现过程,可以看到,首先在容器初始化时解析标签注册自动代理创建器AspectJAwareAdvisorAutoProxyCreator;
在创建初始化和创建bean的时候,通过BeanFactoryAdvisorRetrievalHelper获取所有bean相关的Advisor,使用AspectJExpressionPointcut
进行匹配Advisor,从而获取匹配的Advisor,最后通过ProxyFactory创建代理对象;
  最终AopProxy代理对象的产生,会交给JdkDynamicAopProxy和CglibAopProxy这两个工厂来完成,用的就是我们最开始说到的技术;
  在完后AopProxy代理对象后,我们就可以对Aop切面逻辑进行实现了,首先会对这些方法进行拦截,从而为这些方法提供工作空间,随后进行回调.
从而完成aop切面实现的一整个逻辑,而这里的拦截JdkDynamicAopProxy主要是通过内部的invoke方法来实现,而cglib是通过getCallbacks方法来完成的,
他们为aop切面的实现提供了舞台;

### 总结
  spring aop秉承spring一贯的设计理念,致力于aop框架与ioc容器的紧密继承,以此来为j2ee的开发人员服务;aop的实现是一个三足鼎立的世界:
AspectJ,JBoss AOP,spring aop;
  特别是AspectJ,我们知道spring的增强都是标准的java类编写的;我们可以用一般的java开发环境进行开发切面,虽然好用,但是开发人员必须对java
开发相当熟悉,仅仅使用java也有一定的局限性;而AspectJ与之相反,它专注切面的开发,虽然最初也仅仅是作为java语言的扩展方式来实现,但是通过
特定的aop语言,我们可以获得更强大以及细粒度的控制,从而丰富了aop工具集,所以spring aop为弥补自身的不足,在源码中集成了Aspect框架;