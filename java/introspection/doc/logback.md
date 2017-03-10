Logback
=======

log hibernate
======

logger les requetes
-----
org.hibernate.SQL           Log all SQL DML statements as they are executed
org.hibernate.type          Log all JDBC parameters
org.hibernate.tool.hbm2ddl  Log all SQL DDL statements as they are executed


configure hibernate.cfg.xml
        <property name="hbm2ddl.auto">create</property>
        <property name="format_sql">true</property>
        <property name="show_sql">true</property>



logger dans differents fichiers
=====

En spécifiant l'appender dans le logger
-----

    <appender name="ROLLING1" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>log/file1.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>log/file1/%d{yyyy-MM-dd}_%i.log</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>10MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
        </rollingPolicy>
        <encoder>
          <pattern>%date{HH:mm:ss.SSS};%-5level;%-21.21thread;%-30logger{0}:%3L; %msg%ex%n</pattern>
        </encoder>
    </appender>
    <appender name="ROLLING2" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${log.dir}/file2.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>log/file1/%d{yyyy-MM-dd}_%i.log</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>10MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
        </rollingPolicy>
        <encoder>
              <pattern>%date{HH:mm:ss.SSS};%-5level;%-21.21thread;%-30logger{0}:%3L; %msg%ex%n</pattern>
        </encoder>
    </appender>




    <logger name="fr.incore_systemes.si.mts.manager.javacore.communication.COMLMManager" level="INFO">
        <appender-ref ref="JAVACORE_COMLM"/>
    </logger>
    <logger name="fr.incore_systemes.si.mts.manager.javacore.communication.CCoreManager" level="INFO">
        <appender-ref ref="JAVACORE_CCORE"/>
    </logger>


Avec un filter
----

    <appender name="PERF" class="ch.qos.logback.core.rolling.RollingFileAppender">
    	<filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <level>INFO</level>
        </filter>

    	<file>D:\opt\log\perf.log</file>
		<rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
			<!-- rollover daily -->
			<fileNamePattern>D:\opt\log\perf-%d{yyyy-MM-dd}-%i.log</fileNamePattern>
			<timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
				<maxFileSize>1MB</maxFileSize>
			</timeBasedFileNamingAndTriggeringPolicy>
		</rollingPolicy>
		<encoder>
		     <charset>UTF-8</charset>
			<pattern>%d;%msg%ex{6}%n</pattern>
		</encoder>
    </appender>


Créer un logger custom
======

