/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.beans.factory.xml;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.io.Resource;

/**
 * Convenience extension of {@link DefaultListableBeanFactory} that reads bean definitions
 * from an XML document. Delegates to {@link XmlBeanDefinitionReader} underneath; effectively
 * equivalent to using an XmlBeanDefinitionReader with a DefaultListableBeanFactory.
 *
 * <p>The structure, element and attribute names of the required XML document
 * are hard-coded in this class. (Of course a transform could be run if necessary
 * to produce this format). "beans" doesn't need to be the root element of the XML
 * document: This class will parse all bean definition elements in the XML file.
 *
 * <p>This class registers each bean definition with the {@link DefaultListableBeanFactory}
 * superclass, and relies on the latter's implementation of the {@link BeanFactory} interface.
 * It supports singletons, prototypes, and references to either of these kinds of bean.
 * See {@code "spring-beans-3.x.xsd"} (or historically, {@code "spring-beans-2.0.dtd"}) for
 * details on options and configuration style.
 *
 * <p><b>For advanced needs, consider using a {@link DefaultListableBeanFactory} with
 * an {@link XmlBeanDefinitionReader}.</b> The latter allows for reading from multiple XML
 * resources and is highly configurable in its actual XML parsing behavior.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Chris Beams
 * @since 15 April 2001
 * @see org.springframework.beans.factory.support.DefaultListableBeanFactory
 * @see XmlBeanDefinitionReader
 * @deprecated as of Spring 3.1 in favor of {@link DefaultListableBeanFactory} and
 * {@link XmlBeanDefinitionReader}
 */

/**
 * TODO 已废弃,但思想依旧存在
 * 只提供最基本的ioc容器的功能;
 * XmlBeanFactory是BeanFactory最简单的实现类;
 * XmlBeanFactory的功能建立在DefaultListableBeanFactory这个容器的基础上,并在这个容器的基础上扩展了其他如xml读取的功能,
 * DefaultListableBeanFactory是很重要的一个ioc实现;
 * #ApplicationContext原理和XmlBeanFactory一样,也是通过持有或者扩展 #DefaultListableBeanFactory 来获得基本的ioc容器的功能的;
 */
@Deprecated
@SuppressWarnings({"serial", "all"})
public class XmlBeanFactory extends DefaultListableBeanFactory {
	/**
	 * 通过factory对象来使用DefaultListableBeanFactory这个容器的步骤
	 * (最原始的使用方式,在spring中,已经为用户提供了许多已经定义好的容器实现,例如 #ApplicationContext,所以ApplicationContext是一个高级形态意义的ioc容器):
	 * 1. 创建ioc配置文件的抽象资源,这个抽象资源包含了BeanDefinition的定义信息;
	 * 2. 创建一个BeanFactory,这里使用DefaultListableBeabFactory;
	 * 3. 创建一个载入BeanDefinition的读取器,这里使用XmlBeanDefinitionReader来载入xml文件形式的BeanDefinition,通过回调配置给BeanFactory;
	 * 4. 从定义好的资源位置读入配置信息,具体的解析过程由XmlBeanDefinitionReader来完成;
	 * 完成整个载入和注册Bean定义之后,需要的ioc容器就建立起来了;
	 */

	// TODO 处理以XML方式定义的BeanDefinition
	private final XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(this);


	/**
	 * Create a new XmlBeanFactory with the given resource,
	 * which must be parsable using DOM.
	 * @param resource XML resource to load bean definitions from
	 * @throws BeansException in case of loading or parsing errors
	 */
	/**
	 * 根据给定来源,创建一个XmlBeanFactory
	 * @param resource
	 * @throws BeansException
	 */
	public XmlBeanFactory(Resource resource) throws BeansException {
		this(resource, null);
	}

	/**
	 * Create a new XmlBeanFactory with the given input stream,
	 * which must be parsable using DOM.
	 * @param resource XML resource to load bean definitions from
	 * @param parentBeanFactory parent bean factory
	 * @throws BeansException in case of loading or parsing errors
	 */
	/**
	 * 根据给定来源和BeanFactory创建一个XmlBeanFactory
	 * @param resource
	 * @param parentBeanFactory
	 * @throws BeansException
	 */
	public XmlBeanFactory(Resource resource, BeanFactory parentBeanFactory) throws BeansException {
		super(parentBeanFactory);
		// ioc容器初始化的重要组成部分
		this.reader.loadBeanDefinitions(resource);
	}

}
