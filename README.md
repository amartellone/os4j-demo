# os4j-demo


This repository contains a set of java samples based on the SDK OpenStack4j, which allows provisioning and control of an OpenStack deployment. 

## Example

* Infrastructure.java: Print the basic information about a cloud environment. 
* LaunchVM.java: Launch an Ubuntu VM, using a keypair and adding a floating IP.
* ManageStorage.java: Create a Cinder volume and a new Swift container.

## How to run it

We suggest to use Maven and add to pom.xml the following dependencies:

```xml
<dependency>
  <groupId>org.pacesys</groupId>
  <artifactId>openstack4j</artifactId>
  <version>3.0.2</version>
  <classifier>withdeps</classifier>
</dependency>
<dependency>
  <groupId>org.pacesys</groupId>
  <artifactId>openstack4j-core</artifactId>
  <version>3.0.2</version>
</dependency>
<dependency>
<groupId>org.slf4j</groupId>
<artifactId>slf4j-simple</artifactId>
<version>1.7.20</version>
</dependency>
```

Edit the file resources/config.sample.properties and save it as config.sample.
Here, you should enter the basic information about your Openstack deployment.

openstack.username=myusername
openstack.pwd=mypwd
openstack.tenantName=mytenantid

## Openstack4j links

* Website: [OpenStack4j.com](http://www.openstack4j.com)
* Documentation/Tutorials: [OpenStack4j.com/learn/](http://www.openstack4j.com/learn/)
* Questions - Use Google Groups: [groups.google.com/group/openstack4j](http://groups.google.com/group/openstack4j)
* Questions - [Stackoverflow](http://stackoverflow.com/search?q=openstack4j)
* Chat on Slack: [containx.slack.com](https://containx.slack.com)
* Twitter: [@openstack4j](https://twitter.com/openstack4j)
* Changelog: [Changelog](https://github.com/ContainX/openstack4j/blob/master/CHANGELOG.md)
