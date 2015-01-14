How to convert the Sportsclub project that demonstrates Snowdrop to be Snowdrop-less.
------------------------------------------------------------------------------------

1. **Delete** the Snowdrop module from EAP.
    - *jboss-eap/modules/system/add-ons/snowdrop/org/jboss/snowdrop/main*

2. **Delete** the reference to Snowdrop from:
    - *pom.xml* (the parent pom)  
    
            <snowdrop.version>3.1.2-SNAPSHOT</snowdrop.version>

            <!-- Snowdrop -->
            <dependency>
                <groupId>org.jboss.snowdrop</groupId>
                <artifactId>snowdrop-vfs</artifactId>
                <version>${snowdrop.version}</version>
            </dependency>
            <dependency>
                <groupId>org.jboss.snowdrop</groupId>
                <artifactId>snowdrop-weaving</artifactId>
                <version>${snowdrop.version}</version>
            </dependency>
            <dependency>
                <groupId>org.jboss.snowdrop</groupId>
                <artifactId>snowdrop-interceptors</artifactId>
                <version>${snowdrop.version}</version>
            </dependency>
            <dependency>
                <groupId>org.jboss.snowdrop</groupId>
                <artifactId>snowdrop-namespace</artifactId>
                <version>${snowdrop.version}</version>
            </dependency>

    
    - *sportsclub-ear/src/main/application/META-INF/jboss-deployment-structure.xml*  
    
            <module name="org.jboss.snowdrop" export="true"/>

    - *sportsclub-jpa-ear/src/main/application/META-INF/jboss-deployment-structure.xml*  

            <module name="org.jboss.snowdrop" export="true"/>

    - *sportsclub-invoicing-webmvc/pom.xml*  

            <dependency>
                <groupId>org.jboss.snowdrop</groupId>
                <artifactId>snowdrop-vfs</artifactId>
                <scope>provided</scope>
                <exclusions>
                    <exclusion>
                        <groupId>aopalliance</groupId>
                        <artifactId>aopalliance</artifactId>
                    </exclusion>
                    <exclusion>
                        <groupId>org.springframework</groupId>
                        <artifactId>spring-aop</artifactId>
                    </exclusion>
                    <exclusion>
                        <groupId>org.springframework</groupId>
                        <artifactId>spring-beans</artifactId>
                    </exclusion>
                    <exclusion>
                        <groupId>org.springframework</groupId>
                        <artifactId>spring-core</artifactId>
                    </exclusion>
                    <exclusion>
                        <groupId>org.springframework</groupId>
                        <artifactId>spring-expression</artifactId>
                    </exclusion>
                    <exclusion>
                        <groupId>org.springframework</groupId>
                        <artifactId>spring-context</artifactId>
                    </exclusion>
                </exclusions>
            </dependency>

            <profiles>
            ...
                <dependencies>
                    <dependency>
                        <groupId>org.jboss.snowdrop</groupId>
                        <artifactId>snowdrop-namespace</artifactId>
                        <exclusions>
                            <exclusion> 
                                <groupId>org.springframework.ws</groupId> 
                                <artifactId>spring-oxm</artifactId> 
                            </exclusion> 
                            <exclusion> 
                                <groupId>org.springframework</groupId> 
                                <artifactId>spring-aop</artifactId> 
                            </exclusion> 
                            <exclusion> 
                                <groupId>org.springframework</groupId> 
                                <artifactId>spring-expression</artifactId> 
                            </exclusion> 
                            <exclusion> 
                                <groupId>org.springframework</groupId> 
                                <artifactId>spring-beans</artifactId> 
                            </exclusion> 
                            <exclusion> 
                                <groupId>org.springframework</groupId> 
                                <artifactId>spring-core</artifactId> 
                            </exclusion> 
                            <exclusion> 
                                <groupId>org.springframework</groupId> 
                                <artifactId>spring-context</artifactId> 
                            </exclusion> 
                            <exclusion> 
                                <groupId>org.springframework</groupId> 
                                <artifactId>spring-context-support</artifactId> 
                            </exclusion> 
                            <exclusion> 
                                <groupId>commons-logging</groupId> 
                                <artifactId>commons-logging</artifactId> 
                            </exclusion> 
                            <exclusion> 
                                <groupId>aopalliance</groupId> 
                                <artifactId>aopalliance</artifactId> 
                            </exclusion> 
                        </exclusions>
                    </dependency>
                </dependencies>

    - *sportsclub-invoicing-ejb/pom.xml*  

            <dependency>
                <groupId>org.jboss.snowdrop</groupId>
                <artifactId>snowdrop-interceptors</artifactId>
                <scope>provided</scope>
            </dependency>

    - *sportsclub-subscriptions-ejb/pom.xml*  

            <dependency>
                <groupId>org.jboss.snowdrop</groupId>
                <artifactId>snowdrop-interceptors</artifactId>
                <scope>provided</scope>
            </dependency>

3. **Add** the reference to spring-context in:
    - *sportsclub-invoicing-ejb/pom.xml*  

            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-context</artifactId>
                <scope>provided</scope>
            </dependency>

        - *NOTE: This one just needs the scope changed from `test` to `provided`*

    - *sportsclub-subscriptions-ejb/pom.xml*  

            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-context</artifactId>
                <scope>provided</scope>
            </dependency>

4. **Delete**
    - *sportsclub-ear/src/main/application/META-INF/jboss-spring.xml*  
    - *sportsclub-jpa-ear/src/main/application/META-INF/jboss-spring.xml*  

5. **Configure** the injection annotations:
    - *sportsclub-invoicing-ejb/src/main/java/org/jboss/spring/samples/sportsclub/invoicing/services/BillingServiceImpl.java*  
    
        **Replace** `@Interceptors(SpringLifecycleInterceptor.class)` **with** `@Interceptors(SpringBeanAutowiringInterceptor.class)`  
    
        **Replace** `@Spring(bean = "invoiceRepository", jndiName = "SpringDao")` **with** `@Autowired`  
    
        **Replace** `@Spring(bean = "accountRepository", jndiName = "SpringDao")` **with** `@Autowired`  
    
        **Replace** `@Spring(bean = "paymentRepository", jndiName = "SpringDao")` **with** `@Autowired`  

    - *sportsclub-subscriptions-ejb/src/main/java/org/jboss/snowdrop/samples/sportsclub/ejb/SubscriptionServiceImpl.java*  
    
        **Replace** `@Interceptors(SpringLifecycleInterceptor.class)` **with** `@Interceptors(SpringBeanAutowiringInterceptor.class)`  
    
        **Replace** `@Spring(bean = "accountRepository", jndiName = "SpringDao")` **with** `@Autowired`  
        
        **Replace** `@Spring(bean = "membershipRepository", jndiName = "SpringDao")` **with** `@Autowired`  

6. **Create** the beanRefContext.xml file.
    - *sportsclub-invoicing-ejb/src/main/resources/beanRefContext.xml*  
    with the following content  
    
        <?xml version="1.0" encoding="UTF-8"?>
        <beans xmlns="http://www.springframework.org/schema/beans"
               xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xmlns:context="http://www.springframework.org/schema/context"
               xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                                   http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
            
            <bean id="app-inv-context" class="org.springframework.context.support.ClassPathXmlApplicationContext">
                <constructor-arg>
                    <list>
                        <value>infrastructure.xml</value>
                        <value>dao-context.xml</value>
                    </list>
                </constructor-arg>
            </bean>
        
        </beans>

    This creates the bean ClassPathXmlApplicationContext that references the xml files that have the beans that will be 
    injected. In this case it will have dao-context.xml and infrastructure.xml.  
    
7. **Delete** the imports since we are bringing them in with the beanRefContext.xml.
    - *sportsclub-invoicing-webmvc/src/main/webapp/WEB-INF/spring-business-context.xml*  
    
            <import resource="classpath*:dao-context.xml"/>
            <import resource="classpath*:infrastructure.xml"/>

8. **Configure** parameters in:
    - *sportsclub-invoicing-webmvc/src/main/webapp/WEB-INF/web.xml*  
        - **Delete**  

                <context-param>
                    <param-name>contextClass</param-name>
                    <param-value>org.jboss.spring.vfs.context.VFSXmlWebApplicationContext</param-value>
                </context-param>
    
                <init-param>
                    <param-name>contextClass</param-name>
                    <param-value>org.jboss.spring.vfs.context.VFSXmlWebApplicationContext</param-value>
                </init-param>
    
                <init-param>
                    <param-name>contextClass</param-name>
                    <param-value>org.jboss.spring.vfs.context.VFSXmlWebApplicationContext</param-value>
                </init-param>

        - **Add** context-params in order to get the beanRefContext.xml picked up.

                <context-param>
                    <param-name>locatorFactorySelector</param-name>
                    <param-value>classpath*:beanRefContext.xml</param-value>
                </context-param>
                <context-param>
                    <param-name>parentContextKey</param-name>
                    <param-value>app-inv-context</param-value>
                </context-param>

    - *sportsclub-invoicing-webmvc/src/main/webapp-messaging/WEB-INF/spring-messaging-context.xml*  
        - **Delete**  

                xmlns:jboss="http://www.jboss.org/schema/snowdrop"
                
                http://www.jboss.org/schema/snowdrop http://www.jboss.org/schema/snowdrop/snowdrop.xsd
                
                <jboss:activation-spec-factory id="activationSpecFactory" />
                <jboss:resource-adapter id="resourceAdapter"/>

        - **Add**  

                <bean id="activationSpecFactory" class="org.springframework.jms.listener.endpoint.DefaultJmsActivationSpecFactory">
                    <property name="activationSpecClass" value="org.hornetq.ra.inflow.HornetQActivationSpec" />
                </bean>

                <bean id="resourceAdapter" class="org.springframework.jca.support.ResourceAdapterFactoryBean">
                    <property name="resourceAdapter">
                        <bean class="org.hornetq.ra.HornetQResourceAdapter">
                            <property name="connectorClassName" value="org.hornetq.core.remoting.impl.invm.InVMConnectorFactory" />
                            <property name="transactionManagerLocatorClass" value="org.jboss.as.messaging.jms.TransactionManagerLocator" />
                            <property name="transactionManagerLocatorMethod" value="getTransactionManager" />
                        </bean>
                    </property>
                    <property name="workManager">
                        <bean class="org.springframework.jca.work.SimpleTaskWorkManager" />
                    </property>
                </bean>

9. *NOTE: If you use Spring as an installed Module in EAP you need a jandex.idx for spring-context.*  
    The problem is that the Annotations are not being picked up. Use the jandex.jar in the modules at org/jboss/jandex 
    and index the spring-conext.jar. 
    
    On the CLI modify the Spring jar directly like this:
    
            $ java -jar [JBOSS-HOME]/modules/system/layers/base/org/jboss/jandex/main/jandex-1.0.3.Final-redhat-2.jar -m spring-context.jar

    **OR**

            java -jar [JBOSS_HOME]/modules/system/layers/base/org/jboss/jandex/main/jandex-1.0.3.Final-redhat-2.jar spring-context.jar
            mkdir /tmp/META-INF
            mv spring-context.ifx /tmp/META-INF/jandex.idx
            jar cf index.jar -C /tmp META-INF/jandex.idx

    Then include the created jandex index.jar in the same dir as Spring-context.jar. Don't for get to add/modify the 
    module.xml to reference it.

    See [WildFly Jandex doc](https://github.com/wildfly/jandex) for more details.
    

How to convert the Sportsclub project to **not** depend on Spring Modules installed into EAP.
------------------------------------------------------------------------------------------


1. **Delete** the Snowdrop module from EAP.
    - *jboss-eap/modules/system/add-ons/snowdrop/org/springframework/spring/snowdrop*

2. **Delete** the Spring dependency from:
    - *sportsclub-ear/src/main/application/META-INF/jboss-deployment-structure.xml*  

            <module name="org.springframework.spring" slot="snowdrop" export="true">
                <exports>
                    <include path="META-INF**"/>
                    <include path="org**"/>
                </exports>
                <imports>
                    <include path="META-INF**"/>
                    <include path="org**"/>
                </imports>
            </module>

    - *sportsclub-jpa-ear/src/main/application/META-INF/jboss-deployment-structure.xml*  

            <module name="org.springframework.spring" slot="snowdrop" export="true">
                <exports>
                    <include path="META-INF**"/>
                    <include path="org**"/>
                </exports>
                <imports>
                    <include path="META-INF**"/>
                    <include path="org**"/>
                </imports>
            </module>

3. **Add** the faces module dependency to:
    - *sportsclub-ear/src/main/application/META-INF/jboss-deployment-structure.xml*  

            <module name="javax.faces.api" export="true"/>

    - *sportsclub-jpa-ear/src/main/application/META-INF/jboss-deployment-structure.xml*  

            <module name="javax.faces.api" export="true"/>

4. **Add** all the Spring jars to the EAR pom.xml files.  
5. **Update** all references to Spring in the WAR and JAR pom.xml files to the `<scope>provided</scope>`.  
6. **Delete** `<exclusions>` of Spring jars from the pom.xml files.  
