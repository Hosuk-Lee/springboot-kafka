<h1 align="center">Hi 👋, I'm Hosuk.Lee</h1>
<h3 align="center">A developer from Korea</h3>

<h3 align="left">Connect with me: Don't do it, Just a study purpose.</h3>
<p align="left">
</p>

<h3 align="left">Languages and Tools:</h3>
<p align="left"> 
<a href="https://www.docker.com/" target="_blank" rel="noreferrer"><img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/docker/docker-original-wordmark.svg" alt="docker" width="40" height="40"/> </a> 
<a href="https://www.java.com" target="_blank" rel="noreferrer"><img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="40" height="40"/> </a> <a href="https://developer.mozilla.org/en-US/docs/Web/JavaScript" target="_blank" rel="noreferrer"> 
<a href="https://kafka.apache.org/" target="_blank" rel="noreferrer"><img src="https://www.vectorlogo.zone/logos/apache_kafka/apache_kafka-icon.svg" alt="kafka" width="40" height="40"/> </a> 
<a href="https://spring.io/" target="_blank" rel="noreferrer"><img src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" alt="spring" width="40" height="40"/> </a> 
</p>

<p>
Created topic quickstart-events
<br>
/opt/kafka/bin/kafka-topics.sh --create --topic quickstart-events --bootstrap-server localhost:9092
</p>

<p>
You can stop the producer client with Ctrl-C at any time.
<br>
/opt/kafka/bin/kafka-console-producer.sh --topic quickstart-events --bootstrap-server localhost:9092

log..<br>
This is my first event<br>
This is my second event<br>
</p>

<p>
/opt/kafka/bin/kafka-console-consumer.sh --topic quickstart-events --from-beginning --bootstrap-server localhost:9092
</p>